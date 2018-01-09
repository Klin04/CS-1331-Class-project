import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
* The PgnReader that reads files
*
* @author klin96
* @version java_1.8.0.144
*/

public class PgnReader {

    /**
     * Find the tagName tag pair in a PGN game and return its value.
     *
     * @see http://www.saremba.de/chessgml/standards/pgn/pgn-complete.htm
     *
     * @param tagName the name of the tag whose value you want
     * @param game a `String` containing the PGN text of a chess game
     * @return the value in the named tag pair
     */
    public static String tagValue(String tagName, String game) {
        String tagOutput = "NOT GIVEN";
        int firstIndex = game.indexOf(tagName);
        if (firstIndex != -1) {
            tagOutput = game.substring(firstIndex + tagName.length());
            firstIndex = tagOutput.indexOf('"');
            tagOutput = tagOutput.substring(firstIndex + 1);
            firstIndex = tagOutput.indexOf('"');
            tagOutput = tagOutput.substring(0, firstIndex);
        } else {
            return "NOT GIVEN";
        }
        return tagOutput;
    }

    /**
    * Find the right chess to move.
    *
    * @param col a char that represents the column
    * @return return an int that represent the column
    */
    public static int columnTransform(char col) {
        if (col == 'a') {
            return 0;
        } else if (col == 'b') {
            return 1;
        } else if (col == 'c') {
            return 2;
        } else if (col == 'd') {
            return 3;
        } else if (col == 'e') {
            return 4;
        } else if (col == 'f') {
            return 5;
        } else if (col == 'g') {
            return 6;
        } else if (col == 'h') {
            return 7;
        }
        return 0;
    }

    /**
    *Makes the simple moves of pawns
    *
    * @param currentStep a String representing the current step,
    * @param whiteOrBlack a boolean that indicates whether
    * it's a black or white pawn
    * @param currentBoard a 2D-array that shows
    * the current status of the board
    */
    public static void pawnMoves(char[][] currentBoard,
        String currentStep, boolean whiteOrBlack) {
        boolean found = false;
        int endRow = Integer.parseInt(currentStep.substring(1, 2)) - 1;
        if (whiteOrBlack) {
            for (int i = 0; i < endRow; i++) {
                if (currentBoard[i][columnTransform(currentStep.charAt(0))]
                    == 'P') {
                    currentBoard[i][columnTransform(currentStep.charAt(0))]
                        = 'E';
                    currentBoard[endRow]
                       [columnTransform(currentStep.charAt(0))] = 'P';
                    found = true;
                }
                if (found) {
                    i = endRow;
                }
            }
        } else {
            for (int i = currentBoard[0].length - 1; i > endRow; i--) {
                if (currentBoard[i][columnTransform(currentStep.charAt(0))]
                    == 'p') {
                    currentBoard[i][columnTransform(currentStep.charAt(0))]
                        = 'E';
                    currentBoard[endRow]
                    [columnTransform(currentStep.charAt(0))] = 'p';
                }
                if (found) {
                    i = -1;
                }
            }
        }
    }

