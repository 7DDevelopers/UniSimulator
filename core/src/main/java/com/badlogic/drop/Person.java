package com.badlogic.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;

/**
 * Represents a person entity within the game, with a position and movement behavior.
 * The person can be rendered on the screen and moved to a target position.
 */
public class Person {

    public Vector2 pos;

    /**
     * Constructs a Person object at the specified position.
     *
     * @param pos The initial position of the person, as a Vector2.
     */
    public Person(Vector2 pos){
        this.pos = pos;
    }

    /**
     * Renders the person on the screen and moves them toward a default center point.
     *
     * @param spriteBatch The SpriteBatch used to draw the person texture.
     */
    public void render(SpriteBatch spriteBatch){
        // Move person towards the center of the screen
        Move(1920 / 2, 1080 / 2);

        spriteBatch.begin();
        spriteBatch.draw(new Texture("person.png"), pos.x, pos.y, 20, 20);  // Draw person with width and height
        spriteBatch.end();
    }

    /**
     * Moves the person towards the specified (x, y) position using linear interpolation.
     *
     * @param x The target x-coordinate to move towards.
     * @param y The target y-coordinate to move towards.
     */
    public void Move(int x, int y){
        pos.interpolate(new Vector2(x, y), Gdx.graphics.getDeltaTime(), Interpolation.linear);
    }
}
