import java.util.Scanner;
import java.io.*;

public class Game {
    private Board board;
    private Player player1;
    private Player player2;
    private Player currentPlayer;
    private Scanner scanner;

    public Game() {
        this.scanner = new Scanner(System.in);
        this.board = new Board();
    }

    public void start() throws IlegalMoveException{
        System.out.println("Welcome to Othello!");
        while (true) {
            System.out.println("\nPlease select options:");
            System.out.println("1. Quit");
            System.out.println("2. Load a Game");
            System.out.println("3. Start a New Game");

            int choice = getInputChoice();
            switch (choice) {
                case 1:
                    System.out.println("Goodbye!");
                    return;
                case 2:
                    loadGame();
                    break;
                case 3:
                    startNewGame();
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private int getInputChoice() {
        while (!scanner.hasNextInt()) {
            System.out.println("That's not a valid number. Please enter a valid number.");
            scanner.next();
        }
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        return choice;
    }

    private void loadGame() throws IlegalMoveException{
        System.out.println("Enter filename to load:");
        String filename = scanner.nextLine();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String playerName1 = reader.readLine();
            String playerName2 = reader.readLine();
            String currentPlayerName = reader.readLine();
            player1 = new Player(playerName1,'B');
            player2 = new Player(playerName2,'W');
            currentPlayer = currentPlayerName.equals(playerName1) ? player1 : player2;
            board = new Board(reader.readLine()); // assuming Board has a constructor that takes board state as String
            play();
        } catch (IOException e) {
            System.out.println("Error loading game: " + e.getMessage());
        }
    }

    private void startNewGame() throws IlegalMoveException{
        System.out.println("Enter name for Player 1 as Black color:");
        String name1 = scanner.nextLine();
        System.out.println("Enter name for Player 2 as White color:");
        String name2 = scanner.nextLine();
        player1 = new Player(name1,'B');
        player2 = new Player(name2,'W');
        currentPlayer = player1;
        chooseStartingPosition();// assuming default starting board
    }

    private void chooseStartingPosition() throws IlegalMoveException{
        System.out.println("Choose starting position:");
        System.out.println("1. Four-by-Four Starting Position");
        System.out.println("2. Offset starting position");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (choice == 2) {
            System.out.println("Choose offset (1-4):");
            int offset = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            board = new Board(offset);
        } else {
            board = new Board();
        }
        play();
    }

    private String getStringInput(String prompt) {
        System.out.println(prompt);
        return scanner.nextLine();
    }

    private void play() throws IlegalMoveException{
        boolean done;
        while (!board.isGameOver(player1,player2)) {
            done = false;
            try {
                board.displayBoard();
                offerPlayerOptions(); 
            } catch (IlegalMoveException e) {
                System.out.println("Invalid move. Retake your move.");
                while (!done){
                    try{
                        makeMove();
                    } catch (IlegalMoveException e1){
                        System.out.println("Invalid move. Retake your move.");
                    }
            }
            switchPlayer(); // Switch turns
        }
        announceWinner();
        }
    }

    private void offerPlayerOptions() throws IlegalMoveException{
        System.out.println(currentPlayer.getName()+", please choose an option:");
        System.out.println("1. Make a move");
        System.out.println("2. Save game");
        System.out.println("3. Concede game");
        switch (getInputChoice()) {
            case 1:
                makeMove();
                break;
            case 2:
                saveGame();
                break;
            case 3:
                concede();
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    private boolean makeMove() throws IlegalMoveException{
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the X position of your move:");
        int x = scanner.nextInt();
        System.out.println("Enter the Y position of your move:");
        int y = scanner.nextInt();

        if (!board.isLegalMove(currentPlayer.getPiece(), x, y)) {
            throw new IlegalMoveException();
        }
        board.updateBoard(currentPlayer.getPiece(), x, y);
        return true;
    }

    private int getInput(String prompt) {
        System.out.println(prompt);
        return getInputChoice();
    }

    private void switchPlayer() {
        currentPlayer = (currentPlayer == player1) ? player2 : player1;
        System.out.println("It is now " + currentPlayer.getName() + "'s turn.");
    }

    private void saveGame() throws IlegalMoveException{
        System.out.println("Enter filename to save:");
        String filename = scanner.nextLine();
        try (PrintWriter writer = new PrintWriter(filename)) {
            writer.println(player1.getName());
            writer.println(player2.getName());
            writer.println(currentPlayer.getName());
            writer.println(board.getBoardState()); // assuming getBoardState returns a string representation of the board
            System.out.println("Game Saved Successfully!");
        } catch (IOException e) {
            System.out.println("Failed to save game: " + e.getMessage());
        }
        start();
    }

    private void concede() throws IlegalMoveException{
        System.out.println(currentPlayer.getName() + " has conceded the game.");
        switchPlayer();
        announceWinner();
        start();
    }

    private void announceWinner() {
        System.out.println("Congratulations, " + currentPlayer.getName() + ", you are the winner!");
    }
}