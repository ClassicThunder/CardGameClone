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
import kotlin.test.assertFails
import kotlin.test.assertFalse

class CombatTest {

    @Test
    fun combatTest() {

        // Set up

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

        // ############# Test Attacking ############# //

        val strikeCard = combat.deck.getHand().first { card -> card is StrikeCard }

        val startingHealth = enemy.characterStats.getHealth()

        combat.canPlayCard(strikeCard, enemy.characterStats)
        combat.playCard(strikeCard, enemy.characterStats)
        Assertions.assertEquals(4, combat.deck.getHand().count())

        // Make sure we can't play the same card twice
        assertFalse { combat.canPlayCard(strikeCard, enemy.characterStats) }

        Assertions.assertEquals(6, startingHealth - enemy.characterStats.getHealth())
        Assertions.assertEquals(2, combat.currentEnergy)

        // ############# Test Defending ############# //

        val defendCard = combat.deck.getHand().first { card -> card is DefendCard }

        combat.canPlayCard(defendCard, player.characterStats)
        combat.playCard(defendCard, player.characterStats)
        assertEquals(5, player.characterStats.getBlock())

        Assertions.assertEquals(3, combat.deck.getHand().count())

        // ############# Let Enemy Take Their Turn ############# //

        combat.
    }
}