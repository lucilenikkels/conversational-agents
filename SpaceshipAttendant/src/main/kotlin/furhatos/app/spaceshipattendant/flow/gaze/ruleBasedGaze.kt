package furhatos.app.spaceshipattendant.flow.gaze

import furhatos.flow.kotlin.furhat
import furhatos.flow.kotlin.state
import furhatos.records.Location

import kotlin.random.Random

// Use a different random location function than random gaze because gazing up looks weird most of the time.
fun randomDownLocation(): Location {
    val glances = listOf<Location>(
            Location.DOWN_LEFT, Location.DOWN_RIGHT,
            Location.DOWN, Location.RIGHT, Location.LEFT)
    return glances.shuffled().take(1)[0]
}

val RuleBasedGaze = state {
    onTime(repeat=2000..4000) {
        if (furhat.isSpeaking) {
            // Glance at a random location for 25% to 100% of an iteration (avg 50%)
            furhat.glance(randomDownLocation(), duration= Random.nextInt(1000,2000))
        } else {
            // Glance at a random location for 12.5% to 50% of an iteration (avg 25%)
            furhat.glance(randomDownLocation(), duration= Random.nextInt(500,1000))
        }
        // Glance at the user for the rest of the iteration
    }
}
