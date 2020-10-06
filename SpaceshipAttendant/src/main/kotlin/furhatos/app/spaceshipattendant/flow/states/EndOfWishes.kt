package furhatos.app.spaceshipattendant.flow.states

import furhatos.app.spaceshipattendant.flow.gaze.DataDrivenGaze
import furhatos.flow.kotlin.furhat
import furhatos.flow.kotlin.state

val EndOfWishes = state {
    onEntry {
        parallel {
            goto(DataDrivenGaze)
        }
        furhat.say("All right, your demands have been noted and will be read by the crew. " +
                "Let's move on then.");
        goto(StarshipActivities)
    }
}