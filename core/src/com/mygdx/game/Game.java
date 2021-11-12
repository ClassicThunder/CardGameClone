package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.character.CharacterEntity;
import com.mygdx.game.character.CharacterType;
import com.mygdx.game.deckengine.cards.StrikeCard;
import com.mygdx.game.deckengine.hand.Hand;
import com.mygdx.game.texture.TextureFactory;
import com.mygdx.game.ui.StaticEntity;
import com.mygdx.game.utils.Timer;
import com.mygdx.game.utils.TimerFunction;

public class Game extends ApplicationAdapter {

    SpriteBatch batch;
    PolygonSpriteBatch polygonBatch;

    Texture defendImage;
    Texture strikeImage;
    Texture enemyImage;
    Texture playerImage;

    Texture red;

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

        defendImage = new Texture("Defend_R.png");
        strikeImage = new Texture("Strike_R.png");

        enemyImage = new Texture("Book_of_stabbing_pretty.png");
        playerImage = new Texture("Silent.png");

        red = TextureFactory.Generate(Color.RED);

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
                    hand.AddCard(new StrikeCard(strikeImage));
                }
            }
        });

    }

    private void createUI() {

        float pileSize = 64f;
        float pileBuffer = 15f + pileSize;

        drawPile = new StaticEntity(red,
                new Vector2(pileBuffer, pileBuffer),
                new Vector2(pileSize, pileSize));

        discardPile = new StaticEntity(red,
                new Vector2(Gdx.graphics.getWidth() - pileBuffer, pileBuffer),
                new Vector2(pileSize, pileSize));

        energy = new StaticEntity(red,
                new Vector2(pileBuffer * 2, pileBuffer * 2),
                new Vector2(pileSize, pileSize));

        endTurn = new StaticEntity(red,
                new Vector2(Gdx.graphics.getWidth() - pileBuffer * 2, pileBuffer * 2),
                new Vector2(pileSize, pileSize));
    }

    private void createHand() {

        float center = Gdx.graphics.getWidth() / 2f;

        float handWidth = Gdx.graphics.getWidth() - 400;
        hand = new Hand(center, handWidth, 0.35f);
    }

    private void createCharacters() {

        float centerX = Gdx.graphics.getWidth() / 2f;
        float centerY = Gdx.graphics.getHeight() / 2f;

        player = new CharacterEntity(CharacterType.PLAYER, playerImage,
                new Vector2(centerX - 300, centerY - 150));

        enemy = new CharacterEntity(CharacterType.ENEMY, enemyImage,
                new Vector2(centerX + 300, centerY - 150));
    }


    @Override
    public void render() {

        timer.Update();

        ScreenUtils.clear(0, 0, 0, 1);

        Vector2 mouse = new Vector2(
                Gdx.input.getX(),
                Gdx.graphics.getHeight() - Gdx.input.getY());

        hand.Update(mouse);

        // ##### Draw Sprites ##### //
        batch.begin();

        drawPile.Draw(batch);
        discardPile.Draw(batch);
        energy.Draw(batch);
        endTurn.Draw(batch);

        player.Draw(batch);
        enemy.Draw(batch);

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

        defendImage.dispose();
        strikeImage.dispose();
        enemyImage.dispose();
        playerImage.dispose();

        red.dispose();
    }
}
