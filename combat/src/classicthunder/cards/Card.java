package classicthunder.cards;

import classicthunder.combat.character.CharacterStats;
import com.badlogic.gdx.graphics.Texture;

abstract public class Card {

    private Texture texture;
    private int energyCost;

    public Card(Texture texture, int energyCost) {
        this.texture = texture;
        this.energyCost = energyCost;
    }
    public abstract int GetEnergyCost();

    public abstract boolean CanApplyEffects(CharacterStats stats);

    public abstract void ApplyEffects(CharacterStats stats);

    public Texture getTexture() {
        return texture;
    }

    public int getEnergyCost() {
        return energyCost;
    }
}
