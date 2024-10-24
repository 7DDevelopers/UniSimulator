package com.badlogic.drop;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Building extends Entity {

    public int educationLevel;
    public int funLevel;
    public int price;
    public int income;

    public Building(float x, float y, String textureLocation, String typeOfBuilding) {
        super(x, y, textureLocation);  // Call the parent constructor to initialize the entity
        if (typeOfBuilding == "LECTUREHALL"){
            educationLevel = 100;
            funLevel = 10;
            price = 50;
            income = 0;
        } else if (typeOfBuilding == "ACCOMMODATION") {
            educationLevel = 40;
            funLevel = 70;
            price = 50;
            income = 100;
        } else if (typeOfBuilding == "RESTAURANT") {
            educationLevel = 0;
            funLevel = 75;
            price = 30;
            income = 75;
        } else if (typeOfBuilding == "PUB") {
            educationLevel = 0;
            funLevel = 100;
            price = 40;
            income = 80;
        } else if (typeOfBuilding == "GYM") {
            educationLevel = 20;
            funLevel = 80;
            price = 80;
            income = 70;
        }
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.begin();
        spriteBatch.draw(super.texture, super.getPosition().x, super.getPosition().y);  // Draw with width and height
        spriteBatch.end();
    }

}
