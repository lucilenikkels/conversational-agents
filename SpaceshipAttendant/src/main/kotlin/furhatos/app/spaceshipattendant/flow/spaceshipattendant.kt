package furhatos.app.spaceshipattendant.flow

import furhatos.app.spaceshipattendant.checkinData
import furhatos.app.spaceshipattendant.nlu.*
import furhatos.flow.kotlin.*
import furhatos.nlu.common.*
import java.awt.Robot

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

val CheckinCancel = state {
    onEntry {
        furhat.say("Alright then, please tell me if you'd like to start over. Otherwise, I wish you a good day.")
        goto(Idle)
    }
}

val RobotIntro : State = state {
    onEntry {
        furhat.say("Welcome to Starship Enterprise. We are currently leaving for a 12-day voyage from\n" +
                "planet Earth to planet Vulkan. My name is Data and I am your check-in assistant for today")
        delay(500)
        furhat.ask("Would you like to check in?")
    }

    onResponse<No> {
        goto(Goodbye)
    }

    onResponse<CheckIn> {
        goto(CheckinIntro)
    }

    onResponse<Yes> {
        goto(CheckinIntro)
    }
}

val Goodbye = state {
    onEntry {
        furhat.say("Goodbye then.")
        goto(Idle)
    }
}

val UserDeclines = state {
    onEntry {
        furhat.say("Without your information I cannot book you in.")
        furhat.ask("Are you sure?")
    }

    onResponse<No> {
        goto(HowManyGuests)
    }

    onResponse<Yes> {
        goto(Goodbye)
    }
}

val CheckinIntro = state {
    onEntry {
        furhat.say("Great! As the travel is longer than two days on our journey to Vulkan, regulation requires\n" +
                "we ask a few questions.")
        furhat.ask("Is that okay with you?")
    }

    onResponse<No> {
        goto(UserDeclines)
    }

    onResponse<Yes> {
        goto(HowManyGuests)
    }
}

val HowManyGuests = state {
    onEntry {
        furhat.say("Let's get started then.")
        delay(500)
        furhat.ask("How many people would you like to checkin?")
    }

    onResponse<NumberOfGuests> {
        val guests = it.intent.gs
        if (guests != null) {
            goto(GuestsHeared(guests))
        }
        else {
            propagate()
        }
    }
}

val Amenities = state{
    onEntry {
       furhat.say("You are provided a bed, a table, a chair, and a Replicator, which allows you to instantly create any dish you've ever wanted to eat, in the comfort of your room.")
        goto(FurtherDetails)
    }
}

val FurtherDetails = state{
    onEntry {
        furhat.say("Perfect. Now, could you give me your name, how long you intend to stay on Starship Enterprise, and whether you would like to stay in our Suite-class rooms or the Citizen-class rooms?")
    }
}

fun GuestsHeared(guests: Guests) : State = state(Interaction) {
    onEntry {
        users.current.checkinData.guestNumber = guests
        furhat.say("$guests guests, alright.")
        delay(200)
        furhat.ask("By the way, would you like to know about the available amenities in our rooms?")
        }
    onResponse<Yes> {
        goto(Amenities)
    }

    onResponse<No> {
        goto(FurtherDetails)
    }
}