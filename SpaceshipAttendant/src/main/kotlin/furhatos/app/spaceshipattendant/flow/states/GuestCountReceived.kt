package furhatos.app.spaceshipattendant.flow.states

import furhatos.app.spaceshipattendant.checkinData
import furhatos.app.spaceshipattendant.flow.Interaction
import furhatos.flow.kotlin.State
import furhatos.flow.kotlin.furhat
import furhatos.flow.kotlin.state
import furhatos.flow.kotlin.users
import furhatos.nlu.common.Number


fun GuestCountReceived(guests: Number): State = state(Interaction) {
    onEntry {
        users.current.checkinData.guestNumber = Integer.parseInt(guests.toText())
        furhat.say("$guests guests, alright.")
        goto(RandomQuestion1)
    }
}


