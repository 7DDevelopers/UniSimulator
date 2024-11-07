package com.badlogic.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages the tiles and buildings within the game world.
 * Handles the grid where buildings are placed, keeps track of which tiles are locked or unlocked,
 * and provides rendering for both tiles and buildings.
 */
public class TileManager {

    /**
     * 2D grid representing tile lock states. 1 indicates an unlocked tile, 0 indicates a locked tile.
     */
    int[][] grid;

    /**
     * Texture for the tile appearance.
     */
    Texture tileTexture;

    /**
     * Width of the tile grid.
     */
    int width;

    /**
     * Height of the tile grid.
     */
    int height;

    /**
     * Size of each tile in pixels.
     */
    int gridSize = 40;

    /**
     * List of all buildings managed by this TileManager instance.
     */
    public ArrayList<Building> buildings = new ArrayList<Building>();

    /**
     * Constructs a TileManager object with specified width and height for the tile grid.
     * Initializes the grid to locked (0) and sets the tile texture.
     *
     * @param width  The width of the tile grid.
     * @param height The height of the tile grid.
     */
    public TileManager(int width, int height) {
        this.width = width;
        this.height = height;

        // Initialize grid with locked tiles
        grid = new int[height][width];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                grid[y][x] = 0;
            }
        }

        // Load tile texture
        tileTexture = new Texture(Gdx.files.internal("tile.png"));
    }

    /**
     * Renders all tiles and buildings in the grid using the provided SpriteBatch.
     *
     * @param spriteBatch The SpriteBatch used for rendering.
     */
    public void RenderTiles(SpriteBatch spriteBatch) {
        for (int i = 0; i < buildings.size(); i++) {
            buildings.get(i).render(spriteBatch);
        }
    }

    /**
     * Adds a building to the list of buildings managed by this TileManager.
     *
     * @param building The building to be added.
     */
    public void addBuilding(Building building) {
        buildings.add(building);
    }

    /**
     * Retrieves the list of all buildings managed by this TileManager.
     *
     * @return A list of buildings.
     */
    public List<Building> getBuildings() {
        return buildings;
    }

    /**
     * Renders each building within the list of managed buildings using the provided SpriteBatch.
     *
     * @param batch The SpriteBatch used for rendering buildings.
     */
    public void renderBuildings(SpriteBatch batch) {
        for (Building building : buildings) {
            building.render(batch);
        }
    }

    /**
     * Locks a specific tile in the grid, marking it as unlocked (1).
     *
     * @param x The x-coordinate of the tile to unlock.
     * @param y The y-coordinate of the tile to unlock.
     */
    public void LockTile(int x, int y) {
        grid[y][x] = 1;
    }


    /**
     * Disposes of resources used by TileManager, such as textures.
     */
    public void dispose() {
        if (tileTexture != null) {
            tileTexture.dispose();
        }
        for (Building building : buildings) {
            building.dispose();  // Assuming Building class also has a dispose method
        }
    }

}
