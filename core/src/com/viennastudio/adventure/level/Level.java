package com.viennastudio.adventure.level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.viennastudio.adventure.AdvenTUreGame;
import com.viennastudio.adventure.util.Constants;
import com.viennastudio.adventure.screens.MainMenuScreen;
import com.viennastudio.adventure.hud.PlayerStatisticsHUD;
import com.viennastudio.adventure.util.GameRelated;
import com.viennastudio.adventure.util.KeyMap;

import static com.viennastudio.adventure.util.Constants.WORLD_HEIGHT;
import static com.viennastudio.adventure.util.Constants.WORLD_WIDTH;
import static com.viennastudio.adventure.util.KeyMap.PAUSE_KEY;

public abstract class Level extends GameRelated implements Disposable, Screen {
    protected TiledMap map;
    protected OrthogonalTiledMapRenderer renderer;
    protected ShapeRenderer shapeRenderer;
    protected OrthographicCamera camera;
    protected OrthographicCamera UICamera;
    protected SpriteBatch spriteBatch;
    protected PlayerStatisticsHUD playerStatisticsHUD;

    protected final LevelConfig levelConfig;

    public Level(AdvenTUreGame game, LevelConfig levelConfig) {
        super(game);
        this.levelConfig = levelConfig;

        camera = new OrthographicCamera(WORLD_WIDTH, WORLD_HEIGHT);
        UICamera = new OrthographicCamera(WORLD_WIDTH, WORLD_HEIGHT);
        game.gameViewport = new ExtendViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        game.UIViewport = new ScreenViewport(UICamera);

        game.gameStage = new Stage(game.gameViewport);
        game.UIStage = new Stage(game.UIViewport);
        Gdx.input.setInputProcessor(new InputMultiplexer(game.UIStage, game.gameStage));
    }

    @Override
    public void show() {
        map = new TmxMapLoader().load(levelConfig.mapFileName);

        renderer = new OrthogonalTiledMapRenderer(map, 1f / Constants.TILE_SIZE, game.gameBatch);
        game.gameStage.addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                return game.player.setDirectionKeyPress(KeyMap.directionForKey(keycode));
            }

            @Override
            public boolean keyUp(InputEvent event, int keycode) {
                return game.player.unsetDirectionKeyPress(KeyMap.directionForKey(keycode));
            }
        });
        game.gameStage.addActor(game.player);

        renderer.setView(camera);

        game.player.setCollisionLayer((TiledMapTileLayer) map.getLayers().get(levelConfig.collisionLayer));

        game.player.setSize(0.8f, 0.8f);
        game.player.setX(levelConfig.startCoordinates.x);
        game.player.setY(levelConfig.startCoordinates.y);


        spriteBatch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();

        //PlayerHud Creation
        playerStatisticsHUD = new PlayerStatisticsHUD(game, game.player, game.font, spriteBatch, shapeRenderer);
        game.UIStage.addActor(playerStatisticsHUD);
        afterShow();
    }

    @Override
    public void render(float delta) {
        camera.update();
        game.gameBatch.setProjectionMatrix(camera.combined);

        Gdx.gl.glClearColor(.25f, .25f, .25f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        beforeRender();

        renderer.setView(camera);
        renderer.render(levelConfig.floorLayers);

        act(delta);

        game.gameStage.draw();

        renderer.render(levelConfig.skyLayers);

        afterRender();

        //ShapeRenderer for HUD Graphics
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        playerStatisticsHUD.drawHudGraphics();
        shapeRenderer.end();

        //Sprite Batch Rendering for HUD Text
        spriteBatch.begin();
        playerStatisticsHUD.drawHudText();
        spriteBatch.end();

        game.UIStage.draw();

        Vector3 position = this.camera.position;
        position.x += (game.player.getX() - position.x) * Constants.CAMERA_SPEED * delta;
        position.y += (game.player.getY() - position.y) * Constants.CAMERA_SPEED * delta;

        if (Gdx.input.isKeyPressed(PAUSE_KEY)) {
            game.setScreen(new MainMenuScreen(game));
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        map.dispose();
        renderer.dispose();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);

        playerStatisticsHUD.setSize(game.UIStage.getWidth(), 30);
        playerStatisticsHUD.setPosition(0, game.UIStage.getHeight(), Align.topLeft);
    }

    @Override
    public void hide() {

    }

    protected void afterShow() {}
    protected void beforeRender() {}
    protected void afterRender() {}
}
