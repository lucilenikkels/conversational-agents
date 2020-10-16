package furhatos.app.spaceshipattendant.flow

import furhatos.app.spaceshipattendant.arousal
import furhatos.app.spaceshipattendant.checkinData
import furhatos.app.spaceshipattendant.nlu.*
import furhatos.app.spaceshipattendant.valence
import furhatos.flow.kotlin.*
import furhatos.nlu.common.*
import furhatos.records.Location


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
            Location.DOWN, Location.UP, Location.RIGHT, Location.LEFT,
            Location.DOWN_LEFT, Location.DOWN_RIGHT, Location.UP_LEFT, Location.UP_RIGHT)
    return glances.shuffled().take(1)[0]
}

fun sampleGaussian(mean : Double, std : Double) : Int {
    val r = java.util.Random()
    val x = ((r.nextGaussian() * std + mean) * 1000).toInt()
//    println("$x sampled")
    return x
}

fun sendGetRequest() : State = state {
    onEntry {
        val response = khttp.get("http://127.0.0.1:5000/get_emotion").text
        val values = response.split(' ')
        users.current.valence = values[0].toDouble()
        users.current.arousal = values[1].toDouble()
        println("Valence: ${values[0].toDouble()}, Arousal: ${values[1].toDouble()}")
    }
}
