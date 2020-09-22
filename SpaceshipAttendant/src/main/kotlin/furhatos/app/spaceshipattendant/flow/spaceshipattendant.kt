package furhatos.app.spaceshipattendant.flow

import furhatos.app.spaceshipattendant.checkinData
import furhatos.app.spaceshipattendant.flow.states.*
import furhatos.app.spaceshipattendant.nlu.*
import furhatos.app.spaceshipattendant.roomsLeft
import furhatos.flow.kotlin.*
import furhatos.gestures.Gestures
import furhatos.nlu.common.*
import furhatos.nlu.common.Number
import kotlinx.coroutines.yield

val Start = state(Interaction) {
    onEntry {
        furhat.ask("Hello, how can I help you?")
    }

    onResponse<CheckIn> {
        goto(CheckinIntro)
    }

    onResponse<Help> {
        goto(RobotIntro)
    }
}



























