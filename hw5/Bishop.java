/**
* Represents the bishops
*
* @author klin96
* @version java_1.8.0.144
*/
public class Bishop extends Piece {

    /**
    * Creates a bishop with its color, algebraic name,
    * and fen name.
    *
    *@param color indicates the color of the bishop
    */
    public Bishop(Color color) {
         super(color);
        if (color == Color.BLACK) {
            fenName = "b";
        } else {
            fenName = "B";
        }
        algebraicName = "B";
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
        Square[] temp = new Square[13];
        for (char i = 'a'; i <= 'h'; i++) {
            for (char j = '1'; j <= '8'; j++) {
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