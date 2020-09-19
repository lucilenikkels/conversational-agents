package furhatos.app.spaceshipattendant.flow

import furhatos.app.spaceshipattendant.checkinData
import furhatos.app.spaceshipattendant.nlu.*
import furhatos.app.spaceshipattendant.roomsLeft
import furhatos.flow.kotlin.*
import furhatos.gestures.Gestures
import furhatos.nlu.common.*
import furhatos.nlu.common.Number
import kotlinx.coroutines.yield

val Start = state(Interaction) {
    onEntry {
        furhat.ask("Hello, how can I help you?")
    }

    onResponse<CheckIn> {
        goto(CheckinIntro)
    }

    onResponse<Help> {
        goto(RobotIntro)
    }
}

val CheckinCancel = state {
    onEntry {
        furhat.say("Alright then, please tell me if you'd like to start over. Otherwise, I wish you a good day.")
        goto(Idle)
    }
}

val RobotIntro: State = state {
    onEntry {
        furhat.say("Welcome to Starship Enterprise. We are currently leaving for a 12-day voyage from\n" +
                "planet Earth to planet Vulkan. My name is Data and I am your check-in assistant for today?")
        furhat.ask("Would you like to check in?")
    }

    onResponse<No> {
        goto(CheckinCancel)
    }

    onResponse<CheckIn> {
        goto(CheckinIntro)
    }

    onResponse<Yes> {
        goto(CheckinIntro)
    }
}

val Goodbye = state {
    onEntry {
        furhat.say("Goodbye then.")
        goto(Idle)
    }
}

val UserDeclinesGivingInfo = state {
    onEntry {
        furhat.say("Without your information I cannot book you in.")
        furhat.ask("Are you sure?")
    }

    onResponse<No> {
        goto(HowManyGuests)
    }

    onResponse<Yes> {
        goto(Goodbye)
    }
}

val CheckinIntro = state {
    onEntry {
        furhat.say("Great! As the travel is longer than two days on our journey to Vulkan, regulation requires\n" +
                "we ask a few questions.")
        furhat.ask("Is that okay with you?")
    }

    onResponse<No> {
        goto(UserDeclinesGivingInfo)
    }

    onResponse<Yes> {
        goto(HowManyGuests)
    }
}

val HowManyGuests = state {
    onEntry {
        furhat.say("Let's get started then.")
        furhat.ask("How many people would you like to checkin?")
    }

    onResponse<NumberOfGuests> {
        val guests = it.intent.gs
        println(guests)
        if (guests != null) {
            goto(GuestCountReceived(guests))
        }
        else {
            propagate()
        }
    }

}


fun StarshipOverloaded(rooms: Int): State = state(Interaction) {
    onEntry {
        furhat.say("Unfortunately there are no rooms left of this kind. We only have $rooms rooms of this kind free. ");
        furhat.ask("Would you like to change the number of people you are checking in?");
    }

    onResponse<No> {
        goto(CheckinCancel)
    }

    onResponse<Yes> {
        goto(NumberOfPeopleChange(rooms))
    }
}

val WishesLoop = state {
    onEntry {
        furhat.say("Understood");
        furhat.ask("Anything else?")
    }

    onResponse<No> {
        goto(EndOfWishes)
    }

    onResponse {
        reentry()
    }
}

val EndState = state {
    onEntry {
        furhat.say("Understood. You have now successfully checked in. " +
                "You will soon be teleported to your room, and your luggage will be delivered by our staff. " +
                "We hope your stay at Starship Enterprise will be a fun and relaxing one..")
    }
}

val StarshipActivities = state {
    onEntry {
        furhat.ask("On Starship Enterprise we offer numerous simulated activities" +
                "namely: Skiing, Tennis, Badminton, and Zombie Survival. " +
                "Please tell me which ones of those activities you would like to sign up for today.")
    }

    onResponse<No> {
        goto(EndState)
    }

    onResponse {
        goto(EndState)
    }
}


val NoSpecificWishes = state {
    onEntry {
        furhat.say("Alright, then let's move on.")
        goto(StarshipActivities)
    }
}


