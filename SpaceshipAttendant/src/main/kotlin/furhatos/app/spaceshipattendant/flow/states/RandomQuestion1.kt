package furhatos.app.spaceshipattendant.flow.states

import furhatos.flow.kotlin.furhat
import furhatos.flow.kotlin.onResponse
import furhatos.flow.kotlin.state
import furhatos.nlu.common.No
import furhatos.nlu.common.Yes

val RandomQuestion1 = state {
    onEntry {
        furhat.say("Great!")
        furhat.ask("By the way, would you like to know about the available amenities in our rooms?")

    }

    onResponse<Yes> {
        furhat.say("You are provided a bed, a table, a chair, and a Replicator, " +
                "which allows you to instantly create any dish you've ever wanted to eat, in the comfort of your own room.")
        goto(FurtherDetails)
    }

    onResponse<No> {
        goto(FurtherDetails)
    }

    onResponse { goto(FurtherDetails) }
}


