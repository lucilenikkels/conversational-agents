package furhatos.app.spaceshipattendant.nlu

import furhatos.nlu.*
import furhatos.nlu.common.Number
import furhatos.nlu.common.PersonName
import furhatos.util.Language

class Guests: Number()
class Duration: Number()
class WishList: ListEntity<Wish>()
class ActivityList : ListEntity<Activity>()

class StartOver: Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf("I would like to check in", "I want to check in", "Check-in, please", "Yes, I would like to check in",
        "wait", "I want to start over", "Start over please", "I want to try again")
    }
}

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
        return listOf("@gs", "@gs people", "@gs persons", "@gs person")
    }
}

class Type : EnumEntity(speechRecPhrases = true) {
    override fun getEnum(lang: Language): List<String> {
        return listOf("suite", "citizen")
    }
}

class TimeUnit : EnumEntity(speechRecPhrases = true) {
    override fun getEnum(lang: Language): List<String> {
        return listOf("day", "week", "year", "days", "weeks", "years")
    }
}

open class Details(
        var name: PersonName? = null,
        var duration : Duration? = null,
        var timeUnit: TimeUnit? = null,
        var type : Type? = null
) : Intent() {

    override fun getExamples(lang: Language): List<String> {
        return listOf("My name is @name and I'm planning on staying @duration @timeUnit in the @type room")
    }
}

class Wish : EnumEntity(speechRecPhrases = true) {
    override fun getEnum(lang: Language): List<String> {
        return listOf("extra towels", "room service")
    }
}

class NewWish(var wish : Wish? = null) : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf("I want @wish", "I would like @wish", "Please give me @wish", "Yes I want @wish",
                "Yes I would like @wish", "Yes please give me @wish")
    }
}

class Activity : EnumEntity(stemming = true, speechRecPhrases = true) {
    override fun getEnum(lang: Language): List<String> {
        return listOf("Ski", "Tennis", "Badminton", "Zombie Survival")
    }
}

class NewActivity(var activs : ActivityList? = null) : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf("@activs", "I want @activs", "I would like @activs", "I want to buy @activs")
    }
}