fun SpecificWishes(name: String) = state(Interaction) {
    onEntry {
        furhat.say("Amazing!")
        furhat.say("The data has been entered to your name, $name.")
        furhat.ask("Now, before asking you about the different activities we offer on board, " +
                "I would like to ask you if you have any specific wishes for your stay here?")
    }

    onResponse<No> {
        goto(NoSpecificWishes)
    }

    onResponse {
        goto(WishesLoop)
    }
}


val FurtherDetails = state {
    onEntry {

        furhat.say("Perfect");
        furhat.ask("Now, could you give me your name, how long you intend to stay on Starship Enterprise," +
                "and whether you would like to stay in out Suite-class rooms ir Citizen-class rooms?")
        furhat.listen(30000)
    }


    onResponse<FurtherDetailsResponse> {
        val name = it.intent.name
        val duration = it.intent.duration
        val room = it.intent.room

        users.current.checkinData.name = name.toString()
        users.current.checkinData.lastWantedRoom = room.toString()


        furhat.say("So $name, you want to stay $duration days in a $room room, let's see");
        // room logic
        val guests = users.current.checkinData.guestNumber
        if (room.toString() == "citizen") {
            println()
            if (guests > users.current.roomsLeft.citizenRoomsLeft)
                goto(StarshipOverloaded(users.current.roomsLeft.citizenRoomsLeft));

            users.current.roomsLeft.citizenRoomsLeft -= Integer.parseInt(guests.toString())
            goto(SpecificWishes(name.toString()))
        } else if (room.toString() == "suite") {
            val roomsNeeded = Integer.parseInt(guests.toString()) / 2 + Integer.parseInt(guests.toString()) % 2;

            if (roomsNeeded > users.current.roomsLeft.suiteRoomsLeft)
                goto(StarshipOverloaded(users.current.roomsLeft.suiteRoomsLeft));

            users.current.roomsLeft.citizenRoomsLeft -= roomsNeeded
            goto(SpecificWishes(name.toString()))
        }

    }
}

val RandomQuestion1 = state {
    onEntry {
        furhat.say("Great!")
        furhat.ask("By the way, would you like to know about the available amenities in our rooms?")

    }

    onResponse<Yes> {
        furhat.say("You are provided a bed, a table, a chair, and a Replicator, " +
                "which allows you to instantly create any dish you've ever wanted to eat, in the comfort of your own room.")
        goto(FurtherDetails)
    }

    onResponse<No> {
        goto(FurtherDetails)
    }

    onResponse { goto(FurtherDetails) }
}


val EndOfWishes = state {
    onEntry {
        furhat.say("All right, your demands have been noted and will be read by the crew. " +
                "Let's move on then.");
        goto(StarshipActivities)
    }
}


fun NumberOfPeopleChange(rooms: Int): State = state(Interaction) {
    onEntry {
        furhat.say("Wonderful. Please tell me how many guests you would like to check in.")
    }

    onResponse<NumberOfGuests> {
        val guests = it.intent.gs
        if (guests != null) {
            users.current.checkinData.guestNumber = Integer.parseInt(guests.toString())

            val roomType = users.current.checkinData.lastWantedRoom

            val guestsInt = Integer.parseInt(guests.toString())
            if (roomType == "citizen") {

                if (guestsInt > users.current.roomsLeft.citizenRoomsLeft)
                    goto(StarshipOverloaded(users.current.roomsLeft.citizenRoomsLeft));

                users.current.roomsLeft.citizenRoomsLeft -= guestsInt
                goto(SpecificWishes(users.current.checkinData.name))
            } else if (roomType == "suite") {
                val roomsNeeded = guestsInt / 2 + guestsInt % 2;

                if (roomsNeeded > users.current.roomsLeft.suiteRoomsLeft)
                    goto(StarshipOverloaded(users.current.roomsLeft.suiteRoomsLeft));

                users.current.roomsLeft.citizenRoomsLeft -= roomsNeeded
                goto(SpecificWishes(users.current.checkinData.name))

            }
        } else {
            goto(StarshipOverloaded(rooms))
        }
    }
}


fun GuestCountReceived(guests: Number): State = state(Interaction) {
    onEntry {
        users.current.checkinData.guestNumber = Integer.parseInt(guests.toText())
        furhat.say("$guests guests, alright.")
        goto(RandomQuestion1)
    }
}



























