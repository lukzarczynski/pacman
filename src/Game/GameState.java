package Game;

import Objects.Field;
import Objects.Ghost;
import Objects.GhostType;
import Objects.MapStatic;
import Objects.Player;
import Objects.Point;
import Objects.PointType;
import Objects.Wall;
import Objects.WallType;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author lukasz
 */
public class GameState {

    private final List<Wall> walls = new ArrayList<>();
    private Player player;
    private final List<Ghost> ghosts = new ArrayList<>();
    private final List<Point> points = new ArrayList<>();
    private final List<Field> fields = new ArrayList<>();
    private int level = 1;
    private int score = 0;

    public GameState() {
    }

    public GameState(GameState state) {
        this.walls.addAll(state.getWalls());
        this.ghosts.addAll(state.getGhosts());
        this.points.addAll(state.getPoints());
        this.fields.addAll(state.getFields());
        this.level = state.getLevel();
        this.score = state.getScore();
        this.player = state.getPlayer();
    }

    public void initGameState() {
        walls.clear();
        ghosts.clear();
        points.clear();
        String[][] map = MapStatic.map;
        for (int row = 0; row < map.length; row++) {
            for (int column = 0; column < map[0].length; column++) {
                for (WallType type : WallType.values()) {
                    if (type.getValue().equals(map[row][column])) {
                        walls.add(new Wall(type, column, row));
                    }
                }
                for (PointType type : PointType.values()) {
                    if (type.getTypeValue().equals(map[row][column])) {
                        Point p = new Point(column, row, type);
                        points.add(p);
                        fields.add(new Field(column, row, p));
                    }
                }
                for (GhostType type : GhostType.values()) {
                    if (type.getValue().equals(map[row][column])) {
                        Field field = new Field(column, row);
                        fields.add(field);
                        ghosts.add(new Ghost(type, column, row, field));
                    }
                }
                if (map[row][column].equals("")) {
                    fields.add(new Field(column, row));
                }
                if (map[row][column].equals("t")) {
                    fields.add(new Field(column, row, true));
                }
                if (map[row][column].equals("P")) {
                    Field field = new Field(column, row);
                    fields.add(field);
                    this.player = new Player(column, row, field);
                }
                if (map[row][column].equals("G")) {
                    fields.add(new Field(column, row, false));
                }
            }
        }
        initFields();
    }

    public List<Wall> getWalls() {
        return walls;
    }

    public Player getPlayer() {
        return player;
    }

    public List<Ghost> getGhosts() {
        return ghosts;
    }

    public List<Point> getPoints() {
        return points;
    }

    public int getLevel() {
        return level;
    }

    public int getScore() {
        return score;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setScore(int score) {
        this.score = score;
    }

    private void initFields() {
        for (int row = 0; row < MapStatic.map.length; row++) {
            for (int column = 0; column < MapStatic.map[0].length; column++) {
                Field f = findFieldAtPosition(column, row);
                if (f == null) {
                    continue;
                }
                f.setFieldUp(findFieldAtPosition(column, row - 1));
                f.setFieldDown(findFieldAtPosition(column, row + 1));
                f.setFieldLeft(findFieldAtPosition(column - 1, row));
                f.setFieldRight(findFieldAtPosition(column + 1, row));
                if (f.isTeleport()) {
                    if (f.getFieldRight() == null) {
                        f.setFieldRight(findFieldAtPosition(MapStatic.map[0].length - 1 - column, row));
                    } else if (f.getFieldLeft() == null) {
                        f.setFieldLeft(findFieldAtPosition(MapStatic.map[0].length - 1 - column, row));
                    }
                }
            }
        }
        getFields().stream()
                .filter((f) -> ((f.getFieldUp() != null || f.getFieldDown() != null)
                        && (f.getFieldLeft() != null || f.getFieldRight() != null)))
                .forEach((f) -> {
                    f.setCross(true);
                });
    }

    public List<Field> getFields() {
        return fields;
    }

    private Field findFieldAtPosition(int x, int y) {
        if (x == MapStatic.map[0].length) {
            return fields.stream().filter(f -> f.getColumn() == 0 && f.getRow() == y).findFirst().orElse(null);
        } else if (x == -1) {
            return fields.stream().filter(f -> f.getColumn() == MapStatic.map[0].length - 1 && f.getRow() == y).findFirst().orElse(null);
        }
        return fields.stream().filter(f -> f.getColumn() == x && f.getRow() == y).findFirst().orElse(null);
    }

}
