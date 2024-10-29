package com.badlogic.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.awt.*;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

public class TileManager {

    int[][] grid;
    Texture tileTexture;

    int width;
    int height;

    int gridSize = 40;

    public ArrayList<Building> buildings = new ArrayList<Building>();

    public TileManager(int width, int height){
        this.width = width;
        this.height = height;

        grid = new int[height][width]; //1 = unlocked, 0 = locked
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                grid[y][x] = 0;
            }
        }

        tileTexture = new Texture(Gdx.files.internal("tile.png"));
    }

    public void RenderTiles(SpriteBatch spriteBatch){
        for (int i = 0; i < buildings.size(); i++) {
            buildings.get(i).render(spriteBatch);
        }
    }

    public void addBuilding(Building building) {
        buildings.add(building);
    }

    public List<Building> getBuildings() {
        return buildings;
    }

    public void renderBuildings(SpriteBatch batch) {
        for (Building building : buildings) {
            building.render(batch);
        }
    }
    public void LockTile(int x, int y){
        grid[y][x] = 1;
    }
}
