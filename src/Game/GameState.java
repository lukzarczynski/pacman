package Game;

import AI.NodeStats;
import Objects.Direction;
import Objects.Field;
import Objects.Ghost;
import Objects.GhostType;
import Objects.MovingGameObject;
import Objects.Player;
import Objects.Point;
import Objects.PointType;
import Objects.Wall;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author lukasz
 */
public class GameState {

    public final int SQUARE_SIZE = GameCore.SQUARE_SIZE;
    public final int HALF_SQUARE = GameCore.HALF_SQUARE;
    public final int WINDOW_WIDTH = GameCore.WINDOW_WIDTH;
    public final int WINDOW_HEIGHT = GameCore.WINDOW_HEIGHT;

    private List<Wall> walls = new ArrayList<>();
    private List<Ghost> ghosts = new ArrayList<>();
    private List<Point> points = new ArrayList<>();
    private List<Field> fields = new ArrayList<>();
    private Player player;
    private int level = 1;
    private int score = 0;
    private boolean extraMode = false;
    private int extraModeCounter = Integer.MIN_VALUE;
    private final int extraModeDuration = (int) (GameCore.fps * 5);
    private Field ghostBaseField;
    private int ghostsEaten = 0;
    private int lives = 3;
    private long fps = 0;
    private Map<Direction, NodeStats> stats = new HashMap<>();

    public void initGameState() {
        GameInitializer.initialazeGameState(this);
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
            this.extraModeCounter = 0;
            this.ghosts.stream().forEach(g -> g.setInExtraMode(true));
        } else {
            this.extraModeCounter = Integer.MIN_VALUE;
            this.ghosts.stream().forEach(g -> g.setInExtraMode(false));
        }
        ghostsEaten = 0;
        this.extraMode = extraMode;
    }

    public boolean isExtraModeFinished() {
        if (extraModeCounter < 0) {
            return false;
        }
        return extraModeCounter++ >= extraModeDuration;
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
        return getPlayer().isEaten();
    }

    public void setPlayerDead(boolean playerDead) {
        this.player.setEaten(playerDead);
        if (playerDead) {
            this.lives--;
        }
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

    public void setWalls(List<Wall> walls) {
        this.walls = walls;
    }

    public void setGhosts(List<Ghost> ghosts) {
        this.ghosts = ghosts;
    }

    public void setPoints(List<Point> points) {
        this.points = points;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setExtraModeCounter(int extraModeCounter) {
        this.extraModeCounter = extraModeCounter;
    }

    public void setGhostBaseField(Field ghostBaseField) {
        this.ghostBaseField = ghostBaseField;
    }

    public void setGhostsEaten(int ghostsEaten) {
        this.ghostsEaten = ghostsEaten;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public int getExtraModeCounter() {
        return extraModeCounter;
    }

    public int getExtraModeDuration() {
        return extraModeDuration;
    }

    public int getLives() {
        return lives;
    }

    public boolean isOnMiddle(MovingGameObject mgo) {
        return mgo.getField().getX() == mgo.getX() && mgo.getField().getY() == mgo.getY();
    }

    public String convertToString() {
        StringBuilder builder = new StringBuilder();
        builder.append(getPlayer().toString()).append("|");
        for (GhostType type : GhostType.values()) {
            getGhosts().stream().filter(g -> g.getType().equals(type)).forEach(g -> builder.append(g.toString()).append("|"));
        }
        builder.append(isExtraMode() ? "Y" : "N").append("|");
        builder.append(getLevel()).append("|");
        builder.append(getLives()).append("|");
        builder.append(getScore()).append("|");
        builder.append(countBigPoints()).append("|");
        builder.append(countPointsEaten());
        return builder.toString();
    }

    public long countPointsEaten() {
        return getPoints().stream().filter(p -> p.isEaten()).count();
    }

    public long countBigPoints() {
        return getPoints().stream().filter(p -> !p.isEaten() && p.getType().equals(PointType.BIG)).count();
    }

    void incrementLevel() {
        this.level++;
    }

    public Map<Direction, NodeStats> getStats() {
        return stats;
    }

    public void setStats(Map<Direction, NodeStats> stats) {
        this.stats = stats;
    }

}
