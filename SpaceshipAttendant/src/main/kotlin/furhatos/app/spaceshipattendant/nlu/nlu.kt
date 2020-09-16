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
