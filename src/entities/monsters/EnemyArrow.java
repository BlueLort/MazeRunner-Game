package entities.monsters;

import controllers.Controller;
import entities.Cell;
import entities.Entity;
import entities.tiles.TileBombDecorator;
import entities.tiles.TileCombatChestDecorator;
import entities.tiles.TileH_DoorDecorator;
import entities.tiles.TileStatsChestDecorator;
import entities.walls.DarkWall;
import entities.walls.Wall;
import entities.walls.WallTorchDecorator;
import javafx.scene.canvas.GraphicsContext;
import utility.Collider;

public class EnemyArrow extends Entity {
    public static final double COLLIDER_SHIFT = 5;
    private final double DAMAGE=2500;

    /**
     *      FRONT
     *       LEFT
     *       RIGHT
     *       BACK
     *
     */
    private final double SPEED=120;
    public EnemyArrow(double posX, double posY,int spritePlaceY) {
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

    public EnemyArrow() {
    }

    public static double getColliderShift() {
        return COLLIDER_SHIFT;
    }

    public double getDAMAGE() {
        return DAMAGE;
    }

    public double getSPEED() {
        return SPEED;
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
        if(this.checkCollision(this.getCollider())){
            this.destroy();
        }
        if (this.getCollider().isColliding(Controller.getInstance().getPlayer().getCollider())) {
            Controller.getInstance().getPlayer().takeDamage(DAMAGE);
            this.destroy();
        }
    }
    public void destroy(){
        Controller.getInstance().getToBeDeletedObjects().add(this);
    }
    private boolean checkCollision(Collider coll){
        for(Cell c: Controller.getInstance().getGameObjects()) {
            if(c.getCollider().isColliding(coll)) {
                if (c instanceof Wall || c instanceof DarkWall || c instanceof WallTorchDecorator
                        ||( c instanceof TileH_DoorDecorator&&!((TileH_DoorDecorator)c).isOpened())) {
                    return true;
                }
            }
        }
        return false;
    }


}
