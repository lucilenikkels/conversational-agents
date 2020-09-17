package furhatos.app.spaceshipattendant.flow

import furhatos.app.spaceshipattendant.checkinData
import furhatos.app.spaceshipattendant.nlu.*
import furhatos.flow.kotlin.*
import furhatos.nlu.common.No
import furhatos.nlu.common.PersonName
import furhatos.nlu.common.Yes

val NUM_SUITES = 10
val NUM_CITIZEN_ROOMS = 10
var suitesBooked = 0
var citizenRoomsBooked = 0

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

val RobotIntro : State = state {
    onEntry {
        furhat.say("Welcome to Starship Enterprise. We are currently leaving for a 12-day voyage from\n" +
                "planet Earth to planet Vulkan. My name is Data and I am your check-in assistant for today.")
        furhat.ask("Would you like to check in?")
    }

    onResponse<No> {
        goto(UserDeclines)
    }

    onResponse<CheckIn> {
        goto(CheckinIntro)
    }

    onResponse<Yes> {
        goto(CheckinIntro)
    }
}

val UserDeclines = state {
    onEntry {
        furhat.say("Without your information I cannot book you in.")
        furhat.ask("Are you sure?")
    }

    onResponse<No> {
        goto(HowManyGuests)
    }

    onResponse<Yes> {
        furhat.say("Goodbye then.")
        goto(Idle)
    }
}

val CheckinIntro = state {
    onEntry {
        furhat.say("Great! As the travel is longer than two days on our journey to Vulkan, regulation requires\n" +
                "we ask a few questions.")
        furhat.ask("Is that okay with you?")
    }

    onResponse<No> {
        goto(UserDeclines)
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

    onReentry {
        furhat.ask("How many people would you like to checkin?")
    }

    onResponse<NumberOfGuests> {
        val guests = it.intent.gs
        if (guests != null) {
            users.current.checkinData.guestNumber = guests
            goto(RandomQuestion)
        }
        else {
            propagate()
        }
    }
}

val RandomQuestion = state(Interaction) {
    onEntry {
        furhat.ask("Great. By the way, would you like to know about the available amenities in our rooms?")
    }

    onResponse<Yes> {
        furhat.say(" You are provided a bed, a table, a chair, and a Replicator, which allows you to instantly\n" +
                "create any dish you've ever wanted to eat, in the comfort of your own room.\n")
        goto(FurtherDetails)
    }

    onResponse<No> {
        goto(FurtherDetails)
    }
}

val FurtherDetails : State = state {
    onEntry {
        furhat.ask(" Perfect. Now, could you give me your name, how long you intend to stay on Starship\n" +
                "Enterprise, and whether you would like to stay in our Suite-class rooms or the Citizen-class rooms?\n"+
                "Suite class have 2 beds, citizen-class have 1 bed.")
    }

    onReentry {
        furhat.ask("Could you repeat that?")
    }

    onResponse<Details> {
        val name = it.intent.name
        val duration = it.intent.duration
        val type = it.intent.type
        val guests = users.current.checkinData.guestNumber
        if (duration != null) {
            if (type?.value.equals("suite")) {
                val roomsLeft = NUM_SUITES - suitesBooked
                val roomsNeeded = guests?.value!!.div(2)
                if (roomsNeeded > roomsLeft) {
                    goto(Overloaded(roomsLeft))
                }
            } else if (type?.value.equals("citizen room")) {
                val roomsLeft = NUM_CITIZEN_ROOMS - citizenRoomsBooked
                val roomsNeeded = guests?.value!!
                if (roomsNeeded > roomsLeft) {
                    goto(Overloaded(roomsLeft))
                }
            }
            furhat.say("Noted. $name wants to stay for $duration in the $type.")
            goto(detailsReceived(name, duration, type))
        }
    }
}

fun Overloaded(roomsLeft : Int) = state(Interaction) {
    onEntry {
        furhat.say(" Unfortunately there are no rooms left of this kind. We only " +
            "have $roomsLeft rooms of this kind free. " +
            "Would you like to change the number of people you are checking in?")
    }
}

fun detailsReceived(name: PersonName? = null, duration: DurationEntity?, type: RoomType?) : State = state {
    onEntry {
        users.current.checkinData.name = name
        users.current.checkinData.duration = duration
        users.current.checkinData.type = type
        if (type?.value.equals("suite")) {
            suitesBooked++
        } else if (type?.value.equals("citizen room")) {
            citizenRoomsBooked++
        }
        furhat.say(" Amazing. The data has been entered to your name, $name.")
        goto(Idle)
    }
}

val CheckinCancel = state {
    onEntry {
        furhat.say("Alright then, please tell me if you'd like to start over. Otherwise, I wish you a good day.")
        goto(Idle)
    }
}