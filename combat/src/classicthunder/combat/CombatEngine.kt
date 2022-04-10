package classicthunder.combat

import classicthunder.combat.card.CardActor
import classicthunder.combat.character.AICharacterActor
import classicthunder.combat.character.CharacterActor
import classicthunder.combat.energy.Energy
import classicthunder.combat.energy.EnergyFunction
import classicthunder.combat.hand.Discarder
import classicthunder.combat.hand.Hand
import classicthunder.combat.layout.DeckLayout
import classicthunder.combat.pile.DiscardPile
import classicthunder.combat.pile.DrawPile
import classicthunder.combat.pile.PileFunction
import classicthunder.combat.state.EngineState
import classicthunder.combat.state.StateEngine
import classicthunder.components.card.Deck
import classicthunder.components.character.CharacterStats
import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch
import com.badlogic.gdx.graphics.g2d.SpriteBatch

class CombatEngine(val layout: DeckLayout,
                   deck: Deck,
                   val player: CharacterActor,
                   val enemy: AICharacterActor,
                   drawPileUpdate: PileFunction,
                   discardPileUpdate: PileFunction,
                   energyUpdate: EnergyFunction) {

    val energy: Energy
    val hand: Hand
    val discarder: Discarder
    val drawPile: DrawPile
    val discardPile: DiscardPile
    internal val state: StateEngine

    // Input
    private val gameInputProcessor: CombatEngineInputProcessor =
        CombatEngineInputProcessor(this, player, enemy)
    fun getGameInputProcessor(): InputProcessor {
        return gameInputProcessor.ip
    }

    init {
        drawPile = DrawPile(drawPileUpdate)
        drawPile.SetPile(deck.GetCards().map { CardActor(layout, it) })

        discardPile = DiscardPile(discardPileUpdate)
        discarder = Discarder(layout.discardPosition) { cards ->
            for (card in cards) {
                card!!.ResetToDrawPosition()
            }
            discardPile.AddCards(cards)
        }

        hand = Hand(layout, 0.35f)
        for (i in 0..4) {
            hand.AddCard(drawPile.DrawTopCard())
        }

        energy = Energy(6, energyUpdate)
        state = StateEngine(this, EngineState.PlayerControl)
    }

    fun canPlayCard(character: CharacterStats, card: CardActor): Boolean {
        return if (state.currentState == EngineState.PlayerControl &&
            energy.getEnergy() >= card.GetEnergyCost()) {
            card.CanApplyEffects(character)
        } else false
    }

    fun playCard(character: CharacterStats, card: CardActor) {

        if (!canPlayCard(character, card)) {
            return
        }

        energy.alterEnergy(-card.GetEnergyCost())
        card.ApplyEffects(character)
        hand.DiscardCard(discarder, card)

        if (enemy.character.characterStats.GetHealth() <= 0) {
            state.forceState(EngineState.Done)
        }
    }

    fun requestEndTurn() {
        if (state.currentState == EngineState.PlayerControl) {
            state.forceState(EngineState.Discarding)
        }
    }

    /** Lifecycle */
    fun update() {
        state.update()
    }

    fun draw(batch: SpriteBatch?, polygonBatch: PolygonSpriteBatch?) {
        hand.Draw(batch)
        hand.Draw(polygonBatch)
        discarder.Draw(polygonBatch)
    }
}