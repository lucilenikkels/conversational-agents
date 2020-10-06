package furhatos.app.spaceshipattendant.flow.states

import furhatos.app.spaceshipattendant.flow.Interaction
import furhatos.app.spaceshipattendant.flow.gaze.DataDrivenGaze
import furhatos.flow.kotlin.furhat
import furhatos.flow.kotlin.onResponse
import furhatos.flow.kotlin.state
import furhatos.nlu.common.No

fun SpecificWishes(name: String) = state(Interaction) {
    onEntry {
        parallel {
            goto(DataDrivenGaze)
        }
        furhat.say("Amazing!")
        furhat.say("The data has been entered to your name, $name.")
        furhat.ask("Now, before asking you about the different activities we offer on board, " +
                "I would like to ask you if you have any specific wishes for your stay here?")
    }

    onResponse<No> {
        goto(NoSpecificWishes)
    }

    onResponse {
        goto(WishesLoop)
    }
}