package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.cards.Card;
import com.mygdx.game.texture.TextureFactory;
import com.mygdx.game.hand.Hand;
import com.mygdx.game.ui.StaticEntity;

public class MyGdxGame extends ApplicationAdapter {

	SpriteBatch batch;
	PolygonSpriteBatch polygonBatch;

	Texture defendImage;
	Texture strikeImage;

	Texture red;
	Texture purple;
	Texture yellow;

	Hand hand;

	StaticEntity drawPile;
	StaticEntity discardPile;

	StaticEntity energy;
	StaticEntity endTurn;

	@Override
	public void create () {

		batch = new SpriteBatch();
		polygonBatch = new PolygonSpriteBatch();

		defendImage = new Texture("Defend_R.png");
		strikeImage = new Texture("Strike_R.png");

		red = TextureFactory.Generate(Color.RED);
		purple = TextureFactory.Generate(Color.PURPLE);
		yellow = TextureFactory.Generate(Color.YELLOW);

		float center = Gdx.graphics.getWidth() / 2f;

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

		float handWidth = Gdx.graphics.getWidth() - 400;
		hand = new Hand(center, handWidth, 0.35f);

		hand.AddCard(new Card(defendImage));
		hand.AddCard(new Card(defendImage));
		hand.AddCard(new Card(defendImage));
		hand.AddCard(new Card(defendImage));
		hand.AddCard(new Card(defendImage));
		hand.AddCard(new Card(defendImage));

		hand.AddCard(new Card(strikeImage));
		hand.AddCard(new Card(strikeImage));
		hand.AddCard(new Card(strikeImage));
		hand.AddCard(new Card(strikeImage));
		hand.AddCard(new Card(strikeImage));
	}

	@Override
	public void render () {

		ScreenUtils.clear(0, 0, 0, 1);

		Vector2 mouse = new Vector2(
				Gdx.input.getX(),
				Gdx.graphics.getHeight() - Gdx.input.getY());

		hand.Update(mouse);

		// #################
		batch.begin();

		drawPile.Draw(batch);
		discardPile.Draw(batch);
		energy.Draw(batch);
		endTurn.Draw(batch);

		hand.Draw(batch, purple);

		batch.end();

		// #################
		polygonBatch.begin();

		hand.Draw(polygonBatch);

		polygonBatch.end();


		// #################
		batch.begin();

		batch.draw(yellow, mouse.x - 4,  mouse.y - 4, 8, 8);

		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();

		defendImage.dispose();
		strikeImage.dispose();
		yellow.dispose();
		red.dispose();
		purple.dispose();
	}
}
