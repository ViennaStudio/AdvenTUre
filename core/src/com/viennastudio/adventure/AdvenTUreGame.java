package com.viennastudio.adventure;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.viennastudio.adventure.entities.AnimationMap;
import com.viennastudio.adventure.entities.Player;
import com.viennastudio.adventure.screens.MainMenuScreen;
import com.viennastudio.adventure.screens.SplashWorker;
import com.viennastudio.adventure.util.AnimationLoader;


public class AdvenTUreGame extends Game {

    public SpriteBatch gameBatch;
    public SpriteBatch UIBatch;
    public Stage gameStage;
    public Stage UIStage;
    public Viewport gameViewport;
    public Viewport UIViewport;

    public Skin skin;
    public BitmapFont font;
    public Player player;

    private Texture playerStillTexture;
    private Texture playerUpTexture;
    private Texture playerDownTexture;
    private Texture playerLeftTexture;
    private Texture playerRightTexture;
    private SplashWorker splashWorker;

    public void create() {
        //Close SplashScreen
        splashWorker.closeSplashScreen();

        gameBatch = new SpriteBatch();
        UIBatch = new SpriteBatch();
        gameStage = new Stage();
        UIStage = new Stage();

        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(gameStage);
        multiplexer.addProcessor(UIStage);
        Gdx.input.setInputProcessor(multiplexer);

        font = new BitmapFont(Gdx.files.internal("fonts/Silkscreen.fnt")); // use Silkscreen.fnt for the font

        skin = new Skin(Gdx.files.internal("skin/adventure.json"));

        playerStillTexture = new Texture("player/PlayerStill.png");
        playerDownTexture = new Texture("player/PlayerDown.png");
        playerUpTexture = new Texture("player/PlayerUp.png");
        playerLeftTexture = new Texture("player/PlayerLeft.png");
        playerRightTexture = new Texture("player/PlayerRight.png");

        Animation<TextureRegion> playerStillAnimation = AnimationLoader.load(playerStillTexture);
        Animation<TextureRegion> playerUpAnimation = AnimationLoader.load(playerUpTexture);
        Animation<TextureRegion> playerLeftAnimation = AnimationLoader.load(playerLeftTexture);
        Animation<TextureRegion> playerDownAnimation = AnimationLoader.load(playerDownTexture);
        Animation<TextureRegion> playerRightAnimation = AnimationLoader.load(playerRightTexture);

        AnimationMap animationMap = new AnimationMap(
                playerStillAnimation,
                playerLeftAnimation,
                playerRightAnimation,
                playerUpAnimation,
                playerDownAnimation
        );

        player = new Player(animationMap);

        setScreen(new MainMenuScreen(this));
    }

    public void render() {
        super.render(); // important!
    }

    public void dispose() {
        playerStillTexture.dispose();
        playerLeftTexture.dispose();
        playerRightTexture.dispose();
        playerUpTexture.dispose();
        playerDownTexture.dispose();
        if (gameBatch != null) gameBatch.dispose();
        if (UIBatch != null) UIBatch.dispose();
        font.dispose();
    }

    public SplashWorker getSplashWorker() {
        return splashWorker;
    }

    public void setSplashWorker(SplashWorker splashWorker) {
        this.splashWorker = splashWorker;
    }

}