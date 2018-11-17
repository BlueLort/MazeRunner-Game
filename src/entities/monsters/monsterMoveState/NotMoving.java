package entities.monsters.monsterMoveState;

import entities.Entity;
import entities.monsters.Monster;
import utility.Collider;
import utility.Utility;

public class NotMoving implements MonsterMoveState {
    private Monster monster;
    public NotMoving(){

    }
    @Override
    public void move(double deltaTime){

    }

    public Monster getMonster() {
        return monster;
    }

    public void setMonster(Monster monster) {
        this.monster = monster;
    }
}

