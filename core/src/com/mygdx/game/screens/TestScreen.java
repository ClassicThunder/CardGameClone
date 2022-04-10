package com.mygdx.game.screens;

import classicthunder.actor.ActorEffect;
import classicthunder.actor.FadeEffect;
import classicthunder.actor.WiggleEffect;
import classicthunder.combat.character.AICharacterActor;
import classicthunder.combat.character.CharacterActor;
import classicthunder.components.character.Character;
import classicthunder.components.character.CharacterStats;
import classicthunder.components.character.CharacterType;
import classicthunder.components.character.impl.StabbyBookCharacter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.GameContent;
import de.eskalon.commons.screen.ManagedScreen;

public class TestScreen extends ManagedScreen {

    private Viewport viewport;
    private Camera camera;

    private SpriteBatch batch;
    private PolygonSpriteBatch polygonBatch;

    private GameContent content;

    private CharacterActor player;
    private AICharacterActor enemy;

    int timer = 0;
    ActorEffect actorEffect;
    WiggleEffect wiggleEffect;

    @Override
    protected void create() {

        int worldWidth = 1600;
        int worldHeight = 900;

        camera = new OrthographicCamera(worldWidth, worldHeight);
        camera.translate(worldWidth / 2f, worldHeight / 2f, 0);
        viewport = new FitViewport(worldWidth, worldHeight, camera);

        batch = new SpriteBatch();
        polygonBatch = new PolygonSpriteBatch();

        content = new GameContent();
        content.Load();

        float centerX = worldWidth / 2f;
        float centerY = worldHeight / 2f;

        player = new CharacterActor(
                new Character(new CharacterStats(CharacterType.PLAYER, 25, 0)),
                content.GetTexture("PLAYER"),
                new Vector2(centerX - 300, centerY - 150));

        enemy = new AICharacterActor(
                new StabbyBookCharacter(new CharacterStats(CharacterType.ENEMY, 50, 0)),
                content.GetTexture("ENEMY"),
                new Vector2(centerX + 300, centerY - 150));

        actorEffect = new FadeEffect(player, 10);
        wiggleEffect = new WiggleEffect(enemy, 10);
    }

    @Override
    public void hide() {

    }

    @Override
    public void render(float delta) {

        actorEffect.tick();
        wiggleEffect.tick();

        // Effect Test
        if (timer > 60) {
            actorEffect.start();
            wiggleEffect.start();

            if (actorEffect.isDone() && wiggleEffect.isDone()) {
                timer = 0;
            }
        } else {
            timer++;
        }

        player.update();
        enemy.update();

        batch.setProjectionMatrix(camera.combined);
        polygonBatch.setProjectionMatrix(camera.combined);

        batch.begin();
        polygonBatch.begin();

        player.draw(batch);
        enemy.draw(batch);

        batch.end();
        polygonBatch.end();
    }

    @Override
    public void resize(int width, int height) {

        viewport.update(width, height);
        camera.update();
    }

    public void dispose() {

        batch.dispose();
        polygonBatch.dispose();

        content.Unload();
    }
}
