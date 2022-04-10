package classicthunder.actor

import classicthunder.combat.character.CharacterActor

class FadeEffect(a: CharacterActor, i: Int) : ActorEffect(a, i) {

    override fun update(progress: Float) {

        val p = progress * 2

        if (p < 1f) {
            characterActor.alpha = 1 - p
        } else {
            characterActor.alpha = p - 1
        }
    }
}