package classicthunder.combat.character;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class CharacterActor {

    private final Sprite sprite;
    private final CharacterStats characterStats;

    private final BitmapFont font;

    public CharacterActor(CharacterType characterType, Texture texture, Vector2 location) {

        Vector2 size = new Vector2(texture.getWidth(), texture.getHeight());

        sprite = new Sprite(texture);

        sprite.setOrigin(size.x / 2f, 0);
        sprite.setOriginBasedPosition(location.x, location.y);

        characterStats = new CharacterStats(characterType, 20);

        font = new BitmapFont();
    }

    public boolean ContainsMouse(Vector2 mouse) {

        return sprite.getBoundingRectangle().contains(mouse);
    }

    public CharacterStats GetStats() {

        return characterStats;
    }

    public void Draw(SpriteBatch batch) {

        sprite.draw(batch);

        font.draw(batch, "HP = " + characterStats.GetHealth(),
                sprite.getX(), sprite.getY() + sprite.getHeight());
        font.draw(batch, "Block = " + characterStats.GetBlock(),
                sprite.getX(), sprite.getY() + sprite.getHeight() + 14);

    }
}
