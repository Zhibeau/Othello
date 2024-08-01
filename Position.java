public class Position {
    private boolean isPlayable;
    private char piece;

    // Constants for piece values
    static final char EMPTY = '_';
    static final char BLACK = 'B';
    static final char WHITE = 'W';

    public Position(char new_piece, boolean b) {
        this.piece = new_piece;
        this.isPlayable = new_piece != EMPTY;  // A position is playable if it is not empty
    }

    public char getPiece() {
        return piece;
    }

    public void setPiece(char new_piece) {
        this.piece = new_piece;
        this.isPlayable = new_piece != EMPTY;  // Update playability when piece changes
    }

    public boolean canPlay() {
        return isPlayable;
    }

    @Override
    public String toString() {
        return "Position{" +
                "piece=" + piece +
                ", isPlayable=" + isPlayable +
                '}';
    }
}