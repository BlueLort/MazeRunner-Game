package entities;

import javafx.scene.image.Image;
import utility.Collider;


public abstract class Entity implements Cell {
        //IMAGE SPRITE AND RENDER METHOD COULDNT BE DONE HERE
            //IMAGES LOADED ONCE
        public final static Image TILE_SPRITES = new Image("file:res/ImgData/Floor.png");
         public final static Image WALL_SPRITES = new Image("file:res/ImgData/Wall.png");
    public final static Image PLAYER_SPRITES = new Image("file:res/ImgData/Paladin.png");
    public final static Image ARROW_SPRITES = new Image("file:res/ImgData/Arrow.png");
    public final static Image SUPER_MONSTER_SPRITE = new Image("file:res/ImgData/monster1.png");
    public final static Image GHOST_MONSTER_SPRITE = new Image("file:res/ImgData/monster2.png");
    public final static Image DEAD_SPRITE = new Image("file:res/ImgData/dead.png");
/*
    public static final double SPRITE_SOURCE_WIDTH = 16;
    public static final double SPRITE_SOURCE_HEIGHT = 16;*/
    //ADDING SOME FACTOR TO ZOOM IN
    public static final double SPRITE_WIDTH = 16*2.25;
    public static final double SPRITE_HEIGHT = 16*2.25;

    private static double cameraXShiftMAX;
    private static double cameraYShiftMAX;
    private static double cameraXShiftMIN;
    private static double cameraYShiftMIN;
    private static double cameraX;
    private static double cameraY;


    protected double posX;
    protected double posY;
    protected int spritePlaceX = 0;
    protected int spritePlaceY = 0;
    protected Collider c;

    public Entity(double posX, double posY) {
        this.posX = posX;
        this.posY = posY;
        c = new Collider(posX, posY, SPRITE_WIDTH, SPRITE_HEIGHT);
    }

    public Collider getCollider() {
        return c;
    }

    public Entity(){

    }

    public double getPosX() {
        return posX;
    }

    public double getPosY() {
        return posY;
    }

    public void setPosX(double posX) {
        this.posX = posX;
    }

    public void setPosY(double posY) {
        this.posY = posY;
    }

    public void setSpritePlaceX(int spritePlaceX) {
        this.spritePlaceX = spritePlaceX;
    }

    public void setSpritePlaceY(int spritePlaceY) {
        this.spritePlaceY = spritePlaceY;
    }

    public static Image getTileSprites() {
        return TILE_SPRITES;
    }

    public static Image getWallSprites() {
        return WALL_SPRITES;
    }

    public static Image getPlayerSprites() {
        return PLAYER_SPRITES;
    }

    public static Image getArrowSprites() {
        return ARROW_SPRITES;
    }

    public static double getSpriteWidth() {
        return SPRITE_WIDTH;
    }

    public static double getSpriteHeight() {
        return SPRITE_HEIGHT;
    }

    public int getSpritePlaceX() {
        return spritePlaceX;
    }

    public int getSpritePlaceY() {
        return spritePlaceY;
    }

    public Collider getC() {
        return c;
    }

    public void setC(Collider c) {
        this.c = c;
    }

    public static Image getSuperMonsterSprite() {
        return SUPER_MONSTER_SPRITE;
    }

    public static Image getGhostMonsterSprite() {
        return GHOST_MONSTER_SPRITE;
    }

    public static Image getDeadSprite() {
        return DEAD_SPRITE;
    }

    public static double getCameraXShiftMAX() {
        return cameraXShiftMAX;
    }

    public static void setCameraXShiftMAX(double cameraXShiftMAX) {
        Entity.cameraXShiftMAX = cameraXShiftMAX;
    }

    public static double getCameraYShiftMAX() {
        return cameraYShiftMAX;
    }

    public static void setCameraYShiftMAX(double cameraYShiftMAX) {
        Entity.cameraYShiftMAX = cameraYShiftMAX;
    }

    public static double getCameraXShiftMIN() {
        return cameraXShiftMIN;
    }

    public static void setCameraXShiftMIN(double cameraXShiftMIN) {
        Entity.cameraXShiftMIN = cameraXShiftMIN;
    }

    public static double getCameraYShiftMIN() {
        return cameraYShiftMIN;
    }

    public static void setCameraYShiftMIN(double cameraYShiftMIN) {
        Entity.cameraYShiftMIN = cameraYShiftMIN;
    }

    public static double getCameraX() {
        return cameraX;
    }

    public static void setCameraX(double cameraX) {
        Entity.cameraX = cameraX;
        if (Entity.cameraX > Entity.cameraXShiftMAX)
            Entity.cameraX  = Entity.cameraXShiftMAX;
        else if (Entity.cameraX < Entity.cameraXShiftMIN)
            Entity.cameraX  = Entity.cameraXShiftMIN;
    }

    public static double getCameraY() {
        return cameraY;
    }

    public static void setCameraY(double cameraY) {
        Entity.cameraY = cameraY;
        if (Entity.cameraY > Entity.cameraYShiftMAX)
            Entity.cameraY  = Entity.cameraYShiftMAX;
        else if (Entity.cameraY < Entity.cameraYShiftMIN)
            Entity.cameraY  = Entity.cameraYShiftMIN;
    }

/*
    public static double getSpriteSourceWidth() {
        return SPRITE_SOURCE_WIDTH;
    }

    public static double getSpriteSourceHeight() {
        return SPRITE_SOURCE_HEIGHT;
    }
*/

}
