package classicthunder.card.impl

import classicthunder.card.Card
import classicthunder.character.CharacterStats
import classicthunder.character.CharacterType

class StrikeCard(parent: Card?) : Card(parent) {

    private var damage: Int

    init {
        damage = 6
    }

    override fun getEnergyCost(): Int {
        return 1
    }

    override fun canPlay(stats: CharacterStats): Boolean {
        return stats.characterType == CharacterType.ENEMY
    }

    override fun play(stats: CharacterStats) {
        stats.dealDamage(damage)
    }

    override fun copy(parent: Card): StrikeCard {
        val copy = StrikeCard(parent)
        copy.damage = this.damage
        return copy
    }
}