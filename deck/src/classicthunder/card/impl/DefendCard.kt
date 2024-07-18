package classicthunder.card.impl

import classicthunder.card.Card
import classicthunder.character.CharacterStats
import classicthunder.character.CharacterType

class DefendCard(parent: Card?) : Card(parent) {

    private var block: Int

    init {
        block = 5
    }

    override fun getEnergyCost(): Int {
        return 1
    }

    override fun canPlay(stats: CharacterStats): Boolean {
        return stats.characterType == CharacterType.PLAYER
    }

    override fun play(stats: CharacterStats) {
        stats.adjustBlock(block)
    }

    override fun copy(parent: Card): DefendCard {
        val copy = DefendCard(parent)
        copy.block = this.block
        return copy
    }
}