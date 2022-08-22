package com.viennastudio.adventure;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.viennastudio.adventure.entities.AnimationMap;
import com.viennastudio.adventure.entities.Player;
import com.viennastudio.adventure.util.AnimationLoader;


public class AdvenTUreGame extends Game {

	public SpriteBatch batch;
	public BitmapFont font;
	public Player player;

	private Texture playerStillTexture;
	private Texture playerUpTexture;
	private Texture playerDownTexture;
	private Texture playerLeftTexture;
	private Texture playerRightTexture;

	public void create() {
		batch = new SpriteBatch();
		font = new BitmapFont(Gdx.files.internal("fonts/Silkscreen.fnt")); // use Silkscreen.fnt for the font

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
		batch.dispose();
		font.dispose();
	}
}