    /**
    * Makes the simple moves
    *
    * @param currentStep a String representing the current step,
    * @param whiteOrBlack a boolean that indicates whether
    * it's a black or white move,
    * @param currentBoard a 2D-array
    * that shows the current status of the board
    */
    public static void moves(char[][] currentBoard,
        String currentStep, boolean whiteOrBlack) {
        char chess = currentStep.charAt(0);
        boolean found = false;
        if (!whiteOrBlack) {
            currentStep = currentStep.substring(0, 1).toLowerCase()
                + currentStep.substring(1);
        }
        if (currentStep.length() >= 5
            && Character.isLowerCase(currentStep.charAt(1))
            && Character.isDigit(currentStep.charAt(2))
            && Character.isLowerCase(currentStep.charAt(3))
            && Character.isDigit(currentStep.charAt(4))) {
            currentBoard[Integer.parseInt(currentStep.substring(1, 2)) - 1]
            [columnTransform(currentStep.charAt(2))] = 'E';
            currentBoard[Integer.parseInt(currentStep.substring(4, 5)) - 1]
            [columnTransform(currentStep.charAt(3))]
                = currentStep.charAt(0);
        } else if (Character.isLowerCase(currentStep.charAt(1))
            && Character.isLowerCase(currentStep.charAt(2))) {
            char startCol = currentStep.charAt(1);
            currentStep = currentStep.substring(0, 1)
                + currentStep.substring(2);
            int endRow = Integer.parseInt(currentStep.substring(2, 3)) - 1;
            int endCol = columnTransform(currentStep.charAt(1));
            for (int i = 0; i < currentBoard.length; i++) {
                if (currentBoard[i][columnTransform(startCol)]
                    == currentStep.charAt(0)
                    && canReach(chess, i, columnTransform(startCol),
                        endRow, endCol, currentBoard)) {
                    currentBoard[i][columnTransform(startCol)] = 'E';
                    currentBoard[endRow][endCol] = currentStep.charAt(0);
                    found = true;
                }
                if (found) {
                    i = currentBoard.length;
                }
            }
        } else if (Character.isDigit(currentStep.charAt(1))) {
            int startRow = Integer.parseInt(currentStep.substring(1, 2)) - 1;
            currentStep = currentStep.substring(0, 1)
                + currentStep.substring(2);
            int endRow = Integer.parseInt(currentStep.substring(2, 3)) - 1;
            int endCol = columnTransform(currentStep.charAt(1));
            for (int i = 0; i < currentBoard.length; i++) {
                if (currentBoard[startRow][i] == currentStep.charAt(0)
                    && canReach(chess, startRow, i,
                        endRow, endCol, currentBoard)) {
                    currentBoard[startRow][i] = 'E';
                    currentBoard[endRow][endCol] = currentStep.charAt(0);
                    found = true;
                }
                if (found) {
                    i = currentBoard.length;
                }
            }
        } else {
            int endRow = Integer.parseInt(currentStep.substring(2, 3)) - 1;
            int endCol = columnTransform(currentStep.charAt(1));
            for (int i = 0; i < currentBoard.length; i++) {
                for (int j = 0; j < currentBoard[0].length; j++) {
                    if (currentBoard[i][j] == currentStep.charAt(0)
                        && canReach(chess, i, j, endRow, endCol,
                            currentBoard)) {
                        currentBoard[i][j] = 'E';
                        currentBoard[endRow][endCol] = currentStep.charAt(0);
                        found = true;
                    }
                    if (found) {
                        i = currentBoard.length;
                        j = currentBoard[0].length;
                    }
                }
            }
        }
    }

