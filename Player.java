import java.util.List;

public class Player {
    private String name;
    private char piece;

    public Player(String name, char piece) {
        this.name = name;
        this.piece = piece;
    }

    public String getName() {
        return name;
    }

    public char getPiece() {
        return piece;
    }


    public void setName(String new_name) {
        this.name = new_name;
    }

    public void setPiece(char piece) {
        this.piece = piece;
    }

    @Override
    public String toString() {
        return name + " (" + piece + ")";
    }

    public boolean canMove(Board board) {
        List<Position> positions = new Position[]{board.getBoardPieces()};  // Ensure Board has a getPositions() method returning all positions as a 1D array

        for (int i = 0; i < positions.length; i++) {
            if (positions[i].getPiece() == Position.EMPTY) {  // Check if the position is empty
                int x = i / 8;  // Calculate the row index for a 1D array mapped from 8x8
                int y = i % 8;  // Calculate the column index
                if (board.isLegalMove(piece, x, y)) {
                    return true; // Found a legal move, return true
                }
            }
        }
        return false;  // No legal moves found
    }

    public void makeMove(Board board, int row, int col) {
        if (board == null) {
            throw new IllegalArgumentException("Board cannot be null.");
        }
        if (row < 0 || row >= 8 || col < 0 || col >= 8) {
            throw new IllegalArgumentException("Move out of bounds.");
        }

        board.placePiece(row, col, this.piece);
        board.flipPieces(row, col, this.piece);

    }
}