package classicthunder.combat.state

import classicthunder.actor.FadeEffect
import classicthunder.actor.WiggleEffect
import classicthunder.combat.character.AICharacterActor
import classicthunder.combat.character.CharacterActor

class EnemyTurnState(
    private val player: CharacterActor,
    private val enemy: AICharacterActor) : State
{
    private val actorEffect = FadeEffect(player, 10)
    private val wiggleEffect = WiggleEffect(enemy, 10)

    var timer = 0

    override fun enter() {
        timer = 0

        actorEffect.start()
        wiggleEffect.start()
    }

    override fun update(): EngineState {
        actorEffect.tick()
        wiggleEffect.tick()

        if (actorEffect.isDone() && wiggleEffect.isDone()) {

            if (timer == 0) {
                val damage = enemy.character.takeTurn()
                player.character.characterStats.AdjustHealth(-damage)

                if (player.character.characterStats.GetHealth() <= 0) {
                    return EngineState.Done
                }
            }

            timer++
        }

        return if (timer > 20) {
            EngineState.Drawing
        } else EngineState.EnemyTurn
    }

    override fun exit() {}
}