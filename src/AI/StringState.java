package AI;

/**
 *
 * @author lukasz
 */
public enum StringState {

    P_COL(0),
    P_ROW(1),
    P_EAT(2),
    P_EXT(3),
    G1_COL(4),
    G1_ROW(5),
    G1_EAT(6),
    G1_EXT(7),
    G2_COL(8),
    G2_ROW(9),
    G2_EAT(10),
    G2_EXT(11),
    G3_COL(12),
    G3_ROW(13),
    G3_EAT(14),
    G3_EXT(15),
    G4_COL(16),
    G4_ROW(17),
    G4_EAT(18),
    G4_EXT(19),
    EXTRA(20),
    LEVEL(21),
    LIVES(22),
    SCORE(23),
    EXTRAPIL(24),
    PILLSEAT(25);

    private StringState(int number) {
        this.number = number;
    }

    private final int number;

    public int getNumber() {
        return number;
    }

}
