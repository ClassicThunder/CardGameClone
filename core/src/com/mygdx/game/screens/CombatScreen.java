package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.GameContent;
import com.mygdx.game.character.CharacterEntity;
import com.mygdx.game.character.CharacterType;
import com.mygdx.game.deckengine.DeckEngine;
import com.mygdx.game.ui.Label;
import de.eskalon.commons.screen.ManagedScreen;

public class CombatScreen extends ManagedScreen {

    SpriteBatch batch;
    PolygonSpriteBatch polygonBatch;

    GameContent content;

    DeckEngine deckEngine;

    CharacterEntity player;
    CharacterEntity enemy;

    Label drawPileLabel;
    Label discardPileLabel;
    Label energy;
    Label endTurn;

    @Override
    protected void create() {

        batch = new SpriteBatch();
        polygonBatch = new PolygonSpriteBatch();

        content = new GameContent();
        content.Load();

        createUI();
        createCharacters();
        createDeckEngine();

        super.addInputProcessor(this.deckEngine.getGameInputProcessor());
    }

    private void createUI() {

        float pileSize = 128f;
        float pileBuffer = 15f + pileSize;
        Vector2 pileSizeVector = new Vector2(pileSize, pileSize);

        Vector2 drawLocation = new Vector2(pileBuffer, pileBuffer);
        Vector2 discardLocation = new Vector2(Gdx.graphics.getWidth() - pileBuffer, pileBuffer);

        drawPileLabel = new Label(
                content.GetDebugFont(),
                content.GetTexture("UX_DRAW"),
                drawLocation,
                pileSizeVector);

        discardPileLabel = new Label(
                content.GetDebugFont(),
                content.GetTexture("UX_DISCARD"),
                discardLocation,
                pileSizeVector);

        energy = new Label(
                content.GetDebugFont(),
                content.GetTexture("UX_ENERGY"),
                new Vector2(pileBuffer * 2, pileBuffer * 2),
                new Vector2(64, 64));

        endTurn = new Label(
                content.GetDebugFont(),
                null,
                new Vector2(Gdx.graphics.getWidth() - pileBuffer * 2, pileBuffer * 2),
                pileSizeVector);
        endTurn.setText("Press [ENTER] to\nend turn");
    }

    private void createCharacters() {

        float centerX = Gdx.graphics.getWidth() / 2f;
        float centerY = Gdx.graphics.getHeight() / 2f;

        player = new CharacterEntity(
                CharacterType.PLAYER,
                content.GetTexture("PLAYER"),
                new Vector2(centerX - 300, centerY - 150));

        enemy = new CharacterEntity(
                CharacterType.ENEMY,
                content.GetTexture("ENEMY"),
                new Vector2(centerX + 300, centerY - 150));
    }

    private void createDeckEngine() {

        float pileSize = 128f;
        float pileBuffer = 15f + pileSize;

        Vector2 drawLocation = new Vector2(pileBuffer, pileBuffer);
        Vector2 discardLocation = new Vector2(Gdx.graphics.getWidth() - pileBuffer, pileBuffer);

        float center = Gdx.graphics.getWidth() / 2f;
        float handWidth = Gdx.graphics.getWidth() - 400;

        deckEngine = new DeckEngine(
                center, handWidth, drawLocation, discardLocation,
                content, player, enemy,
                cardCount -> drawPileLabel.setText("" + cardCount),
                cardCount -> discardPileLabel.setText("" + cardCount),
                energyCount -> energy.setText("" + energyCount));
    }

    @Override
    public void hide() {

    }

    @Override
    public void render(float delta) {

        deckEngine.Update();

        batch.begin();
        polygonBatch.begin();

        drawPileLabel.Draw(batch);
        discardPileLabel.Draw(batch);
        energy.Draw(batch);
        endTurn.Draw(batch);

        player.Draw(batch);
        enemy.Draw(batch);

        deckEngine.Draw(batch, polygonBatch);

        batch.end();
        polygonBatch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    public void dispose() {

        batch.dispose();
        polygonBatch.dispose();

        content.Unload();
    }
}
