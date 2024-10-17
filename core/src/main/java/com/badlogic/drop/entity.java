package com.badlogic.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class entity {
    private Texture texture;
    private Vector2 position;

    public entity(float x, float y, String textureLocation) {
        // Load the Badlogic texture
        texture = new Texture(Gdx.files.internal(textureLocation)); // Make sure you have this file in your assets folder
        position = new Vector2(x, y);
    }

    public void render(SpriteBatch spriteBatch) {
        spriteBatch.begin();
        spriteBatch.draw(texture, position.x, position.y);
        spriteBatch.end();
    }

    public void dispose() {
        texture.dispose();
    }

    public void setPosition(float x, float y) {
        position.set(x, y);
    }

    public Vector2 getPosition() {
        return position;
    }
}
