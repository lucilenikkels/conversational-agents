package furhatos.app.spaceshipattendant

import furhatos.app.spaceshipattendant.flow.*
import furhatos.skills.Skill
import furhatos.flow.kotlin.*

class SpaceshipattendantSkill : Skill() {
    override fun start() {
        Flow().run(Idle)
    }
}

fun main(args: Array<String>) {
    Skill.main(args)
}
