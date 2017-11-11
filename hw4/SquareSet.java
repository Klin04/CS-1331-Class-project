import java.util.Set;
import java.util.Collection;
import java.util.Iterator;
import java.util.Arrays;
import java.util.NoSuchElementException;

/**
* This class represents the sets consisting of squares.
*
* @author klin96
*
* @version jdk1.8.0_144
*/
public class SquareSet implements Set<Square> {
    private int size;
    private final int dLength = 5;
    private Square[] backStore;

    /**
    * This is an inner class that represents
    * the iterators of the square sets.
    */
    private class SquareIterator implements Iterator<Square> {

        private int current;
        /**
        * This constructs a new Square iterator.
        */
        public SquareIterator() {
            current = 0;
        }

        /**
        * This method exams if there's an element
        * after the iterator
        * @return return a boolean value indicating
        * there is one or there is null
        */
        public boolean hasNext() {
            if (current < size) {
                return true;
            }
            return false;
        }

        /**
        * This method returns the element right
        * after the iterator
        * @return return a square object
        */
        public Square next() {
            if (this.hasNext()) {
                current++;
                return backStore[current - 1];
            }
            throw new NoSuchElementException();
        }
    }

    /**
    * Construct a new Square set collection.
    */
    public SquareSet() {
        size = 0;
        backStore = new Square[dLength];
    }

    /**
    * Construct a new Square set collection.
    * @param c a collection of squares
    */
    public SquareSet(Collection<Square> c) {
        this();
        for (Square sq0: c) {
            if (sq0 != null) {
                add(sq0);
            }
        }
    }

    /**
    * @param sq1 a square object
    * This method adds a new square object into a Square set.
    * @return returns a boolean value indicating
    * whether adding is successful.
    */
    public boolean add(Square sq1) {
        if (sq1 == null) {
            throw new NullPointerException();
        }
        char file = sq1.getFile();
        char rank = sq1.getRank();
        if ((file > 'h' || file < 'a')
            || (rank > '8' || rank < '1')) {
            throw new InvalidSquareException("" + file + rank);
        }
        if (this.contains(sq1)) {
            return false;
        }
        backStore[size] = sq1;
        size++;
        if (backStore.length == size) {
            Square[] temp = new Square[backStore.length * 2];
            for (int i = 0; i < size; i++) {
                temp[i] = backStore[i];
            }
            backStore = temp;
        }
        return true;
    }

    /**
    * @param o an object
    * This method exams if the object is in the Square set.e
    * @return return true if it's in the set, false if
    * it's not in the set.
    */
    public boolean contains(Object o) {
        if (o == null) {
            throw new NullPointerException();
        }
        if (!(o instanceof Square)) {
            throw new ClassCastException();
        }
        Square sq2 = (Square) o;
        for (int i = 0; i < size; i++) {
            if (backStore[i].equals(sq2)) {
                return true;
            }
        }
        return false;
    }

    /**
    * @param c a collection of objects
    * This method exams if the Square set contains the
    * same objects as the one to be examed.
    * @return true if they contains the same objects
    * false if any of the objects doesn't match.
    */
    public boolean containsAll(Collection<?> c) {
        if (c == null) {
            throw new NullPointerException();
        }
        for (Object o: c) {
            if (o == null) {
                throw new NullPointerException();
            }
            if (!(o instanceof Square)) {
                throw new ClassCastException();
            }
        }
        for (Object sq3: c) {
            if (!this.contains(((Square) sq3))) {
                return false;
            }
        }
        return true;
    }

    /**
    * This method exams if the Square set is empty.
    * @return true if it's empty, false if it's not
    */
    public boolean isEmpty() {
        return (size == 0);
    }

    /**
    * @param o an Object
    * This method exams if the set and the Object o
    * contains the same elements.
    * @return true if they contain the same element,
    * false if they do not.
    */
    public boolean equals(Object o) {
        if (!(o instanceof Set)) {
            return false;
        }
        if (((Set) o).size() != this.size()) {
            return false;
        }
        Set sSet1 = (Set) o;
        return this.containsAll(sSet1);
    }

