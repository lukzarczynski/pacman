package Objects;

/**
 *
 * @author lukasz
 */
public class Wall extends GameObject implements Cloneable {

    private final WallType type;

    public Wall(WallType type, int posX, int posY) {
        super(posX, posY);
        this.type = type;
    }

    public WallType getType() {
        return type;
    }

}
