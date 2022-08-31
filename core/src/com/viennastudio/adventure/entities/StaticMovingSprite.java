package com.viennastudio.adventure.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;

import static com.viennastudio.adventure.util.Constants.TRAIN_SPEED;

public class StaticMovingSprite extends Sprite {
    private final float minX;
    private final float maxX;
    public StaticMovingSprite(Sprite sprite, float minX, float maxX, float x, float y) {
        super(sprite);
        this.minX = minX;
        this.maxX = maxX;

        setPosition(x, y);
    }

    public void update() {
        float x = getX();
        x -= TRAIN_SPEED * Gdx.graphics.getDeltaTime();
        setX(x < minX ? maxX : x);
    }
}