    /**
    * This method shows the size of the set.
    * @return return the size of the set.
    */
    public int size() {
        return size;
    }

    /**
    * This methods returns the hashcode of a set object.
    * @return the hashcode of the set object
    */
    public int hashCode() {
        if (this == null) {
            return 0;
        }
        int hashc = 0;
        for (int i = 0; i < size; i++) {
            Square sq7 = backStore[i];
            hashc = hashc + sq7.hashCode();
        }
        return hashc;
    }
    /**
    * This method transforms the Square set into an array.
    * @return an array containing Square objects in the set
    */
    public Object[] toArray() {
        Square[] finalArray =
            Arrays.copyOf(backStore, size);
        return (Object[]) finalArray;
    }

    /**
    * @return return an iterator of the Square set
    */
    public Iterator<Square> iterator() {
        Iterator<Square> iSquare = new SquareIterator();
        return iSquare;
    }

    /**
    * @param cSquare a collection the contains objects
    * of the subclasses that extend Square
    * This method adds all objects in the given collection
    * into the Square Set.
    * @return true if the adding is successful,
    * false if it's not successful.
    */
    public boolean addAll(Collection<? extends Square> cSquare) {
        int originalSize = this.size;
        if (cSquare == null) {
            throw new NullPointerException();
        }
        for (Square sq4: cSquare) {
            if (sq4 == null) {
                throw new NullPointerException();
            }
            if (!(sq4 instanceof Square)) {
                throw new ClassCastException();
            }
            char file = sq4.getFile();
            char rank = sq4.getRank();
            if ((file > 'h' || file < 'a')
                || (rank > '8' || rank < '1')) {
                throw new InvalidSquareException("" + file + rank);
            }
        }
        for (Square sq4: cSquare) {
            this.add(sq4);
        }
        if (originalSize == this.size) {
            return false;
        }
        return true;
    }

    /**
    * @param a an array that indicates the generics
    * of the output array.
    * @param <T> any generic reference type
    * @return an array that contains the same type of
    * objects as the given array.
    */
    @SuppressWarnings("unchecked")
    public <T> T[] toArray(T[] a) {
        if (a == null) {
            throw new NullPointerException();
        }
        if (!a.getClass().isAssignableFrom(backStore.getClass())) {
            throw new ArrayStoreException();
        }
        if (a.length < size) {
            Object[] newArr = new Object[size];
            for (int i = 0; i < size; i++) {
                newArr[i] = backStore[i];
            }
            return ((T[]) newArr);
        }
        if (a.length >= size) {
            for (int i = 0; i < size; i++) {
                a[i] = (T) backStore[i];
            }
            for (int i = size; i < a.length; i++) {
                a[i] = null;
            }
        }
        return a;
    }

    /**
    * @param o an Object to be removed
    * This method removes a given object
    * from the Square Set.
    * @return true if the removing is successful,
    * false if it's not.
    */
    public boolean remove(Object o) {
        if (o == null) {
            throw new NullPointerException();
        }
        if (!(o instanceof Square)) {
            throw new ClassCastException();
        }
        Square sq6 = (Square) o;
        boolean exist = false;
        int place = 0;
        for (int i = 0; i < size; i++) {
            if (backStore[i].equals(sq6)) {
                exist = true;
                place = i;
            }
        }
        if (exist) {
            size--;
            Square[] newBackStore = new Square[backStore.length];
            for (int i = 0; i < place; i++) {
                newBackStore[i] = backStore[i];
            }
            for (int i = place; i < size; i++) {
                newBackStore[i] = backStore[i + 1];
            }
            backStore = newBackStore;
            return true;
        }
        return false;
    }

    /**
    * The methods below are not supported
    * in the Square Set.
    */

    /**
    * This method clears the set.
    */
    public void clear() {
        throw new UnsupportedOperationException();
    }

    /**
    * @param c a collection
    *
    * @return true if retaining is successful;
    * false if it's not.
    */
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    /**
    * @param c a collection
    *
    * @return true if removing is successful;
    * false if it's not.
    */
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }
}