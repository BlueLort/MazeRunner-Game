package entities.tiles;

import controllers.Controller;
import entities.Entity;
import entities.EntityDecorator;
import javafx.scene.canvas.GraphicsContext;

public class TileCombatChestDecorator extends EntityDecorator {

    boolean Opened=false;

    public TileCombatChestDecorator() {
    }

    public TileCombatChestDecorator(Entity decoratedCell) {
        super(decoratedCell);
        this.spritePlaceX=4;
        this.spritePlaceY=0;
    }

    @Override
    public void render(GraphicsContext canvas){
        decoratedCell.render(canvas);
        if(Opened) {
            canvas.drawImage(Chest1, this.spritePlaceX * 16, this.spritePlaceY * 16, 16, 16
                    , this.posX, this.posY, Entity.SPRITE_WIDTH, Entity.SPRITE_HEIGHT);
        }else {
            canvas.drawImage(Chest0, this.spritePlaceX * 16, this.spritePlaceY * 16, 16, 16
                    , this.posX, this.posY, Entity.SPRITE_WIDTH, Entity.SPRITE_HEIGHT);
        }
    }
    @Override
    public void update( double deltaTime) {
        cameraNColliderUpdate(deltaTime);
        if( this.decoratedCell.getCollider().isColliding(Controller.getInstance().getPlayer().getCollider())) {
            if(!Opened) {
                Controller.getInstance().getPlayer().OnC_ChestCollision();
                this.Opened = true;
            }
        }
        if(Controller.getInstance().getPlayer().getSwordsAnimationTime()>0&&!Controller.getInstance().getPlayer().getSwords().isDAMAGE_DONE()) {
            if (this.getCollider().isColliding(Controller.getInstance().getPlayer().getSwords().getCollider())) {
                Controller.getInstance().getPlayer().addScore(-200);
                this.destroy();
                Controller.getInstance().getPlayer().getSwords().setDAMAGE_DONE(true);
            }
        }
    }
    public void destroy(){
        Controller.getInstance().getToBeDeletedObjects().add(this);
        Controller.getInstance().getToBeAddedObjects().add(decoratedCell);
    }

    public boolean isOpened() {
        return Opened;
    }

    public void setOpened(boolean opened) {
        Opened = opened;
    }
}
