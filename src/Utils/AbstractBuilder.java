package Utils;

import Game.GameCore;
import Objects.GameObject;

/**
 *
 * @author lukasz
 * @param <T>
 */
public abstract class AbstractBuilder<T extends GameObject> {

    protected T item;

    public AbstractBuilder() {
        this.item = createObject();
    }

    public AbstractBuilder<T> withColumnAndRow(int column, int row) {
        this.item.setColumn(column);
        this.item.setRow(row);
        this.item.setX(column * GameCore.SQUARE_SIZE + GameCore.HALF_SQUARE);
        this.item.setY(row * GameCore.SQUARE_SIZE + GameCore.HALF_SQUARE);
        return this;
    }

    public T build() {
        return this.item;
    }

    abstract T createObject();

}
