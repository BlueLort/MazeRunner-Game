package utility;

import entities.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import entities.ENUMS.*;
import entities.monsters.Monster;
import entities.monsters.MonsterFactory;
import entities.player.Player;
import entities.tiles.TilesFactory;
import entities.walls.DarkWall;
import entities.walls.Wall;
import entities.walls.WallFactory;

public class MapParser {
    private static MapParser mp = null;
    private Player p;

    //SINGLETON
    public static MapParser getInstance() {
        if (mp == null) {
            mp = new MapParser();
            return mp;
        } else {
            return mp;
        }
    }


    public ArrayList<ArrayList<Character>> read(String fileName) {
        ArrayList<ArrayList<Character>> Level = new ArrayList();
        // Java 8 has different style!
        FileReader fr = null;
        try {
            fr = new FileReader(fileName);
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {
                ArrayList<Character> row = new ArrayList();
                int length = line.length();
                for (int i = 0; i < length; i++) {
                    row.add(line.charAt(i));
                }
                Level.add(row);
            }
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fr != null) {
                try {
                    fr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return Level;
    }

    public ArrayList<Cell> ConstructMap(ArrayList<ArrayList<Character>> level) {
        /**
         * NOTE THAT MAP IN FILE IS DESIGNED CAREFULLY TO AVOID NULL POINTER EXCEPTIONS and make this task easy
         *
         */
        ArrayList<Cell> gameObjects = new ArrayList<>();
        ArrayList<Monster> monsters = new ArrayList<>();
        int nRows = level.size();
        try {
            for (int i = 0; i < nRows; i++) {
                int nCols = level.get(i).size();
                for (int j = 0; j < nCols; j++) {
                    char object = level.get(i).get(j);
                    wallMaterial wm = wallMaterial.NONE;
                    switch (object) {
                        /**
                         *  + wall corners
                         *  | wall v side
                         *  - wall h side
                         *  ^ wall h side decorated with a torch
                         *  . tile
                         *  \ wall side end
                         *
                         */
                        case '+'://WALL
                            if (level.get(i).get(j + 35) == ',') wm = wallMaterial.DARK;
                            //top left
                            wallType c = wallType.CORNER_TOP_LEFT;//if not initilized will be topleft

                            /**
                             * //top right
                             *  -+
                             *   |
                             */
                            if (((level.get(i).get(j - 1) == '-' || level.get(i).get(j - 1) == '^') && level.get(i + 1).get(j) == '|')) {
                                c = wallType.CORNER_TOP_RIGHT;

                                /**
                                 *  //bot left
                                 *  |
                                 *  +-
                                 */
                            } else if ((level.get(i).get(j + 1) == '-' || level.get(i).get(j + 1) == '^') && level.get(i - 1).get(j) == '|') {
                                c = wallType.CORNER_BOT_LEFT;

                                /**
                                 *  //bot right
                                 *   |
                                 *  -+
                                 */
                            } else if ((level.get(i).get(j - 1) == '-' || level.get(i).get(j - 1) == '^') && level.get(i - 1).get(j) == '|') {
                                c = wallType.CORNER_BOT_RIGHT;
                            }


                            gameObjects.add(WallFactory.getWall(j * Entity.SPRITE_WIDTH, i * Entity.SPRITE_HEIGHT,
                                    false, wm, c));
                            break;

                        case '-':
                            if (level.get(i).get(j + 50) == ',') wm = wallMaterial.DARK;
                            gameObjects.add(WallFactory.getWall(j * Entity.SPRITE_WIDTH, i * Entity.SPRITE_HEIGHT,
                                    false, wm, wallType.H_SIDE));
                            break;
                        case '^':
                            if (level.get(i).get(j + 50) == ',') wm = wallMaterial.DARK;
                            gameObjects.add(WallFactory.getWall(j * Entity.SPRITE_WIDTH, i * Entity.SPRITE_HEIGHT,
                                    true, wm, wallType.H_SIDE));
                            break;


                        case '|':
                            if (level.get(i).get(j + 50) == ',') wm = wallMaterial.DARK;
                            gameObjects.add(WallFactory.getWall(j * Entity.SPRITE_WIDTH, i * Entity.SPRITE_HEIGHT,
                                    false, wm, wallType.V_SIDE));
                            break;
                        case '/':
                            if (level.get(i).get(j +50) == ',') wm = wallMaterial.DARK;
                            gameObjects.add(WallFactory.getWall(j * Entity.SPRITE_WIDTH, i * Entity.SPRITE_HEIGHT,
                                    false, wm, wallType.SIDE_END));
                            break;
                        case '.':
                            TileDecoration td = TileDecoration.NONE;
                            boolean darkTiles = false;
                            //PUT DATA TO DECORATE THE MAP ON THE SIDES AWAY FROM THE MAP
                            if (level.get(i + 40).get(j) == 'C') {
                                td = TileDecoration.C_CHEST;
                            } else if (level.get(i + 40).get(j) == 'S') {
                                td = TileDecoration.S_CHEST;
                            } else if (level.get(i + 40).get(j) == 'R') {
                                td = TileDecoration.ROCKS;
                            } else if (level.get(i + 40).get(j) == 'T') {
                                td = TileDecoration.TRAP;
                            } else if (level.get(i + 40).get(j) == 'B') {
                                td = TileDecoration.BOMB;
                            } else if (level.get(i + 40).get(j) == 'K') {
                                td = TileDecoration.KEY;
                            } else if (level.get(i + 40).get(j) == 'P') {
                                this.p = new Player(j * Entity.SPRITE_WIDTH, i * Entity.SPRITE_HEIGHT);
                            } else if (level.get(i + 40).get(j) == 'M') {
                                monsters.add(MonsterFactory.getMonster(j * Entity.SPRITE_WIDTH, i * Entity.SPRITE_HEIGHT, true));
                            } else if (level.get(i + 40).get(j) == 'G') {
                                monsters.add(MonsterFactory.getMonster(j * Entity.SPRITE_WIDTH, i * Entity.SPRITE_HEIGHT, false));
                            } else if (level.get(i + 40).get(j) == '<') {
                                td=TileDecoration.TURRET;
                            }
                            if (level.get(i).get(j + 50) == ',') darkTiles = true;


                            tileType t = tileType.CENTER;//default is center
                            /**     +-
                             *      |.
                             *
                             */
                            if ((level.get(i - 1).get(j - 1) == '+' && level.get(i).get(j - 1) == '|' && (level.get(i - 1).get(j) == '-' || level.get(i - 1).get(j) == '^'))) {
                                t = tileType.CORNER_TOP_LEFT;

                                /**
                                 *       |.
                                 *       +-
                                 *
                                 */
                            } else if ((level.get(i + 1).get(j - 1) == '+' && level.get(i).get(j - 1) == '|' && (level.get(i + 1).get(j) == '-' || level.get(i + 1).get(j) == '^'))) {
                                t = tileType.CORNER_BOT_LEFT;
                                /**      -+
                                 *       .|
                                 *
                                 */
                            } else if (level.get(i - 1).get(j + 1) == '+' && level.get(i).get(j + 1) == '|' && (level.get(i - 1).get(j) == '-' || level.get(i - 1).get(j) == '^')) {
                                t = tileType.CORNER_TOP_RIGHT;
                                /**
                                 *       .|
                                 *       -+
                                 */
                            } else if (level.get(i + 1).get(j + 1) == '+' && level.get(i).get(j + 1) == '|' && (level.get(i + 1).get(j) == '-' || level.get(i + 1).get(j) == '^')) {
                                t = tileType.CORNER_BOT_RIGHT;
                                /**
                                 *       |.|
                                 *       +-+
                                 *
                                 */
                            } else if (level.get(i + 1).get(j - 1) == '+' && level.get(i + 1).get(j + 1) == '+'
                                    && (level.get(i + 1).get(j) == '-' || level.get(i + 1).get(j) == '^') && level.get(i).get(j - 1) == '|' && level.get(i).get(j + 1) == '|') {
                                t = tileType.MINI_BOT;
                                /**
                                 *      +-+
                                 *      |.|
                                 *
                                 */
                            } else if (level.get(i - 1).get(j - 1) == '+' && level.get(i - 1).get(j + 1) == '+'
                                    && (level.get(i - 1).get(j) == '-' || level.get(i - 1).get(j) == '^') && level.get(i).get(j - 1) == '|' && level.get(i).get(j + 1) == '|') {
                                t = tileType.MINI_TOP;
                                /**
                                 *      +-
                                 *      |.
                                 *      +-
                                 */
                            } else if (level.get(i - 1).get(j - 1) == '+' && level.get(i + 1).get(j - 1) == '+'
                                    && level.get(i).get(j - 1) == '|' && (level.get(i - 1).get(j) == '-' || level.get(i - 1).get(j) == '^')
                                    && (level.get(i + 1).get(j) == '-' || level.get(i + 1).get(j) == '^')) {
                                t = tileType.MINI_LEFT;
                                /**
                                 *     -+
                                 *     .|
                                 *     -+
                                 */
                            } else if (level.get(i - 1).get(j + 1) == '+' && level.get(i + 1).get(j + 1) == '+'
                                    && level.get(i).get(j + 1) == '|' && (level.get(i - 1).get(j) == '-' || level.get(i - 1).get(j) == '^')
                                    && (level.get(i + 1).get(j) == '-' || level.get(i + 1).get(j) == '^')) {
                                t = tileType.MINI_RIGHT;
                                /**
                                 *      -
                                 *     ...
                                 *      -
                                 */
                            } else if ((level.get(i - 1).get(j) == '-' || level.get(i - 1).get(j) == '+' || level.get(i - 1).get(j) == '^')
                                    && (level.get(i + 1).get(j) == '-' || level.get(i + 1).get(j) == '+' || level.get(i + 1).get(j) == '^')
                                    && level.get(i).get(j - 1) == '.' && level.get(i).get(j + 1) == '.') {
                                t = tileType.MINI_H_SIDE;
                                /**     .
                                 *     |.|
                                 *      .
                                 */
                            } else if ((level.get(i).get(j - 1) == '|' || level.get(i).get(j - 1) == '+')
                                    && (level.get(i).get(j + 1) == '|' || level.get(i).get(j + 1) == '+')
                                    && level.get(i - 1).get(j) == '.' && level.get(i + 1).get(j) == '.') {
                                t = tileType.MINI_V_SIDE;

                                /**
                                 *  |..
                                 */
                            } else if (level.get(i).get(j + 1) == '.' && (level.get(i).get(j - 1) == '|' || level.get(i).get(j - 1) == '+')) {
                                t = tileType.LEFT_SIDE;
                                /**
                                 *  ..|
                                 */
                            } else if (level.get(i).get(j - 1) == '.' && (level.get(i).get(j + 1) == '|' || level.get(i).get(j + 1) == '+')) {
                                t = tileType.RIGHT_SIDE;
                                /**   -
                                 *    .
                                 *    .
                                 */
                            } else if (level.get(i + 1).get(j) == '.' && (level.get(i - 1).get(j) == '-' || level.get(i - 1).get(j) == '+' || level.get(i - 1).get(j) == '^')) {
                                t = tileType.TOP_SIDE;
                                /**   .
                                 *    .
                                 *    -
                                 */
                            } else if (level.get(i - 1).get(j) == '.' && (level.get(i + 1).get(j) == '-' || level.get(i + 1).get(j) == '+' || level.get(i + 1).get(j) == '^')) {
                                t = tileType.BOT_SIDE;
                            }


                            gameObjects.add(TilesFactory.getTile(j * Entity.SPRITE_WIDTH, i * Entity.SPRITE_HEIGHT, td, darkTiles, t));
                            break;
                        case 'O':
                            gameObjects.add(TilesFactory.getTile(j * Entity.SPRITE_WIDTH, i * Entity.SPRITE_HEIGHT, TileDecoration.NONE, false, tileType.MINI_CENTER));
                            break;
                        case 'E':
                            gameObjects.add(TilesFactory.getTile(j * Entity.SPRITE_WIDTH, i * Entity.SPRITE_HEIGHT, TileDecoration.E_DOOR
                                    , false, tileType.MINI_CENTER));
                            break;
                        case 'D':
                            gameObjects.add(TilesFactory.getTile(j * Entity.SPRITE_WIDTH, i * Entity.SPRITE_HEIGHT, TileDecoration.H_DOOR
                                    , false, tileType.MINI_CENTER));
                            break;
                        case '2':
                            gameObjects.add(TilesFactory.getTile(j * Entity.SPRITE_WIDTH, i * Entity.SPRITE_HEIGHT, TileDecoration.NONE
                                    , false, tileType.MINI_V_SIDE));
                            break;
                        case '3':
                            gameObjects.add(TilesFactory.getTile(j * Entity.SPRITE_WIDTH, i * Entity.SPRITE_HEIGHT, TileDecoration.NONE
                                    , false, tileType.CORNER_BOT_RIGHT));
                            break;
                        case '5':
                            gameObjects.add(TilesFactory.getTile(j * Entity.SPRITE_WIDTH, i * Entity.SPRITE_HEIGHT, TileDecoration.NONE
                                    , false, tileType.CORNER_TOP_LEFT));
                            break;
                        //EMPTY PLACE FOR PLAYER TO FALL TECHNIQUE
                        case '?':
                            gameObjects.add(WallFactory.getWall(j * Entity.SPRITE_WIDTH, i * Entity.SPRITE_HEIGHT, false,
                                    wallMaterial.DEEP, wallType.CORNER_BOT_LEFT));
                            break;
                        case '"':
                            gameObjects.add(WallFactory.getWall(j * Entity.SPRITE_WIDTH, i * Entity.SPRITE_HEIGHT, false,
                                    wallMaterial.DEEP, wallType.SIDE_END));
                            break;
                        case '\'':
                            gameObjects.add(WallFactory.getWall(j * Entity.SPRITE_WIDTH, i * Entity.SPRITE_HEIGHT, false,
                                    wallMaterial.DEEP, wallType.H_SIDE));
                            break;
                        case ']':
                            gameObjects.add(WallFactory.getWall(j * Entity.SPRITE_WIDTH, i * Entity.SPRITE_HEIGHT, false,
                                    wallMaterial.DEEP, wallType.CORNER_TOP_RIGHT));
                            break;
                        case '[':
                            gameObjects.add(WallFactory.getWall(j * Entity.SPRITE_WIDTH, i * Entity.SPRITE_HEIGHT, false,
                                    wallMaterial.DEEP, wallType.CORNER_TOP_LEFT));
                            break;
                        case '*':
                            gameObjects.add(WallFactory.getWall(j * Entity.SPRITE_WIDTH, i * Entity.SPRITE_HEIGHT, false,
                                    wallMaterial.DEEP, wallType.V_SIDE));
                            break;

                        case '}':
                            gameObjects.add(TilesFactory.getTile(j * Entity.SPRITE_WIDTH, i * Entity.SPRITE_HEIGHT, TileDecoration.NONE,
                                    false, tileType.CORNER_TOP_RIGHT));
                            break;
                        case '{':
                            gameObjects.add(TilesFactory.getTile(j * Entity.SPRITE_WIDTH, i * Entity.SPRITE_HEIGHT, TileDecoration.NONE,
                                    false, tileType.CORNER_TOP_LEFT));
                            break;
                        case '#':
                            gameObjects.add(TilesFactory.getTile(j * Entity.SPRITE_WIDTH, i * Entity.SPRITE_HEIGHT, TileDecoration.NONE,
                                    false, tileType.TOP_SIDE));
                            break;
                        case '!':
                            gameObjects.add(WallFactory.getWall(j * Entity.SPRITE_WIDTH, i * Entity.SPRITE_HEIGHT, false
                                    , wallMaterial.DARK, wallType.H_SIDE));
                            break;
                        default:
                            //DONT DO ANYTHING
                            break;
                    }
                }
            }
            for (Monster m : monsters) {
                gameObjects.add(m);//keep them at last (TO APPLY PAINTER'S ALGORITHM)
            }
        } catch (Exception e) {
            System.out.println("Error During constructing map from the level file");
        }
        return gameObjects;

    }

    public Player getPlayer() {
        return p;
    }
}
