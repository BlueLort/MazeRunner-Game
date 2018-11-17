package entities.walls;

import entities.ENUMS.wallType;
import entities.Entity;
import entities.EntityDecorator;
import javafx.scene.canvas.GraphicsContext;
import utility.Collider;

public class DeepWall extends Entity {
    public DeepWall() {
    }

    @Override
    public void render(GraphicsContext canvas) {
        canvas.drawImage(EntityDecorator.Pit0,  this.spritePlaceX*16,  this.spritePlaceY*16, 16, 16, this.posX, this.posY, SPRITE_WIDTH, SPRITE_HEIGHT);
    }


    @Override
    public void update( double deltaTime) {
        this.posX=this.posX-Entity.getCameraX();
        this.posY=this.posY-Entity.getCameraY();
        this.c=new Collider(posX, posY, Entity.SPRITE_WIDTH, Entity.SPRITE_HEIGHT);

    }

    public DeepWall(double posX, double posY,wallType type) {
        super(posX,posY);
        switch (type){
            case H_SIDE:
                spritePlaceX=1;
                spritePlaceY=2;
                break;
            case V_SIDE:
                spritePlaceX=0;
                spritePlaceY=3;
                break;
            case SIDE_END:
                spritePlaceX=2;
                spritePlaceY=3;
                break;
            case CORNER_TOP_LEFT:
                spritePlaceX=0;
                spritePlaceY=2;
                break;
            case CORNER_TOP_RIGHT:
                spritePlaceX=2;
                spritePlaceY=2;
                break;

            /**
             * EMPTY PLACE SO PLAYER MAY FALL AND DIE
             */
            case CORNER_BOT_LEFT:case CORNER_BOT_RIGHT:
                spritePlaceX=3;
                spritePlaceY=0;
                break;


        }
    }
}
