package furhatos.app.spaceshipattendant.flow

import furhatos.autobehavior.*
import furhatos.flow.kotlin.*
import furhatos.gestures.BasicParams
import furhatos.gestures.Gestures
import furhatos.util.*
import kotlin.random.Random

val Idle: State = state {

    init {
        furhat.setVoice(Language.ENGLISH_US, Gender.MALE)
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

    onTime(repeat=2000..4500) {
        if (furhat.isSpeaking) {
            furhat.glance(randomLocation(), duration= Random.nextInt(1000,2000))
        } else {
            furhat.glance(randomLocation(), duration= Random.nextInt(500,1000))
        }
    }

}
