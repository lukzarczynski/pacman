package Utils;

import Game.GameCore;
import Objects.Direction;
import Objects.Field;
import Objects.MovingGameObject;

/**
 *
 * @author lukasz
 * @param <T>
 */
public abstract class MovingGameObjectBuilder<T extends MovingGameObject> extends
        AbstractBuilder<T> {

    public MovingGameObjectBuilder<T> withField(Field field) {
        this.item.setField(field);
        return this;
    }

    @Override
    public MovingGameObjectBuilder<T> withColumnAndRow(int column, int row) {
        this.item.setBaseFieldColumn(column);
        this.item.setBaseFieldRow(row);
        this.item.setColumn(column);
        this.item.setRow(row);
        this.item.setX(column * GameCore.SQUARE_SIZE + GameCore.HALF_SQUARE);
        this.item.setY(row * GameCore.SQUARE_SIZE + GameCore.HALF_SQUARE);
        return this;
    }

    public MovingGameObjectBuilder<T> withBaseColumn(int column) {
        this.item.setBaseFieldColumn(column);
        return this;
    }

    public MovingGameObjectBuilder<T> withBaseRow(int row) {
        this.item.setBaseFieldRow(row);
        return this;
    }

    public MovingGameObjectBuilder<T> withColumn(int column) {
        this.item.setColumn(column);
        return this;
    }

    public MovingGameObjectBuilder<T> withRow(int row) {
        this.item.setRow(row);
        return this;
    }

    public MovingGameObjectBuilder<T> withX(int x) {
        this.item.setX(x);
        return this;
    }

    public MovingGameObjectBuilder<T> withY(int y) {
        this.item.setY(y);
        return this;
    }

    public MovingGameObjectBuilder<T> withEaten(boolean eaten) {
        this.item.setEaten(eaten);
        return this;
    }

    public MovingGameObjectBuilder<T> withDirection(Direction dir) {
        this.item.setDirection(dir);
        return this;
    }

    public MovingGameObjectBuilder<T> withNextDirection(Direction dir) {
        this.item.setNextDirection(dir);
        return this;
    }

    public MovingGameObjectBuilder<T> withExtraMode(boolean extra) {
        this.item.setInExtraMode(extra);
        return this;
    }

}
