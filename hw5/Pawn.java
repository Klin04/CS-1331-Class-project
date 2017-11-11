/**
* Represents the pawns
*
* @author klin96
* @version java_1.8.0.144
*/
public class Pawn extends Piece {

    /**
    * Creates a pawn with its color, algebraic name,
    * and fen name.
    *
    *@param color indicates the color of the pawn
    */
    public Pawn(Color color) {
         super(color);
        if (color == Color.BLACK) {
            fenName = "p";
        } else {
            fenName = "P";
        }
        algebraicName = "";
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
        Square[] temp = new Square[2];
        Square[] canReach = new Square[1];
        if (square.getRank() == '7'
            && this.color == Color.BLACK) {
            temp[0] = new Square(square.getFile(), '6');
            temp[1] = new Square(square.getFile(), '5');
            return temp;
        } else if (square.getRank() == '2'
            && this.color == Color.WHITE) {
            temp[0] = new Square(square.getFile(), '3');
            temp[1] = new Square(square.getFile(), '4');
            return temp;
        } else {
            if (this.color == Color.WHITE
                && square.getRank() != '8') {
                canReach[0] = new Square(square.getFile(),
                    (char) (square.getRank() + 1));
            } else if (this.color == Color.BLACK
                && square.getRank() != '1') {
                canReach[0] = new Square(square.getFile(),
                    (char) (square.getRank() - 1));
            } else {
                canReach = new Square[0];
            }
        }
        return canReach;
    }
}