package com.viennastudio.adventure.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.viennastudio.adventure.AdvenTUreGame;
import com.viennastudio.adventure.levels.KarlsplatzLevel;
import com.viennastudio.adventure.levels.StarterLevel;

public class MainMenuScreen implements Screen {

    final AdvenTUreGame game;

    OrthographicCamera camera;

    Texture logo;

    private Viewport viewport;

    public MainMenuScreen(final AdvenTUreGame game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        logo = new Texture(Gdx.files.internal("AdvenTUre.png"));
        System.out.println("logo.getHeight() = " + logo.getHeight());
        System.out.println("logo.getWidth() = " + logo.getWidth());

        viewport = new ScreenViewport(camera);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                game.setScreen(new StarterLevel(game));
                return true;
            }

            @Override
            public boolean keyDown(int keycode) {
                if (keycode == Input.Keys.ESCAPE) {
                    Gdx.app.exit();
                    return true;
                }
                game.setScreen(new KarlsplatzLevel(game));
                return true;
            }
        });
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.font.draw(game.batch, "Welcome to AdvenTUre game!!! ", 100, 150);
        game.font.draw(game.batch, "Tap anywhere to begin!", 100, 100);
        game.batch.draw(logo, 175, 200, 450, 300);
        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
    }
}