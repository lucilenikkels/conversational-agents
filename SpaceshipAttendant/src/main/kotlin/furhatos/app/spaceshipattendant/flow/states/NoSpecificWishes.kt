package furhatos.app.spaceshipattendant.flow.states

import furhatos.app.spaceshipattendant.flow.gaze.DataDrivenGaze
import furhatos.flow.kotlin.furhat
import furhatos.flow.kotlin.state

val NoSpecificWishes = state {
    onEntry {
        parallel {
            goto(DataDrivenGaze)
        }
        furhat.say("Alright, then let's move on.")
        goto(StarshipActivities)
    }
}