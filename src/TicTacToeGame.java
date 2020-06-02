import java.util.Scanner;

public class TicTacToeGame {
    private final Scanner scanner;
    private final char[][] gameBoard;
    private boolean running = true;
    private boolean xTurn = true;
    private int row = 0;
    private int column = 0;

    public TicTacToeGame() {
        this.gameBoard = new char[3][3];

        for (int i = 0; i < this.gameBoard.length; i++) {
            for (int j = 0; j < this.gameBoard.length; j++) {
                this.gameBoard[i][j] = '_';
            }
        }

        this.scanner = new Scanner(System.in);
    }

    public void play() {
        displayInstructions();
        displayGameBoard();

        while (this.running) {
            if (validateInput()) {
                if (this.xTurn) {
                    fillCurrentCell('X');
                    this.xTurn = false;
                } else {
                    fillCurrentCell('O');
                    this.xTurn = true;
                }
                displayGameBoard();
                outputBoardState();
            }
        }
    }

    private void displayInstructions() {
        System.out.println("+--------------------------------------------------------------------------------+");
        System.out.println("|                              Instructions                                      |");
        System.out.println("+--------------------------------------------------------------------------------+");
        System.out.println("|    Game starts with X and alternates between X and O from there.               |");
        System.out.println("|    To place an X or O, input the row number, a space, then the column number.  |");
        System.out.println("|    Example: 1 1 <--- Places an X or O at the top left of the board.            |");
        System.out.println("|    The row number and column number ranges between 1 and 3.                    |");
        System.out.println("+--------------------------------------------------------------------------------+");
    }

    private void displayGameBoard() {
        System.out.println("---------");

        for (int i = 0; i < 3; i++) {
            System.out.print("| ");

            for (int j = 0; j < 3; j++) {
                char currentCharacter = this.gameBoard[i][j];

                if (currentCharacter == '_') {
                    System.out.print(" ");
                } else {
                    System.out.print(currentCharacter);
                }

                System.out.print(" ");
            }

            System.out.println("|");
        }

        System.out.println("---------");
    }

    private boolean validateInput() {
        System.out.print("Enter co-ordinates: ");

        int row;
        int column;

        try {
            row = Integer.parseInt(this.scanner.next()) - 1;
            column = Integer.parseInt(this.scanner.next()) - 1;
        } catch (NumberFormatException e) {
            System.out.println("Please enter numbers.");
            return false;
        }

        if (row > 3 || column > 3 || row < 0 || column < 0) {
            System.out.println("Co-ordinates should be from 1 to 3.");
            return false;
        }

        if (this.gameBoard[row][column] != '_') {
            System.out.println("This cell is occupied. Choose another one.");
            return false;
        }

        this.row = row;
        this.column = column;
        return true;
    }

    private void fillCurrentCell(char playerCharacter) {
        this.gameBoard[this.row][this.column] = playerCharacter;
    }

    private void outputBoardState() {
        int xWins = 0;
        int oWins = 0;

        // Check how many wins X and O have in rows and columns
        for (int i = 0; i < 3; i++) {
            int rowSum = 0;
            int columnSum = 0;


            for (int j = 0; j < 3; j++) {
                char currentRowCharacter = this.gameBoard[i][j];
                char currentColumnCharacter = this.gameBoard[j][i];

                rowSum += currentRowCharacter;
                columnSum += currentColumnCharacter;

            }

            if (rowSum == 3 * 'X' || columnSum == 3 * 'X') {
                xWins++;
            }

            if (rowSum == 3 * 'O' || columnSum == 3 * 'O') {
                oWins++;
            }
        }

        // Check how many wins X and O have in right and left diagonals
        int diagonalRightSum = 0;
        int diagonalLeftSum = 0;

        for (int i = 0; i < 3; i++) {


            char currentDiagonalRightCharacter = this.gameBoard[i][i];
            char currentDiagonalLeftCharacter = this.gameBoard[i][2 - i];

            diagonalRightSum += currentDiagonalRightCharacter;
            diagonalLeftSum += currentDiagonalLeftCharacter;

        }

        if (diagonalRightSum == 3 * 'X' || diagonalLeftSum == 3 * 'X') {
            xWins++;
        }

        if (diagonalRightSum == 3 * 'O' || diagonalLeftSum == 3 * 'O') {
            oWins++;
        }



        // Count numbers of Xs and Os in board and if there are empty spaces
        boolean hasEmpty = false;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (this.gameBoard[i][j] == '_') {
                    hasEmpty = true;
                    break;
                }
            }
        }

        // Check if game is not finished or is at a draw
        boolean noWins = xWins + oWins == 0;

        if (hasEmpty && noWins) {
            return;
        }
        if (!hasEmpty && noWins){
            System.out.println("Draw!");
            this.running = false;
        }

        boolean tooManyWins = xWins + oWins > 1;

        if (xWins > oWins && !tooManyWins) {
            System.out.println("X wins!");
            this.running = false;
        }

        if (oWins > xWins && !tooManyWins) {
            System.out.println("O wins!");
            this.running = false;
        }
    }
}
