package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.algs4.StdDraw;

import java.awt.*;
import java.io.IOException;
import java.util.Random;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.FileWriter;
import edu.princeton.cs.algs4.In;
import java.util.*;

public class Engine {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;
    public static final int TWENTY = 20;
    public static final int FIFTY = 50;
    public static final double EIGHTFIVE = 8.5;
    public static final int TEN = 10;
    private String mouseLoc = "Nothing";
    private int width;
    private int height;
    private ArrayList<ArrayList<Integer>> roomSaver = new ArrayList<ArrayList<Integer>>();
    private int numRooms;
    private int savedSeed;
    private ArrayList<Integer> avatarLoc = new ArrayList<Integer>();
    private TETile[][] tiles;
    private TETile[][] frameTiles;
    private String saveSeed;
    private TETile[][] savedGame = new TETile[width][height];
    private int savedX;
    private int savedY;
    private int avatarX;
    private int avatarY;
    private String directions = "";
    //added
    private ArrayList<Integer> foodLoc = new ArrayList<Integer>();
    private int foodX;
    private int foodY;
    //
    private String avatarDir = "";
    private int seed;
    private int score = 0;
    ArrayList<ArrayList<Integer>> floors = new ArrayList<ArrayList<Integer>>();
    //Method that returns tiles
    public TETile[][] getTiles() {
        return tiles;
    }


    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */
    public void interactWithKeyboard() {
        this.width = WIDTH;
        this.height = HEIGHT;
        this.ter.initialize(WIDTH, HEIGHT);
        StdDraw.enableDoubleBuffering();
        drawWords("Saved game(L), new game(N) or quit(Q)?", width / 2, height / 2);
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char typed = StdDraw.nextKeyTyped();
                if (typed == 'N' || typed == 'n') {
                    drawWords("Type in seed", width / 2, height / 2);
                    String inputSeed = "N";
                    boolean cont = true;
                    while (cont) {
                        if (StdDraw.hasNextKeyTyped()) {
                            char letter = StdDraw.nextKeyTyped();
                            if (letter == 's' | letter == 'S') {
                                cont = false;
                            }
                            inputSeed += letter;
                            drawWords(inputSeed, width / 2, height / 2);
                        }
                    }
                    saveSeed = inputSeed;
                    this.interactWithInputString(inputSeed);
                    break;
                } else if (typed == 'L' || typed  == 'l') {
                    loadGame();
                    break;
                } else if (typed == 'q' || typed == 'Q') {
                    System.exit(0);
                }
            }
        }
    }

    /**
     * Method used for autograding and testing your code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The engine should
     * behave exactly as if the user typed these characters into the engine using
     * interactWithKeyboard.
     *
     * Recall that strings ending in ":q" should cause the game to quite save. For example,
     * if we do interactWithInputString("n123sss:q"), we expect the game to run the first
     * 7 commands (n123sss) and then quit and save. If we then do
     * interactWithInputString("l"), we should be back in the exact same state.
     *
     * In other words, running both of these:
     *   - interactWithInputString("n123sss:q")
     *   - interactWithInputString("lww")
     *
     * should yield the exact same world state as:
     *   - interactWithInputString("n123sssww")
     *
     * //@param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */


    //Splits grid width-wise and height-wise evenly by random ints. Places room in each part.
    public void partition() {
        width = width - 2;
        height = height - 2;
        Random r = new Random(seed);
        int widthParts = r.nextInt(width / 5) + 2;
        int heightParts = r.nextInt(height / 5) + 1;
        ArrayList<ArrayList<Integer>> w = new ArrayList<ArrayList<Integer>>();
        ArrayList<ArrayList<Integer>> h = new ArrayList<ArrayList<Integer>>();
        for (int i = 0; i < widthParts; i++) {
            ArrayList<Integer> input = new ArrayList<Integer>();
            if (i == 0) {
                input.add(1);
            } else {
                input.add(w.get(i - 1).get(1));
            }
            input.add(width / widthParts + i * width / widthParts);
            w.add(input);
        }
        for (int i = 0; i < heightParts; i++) {
            ArrayList<Integer> input = new ArrayList<Integer>();
            if (i == 0) {
                input.add(1);
            } else {
                input.add(h.get(i - 1).get(1));
            }
            input.add(height / heightParts + i * height / heightParts);
            h.add(input);
        }
        partitionPartTwo(r, w, h);
    }

    public void partitionPartTwo(Random r, ArrayList<ArrayList<Integer>> w, ArrayList<ArrayList<Integer>> h) {
        for (int i = 0; i < h.size(); i++) {
            for (int j = 0; j < w.size(); j++) {
                int xStart = r.nextInt(w.get(j).get(0), (w.get(j).get(1) + w.get(j).get(0)) / 2);
                if ((w.get(j).get(1) + w.get(j).get(0)) / 2 > (w.get(j).get(0) + 1)) {
                    xStart = r.nextInt(w.get(j).get(0) + 1, (w.get(j).get(1) + w.get(j).get(0)) / 2);
                }
                int xEnd = r.nextInt(xStart, w.get(j).get(1) - 1); //ADDED -1
                while (xEnd - xStart < 3 && xEnd != width - 1) {
                    xEnd++;
                }
                int yStart = r.nextInt(h.get(i).get(0), (h.get(i).get(1) + h.get(i).get(0)) / 2);
                if ((h.get(i).get(1) + h.get(i).get(0)) / 2 > (h.get(i).get(0) + 1)) {
                    yStart = r.nextInt(h.get(i).get(0) + 1, (h.get(i).get(1) + h.get(i).get(0)) / 2);
                }
                int yEnd = r.nextInt(yStart, h.get(i).get(1) - 1); //ADDED -1
                while (yEnd - yStart < 3 && yEnd != height - 1) {
                    yEnd++;
                }
                ArrayList<Integer> input = new ArrayList<Integer>();
                if (i == 0 && j == 0) {
                    for (int x = xStart; x < xEnd; x++) {
                        for (int y = yStart; y < yEnd; y++) {
                            tiles[x][y] = Tileset.FLOOR;
                        }
                    }
                    input.add(r.nextInt(xStart, xEnd));
                    input.add(r.nextInt(yStart, yEnd));
                    numRooms++;
                } else if (r.nextInt() % 2 == 0) {
                    for (int x = xStart; x < xEnd; x++) {
                        for (int y = yStart; y < yEnd; y++) {
                            if (y + 1 < height & x + 1 < width & y > 0 & x > 0) {
                                tiles[x][y] = Tileset.FLOOR;
                            }
                        }
                    }
                    input.add(r.nextInt(xStart, xEnd));
                    input.add(r.nextInt(yStart, yEnd));
                    numRooms++;
                } else if (j == w.size() - 1 && i == h.size() - 1 && numRooms == 1) {
                    for (int x = xStart; x < xEnd; x++) {
                        for (int y = yStart; y < yEnd; y++) {
                            if (y + 1 < height & x + 1 < width & y > 0 & x > 0) {
                                tiles[x][y] = Tileset.FLOOR;
                            }
                        }
                    }
                    input.add(r.nextInt(xStart, xEnd));
                    input.add(r.nextInt(yStart, yEnd));
                }
                if (!input.isEmpty()) {
                    roomSaver.add(input);
                }
            }
        }
        findClosestNeighbors();
    }

    //Creates map that maps room # -> coordinates and room # -> closest neighbor such that
    //all rooms are connected
    public void findClosestNeighbors() {
        HashMap<Integer, ArrayList<Integer>> roomMap = new HashMap<Integer, ArrayList<Integer>>();
        HashMap<Integer, Integer> closestNeighbor = new HashMap<Integer, Integer>();
        for (int i = 0; i < roomSaver.size(); i++) {
            roomMap.put(i, roomSaver.get(i));
        }
        Coordinate c = new Coordinate(0, 0);
        closestNeighbor = c.neighbors(roomMap);
        addHallways(closestNeighbor, roomMap);
    }

    //Connects rooms using connectCoordinates from Coordinate class. These are hallways.
    public void addHallways(HashMap<Integer, Integer> closestNeighbor, HashMap<Integer, ArrayList<Integer>> roomMap) {
        Random r = new Random(seed);
        for (int i : closestNeighbor.keySet()) {
            Coordinate c = new Coordinate(roomMap.get(i).get(0), roomMap.get(i).get(1));
            Coordinate p = new Coordinate(roomMap.get(closestNeighbor.get(i)).get(0),
                    roomMap.get(closestNeighbor.get(i)).get(1));
            ArrayList<ArrayList<Integer>> coords = c.connectCoordinates(c, p);
            if (r.nextInt() % 2 == 0) {
                for (ArrayList<Integer> co : coords) {
                    if (co.get(0) == width - 1 || co.get(1) == height - 1) {
                        continue;
                    } else if (co.get(0) + 1 < width && co.get(1) + 1 < height) {
                        tiles[co.get(0) + 1][co.get(1) + 1] = Tileset.FLOOR;
                    }
                }
            }
            for (ArrayList<Integer> co : coords) {
                tiles[co.get(0)][co.get(1)] = Tileset.FLOOR;
            }
        }
        addWalls();
    }

    public boolean floorAdjacent(int x, int y) {
        boolean ret = false;
        if (x + 1 < width) {
            ret = tiles[x + 1][y] == Tileset.FLOOR;
            if (ret) {
                return ret;
            }
        }
        if (x - 1 >= 0) {
            ret = tiles[x - 1][y] == Tileset.FLOOR;
            if (ret) {
                return ret;
            }
        }
        if (y + 1 < height) {
            ret = tiles[x][y + 1] == Tileset.FLOOR;
            if (ret) {
                return ret;
            }
        }
        if (y - 1 >= 0) {
            ret = tiles[x][y - 1] == Tileset.FLOOR;
            if (ret) {
                return ret;
            }
        }
        if (x + 1 < width && y + 1 < height) {
            ret = tiles[x + 1][y + 1] == Tileset.FLOOR;
            if (ret) {
                return ret;
            }
        }
        if (x - 1 >= 0 && y - 1 >= 0) { //ADDED >=
            ret = tiles[x - 1][y - 1] == Tileset.FLOOR;
            if (ret) {
                return ret;
            }
        }
        if (x + 1 < width && y - 1 >= 0) {
            ret = tiles[x + 1][y - 1] == Tileset.FLOOR;
            if (ret) {
                return ret;
            }
        }
        if (x - 1 >= 0 && y + 1 < height) {
            ret = tiles[x - 1][y + 1] == Tileset.FLOOR;
            if (ret) {
                return ret;
            }
        }
        return ret;
    }

    //Adds walls to all tiles that are adjacent to floor tiles
    public void addWalls() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (floorAdjacent(x, y) && tiles[x][y] == Tileset.TREES) {
                    tiles[x][y] = Tileset.WALL;
                } else if ((x == width - 1 || y == height - 1) & tiles[x][y] == Tileset.FLOOR) {
                    tiles[x][y] = Tileset.WALL;
                }
            }
        }
    }

    public TETile[][] interactWithInputString(String input) {
        // Fill out this method so that it run the engine using the input
        // passed in as an argument, and return a 2D tile representation of the
        // world that would have been drawn if the same inputs had been given
        // to interactWithKeyboard().
        //
        // See proj3.byow.InputDemo for a demo of how you can make a nice clean interface
        // that works for many different input types.

        //Of form: "N#######S"
        //directions = input.replaceAll("[^A-Za-z]", "");
        if (input.charAt(0) != 'l' && input.charAt(0) != 'L') {
            String strSeed = input.replaceAll("[^0-9]", "");
            long longString = 0;
            if (strSeed.equals("")) {
                longString = 0;
            } else if (strSeed.length() > 0) {
                longString = Long.parseLong(strSeed);
            }
            System.out.println("The seed from inputString is: " + longString);
            seed = (int) longString;
        } else {
            In saveFileReader = new In("./byow/saveGame.txt");
            seed = saveFileReader.readInt();
            System.out.println("The seed from inputString is: " + seed);
        }
        directions = input.replaceAll("[\\d]", "");
        directions = directions.substring(2);

        this.width = WIDTH;
        this.height = HEIGHT;
        this.ter.initialize(WIDTH, HEIGHT);
        StdDraw.enableDoubleBuffering();
        this.tiles = new TETile[WIDTH][HEIGHT];

        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                this.tiles[x][y] = Tileset.TREES;
            }
        }

        this.partition();
        this.placeAvatar(seed);
        this.placeFood(seed + 1);

        TETile[][] finalWorldFrame = this.getTiles();
        saveGame();
        //loadGame();S
        return finalWorldFrame;
    }

    public void placeAvatar(int s) {
        Random r = new Random(s);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (tiles[i][j] == Tileset.FLOOR) {
                    ArrayList<Integer> input = new ArrayList<Integer>();
                    input.add(i);
                    input.add(j);
                    floors.add(input);
                }
            }
        }
        int index = r.nextInt(floors.size());
        avatarX = floors.get(index).get(0);
        avatarY = floors.get(index).get(1);
        tiles[avatarX][avatarY] = Tileset.AVA;
        avatarLoc.add(avatarX);
        avatarLoc.add(avatarY);
    }

    public void placeFood(int s) {
        Random r = new Random(s);
        int index = r.nextInt(floors.size());
        foodX = floors.get(index).get(0);
        foodY = floors.get(index).get(1);
        tiles[foodX][foodY] = Tileset.FOOD;
    }

    public void setFood() {
        Random r = new Random();
        ArrayList<ArrayList<Integer>> tileFloor = new ArrayList<ArrayList<Integer>>();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (tiles[i][j] == Tileset.FLOOR) {
                    ArrayList<Integer> input = new ArrayList<Integer>();
                    input.add(i);
                    input.add(j);
                    tileFloor.add(input);
                }
            }
        }
        int index = r.nextInt(tileFloor.size());
        int trapX = tileFloor.get(index).get(0);
        int trapY = tileFloor.get(index).get(1);
        tiles[trapX][trapY] = Tileset.FOOD;
        foodX = trapX;
        foodY = trapY;
    }

    public void drawFrame(String s, int x, int y) {
        StdDraw.setPenColor(Color.ORANGE);
        Font fontBig = new Font("MONACO", Font.BOLD, TWENTY);
        StdDraw.setFont(fontBig);
        StdDraw.text(x, y, s);
        StdDraw.show();
    }

    public void tileFrame(int x, int y) {
        frameTiles = new TETile[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                frameTiles[i][j] = Tileset.NOTHING;
            }
        }
        for (int i = x - 2; i < x + 3; i++) {
            for (int j = y - 2; j < y + 3; j++) {
                frameTiles[i][j] = tiles[i][j];
            }
        }
    }

    public void drawWords(String s, int x, int y) {
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.WHITE);
        Font fontBig = new Font("MONACO", Font.BOLD, FIFTY);
        StdDraw.setFont(fontBig);
        StdDraw.text(x, y, s);
        StdDraw.show();
    }

    public void preMoves(String dirs) {
        boolean colonPressed = false;
        boolean smallView = false;
        if (!dirs.equals("")) {
            for (int i = 0; i < dirs.length(); i++) {
                this.ter.renderFrame(getTiles());
                char c = dirs.charAt(i);
                if (c == ':') {
                    colonPressed = true;
                } else if (colonPressed & (c != 'q' && c != 'Q')) {
                    colonPressed = false;
                } else if ((c == 'q' || c == 'Q') & colonPressed) {
                    saveGame();
                    System.exit(0);
                } else if (c == 'w' | c == 'W') {
                    if (tiles[avatarX][avatarY + 1] == Tileset.WALL) {
                        drawFrame("WALL!", avatarX, avatarY + 2);
                        continue;
                    } else {
                        if (tiles[avatarX][avatarY + 1] == Tileset.FOOD) {
                            setFood();
                            score++;
                        }
                        tiles[avatarX][avatarY] = Tileset.FLOOR;
                        avatarY++;
                        tiles[avatarX][avatarY] = Tileset.AVA;
                    }
                } else if (c == 's' | c == 'S') {
                    if (tiles[avatarX][avatarY - 1] == Tileset.WALL) {
                        drawFrame("WALL!", avatarX, avatarY + 2);
                        continue;
                    } else {
                        if (tiles[avatarX][avatarY - 1] == Tileset.FOOD) {
                            setFood();
                            score++;
                        }
                        tiles[avatarX][avatarY] = Tileset.FLOOR;
                        avatarY--;
                        tiles[avatarX][avatarY] = Tileset.AVA;
                    }
                } else if (c == 'a' | c == 'A') {
                    if (tiles[avatarX - 1][avatarY] == Tileset.WALL) {
                        drawFrame("WALL!", avatarX, avatarY + 2);
                        continue;
                    } else {
                        if (tiles[avatarX - 1][avatarY] == Tileset.FOOD) {
                            setFood();
                            score++;
                        }
                        tiles[avatarX][avatarY] = Tileset.FLOOR;
                        avatarX--;
                        tiles[avatarX][avatarY] = Tileset.AVA;
                    }
                } else if (c == 'd' | c == 'D') {
                    if (tiles[avatarX + 1][avatarY] == Tileset.WALL) {
                        drawFrame("WALL!", avatarX, avatarY + 2);
                        continue;
                    } else {
                        if (tiles[avatarX + 1][avatarY] == Tileset.FOOD) {
                            setFood();
                            score++;
                        }
                        tiles[avatarX][avatarY] = Tileset.FLOOR;
                        avatarX++;
                        tiles[avatarX][avatarY] = Tileset.AVA;
                    }
                }
                this.ter.renderFrame(getTiles());
            }
        }
    }

    public void tileShifter(int avX, int avY, String mvmt) {
        tiles[avatarX][avatarY] = Tileset.FLOOR;
        if (mvmt.equals("yUp")) {
            avatarY++;
        } else if (mvmt.equals("yDown")) {
            avatarY--;
        } else if (mvmt.equals("xUp")) {
            avatarX++;
        } else if (mvmt.equals("xDown")) {
            avatarX--;
        }
        tiles[avatarX][avatarY] = Tileset.AVA;
    }

    public void moveAvatar(String dirs) {
        avatarX = avatarLoc.get(0);
        avatarY = avatarLoc.get(1);
        tileFrame(avatarX, avatarY);
        boolean colonPressed = false;
        boolean smallView = false;
        preMoves(dirs);
        TETile prev = Tileset.NOTHING;
        while (true) {
            drawFrame("SCORE: " + score, width - 3, height);
            if (tiles[0][0] != null) {
                if (smallView) {
                    prev = hudMouse(frameTiles, prev);
                } else {
                    prev = hudMouse(tiles, prev);
                }
            }
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                if (key == ':') {
                    colonPressed = true;
                } else if (colonPressed & (key != 'q' && key != 'Q')) {
                    colonPressed = false;
                } else if ((key == 'q' || key == 'Q') & colonPressed) {
                    saveGame();
                    System.exit(0);
                } else if (key == 'w' || key == 'W') {
                    if (tiles[avatarX][avatarY + 1] == Tileset.WALL) {
                        drawFrame("WALL!", avatarX, avatarY + 2);
                    } else {
                        if (tiles[avatarX][avatarY + 1] == Tileset.FOOD) {
                            setFood();
                            score++;
                        }
                        tileShifter(avatarX, avatarY, "yUp");
                    }
                } else if (key == 's' || key == 'S') {
                    if (tiles[avatarX][avatarY - 1] == Tileset.WALL) {
                        drawFrame("WALL!", avatarX, avatarY + 2);
                    } else {
                        if (tiles[avatarX][avatarY - 1] == Tileset.FOOD) {
                            setFood();
                            score++;
                        }
                        tileShifter(avatarX, avatarY, "yDown");
                    }
                } else if (key == 'a' || key == 'A') {
                    if (tiles[avatarX - 1][avatarY] == Tileset.WALL) {
                        drawFrame("WALL!", avatarX, avatarY + 2);
                    } else {
                        if (tiles[avatarX - 1][avatarY] == Tileset.FOOD) {
                            setFood();
                            score++;
                        }
                        tileShifter(avatarX, avatarY, "xDown");
                    }
                } else if (key == 'd' || key == 'D') {
                    if (tiles[avatarX + 1][avatarY] == Tileset.WALL) {
                        drawFrame("WALL!", avatarX, avatarY + 2);
                    } else {
                        if (tiles[avatarX + 1][avatarY] == Tileset.FOOD) {
                            setFood();
                            score++;
                        }
                        tileShifter(avatarX, avatarY, "xUp");
                    }
                } else if ((key == 'f' || key == 'F') & smallView) {
                    smallView = false;
                } else if (key == 'f' || key == 'F') {
                    smallView = true;
                }
                if (smallView) {
                    tileFrame(avatarX, avatarY);
                    this.ter.renderFrame(frameTiles);
                } else {
                    this.ter.renderFrame(getTiles());
                }
            }
        }
    }

    public TETile hudMouse(TETile[][] world, TETile prevTile) {
        TETile tile = prevTile;
        int x = (int) StdDraw.mouseX();
        int y = (int) StdDraw.mouseY();

        if (!(x >= WIDTH) && !(x < 0) && !(y >= HEIGHT) && !(y < 0)) {
            //if (world[x][y] != prevTile) {
            ter.renderFrame(world);
            String desc = world[x][y].description();
            StdDraw.setPenColor(Color.WHITE);
            StdDraw.text(WIDTH / 2, EIGHTFIVE * HEIGHT / TEN, desc);
            //StdDraw.show();
            tile = world[x][y];
            //}
        }
        return tile;
    }
    public void saveGame() {
        //Info to save: world, ter, score, seed, avatarLoc
        //Maybe only need seed, score, and location? We can redraw world
        //Should save info in text file, with each line being a new piece of info
        try {
            FileWriter fw = new FileWriter("./byow/saveGame.txt");
            fw.write(String.valueOf(seed));
            fw.write("\r\n");
            fw.write(String.valueOf(score));
            fw.write("\r\n");
            fw.write(String.valueOf(avatarX));
            fw.write("\r\n");
            fw.write(String.valueOf(avatarY));
            fw.write("\r\n");
            fw.write(String.valueOf(foodX));
            fw.write("\r\n");
            fw.write(String.valueOf(foodY));
            fw.close();
        } catch (IOException e) {
            e.getStackTrace();
        }
    }

    public void loadGame() {
        In saveFileReader = new In("./byow/saveGame.txt");
        String inputSeed = null;
        while (saveFileReader.hasNextLine()) {
            seed = saveFileReader.readInt();
            score = saveFileReader.readInt();
            avatarX = saveFileReader.readInt();
            avatarY = saveFileReader.readInt();
            foodX = saveFileReader.readInt();
            foodY = saveFileReader.readInt();
        }
        //Now make world with info!
        this.width = WIDTH;
        this.height = HEIGHT;
        this.ter.initialize(WIDTH, HEIGHT);
        this.tiles = new TETile[WIDTH][HEIGHT];

        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                this.tiles[x][y] = Tileset.TREES;
            }
        }
        this.partition();
        tiles[avatarX][avatarY] = Tileset.AVA;
        ArrayList<Integer> input = new ArrayList<Integer>();
        input.add(avatarX);
        input.add(avatarY);
        avatarLoc = input;
        tiles[foodX][foodY] = Tileset.FOOD;
        savedSeed = seed;
        TETile[][] finalWorldFrame = this.getTiles();
    }

    public static void main(String[] args) {
        Engine engine = new Engine();

        //TETile[][] testNull = engine.interactWithInputString("N9894567460S"); //N32423423S
        //engine.interactWithInputString("n11s");
        //System.out.println("seed of engine is: " + engine.seed);

        //engine.interactWithInputString("N123Swwaasddsw");
        //System.out.println("seed of engine is: " + engine.seed);
        engine.interactWithKeyboard();
        engine.ter.renderFrame(engine.getTiles());
        engine.moveAvatar(engine.directions);
    }
}
