package entities.player.playerMoveState;

import controllers.Controller;
import entities.Entity;
import entities.player.Player;
import utility.Collider;
import utility.Utility;

public class MovingDown implements PlayerMoveState {
    private Player player;
    private Collider coll;

    public MovingDown(Player player) {
        this.player = player;
    }

    @Override
    public void move(double deltaTime) {
        double posX = player.getPosX();
        double posY = player.getPosY();

        posY += player.getSpeed() * deltaTime;

        //DIVISION VALUES ARE TESTED SO THE COLLIDER WOULD BE OK
        this.coll = new Collider(posX + Player.COLLIDER_SHIFT_WIDTH / 1.35, posY + Player.COLLIDER_SHIFT_HEIGHT*1.5, (Entity.SPRITE_WIDTH - Player.COLLIDER_SHIFT_WIDTH)/1.7
                , (Entity.SPRITE_HEIGHT - Player.COLLIDER_SHIFT_HEIGHT)/2);
        //Controller.getInstance().get_canvas().getGraphicsContext2D().strokeRect(this.coll.getStartX(),this.coll.getStartY(),this.coll.getWidth(),this.coll.getHeight());
        if(!Utility.getInstance().checkCollision(this.coll)) {
            player.changeSpriteTransition();
            player.setPosX(posX);
            player.setPosY(posY);
            player.setMapY(player.getMapY()+player.getSpeed() * deltaTime);
        }
    }

    public MovingDown() {
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Collider getColl() {
        return coll;
    }

    public void setColl(Collider coll) {
        this.coll = coll;
    }
}
