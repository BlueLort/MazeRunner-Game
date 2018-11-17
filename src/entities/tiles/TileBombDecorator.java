package entities.tiles;

import controllers.Controller;
import entities.Entity;
import entities.EntityDecorator;
import javafx.scene.canvas.GraphicsContext;
import utility.Utility;

public class TileBombDecorator extends EntityDecorator {
    private final double UPDATE_RATE=0.8;//0.9 second
    private final double EXPLOSION_DELAY=0.35;
    private final double DAMAGE=1200;
    private double explosionTime=EXPLOSION_DELAY;
    private boolean destroyed;
    private double swapTime;

    public TileBombDecorator() {
    }

    public TileBombDecorator(Entity decoratedCell) {
        super(decoratedCell);
        this.spritePlaceX=1;
    }

    @Override
    public void render(GraphicsContext canvas){
        decoratedCell.render(canvas);
        if(!destroyed) {
            canvas.drawImage(Bomb, this.spritePlaceX * 16, this.spritePlaceY * 16, 16, 16
                    , this.posX, this.posY, Entity.SPRITE_WIDTH - 2, Entity.SPRITE_HEIGHT - 2);
        }else{
            if(explosionTime>=EXPLOSION_DELAY/2){
                canvas.drawImage(Explosion, 0, 0, 16, 16
                        , this.posX, this.posY, Entity.SPRITE_WIDTH, Entity.SPRITE_HEIGHT*2);
            }else if(explosionTime<=EXPLOSION_DELAY/2&&explosionTime>0){
                canvas.drawImage(Explosion, 16, 0, 16, 16
                        , this.posX, this.posY, Entity.SPRITE_WIDTH, Entity.SPRITE_HEIGHT);
            }else{
                this.removeFromObjects();
            }

        }

    }
    @Override
    public void update( double deltaTime) {
        cameraNColliderUpdate(deltaTime);
        swapTime-=deltaTime;
        if(swapTime<=0){
            swapTime=UPDATE_RATE;
            spritePlaceX=(spritePlaceX+1)%2;
        }
        if(Controller.getInstance().getPlayer().getCollider().isColliding(decoratedCell.getCollider())){
            Controller.getInstance().getPlayer().takeDamage(DAMAGE);
            destroyed=true;
        }
        if(Controller.getInstance().getPlayer().getSwordsAnimationTime()>0&&!Controller.getInstance().getPlayer().getSwords().isDAMAGE_DONE()) {
            if (this.getCollider().isColliding(Controller.getInstance().getPlayer().getSwords().getCollider())) {
                this.destroy();
              Controller.getInstance().getPlayer().getSwords().setDAMAGE_DONE(true);
            }
        }
        if(destroyed){
            explosionTime-=deltaTime;
        }
    }
    public void destroy(){
        if(!destroyed)Utility.getInstance().playSound("res/Audio/Effects/explosion-3.wav");
        this.destroyed=true;

    }
   private void removeFromObjects(){
       Controller.getInstance().getToBeDeletedObjects().add(this);
       Controller.getInstance().getToBeAddedObjects().add(decoratedCell);//ALWAYS ADD FRONT
    }

    public double getUPDATE_RATE() {
        return UPDATE_RATE;
    }

    public double getSwapTime() {
        return swapTime;
    }

    public void setSwapTime(double swapTime) {
        this.swapTime = swapTime;
    }
}
