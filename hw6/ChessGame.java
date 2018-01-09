import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
* The Chess Games
*
* @author klin96
* @version java_1.8.0.144
*/

public class ChessGame {

    private StringProperty event = new SimpleStringProperty(this, "NA");
    private StringProperty site = new SimpleStringProperty(this, "NA");
    private StringProperty date = new SimpleStringProperty(this, "NA");
    private StringProperty white = new SimpleStringProperty(this, "NA");
    private StringProperty black = new SimpleStringProperty(this, "NA");
    private StringProperty result = new SimpleStringProperty(this, "NA");
    private StringProperty opening = new SimpleStringProperty(this, "NA");
    private List<String> moves;

    /**
    * Generate a new Chess Games
    * @param event a String representing the event
    * @param site a String representing the site
    * @param date a String representing the date
    * @param white a String representing the white player
    * @param black a String representing the black player
    * @param result a String representing the result
    */
    public ChessGame(String event, String site, String date,
                     String white, String black, String result) {
        this.event.set(event);
        this.site.set(site);
        this.date.set(date);
        this.white.set(white);
        this.black.set(black);
        this.result.set(result);
        moves = new ArrayList<>();
        this.opening.set("");
    }

    /**
    * @param move a String representing a move
    */
    public void addMove(String move) {
        moves.add(move);
    }

    /**
    * @param n an int indicating which move it is
    *
    * @return a string representing the move wanted
    */
    public String getMove(int n) {
        return moves.get(n - 1);
    }

    /**
    * @return a string representing the event
    */
    public String getEvent() {
        return event.get();
    }
    /**
    * @return a string representing the site
    */
    public String getSite() {
        return site.get();
    }
    /**
    * @return a string representing the data
    */
    public String getDate() {
        return date.get();
    }
    /**
    * @return a string representing the white player
    */
    public String getWhite() {
        return white.get();
    }
    /**
    * @return a string representing the black player
    */
    public String getBlack() {
        return black.get();
    }
    /**
    * @return a string representing the result
    */
    public String getResult() {
        return result.get();
    }

    /**
    * @return a string representing the opening
    */
    public String getOpening() {
        String open = "";
        for (int i = 0; i < moves.size(); i++) {
            open += moves.get(i) + " ";
        }
        if (open.length() >= 21
            && open.substring(0, 21).contains("e4 e5 Nf3 Nc6 Bc4 Bc5")) {
            opening.set("Giuoco Piano");
            return "Giuoco Piano";
        } else if (open.length() >= 5
            && open.substring(0, 5).contains("e4 c5")) {
            opening.set("Sicilian Defence");
            return "Sicilian Defence";
        } else if (open.length() >= 8
            && open.substring(0, 8).contains("d4 d5 c4")) {
            opening.set("Queen's Gambit");
            return "Queen's Gambit";
        } else if (open.length() >= 6
            && open.substring(0, 6).contains("d4 Nf6")) {
            opening.set("Indian Defence");
            return "Indian Defence";
        } else if (open.length() >= 12
            && open.substring(0, 12).contains("e4 e5 Nf3 d6")) {
            opening.set("Philidor Defence");
            return "Philidor Defence";
        } else if (open.length() >= 17
            && open.substring(0, 17).contains("e4 e5 Nf3 Nc6 Bb5")) {
            opening.set("Ruy Lopez");
            return "Ruy Lopez";
        } else {
            opening.set("NOT Given");
            return "NOT Given";
        }
    }
}
