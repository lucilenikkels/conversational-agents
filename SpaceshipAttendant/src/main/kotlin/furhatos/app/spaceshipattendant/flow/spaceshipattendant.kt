package furhatos.app.spaceshipattendant.flow

import furhatos.app.spaceshipattendant.flow.gaze.RandomGaze
import furhatos.app.spaceshipattendant.checkinData
import furhatos.app.spaceshipattendant.flow.gaze.DataDrivenGaze
import furhatos.app.spaceshipattendant.flow.states.*
import furhatos.app.spaceshipattendant.nlu.*
import furhatos.app.spaceshipattendant.roomsLeft
import furhatos.flow.kotlin.*
import furhatos.gestures.Gestures
import furhatos.nlu.common.*
import furhatos.nlu.common.Number
import kotlinx.coroutines.yield
import kotlin.random.Random
import furhatos.records.Location




val Start = state(Interaction) {

//    onTime(repeat=100..8000) {
//        val duration = Random.nextInt(100, 3000)
//        println("Random gaze for $duration milliseconds")
//        furhat.glance(randomLocation(), duration=duration)
//    }

    onEntry {
        parallel {
            goto(DataDrivenGaze)
        }
        furhat.ask("Hello, how can I help you?")
    }

    onResponse<CheckIn> {
        goto(CheckinIntro)
    }

    onResponse<Help> {
        goto(RobotIntro)
    }
}



























