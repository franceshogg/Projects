package byow.Core;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Coordinate {
    public int x;
    public int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public ArrayList<ArrayList<Integer>> connectCoordinates(Coordinate current, Coordinate target) {
        ArrayList<ArrayList<Integer>> ret = new ArrayList<ArrayList<Integer>>();
        int newX = current.x;
        int smallerX = Math.min(current.x, target.x);
        int biggerX = Math.max(current.x, target.x);
        int smallerY = Math.min(current.y, target.y);
        int biggerY = Math.max(current.y, target.y);
        for (int x = smallerX; x <= biggerX; x++) {
            ArrayList<Integer> xs = new ArrayList<Integer>();
            if (smallerX == current.x) {
                xs.add(x);
                newX = x;
                xs.add(current.y);
            } else {
                xs.add(x);
                newX = x;
                xs.add(target.y);
            }
            ret.add(xs);
        }
        for (int y = smallerY; y <= biggerY; y++) {
            ArrayList<Integer> ys = new ArrayList<Integer>();
            if (smallerY == current.y) {
                ys.add(newX);
                ys.add(y);
            } else {
                ys.add(newX);
                ys.add(y);
            }
            ret.add(ys);
        }
        return ret;
    }

    public double distance(Coordinate curr, Coordinate tar) {
        return Math.sqrt(Math.pow(curr.x - tar.x, 2) + Math.pow(curr.y - tar.y, 2));
    }

    public HashMap<Integer, Integer> neighbors(HashMap<Integer, ArrayList<Integer>> roomMap) {
        HashMap<Integer, Integer> ret = new HashMap<Integer, Integer>();
        HashSet<Integer> connected = new HashSet<Integer>();
        for (int i = 0; i < roomMap.size(); i++) {
            Coordinate curr = new Coordinate(roomMap.get(i).get(0), roomMap.get(i).get(1));
            double min = Double.POSITIVE_INFINITY;
            int roomMin = 0;
            for (int j = 0; j < roomMap.size(); j++) {
                if (i != j & !connected.contains(j)) {
                    Coordinate tar = new Coordinate(roomMap.get(j).get(0), roomMap.get(j).get(1));
                    double dist = distance(curr, tar);
                    if (dist < min) {
                        min = dist;
                        roomMin = j;
                    }
                }
            }
            ret.put(i, roomMin);
            connected.add(i);
            connected.add(roomMin);
        }
        return ret;
    }

    public ArrayList<ArrayList<Integer>> roomSeparator(Coordinate a, Coordinate b, Coordinate c, Coordinate d) {
        ArrayList<ArrayList<Integer>> ret = new ArrayList<ArrayList<Integer>>();
        ret.addAll(connectCoordinates(a, b));
        ret.addAll(connectCoordinates(b, c));
        ret.addAll(connectCoordinates(c, d));
        ret.addAll(connectCoordinates(d, a));
        return ret;
    }

    public static void main(String[] args) {
        HashMap<Integer, ArrayList<Integer>> roomMap = new HashMap<Integer, ArrayList<Integer>>();
        ArrayList<ArrayList<Integer>> arrays = new ArrayList<ArrayList<Integer>>();
        for (int i = 0; i < 5; i++) {
            ArrayList<Integer> input = new ArrayList<Integer>();
            input.add(1 + i * 3 / 2);
            input.add(2 + i * 4 / 3);
            arrays.add(input);
        }
        for (int i = 0; i < arrays.size(); i++) {
            roomMap.put(i, arrays.get(0));
        }
        Coordinate c = new Coordinate(3, 4);
        System.out.print(c.neighbors(roomMap));
    }
}
