/**
* Represents the rooks
*
* @author klin96
*/
public class Rook extends Piece {

    /**
    * Creates a rook with its color, algebraic name,
    * and fen name.
    *
    *@param color indicates the color of the rook
    */
    public Rook(Color color) {
        super(color);
        if (color == Color.BLACK) {
            fenName = "r";
        } else {
            fenName = "R";
        }
        algebraicName = "R";
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
        Square[] canReach = new Square[14];
        int num = 0;
        for (char i = 'a'; i <= 'h'; i++) {
            if (i != square.getFile()) {
                canReach[num] = new Square(i, square.getRank());
                num++;
            }
        }
        for (char j = '1'; j <= '8'; j++) {
            if (j != square.getRank()) {
                canReach[num] = new Square(square.getFile(), j);
                num++;
            }
        }
        return canReach;
    }
}