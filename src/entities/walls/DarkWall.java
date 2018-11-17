package entities.walls;

import controllers.Controller;
import entities.ENUMS.TileDecoration;
import entities.ENUMS.tileType;
import entities.ENUMS.wallType;
import entities.Entity;
import entities.tiles.TilesFactory;
import javafx.scene.canvas.GraphicsContext;
import utility.Collider;

public class DarkWall extends Entity {
    public DarkWall() {
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
        if(Controller.getInstance().getPlayer().getSwordsAnimationTime()>0&&!Controller.getInstance().getPlayer().getSwords().isDAMAGE_DONE()) {
            if (this.getCollider().isColliding(Controller.getInstance().getPlayer().getSwords().getCollider())) {
                Controller.getInstance().getPlayer().addScore(200);
                this.destroy();
               Controller.getInstance().getPlayer().getSwords().setDAMAGE_DONE(true);
            }
        }

    }
    public void destroy(){
        Controller.getInstance().getToBeDeletedObjects().add(this);
        Controller.getInstance().getToBeAddedObjects().add(TilesFactory.getTile(this.posX,this.posY, TileDecoration.ROCKS,false, tileType.MINI_CENTER));
    }
    public DarkWall(double posX, double posY,wallType type) {
        super(posX,posY);
        switch (type){
            case H_SIDE:
                spritePlaceX=1;
                spritePlaceY=12;
                break;
            case V_SIDE:
                spritePlaceX=5;
                spritePlaceY=13;
                break;
            case SIDE_END:
                spritePlaceX=1;
                spritePlaceY=13;
                break;
            case CORNER_BOT_LEFT:
                spritePlaceX=0;
                spritePlaceY=14;
                break;
            case CORNER_TOP_LEFT:
                spritePlaceX=0;
                spritePlaceY=12;
                break;
            case CORNER_BOT_RIGHT:
                spritePlaceX=2;
                spritePlaceY=14;
                break;
            case CORNER_TOP_RIGHT:
                spritePlaceX=2;
                spritePlaceY=12;
                break;



        }
    }
}
