package entities.tiles;

import entities.Cell;
import entities.ENUMS.TileDecoration;
import entities.ENUMS.tileType;

public class TilesFactory {
    public TilesFactory() {
    }

    public static Cell getTile(double posX, double posY, TileDecoration Decoration, boolean DarkTiles, tileType t) {
        switch (Decoration) {
            case S_CHEST:
                if (DarkTiles) {
                    return new TileStatsChestDecorator(new DarkTile(posX, posY, t));
                } else {
                    return new TileStatsChestDecorator(new Tile(posX, posY, t));
                }
            case ROCKS:
                if (DarkTiles) {
                    return new TileRockDecorator(new DarkTile(posX, posY, t));
                } else {
                    return new TileRockDecorator(new Tile(posX, posY, t));
                }
            case H_DOOR:
                if (DarkTiles) {
                    return new TileH_DoorDecorator(new DarkTile(posX, posY, t));
                } else {
                    return new TileH_DoorDecorator(new Tile(posX, posY, t));
                }
            case TRAP:
                if (DarkTiles) {
                    return new TileTrapDecorator(new DarkTile(posX, posY, t));
                } else {
                    return new TileTrapDecorator(new Tile(posX, posY, t));
                }
            case BOMB:
                if (DarkTiles) {
                    return new TileBombDecorator(new DarkTile(posX, posY, t));
                } else {
                    return new TileBombDecorator(new Tile(posX, posY, t));
                }
            case KEY:
                if (DarkTiles) {
                    return new TileKeyDecorator(new DarkTile(posX, posY, t));
                } else {
                    return new TileKeyDecorator(new Tile(posX, posY, t));
                }
            case C_CHEST:
                if (DarkTiles) {
                    return new TileCombatChestDecorator(new DarkTile(posX, posY, t));
                } else {
                    return new TileCombatChestDecorator(new Tile(posX, posY, t));
                }
            case E_DOOR:
                if (DarkTiles) {
                    return new TileEnd_DoorDecorator(new DarkTile(posX, posY, t));
                } else {
                    return new TileEnd_DoorDecorator(new Tile(posX, posY, t));
                }
            case SKULL:
                if (DarkTiles) {
                    return new TileSkullDecorator(new DarkTile(posX, posY, t));
                } else {
                    return new TileSkullDecorator(new Tile(posX, posY, t));
                }
            case TURRET:
                if (DarkTiles) {
                    return new TileTurretDecorator(new DarkTile(posX, posY, t));
                } else {
                    return new TileTurretDecorator(new Tile(posX, posY, t));
                }

            default:
                if (DarkTiles) {
                    return new DarkTile(posX, posY, t);
                } else {
                    return new Tile(posX, posY, t);
                }
        }
    }
}
