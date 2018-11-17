package entities.player;

import controllers.Controller;
import entities.Cell;
import entities.Entity;
import entities.tiles.TileBombDecorator;
import entities.tiles.TileCombatChestDecorator;
import entities.tiles.TileStatsChestDecorator;
import entities.tiles.TileH_DoorDecorator;
import entities.walls.DarkWall;
import entities.walls.Wall;
import entities.walls.WallTorchDecorator;
import javafx.scene.canvas.GraphicsContext;
import utility.Collider;

public class Arrow extends Entity {
    public static final double COLLIDER_SHIFT = 5;
    private static final double DARKWALL_SCORE=200;
    private static final double BOMB_SCORE=400;
    private static final double CHEST_SCORE=-200;
    /**
     *      FRONT
     *       LEFT
     *       RIGHT
     *       BACK
     *
     */
    private final double SPEED=120;
    public Arrow(double posX, double posY,int spritePlaceY) {
        this.posX=posX+SPRITE_WIDTH/4;
        this.posY=posY+SPRITE_HEIGHT/4;
        this.c= new Collider(posX+COLLIDER_SHIFT/2, posY+COLLIDER_SHIFT, SPRITE_WIDTH/4, SPRITE_HEIGHT/4);
        this.spritePlaceY=spritePlaceY;
    }

    @Override
    public void render(GraphicsContext canvas) {
        canvas.drawImage(ARROW_SPRITES, 0, spritePlaceY * 52, 52, 52,
                this.posX, this.posY, SPRITE_WIDTH/1.5, SPRITE_HEIGHT/1.5);
    }


    @Override
    public void update( double deltaTime) {

        switch (this.spritePlaceY){
            case 0:
                this.posY+=SPEED*deltaTime;
                break;
            case 1:
                this.posX-=SPEED*deltaTime;
                break;
            case 2:
                this.posX+=SPEED*deltaTime;
                break;
            case 3:
                this.posY-=SPEED*deltaTime;
                break;
        }
        this.posX=this.posX-Entity.getCameraX();
        this.posY=this.posY-Entity.getCameraY();
        this.getCollider().setStartX(this.posX+COLLIDER_SHIFT/2);
        this.getCollider().setStartY(this.posY+COLLIDER_SHIFT);
        if(checkCollision(this.getCollider())){
            this.destroy();
        }
    }
    public void destroy(){
        Controller.getInstance().getToBeDeletedObjects().add(this);
    }
   private boolean checkCollision(Collider coll){
        for(Cell c: Controller.getInstance().getGameObjects()){
            boolean colision=c.getCollider().isColliding(coll);
            if(c instanceof Wall ||c instanceof DarkWall ||c instanceof WallTorchDecorator||c instanceof TileH_DoorDecorator){

                if(colision){
                    if(c instanceof DarkWall){
                        ((DarkWall)c).destroy();
                        Controller.getInstance().getPlayer().addScore(DARKWALL_SCORE);
                    }
                    return true;
                }
            } else if(c instanceof TileStatsChestDecorator){
                if(colision) {
                    ((TileStatsChestDecorator) c).destroy();
                    Controller.getInstance().getPlayer().addScore(CHEST_SCORE);
                    return true;
                }
            } else if(c instanceof TileCombatChestDecorator){
                if(colision) {
                    ((TileCombatChestDecorator) c).destroy();
                    Controller.getInstance().getPlayer().addScore(CHEST_SCORE);
                    return true;
                }
            }else if(c instanceof TileBombDecorator){
                if(colision) {
                    ((TileBombDecorator) c).destroy();
                    Controller.getInstance().getPlayer().addScore(BOMB_SCORE);
                    return true;
                }
            }

        }
        return false;
    }

}
