package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.character.CharacterEntity;
import com.mygdx.game.character.CharacterType;
import com.mygdx.game.deckengine.cards.CardLayout;
import com.mygdx.game.deckengine.cards.CardPosition;
import com.mygdx.game.deckengine.cards.StrikeCard;
import com.mygdx.game.deckengine.hand.Hand;
import com.mygdx.game.ui.StaticEntity;
import com.mygdx.game.utils.Timer;
import com.mygdx.game.utils.TimerFunction;

public class Game extends ApplicationAdapter {

    SpriteBatch batch;
    PolygonSpriteBatch polygonBatch;

    GameContent content;

    CardLayout cardLayout;
    Hand hand;

    CharacterEntity player;
    CharacterEntity enemy;

    StaticEntity drawPile;
    StaticEntity discardPile;

    StaticEntity energy;
    StaticEntity endTurn;

    private Timer timer;
    private GameInputProcessor gameInputProcessor;

    @Override
    public void create() {

        batch = new SpriteBatch();
        polygonBatch = new PolygonSpriteBatch();

        content = new GameContent();
        content.Load();

        createUI();
        createHand();
        createCharacters();

        gameInputProcessor = new GameInputProcessor(hand, player, enemy);
        gameInputProcessor.Activate();

        timer = new Timer(60 * 1, new TimerFunction() {
            @Override
            public void onTrigger() {
                if (hand.GetCardCount() == 11) {
                    hand.ClearCards();
                } else {
                    hand.AddCard(new StrikeCard(cardLayout, content.GetTexture("CARD_STRIKE")));
                }
            }
        });
    }

    private void createUI() {

        float pileSize = 128f;
        float pileBuffer = 15f + pileSize;
        Vector2 pileSizeVector = new Vector2(pileSize, pileSize);

        Vector2 drawLocation = new Vector2(pileBuffer, pileBuffer);
        Vector2 discardLocation = new Vector2(Gdx.graphics.getWidth() - pileBuffer, pileBuffer);

        cardLayout = new CardLayout(
                new CardPosition(drawLocation, pileSizeVector, 0f),
                new CardPosition(discardLocation, pileSizeVector, 0f));

        drawPile = new StaticEntity(content.GetTexture("UX_DRAW"), drawLocation, pileSizeVector);

        discardPile = new StaticEntity(content.GetTexture("UX_DISCARD"), discardLocation, pileSizeVector);

        energy = new StaticEntity(content.GetDebugTexture(),
                new Vector2(pileBuffer * 2, pileBuffer * 2),
                pileSizeVector);

        endTurn = new StaticEntity(content.GetDebugTexture(),
                new Vector2(Gdx.graphics.getWidth() - pileBuffer * 2, pileBuffer * 2),
                pileSizeVector);
    }

    private void createHand() {

        float center = Gdx.graphics.getWidth() / 2f;

        float handWidth = Gdx.graphics.getWidth() - 400;
        hand = new Hand(content, cardLayout, center, handWidth, 0.35f);
    }

    private void createCharacters() {

        float centerX = Gdx.graphics.getWidth() / 2f;
        float centerY = Gdx.graphics.getHeight() / 2f;

        player = new CharacterEntity(CharacterType.PLAYER, content.GetTexture("PLAYER"),
                new Vector2(centerX - 300, centerY - 150));

        enemy = new CharacterEntity(CharacterType.ENEMY, content.GetTexture("ENEMY"),
                new Vector2(centerX + 300, centerY - 150));
    }


    @Override
    public void render() {

        ScreenUtils.clear(0, 0, 0, 1);

        // ##### Update ##### //
        timer.Update();
        hand.Update();

        // ##### Draw Sprites ##### //
        batch.begin();

        drawPile.Draw(batch);
        discardPile.Draw(batch);
        energy.Draw(batch);
        endTurn.Draw(batch);

        player.Draw(batch);
        enemy.Draw(batch);

        hand.Draw(batch);

        batch.end();

        // ##### Draw Polygons ##### //
        polygonBatch.begin();

        hand.Draw(polygonBatch);

        polygonBatch.end();
    }

    @Override
    public void dispose() {

        batch.dispose();
        polygonBatch.dispose();

        content.Unload();
    }
}
