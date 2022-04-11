package classicthunder.combat.hand

import classicthunder.combat.card.CardActor
import classicthunder.combat.layout.DeckLayout
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2

internal class Hand(layout: DeckLayout, cardScale: Float) {
    val MAX_CARDS = 11
    private val cardsInHand: MutableList<CardActor> = ArrayList(MAX_CARDS)
    private val cardBezier: CardBezier

    init {
        val cardSize = Vector2(CARD_WIDTH, CARD_HEIGHT)
        cardSize.scl(cardScale)
        val cardArea = Rectangle(
                layout.centerWidth - layout.handWidth / 2f, 0.0f,
                layout.handWidth, 200.0f)
        cardBezier = CardBezier(cardArea, cardSize)
    }

    // Card Info
    fun AddCard(card: CardActor) {
        cardsInHand.add(0, card)
        cardBezier.findRestingPosition(cardsInHand)
    }

    fun ClearCards() {
        cardsInHand.clear()
    }

    fun GetCardCount(): Int {
        return cardsInHand.size
    }

    // Collision Detection
    fun GrabCard(mouse: Vector2): CardActor? {
        for (x in cardsInHand.indices.reversed()) {
            val currentCard = cardsInHand[x]
            if (currentCard.containsMouse(mouse)) {
                currentCard.setGrabbed(true)
                currentCard.setDragPosition(mouse)
                return currentCard
            }
        }
        return null
    }

    fun DiscardHand(discard: Discarder) {
        discard.addCards(cardsInHand)
        cardsInHand.clear()
    }

    fun DiscardCard(discard: Discarder, card: CardActor) {
        discard.addCard(card)
        cardsInHand.remove(card)
        cardBezier.findRestingPosition(cardsInHand)
    }

    fun ResetCards() {
        for (card in cardsInHand) {
            card.reset()
        }
    }

    // LifeCycle
    fun Update() {
        for (card in cardsInHand) {
            card.update()
        }
    }

    fun Draw(polygonBatch: PolygonSpriteBatch?) {
        for (card in cardsInHand) {
            card.draw(polygonBatch, 1.0f)
        }
    }

    fun Draw(batch: SpriteBatch) {}

    companion object {
        const val CARD_WIDTH = 678f
        const val CARD_HEIGHT = 874f
    }
}