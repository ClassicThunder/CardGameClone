package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.cards.Card;
import com.mygdx.game.character.CharacterEntity;
import com.mygdx.game.hand.Hand;

import java.util.List;

public class GameInputProcessor {

    private InputProcessor ip;

    public GameInputProcessor(final Hand hand, final CharacterEntity player, final CharacterEntity enemy) {

        this.ip = new InputProcessor() {

            @Override
            public boolean keyDown(int keycode) {
                return false;
            }

            @Override
            public boolean keyUp(int keycode) {
                return false;
            }

            @Override
            public boolean keyTyped(char character) {
                return false;
            }

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {

                Vector2 mouse = new Vector2(screenX, Gdx.graphics.getHeight() - screenY);

                List<Card> cards = hand.GetCards();

                if (button == Input.Buttons.LEFT) {

                    for (int x = cards.size() - 1; x >= 0; x--) {
                        Card currentCard = cards.get(x);
                        if (currentCard.ContainsMouse(mouse)) {

                            currentCard.SetGrabbed(true);
                            currentCard.SetDragPosition(mouse);

                            return true;
                        }
                    }
                }

                return false;
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {

                Vector2 mouse = new Vector2(screenX, Gdx.graphics.getHeight() - screenY);

                for (Card card : hand.GetCards()) {

                    if (card.GetIsGrabbed()) {

                        if (player.ContainsMouse(mouse)) {
                            player.ApplyCard(card);;
                        }

                        if (enemy.ContainsMouse(mouse)) {
                            enemy.ApplyCard(card);;
                        }
                    }

                    card.Reset();
                }

                return false;
            }

            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {

                Vector2 mouse = new Vector2(screenX, Gdx.graphics.getHeight() - screenY);

                for (Card card : hand.GetCards()) {
                    card.SetDragPosition(mouse);
                }

                return false;
            }

            @Override
            public boolean mouseMoved(int screenX, int screenY) {
                return false;
            }

            @Override
            public boolean scrolled(float amountX, float amountY) {
                return false;
            }
        };
    }

    public void Activate() {
        Gdx.input.setInputProcessor(ip);
    }

    public void Deactivate() {
        Gdx.input.setInputProcessor(null);
    }
}
