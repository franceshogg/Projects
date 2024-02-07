package byow.TileEngine;

import java.awt.Color;

/**
 * Contains constant tile objects, to avoid having to remake the same tiles in different parts of
 * the code.
 *
 * You are free to (and encouraged to) create and add your own tiles to this file. This file will
 * be turned in with the rest of your code.
 *
 * Ex:
 *      world[x][y] = Tileset.FLOOR;
 *
 * The style checker may crash when you try to style check this file due to use of unicode
 * characters. This is OK.
 */

public class Tileset {
    public static final TETile AVATAR = new TETile('@', Color.white, Color.black, "you");
    public static final TETile WALL = new TETile('#', new Color(216, 128, 128), Color.darkGray,
            "A wall!", "./byow/ourTiles/blueWall.png");
    public static final TETile FLOOR = new TETile('·', new Color(128, 192, 128), Color.black,
            "A floor!", "./byow/ourTiles/blueTile.png");

    public static final TETile TREES = new TETile('0', new Color(51, 119, 10), Color.green,
            "Nice Green Grass!", "./byow/ourTiles/grass.png");

    public static final TETile AVA = new TETile('0', new Color(51, 119, 10), Color.green,
            "Look, it's you!", "./byow/ourTiles/linkBlueDown.png");

    public static final TETile AVALEFT = new TETile('0', Color.black, Color.black,
            "avatar", "./byow/ourTiles/linkBlueLeft.png");

    public static final TETile AVARIGHT = new TETile('0', Color.black, Color.black,
            "avatar", "./byow/ourTiles/linkBlueRight.png");

    public static final TETile AVAUP = new TETile('0', Color.black, Color.black,
            "avatar", "./byow/ourTiles/linkBlueUp.png");
    public static final TETile NOTHING = new TETile(' ', Color.black, Color.black, "nothing");
    public static final TETile TRAP = new TETile(' ', Color.red, Color.red, "food");
    public static final TETile ENEMY = new TETile('?', Color.red, Color.BLACK, "enemy");
    public static final TETile GRASS = new TETile('"', Color.green, Color.black, "grass");
    public static final TETile WATER = new TETile('≈', Color.blue, Color.black, "water");
    public static final TETile FLOWER = new TETile('❀', Color.magenta, Color.pink, "flower");
    public static final TETile LOCKED_DOOR = new TETile('█', Color.orange, Color.black,
            "locked door");
    public static final TETile UNLOCKED_DOOR = new TETile('▢', Color.orange, Color.black,
            "unlocked door");
    public static final TETile SAND = new TETile('▒', Color.yellow, Color.black, "sand");
    public static final TETile MOUNTAIN = new TETile('▲', Color.gray, Color.black, "mountain");
    public static final TETile TREE = new TETile('♠', Color.green, Color.black, "tree");
    public static final TETile STAR = new TETile('*', Color.yellow, Color.black, "star");
    //Added Tiles
    public static final TETile playerUp = new TETile('k', Color.yellow, Color.black, "Look, it's you!", "./byow/ourTiles/linkBlueUp.png");
    public static final TETile playerDown = new TETile('j', Color.yellow, Color.black, "Look, it's you!", "./byow/ourTiles/linkBlueDown.png");
    public static final TETile playerLeft = new TETile('l', Color.yellow, Color.black, "Look, it's you!", "./byow/ourTiles/linkBlueLeft.png");
    public static final TETile playerRight = new TETile(';', Color.yellow, Color.black, "Look, it's you!", "./byow/ourTiles/linkBlueRight.png");
    public static final TETile FOOD = new TETile(';', Color.yellow, Color.black, "A Tasty Apple!", "./byow/ourTiles/food.png");
}
