package com.badlogic.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.ArrayList;

/**
 * Manages user input for the game, including mouse and keyboard interactions.
 * Handles the placement of buildings, people creation, and tile locking based on user input.
 * The input manager is responsible for controlling camera zoom and updating the building selection state.
 */
public class InputManager implements InputProcessor {

    /**
     * The camera used to view the game world.
     */
    OrthographicCamera cam;

    /**
     * The viewport that adjusts the world rendering based on screen size.
     */
    FitViewport viewport;

    /**
     * The TileManager that handles the grid and building placement.
     */
    TileManager tileManager;

    /**
     * The current building number selected by the user for placement.
     */
    public int buildingNum = 0;

    /**
     * List of all people in the game world.
     */
    public ArrayList<Person> people = new ArrayList<Person>();

    /**
     * Menu that provides building options to the user.
     */
    BuildingMenu buildingMenu;

    /**
     * Counters for each type of building placed in the game world.
     */
    public int lectureCount = 0;
    public int accommodationCount = 0;
    public int restaurantCount = 0;
    public int gymCount = 0;
    public int labCount = 0;
    public int pathCount = 0;

    /**
     * Constructs an InputManager with the specified camera and viewport.
     *
     * @param viewport The viewport used for rendering the game world.
     * @param cam      The camera used to view the game world.
     */
    public InputManager(FitViewport viewport, OrthographicCamera cam) {
        this.cam = cam;
        this.viewport = viewport;
    }

    @Override
    public boolean keyDown(int i) {
        return false;
    }

    @Override
    public boolean keyUp(int i) {
        return false;
    }

    /**
     * Updates the currently selected building number based on the input button.
     *
     * @param newBuildingNum The new building number to set.
     */
    public void updateBuildingNum(int newBuildingNum) {
        buildingNum = newBuildingNum;
        System.out.println("Building number updated to: " + buildingNum);
    }

    @Override
    public boolean keyTyped(char c) {
        return false;
    }

    @Override
    public boolean touchDown(int i, int i1, int i2, int i3) {
        return false;
    }
    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        // Convert screen coordinates to world coordinates
        Vector3 clickPos = viewport.unproject(new Vector3(screenX, screenY, 0));

        // Calculate the grid coordinates based on the grid size
        int gridX = (int) Math.floor(clickPos.x / 40);
        int gridY = (int) Math.floor(clickPos.y / 40);

        // Clamp grid coordinates to prevent selecting tiles outside of the grid bounds
        gridX = Math.max(0, Math.min(gridX, tileManager.width - 1));
        gridY = Math.max(0, Math.min(gridY, tileManager.height - 1));

        // Debug output
        System.out.println("Screen click at: (" + screenX + ", " + screenY + ")");
        System.out.println("World coordinates: (" + clickPos.x + ", " + clickPos.y + ")");
        System.out.println("Clamped Grid coordinates: (" + gridX + ", " + gridY + ")");

        // Check if the tile is available for building placement
        if ((buildingNum != 6 && (tileManager.grid[gridY][gridX] != 1 &&
            tileManager.grid[gridY + 1][gridX + 1] != 1 &&
            tileManager.grid[gridY + 1][gridX] != 1 &&
            tileManager.grid[gridY][gridX + 1] != 1)) ||
            (buildingNum == 6 && (tileManager.grid[gridY][gridX] != 1))) {

            // Create and add the new building to the tile manager
            Building newBuilding = null;
            String buildingTexture = "";

            switch (buildingNum) {
                case 1:
                    buildingTexture = "lectureHall.png";
                    newBuilding = new Building(gridX * 40, gridY * 40, buildingTexture, "LECTUREHALL");
                    tileManager.addBuilding(newBuilding);
                    lectureCount++;
                    lockTile(buildingNum, new Vector3(gridX, gridY, 0));
                    break;
                case 2:
                    buildingTexture = "accommodation.png";
                    newBuilding = new Building(gridX * 40, gridY * 40, buildingTexture, "ACCOMMODATION");
                    tileManager.addBuilding(newBuilding);
                    accommodationCount++;
                    lockTile(buildingNum, new Vector3(gridX, gridY, 0));
                    break;
                case 3:
                    buildingTexture = "pub.png";
                    newBuilding = new Building(gridX * 40, gridY * 40, buildingTexture, "RESTAURANT");
                    tileManager.addBuilding(newBuilding);
                    restaurantCount++;
                    lockTile(buildingNum, new Vector3(gridX, gridY, 0));
                    break;
                case 4:
                    buildingTexture = "gym.png";
                    newBuilding = new Building(gridX * 40, gridY * 40, buildingTexture, "GYM");
                    tileManager.addBuilding(newBuilding);
                    gymCount++;
                    break;
                case 5:
                    buildingTexture = "lab.png";
                    newBuilding = new Building(gridX * 40, gridY * 40, buildingTexture, "LAB");
                    tileManager.addBuilding(newBuilding);
                    labCount++;
                    lockTile(buildingNum, new Vector3(gridX, gridY, 0));
                    break;
                case 6:
                    buildingTexture = "path.png";
                    newBuilding = new Building(gridX * 40, gridY * 40, buildingTexture, "PATH");
                    tileManager.addBuilding(newBuilding);
                    pathCount++;
                    lockTile(buildingNum, new Vector3(gridX, gridY, 0));
                    break;
                default:
                    break;
            }
        }
        // Deselect the building
        buildingNum = 0;
        return false;
    }
    /**
     * Lock tiles in the grid for the selected building
     *
     * @param buildingNum The corresponding building number.
     * @param clickPos The location the user places the building
     */
    public void lockTile(int buildingNum, Vector3 clickPos){
        if (buildingNum != 6) {
            tileManager.LockTile((int) clickPos.x, (int) clickPos.y);
            tileManager.LockTile((int) clickPos.x, (int) clickPos.y + 1);
            tileManager.LockTile((int) clickPos.x, (int) clickPos.y + 2);
            tileManager.LockTile((int) clickPos.x + 1, (int) clickPos.y);
            tileManager.LockTile((int) clickPos.x + 1, (int) clickPos.y + 1);
            tileManager.LockTile((int) clickPos.x + 1, (int) clickPos.y + 2);
            tileManager.LockTile((int) clickPos.x + 2, (int) clickPos.y);
            tileManager.LockTile((int) clickPos.x + 2, (int) clickPos.y + 1);
            tileManager.LockTile((int) clickPos.x + 2, (int) clickPos.y + 2);

        } else {
            tileManager.LockTile((int) clickPos.x, (int) clickPos.y);
        }
    }

    @Override
    public boolean touchCancelled(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchDragged(int i, int i1, int i2) {
        return false;
    }

    @Override
    public boolean mouseMoved(int i, int i1) {
        return false;
    }

    @Override
    public boolean scrolled(float v, float v1) {
        return false;
    }
}