    /**
    *Makes the simple movements of captures
    *
    * @param currentStep a String representing the current step
    * @param whiteOrBlack a boolean that indicates whether
    * it's a black or white move
    * @param currentBoard a 2D-array that shows the current status of the board
    */
    public static void captures(char[][] currentBoard,
        String currentStep, boolean whiteOrBlack) {
        boolean found = false;
        char chess = currentStep.charAt(0);
        if (!whiteOrBlack) {
            currentStep = currentStep.substring(0, 1).toLowerCase()
                + currentStep.substring(1);
        }
        if (Character.isLowerCase(chess)) {
            int currentCol = columnTransform(chess);
            int endRow = Integer.parseInt(currentStep.substring(3, 4)) - 1;
            int endCol = columnTransform(currentStep.charAt(2));
            for (int i = 0; i < currentBoard.length; i++) {
                if (whiteOrBlack && currentBoard[i][currentCol] == 'P'
                    && canReach(chess, i, currentCol, endRow, endCol,
                        currentBoard)) {
                    if (currentBoard[endRow][endCol] == 'E') {
                        currentBoard[endRow - 1][endCol] = 'E';
                    }
                    currentBoard[i][currentCol] = 'E';
                    currentBoard[endRow][endCol] = 'P';
                    found = true;
                    if (found) {
                        i = currentBoard.length;
                    }
                } else if (!whiteOrBlack && currentBoard[i][currentCol] == 'p'
                    && canReach(chess, i, currentCol, endRow, endCol,
                        currentBoard)) {
                    if (currentBoard[endRow][endCol] == 'E') {
                        currentBoard[endRow - 1][endCol] = 'E';
                    }
                    currentBoard[i][currentCol] = 'E';
                    currentBoard[endRow][endCol] = 'p';
                    found = true;
                    if (found) {
                        i = currentBoard.length;
                    }
                }
            }
        } else {
            if (Character.isLowerCase(currentStep.charAt(1))
                && currentStep.charAt(2) == 'x') {
                int startCol = columnTransform(currentStep.charAt(1));
                currentStep = currentStep.substring(0, 1)
                    + currentStep.substring(3);
                int endRow = Integer.parseInt(currentStep.substring(2, 3)) - 1;
                int endCol = columnTransform(currentStep.charAt(1));
                for (int i = 0; i < currentBoard.length; i++) {
                    if (currentBoard[i][startCol]
                        == currentStep.charAt(0)
                        && canReach(chess, i, startCol,
                            endRow, endCol, currentBoard)) {
                        currentBoard[i][startCol] = 'E';
                        currentBoard[endRow][endCol] = currentStep.charAt(0);
                        found = true;
                    }
                    if (found) {
                        i = currentBoard.length;
                    }
                }
            } else if (Character.isLowerCase(currentStep.charAt(1))
                && Character.isDigit(currentStep.charAt(2))) {
                currentBoard[Integer.parseInt(currentStep.substring(2, 3)) - 1]
                [columnTransform(currentStep.charAt(2))] = 'E';
                currentBoard[Integer.parseInt(currentStep.substring(5, 6)) - 1]
                [columnTransform(currentStep.charAt(4))]
                    = currentStep.charAt(0);
            } else if (Character.isDigit(currentStep.charAt(1))
                && currentStep.charAt(2) == 'x') {
                int startRow = Integer.parseInt(currentStep.
                    substring(1, 2)) - 1;
                currentStep = currentStep.substring(0, 1)
                    + currentStep.substring(3);
                int endRow = Integer.parseInt(currentStep.substring(2, 3)) - 1;
                int endCol = columnTransform(currentStep.charAt(1));
                for (int i = 0; i < currentBoard[startRow].length; i++) {
                    if (currentBoard[startRow][i] == currentStep.charAt(0)
                        && canReach(chess, startRow, i,
                            endRow, endCol, currentBoard)) {
                        currentBoard[startRow][i] = 'E';
                        currentBoard[endRow][endCol] = currentStep.charAt(0);
                        found = true;
                    }
                    if (found) {
                        i = currentBoard[startRow].length;
                    }
                }
            } else {
                int endRow = Integer.parseInt(currentStep.substring(3, 4)) - 1;
                int endCol = columnTransform(currentStep.charAt(2));
                for (int i = 0; i < currentBoard.length; i++) {
                    for (int j = 0; j < currentBoard[0].length; j++) {
                        if (currentBoard[i][j] == currentStep.charAt(0)
                            && canReach(chess, i, j, endRow, endCol,
                                currentBoard)) {
                            currentBoard[i][j] = 'E';
                            currentBoard[endRow][endCol] =
                            currentStep.charAt(0);
                            found = true;
                        }
                        if (found) {
                            i = currentBoard.length;
                            j = currentBoard[0].length;
                        }
                    }
                }
            }
        }
    }

    /**
    * Creates a new board (a 2D array) and initializes all
    * locations of the chesses.
    * @return returns a '2D Array' representing the start of the game
    */
    public static char[][] boardGenerator() {
        char[][] chessBoard = {{'R', 'N', 'B', 'Q', 'K', 'B', 'N', 'R'},
                               {'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P'},
                               {'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E'},
                               {'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E'},
                               {'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E'},
                               {'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E'},
                               {'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p'},
                               {'r', 'n', 'b', 'q', 'k', 'b', 'n', 'r'}};
        return chessBoard;
    }

