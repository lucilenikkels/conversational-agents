package furhatos.app.spaceshipattendant.flow.states

import furhatos.flow.kotlin.furhat
import furhatos.flow.kotlin.state

val EndState = state {
    onEntry {
        furhat.say("Understood. You have now successfully checked in. " +
                "You will soon be teleported to your room, and your luggage will be delivered by our staff. " +
                "We hope your stay at Starship Enterprise will be a fun and relaxing one..")
    }
}
