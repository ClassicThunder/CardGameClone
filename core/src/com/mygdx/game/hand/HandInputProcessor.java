package com.mygdx.game.hand;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.cards.Card;

import java.util.List;

public class HandInputProcessor {

    public HandInputProcessor(final Hand hand) {

        Gdx.input.setInputProcessor(new InputProcessor() {

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

                for (Card card: hand.GetCards()) {
                    card.Reset();
                }

                return false;
            }

            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {

                Vector2 mouse = new Vector2(screenX, Gdx.graphics.getHeight() - screenY);

                for (Card card: hand.GetCards()) {
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
        });
    }
}
