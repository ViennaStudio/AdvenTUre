package com.viennastudio.adventure.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.viennastudio.adventure.util.Constants;

public class Player extends Actor {
    private final Sprite sprite;
    private final Vector2 velocity = new Vector2();
    private static final float MAX_SPEED = Constants.PLAYER_SPEED;
    private float increment = 0;
    private final AnimationMap animationMap;
    private TiledMapTileLayer collisionLayer;
    public String name = "Alexander Budik";
    private int ECTS = 0;
    private int mentalHealth = 100;

    public boolean setDirectionKeyPress(Constants.DIRECTION direction) {
        switch (direction) {
            case UP:
                velocity.y = MAX_SPEED;
                break;
            case DOWN:
                velocity.y = -MAX_SPEED;
                break;
            case LEFT:
                velocity.x = -MAX_SPEED;
                break;
            case RIGHT:
                velocity.x = MAX_SPEED;
                break;
            default:
                return false;
        }
        return true;
    }

    public boolean unsetDirectionKeyPress(Constants.DIRECTION direction) {
        switch (direction) {
            case LEFT:
            case RIGHT:
                velocity.x = 0;
                return true;
            case UP:
            case DOWN:
                velocity.y = 0;
                return true;
        }
        return false;
    }

    public Player(
            AnimationMap animationMap
    ) {
        sprite = new Sprite(animationMap.getFirstFrame());
        this.animationMap = animationMap;
        setTouchable(Touchable.enabled);
        addListener(new InputListener() {
            @Override
            public boolean keyTyped(InputEvent event, char character) {
                System.out.println("character = " + character);
                return true;
            }
        });
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
        batch.draw(
                animationMap.getFrame(
                        Gdx.graphics.getDeltaTime(),
                        velocity
                ),
                getX(),
                getY(),
                getOriginX(),
                getOriginY(),
                getWidth(),
                getHeight(),
                getScaleX(),
                getScaleY(),
                getRotation()
        );
    }

    @Override
    public void act(float deltaTime) {
        super.act(deltaTime);
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

            Rectangle hitbox = new Rectangle(getX(), getY(), getWidth(), getHeight());

            if (Intersector.overlaps(rectangle, hitbox)) {
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

    public int getECTS() {
        return ECTS;
    }

    public void setECTS(int ECTS) {
        this.ECTS = ECTS;
    }

    public int getMentalHealth() {
        return mentalHealth;
    }

    public void setMentalHealth(int mentalHealth) {
        this.mentalHealth = Math.min(Math.max(mentalHealth, 0), 100);
    }

    public TiledMapTileLayer getCollisionLayer() {
        return collisionLayer;
    }

    public void setCollisionLayer(TiledMapTileLayer collisionLayer) {
        this.collisionLayer = collisionLayer;
    }
}
