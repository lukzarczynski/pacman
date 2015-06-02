package Objects;

/**
 *
 * @author lukasz
 */
public enum GhostType {

    PINK("PG"),
    RED("RG"),
    BLUE("BG"),
    YELLOW("YG");

    public String value;

    GhostType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
