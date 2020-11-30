package es.datastructur.synthesizer;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

//TODO: Make sure to that this class and all of its methods are public
//TODO: Make sure to add the override tag for all overridden methods
//TODO: Make sure to make this class implement BoundedQueue<T>

public class ArrayRingBuffer<T> implements BoundedQueue<T> {
    /* Index for the next dequeue or peek. */
    private int first;
    /* Index for the next enqueue. */
    private int last;
    /* Variable for the fillCount. */

    private int fillCount;
    /* Array for storing the buffer data. */
    private T[] rb;


    /**Make ArrayRingBuffer object iterable and return an iterator*/
    @Override
    public Iterator<T> iterator() {
        return new ArrayRingBufferIterator(first, fillCount, rb);
    }

    /**private class which implements the Iterator interface and create an Iterator object*/
    private class ArrayRingBufferIterator implements Iterator<T> {
        int fillCount;
        int index;
        T[] rb;

        public ArrayRingBufferIterator(int first, int fillCount, T[] rb) {
            this.fillCount = fillCount;
            this.index = first;
            this.rb = rb;
        }

        @Override
        public boolean hasNext() {
            if (fillCount == 0) {
                return false;
            }
            fillCount -= 1;
            return true;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            T temp = rb[index];
            rb[index] = null;
            index++;
            fillCount--;
            return temp;
        }
    }

    /**return true only if the other object is in an ArrayRingBuffer with the exact same values*/
    @Override
    public boolean equals(Object o) {
        if (o instanceof ArrayRingBuffer && this.equals(o)) {
            return true;
        }
        return false;
    }



    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        // TODO: Create new array with capacity elements.
        //       first, last, and fillCount should all be set to 0.
        rb = (T[]) new Object[capacity];
        first = 0;
        last = 0;
        fillCount = 0;
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow").
     */
    @Override
    public void enqueue(T x) {
        // TODO: Enqueue the item. Don't forget to increase fillCount and update
        //       last. Don't worry about throwing the RuntimeException until you
        //       get to task 4.
        if (BoundedQueue.super.isFull()) {
            throw new RuntimeException("Ring buffer underflow");
        }
        rb[last] = x;
        last++;
        if (last == rb.length) {
            last = 0;
        }
        fillCount++;
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     */
    public T dequeue() {
        // TODO: Dequeue the first item. Don't forget to decrease fillCount and
        //       update first. Don't worry about throwing the RuntimeException until you
        //       get to task 4.
        if (BoundedQueue.super.isEmpty()) {
            throw new RuntimeException("Ring buffer underflow");
        }
        T temp = rb[first];
        rb[first] = null;
        first++;
        if (first == rb.length) {
            first = 0;
        }
        fillCount--;
        return temp;
    }

    /**
     * Return oldest item, but don't remove it. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     */
    @Override
    public T peek() {
        // TODO: Return the first item. None of your instance variables should
        //       change. Don't worry about throwing the RuntimeException until you
        //       get to task 4.
        if (BoundedQueue.super.isEmpty()) {
            throw new RuntimeException("Ring buffer underflow");
        }
        return rb[first];
    }

    // TODO: When you get to part 4, implement the needed code to support
    //       iteration and equals.


    @Override
    public int fillCount() {
        return fillCount;
    }

    @Override
    public int capacity() {
        return rb.length;
    }
}
    // TODO: Remove all comments that say TODO when you're done.

