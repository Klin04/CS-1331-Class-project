/**
* Represents the squares on a chessboard
*
*@author klin96
*/
public class Square {
    private char file;
    private char rank;

    /**
    * Creates a square with a file and a rank
    *
    * @param file the file of the square
    * @param rank the rank of the square
    */
    public Square(char file, char rank) {
        this.file = file;
        this.rank = rank;
    }

    /**
    * Creates a square with a file and a rank
    *
    * @param name the file and rank of the square
    */
    public Square(String name) {
        file = name.charAt(0);
        rank = name.charAt(1);
    }

    /**
    * @return the string representation of the square
    */
    @Override
    public String toString() {
        String st = "";
        st += file;
        st += rank;
        return st;
    }

    /**
    * @param sq2 another square on a chessboard
    *
    * @return if two squares are the same square
    * on a chessboard
    */
    @Override
    public boolean equals(Object sq2) {
        if (sq2 == null) {
            return false;
        }
        if (this == sq2) {
            return true;
        }
        if (!(sq2 instanceof Square)) {
            return false;
        }
        Square sq3 = (Square) sq2;
        return (this.file == sq3.file) && (this.rank == sq3.rank);
    }

    /**
    * @return a character indicates the file of the space
    */
    public char getFile() {
        return this.file;
    }

    /**
    * @return a character indicates the rank of the space
    */
    public char getRank() {
        return this.rank;
    }
}