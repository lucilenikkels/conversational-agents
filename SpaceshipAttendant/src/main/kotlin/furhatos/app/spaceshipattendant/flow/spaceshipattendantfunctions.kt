package furhatos.app.spaceshipattendant.flow

import furhatos.app.spaceshipattendant.checkinData
import furhatos.app.spaceshipattendant.nlu.*
import furhatos.flow.kotlin.*
import furhatos.nlu.common.*
import furhatos.records.Location
import kotlin.random.Random


fun guestsHeared(guests: Guests) : State = state {
    onEntry {
        users.current.checkinData.guestNumber = guests
        furhat.say("Great.")
        goto(RandomQuestion)
    }
}

fun detailsReceived(name: PersonName?, duration: Duration?, timeUnit: TimeUnit?, type: Type?) : State = state {
    onEntry {
        users.current.checkinData.name = name
        users.current.checkinData.duration = duration
        users.current.checkinData.timeUnit = timeUnit
        users.current.checkinData.type = type
        if ((type?.value === "citizen" && users.current.checkinData.guestNumber.value!! > citizenRooms) ||
                (type?.value === "suite" && users.current.checkinData.guestNumber.value!! >  suiteRooms * 2)) {
            goto(StarshipOverload)
        } else {
            goto(SpecificWishes)
        }
    }
}

fun newWish(wish : Wish) : State = state {
    onEntry {
        users.current.checkinData.wishes.list.add(wish)
        goto(WishesLoop)
    }
}

fun activitiesReceived(activs : ActivityList) : State = state {
    onEntry{
        activs.list.forEach {
            users.current.checkinData.activities.list.add(it)
        }
        goto(EndState)
    }
}

fun randomLocation() : Location {
    val glances = listOf<Location>(
            Location.DOWN, Location.RIGHT, Location.LEFT,
            Location.DOWN_LEFT, Location.DOWN_RIGHT)
    return glances.shuffled().take(1)[0]
}

fun longUtterances() : State = state {

}

fun shortUtterances() : State = state {

}

fun listen() : State = state {
    //furhat.glance(randomLocation(), duration= Random.nextInt(500,1000))
}