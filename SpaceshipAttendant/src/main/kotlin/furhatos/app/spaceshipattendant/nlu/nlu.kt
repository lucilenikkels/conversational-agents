package furhatos.app.spaceshipattendant.nlu

import furhatos.nlu.EnumEntity
import furhatos.nlu.Intent
import furhatos.nlu.WildcardEntity
import furhatos.nlu.common.Number
import furhatos.nlu.common.PersonName
import furhatos.util.Language


class CheckIn : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf("I would like to check in", "I want to check in", "Check-in, please", "Yes, I would like to check in")
    }
}

class Help : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf("Who are you?", "Where am I?", "What is this", "What?", "Help")
    }
}

class NumberOfGuests(var gs : Number? = Number(1)) : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf("@gs", "@gs people", "@gs persons")
    }
}

class RoomType : EnumEntity(speechRecPhrases = true) {
    override fun getEnum(lang: Language): List<String> {
        return listOf("suite", "citizen room")
    }

    override fun getSpeechRecPhrases(lang: Language): List<String> {
        return listOf("suite")
    }
}

class DurationEntity: WildcardEntity("duration", Details())


class Details : Intent() {

    var name : PersonName? = null
    var duration : DurationEntity? = null
    var type : RoomType? = null

    override fun getExamples(lang: Language): List<String> {
        return listOf("My name is @name and I'm planning on staying for @duration in the @type.",
                "I am @name and I will stay for @duration in the @type",
                "My name is @name and I will be here for @duration in the @type")
    }
}
