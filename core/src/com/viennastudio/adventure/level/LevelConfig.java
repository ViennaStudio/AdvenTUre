package com.viennastudio.adventure.level;

import com.badlogic.gdx.math.Vector2;

public class LevelConfig {
    public final int[] floorLayers;
    public final int[] skyLayers;
    public final int collisionLayer;
    public final Vector2 startCoordinates;
    public final String mapFileName;

    public LevelConfig(int[] floorLayers, int[] skyLayers, int collisionLayer, Vector2 startCoordinates, String mapFileName) {
        this.floorLayers = floorLayers;
        this.skyLayers = skyLayers;
        this.collisionLayer = collisionLayer;
        this.startCoordinates = startCoordinates;
        this.mapFileName = mapFileName;
    }

    public int[] getFloorLayers() {
        return floorLayers;
    }

    public int[] getSkyLayers() {
        return skyLayers;
    }

    public int getCollisionLayer() {
        return collisionLayer;
    }

    public Vector2 getStartCoordinates() {
        return startCoordinates;
    }

    public String getMapFileName() {
        return mapFileName;
    }
}
