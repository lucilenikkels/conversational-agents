package furhatos.app.spaceshipattendant.flow

import furhatos.app.spaceshipattendant.checkinData
import furhatos.app.spaceshipattendant.nlu.*
import furhatos.flow.kotlin.*
import furhatos.nlu.common.*

var citizenRooms = 5
var suiteRooms = 2

// Constants retrieved from gaze file
const val randomGazeMean = 1.3726315789473684
const val randomGazeStd = 1.1903747402228744
const val userGazeMean = 8.095884210526314
const val userGazeStd = 14.26086952089024

val gazeInterval = 100..6000

val InitialState = state(Interaction) {

    onTime(repeat=gazeInterval, instant = true) {
        call(glanceState)
    }

    onEntry {
        furhat.say("Hello, how can I help you?")
        furhat.glance(users.current)
        furhat.listen()
    }

    onReentry {
        furhat.ask("")
    }

    onResponse<CheckIn> {
        goto(CheckinIntro)
    }

    onResponse<Help> {
        goto(RobotIntro)
    }
}

val RobotIntro : State = state {

    onTime(repeat=gazeInterval, instant = true) {
        call(glanceState)
    }

    onEntry {
        furhat.say("Welcome to Starship Enterprise. We are currently leaving for a 12-day voyage from\n" +
                "planet Earth to planet Vulkan. My name is Data and I am your check-in assistant for today.")
        furhat.say("Would you like to check in?")
        furhat.glance(users.current)
        furhat.listen()
    }

    onResponse<No> {
        goto(UserDeclines)
    }

    onResponse<CheckIn> {
        goto(CheckinIntro)
    }

    onResponse<Yes> {
        goto(CheckinIntro)
    }
}

val CheckinIntro = state {

    onTime(repeat=gazeInterval, instant = true) {
        call(glanceState)
    }

    onEntry {
        furhat.say("Great! As the travel is longer than two days on our journey to Vulkan, regulation requires\n" +
                "we ask a few questions.")
        furhat.say("Is that okay with you?")
        furhat.glance(users.current)
        furhat.listen()
    }

    onResponse<No> {
        goto(UserDeclines)
    }

    onResponse<Yes> {
        goto(HowManyGuests)
    }
}

val UserDeclines = state {

    onTime(repeat=gazeInterval, instant = true) {
        call(glanceState)
    }

    onEntry {
        furhat.say("Without your information I cannot book you in.")
        furhat.say("Are you sure?")
        furhat.glance(users.current)
        furhat.listen()
    }

    onResponse<No> {
        goto(HowManyGuests)
    }

    onResponse<Yes> {
        furhat.say("Goodbye then.")
        goto(Idle)
    }
}

val HowManyGuests = state {

    onTime(repeat=gazeInterval, instant = true) {
        call(glanceState)
    }

    onEntry {
        furhat.say("Let's get started then.")
        furhat.say("How many people would you like to checkin?")
        furhat.glance(users.current)
        furhat.listen()
    }

    onReentry {
        furhat.ask("How many people would you like to checkin?")
    }

    onResponse<NumberOfGuests> {
        val guests = it.intent.gs
        if (guests != null) {
            goto(guestsHeared(guests))
        }
        else {
            propagate()
        }
    }
}

val RandomQuestion : State = state {

    onTime(repeat=gazeInterval, instant = true) {
        call(glanceState)
    }

    onEntry {
        furhat.say(" By the way, would you like to know about the available amenities in our rooms?")
        furhat.glance(users.current)
        furhat.listen()
    }

    onResponse<Yes> {
        furhat.say(" You are provided a bed, a table, a chair, and a Replicator, which allows you to instantly\n" +
                "create any dish you've ever wanted to eat, in the comfort of your own room.\n")
        goto(FurtherDetails)
    }

    onResponse<No> {
        goto(FurtherDetails)
    }

    onNoResponse {
        goto(FurtherDetails)
    }
}

val FurtherDetails : State = state {

    onTime(repeat=gazeInterval, instant = true) {
        call(glanceState)
    }

    onEntry {
        furhat.say(" Perfect. Now, could you give me your name, how long you intend to stay on Starship\n" +
                "Enterprise, and whether you would like to stay in our Suite-class rooms or the Citizen-class rooms?\n"+
                "Suite class have 2 beds, citizen-class have 1 bed.")
        furhat.glance(users.current)
        furhat.listen()
    }

    onReentry {
        furhat.ask("Could you repeat that?")
    }

    onResponse<Details> {
        val name = it.intent.name
        val duration = it.intent.duration
        val timeUnit = it.intent.timeUnit
        val type = it.intent.type
        if (duration != null && timeUnit != null && type != null && name != null) {
            goto(detailsReceived(name,duration,timeUnit,type))
        }
    }
}

val StarshipOverload : State = state {

    onTime(repeat=gazeInterval, instant = true) {
        call(glanceState)
    }
    onEntry {
        if (users.current.checkinData.type?.value === "citizen") {
            furhat.say("Unfortunately there are no rooms left of this kind. We only have ${citizenRooms} rooms of this kind\n" +
                    "free. Would you like to change the number of people you are checking in?")
        } else if (users.current.checkinData.type?.value === "suite") {
            furhat.say("Unfortunately there are no rooms left of this kind. We only have ${suiteRooms} rooms of this kind\n" +
                    "free. Would you like to change the number of people you are checking in?")
        }
        furhat.glance(users.current)
        furhat.listen()
    }

    onResponse<No> {
        goto(CheckinCancel)
    }

    onNoResponse {
        goto(CheckinCancel)
    }

    onResponse<Yes> {
        goto(ChangeNumber)
    }
}

