package entities.player.playerArmoredState;

import entities.ENUMS.PlayerDirection;
import entities.Entity;
import entities.player.Player;
import javafx.scene.canvas.GraphicsContext;
import observer.Health;
import utility.Utility;

public class Disarmoured implements PlayerArmouredState {

    private Player p;
    public Disarmoured(Player p) {
        this.p = p;
    }
    public void render(GraphicsContext canvas) {

        if (p.getSpritePlaceY()==1||p.getSpritePlaceY()==3) {
            if (p.getSwordsAnimationTime() > 0) p.getSwords().render(canvas);
            if (p.getBowAnimationTime() > 0) p.getBow().render(canvas);
            canvas.drawImage(Entity.PLAYER_SPRITES, p.getSpritePlaceX() * 16, p.getSpritePlaceY() * 16, 16, 16,
                    p.getPosX(), p.getPosY(), Entity.SPRITE_WIDTH, Entity.SPRITE_HEIGHT);
        } else {
            canvas.drawImage(Entity.PLAYER_SPRITES, p.getSpritePlaceX() * 16, p.getSpritePlaceY() * 16, 16, 16,
                    p.getPosX(), p.getPosY(), Entity.SPRITE_WIDTH, Entity.SPRITE_HEIGHT);
            if (p.getSwordsAnimationTime() > 0) p.getSwords().render(canvas);
            if (p.getBowAnimationTime() > 0) p.getBow().render(canvas);
        }

    }

    public void takeDamage(double damage){
        //if player is not respawning
        if(!p.isRespawn()&&p.getInvulnerableTime()<=0) {
            p.setInvulnerableTime(p.getINVULNERABLE_TIME());
            p.setHealth(p.getHealth()- damage / p.getDefense());
            if(p.getHealth()<0)p.setHealth(0);
            Utility.getInstance().playSound("res/Audio/Effects/scream-1.wav");
            p.notifyObservers(new Health(p.getHealth()));
            if (p.getHealth() <= 0) {
                p.onDeath();
            }
        }
    }

    public Disarmoured() {
    }

    public Player getP() {
        return p;
    }

    public void setP(Player p) {
        this.p = p;
    }
}

