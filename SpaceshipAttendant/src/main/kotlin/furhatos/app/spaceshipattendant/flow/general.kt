package furhatos.app.spaceshipattendant.flow

import furhatos.autobehavior.*
import furhatos.flow.kotlin.*
import furhatos.gestures.BasicParams
import furhatos.gestures.Gestures
import furhatos.util.*

val Idle: State = state {

    init {
        furhat.setVoice(Language.ENGLISH_US, Gender.MALE)

        furhat.setMicroexpression(
                defineMicroexpression {
                    // Adjust eye gaze randomly (between -180 and 180 degrees) with a random interval of 0-1000 ms.
                    repeat(0..1000) {
                        adjust(-180.0..180.0, BasicParams.GAZE_PAN)
                        adjust(-180.0..180.0, BasicParams.GAZE_TILT)
                    }
                    repeat(2000..8000, Gestures.Blink)
                })

        if (users.count > 0) {
            furhat.attend(users.random)
            goto(InitialState)
        }
    }

    onEntry {
        furhat.attendNobody()
    }

    onUserEnter {
        furhat.attend(it)
        goto(InitialState)
    }
}

val Interaction: State = state {

    onUserLeave(instant = true) {
        if (users.count > 0) {
            if (it == users.current) {
                furhat.attend(users.other)
                goto(InitialState)
            } else {
                furhat.glance(it)
            }
        } else {
            goto(Idle)
        }
    }

    onUserEnter(instant = true) {
        furhat.glance(it)
    }

}
