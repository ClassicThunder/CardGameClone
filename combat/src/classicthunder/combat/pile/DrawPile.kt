package classicthunder.combat.pile

import classicthunder.combat.card.CardActor
import java.util.*

class DrawPile(private val pileFunction: PileFunction) {
    private val cards: MutableList<CardActor?> = ArrayList()
    fun ClearCards() {
        cards.clear()
        pileFunction.onCardCountChanged(0)
    }

    fun GetCardCount(): Int {
        return cards.size
    }

    fun SetPile(cards: List<CardActor?>?) {
        this.cards.clear()
        val tempCards: List<CardActor?> = ArrayList(cards)
        Collections.shuffle(tempCards)
        this.cards.addAll(tempCards)
        pileFunction.onCardCountChanged(this.cards.size)
    }

    fun DrawTopCard(): CardActor? {
        val topCard = cards.removeAt(0)
        pileFunction.onCardCountChanged(cards.size)
        return topCard
    }
}