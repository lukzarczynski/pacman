package Objects;

/**
 *
 * @author lukasz
 */
public enum PointType {

    NONE("none",0),
    SMALL("o",10),
    BIG("O",50);

    String typeValue;
    int value;

    private PointType(String typeValue, int value) {
        this.typeValue = typeValue;
        this.value = value;
    }

    public String getTypeValue() {
        return typeValue;
    }

    public int getValue() {
        return value;
    }
    
}
