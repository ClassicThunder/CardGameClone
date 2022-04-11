package classicthunder.combat.pile

import classicthunder.combat.card.CardActor

internal class DrawPile(private val pileFunction: PileFunction) {
    private var cards: MutableList<CardActor> = ArrayList()

    fun clearCards() {
        cards.clear()
        pileFunction.onCardCountChanged(0)
    }

    fun getCardCount(): Int {
        return cards.size
    }

    fun setPile(cards: List<CardActor>) {
        this.cards = cards.shuffled().toMutableList()
        pileFunction.onCardCountChanged(this.cards.size)
    }

    fun drawTopCard(): CardActor {
        val topCard = cards.removeFirst()
        pileFunction.onCardCountChanged(cards.size)
        return topCard
    }
}