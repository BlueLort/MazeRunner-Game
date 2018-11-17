package entities.monsters;

import entities.Entity;
import entities.player.Player;
import javafx.scene.canvas.GraphicsContext;
import utility.Collider;

public class SuperMonster extends Monster {
    public SuperMonster() {
    }

    public SuperMonster(double posX, double posY) {
            super(posX,posY);
             this.health=300;
             this.defense=15;
             this.speed=95;
             this.attackPower=1600;
    }
    @Override
    public void render(GraphicsContext canvas){
        canvas.drawImage(SUPER_MONSTER_SPRITE, this.spritePlaceX * 16, this.spritePlaceY * 16, 16, 16,
              this.posX, this.posY, SPRITE_WIDTH, SPRITE_HEIGHT);
    }
    @Override
    public void skill(Player p){
        if(p.getInvulnerableTime()<=0) {
            p.setArrows(p.getArrows() - 2);//STEAL ARROWS
            if (p.getArrows() < 0) p.setArrows(0);
            p.refreshInfo();
        }
    }


}
