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
import java.util.Date;
import java.util.List;

/**
 *
 * @author lukasz
 */
public class GameState {
    
    public final int SQUARE_SIZE;
    public final int HALF_SQUARE;
    public final int WINDOW_WIDTH;
    public final int WINDOW_HEIGHT;
    
    private final List<Wall> walls = new ArrayList<>();
    private final List<Ghost> ghosts = new ArrayList<>();
    private final List<Point> points = new ArrayList<>();
    private final List<Field> fields = new ArrayList<>();
    private Player player;
    private int level = 1;
    private int score = 0;
    private boolean extraMode = false;
    private Long extraModeStart;
    private final Long extraModeDuration = 5000L;
    private Field ghostBaseField;
    private int ghostsEaten = 0;
    private boolean playerDead = false;
    private int lives = 3;
    
    private long fps = 0;
    
    public GameState(int SQUARE_SIZE, int WINDOW_WIDTH, int WINDOW_HEIGHT) {
        this.SQUARE_SIZE = SQUARE_SIZE;
        this.HALF_SQUARE = SQUARE_SIZE / 2;
        this.WINDOW_WIDTH = WINDOW_WIDTH;
        this.WINDOW_HEIGHT = WINDOW_HEIGHT;
    }
    
    public GameState(GameState state) {
        this.HALF_SQUARE = state.getHALF_SQUARE();
        this.SQUARE_SIZE = state.getSQUARE_SIZE();
        this.WINDOW_HEIGHT = state.getWINDOW_HEIGHT();
        this.WINDOW_WIDTH = state.getWINDOW_WIDTH();
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
                if (map[row][column].equals("b")) {
                    ghostBaseField = new Field(true, column, row);
                    fields.add(ghostBaseField);
                }
            }
        }
        initFields();
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
        getFields().stream().forEach(f -> f.setFieldCoords(getHALF_SQUARE(), getSQUARE_SIZE()));
        getFields().stream()
                .filter((f) -> ((f.getFieldUp() != null || f.getFieldDown() != null)
                        && (f.getFieldLeft() != null || f.getFieldRight() != null)))
                .forEach((f) -> {
                    f.setCross(true);
                });
    }
    
    private Field findFieldAtPosition(int x, int y) {
        if (x == MapStatic.map[0].length) {
            return fields.stream().filter(f -> f.getColumn() == 0 && f.getRow() == y).findFirst().orElse(null);
        } else if (x == -1) {
            return fields.stream().filter(f -> f.getColumn() == MapStatic.map[0].length - 1 && f.getRow() == y).findFirst().orElse(null);
        }
        return fields.stream().filter(f -> f.getColumn() == x && f.getRow() == y).findFirst().orElse(null);
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
    
    public List<Field> getFields() {
        return fields;
    }
    
    public boolean isExtraMode() {
        return extraMode;
    }
    
    public void setExtraMode(boolean extraMode) {
        if (extraMode) {
            this.extraModeStart = new Date().getTime();
        } else {
            this.extraModeStart = null;
        }
        ghostsEaten = 0;
        this.extraMode = extraMode;
    }
    
    public boolean isExtraModeFinished() {
        if (extraModeStart == null) {
            return false;
        }
        return new Date().getTime() > (extraModeStart + extraModeDuration);
    }
    
    public Field getGhostBaseField() {
        return ghostBaseField;
    }
    
    public int getGhostsEaten() {
        return ghostsEaten;
    }
    
    public void eatGhost() {
        if (ghostsEaten == 0) {
            ghostsEaten = 200;
        } else {
            ghostsEaten *= 2;
        }
    }
    
    public boolean isPlayerDead() {
        return playerDead;
    }
    
    public void setPlayerDead(boolean playerDead) {
        this.player.setEaten(playerDead);
        this.playerDead = playerDead;
        this.lives--;
    }
    
    public void reset(int SQUARE_SIZE, boolean resetPoints) {
        this.player.resetToBase();
        ghosts.stream().forEach(g -> g.resetToBase());
        if (resetPoints) {
            points.stream().forEach(p -> p.setEaten(false));
        }
        setPlayerDead(false);
        setExtraMode(false);
    }
    
    public int getSQUARE_SIZE() {
        return SQUARE_SIZE;
    }
    
    public int getHALF_SQUARE() {
        return HALF_SQUARE;
    }
    
    public int getWINDOW_WIDTH() {
        return WINDOW_WIDTH;
    }
    
    public int getWINDOW_HEIGHT() {
        return WINDOW_HEIGHT;
    }
    
    public long getFps() {
        return fps;
    }
    
    public void setFps(long fps) {
        this.fps = fps;
    }
    
}
