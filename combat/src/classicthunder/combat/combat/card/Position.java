package classicthunder.combat.combat.card;

import com.badlogic.gdx.math.Vector2;

public class Position {

    private final Vector2 location;
    private final Vector2 size;
    private final float rotation;

    public Position(Vector2 location, Vector2 size, float rotation) {

        this.location = location.cpy();
        this.size = size.cpy();
        this.rotation = rotation;
    }

    public Vector2 getLocation() {
        return location.cpy();
    }

    public Vector2 getSize() {
        return size.cpy();
    }

    public float getRotation() {
        return rotation;
    }
}
