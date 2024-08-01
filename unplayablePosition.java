public class unplayablePosition extends Position {
    static final char UNPLAYABLE = '*';  // Define a constant for the unplayable character

    public unplayablePosition() {
        super(UNPLAYABLE, false);  // Call the superclass constructor with UNPLAYABLE and false for isPlayable
    }

    @Override
    public boolean canPlay() {
        return false;  // Ensure this position is always unplayable
    }

    @Override
    public String toString() {
        return "Position{" +
                "piece=" + getPiece() +
                ", isPlayable=" + canPlay() +
                '}';
    }
}