    /**
    * examines if a chess could reach the given place, used to identify which
    * 'N' or 'B' or 'R' to move in each movement
    * @param chess a char identify the kind of chess
    * @param startRow an int showing the row of the starting cell
    * @param startCol an int showing the column of the starting cell
    * @param endCol an int showing the column of the ebd cell
    * @param endRow an int showing the row of the end cell
    * @param chessBoard a 2D array that shows the current chessboard
    * @return return a boolean value that indicates if the movement is valid
    */
    public static boolean canReach(char chess, int startRow, int startCol,
        int endRow, int endCol, char[][] chessBoard) {
        if (chess == 'B' && (Math.abs(startCol - endCol)
            == Math.abs(startRow - endRow))) {
            boolean clear = true;
            int j = startCol;
            if (startCol < endCol && startRow < endRow) {
                for (int i = startRow; i < endRow - 1; i++) {
                    if (chessBoard[i + 1][j + 1] != 'E') {
                        return !clear;
                    }
                    j++;
                }
            } else if (startRow > endRow && startCol < endCol) {
                for (int i = startRow; i > endRow + 1; i--) {
                    if (chessBoard[i - 1][j + 1] != 'E') {
                        return !clear;
                    }
                    j++;
                }
            } else if (startCol > endCol && startRow < endRow) {
                for (int i = startRow; i < endRow - 1; i++) {
                    if (chessBoard[i + 1][j - 1] != 'E') {
                        return !clear;
                    }
                    j--;
                }
            } else if (startCol > endCol && startRow > endRow) {
                for (int i = startRow; i > endRow + 1; i--) {
                    if (chessBoard[i - 1][j - 1] != 'E') {
                        return !clear;
                    }
                    j--;
                }
            }
            return clear;
        } else if (chess == 'N' && (((endRow - startRow)
            * (endRow - startRow) + (endCol - startCol)
            * (endCol - startCol)) == 5)) {
            return true;
        } else if (chess == 'R' && (startCol == endCol || startRow == endRow)) {
            boolean clear = true;
            if (startCol == endCol) {
                for (int i = Math.min(startRow, endRow) + 1;
                    i < Math.max(startRow, endRow); i++) {
                    if (chessBoard[i][startCol] != 'E') {
                        clear = false;
                    }
                    if (!clear) {
                        i = Math.max(startRow, endRow);
                    }
                }
            } else {
                for (int i = Math.min(startCol, endCol) + 1;
                    i < Math.max(startCol, endCol); i++) {
                    if (chessBoard[startRow][i] != 'E') {
                        clear = false;
                    }
                    if (!clear) {
                        i = Math.max(startCol, endCol);
                    }
                }
            }
            return clear;
        } else if (chess == 'K' || chess == 'Q') {
            return true;
        } else if (Character.isLowerCase(chess) && (((endRow - startRow)
            * (endRow - startRow) + (endCol - startCol)
            * (endCol - startCol)) <= 2)) {
            return true;
        }
        return false;
    }

    /**
    *Updates the 2D array that represents the
    *current situation of the chess game.
    * @param currentBoard a 2D array representing the board
    * @param currentStep a String representing the current step
    * @param whiteOrBlack a boolean that indicates whether it's a black or white
    */
    public static void updateBoard(char[][] currentBoard,
        String currentStep, boolean whiteOrBlack) {
        boolean found = false;
        if (currentStep.contains("=")) {
            int promo = currentStep.indexOf("=");
            updateBoard(currentBoard, currentStep.substring(0, promo),
                whiteOrBlack);
            char promoTarget = currentStep.charAt(promo + 1);
            if (!whiteOrBlack) {
                promoTarget = Character.toLowerCase(promoTarget);
            }
            if (!currentStep.contains("x")) {
                currentBoard[Integer.parseInt(currentStep.substring(1, 2)) - 1]
                [columnTransform(currentStep.charAt(0))] = promoTarget;
            } else {
                currentBoard[Integer.parseInt(currentStep.substring(3, 4)) - 1]
                [columnTransform(currentStep.charAt(2))] = promoTarget;
            }
        } else if (currentStep.equals("O-O")) {
            if (whiteOrBlack) {
                currentBoard[0][4] = 'E';
                currentBoard[0][6] = 'K';
                currentBoard[0][7] = 'E';
                currentBoard[0][5] = 'R';
            } else {
                currentBoard[7][4] = 'E';
                currentBoard[7][6] = 'k';
                currentBoard[7][7] = 'E';
                currentBoard[7][5] = 'r';
            }
        } else if (currentStep.equals("O-O-O")) {
            if (whiteOrBlack) {
                currentBoard[0][4] = 'E';
                currentBoard[0][2] = 'K';
                currentBoard[0][0] = 'E';
                currentBoard[0][3] = 'R';
            } else {
                currentBoard[7][4] = 'E';
                currentBoard[7][2] = 'k';
                currentBoard[7][0] = 'E';
                currentBoard[7][3] = 'r';
            }
        } else if (Character.isLowerCase(currentStep.charAt(0))
            && Character.isDigit(currentStep.charAt(1))) {
            pawnMoves(currentBoard, currentStep, whiteOrBlack);
        } else if (!currentStep.contains("x")) {
            moves(currentBoard, currentStep, whiteOrBlack);
        } else if (currentStep.contains("x")) {
            captures(currentBoard, currentStep, whiteOrBlack);
        }
    }

