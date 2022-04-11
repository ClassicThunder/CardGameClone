package classicthunder.actor

import classicthunder.combat.character.CharacterActor

internal class WiggleEffect(a: CharacterActor, i: Int) : ActorEffect(a, i) {

    override fun update(progress: Float) {

        val p = progress * 2

        if (p <= 1f) {
            characterActor.offsetX = -25 * p
        } else {
            characterActor.offsetX = -25 * (2 - p)
        }
    }
}