package classicthunder.combat.hand

import classicthunder.card.Card

interface DiscardFunction {
    fun onDiscard(cards: List<Card>)
}