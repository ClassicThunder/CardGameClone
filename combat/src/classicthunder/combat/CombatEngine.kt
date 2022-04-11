package classicthunder.combat

import classicthunder.card.Deck
import classicthunder.character.AINPC
import classicthunder.character.CharacterStats
import classicthunder.character.NPC
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
import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2

class CombatEngine(val layout: DeckLayout,
                   deck: Deck,
                   player: NPC,
                   enemy: AINPC,
                   drawPileUpdate: PileFunction,
                   discardPileUpdate: PileFunction,
                   energyUpdate: EnergyFunction) {

    internal val playerActor = CharacterActor(player, player.texture,
        Vector2(layout.centerWidth - 300, layout.centerHeight - 150))
    internal val enemyActor = AICharacterActor(enemy, enemy.texture,
        Vector2(layout.centerWidth + 300, layout.centerHeight - 150))

    internal val energy: Energy
    internal val hand: Hand
    internal val discarder: Discarder
    internal val drawPile: DrawPile
    internal val discardPile: DiscardPile
    internal val state: StateEngine

    // Input
    private val gameInputProcessor: CombatEngineInputProcessor =
        CombatEngineInputProcessor(this, playerActor, enemyActor)
    fun getGameInputProcessor(): InputProcessor {
        return gameInputProcessor.ip
    }

    init {
        drawPile = DrawPile(drawPileUpdate)
        drawPile.setPile(deck.GetCards().map { CardActor(layout, it) })

        discardPile = DiscardPile(discardPileUpdate)
        discarder = Discarder(layout.discardPosition) { cards ->
            for (card in cards) {
                card.resetToDrawPosition()
            }
            discardPile.addCards(cards)
        }

        hand = Hand(layout, 0.35f)
        for (i in 0..4) {
            hand.AddCard(drawPile.drawTopCard())
        }

        energy = Energy(6, energyUpdate)
        state = StateEngine(this)
    }

    internal fun canPlayCard(character: CharacterStats, card: CardActor): Boolean {
        return if (state.currentState == EngineState.PlayerControl &&
            energy.getEnergy() >= card.getEnergyCost()) {
            card.canApplyEffects(character)
        } else false
    }

    internal fun playCard(character: CharacterStats, card: CardActor) {

        if (!canPlayCard(character, card)) {
            return
        }

        energy.alterEnergy(-card.getEnergyCost())
        card.applyEffects(character)
        hand.DiscardCard(discarder, card)

        if (enemyActor.character.characterStats.GetHealth() <= 0) {
            state.forceState(EngineState.Done)
        }
    }

    internal fun requestEndTurn() {
        if (state.currentState == EngineState.PlayerControl) {
            state.forceState(EngineState.Discarding)
        }
    }

    /** Lifecycle */
    fun update() {
        playerActor.update()
        enemyActor.update()

        state.update()
    }

    fun draw(batch: SpriteBatch, polygonBatch: PolygonSpriteBatch?) {
        playerActor.draw(batch)
        enemyActor.draw(batch)

        hand.Draw(batch)
        hand.Draw(polygonBatch)
        discarder.draw(polygonBatch)
    }
}