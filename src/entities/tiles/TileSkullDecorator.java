package entities.tiles;

import entities.Entity;
import entities.EntityDecorator;
import javafx.scene.canvas.GraphicsContext;

public class TileSkullDecorator extends EntityDecorator {


    public TileSkullDecorator() {
    }


    public TileSkullDecorator(Entity decoratedCell) {
        super(decoratedCell);
        this.spritePlaceX=0;
        this.spritePlaceY=12;
    }

    @Override
    public void render(GraphicsContext canvas){
    //    decoratedCell.render(canvas); //<- mainly used on monsters death so it will be buggy to render tiles beneath it

            canvas.drawImage(Decor0, this.spritePlaceX * 16, this.spritePlaceY * 16, 16, 16
                    , this.posX, this.posY, Entity.SPRITE_WIDTH, Entity.SPRITE_HEIGHT);
    }
    @Override
    public void update( double deltaTime) {
        cameraNColliderUpdate(deltaTime);

    }



}
