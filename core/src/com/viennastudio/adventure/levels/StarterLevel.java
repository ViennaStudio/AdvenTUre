package com.viennastudio.adventure.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.viennastudio.adventure.AdvenTUreGame;
import com.viennastudio.adventure.entities.StaticMovingSprite;
import com.viennastudio.adventure.level.Level;
import com.viennastudio.adventure.level.LevelConfig;

import static com.viennastudio.adventure.util.Constants.TRAIN_SPEED;

public class StarterLevel extends Level {

    private Sprite[] tracks;
    private float currentX = 0;
    private StaticMovingSprite staticMovingSprite;
    private static final LevelConfig levelConfig = new LevelConfig(
            new int[]{0, 1},
            new int[]{},
            1,
            new Vector2(7, 2),
            "tiles/Ubahn.tmx"
    );

    public StarterLevel(AdvenTUreGame game) {
        super(game, levelConfig);
    }

    @Override
    public void show() {
        super.show();
        Sprite tmp = new Sprite(new Texture(Gdx.files.internal("tiles/ubahn-assets/schienen.png")));
        tmp.setSize(32, 16);
        tmp.setY(-.5f);

        tracks = new Sprite[10];

        for (int i = 0; i < tracks.length; i++) {
            tracks[i] = new Sprite(tmp);
        }

        setTrackPosition();

        staticMovingSprite = new StaticMovingSprite(new Sprite(new Texture(Gdx.files.internal("tiles/ubahn-assets/bridge.png"))), -16, 304, 30, -2);
        staticMovingSprite.setSize(3, 10);
    }

    private void setTrackPosition() {
        float j = currentX - 16;

        for (Sprite track : tracks) {
            track.setX(j);
            j += 32;
        }
        currentX -= TRAIN_SPEED * Gdx.graphics.getDeltaTime();
        currentX %= 32;
    }

    @Override
    protected void beforeRender() {
        game.batch.begin();
        for (Sprite track : tracks) {
            track.draw(game.batch);
        }
        game.batch.end();
        setTrackPosition();
    }

    @Override
    protected void afterRender() {
        staticMovingSprite.update();
        game.batch.begin();
        staticMovingSprite.draw(game.batch);
        game.batch.end();
    }
}
