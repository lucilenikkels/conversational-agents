package furhatos.app.spaceshipattendant

import furhatos.app.spaceshipattendant.nlu.*
import furhatos.nlu.common.PersonName
import furhatos.records.User

class CheckinData (
        var guestNumber : Guests = Guests(),
        var name : PersonName? = PersonName(),
        var duration: Duration? = Duration(),
        var timeUnit: TimeUnit? = TimeUnit(),
        var type: Type? = null,
        var wishes : WishList = WishList(),
        var activities : ActivityList = ActivityList()
) {
    override fun toString(): String {
        return "$guestNumber persons under the name $name for $duration $timeUnit in the $type room. " +
                "As specific whishes you have: $wishes. You signed up for $activities"
    }
}


val User.checkinData : CheckinData
    get() = data.getOrPut(CheckinData::class.qualifiedName, CheckinData())
