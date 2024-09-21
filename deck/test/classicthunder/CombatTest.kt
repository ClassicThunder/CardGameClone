package classicthunder

import classicthunder.card.Deck
import classicthunder.card.impl.DefendCard
import classicthunder.card.impl.StrikeCard
import classicthunder.character.CharacterStats
import classicthunder.character.CharacterType
import classicthunder.character.PlayableCharacter
import classicthunder.character.impl.StabbyBookPlayableCharacter
import classicthunder.combat.CombatInstance
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class CombatTest {

    @Test
    fun combatTest() {

        val deck = Deck(listOf(
            StrikeCard(null),
            StrikeCard(null),
            StrikeCard(null),
            StrikeCard(null),
            StrikeCard(null),
            StrikeCard(null),
            DefendCard(null),
            DefendCard(null),
            DefendCard(null),
            DefendCard(null),
        ))

        val player = PlayableCharacter(CharacterStats(CharacterType.PLAYER, 100))
        val enemy = StabbyBookPlayableCharacter(CharacterStats(CharacterType.ENEMY, 100))

        val combat = CombatInstance(deck, player, enemy, 3)

        combat.start()
        val strikeCard = combat.deck.getHand().first { card -> card is StrikeCard }

        val startingHealth = enemy.characterStats.getHealth()

        combat.canPlayCard(strikeCard, enemy.characterStats)
        combat.playCard(strikeCard, enemy.characterStats)

        Assertions.assertEquals(6, startingHealth - enemy.characterStats.getHealth())
        Assertions.assertEquals(2, combat.currentEnergy)
    }
}