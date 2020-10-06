package furhatos.app.spaceshipattendant.flow.states

import furhatos.app.spaceshipattendant.flow.gaze.DataDrivenGaze
import furhatos.flow.kotlin.furhat
import furhatos.flow.kotlin.onResponse
import furhatos.flow.kotlin.state
import furhatos.nlu.common.No

val WishesLoop = state {
    onEntry {
        parallel {
            goto(DataDrivenGaze)
        }
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