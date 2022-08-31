package com.viennastudio.adventure.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.viennastudio.adventure.AdvenTUreGame;
import com.viennastudio.adventure.levels.KarlsplatzLevel;
import com.viennastudio.adventure.levels.StarterLevel;
import com.viennastudio.adventure.util.GameRelated;

import static com.viennastudio.adventure.util.KeyMap.PAUSE_KEY;


public class MainMenuScreen extends GameRelated implements Screen {
    OrthographicCamera camera;

    Texture logo;

    public MainMenuScreen(final AdvenTUreGame game) {
        super(game);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        logo = new Texture(Gdx.files.internal("AdvenTUre.png"));

        game.gameViewport = new ScreenViewport(camera);
        game.UIViewport = new ScreenViewport(camera);

        game.gameStage = new Stage(game.gameViewport);
        game.UIStage = new Stage(game.UIViewport);

        Gdx.input.setInputProcessor(new InputMultiplexer(game.UIStage, game.gameStage));
    }

    @Override
    public void show() {
        final Button exitButton = new TextButton("* Exit", game.skin, "default");
        exitButton.setSize(300, 60);
        exitButton.setPosition(Gdx.graphics.getWidth() /2f - 10, 200, Align.bottomRight);
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (event.getTarget().isDescendantOf(exitButton)) Gdx.app.exit();
            }
        });
        game.UIStage.addActor(exitButton);

        final Button playButton = new TextButton("Play >", game.skin, "default");
        playButton.setOrigin(Align.center);
        playButton.setSize(
                300,
                60
        );
        playButton.setPosition(Gdx.graphics.getWidth() / 2f + 10, 200);
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (event.getTarget().isDescendantOf(playButton)) game.setScreen(new StarterLevel(game));
            }
        });
        game.UIStage.addActor(playButton);

        Image image = new Image(logo);
        image.setSize(450, 300);
        image.setPosition(
                Gdx.graphics.getWidth() / 2f,
                Gdx.graphics.getHeight() * 3 / 4f,
                Align.center
        );
        game.UIStage.addActor(image);

        Label.LabelStyle labelStyle = new Label.LabelStyle(game.font, Color.WHITE);
        final Label label = new Label("Welcome to AdvenTUre game!!!\nTap anywhere to begin", labelStyle);
        label.setAlignment(Align.center);
        label.setPosition(Gdx.graphics.getWidth() / 2f, 350, Align.center);
        game.UIStage.addActor(label);


        game.UIStage.addListener(new InputListener() {

            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == PAUSE_KEY) {
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
        act(delta);
        ScreenUtils.clear(0, 0, 0.2f, 1);

        camera.update();
        game.UIBatch.setProjectionMatrix(camera.combined);

        draw();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
//        Gdx.input.setInputProcessor(null);
    }
}