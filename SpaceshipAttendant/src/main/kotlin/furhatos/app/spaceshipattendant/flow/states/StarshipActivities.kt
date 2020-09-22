package furhatos.app.spaceshipattendant.flow.states

import furhatos.flow.kotlin.furhat
import furhatos.flow.kotlin.onResponse
import furhatos.flow.kotlin.state
import furhatos.nlu.common.No

val StarshipActivities = state {
    onEntry {
        furhat.ask("On Starship Enterprise we offer numerous simulated activities" +
                "namely: Skiing, Tennis, Badminton, and Zombie Survival. " +
                "Please tell me which ones of those activities you would like to sign up for today.")
    }

    onResponse<No> {
        goto(EndState)
    }

    onResponse {
        goto(EndState)
    }
}