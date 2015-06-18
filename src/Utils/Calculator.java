package Utils;

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
        double px = p.getX();
        return Math.sqrt(((g.getX() - p.getX()) * (g.getX() - p.getX())) + ((g.getY() - p.getY()) * (g.getY() - p.getY())));
    }

}
