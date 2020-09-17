package furhatos.app.spaceshipattendant

import furhatos.app.spaceshipattendant.nlu.*
import furhatos.records.User


class CheckinData (
        var guestNumber : Guests = Guests(),
        var name : String? = "",
        var duration: Duration? = Duration(),
        var type: Type? = null
)

val User.checkinData : CheckinData
    get() = data.getOrPut(CheckinData::class.qualifiedName, CheckinData())
