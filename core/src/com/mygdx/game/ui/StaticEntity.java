package com.mygdx.game.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Vector2;

public class StaticEntity {

    private final Texture texture;

    private final Vector2 size;
    private final Vector2 location;

    public StaticEntity(Texture texture, Vector2 location, Vector2 size) {

        this.texture = texture;

        // Center the location
        Matrix3 transformations = new Matrix3()
                .setToTranslation(new Vector2(-size.x / 2, -size.y / 2));

        this.location = location.mul(transformations);
        this.size = size;
    }

    public void Draw(SpriteBatch batch) {

        batch.draw(texture, location.x, location.y, size.x, size.y);
    }
}
