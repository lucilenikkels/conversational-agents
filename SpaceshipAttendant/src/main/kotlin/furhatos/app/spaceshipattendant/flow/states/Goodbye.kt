package furhatos.app.spaceshipattendant.flow.states

import furhatos.app.spaceshipattendant.flow.Idle
import furhatos.flow.kotlin.furhat
import furhatos.flow.kotlin.state

val Goodbye = state {
    onEntry {
        furhat.say("Goodbye then.")
        goto(Idle)
    }
}