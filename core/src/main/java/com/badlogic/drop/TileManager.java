package com.badlogic.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.awt.*;

public class TileManager {

    int[][] grid;
    Texture tileTexture;

    int width;
    int height;

    int gridSize = 40;

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
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if(grid[y][x] != 1) {
                    spriteBatch.begin();
                    spriteBatch.draw(tileTexture, x * gridSize - width, y * gridSize - height);
                    spriteBatch.end();
                }
            }
        }
    }

//    public void UnlockTile(int x, int y){
//        grid[y][x] = 1;
//    }
}
