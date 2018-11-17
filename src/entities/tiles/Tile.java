package entities.tiles;

import entities.ENUMS.tileType;
import entities.Entity;
import javafx.scene.canvas.GraphicsContext;
import utility.Collider;

public class Tile extends Entity {

    public Tile() {
    }

    @Override
    public void render(GraphicsContext canvas) {
        canvas.drawImage(TILE_SPRITES,  this.spritePlaceX*16,  this.spritePlaceY*16, 16, 16, this.posX, this.posY, SPRITE_WIDTH, SPRITE_HEIGHT);
    }


    @Override
    public void update( double deltaTime) {
        this.posX=this.posX-Entity.getCameraX();
        this.posY=this.posY-Entity.getCameraY();
        this.c=new Collider(posX, posY, Entity.SPRITE_WIDTH, Entity.SPRITE_HEIGHT);
    }

    public Tile(double posX, double posY, tileType type) {
        super(posX,posY);
        switch (type){

            case CORNER_BOT_LEFT:
                spritePlaceX=0;
                spritePlaceY=11;
                break;
            case CORNER_TOP_LEFT:
                spritePlaceX=0;
                spritePlaceY=9;
                break;
            case CORNER_BOT_RIGHT:
                spritePlaceX=2;
                spritePlaceY=11;
                break;
            case CORNER_TOP_RIGHT:
                spritePlaceX=2;
                spritePlaceY=9;
                break;
            case CENTER:
                spritePlaceX=1;
                spritePlaceY=10;
                break;
            case RIGHT_SIDE:
                spritePlaceX=2;
                spritePlaceY=10;
                break;
            case LEFT_SIDE:
                spritePlaceX=0;
                spritePlaceY=10;
                break;
            case TOP_SIDE:
                spritePlaceX=1;
                spritePlaceY=9;
                break;
            case BOT_SIDE:
                spritePlaceX=1;
                spritePlaceY=11;
                break;

            case MINI_BOT:
                spritePlaceX=3;
                spritePlaceY=11;
                break;
            case MINI_TOP:
                spritePlaceX=3;
                spritePlaceY=9;
                break;
            case MINI_V_SIDE:
                spritePlaceX=3;
                spritePlaceY=10;
                break;
            case MINI_CENTER:
                spritePlaceX=5;
                spritePlaceY=9;
                break;
            case MINI_LEFT:
                spritePlaceX=4;
                spritePlaceY=10;
                break;
            case MINI_RIGHT:
                spritePlaceX=6;
                spritePlaceY=10;
                break;
            case MINI_H_SIDE:
                spritePlaceX=5;
                spritePlaceY=10;
                break;



        }
    }
}
