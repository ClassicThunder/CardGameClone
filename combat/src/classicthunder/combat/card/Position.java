package classicthunder.combat.card;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Position {

    private Vector2 location;
    private Vector2 size;
    private float rotation;

    public Position() {

        this.location = new Vector2();
        this.size = new Vector2();
        this.rotation = 0.0f;
    }

    public Position(Vector2 location, Vector2 size, float rotation) {

        Set(location, size, rotation);
    }

    public void Set(Vector2 location, Vector2 size, float rotation) {

        this.location = location.cpy();
        this.size = size.cpy();
        this.rotation = rotation;
    }

    public void LerpTowards(Position destination, float lerpAmount) {

        this.location = this.location.lerp(destination.getLocation(), lerpAmount);
        this.size = this.size.lerp(destination.getSize(), lerpAmount);
        this.rotation = MathUtils.lerp(this.rotation, 0, lerpAmount);
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
