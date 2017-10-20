public class testChess {
    public static void main(String[] args) {
        Piece king = new King(Color.BLACK);
        System.out.println(king.algebraicName());
        System.out.println(king.algebraicName().equals("N"));
        System.out.println(king.fenName());
        System.out.println(king.fenName().equals("n"));
        Piece queen = new Queen(Color.WHITE);
        Piece knight = new Knight(Color.BLACK);
        Piece pawn = new Pawn(Color.WHITE);
        System.out.println(knight.algebraicName());
        System.out.println(knight.fenName());
        Square[] attackedSquares1 = queen.movesFrom(new Square("f6"));
        Square[] attackedSquares = king.movesFrom(new Square("f6"));
        Square[] attackedSquares2 = knight.movesFrom(new Square("g7"));
        Square[] attackedSquares3 = pawn.movesFrom(new Square("e8"));
        for (int i = 0; i < attackedSquares.length; i++) {
            System.out.println(attackedSquares[i]);
        }
        for (int i = 0; i < attackedSquares1.length; i++) {
            System.out.println(attackedSquares1[i]);
        }
        for (int i = 0; i < attackedSquares3.length; i++) {
            System.out.println(attackedSquares3[i]);
        }
        //for (int i = 0; i < attackedSquares2.length; i++) {
          //  System.out.println(attackedSquares2[i]);
        //}
        // test that attackedSquares contains e8, g8, etc.
        Square a1 = new Square("a1");
        Square otherA1 = new Square('a', '1');
        Square h8 = new Square("h8");
        System.out.println(h8.equals(a1));
    }
}