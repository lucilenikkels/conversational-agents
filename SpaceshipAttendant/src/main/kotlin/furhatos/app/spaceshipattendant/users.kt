package furhatos.app.spaceshipattendant

import furhatos.app.spaceshipattendant.nlu.Guests
import furhatos.nlu.common.Number
import furhatos.records.User


class CheckinData (
        var guestNumber : Int = 1,
        var lastWantedRoom : String = "",
        var name: String = ""
)

val User.checkinData : CheckinData
    get() = data.getOrPut(CheckinData::class.qualifiedName, CheckinData())

class RoomData (
        var citizenRoomsLeft : Int = 5,
        var suiteRoomsLeft : Int = 5
)

val User.roomsLeft : RoomData
    get() = data.getOrPut(RoomData::class.qualifiedName, RoomData())
