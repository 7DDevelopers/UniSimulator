package com.badlogic.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Represents a generic game entity with a texture and position.
 * Allows for rendering on screen, setting position, and managing the entity's texture.
 */
public class Entity {

    public Texture texture;
    private Vector2 position;

    /**
     * Constructs an Entity at the specified coordinates with a given texture.
     *
     * @param x The x-coordinate for the entity's position.
     * @param y The y-coordinate for the entity's position.
     * @param textureLocation The file path to the entity's texture image.
     */
    public Entity(float x, float y, String textureLocation) {
        // Load the texture from the specified file location
        texture = new Texture(Gdx.files.internal(textureLocation)); // Ensure the file exists in the assets folder
        position = new Vector2(x, y);
    }

    /**
     * Renders the entity on the screen using the provided SpriteBatch.
     *
     * @param spriteBatch The SpriteBatch used to draw the texture.
     */
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.begin();
        spriteBatch.draw(texture, position.x, position.y);
        spriteBatch.end();
    }

    /**
     * Disposes of the texture to free resources when the entity is no longer needed.
     */
    public void dispose() {
        texture.dispose();
    }

    /**
     * Updates the entity's position.
     *
     * @param x The new x-coordinate for the entity's position.
     * @param y The new y-coordinate for the entity's position.
     */
    public void setPosition(float x, float y) {
        position.set(x, y);
    }

    /**
     * Retrieves the current position of the entity.
     *
     * @return A Vector2 object representing the entity's position.
     */
    public Vector2 getPosition() {
        return position;
    }
}
