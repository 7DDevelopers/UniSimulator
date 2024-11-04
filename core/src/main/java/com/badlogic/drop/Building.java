package com.badlogic.drop;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Building extends Entity {

    public int educationLevel;
    public int funLevel;
    public int price;
    public int income;
    public String buildingType;

    public Building(float x, float y, String textureLocation, String typeOfBuilding) {
        super(x, y, textureLocation);  // Call the parent constructor to initialize the entity
        buildingType = typeOfBuilding;
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
        } else  if (typeOfBuilding == "PATH"){
            educationLevel = 0;
            funLevel = 0;
            price = 1;
            income = 0;
        }
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.begin();
        if(buildingType == "PATH"){
            spriteBatch.draw(super.texture, super.getPosition().x, super.getPosition().y, 40, 40);  // Draw with width and height
        }
        else{
            spriteBatch.draw(super.texture, super.getPosition().x, super.getPosition().y, 120, 120);
        }  // Draw with width and height
        spriteBatch.end();
    }

}
