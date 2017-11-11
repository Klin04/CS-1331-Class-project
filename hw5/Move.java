/**
* Represents one turn of the chess
*
* @author klin96
* @version java_1.8.0.144
*/
public class Move {
    private Ply whitePly;
    private Ply blackPly;

    /**
    * constructs a move object
    * @param whitePly a Ply representing the white player
    * @param blackPly a Ply representing the black player
    */
    public Move(Ply whitePly, Ply blackPly) {
        this.whitePly = whitePly;
        this.blackPly = blackPly;
    }

    /**
    * @return return the white player (whitePly)
    */
    public Ply getWhitePly() {
        return whitePly;
    }

    /**
    * @return return the black player (blackPly)
    */
    public Ply getBlackPly() {
        return blackPly;
    }
}