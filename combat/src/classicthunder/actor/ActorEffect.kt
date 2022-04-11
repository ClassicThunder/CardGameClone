package classicthunder.actor

import classicthunder.combat.character.CharacterActor

internal abstract class ActorEffect(
    protected val characterActor: CharacterActor,
    private val lengthInTicks: Int)
{
    private var ticks = 0
    private var isEnabled: Boolean = false

    /**
     * If the actor is not currently running then it starts at the beginning
     * of the animation.
     */
    fun start() {
        if (isEnabled) return;

        isEnabled = true
        ticks = 0
    }

    fun isDone(): Boolean {
        return ticks >= lengthInTicks
    }

    protected abstract fun update(progress: Float)

    fun tick() {

        if (!isEnabled) return

        if (ticks < lengthInTicks) {
            update(ticks.toFloat() / lengthInTicks.toFloat())
            ticks++
        } else {
            characterActor.reset()
            isEnabled = false
        }
    }
}