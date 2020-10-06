package furhatos.app.spaceshipattendant.flow.gaze

import furhatos.flow.kotlin.furhat
import furhatos.flow.kotlin.state
import furhatos.flow.kotlin.users
import kotlin.math.abs

const val gGazeMean = 8.1;
const val gGazeStdev = 14.26;
const val xGazeMean = 1.37;
const val xGazeStdev = 1.19;
const val userGazeProb = 0.654;


val DataDrivenGaze = state {
    val r = java.util.Random();
    onTime(repeat = 1000..2500) {
        if (Math.random() < userGazeProb) {
            val duration = (((abs(r.nextGaussian())) * gGazeStdev + gGazeMean ) * 1000).toInt();
            println("User gaze $duration miliseconds")
            furhat.glance(users.random, duration = duration);
        } else {
            val duration = (((abs(r.nextGaussian())) * xGazeStdev + xGazeMean ) * 1000).toInt();
            println("Random gaze $duration miliseconds")
            furhat.glance(randomLocation(), duration = duration);
        }

    }
}
