package classicthunder.combat.pile

import classicthunder.combat.card.CardActor

internal class DiscardPile(private val pileFunction: PileFunction) {
    private val cards: MutableList<CardActor> = ArrayList()

    fun clearCards() {
        cards.clear()
        pileFunction.onCardCountChanged(0)
    }

    fun addCard(card: CardActor) {
        cards.add(card)
        pileFunction.onCardCountChanged(cards.size)
    }

    fun addCards(cards: List<CardActor>) {
        this.cards.addAll(cards)
        pileFunction.onCardCountChanged(this.cards.size)
    }

    fun getCardCount(): Int {
        return cards.size
    }

    fun empty(): List<CardActor> {
        val tempCards: List<CardActor> = ArrayList(cards)
        cards.clear()
        pileFunction.onCardCountChanged(0)
        return tempCards
    }
}