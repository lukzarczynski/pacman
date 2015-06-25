package Utils;

import Game.GameCore;
import Objects.Field;
import Objects.Ghost;
import Objects.MovingGameObject;
import Objects.Player;

/**
 *
 * @author lukzar
 */
public class Calculator {

    public static double distance(Player p, Ghost g) {
        return Math.sqrt(((g.getX() - p.getX()) * (g.getX() - p.getX())) + ((g.getY() - p.getY()) * (g.getY() - p.getY())));
    }

    public static double distance(MovingGameObject p, Field g) {
        return Math.sqrt(((g.getX() - p.getX()) * (g.getX() - p.getX())) + ((g.getY() - p.getY()) * (g.getY() - p.getY())));
    }

    public static double distance(Field f, double x, double y) {
        if (f == null) {
            return 0;
        }
        return Math.sqrt(((f.getX() - x) * (f.getX() - x)) + ((f.getY() - y) * (f.getY() - y)));
    }

    public static double distance(double x1, double y1, double x2, double y2) {
        return Math.sqrt(((x1 - x2) * (x1 - x2)) + ((y1 - y2) * (y1 - y2)));
    }

    public static int calcX(int x) {
        return x - GameCore.getHALF_SQUARE();
    }

    public static int calcY(int y) {
        return y - GameCore.getHALF_SQUARE();
    }

}
