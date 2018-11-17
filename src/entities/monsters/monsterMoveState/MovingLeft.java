package entities.monsters.monsterMoveState;

import entities.Entity;
import entities.monsters.Monster;
import utility.Collider;
import utility.Utility;

public class MovingLeft implements MonsterMoveState{
    private Monster monster;
    private Collider coll;

    public MovingLeft(Monster monster){
        this.monster=monster;
    }

    @Override
    public void move(double deltaTime){
        double posX=monster.getPosX();
        double posY=monster.getPosY();

        posX-=monster.getSpeed() * deltaTime;


        this.coll=new Collider(posX + Monster.COLLIDER_SHIFT_WIDTH / 2,posY+Monster.COLLIDER_SHIFT_HEIGHT, Entity.SPRITE_WIDTH - Monster.COLLIDER_SHIFT_WIDTH
                ,Entity.SPRITE_HEIGHT - Monster.COLLIDER_SHIFT_HEIGHT);
        if(!Utility.getInstance().checkCollisionMonster(this.coll)) {
            monster.changeSpriteTransition();
            monster.setPosX(posX);
            monster.setPosY(posY);
        }
    }

    public MovingLeft() {
    }

    public Monster getMonster() {
        return monster;
    }

    public void setMonster(Monster monster) {
        this.monster = monster;
    }

    public Collider getColl() {
        return coll;
    }

    public void setColl(Collider coll) {
        this.coll = coll;
    }
}
