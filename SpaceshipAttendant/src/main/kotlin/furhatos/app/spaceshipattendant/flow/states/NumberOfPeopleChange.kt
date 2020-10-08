package furhatos.app.spaceshipattendant.flow.states

import furhatos.app.spaceshipattendant.checkinData
import furhatos.app.spaceshipattendant.flow.CustomGaze
import furhatos.app.spaceshipattendant.flow.Interaction
import furhatos.app.spaceshipattendant.flow.gaze.DataDrivenGaze
import furhatos.app.spaceshipattendant.nlu.NumberOfGuests
import furhatos.app.spaceshipattendant.roomsLeft
import furhatos.flow.kotlin.*

fun NumberOfPeopleChange(rooms: Int): State = state(Interaction) {
    onEntry {
        parallel {
            goto(CustomGaze)
        }
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
