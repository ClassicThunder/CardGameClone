package classicthunder.combat.state

import classicthunder.character.NonPlayableCharacter
import classicthunder.character.PlayableCharacter
import classicthunder.combat.energy.Energy
import classicthunder.combat.hand.Discarder
import classicthunder.combat.hand.Hand

internal class PlayerControlState(
    private val hand: Hand,
    private val discarder: Discarder,
    private val energy: Energy,
    private val player: PlayableCharacter,
    private val enemy: NonPlayableCharacter
) : State {
    override fun enter() {
        enemy.rollIntent()
        player.characterStats.reset()
        energy.resetToBaseAmount()
    }

    override fun update(): EngineState {
        hand.update()
        discarder.update()
        return EngineState.PlayerControl
    }

    override fun exit() {}
}