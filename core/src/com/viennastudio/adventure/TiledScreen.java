package com.viennastudio.adventure;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.viennastudio.adventure.entities.Player;
import com.viennastudio.adventure.hud.PlayerStatisticsHUD;

import static com.viennastudio.adventure.Constants.WORLD_HEIGHT;
import static com.viennastudio.adventure.Constants.WORLD_WIDTH;

public class TiledScreen implements Screen {

    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private ShapeRenderer shapeRenderer;
    private OrthographicCamera camera;
    private Viewport viewport;
    private final AdvenTUreGame game;
    private Player player;

    private Sprite temporarySprite;
    private SpriteBatch spriteBatch;
    private PlayerStatisticsHUD playerStatisticsHUD;

    private static final int[] floorLayers = new int[]{0, 1};
    private static final int[] skyLayers = new int[]{2, 3};
    private static final int collisionLayer = 1;
    private static final String mapFileName = "tiles/Karlsplatz.tmx";


    public TiledScreen(AdvenTUreGame game) {
        this.game = game;
    }


    @Override
    public void show() {
        map = new TmxMapLoader().load(mapFileName);

        renderer = new OrthogonalTiledMapRenderer(map, 1 / 16f, game.batch);

        camera = new OrthographicCamera(WORLD_WIDTH, WORLD_HEIGHT);
        viewport = new ExtendViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);

        renderer.setView(camera);


        player = new Player(new Sprite(new Texture("badlogic.jpg")), (TiledMapTileLayer) map.getLayers().get(collisionLayer));
        Gdx.input.setInputProcessor(player);

        player.setSize(0.9f, 0.9f);
        player.setX(17);
        player.setY(11);
        spriteBatch = new SpriteBatch();

        playerStatisticsHUD = new PlayerStatisticsHUD();

        shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void render(float delta) {
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        Gdx.gl.glClearColor(.25f, .25f, .25f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.setView(camera);
        renderer.render(floorLayers);

        game.batch.begin();
        game.batch.setProjectionMatrix(camera.combined);
        player.draw(renderer.getBatch());
        game.batch.end();

        renderer.render(skyLayers);

        spriteBatch.begin();
        game.font.draw(spriteBatch, player.name, 10, 15);
        spriteBatch.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        playerStatisticsHUD.drawInside(shapeRenderer);
        shapeRenderer.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        playerStatisticsHUD.drawOutside(shapeRenderer);
        shapeRenderer.end();


        Vector3 position = this.camera.position;
        position.x += (player.getX() - position.x) * Constants.CAMERA_SPEED * delta;
        position.y += (player.getY() - position.y) * Constants.CAMERA_SPEED * delta;

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            game.setScreen(new MainMenuScreen(game));
        }
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
    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
        player.getTexture().dispose();
    }
}