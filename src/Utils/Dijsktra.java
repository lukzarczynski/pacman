package Utils;

import Objects.Direction;
import Objects.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 *
 * @author lukasz
 */
public class Dijsktra {

    private static final Logger LOG = Logger.getLogger(Dijsktra.class.getName());

    private static final Map<Field, Boolean> vis = new HashMap<>();

    private static final Map<Field, Field> prev = new HashMap<>();

    public static List<Field> getFields(Field start, Field finish) {
        vis.clear();
        prev.clear();
        List<Field> directions = new LinkedList<>();
        Queue<Field> q = new LinkedList<>();
        Field current = start;
        q.add(current);
        vis.put(current, true);
        while (!q.isEmpty()) {
            current = q.remove();
            if (current.equals(finish)) {
                break;
            } else {
                for (Field node : current.getFields()) {
                    if (!vis.containsKey(node)) {
                        q.add(node);
                        vis.put(node, true);
                        prev.put(node, current);
                    }
                }
            }
        }
        for (Field node = finish; node != null; node = prev.get(node)) {
            directions.add(node);
        }
        Collections.reverse(directions);
        return directions;
    }

    public static List<Direction> getDirections(Field start, Field finish) {
        if (start.equals(finish)) {
            return new ArrayList<>();
        }
        List<Field> directions = getFields(start, finish);
        Map<Field, Direction> dirs = new HashMap<>();
        for (int i = 0; i < directions.size() - 1; i++) {
            dirs.put(directions.get(i), directions.get(i).getDirectionToField(directions.get(i + 1)));
        }
        return directions.stream().filter(d -> d.isCross()).map(d -> dirs.get(d)).collect(Collectors.toList());
    }

}
