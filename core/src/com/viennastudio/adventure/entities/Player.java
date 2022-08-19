package com.viennastudio.adventure.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.viennastudio.adventure.Constants;

public class Player extends Sprite implements InputProcessor {
    private Vector2 velocity = new Vector2();
    private static final float MAX_SPEED = Constants.PLAYER_SPEED;
    private float increment, animationTime = 0;
    private TiledMapTileLayer collisionLayer;
    private final String blockedKey = "blocked";
    public String name = "Alexander Budik";
    private float XP = 0;
    private int ECTS = 0;
    private int mentalHealth = 100;

    public Player(Sprite sprite, TiledMapTileLayer collisionLayer) {
        super(sprite);
        this.collisionLayer = collisionLayer;
    }

    @Override
    public void draw(Batch batch) {
        update(Gdx.graphics.getDeltaTime());
        super.draw(batch);
    }

    public int getMentalHealth() {
        return mentalHealth;
    }

    private void update(float deltaTime) {
        // clamp velocity
        if (velocity.y > MAX_SPEED) velocity.y = MAX_SPEED;
        else if (velocity.y < -MAX_SPEED) velocity.y = -MAX_SPEED;

        // save old position
        float oldX = getX(), oldY = getY();
        boolean collisionX = false, collisionY = false;

        // move on x
        setX(getX() + velocity.x * deltaTime);

        // calculate the increment for step in #collidesLeft() and #collidesRight()
        increment = collisionLayer.getTileWidth();
        increment = getWidth() < increment ? getWidth() / 2 : increment / 2;

        if (velocity.x < 0) // going left
            collisionX = collidesLeft();
        else if (velocity.x > 0) // going right
            collisionX = collidesRight();

        // react to x collision
        if (collisionX) {
            setX(oldX);
        }

        // move on y
        setY(getY() + velocity.y * deltaTime);

        // calculate the increment for step in #collidesBottom() and #collidesTop()
        increment = collisionLayer.getTileHeight();
        increment = getHeight() < increment ? getHeight() / 2 : increment / 2;

        if (velocity.y < 0) // going down
            collisionY = collidesBottom();
        else if (velocity.y > 0) // going up
            collisionY = collidesTop();

        // react to y collision
        if (collisionY) {
            setY(oldY);
        }
    }

    private boolean isCellBlocked(float x, float y) {
        int tileX = (int) x;
        int tileY = (int) y;
        TiledMapTileLayer.Cell cell = collisionLayer.getCell(tileX, tileY);
        if (cell == null) return false;
        if (cell.getTile() == null) return false;
        if (cell.getTile().getObjects().getCount() == 0) return false;
        for (RectangleMapObject rectangleObject : cell.getTile().getObjects().getByType(RectangleMapObject.class)) {

            Rectangle rectangle = new Rectangle(rectangleObject.getRectangle());

            rectangle.x /= collisionLayer.getTileWidth();
            rectangle.y /= collisionLayer.getTileHeight();
            rectangle.x += tileX;
            rectangle.y += tileY;
            rectangle.width /= collisionLayer.getTileWidth();
            rectangle.height /= collisionLayer.getTileHeight();

            if (Intersector.overlaps(rectangle, this.getBoundingRectangle())) {
                return true;
            }
        }
        return false;
    }

    public boolean collidesRight() {
        for (float step = 0; step <= getHeight(); step += increment)
            if (isCellBlocked(getX() + getWidth(), getY() + step)) return true;
        return false;
    }

    public boolean collidesLeft() {
        for (float step = 0; step <= getHeight(); step += increment)
            if (isCellBlocked(getX(), getY() + step)) return true;
        return false;
    }

    public boolean collidesTop() {
        for (float step = 0; step <= getWidth(); step += increment)
            if (isCellBlocked(getX() + step, getY() + getHeight())) return true;
        return false;

    }

    public boolean collidesBottom() {
        for (float step = 0; step <= getWidth(); step += increment)
            if (isCellBlocked(getX() + step, getY())) return true;
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.W:
                velocity.y = MAX_SPEED;
                animationTime = 0;
                break;
            case Input.Keys.S:
                velocity.y = -MAX_SPEED;
                animationTime = 0;
                break;
            case Input.Keys.A:
                velocity.x = -MAX_SPEED;
                animationTime = 0;
                break;
            case Input.Keys.D:
                velocity.x = MAX_SPEED;
                animationTime = 0;
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.A:
            case Input.Keys.D:
                velocity.x = 0;
                animationTime = 0;
                break;
            case Input.Keys.W:
            case Input.Keys.S:
                velocity.y = 0;
                animationTime = 0;
        }
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

    public float getXP() {
        return XP;
    }

    public void setXP(float XP) {
        this.XP = XP;
    }

    public int getECTS() {
        return ECTS;
    }

    public void setECTS(int ECTS) {
        this.ECTS = ECTS;
    }
}
