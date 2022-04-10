package classicthunder.combat.pile

import classicthunder.combat.card.CardActor

class DiscardPile(private val pileFunction: PileFunction) {
    private val cards: MutableList<CardActor?> = ArrayList()
    fun ClearCards() {
        cards.clear()
        pileFunction.onCardCountChanged(0)
    }

    fun AddCard(card: CardActor?) {
        cards.add(card)
        pileFunction.onCardCountChanged(cards.size)
    }

    fun AddCards(cards: List<CardActor?>?) {
        this.cards.addAll(cards!!)
        pileFunction.onCardCountChanged(this.cards.size)
    }

    fun GetCardCount(): Int {
        return cards.size
    }

    fun Empty(): List<CardActor?> {
        val tempCards: List<CardActor?> = ArrayList(cards)
        cards.clear()
        pileFunction.onCardCountChanged(0)
        return tempCards
    }
}