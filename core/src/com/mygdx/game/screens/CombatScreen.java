package com.mygdx.game.screens;

import classicthunder.card.Deck;
import classicthunder.card.DefendCard;
import classicthunder.card.StrikeCard;
import classicthunder.character.AINPC;
import classicthunder.character.CharacterStats;
import classicthunder.character.NPC;
import classicthunder.combat.CombatEngine;
import classicthunder.character.CharacterType;
import classicthunder.combat.layout.DeckLayout;
import classicthunder.character.impl.StabbyBookNPC;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.GameContent;
import com.mygdx.game.ui.Label;
import de.eskalon.commons.screen.ManagedScreen;

public class CombatScreen extends ManagedScreen {

    private Viewport viewport;
    private Camera camera;

    private SpriteBatch batch;
    private PolygonSpriteBatch polygonBatch;

    private GameContent content;

    private CombatEngine deckEngine;

    private NPC player;
    private AINPC enemy;

    private Label drawPileLabel;
    private Label discardPileLabel;
    private Label energy;
    private Label endTurn;

    final private int worldWidth = 1600;
    final private int worldHeight = 900;

    @Override
    protected void create() {

        camera = new OrthographicCamera(worldWidth, worldHeight);
        camera.translate(worldWidth / 2f, worldHeight / 2f, 0);
        viewport = new FitViewport(worldWidth, worldHeight, camera);

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
        Vector2 discardLocation = new Vector2(worldWidth - pileBuffer, pileBuffer);

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
                new Vector2(worldWidth - pileBuffer * 2, pileBuffer * 2),
                pileSizeVector);
        endTurn.setText("Press [ENTER] to\nend turn");
    }

    private void createCharacters() {

        player = new NPC(
                content.GetTexture("PLAYER"),
                new CharacterStats(CharacterType.PLAYER, 25, 0));

        enemy = new StabbyBookNPC(
                content.GetTexture("ENEMY"),
                new CharacterStats(CharacterType.ENEMY, 50, 0));

    }

    private void createDeckEngine() {

        float pileSize = 128f;
        float pileBuffer = 15f + pileSize;

        Vector2 drawLocation = new Vector2(pileBuffer, pileBuffer);
        Vector2 discardLocation = new Vector2(worldWidth - pileBuffer, pileBuffer);

        DeckLayout layout = new DeckLayout(viewport, drawLocation, discardLocation);
        Texture s = content.GetTexture("CARD_STRIKE");
        Texture d = content.GetTexture("CARD_DEFEND");

        Deck deck = new Deck();
        deck.AddCard(new StrikeCard(s));
        deck.AddCard(new StrikeCard(s));
        deck.AddCard(new StrikeCard(s));
        deck.AddCard(new StrikeCard(s));
        deck.AddCard(new StrikeCard(s));
        deck.AddCard(new StrikeCard(s));
        deck.AddCard(new DefendCard(d));
        deck.AddCard(new DefendCard(d));
        deck.AddCard(new DefendCard(d));
        deck.AddCard(new DefendCard(d));

        deckEngine = new CombatEngine(
                layout,
                deck, player, enemy,
                cardCount -> drawPileLabel.setText("" + cardCount),
                cardCount -> discardPileLabel.setText("" + cardCount),
                (count, base) -> energy.setText(count + "/" + base));
    }

    @Override
    public void hide() {

    }

    @Override
    public void render(float delta) {

        batch.setProjectionMatrix(camera.combined);
        polygonBatch.setProjectionMatrix(camera.combined);

        deckEngine.update();

        batch.begin();
        polygonBatch.begin();

        drawPileLabel.Draw(batch);
        discardPileLabel.Draw(batch);
        energy.Draw(batch);
        endTurn.Draw(batch);

        deckEngine.draw(batch, polygonBatch);

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
