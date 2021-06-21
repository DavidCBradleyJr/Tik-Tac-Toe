import jdk.swing.interop.SwingInterOpUtils;

import java.util.*;

public class GameRunner {

    private static final GameBoard game = new GameBoard();
    private static char symbol = ' ';
    private static HashMap<Integer, String> positions = new HashMap<>();
    private static boolean gameDone = false;
    private static ArrayList<Integer> playerPositions = new ArrayList<>();
    private static ArrayList<Integer> cpuPositions = new ArrayList<>();


    public static void printGameBoard(char[][] gameBoard) {
        positions.put(1, (game.getGameBoard())[0][0] + "");
        positions.put(2, (game.getGameBoard())[0][2] + "");
        positions.put(3, (game.getGameBoard())[0][4] + "");
        positions.put(4, (game.getGameBoard())[2][0] + "");
        positions.put(5, (game.getGameBoard())[2][2] + "");
        positions.put(6, (game.getGameBoard())[2][4] + "");
        positions.put(7, (game.getGameBoard())[4][0] + "");
        positions.put(8, (game.getGameBoard())[4][2] + "");
        positions.put(9, (game.getGameBoard())[4][4] + "");

        for (char[] row : gameBoard) {
            for (char column : row) {
                System.out.print(column);
            }
            System.out.println();
        }

    }

    public static void setPosition(String user) {
        if (user.equals("player")) {
            System.out.print("Where do you want to place your X (Number position 1-9, left to right)? ");
            Scanner scan = new Scanner(System.in);
            int pos = scan.nextInt();
            game.setPlayerPos(pos);
            boolean isEmpty = positions.get(pos).equals(" ");
            symbol = 'X';
            if(isEmpty) {
                playerPositions.add(pos);
            } else {
                System.out.println("That spot is already taken! Try again.");
                setPosition("player");
            }
        } else if (user.equals("cpu")) {
            Random random = new Random();
            int cpuPos = random.nextInt(9 - 1 + 1) + 1;
            boolean isEmpty = positions.get(cpuPos).equals(" ");
            if (isEmpty) {
                game.setPlayerPos(cpuPos);
                cpuPositions.add(cpuPos);
            } else {
                setPosition("cpu");
            }
            symbol = 'O';
        }

        char[][] gameBoard = game.getGameBoard();
        switch (game.getPlayerPos()) {
            case 1:
                gameBoard[0][0] = symbol;
                break;
            case 2:
                gameBoard[0][2] = symbol;
                break;
            case 3:
                gameBoard[0][4] = symbol;
                break;
            case 4:
                gameBoard[2][0] = symbol;
                break;
            case 5:
                gameBoard[2][2] = symbol;
                break;
            case 6:
                gameBoard[2][4] = symbol;
                break;
            case 7:
                gameBoard[4][0] = symbol;
                break;
            case 8:
                gameBoard[4][2] = symbol;
                break;
            case 9:
                gameBoard[4][4] = symbol;
                break;
            default:
                break;
        }
        game.setGameBoard(gameBoard);
        printGameBoard(game.getGameBoard());
    }

    public static boolean checkIfGameOver() {
        List topRow = Arrays.asList(1, 2, 3);
        List midRow = Arrays.asList(4, 5, 6);
        List botRow = Arrays.asList(7, 8, 9);
        List leftCol = Arrays.asList(1, 4, 7);
        List midCol = Arrays.asList(2, 5, 8);
        List rightCol = Arrays.asList(3, 6, 9);
        List firstDiagonal = Arrays.asList(1, 5, 9);
        List secondDiagonal = Arrays.asList(3, 5, 7);

        List<List> winConditions = new ArrayList<>();
        winConditions.add(topRow);
        winConditions.add(midRow);
        winConditions.add(botRow);
        winConditions.add(leftCol);
        winConditions.add(midCol);
        winConditions.add(rightCol);
        winConditions.add(firstDiagonal);
        winConditions.add(secondDiagonal);

        for (List l : winConditions) {
            if (playerPositions.containsAll(l)) {
                System.out.println("You win, playa!");
                return true;
            } else if (cpuPositions.containsAll(l)) {
                System.out.println("Bummer, try again?");
                return true;
            } else if (cpuPositions.size() + playerPositions.size() == 9) {
                System.out.println("Stalemate!");
                return true;
            }
        }
        return false;
    }

    public static void main(String args[]) {
        printGameBoard(game.getGameBoard());
        int turn = 0;
        while (!gameDone) {
            if (turn == 0) {
                setPosition("player");
                turn = 1;
            } else if (turn == 1) {
                setPosition("cpu");
                turn = 0;
            }
           gameDone = checkIfGameOver();
        }
    }
}

