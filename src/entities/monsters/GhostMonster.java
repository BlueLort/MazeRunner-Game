package entities.monsters;

import controllers.Controller;
import entities.player.Player;
import javafx.scene.canvas.GraphicsContext;

public class GhostMonster extends Monster {
    public GhostMonster() {
    }

    public GhostMonster(double posX, double posY) {
        super(posX,posY);
         this.health=100;
         this.defense=5;
         this.speed=150;
         this.attackPower=800;
    }
    @Override
    public void render(GraphicsContext canvas){
        canvas.drawImage(GHOST_MONSTER_SPRITE, this.spritePlaceX * 16, this.spritePlaceY * 16, 16, 16,
                this.posX, this.posY, SPRITE_WIDTH, SPRITE_HEIGHT);
        //DRAW TRIGGER COLLIDER
   //     Controller.getInstance().get_canvas().getGraphicsContext2D().strokeRect(AICollider.getStartX(),AICollider.getStartY(),AICollider.getWidth(),AICollider.getHeight());
    }
    @Override
    public void skill(Player p){
        if(p.getInvulnerableTime()<=0) {
            p.addScore(-100);
        }
    }


}
