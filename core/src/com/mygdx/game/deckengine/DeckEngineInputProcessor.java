package com.mygdx.game.deckengine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.character.CharacterEntity;
import com.mygdx.game.deckengine.card.Card;

public class DeckEngineInputProcessor {

    private final InputProcessor ip;

    public DeckEngineInputProcessor(final DeckEngine deckEngine,
                                    final CharacterEntity player,
                                    final CharacterEntity enemy) {

        ip = new InputProcessor() {

            Card grabbedCard = null;

            @Override
            public boolean keyDown(int keycode) {
                return false;
            }

            @Override
            public boolean keyUp(int keycode) {

                if (keycode == Input.Keys.ENTER) {
                    deckEngine.requestEndTurn();
                    return true;
                }

                return false;
            }

            @Override
            public boolean keyTyped(char character) {

                return false;
            }

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {

                Vector2 mouse = new Vector2(screenX, Gdx.graphics.getHeight() - screenY);

                grabbedCard = deckEngine.hand.GrabCard(mouse);

                return true;
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {

                Vector2 mouse = new Vector2(screenX, Gdx.graphics.getHeight() - screenY);

                if (grabbedCard != null) {

                    if (player.ContainsMouse(mouse) && player.CanApplyCard(grabbedCard)) {
                        player.ApplyCard(grabbedCard); // Card is Played
                        deckEngine.hand.DiscardCard(deckEngine.discarder, grabbedCard);
                    }

                    if (enemy.ContainsMouse(mouse) && enemy.CanApplyCard(grabbedCard)) {
                        enemy.ApplyCard(grabbedCard); // Card is Played
                        deckEngine.hand.DiscardCard(deckEngine.discarder, grabbedCard);
                    }
                }

                deckEngine.hand.ResetCards();

                return true;
            }

            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {

                Vector2 mouse = new Vector2(screenX, Gdx.graphics.getHeight() - screenY);

                if (grabbedCard != null) {

                    grabbedCard.SetDragPosition(mouse);

                    if (player.ContainsMouse(mouse)) {
                        if (player.CanApplyCard(grabbedCard)) {
                            grabbedCard.SetIsPlayable(true);
                        } else {
                            grabbedCard.SetIsNotPlayable(true);
                        }
                    } else if (enemy.ContainsMouse(mouse)) {
                        if (enemy.CanApplyCard(grabbedCard)) {
                            grabbedCard.SetIsPlayable(true);
                        } else {
                            grabbedCard.SetIsNotPlayable(true);
                        }
                    } else {
                        grabbedCard.SetIsPlayable(false);
                        grabbedCard.SetIsNotPlayable(false);
                    }
                }

                return true;
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
