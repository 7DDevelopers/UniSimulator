package com.badlogic.drop;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Building extends Entity {


    public Building(float x, float y, String textureLocation) {
        super(x, y, textureLocation);  // Call the parent constructor to initialize the entity
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.begin();
        spriteBatch.draw(super.texture, super.getPosition().x, super.getPosition().y);  // Draw with width and height
        spriteBatch.end();
    }

}
