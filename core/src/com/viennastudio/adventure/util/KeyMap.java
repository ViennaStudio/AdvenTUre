package com.viennastudio.adventure.util;

import com.badlogic.gdx.Input;

public class KeyMap {

    public static final int PAUSE_KEY = Input.Keys.ESCAPE;

    public static Constants.DIRECTION directionForKey(int keyCode) {
        switch (keyCode) {
            case Input.Keys.W:
                return Constants.DIRECTION.UP;
            case Input.Keys.A:
                return Constants.DIRECTION.LEFT;
            case Input.Keys.S:
                return Constants.DIRECTION.DOWN;
            case Input.Keys.D:
                return Constants.DIRECTION.RIGHT;
        }
        return Constants.DIRECTION.NULL;
    }
}