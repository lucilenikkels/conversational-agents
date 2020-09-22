package furhatos.app.spaceshipattendant.flow.states

import furhatos.app.spaceshipattendant.nlu.NumberOfGuests
import furhatos.flow.kotlin.furhat
import furhatos.flow.kotlin.onResponse
import furhatos.flow.kotlin.state


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