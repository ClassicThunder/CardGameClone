package classicthunder.combat.hand

import classicthunder.combat.card.CardActor

interface DiscardFunction {
    fun onDiscard(cards: List<CardActor?>)
}