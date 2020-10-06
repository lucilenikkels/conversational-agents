package furhatos.app.spaceshipattendant.flow.states

import furhatos.app.spaceshipattendant.flow.Idle
import furhatos.app.spaceshipattendant.flow.gaze.DataDrivenGaze
import furhatos.flow.kotlin.furhat
import furhatos.flow.kotlin.state

val CheckinCancel = state {
    onEntry {
        parallel {
            goto(DataDrivenGaze)
        }
        furhat.say("Alright then, please tell me if you'd like to start over. Otherwise, I wish you a good day.")
        goto(Idle)
    }
}