package entities.tiles;

import controllers.Controller;
import entities.ENUMS.TileDecoration;
import entities.ENUMS.tileType;
import entities.Entity;
import entities.EntityDecorator;
import entities.monsters.EnemyArrow;
import javafx.scene.canvas.GraphicsContext;
import utility.Utility;

public class TileTurretDecorator  extends EntityDecorator{
    private final double DAMAGE=4000;
    private final double UPDATE_RATE=4.25;
    private double lightTime;
    boolean swapDecor=false;

    public TileTurretDecorator() {
    }

    public TileTurretDecorator(Entity decoratedCell) {
        super(decoratedCell);
        this.spritePlaceX=1;
    }

    @Override
    public void render(GraphicsContext canvas){
        decoratedCell.render(canvas);
        if(swapDecor) {
            canvas.drawImage(Turret, 0, 0, 16, 16
                    , this.posX, this.posY, Entity.SPRITE_WIDTH, Entity.SPRITE_HEIGHT);
        }else{
            canvas.drawImage(Turret, 0, 16, 16, 16
                    , this.posX, this.posY, Entity.SPRITE_WIDTH, Entity.SPRITE_HEIGHT);
        }
    }
    @Override
    public void update( double deltaTime) {
        cameraNColliderUpdate(deltaTime);
        lightTime-=deltaTime;
        if(lightTime<=0){
            lightTime=UPDATE_RATE;
            swapDecor=!swapDecor;
            Utility.getInstance().playSound("res/Audio/Effects/turretAttack.wav");
            for(int i=0;i<4;i++){

                Controller.getInstance().getToBeAddedObjects().add(new EnemyArrow(this.posX,this.posY,i));
            }

        }
        if(Controller.getInstance().getPlayer().getSwordsAnimationTime()>0&&!Controller.getInstance().getPlayer().getSwords().isDAMAGE_DONE()) {
            if (this.getCollider().isColliding(Controller.getInstance().getPlayer().getSwords().getCollider())) {
                Controller.getInstance().getPlayer().addScore(500);
                this.destroy();
              Controller.getInstance().getPlayer().getSwords().setDAMAGE_DONE(true);
            }
        }
    }
    public void destroy(){
        Utility.getInstance().playSound("res/Audio/Effects/explosion-2.wav");
        Controller.getInstance().getToBeAddedObjects().add(TilesFactory.getTile(this.posX,this.posY, TileDecoration.ROCKS,false, tileType.MINI_CENTER));
        Controller.getInstance().getToBeDeletedObjects().add(this);

    }
    private void removeFromObjects(){
        Controller.getInstance().getToBeDeletedObjects().add(this);
        Controller.getInstance().getToBeAddedObjects().add(decoratedCell);//ALWAYS ADD FRONT
    }

    public double getUPDATE_RATE() {
        return UPDATE_RATE;
    }


}
