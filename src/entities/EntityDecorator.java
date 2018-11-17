package entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import utility.Collider;

public abstract class EntityDecorator implements Cell {
    public final static Image Decor0=new Image("file:res/ImgData/Decor0.png");
    public final static Image Decor1=new Image("file:res/ImgData/Decor1.png");
    public final static Image Ground0=new Image("file:res/ImgData/Ground0.png");
    public final static Image Ground1=new Image("file:res/ImgData/Ground1.png");
    public final static Image Chest0=new Image("file:res/ImgData/Chest0.png");
    public final static Image Chest1=new Image("file:res/ImgData/Chest1.png");
    public final static Image Door0=new Image("file:res/ImgData/Door0.png");
    public final static Image Door1=new Image("file:res/ImgData/Door1.png");
    public final static Image Pit0=new Image("file:res/ImgData/Pit0.png");
    public final static Image Pit1=new Image("file:res/ImgData/Pit1.png");
    public final static Image Trap0=new Image("file:res/ImgData/Trap0.png");
    public final static Image Trap1=new Image("file:res/ImgData/Trap1.png");
    public final static Image Bomb=new Image("file:res/ImgData/bomb.png");
    public final static Image Bow= new Image("file:res/ImgData/Bow.png");
    public final static Image Sword = new Image("file:res/ImgData/Sword.png");
    public final static Image Explosion = new Image("file:res/ImgData/explosion.png");
    public final static Image Key = new Image("file:res/ImgData/Key.png");
    public final static Image PLAYER_ARMORED_SPRITES = new Image("file:res/ImgData/Warrior.png");
    public final static Image Turret = new Image("file:res/ImgData/turret.png");


    protected double posX = 0;
    protected double posY = 0;
    protected int spritePlaceX = 0;
    protected int spritePlaceY = 0;
 protected Entity decoratedCell;

    public EntityDecorator() {
    }

    ///NOTE THAT ENTITY CLASS WILL MAKE IT EASIER THAT USING THE CELL INTERFACE
    public EntityDecorator(Entity decoratedCell){
        this.decoratedCell = decoratedCell;
        this.posX=decoratedCell.getPosX();
        this.posY=decoratedCell.getPosY();
    }

    @Override
    public void render(GraphicsContext canvas){
        decoratedCell.render(canvas);
    }

    @Override
    public void update( double deltaTime){

        decoratedCell.update(deltaTime);
    }

    public void cameraNColliderUpdate(double deltaTime){
        this.posX=this.posX-Entity.getCameraX();
        this.posY=this.posY-Entity.getCameraY();
        decoratedCell.update(deltaTime);

    }
    public Entity getDecoratedCell() {
        return decoratedCell;
    }

    public void setSpritePlaceY(int spritePlaceY) {
        this.spritePlaceY = spritePlaceY;
    }

    public void setPosX(double posX) {
        this.posX = posX;
    }

    public void setPosY(double posY) {
        this.posY = posY;
    }
    @Override
    public Collider getCollider(){
        return decoratedCell.getCollider();
    }

    public static Image getDecor0() {
        return Decor0;
    }

    public static Image getDecor1() {
        return Decor1;
    }

    public static Image getGround0() {
        return Ground0;
    }

    public static Image getGround1() {
        return Ground1;
    }

    public static Image getChest0() {
        return Chest0;
    }

    public static Image getChest1() {
        return Chest1;
    }

    public static Image getDoor0() {
        return Door0;
    }

    public static Image getDoor1() {
        return Door1;
    }

    public static Image getPit0() {
        return Pit0;
    }

    public static Image getPit1() {
        return Pit1;
    }

    public static Image getTrap0() {
        return Trap0;
    }

    public static Image getTrap1() {
        return Trap1;
    }

    public static Image getBomb() {
        return Bomb;
    }

    public static Image getBow() {
        return Bow;
    }

    public static Image getSword() {
        return Sword;
    }

    public double getPosX() {
        return posX;
    }

    public double getPosY() {
        return posY;
    }

    public int getSpritePlaceX() {
        return spritePlaceX;
    }

    public void setSpritePlaceX(int spritePlaceX) {
        this.spritePlaceX = spritePlaceX;
    }

    public int getSpritePlaceY() {
        return spritePlaceY;
    }

    public void setDecoratedCell(Entity decoratedCell) {
        this.decoratedCell = decoratedCell;
    }

}
