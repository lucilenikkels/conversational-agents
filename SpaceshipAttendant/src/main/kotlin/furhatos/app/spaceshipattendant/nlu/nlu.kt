package furhatos.app.spaceshipattendant.nlu

import furhatos.nlu.*
import furhatos.nlu.common.Number
import furhatos.util.Language

class Guests: Number()

class CheckIn : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf("I would like to check in", "I want to check in", "Check-in, please", "Yes, I would like to check in")
    }
}

class Help : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf("Who are you?", "Where am I?", "What is this", "What")
    }
}

class NumberOfGuests(var gs : Guests? = null) : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf("@gs", "@gs people", "@gs persons")
    }
}

class Type : EnumEntity(speechRecPhrases = true) {
    override fun getEnum(lang: Language): List<String> {
        return listOf("suite", "citizen")
    }
}

class Duration(
        var count : Number? = Number(1),
        var unit : String? = null) : EnumEntity() {
    override fun getEnum(lang: Language): List<String> {
        return listOf("@count @unit", "@unit")
    }

    override fun toText(): String {
        return "$count $unit"
    }
}

open class Details(
        var name : String? = null,
        var duration : Duration? = null,
        var type : Type? = null
) : Intent() {

    override fun getExamples(lang: Language): List<String> {
        return listOf("My name is @name and I'm planning on staying @duration in the @type room")
    }
}