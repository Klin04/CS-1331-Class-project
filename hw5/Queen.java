/**
* Represents the queens
*
* @author klin96
* @version java_1.8.0.144
*/
public class Queen extends Piece {

    /**
    * Creates a queen with its color, algebraic name,
    * and fen name.
    *
    *@param color indicates the color of the queen
    */
    public Queen(Color color) {
         super(color);
        if (color == Color.BLACK) {
            fenName = "q";
        } else {
            fenName = "Q";
        }
        algebraicName = "Q";
    }

    @Override
    public String algebraicName() {
        return this.algebraicName;
    }

    @Override
    public String fenName() {
        return this.fenName;
    }

    @Override
    public Square[] movesFrom(Square square) {
        Square[] temp = new Square[27];
        int num = 0;
        for (char i = 'a'; i <= 'h'; i++) {
            for (char j = '1'; j <= '8'; j++) {
                if (square.getFile() == i
                    || square.getRank() == j) {
                    Square toReach = new Square(i, j);
                    if (!toReach.equals(square)) {
                        temp[num] = toReach;
                        num++;
                    }
                }
                if (Math.abs(i - square.getFile())
                    == Math.abs(j - square.getRank())) {
                    Square toReach = new Square(i, j);
                    if (!toReach.equals(square)) {
                        temp[num] = toReach;
                        num++;
                    }
                }
            }
        }
        int totalMoves = 0;
        for (int i = 0; i < temp.length; i++) {
            if (temp[i] != null) {
                totalMoves++;
            } else {
                i = temp.length;
            }
        }
        Square[] canReach = new Square[totalMoves];
        if (totalMoves != -1) {
            for (int i = 0; i < totalMoves; i++) {
                canReach[i] = temp[i];
            }
        } else {
            return temp;
        }
        return canReach;
    }
}