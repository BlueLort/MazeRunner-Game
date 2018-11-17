package entities.tiles;

import controllers.Controller;
import entities.Entity;
import entities.EntityDecorator;
import javafx.scene.canvas.GraphicsContext;

public class TileKeyDecorator extends EntityDecorator {
    boolean keyTaken=false;
    public TileKeyDecorator(Entity decoratedCell) {
        super(decoratedCell);
    }

    @Override
    public void render(GraphicsContext canvas){
        decoratedCell.render(canvas);
        canvas.drawImage(Key, this.spritePlaceX * 16, this.spritePlaceY * 16, 16, 16
                , this.posX, this.posY, Entity.SPRITE_WIDTH, Entity.SPRITE_HEIGHT);
    }
    @Override
    public void update( double deltaTime) {
        cameraNColliderUpdate(deltaTime);
            if (this.getCollider().isColliding(Controller.getInstance().getPlayer().getCollider())) {
               if(!keyTaken){
                   Controller.getInstance().getPlayer().takeKey();
                    keyTaken=true;//fixing a bug where tobeadded is delayed
               }
                this.destroy();
            }
    }
    public void destroy(){
        Controller.getInstance().getToBeDeletedObjects().add(this);
        Controller.getInstance().getToBeAddedObjects().add(decoratedCell);
    }

    public TileKeyDecorator() {
    }
}
