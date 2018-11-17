package entities.walls;

import entities.Entity;

import javafx.scene.canvas.GraphicsContext;
import entities.ENUMS.wallType;
import utility.Collider;
;

public class Wall extends Entity {
    public Wall() {
    }

    @Override
    public void render(GraphicsContext canvas) {
        canvas.drawImage(WALL_SPRITES,  this.spritePlaceX*16,  this.spritePlaceY*16, 16, 16, this.posX, this.posY, SPRITE_WIDTH, SPRITE_HEIGHT);
    }


    @Override
    public void update( double deltaTime) {
        this.posX=this.posX-Entity.getCameraX();
        this.posY=this.posY-Entity.getCameraY();
        this.c=new Collider(posX, posY, Entity.SPRITE_WIDTH, Entity.SPRITE_HEIGHT);

        }

    public Wall(double posX, double posY,wallType type) {
        super(posX,posY);
        switch (type){
            case H_SIDE:
                spritePlaceX=1;
                spritePlaceY=9;
                break;
            case V_SIDE:
                spritePlaceX=0;
                spritePlaceY=10;
                break;
            case SIDE_END:
                spritePlaceX=1;
                spritePlaceY=10;
                break;
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



        }
    }
}
