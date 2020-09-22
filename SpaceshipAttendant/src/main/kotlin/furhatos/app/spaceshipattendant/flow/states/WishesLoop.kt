package furhatos.app.spaceshipattendant.flow.states

import furhatos.flow.kotlin.furhat
import furhatos.flow.kotlin.onResponse
import furhatos.flow.kotlin.state
import furhatos.nlu.common.No

val WishesLoop = state {
    onEntry {
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