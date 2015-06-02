package Objects;

/**
 *
 * @author lukasz
 */
public enum WallType {

    WEST_EAST_WALL("WE"),
    NORTH_SOUTH_WALL("NS"),
    SOUTH_WEST_CORNER("SW"),
    SOUTH_EAST_CORNER("SE"),
    NORTH_WEST_CORNER("NW"),
    NORTH_EAST_CORNER("NE"),
    WEST_EAST_DOUBLE_WALL("WED"),
    NORTH_SOUTH_DOUBLE_WALL("NSD"),
    SOUTH_WEST_DOUBLE_CORNER("SWD"),
    SOUTH_EAST_DOUBLE_CORNER("SED"),
    NORTH_WEST_DOUBLE_CORNER("NWD"),
    NORTH_EAST_DOUBLE_CORNER("NED"),
    NORTH_WEST_CORNER_NORTH_SOUTH_WALL("NWNS"),
    SOUTH_WEST_CORNER_NORTH_SOUTH_WALL("SWNS"),
    SOUTH_EAST_CORNER_NORTH_SOUTH_WALL("SENS"),
    NORTH_EAST_CORNER_NORTH_SOUTH_WALL("NENS"),
    SOUTH_WEST_CORNER_WEST_EAST_WALL("SWWE"),
    SOUTH_EAST_CORNER_WEST_EAST_WALL("SEWE");

    String value;

    private WallType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
