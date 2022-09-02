package com.viennastudio.adventure.util;

import com.badlogic.gdx.utils.Disposable;
import com.viennastudio.adventure.AdvenTUreGame;

public abstract class GameRelated implements Disposable {
    protected final AdvenTUreGame game;

    protected GameRelated(AdvenTUreGame game) {
        this.game = game;
    }

    public void resize(int width, int height) {
        game.gameStage.getViewport().update(width, height);
        game.UIStage.getViewport().update(width, height, true);
    }

    protected void act(float deltaTime) {
        game.gameStage.act(deltaTime);
        game.UIStage.act(deltaTime);
    }

    protected void draw() {
        game.gameStage.draw();
        game.UIStage.draw();
    }

    @Override
    public void dispose() {
        game.dispose();
    }
}
