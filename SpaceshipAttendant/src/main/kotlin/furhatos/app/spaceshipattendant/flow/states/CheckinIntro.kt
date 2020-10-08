package furhatos.app.spaceshipattendant.flow.states

import furhatos.app.spaceshipattendant.flow.*
import furhatos.app.spaceshipattendant.flow.gaze.DataDrivenGaze
import furhatos.flow.kotlin.furhat
import furhatos.flow.kotlin.onResponse
import furhatos.flow.kotlin.state
import furhatos.nlu.common.No
import furhatos.nlu.common.Yes

val CheckinIntro = state {
    onEntry {
        parallel {
            goto(CustomGaze)
        }
        furhat.say("Great! As the travel is longer than two days on our journey to Vulkan, regulation requires\n" +
                "we ask a few questions.")
        furhat.ask("Is that okay with you?")
    }

    onResponse<No> {
        goto(UserDeclinesGivingInfo)
    }

    onResponse<Yes> {
        goto(HowManyGuests)
    }
}