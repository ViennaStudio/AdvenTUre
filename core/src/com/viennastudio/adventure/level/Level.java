package com.viennastudio.adventure.level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.viennastudio.adventure.AdvenTUreGame;
import com.viennastudio.adventure.Constants;
import com.viennastudio.adventure.MainMenuScreen;
import com.viennastudio.adventure.hud.PlayerStatisticsHUD;

import static com.viennastudio.adventure.Constants.WORLD_HEIGHT;
import static com.viennastudio.adventure.Constants.WORLD_WIDTH;

public abstract class Level implements Disposable, Screen {
    protected TiledMap map;
    protected OrthogonalTiledMapRenderer renderer;
    protected ShapeRenderer shapeRenderer;
    protected OrthographicCamera camera;
    protected Viewport viewport;
    protected final AdvenTUreGame game;

    private Sprite temporarySprite;
    protected SpriteBatch spriteBatch;
    protected PlayerStatisticsHUD playerStatisticsHUD;

    protected LevelConfig levelConfig;

    public Level(AdvenTUreGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        map = new TmxMapLoader().load(levelConfig.mapFileName);

        renderer = new OrthogonalTiledMapRenderer(map, 1f / Constants.TILE_SIZE, game.batch);

        camera = new OrthographicCamera(WORLD_WIDTH, WORLD_HEIGHT);
        viewport = new ExtendViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);

        renderer.setView(camera);

        game.player.setCollisionLayer((TiledMapTileLayer) map.getLayers().get(levelConfig.collisionLayer));
        Gdx.input.setInputProcessor(game.player);

        game.player.setSize(0.9f, 0.9f);
        game.player.setX(levelConfig.startCoordinates.x);
        game.player.setY(levelConfig.startCoordinates.y);


        spriteBatch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();

        //PlayerHud Creation
        playerStatisticsHUD = new PlayerStatisticsHUD(game.player, game.font, spriteBatch, shapeRenderer);
    }

    @Override
    public void render(float delta) {
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        Gdx.gl.glClearColor(.25f, .25f, .25f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.setView(camera);
        renderer.render(levelConfig.floorLayers);

        game.batch.begin();
        game.batch.setProjectionMatrix(camera.combined);
        game.player.draw(renderer.getBatch());
        game.batch.end();

        renderer.render(levelConfig.skyLayers);

        //ShapeRenderer for HUD Graphics
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        playerStatisticsHUD.drawHudGraphics();
        shapeRenderer.end();

        //Sprite Batch Rendering for HUD Text
        spriteBatch.begin();
        playerStatisticsHUD.drawHudText();
        spriteBatch.end();

        Vector3 position = this.camera.position;
        position.x += (game.player.getX() - position.x) * Constants.CAMERA_SPEED * delta;
        position.y += (game.player.getY() - position.y) * Constants.CAMERA_SPEED * delta;

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            game.setScreen(new MainMenuScreen(game));
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }


    @Override
    public void dispose() {
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
    public void hide() {

    }
}
