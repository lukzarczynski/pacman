package Handlers;

import Game.GameState;
import Objects.Wall;
import Objects.drawings.Arc;
import Objects.drawings.Line;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author lukasz
 */
public class MapHandler
        extends AbstractHandler {

    private final int sqsd1;
    private final int sqsd2;

    private final Set<Line> linesToDraw = new HashSet<>();
    private final Set<Arc> arcsToDraw = new HashSet<>();

    public MapHandler(GameState state) {
        super();
        this.sqsd1 = (int) (state.getSQUARE_SIZE() * 0.4);
        this.sqsd2 = (int) (state.getSQUARE_SIZE() * 0.6);
    }

    @Override
    void behaveData(GameState state) {
    }

    @Override
    void drawData(Graphics2D data, GameState state) {
        data.setColor(Color.BLUE);
        if (linesToDraw.isEmpty()) {
            initMap(state, state.getSQUARE_SIZE());
        }
        linesToDraw.stream().forEach((line) -> {
            data.drawLine(line.getX1(), line.getY1(), line.getX2(), line.getY2());
        });
        arcsToDraw.stream().forEach((arc) -> {
            data.drawArc(arc.getX1(), arc.getY1(), arc.getX2(), arc.getY2(), arc.getStartAngle(), arc.getArcAngle());
        });
    }

    private void initMap(GameState state, int squareSize) {
        int x1, x2, y1, y2;
        for (Wall wall : state.getWalls()) {
            switch (wall.getType()) {
                case NORTH_SOUTH_WALL:
                    x1 = wall.getColumn() * squareSize + state.getHALF_SQUARE();
                    y1 = wall.getRow() * squareSize;
                    y2 = (wall.getRow() + 1) * squareSize;
                    linesToDraw.add(new Line(x1, y1, x1, y2));
                    break;
                case WEST_EAST_WALL:
                    x1 = wall.getColumn() * squareSize;
                    x2 = (wall.getColumn() + 1) * squareSize;
                    y1 = wall.getRow() * squareSize + state.getHALF_SQUARE();
                    linesToDraw.add(new Line(x1, y1, x2, y1));
                    break;
                case NORTH_EAST_CORNER:
                    x1 = (wall.getColumn()) * squareSize + state.getHALF_SQUARE();
                    y1 = (wall.getRow()) * squareSize - state.getHALF_SQUARE();
                    arcsToDraw.add(new Arc(x1, y1, squareSize, squareSize, 180, 90));
                    break;
                case NORTH_WEST_CORNER:
                    x1 = (wall.getColumn()) * squareSize - state.getHALF_SQUARE();
                    y1 = (wall.getRow()) * squareSize - state.getHALF_SQUARE();
                    arcsToDraw.add(new Arc(x1, y1, squareSize, squareSize, 270, 90));
                    break;
                case SOUTH_EAST_CORNER:
                    x1 = wall.getColumn() * squareSize + state.getHALF_SQUARE();
                    y1 = wall.getRow() * squareSize + state.getHALF_SQUARE();
                    arcsToDraw.add(new Arc(x1, y1, squareSize, squareSize, 90, 90));
                    break;
                case SOUTH_WEST_CORNER:
                    x1 = wall.getColumn() * squareSize - state.getHALF_SQUARE();
                    y1 = wall.getRow() * squareSize + state.getHALF_SQUARE();
                    arcsToDraw.add(new Arc(x1, y1, squareSize, squareSize, 0, 90));
                    break;
                case NORTH_SOUTH_DOUBLE_WALL:
                    x1 = wall.getColumn() * squareSize;
                    x2 = wall.getColumn() * squareSize;
                    y1 = wall.getRow() * squareSize;
                    y2 = (wall.getRow() + 1) * squareSize;
                    linesToDraw.add(new Line(x1 + sqsd1, y1, x2 + sqsd1, y2));
                    linesToDraw.add(new Line(x1 + sqsd2, y1, x2 + sqsd2, y2));
                    break;
                case WEST_EAST_DOUBLE_WALL:
                    x1 = wall.getColumn() * squareSize;
                    x2 = (wall.getColumn() + 1) * squareSize;
                    y1 = wall.getRow() * squareSize;
                    y2 = wall.getRow() * squareSize;
                    linesToDraw.add(new Line(x1, y1 + sqsd1, x2, y2 + sqsd1));
                    linesToDraw.add(new Line(x1, y1 + sqsd2, x2, y2 + sqsd2));
                    break;
                case NORTH_EAST_DOUBLE_CORNER:
                    x1 = wall.getColumn() * squareSize + sqsd1;
                    x2 = wall.getColumn() * squareSize + sqsd2;
                    y1 = wall.getRow() * squareSize - sqsd2;
                    y2 = wall.getRow() * squareSize - sqsd1;
                    arcsToDraw.add(new Arc(x1, y1, sqsd2 * 2, sqsd2 * 2, 180, 90));
                    arcsToDraw.add(new Arc(x2, y2, sqsd1 * 2, sqsd1 * 2, 180, 90));
                    break;
                case NORTH_WEST_DOUBLE_CORNER:
                    x1 = (wall.getColumn() - 1) * squareSize + sqsd1;
                    x2 = (wall.getColumn() - 1) * squareSize + sqsd2;
                    y1 = wall.getRow() * squareSize - sqsd2;
                    y2 = wall.getRow() * squareSize - sqsd1;
                    arcsToDraw.add(new Arc(x1, y1, sqsd2 * 2, sqsd2 * 2, 270, 90));
                    arcsToDraw.add(new Arc(x2, y2, sqsd1 * 2, sqsd1 * 2, 270, 90));
                    break;
                case SOUTH_EAST_DOUBLE_CORNER:
                    x1 = wall.getColumn() * squareSize + sqsd1;
                    x2 = wall.getColumn() * squareSize + sqsd2;
                    y1 = (wall.getRow() + 1) * squareSize - sqsd2;
                    y2 = (wall.getRow() + 1) * squareSize - sqsd1;
                    arcsToDraw.add(new Arc(x1, y1, sqsd2 * 2, sqsd2 * 2, 90, 90));
                    arcsToDraw.add(new Arc(x2, y2, sqsd1 * 2, sqsd1 * 2, 90, 90));
                    break;
                case SOUTH_WEST_DOUBLE_CORNER:
                    x1 = (wall.getColumn() - 1) * squareSize + sqsd1;
                    x2 = (wall.getColumn() - 1) * squareSize + sqsd2;
                    y1 = (wall.getRow() + 1) * squareSize - sqsd2;
                    y2 = (wall.getRow() + 1) * squareSize - sqsd1;
                    arcsToDraw.add(new Arc(x1, y1, sqsd2 * 2, sqsd2 * 2, 0, 90));
                    arcsToDraw.add(new Arc(x2, y2, sqsd1 * 2, sqsd1 * 2, 0, 90));
                    break;
                case NORTH_EAST_CORNER_NORTH_SOUTH_WALL:
                    x1 = wall.getColumn() * squareSize + sqsd1;
                    x2 = wall.getColumn() * squareSize + sqsd1;
                    y1 = wall.getRow() * squareSize;
                    y2 = (wall.getRow() + 1) * squareSize;
                    linesToDraw.add(new Line(x1, y1, x2, y2));
                    x2 = wall.getColumn() * squareSize + sqsd2;
                    y2 = wall.getRow() * squareSize - state.getHALF_SQUARE();
                    arcsToDraw.add(new Arc(x2, y2, sqsd1 * 2, squareSize, 180, 90));
                    break;
                case SOUTH_EAST_CORNER_NORTH_SOUTH_WALL:
                    x1 = wall.getColumn() * squareSize + sqsd1;
                    x2 = wall.getColumn() * squareSize + sqsd1;
                    y1 = wall.getRow() * squareSize;
                    y2 = (wall.getRow() + 1) * squareSize;
                    linesToDraw.add(new Line(x1, y1, x2, y2));
                    x2 = wall.getColumn() * squareSize + sqsd2;
                    y2 = wall.getRow() * squareSize + state.getHALF_SQUARE();
                    arcsToDraw.add(new Arc(x2, y2, sqsd1 * 2, squareSize, 90, 90));
                    break;
                case NORTH_WEST_CORNER_NORTH_SOUTH_WALL:
                    x1 = wall.getColumn() * squareSize + sqsd2;
                    x2 = wall.getColumn() * squareSize + sqsd2;
                    y1 = wall.getRow() * squareSize;
                    y2 = (wall.getRow() + 1) * squareSize;
                    linesToDraw.add(new Line(x1, y1, x2, y2));
                    x2 = (wall.getColumn() - 1) * squareSize + sqsd2;
                    y2 = wall.getRow() * squareSize - state.getHALF_SQUARE();
                    arcsToDraw.add(new Arc(x2, y2, sqsd1 * 2, squareSize, 270, 90));
                    break;
                case SOUTH_WEST_CORNER_NORTH_SOUTH_WALL:
                    x1 = wall.getColumn() * squareSize + sqsd2;
                    x2 = wall.getColumn() * squareSize + sqsd2;
                    y1 = wall.getRow() * squareSize;
                    y2 = (wall.getRow() + 1) * squareSize;
                    linesToDraw.add(new Line(x1, y1, x2, y2));
                    x2 = (wall.getColumn() - 1) * squareSize + sqsd2;
                    y2 = wall.getRow() * squareSize + state.getHALF_SQUARE();
                    arcsToDraw.add(new Arc(x2, y2, sqsd1 * 2, squareSize, 0, 90));
                    break;
                case SOUTH_EAST_CORNER_WEST_EAST_WALL:
                    x1 = wall.getColumn() * squareSize;
                    x2 = (wall.getColumn() + 1) * squareSize;
                    y1 = wall.getRow() * squareSize + sqsd1;
                    y2 = wall.getRow() * squareSize + sqsd1;
                    linesToDraw.add(new Line(x1, y1, x2, y2));
                    x2 = (wall.getColumn() + 1) * squareSize - state.getHALF_SQUARE();
                    y2 = wall.getRow() * squareSize + sqsd2;
                    arcsToDraw.add(new Arc(x2, y2, squareSize, sqsd1 * 2, 90, 90));
                    break;
                case SOUTH_WEST_CORNER_WEST_EAST_WALL:
                    x1 = wall.getColumn() * squareSize;
                    x2 = (wall.getColumn() + 1) * squareSize;
                    y1 = wall.getRow() * squareSize + sqsd1;
                    y2 = wall.getRow() * squareSize + sqsd1;
                    linesToDraw.add(new Line(x1, y1, x2, y2));
                    x2 = wall.getColumn() * squareSize - state.getHALF_SQUARE();
                    y2 = wall.getRow() * squareSize + sqsd2;
                    arcsToDraw.add(new Arc(x2, y2, squareSize, sqsd1 * 2, 0, 90));
                    break;
            }
        }
    }

}
