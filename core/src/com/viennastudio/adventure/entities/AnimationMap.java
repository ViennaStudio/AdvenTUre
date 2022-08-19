package com.viennastudio.adventure.entities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class AnimationMap {
    float time = 0;
    Animation<TextureRegion> still;
    Animation<TextureRegion> left;
    Animation<TextureRegion> right;
    Animation<TextureRegion> up;
    Animation<TextureRegion> down;

    public AnimationMap(Animation<TextureRegion> still, Animation<TextureRegion> left, Animation<TextureRegion> right, Animation<TextureRegion> up, Animation<TextureRegion> down) {
        this.still = still;
        this.left = left;
        this.right = right;
        this.up = up;
        this.down = down;
    }

    public TextureRegion getFrame(float deltaTime, Vector2 velocity) {
        time += deltaTime;

        if (velocity.isZero()) {
            return still.getKeyFrame(time, true);
        } else if (Math.abs(velocity.x) >= Math.abs(velocity.y)) {
            if (velocity.x > 0) {
                return right.getKeyFrame(time, true);
            } else {
                return left.getKeyFrame(time, true);
            }
        } else {
            if (velocity.y > 0) {
                return up.getKeyFrame(time, true);
            } else {
                return down.getKeyFrame(time, true);
            }
        }
    }

    public TextureRegion getFirstFrame() {
        return still.getKeyFrame(0);
    }
}
