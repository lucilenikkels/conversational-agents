package furhatos.app.spaceshipattendant.flow.states

import furhatos.app.spaceshipattendant.checkinData
import furhatos.app.spaceshipattendant.flow.CustomGaze
import furhatos.app.spaceshipattendant.flow.gaze.DataDrivenGaze
import furhatos.app.spaceshipattendant.nlu.FurtherDetailsResponse
import furhatos.app.spaceshipattendant.roomsLeft
import furhatos.flow.kotlin.furhat
import furhatos.flow.kotlin.onResponse
import furhatos.flow.kotlin.state
import furhatos.flow.kotlin.users

val FurtherDetails = state {
    onEntry {
        parallel {
            goto(CustomGaze)
        }

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
        } else if (room.toString() == "suite" || room.toString() == "sweet" || room.toString() == "suit") {
            val roomsNeeded = Integer.parseInt(guests.toString()) / 2 + Integer.parseInt(guests.toString()) % 2;

            if (roomsNeeded > users.current.roomsLeft.suiteRoomsLeft)
                goto(StarshipOverloaded(users.current.roomsLeft.suiteRoomsLeft));

            users.current.roomsLeft.citizenRoomsLeft -= roomsNeeded
            goto(SpecificWishes(name.toString()))
        }

    }
}