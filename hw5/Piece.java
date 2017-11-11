/**
* Represents pieces on a chessboard
*
* @author klin96
* @version java_1.8.0.144
*/
public abstract class Piece {
    protected Color color;
    protected String algebraicName;
    protected String fenName;

    /**
    * Creates a piece with its color,
    * its algebraic name, and its fen name
    *
    * @param color the color of the piece
    */
    public Piece(Color color) {
        this.color = color;
    }

    /**
    * @return return the color of the piece
    */
    public Color getColor() {
        return this.color;
    }

    /**
    * @param o an Object
    *
    * @return true if two piece is the same
    * false if it's not
    */
    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (!(o instanceof Piece)) {
            return false;
        }
        if (o == this) {
            return true;
        }
        Piece p1 = (Piece) o;
        return this.algebraicName == p1.algebraicName;
    }

    /**
    * @return the hashcode of the piece
    */
    @Override
    public int hashCode() {
        return algebraicName.hashCode();
    }

    /**
    * @return return the algebraic name of the piece
    */
    public abstract String algebraicName();

    /**
    * @return return the fen name of the piece
    */
    public abstract String fenName();

    /**
    * @param square the sqaure the piece is in
    *
    * @return return all squares a piece could reach
    */
    public abstract Square[] movesFrom(Square square);
}