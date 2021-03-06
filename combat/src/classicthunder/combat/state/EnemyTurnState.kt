package classicthunder.combat.state

import classicthunder.actor.FadeEffect
import classicthunder.actor.WiggleEffect
import classicthunder.combat.character.AICharacterActor
import classicthunder.combat.character.CharacterActor

internal class EnemyTurnState(
    private val player: CharacterActor,
    private val enemy: AICharacterActor
) : State {
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
                val damage = enemy.character.getIntent()
                player.character.characterStats.dealDamage(damage)

                if (player.character.characterStats.getHealth() <= 0) {
                    return EngineState.Done
                }
            }

            timer++
        }

        return if (timer > 20) {
            EngineState.Drawing
        } else EngineState.EnemyTurn
    }

    override fun exit() {
        enemy.character.resetIntent()
    }
}