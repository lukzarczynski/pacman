package Game;

import Objects.Field;
import Objects.Ghost;
import Objects.GhostType;
import Objects.MapStatic;
import Objects.Point;
import Objects.PointType;
import Objects.Wall;
import Objects.WallType;
import Utils.FieldBuilder;
import Utils.GhostBuilder;
import Utils.PlayerBuilder;
import Utils.PointBuilder;

/**
 *
 * @author lukasz
 */
public class GameInitializer {

    public static void initialazeGameState(GameState state) {
        state.getWalls().clear();
        state.getGhosts().clear();
        state.getPoints().clear();
        String[][] map = MapStatic.map;
        for (int row = 0; row < map.length; row++) {
            for (int column = 0; column < map[0].length; column++) {
                for (WallType type : WallType.values()) {
                    if (type.getValue().equals(map[row][column])) {
                        state.getWalls().add(new Wall(type, column, row));
                    }
                }
                for (PointType type : PointType.values()) {
                    if (type.getTypeValue().equals(map[row][column])) {
                        Point p = point().withType(type).withColumnAndRow(column, row).build();
                        state.getPoints().add(p);
                        state.getFields().add(
                                field()
                                .withPoint(p)
                                .withColumnAndRow(column, row)
                                .build());
                    }
                }
                for (GhostType type : GhostType.values()) {
                    if (type.getValue().equals(map[row][column])) {
                        Field field = field().withColumnAndRow(column, row).build();
                        state.getFields().add(field);
                        Ghost ghost = ghost()
                                .withType(type)
                                .withField(field)
                                .withColumnAndRow(column, row).build();
                        state.getGhosts().add(ghost);
                    }
                }
                if (map[row][column].equals("")) {
                    state.getFields().add(field().withColumnAndRow(column, row).build());
                }
                if (map[row][column].equals("t")) {
                    state.getFields().add(field().withTeleport(true).withColumnAndRow(column, row).build());
                }
                if (map[row][column].equals("P")) {
                    Field f = field().withColumnAndRow(column, row).build();
                    state.setPlayer(player().withField(f).withColumnAndRow(column, row).build());
                    state.getFields().add(f);
                }
                if (map[row][column].equals("G")) {
                    state.getFields().add(field().withTeleport(false).withColumnAndRow(column, row).build());
                }
                if (map[row][column].equals("b")) {
                    state.setGhostBaseField(field().withGhostBase(true).withColumnAndRow(column, row).build());
                    state.getFields().add(state.getGhostBaseField());
                }
            }
        }
        initFields(state);
    }

    public static void initFields(GameState state) {
        for (int row = 0; row < MapStatic.map.length; row++) {
            for (int column = 0; column < MapStatic.map[0].length; column++) {
                Field f = findFieldAtPosition(state, column, row);
                if (f == null) {
                    continue;
                }
                f.setFieldUp(findFieldAtPosition(state, column, row - 1));
                f.setFieldDown(findFieldAtPosition(state, column, row + 1));
                f.setFieldLeft(findFieldAtPosition(state, column - 1, row));
                f.setFieldRight(findFieldAtPosition(state, column + 1, row));
                if (f.isTeleport()) {
                    if (f.getFieldRight() == null) {
                        f.setFieldRight(findFieldAtPosition(state, MapStatic.map[0].length - 1 - column, row));
                    } else if (f.getFieldLeft() == null) {
                        f.setFieldLeft(findFieldAtPosition(state, MapStatic.map[0].length - 1 - column, row));
                    }
                }
            }
        }
        state.getFields().stream()
                .filter((f) -> ((f.getFieldUp() != null || f.getFieldDown() != null)
                        && (f.getFieldLeft() != null || f.getFieldRight() != null)))
                .forEach((f) -> {
                    f.setCross(true);
                });
    }

    public static Field findFieldAtPosition(GameState state, int x, int y) {
        if (x == MapStatic.map[0].length) {
            return state.getFields().stream().filter(f -> f.getColumn() == 0 && f.getRow() == y).findFirst().orElse(null);
        } else if (x == -1) {
            return state.getFields().stream().filter(f -> f.getColumn() == MapStatic.map[0].length - 1 && f.getRow() == y).findFirst().orElse(null);
        }
        return state.getFields().stream().filter(f -> f.getColumn() == x && f.getRow() == y).findFirst().orElse(null);
    }

    private static FieldBuilder field() {
        return new FieldBuilder();
    }

    private static GhostBuilder ghost() {
        return new GhostBuilder();
    }

    private static PlayerBuilder player() {
        return new PlayerBuilder();
    }

    private static PointBuilder point() {
        return new PointBuilder();
    }
}
