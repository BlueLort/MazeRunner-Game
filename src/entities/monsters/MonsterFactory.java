package entities.monsters;

import entities.Cell;

public class MonsterFactory {
    public MonsterFactory() {
    }

    public static Monster getMonster(double posX, double posY,boolean SuperType) {
        if(SuperType)return new SuperMonster(posX,posY);
        return new GhostMonster(posX,posY);
    }
}
