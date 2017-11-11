import java.util.Optional;
/**
* Represents one player's move in the game
*
* @author klin96
* @version java_1.8.0.144
*/
public class Ply {
    private Piece piece;
    private Square from;
    private Square to;
    private Optional<String> comment;

    /**
    * constructs a Ply object
    * @param piece the chess piece moved,
    * @param from the square where the chess comes from,
    * @param to the square where the chess goes to,
    * @param comment an optional<"string"> comment.
    */
    public Ply(Piece piece, Square from, Square to,
        Optional<String> comment) {
        this.piece = piece;
        this.from = from;
        this.to = to;
        this.comment = comment;
    }

    /**
    * @return return the chess that is moved
    */
    public Piece getPiece() {
        return piece;
    }

    /**
    * @return return the square the chess
    * moves from
    */
    public Square getFrom() {
        return from;
    }

    /**
    * @return return the square the chess
    * moves from
    */
    public Square getTo() {
        return to;
    }

    /**
    * @return return the comment on this move
    */
    public Optional<String> getComment() {
        return comment;
    }
}