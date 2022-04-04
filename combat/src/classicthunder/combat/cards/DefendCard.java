package classicthunder.combat.cards;

import classicthunder.combat.combat.character.CharacterStats;
import classicthunder.combat.combat.character.CharacterType;
import com.badlogic.gdx.graphics.Texture;

public class DefendCard extends Card {

    public DefendCard(Texture texture) {
        super(texture, 1);
    }

    @Override
    public int GetEnergyCost() {
        return 1;
    }

    @Override
    public boolean CanApplyEffects(CharacterStats stats) {
        return (stats.getCharacterType() == CharacterType.PLAYER);
    }

    @Override
    public void ApplyEffects(CharacterStats stats) {
        stats.AdjustBlock(5);
    }
}