    /**
    *Receives the final situation of the chessboard,
    *and updates final position with the final situation.
    *@param finalBoard a '2D Array' containing the final positions
    *       of the chesses
    *@return the game's final postiton in FEN
    */
    public static String transformer(
        char[][] finalBoard) {
        String finalPos = "";
        int emptySpaces = 0;
        for (int i = 7; i >= 0; i--) {
            for (int j = 0; j < finalBoard[i].length; j++) {
                if (finalBoard[i][j] == 'E') {
                    emptySpaces++;
                    if (j == finalBoard[i].length - 1) {
                        finalPos += emptySpaces;
                        emptySpaces = 0;
                    }
                } else {
                    if (emptySpaces != 0) {
                        finalPos += emptySpaces;
                        emptySpaces = 0;
                    }
                    finalPos += finalBoard[i][j];
                }
            }
            finalPos += '/';
        }
        finalPos = finalPos.substring(0, finalPos.length() - 1);
        return finalPos;
    }


    /**
     * Play out the moves in game and return a String with the game's
     * final position in Forsyth-Edwards Notation (FEN).
     *
     * @see http://www.saremba.de/chessgml/standards/pgn/pgn-complete.htm#c16.1
     *
     * @param game a `String` containing a PGN-formatted chess game or opening
     * @return the game's final position in FEN.
     */
    public static String finalPosition(String game) {
        String finalPosition = "";
        String currentStep = "";
        String steps = game.substring(game.lastIndexOf("]") + 1);
        steps = steps.substring(steps.indexOf("1"));
        String[] everyTurn = steps.split("[0-9]" + "\\.");
        char[][] chessBoard = boardGenerator();
        int elementNum = 1;
        for (int i = 1; i < everyTurn.length; i++) {
            if (everyTurn[i].contains("[0-9]")) {
                everyTurn[i] = everyTurn[i].substring(0,
                    everyTurn[i].indexOf("[0-9]"));
            }
            everyTurn[i] = everyTurn[i].substring(1, everyTurn[i].length() - 1);
            if (Math.log10(i) == elementNum) {
                elementNum++;
            }
            if (Math.log10(i) < elementNum && i < everyTurn.length - 1) {
                everyTurn[i] = everyTurn[i].substring(0,
                    everyTurn[i].length() - elementNum + 1);
            }
        }
        for (int i = 1; i < everyTurn.length; i++) {
            currentStep = everyTurn[i];
            if (currentStep.indexOf(" ") == -1) {
                if (!currentStep.equals("1-0") && !currentStep.equals("0-1")) {
                    updateBoard(chessBoard, currentStep, true);
                    finalPosition = transformer(chessBoard);
                    return finalPosition;
                } else {
                    finalPosition = transformer(chessBoard);
                    return finalPosition;
                }
            } else {
                int space = currentStep.indexOf(" ");
                String white = currentStep.substring(0, space);
                String black = currentStep.substring(space + 1);
                updateBoard(chessBoard, white, true);
                if (!black.equals("1-0") && !black.equals("0-1")) {
                    updateBoard(chessBoard, black, false);
                } else {
                    finalPosition = transformer(chessBoard);
                    return finalPosition;
                }
                if (black.contains("#")) {
                    finalPosition = transformer(chessBoard);
                    return finalPosition;
                }
            }
        }
        finalPosition = transformer(chessBoard);
        return finalPosition;
    }

    /**
     * Reads the file named by path and returns its content as a String.
     *
     * @param path the relative or abolute path of the file to read
     * @return a String containing the content of the file
     */
    public static String fileContent(String path) {
        Path file = Paths.get(path);
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = Files.newBufferedReader(file)) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                // Add the \n that's removed by readline()
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
            System.exit(1);
        }
        return sb.toString();
    }

    /**
    * the main method
    * @param args a string array that's the input from the console
    */
    public static void main(String[] args) {
        String game = fileContent(args[0]);
        System.out.format("Event: %s%n", tagValue("Event", game));
        System.out.format("Site: %s%n", tagValue("Site", game));
        System.out.format("Date: %s%n", tagValue("Date", game));
        System.out.format("Round: %s%n", tagValue("Round", game));
        System.out.format("White: %s%n", tagValue("White", game));
        System.out.format("Black: %s%n", tagValue("Black", game));
        System.out.format("Result: %s%n", tagValue("Result", game));
        System.out.println("Final Position:");
        System.out.println(finalPosition(game));
    }
}
