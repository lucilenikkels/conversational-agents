package furhatos.app.spaceshipattendant

import furhatos.app.spaceshipattendant.nlu.DurationEntity
import furhatos.app.spaceshipattendant.nlu.RoomType
import furhatos.nlu.common.Number
import furhatos.nlu.common.PersonName
import furhatos.records.User


class CheckinData(
        var guestNumber: Number? = Number(1),
        var name: PersonName? = null,
        var duration: DurationEntity? = null,
        var type: RoomType? = null
)

val User.checkinData : CheckinData
    get() = data.getOrPut(CheckinData::class.qualifiedName, CheckinData())
