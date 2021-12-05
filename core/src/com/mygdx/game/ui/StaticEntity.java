package com.mygdx.game.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class StaticEntity {

    protected final Texture texture;
    protected final Vector2 size;
    protected final Vector2 center;

    public StaticEntity(Texture texture, Vector2 center, Vector2 size) {

        this.texture = texture;
        this.center = center;
        this.size = size;
    }

    public void Draw(SpriteBatch batch) {

        float x = center.x - (size.x / 2.0f);
        float y = center.y - (size.y / 2.0f);
        batch.draw(texture, x, y, size.x, size.y);
    }
}
