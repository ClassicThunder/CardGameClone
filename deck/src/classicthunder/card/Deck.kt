package classicthunder.card

class Deck {

    private val cards: MutableList<Card>

    constructor() {
        cards = ArrayList()
    }

    constructor(cards: MutableList<Card>) {
        this.cards = cards
    }

    fun AddCard(card: Card) {
        cards.add(card)
    }

    fun AddCards(cards: List<Card>?) {
        this.cards.addAll(cards!!)
    }

    fun GetCards(): List<Card> {
        return cards
    }
}