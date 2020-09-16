package furhatos.app.spaceshipattendant

import furhatos.app.spaceshipattendant.nlu.Guests
import furhatos.records.User


class CheckinData (
        var guestNumber : Guests = Guests()
)

val User.checkinData : CheckinData
    get() = data.getOrPut(CheckinData::class.qualifiedName, CheckinData())
