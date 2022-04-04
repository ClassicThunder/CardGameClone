package classicthunder.combat.cards;

import classicthunder.combat.combat.character.CharacterStats;
import classicthunder.combat.combat.character.CharacterType;
import com.badlogic.gdx.graphics.Texture;

public class StrikeCard extends Card {

    public StrikeCard(Texture texture) {
        super(texture, 1);
    }

    @Override
    public int GetEnergyCost() {
        return 1;
    }

    @Override
    public boolean CanApplyEffects(CharacterStats stats) {

        return (stats.getCharacterType() == CharacterType.ENEMY);
    }

    @Override
    public void ApplyEffects(CharacterStats stats) {
        stats.AdjustHealth(-6);
    }
}
