package com.viennastudio.adventure.levels;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.math.Vector2;
import com.viennastudio.adventure.AdvenTUreGame;
import com.viennastudio.adventure.level.Level;
import com.viennastudio.adventure.level.LevelConfig;

public class KarlsplatzLevel extends Level {

    public KarlsplatzLevel(AdvenTUreGame game) {
        super(game, new LevelConfig(
                new int[]{0, 1},
                new int[]{2, 3},
                1,
                new Vector2(17, 11),
                "tiles/Karlsplatz.tmx"
        ));
    }
}
