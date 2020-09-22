package furhatos.app.spaceshipattendant.flow.states

import furhatos.flow.kotlin.furhat
import furhatos.flow.kotlin.state

val NoSpecificWishes = state {
    onEntry {
        furhat.say("Alright, then let's move on.")
        goto(StarshipActivities)
    }
}