package entities.walls;

import entities.Cell;
import entities.ENUMS.wallType;
import entities.ENUMS.wallMaterial;

public class WallFactory {
    public WallFactory() {
    }

    public static Cell getWall(double posX, double posY, boolean Decoration, wallMaterial w, wallType c) {

        switch (w) {
            case DARK:
                if (Decoration) {
                    return new WallTorchDecorator(new DarkWall(posX, posY, c));
                } else {
                    return new DarkWall(posX, posY, c);
                }
            case DEEP:
              return new DeepWall(posX, posY, c);
            case NONE:
                if (Decoration) {
                    return new WallTorchDecorator(new Wall(posX, posY, c));
                } else {
                    return new Wall(posX, posY, c);
                }
        }
        return new Wall(posX, posY, c);
    }
}
