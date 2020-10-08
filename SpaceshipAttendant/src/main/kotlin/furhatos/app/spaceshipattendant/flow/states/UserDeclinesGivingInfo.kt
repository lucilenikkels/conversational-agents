package furhatos.app.spaceshipattendant.flow.states

import furhatos.app.spaceshipattendant.flow.CustomGaze
import furhatos.app.spaceshipattendant.flow.gaze.DataDrivenGaze
import furhatos.flow.kotlin.furhat
import furhatos.flow.kotlin.onResponse
import furhatos.flow.kotlin.state
import furhatos.nlu.common.No
import furhatos.nlu.common.Yes

val UserDeclinesGivingInfo = state {
    onEntry {
        parallel {
            goto(CustomGaze)
        }
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
