package com.badlogic.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.ArrayList;

public class InputManager implements InputProcessor {

    OrthographicCamera cam;
    FitViewport viewport;

    TileManager tileManager;

    public int buildingNum = 0;

    public ArrayList<Person> people = new ArrayList<Person>();

    BuildingMenu buildingMenu;
    public int lectureCount = 0;
    public int accommodationCount = 0;
    public int restaurantCount=  0;
    public int gymCount = 0;
    public int labCount = 0;
    public int pathCount = 0;
    public InputManager(FitViewport viewport, OrthographicCamera cam){
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

    // Method to update buildingNum based on button clicked
    public void updateBuildingNum(int newBuildingNum) {
        buildingNum = newBuildingNum;
        System.out.println("Building number updated to: " + buildingNum);
    }

    @Override
    public boolean keyTyped(char c) {
        switch (c){
            //Numbers
            case '1':
                buildingNum = 0;
                break;
            case '2':
                buildingNum = 1;
                break;
            case '3':
                buildingNum = 2;
                break;
            //other
            case 'j':
                Person p = new Person(new Vector2(0,0));
                people.add(p);
                break;
            default:
                break;
        }
        return false;
    }

    @Override
    public boolean touchDown(int i, int i1, int i2, int i3) {
        return false;
    }

    public boolean setVariable(int c){
        switch (c){
            //Numbers
            case '1':
                buildingNum = 0;
                break;
            case '2':
                buildingNum = 1;
                break;
            case '3':
                buildingNum = 2;
                break;
            default:
                break;
        }
        return false;
    }

    @Override
    public boolean touchUp(int i, int i1, int i2, int i3) {
        Vector3 clickPos = viewport.unproject(new Vector3(i,i1,0));
        clickPos = new Vector3((int)Math.floor(clickPos.x / 40), (int)Math.floor(clickPos.y / 40), 0);

        //System.out.println((int)clickPos.x + "," + (int)clickPos.y);
        if ((buildingNum != 6 && (tileManager.grid[(int) clickPos.y][(int) clickPos.x] != 1 &&
            tileManager.grid[(int) clickPos.y + 1][(int) clickPos.x + 1] != 1 &&
            tileManager.grid[(int) clickPos.y + 1][(int) clickPos.x] != 1 &&
            tileManager.grid[(int) clickPos.y][(int) clickPos.x + 1] != 1)) || (buildingNum == 6 && (tileManager.grid[(int) clickPos.y][(int) clickPos.x] != 1))) {
            String buildingTexture = "";

            if(buildingNum != 6) {
                tileManager.LockTile((int) clickPos.x, (int) clickPos.y);
                tileManager.LockTile((int) clickPos.x+1, (int) clickPos.y);
                tileManager.LockTile((int) clickPos.x+1, (int) clickPos.y+1);
                tileManager.LockTile((int) clickPos.x, (int) clickPos.y+1);
            }else{
                tileManager.LockTile((int) clickPos.x, (int) clickPos.y);
            }

            Building newBuilding = null;
            switch (buildingNum) {
                case 1:
                    buildingTexture = "lectureHall.png";
                    newBuilding = new Building((int) clickPos.x * 40, (int) clickPos.y * 40, buildingTexture, "LECTUREHALL");
                    tileManager.addBuilding(newBuilding);
                    lectureCount += 1;
                    break;
                case 2:
                    buildingTexture = "accommodation.png";
                    newBuilding = new Building((int) clickPos.x * 40, (int) clickPos.y * 40, buildingTexture, "ACCOMMODATION");
                    tileManager.addBuilding(newBuilding);
                    accommodationCount += 1;
                    break;
                case 3:
                    buildingTexture = "pub.png";
                    newBuilding = new Building((int) clickPos.x * 40, (int) clickPos.y * 40, buildingTexture, "RESTAURANT");
                    tileManager.addBuilding(newBuilding);
                    restaurantCount +=1;
                    break;
                case 4:
                    buildingTexture = "gym.png";
                    newBuilding = new Building((int) clickPos.x * 40, (int) clickPos.y * 40, buildingTexture, "GYM");
                    tileManager.addBuilding(newBuilding);
                    gymCount +=1;
                    break;
                case 5:
                    buildingTexture = "lab.png";
                    newBuilding = new Building((int) clickPos.x * 40, (int) clickPos.y * 40, buildingTexture, "LAB");
                    tileManager.addBuilding(newBuilding);
                    labCount += 1;
                    break;
                case 6:
                    buildingTexture = "path.png";
                    newBuilding = new Building((int) clickPos.x * 40, (int) clickPos.y * 40, buildingTexture, "PATH");
                    tileManager.addBuilding(newBuilding);
                    pathCount +=1;
                    break;
                default:
                    break;

            }


        }

        return false;
    }

    @Override
    public boolean touchCancelled(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchDragged(int i, int i1, int i2) {
        //cam.position.set(cam.viewportWidth-i,i1,cam.position.z);
        //cam.update();
        return false;
    }

    @Override
    public boolean mouseMoved(int i, int i1) {
        return false;
    }

    @Override
    public boolean scrolled(float v, float v1) {
        cam.zoom = Math.clamp(cam.zoom + v1*0.1f, 0.3f,100);
        return false;
    }
}
