/**
* Throws exception when an invalid square appears
* I used unchecked exception because this exception is not always
* caught. Besides, it happens during runtime.
* @author klin96
* @version java_1.8.0.144
*/
public class InvalidSquareException extends RuntimeException {

    /**
    * This creates a new invalid square exception.
    * @param square a string tells where is the square
    */
    public InvalidSquareException(String square) {
        super(square);
    }
}