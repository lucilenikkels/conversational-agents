package furhatos.app.spaceshipattendant.flow.gaze

import furhatos.flow.kotlin.*
import furhatos.records.Location
import kotlin.random.Random

fun randomLocation(): Location {
    val glances = listOf<Location>(
            Location.DOWN_LEFT, Location.DOWN_RIGHT,
            Location.DOWN, Location.UP, Location.RIGHT, Location.LEFT,
            Location.UP_LEFT, Location.UP_RIGHT)
    return glances.shuffled().take(1)[0]
}

val RandomGaze = state {
    onTime(repeat = 400..4000) {
        furhat.glance(randomLocation(), duration = Random.nextInt(10, 40) * 100)
    }
}

