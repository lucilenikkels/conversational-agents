package furhatos.app.spaceshipattendant.flow.states

import furhatos.app.spaceshipattendant.flow.Interaction
import furhatos.flow.kotlin.State
import furhatos.flow.kotlin.furhat
import furhatos.flow.kotlin.onResponse
import furhatos.flow.kotlin.state
import furhatos.nlu.common.No
import furhatos.nlu.common.Yes


fun StarshipOverloaded(rooms: Int): State = state(Interaction) {
    onEntry {
        furhat.say("Unfortunately there are no rooms left of this kind. We only have $rooms rooms of this kind free. ");
        furhat.ask("Would you like to change the number of people you are checking in?");
    }

    onResponse<No> {
        goto(CheckinCancel)
    }

    onResponse<Yes> {
        goto(NumberOfPeopleChange(rooms))
    }
}