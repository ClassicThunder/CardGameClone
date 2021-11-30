package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.character.CharacterEntity;
import com.mygdx.game.character.CharacterType;
import com.mygdx.game.deckengine.DiscardPile;
import com.mygdx.game.deckengine.DrawPile;
import com.mygdx.game.deckengine.cards.*;
import com.mygdx.game.deckengine.hand.DiscardFunction;
import com.mygdx.game.deckengine.hand.Discarder;
import com.mygdx.game.deckengine.hand.Hand;
import com.mygdx.game.ui.StaticEntity;

import java.util.List;

public class Game extends ApplicationAdapter {

    SpriteBatch batch;
    PolygonSpriteBatch polygonBatch;

    GameContent content;

    CardLayout cardLayout;
    Hand hand;
    Discarder discarder;

    Deck deck;
    DrawPile drawPile;
    DiscardPile discardPile;

    CharacterEntity player;
    CharacterEntity enemy;

    StaticEntity drawPileEntity;
    StaticEntity discardPileEntity;

    StaticEntity energy;
    StaticEntity endTurn;

    private GameInputProcessor gameInputProcessor;

    @Override
    public void create() {

        batch = new SpriteBatch();
        polygonBatch = new PolygonSpriteBatch();

        content = new GameContent();
        content.Load();

        createUI();
        createCardStuff();
        createCharacters();

        gameInputProcessor = new GameInputProcessor(hand, discarder, player, enemy);
        gameInputProcessor.Activate();
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

        drawPileEntity = new StaticEntity(
                content.GetTexture("UX_DRAW"),
                drawLocation, pileSizeVector);

        discardPileEntity = new StaticEntity(
                content.GetTexture("UX_DISCARD"),
                discardLocation, pileSizeVector);

        energy = new StaticEntity(content.GetDebugTexture(),
                new Vector2(pileBuffer * 2, pileBuffer * 2),
                pileSizeVector);

        endTurn = new StaticEntity(content.GetDebugTexture(),
                new Vector2(Gdx.graphics.getWidth() - pileBuffer * 2, pileBuffer * 2),
                pileSizeVector);
    }

    private void createCardStuff() {

        //
        Texture s = content.GetTexture("CARD_STRIKE");
        Texture d = content.GetTexture("CARD_DEFEND");

        this.deck = new Deck();
        this.deck.AddCard(new StrikeCard(cardLayout, s));
        this.deck.AddCard(new StrikeCard(cardLayout, s));
        this.deck.AddCard(new StrikeCard(cardLayout, s));
        this.deck.AddCard(new StrikeCard(cardLayout, s));
        this.deck.AddCard(new StrikeCard(cardLayout, s));
        this.deck.AddCard(new StrikeCard(cardLayout, s));
        this.deck.AddCard(new DefendCard(cardLayout, d));
        this.deck.AddCard(new DefendCard(cardLayout, d));
        this.deck.AddCard(new DefendCard(cardLayout, d));
        this.deck.AddCard(new DefendCard(cardLayout, d));

        //
        float center = Gdx.graphics.getWidth() / 2f;

        float handWidth = Gdx.graphics.getWidth() - 400;
        hand = new Hand(content, cardLayout, center, handWidth, 0.35f);

        //
        this.discarder = new Discarder(cardLayout.getDiscardPosition(),
                new DiscardFunction() {
                    @Override
                    public void onDiscard(List<Card> cards) {
                        discardPile.AddCards(cards);
                    }
                });

        //
        this.drawPile = new DrawPile();
        this.drawPile.SetPile(deck.GetCards());

        this.discardPile = new DiscardPile();

        //
        for (int i = 0; i < 5; i++) {
            this.hand.AddCard(drawPile.DrawTopCard());
        }
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
        hand.Update();
        discarder.Update();

        // ##### Draw Sprites ##### //
        batch.begin();

        drawPileEntity.Draw(batch);
        discardPileEntity.Draw(batch);
        energy.Draw(batch);
        endTurn.Draw(batch);

        player.Draw(batch);
        enemy.Draw(batch);

        hand.Draw(batch);

        batch.end();

        // ##### Draw Polygons ##### //
        polygonBatch.begin();

        hand.Draw(polygonBatch);
        discarder.Draw(polygonBatch);

        polygonBatch.end();
    }

    @Override
    public void dispose() {

        batch.dispose();
        polygonBatch.dispose();

        content.Unload();
    }
}
