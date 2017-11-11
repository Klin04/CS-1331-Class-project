import java.util.List;
import java.util.ArrayList;
import java.util.function.Predicate;
/**
* Represents a game (a sequence of moves)
*
* @author klin96
* @version java_1.8.0.144
*/
public class ChessGame {
    private List<Move> moves;

    /**
    * Construct a chessgame
    *
    * @param moves a list that contains the moves
    */
    public ChessGame(List<Move> moves) {
        this.moves = moves;
    }

    /**
    * @return return the list containing all the moves
    */
    public List<Move> getMoves() {
        return moves;
    }

    /**
    * @param n an int representing the place of the move
    *
    * @return return the nth move
    */
    public Move getMove(int n) {
        return moves.get(n);
    }

    /**
    * @param filter a predicate
    *
    * @return return the list filtered by the predicate
    */
    public List<Move> filter(Predicate<Move> filter) {
        List<Move> result = new ArrayList<Move>();
        for (Move m: moves) {
            if (filter.test(m)) {
                result.add(m);
            }
        }
        return result;
    }

    /**
    * @return return a list of moves with comments
    */
    public List<Move> getMovesWithComment() {
        return filter((Move m) -> m.getWhitePly().getComment().isPresent()
            || m.getBlackPly().getComment().isPresent());
    }

    /**
    * @return return a list of moves without comments
    */
    public List<Move> getMovesWithoutComment() {
        return filter(new Predicate<Move>() {
            public boolean test(Move m) {
                return !(m.getWhitePly().getComment().isPresent()
                    || m.getBlackPly().getComment().isPresent());
            }
        });
    }

    private class MovePredicate implements Predicate<Move> {
        private Piece p;

        /**
        * construct a new movePredicate object
        */
        public MovePredicate(Piece p) {
            this.p = p;
        }

        /**
        * @param m a move
        *
        * @return true if there is a piece p involved in the move,
        * false there is not
        */
        @Override
        public boolean test(Move m) {
            return (m.getWhitePly().getPiece().equals(this.p)
                || m.getBlackPly().getPiece().equals(this.p));
        }
    }

    /**
    * @param p a piece representing the type
    *
    * @return returns a list of moves with a piece of this type
    */
    public List<Move> getMovesWithPiece(Piece p) {
        return filter(new MovePredicate(p));
    }
}