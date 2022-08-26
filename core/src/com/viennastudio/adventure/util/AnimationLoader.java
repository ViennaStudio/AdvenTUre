package com.viennastudio.adventure.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AnimationLoader {
    public static Animation<TextureRegion> load(String fileName) {
        return load(new Texture(Gdx.files.internal(fileName)));
    }

    public static Animation<TextureRegion> load(Texture texture) {
        TextureRegion[][] tmp = TextureRegion.split(
                texture,
                texture.getWidth() / Constants.ANIMATION_COLS,
                Constants.ANIMATION_SIZE
        );
        TextureRegion[] frames = new TextureRegion[Constants.ANIMATION_COLS * texture.getHeight() / Constants.ANIMATION_SIZE];
        int index = 0;
        for (int i = 0; i < texture.getHeight() / Constants.ANIMATION_SIZE; i++) {
            for (int j = 0; j < Constants.ANIMATION_COLS; j++) {
                frames[index++] = tmp[i][j];
            }
        }
        return new Animation<TextureRegion>(Constants.ANIMATION_INTERVAL, frames);
    }
}
