package furhatos.app.spaceshipattendant.flow.states

import furhatos.flow.kotlin.furhat
import furhatos.flow.kotlin.state

val EndOfWishes = state {
    onEntry {
        furhat.say("All right, your demands have been noted and will be read by the crew. " +
                "Let's move on then.");
        goto(StarshipActivities)
    }
}