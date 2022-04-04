package classicthunder.combat.layout;

import classicthunder.combat.card.Position;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;

public class DeckLayout {

    final private int worldWidth = 1600;
    final private int centerWidth = worldWidth / 2;

    final private int worldHeight = 900;
    final private int centerHeight = worldHeight / 2;

    final private int edgeBuffer = 200;

    final private Position drawPosition;
    final private Position discardPosition;

    final private Viewport viewport;

    public DeckLayout(Viewport viewport, Vector2 drawLocation, Vector2 discardLocation) {

        this.viewport = viewport;

        this.drawPosition = new Position(drawLocation, new Vector2(100, 150), 0f);
        this.discardPosition = new Position(discardLocation, new Vector2(100, 150), 0f);
    }

    public Position getDrawPosition() {
        return drawPosition;
    }

    public Position getDiscardPosition() {
        return discardPosition;
    }

    public Viewport getViewport() {
        return viewport;
    }

    public int getCenterWidth() {
        return centerWidth;
    }

    public int getCenterHeight() {
        return centerHeight;
    }

    public int getHandWidth() {
        return worldWidth - (2 * edgeBuffer);
    }
}
