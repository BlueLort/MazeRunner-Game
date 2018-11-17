package entities.player;

import controllers.Controller;
import entities.Entity;
import entities.EntityDecorator;
import javafx.scene.canvas.GraphicsContext;
import utility.Collider;

public class PlayerSwordDecorator extends EntityDecorator {

    /**
     * FRONT
     * LEFT
     * RIGHT
     * BACK
     */
    private final double UPDATE_RATE=0.20;
    private double swapTime=0.20;
    private boolean  DAMAGE_DONE=false;//BOOL TO MAKE THE SWORD HIT ONE TARGET PER ANIMATION

    public PlayerSwordDecorator(Entity decoratedCell) {
        super(decoratedCell);
    }

    @Override
    public void render(GraphicsContext canvas){
            canvas.drawImage(Sword, this.spritePlaceX * 16, this.spritePlaceY * 16, 16, 16
                    , this.posX, this.posY, Entity.SPRITE_WIDTH, Entity.SPRITE_HEIGHT);

    }
    @Override
    public void update( double deltaTime) {
        swapTime-=deltaTime;
        if(swapTime<=0){
            swapTime=UPDATE_RATE;
            spritePlaceX=(spritePlaceX+1)%3;
        }
    }
    public void prepare(){
        swapTime=UPDATE_RATE;
        this.spritePlaceX=0;
        DAMAGE_DONE=false;
    }
    @Override
    public Collider getCollider(){
        double ColliderPlaceX=this.posX;
        double ColliderPlaceY=this.posY;
        switch (spritePlaceY){
            case 0://Front
                ColliderPlaceX=this.posX;
                ColliderPlaceY=this.posY+ Entity.SPRITE_HEIGHT/60;
                break;
            case 1://LEFT
                ColliderPlaceX=this.posX-Entity.SPRITE_WIDTH/60;
                ColliderPlaceY=this.posY;
                break;
            case 2://RIGHT
                ColliderPlaceX=this.posX+Entity.SPRITE_WIDTH/60;
                ColliderPlaceY=this.posY;
                break;
            case 3://Back
                ColliderPlaceX=this.posX;
                ColliderPlaceY=this.posY- Entity.SPRITE_HEIGHT/60;
                break;
        }
        //RENDER COLLIDER
       // Controller.getInstance().get_canvas().getGraphicsContext2D().strokeRect(ColliderPlaceX,ColliderPlaceY,Entity.SPRITE_WIDTH,Entity.SPRITE_HEIGHT);

        return  new Collider(ColliderPlaceX,ColliderPlaceY,Entity.SPRITE_WIDTH,Entity.SPRITE_HEIGHT);
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

    public boolean isDAMAGE_DONE() {
        return DAMAGE_DONE;
    }

    public void setDAMAGE_DONE(boolean DAMAGE_DONE) {
        this.DAMAGE_DONE = DAMAGE_DONE;
    }
}
