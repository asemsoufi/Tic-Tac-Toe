package tictactoe;

import java.util.Arrays;
import java.util.Scanner;



public class Main {

    private static char[][] field;
    private static int emptyCellsCount = 0;
    private static int xCellsCount = 0;
    private static int oCellsCount = 0;
    private static boolean xWon = false;
    private static boolean oWon = false;
    private static boolean gameOn = true;
    private static char charTurn = 'X'; // this will change as players switch turns


    private static final String[][] validcoordinatesArray = new String[][] {{"(1,3)", "(2,3)", "(3,3)"}, {"(1,2)",
            "(2,2)", "(3,2)"}, {"(1,1)", "(2,1)", "(3,1)"}};

    public static void main(String[] args) {
        /* old version: System.out.print("Enter cells: ");
        Scanner scanner = new Scanner(System.in);
        // get initial incomplete user input as a single string
        // String inputString = scanner.next();

        // fill matrix with available input then print it to the screen
        field = getFieldMatrix(inputString); */
        field = getFieldMatrix();
        printFieldMatrix(field);

        // get next valid move from user
        while (gameOn) {
            processNextMove(charTurn);
            if (charTurn == 'X') {
                charTurn = 'O';
            } else {
                charTurn = 'X';
            }
            checkGameStatus();
        }
        // print status
        if (xWon) {
            System.out.println("X wins");
        } else if (oWon) {
            System.out.println("O wins");
        } else if (emptyCellsCount == 0) {
            System.out.println("Draw");
        }

        // hold for now: displayGameStatus();
    }

    private static void printFieldMatrix(char[][] field) {
        System.out.println("---------");
        for (int i = 0; i < 3; i++) {
            System.out.print("| ");
            for (int j = 0; j < 3; j++) {
                System.out.print(field[i][j] + " ");
            }
            System.out.println("|");
        }
        System.out.println("---------");
    }

    public static char[][] getFieldMatrix() {
        // extract characters from user's string
        // char[] chars = inputStr.toCharArray();
        // create a 3x3 field using these characters
        char[][] fieldMatrix = new char[3][3];
        int index = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                // fieldMatrix[i][j] = (chars[index] == 'O' || chars[index] == 'X') ? chars[index] : ' ';
                fieldMatrix[i][j] = ' ';
                index++;
            }
        }
        return fieldMatrix;
    }

    public static void processNextMove(char ch) {
        boolean numeric = false;
        int iCoordinate = -1;
        int jCoordinate = -1;

        int xCoordinate = -1;
        int yCoordinate = -1;

        while (!numeric) {
            System.out.print("Enter the coordinates: ");
            Scanner scanner = new Scanner(System.in);
            String xStr = scanner.next();
            String yStr = scanner.next();
            try {
                xCoordinate = Integer.parseInt(xStr);
                yCoordinate = Integer.parseInt(yStr);
                numeric  =true;
            } catch(NumberFormatException e) {
                System.out.println("You should enter numbers!");
            }
        }

        if (xCoordinate < 1 || xCoordinate > 3 || yCoordinate < 1 || yCoordinate > 3) {
            System.out.println("Coordinates should be from 1 to 3!");
            processNextMove(ch);
        }
        String userCoordinate =  "(" + xCoordinate + "," + yCoordinate + ")";

        // validate input has numbers and matches available cell
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (userCoordinate.equals(validcoordinatesArray[i][j])) {
                    iCoordinate = i;
                    jCoordinate = j;
                    break;
                }
            }
        }

        if (iCoordinate >=0 && jCoordinate >= 0) {
            if (field[iCoordinate][jCoordinate] == ' ') {
                field[iCoordinate][jCoordinate] = ch;
                printFieldMatrix(field);
            } else {
                System.out.println("This cell is occupied! Choose another one!");
                processNextMove(ch);
            }
        }

    }

    public static void checkGameStatus() {
        int xPerRow = 0;
        int oPerRow = 0;
        char[] rightDiagonlaRow = new char[]{field[0][0], field[1][1], field[2][2]};
        char[] leftDiagonlaRow = new char[]{field[0][2], field[1][1], field[2][0]};
        char[] verticalRow1 = new char[]{field[0][0], field[1][0], field[2][0]};
        char[] verticalRow2 = new char[]{field[0][1], field[1][1], field[2][1]};
        char[] verticalRow3 = new char[]{field[0][2], field[1][2], field[2][2]};

        for (char[] row : field) {  // check each row
            for (char ch : row) {   // check each cell
                switch (ch) {
                    case 'X':
                        xPerRow++;
                        xCellsCount++;
                        break;
                    case 'O':
                        oPerRow++;
                        oCellsCount++;
                        break;
                    default: // case' '
                        emptyCellsCount++;
                        break;
                }
                // check if we have a winner on this row
                if (xPerRow == 3) {
                    xWon = true;
                    gameOn = false;
                    return;
                    // error check: System.out.println("X wins normally");
                } else if (oPerRow == 3) {
                    oWon = true;
                    gameOn = false;
                    return;
                    // error check: System.out.println("X wins normally");
                }
            }
            // reset row counters
            xPerRow = 0;
            oPerRow = 0;
        }
        // check diagonal rows
        if (Arrays.equals(rightDiagonlaRow, new char[]{'X', 'X', 'X'}) ||
                Arrays.equals(leftDiagonlaRow, new char[]{'X', 'X', 'X'})) {
            xWon = true;
            gameOn = false;
            return;
            // error check: System.out.println("X wins diagonally");
        } else if (Arrays.equals(rightDiagonlaRow, new char[]{'O', 'O', 'O'}) ||
                Arrays.equals(leftDiagonlaRow, new char[]{'O', 'O', 'O'})) {
            oWon = true;
            gameOn = false;
            return;
            // error check: System.out.println("O wins diagonally");
        }
        // check vertical rows
        if (Arrays.equals(verticalRow1, new char[]{'X', 'X', 'X'}) ||
                Arrays.equals(verticalRow2, new char[]{'X', 'X', 'X'}) ||
                Arrays.equals(verticalRow3, new char[]{'X', 'X', 'X'})) {
            xWon = true;
            gameOn = false;
            return;
            // error check: System.out.println("X wins diagonally");
        }
        if (Arrays.equals(verticalRow1, new char[]{'O', 'O', 'O'}) ||
                Arrays.equals(verticalRow2, new char[]{'O', 'O', 'O'}) ||
                Arrays.equals(verticalRow3, new char[]{'O', 'O', 'O'})) {
            oWon = true;
            gameOn = false;
            // error check: System.out.println("O wins diagonally");
        }

        /* old version: if (Math.abs(xCellsCount - oCellsCount) > 1 || xWon && oWon) {
            System.out.println("Impossible");
            } else if (xWon) {
                System.out.println("X wins");
            } else if (oWon) {
                System.out.println("O wins");
            } else if (emptyCellsCount == 0) {
                System.out.println("Draw");
            } else {
                System.out.println("Game not finished");
            }*/
    }
}
