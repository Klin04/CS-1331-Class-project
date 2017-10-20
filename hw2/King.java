/**
* Represents the kings
*
* @author klin96
*/
public class King extends Piece {

    /**
    * Creates a king with its color, algebraic name,
    * and fen name.
    *
    *@param color indicates the color of the king
    */
    public King(Color color) {
        super(color);
        if (color == Color.BLACK) {
            fenName = "k";
        } else {
            fenName = "K";
        }
        algebraicName = "K";
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
        int num = 0;
        Square[] temp = new Square[8];
        for (char i = (char) (square.getFile() - 1);
            i <= square.getFile() + 1; i++) {
            for (char j = (char) (square.getRank() - 1);
                j <= square.getRank() + 1; j++) {
                if ('a' <= i && i <= 'h' && '1' <= j && j <= '8') {
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