val CheckinCancel = state {

    onTime(repeat=gazeInterval, instant = true) {
        call(glanceState)
    }

    onEntry {
        furhat.say("Alright then, please tell me if you'd like to start over. Otherwise, I wish you a good day.")
    }

    onResponse<StartOver> {
        goto(InitialState)
    }

    onNoResponse {
        goto(Idle)
    }
}

val ChangeNumber : State = state {

    onTime(repeat=gazeInterval, instant = true) {
        call(glanceState)
    }
    onEntry {
        furhat.say("Wonderful. Please tell me how many guests you would like to check in.")
        furhat.glance(users.current)
        furhat.listen()
    }

    onReentry {
        furhat.ask("How many guests do you want to check in?")
    }

    onResponse<NumberOfGuests> {
        val guests = it.intent.gs
        if (guests != null) {
            if ((users.current.checkinData.type?.value === "citizen" && guests.value!! <=  citizenRooms) ||
                    (users.current.checkinData.type?.value === "suite" && guests.value!! <= suiteRooms*2)) {
                users.current.checkinData.guestNumber = guests
                goto(SpecificWishes)
            } else {
                goto(StarshipOverload)
            }
        } else {
            propagate()
        }
    }
}

val SpecificWishes : State = state {

    onTime(repeat=gazeInterval, instant = true) {
        call(glanceState)
    }

    onEntry {
        furhat.say("Amazing. The data has been entered to your name, ${users.current.checkinData.name}. Now," +
                "before asking you about the different activities we offer on board, I would like to ask you if you" +
                "have any specific wishes for your stay here?")
        furhat.glance(users.current)
        furhat.listen()
        if (users.current.checkinData.type?.value === "citizen") {
            citizenRooms = citizenRooms - users.current.checkinData.guestNumber.value!!
        } else if (users.current.checkinData.type?.value === "suite") {
            suiteRooms = suiteRooms - ((users.current.checkinData.guestNumber.value!! + users.current.checkinData.guestNumber.value!! % 2) / 2)
        }
    }

    onReentry {
        furhat.ask("Do you have any specific wishes for your stay here?")
    }

    onResponse<No> {
        furhat.say(" Alright, then let's move on.")
        goto(StarshipActivities)
    }

    onResponse<NewWish> {
        val wish = it.intent.wish
        if (wish != null) {
            goto(newWish(wish))
        }
        else {
            propagate()
        }
    }
}

val WishesLoop : State = state {

    onTime(repeat=gazeInterval, instant = true) {
        call(glanceState)
    }

    onEntry {
        random(
                { furhat.say("Understood. Anything else?")},
                { furhat.say("Sure. Do you need something else?")},
                { furhat.say("Noted. Do you have more wishes?")}
        )
        furhat.glance(users.current)
        furhat.listen()
    }

    onResponse<Yes> {
        random(
                { furhat.ask("What kind of wishes?") },
                { furhat.ask("What is it that you want?") }
        )
    }

    onResponse<No> {
        goto(EndWishes)
    }

    onResponse<NewWish> {
        val wish = it.intent.wish
        if (wish != null) {
            goto(newWish(wish))
        }
        else {
            propagate()
        }
    }
}

val EndWishes : State = state {

    onTime(repeat=gazeInterval, instant = true) {
        call(glanceState)
    }

    onEntry {
        furhat.say("All right, your demands have been noted and will be read by the crew. Let's move on then.\n")
        goto(StarshipActivities)
    }
}

val StarshipActivities : State = state {

    onTime(repeat=gazeInterval, instant = true) {
        call(glanceState)
    }

    onEntry {
        furhat.say("On Starship Enterprise we offer numerous simulated activities, namely: ${Activity().optionsToText()}.")
        furhat.say("Please tell me which ones of those activities you would like to sign up for today.")
        furhat.glance(users.current)
        furhat.listen()
    }

    onReentry {
        furhat.ask("Please tell me which activity you would like to sign up for today.")
    }

    onResponse<No> {
        goto(EndState)
    }

    onResponse<NewActivity> {
        val acts = it.intent.activs
        if (acts != null) {
            goto(activitiesReceived(acts))
        }
        else {
            propagate()
        }
    }
}

val EndState : State = state {

    onTime(repeat=gazeInterval, instant = true) {
        call(glanceState)
    }

    onEntry {
        furhat.say("To summarize, you checked in " + users.current.checkinData.toString())
        furhat.say("You have now successfully checked in. You will soon be teleported to your\n" +
                "room, and your luggage will be delivered by our staff. We hope your stay at Starship\n" +
                "Enterprise will be a fun and relaxing one.")
        println(users.current.checkinData.toString())
        goto(Idle)
    }
}

val glanceState : State = state {
    onEntry {
        val duration = sampleGaussian(randomGazeMean, randomGazeStd)
        println("Random gaze for $duration milliseconds")
        furhat.glance(randomLocation(), duration=duration)
        terminate()
    }
}
