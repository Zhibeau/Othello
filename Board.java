import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Board {
    private List<Position> boardPieces;
    private String name;

    public Board() {
        this.boardPieces = new LinkedList<>();
        // Set up the board with specific initial positions
        addPositions(18, Position.EMPTY);
        addPositions(2, Position.WHITE);
        addPositions(2, Position.BLACK);
        addPositions(4, Position.EMPTY);
        addPositions(2, Position.WHITE);
        addPositions(2, Position.BLACK);
        addPositions(4, Position.EMPTY);
        addPositions(2, Position.BLACK);
        addPositions(2, Position.WHITE);
        addPositions(4, Position.EMPTY);
        addPositions(2, Position.BLACK);
        addPositions(2, Position.WHITE);
        addPositions(18, Position.EMPTY);
    }

    public Board(int offset) {
        this(); // Initialize with standard setup
        boardPieces.clear(); // Clear the initial setup for offset configuration
        switch (offset) {
            case 1:
                // Configure specific board setup based on offset 1
                addPositions(18, Position.EMPTY);
                addPositions(1, Position.WHITE);
                addPositions(1, Position.BLACK);
                addPositions(6, Position.EMPTY);
                addPositions(1, Position.BLACK);
                addPositions(1, Position.WHITE);
                addPositions(36, Position.EMPTY);
                break;
            case 2:
                // Configure specific board setup based on offset 2
                addPositions(20, Position.EMPTY);
                addPositions(1, Position.WHITE);
                addPositions(1, Position.BLACK);
                addPositions(6, Position.EMPTY);
                addPositions(1, Position.BLACK);
                addPositions(1, Position.WHITE);
                addPositions(34, Position.EMPTY);
                break;
            case 3:
                // Configure specific board setup based on offset 3
                addPositions(34, Position.EMPTY);
                addPositions(1, Position.WHITE);
                addPositions(1, Position.BLACK);
                addPositions(6, Position.EMPTY);
                addPositions(1, Position.BLACK);
                addPositions(1, Position.WHITE);
                addPositions(20, Position.EMPTY);
                break;
            case 4:
                // Configure specific board setup based on offset 4
                addPositions(36, Position.EMPTY);
                addPositions(1, Position.WHITE);
                addPositions(1, Position.BLACK);
                addPositions(6, Position.EMPTY);
                addPositions(1, Position.BLACK);
                addPositions(1, Position.WHITE);
                addPositions(18, Position.EMPTY);
                break;
        }
    }

    public Board(String savedPieces) {
        this.boardPieces = new LinkedList<>();
        for (int i = 0; i < savedPieces.length(); i++) {
            char piece = savedPieces.charAt(i);
            this.boardPieces.add(new Position(piece, piece != Position.EMPTY));
        }
    }

    public void setName(String newName) {
        this.name = newName;
    }

    private void addPositions(int count, char pieceType) {
        for (int i = 0; i < count; i++) {
            this.boardPieces.add(new Position(pieceType, pieceType != Position.EMPTY));
        }
    }

    public void displayBoard() {
        System.out.println("Current Board:");
        for (int i = 0; i < 64; i++) {
            System.out.print(boardPieces.get(i).getPiece() + " ");
            if ((i + 1) % 8 == 0) {
                System.out.println();
            }
        }
    }

    public boolean isGameOver(Player Player1, Player Player2) {
        // Game is over if no legal moves are available for any player
        return !canPlayerMove(Player1) && !canPlayerMove(Player2);
    }

    public boolean canPlayerMove(Player player) {
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                if (boardPieces.get(y * 8 + x).getPiece() == Position.EMPTY) {
                    if (isLegalMove(player.getPiece(), x, y)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    boolean isLegalMove(char playerPiece, int x, int y) {
        char opponentPiece = (playerPiece == Position.BLACK) ? Position.WHITE : Position.BLACK;
        int[] dx = {-1, -1, -1, 0, 1, 1, 1, 0};
        int[] dy = {-1, 0, 1, 1, 1, 0, -1, -1};
        for (int dir = 0; dir < 8; dir++) {
            int nx = x + dx[dir], ny = y + dy[dir];
            int count = 0;
            while (nx >= 0 && nx < 8 && ny >= 0 && ny < 8 && boardPieces.get(ny * 8 + nx).getPiece() == opponentPiece) {
                nx += dx[dir];
                ny += dy[dir];
                count++;
            }
            if (count > 0 && nx >= 0 && nx < 8 && ny >= 0 && ny < 8 && boardPieces.get(ny * 8 + nx).getPiece() == playerPiece) {
                return true;
            }
        }
        return false;
    }

    public List<Position> getBoardPieces() {
        return boardPieces;
    }

    public String getBoardState() {
        StringBuilder sb = new StringBuilder();
        for (Position pos : boardPieces) {
            sb.append(pos.getPiece());
        }
        return sb.toString();
    }

    public boolean makeMove(Player currentPlayer, int x, int y) {
        if (x < 0 || x >= 8 || y < 0 || y >= 8) {  // Check bounds
            System.out.println("Move out of bounds. Please try again.");
            return false;
        }

        if (!boardPieces.get(y * 8 + x).canPlay()) {  // Check if the move is legal
            System.out.println("Invalid move. Please try again.");
            return false;
        }

        // Assuming you have a method to check if a move flips any opponent pieces
        if (!canFlipAny(currentPlayer.getPiece(), x, y)) {
            System.out.println("No pieces to flip. Move is not legal.");
            return false;
        }

        // Perform the move, updating the board state
        updateBoard(currentPlayer.getPiece(), x, y);
        return true;  // Return true if the move was successfully made
    }

    private boolean canFlipAny(char playerPiece, int x, int y) {
        // This method would contain the logic to check if placing a piece at (x, y) will flip at least one opponent piece
        List<Position> originalPositions = this.boardPieces;
        List<Position> flipedPositions = flipPieces(x, y, playerPiece, boardPieces);
        if (flipedPositions.equals(originalPositions)){
            return true;
        } else {
            return false; // Placeholder
        }
    }

    void updateBoard(char piece, int x, int y) {
        // Update the board at position (x, y) and potentially flip opponent pieces
        boardPieces.get(y * 8 + x).setPiece(piece);
        // Assuming you have a method to handle flipping logic
        boardPieces = flipPieces(x, y, piece, boardPieces);
    }

    private List<Position> flipPieces(int x, int y, char piece, List<Position> flipedPositions) {
        int[] directionsX = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] directionsY = {-1, 0, 1, -1, 1, -1, 0, 1};

        for (int i = 0; i < directionsX.length; i++) {
            int currentX = x + directionsX[i];
            int currentY = y + directionsY[i];
            List<Position> toFlip = new ArrayList<>();

            // Move in the direction until you hit the edge of the board or a different piece
            while (currentX >= 0 && currentX < 8 && currentY >= 0 && currentY < 8
                    && flipedPositions.get(currentY * 8 + currentX).getPiece() == (piece == Position.BLACK ? Position.WHITE : Position.BLACK)) {
                toFlip.add(flipedPositions.get(currentY * 8 + currentX));
                currentX += directionsX[i];
                currentY += directionsY[i];
            }

            // Confirm that the line of pieces to flip is terminated by a piece of the current player's color
            if (currentX >= 0 && currentX < 8 && currentY >= 0 && currentY < 8
                    && flipedPositions.get(currentY * 8 + currentX).getPiece() == piece) {
                for (Position pos : toFlip) {
                    pos.setPiece(piece);
                }
            }
        }
        return flipedPositions;
    }

    public void placePiece(int row, int col, char piece) {
    }
}