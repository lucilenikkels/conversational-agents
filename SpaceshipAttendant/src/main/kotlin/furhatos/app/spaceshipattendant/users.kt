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
)

val User.checkinData : CheckinData
    get() = data.getOrPut(CheckinData::class.qualifiedName, CheckinData())

var citizenRooms : Number? = 2
var suiteRooms : Number? = 5
