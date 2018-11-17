package entities.player.playerArmoredState;

import entities.player.Player;
import entities.player.PlayerArmorDecorator;
import javafx.scene.canvas.GraphicsContext;

public class Armoured implements PlayerArmouredState {
    private Player p;
    private PlayerArmorDecorator pad;
    public Armoured(Player p) {
        this.p = p;
        this.pad=new PlayerArmorDecorator(p);
    }
    public void render(GraphicsContext canvas) {
        if (p.getSpritePlaceY()==1||p.getSpritePlaceY()==3) {
            if (p.getSwordsAnimationTime() > 0) p.getSwords().render(canvas);
            if (p.getBowAnimationTime() > 0) p.getBow().render(canvas);
            pad.render(canvas);
        } else {
            pad.render(canvas);
            if (p.getSwordsAnimationTime() > 0) p.getSwords().render(canvas);
            if (p.getBowAnimationTime() > 0) p.getBow().render(canvas);
        }

    }

    public void takeDamage(double damage){
        p.removeArmour();
        p.setInvulnerableTime(p.getINVULNERABLE_TIME());
        }

    public Player getP() {
        return p;
    }

    public void setP(Player p) {
        this.p = p;
    }

    public PlayerArmorDecorator getPad() {
        return pad;
    }

    public void setPad(PlayerArmorDecorator pad) {
        this.pad = pad;
    }

    public Armoured() {
    }
}

