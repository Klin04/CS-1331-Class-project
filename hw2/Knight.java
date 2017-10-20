/**
* Represents the knights
*
* @author klin96
*/
public class Knight extends Piece {

    /**
    * Creates a knight with its color, algebraic name,
    * and fen name.
    *
    *@param color indicates the color of the knight
    */
    public Knight(Color color) {
        super(color);
        if (color == Color.BLACK) {
            fenName = "n";
        } else {
            fenName = "N";
        }
        algebraicName = "N";
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
        Square[] temp = new Square[8];
        int num = 0;
        for (char i = 'a'; i <= 'h'; i++) {
            for (char j = '1'; j <= '8'; j++) {
                if ((square.getFile() - i)
                    * (square.getFile() - i)
                    + (square.getRank() - j)
                    * (square.getRank() - j) == 5) {
                    temp[num] = new Square(i, j);
                    num++;
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