package classicthunder.combat.character;

public class CharacterStats {

    private final CharacterType characterType;

    private int health;
    private int block;

    public CharacterStats(CharacterType characterType, int startingHealth) {

        this.characterType = characterType;
        this.health = startingHealth;
        this.block = 0;
    }

    public void AdjustHealth(int delta) {
        this.health += delta;
    }

    public int GetHealth() {
        return this.health;
    }

    public void AdjustBlock(int delta) {
        this.block += delta;
    }

    public int GetBlock() {
        return this.block;
    }

    public CharacterType getCharacterType() {
        return characterType;
    }
}
