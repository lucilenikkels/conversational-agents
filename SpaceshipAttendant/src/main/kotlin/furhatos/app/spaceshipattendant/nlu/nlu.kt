package furhatos.app.spaceshipattendant.nlu

import furhatos.nlu.*
import furhatos.nlu.common.Number
import furhatos.util.Language

class Guests : Number()
class Duration : Number()

class CheckIn : Intent() //{
//    override fun getExamples(lang: Language): List<String> {
//        return listOf("I would like to check in",
//                "I want to check in", "Check-in, please",
//                "Yes, I would like to check in", "Check in", "Check")
//    }
//}

class Suite : EnumEntity() //{
//    override fun getEnum(lang: Language): List<String> {
//        return listOf("suite", "citizen")
//    }
//}

class Help : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf("Who are you?", "Where am I?", "What is this?", "What?")
    }
}

class NumberOfGuests(var gs : Number? = null) : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf("@gs", "@gs people", "@gs persons")
    }
}


class FurtherDetailsResponse(var name: furhatos.nlu.common.PersonName? = null,
                             var duration: Duration? = null,
                             var room: Suite? = null
) : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf("@name, @duration, @room",
                "Name: @name, duration: @duration, room: @room",
                "My name is @name, I want to stay @duration days in a @room room")
    }
}

