package furhatos.app.spaceshipattendant.flow.states

import furhatos.app.spaceshipattendant.flow.Idle
import furhatos.app.spaceshipattendant.flow.gaze.DataDrivenGaze
import furhatos.flow.kotlin.furhat
import furhatos.flow.kotlin.state

val Goodbye = state {
    onEntry {
        parallel {
            goto(DataDrivenGaze)
        }
        furhat.say("Goodbye then.")
        goto(Idle)
    }
}