package furhatos.app.spaceshipattendant.flow

import furhatos.app.spaceshipattendant.checkinData
import furhatos.app.spaceshipattendant.nlu.*
import furhatos.flow.kotlin.*
import furhatos.nlu.common.*
import furhatos.gestures.Gestures
import furhatos.gestures.defineGesture
import kotlinx.coroutines.delay

/*fun listening() = state {
    onEntry {
        furhat.gesture(Gestures.GazeAway(strength=1.0, duration=500.0))
    }
}

fun speaking() = state {
    onEntry {
        furhat.gesture(Gestures.GazeAway(strength=2.0, duration=1000.0))
    }
}*/

fun Listen(strength: Double = 100.0, iterations: Int = 1) =
        defineGesture("Listen") {
            for (i in 0 until iterations) {
                Gestures.GazeAway(strength=strength, duration=0.5)
                Gestures.Nod(strength=10.0, duration=1.5)
                reset()
            }
        }