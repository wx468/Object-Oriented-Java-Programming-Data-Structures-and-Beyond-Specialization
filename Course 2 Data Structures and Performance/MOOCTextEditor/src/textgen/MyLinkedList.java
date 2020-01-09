package textgen;

import java.util.AbstractList;


/**
 * A class that implements a doubly linked list
 * WEI XU:  IF A LIST IS 1,2,3 ,(NOT HEAD,1,2,3,TAIL)
 * HEAD IS THE FIRST OBJECT OF THE LIST, WHICH IS NODE1
 * TAIL IS THE LAST  OBJECT OF THE LIST. WHICH IS NODE3
 *
 * @param <E> The type of the elements stored in the list
 */
// todo: HEAD, 1,2,3, TAIL;(HEAD, TAIL ARE SENTINELS)
public class MyLinkedList<E> extends AbstractList<E> {
    LLNode<E> head;//sentinel before the first item;
    LLNode<E> tail;//sentinel after the last item;
    int size;
    
    /**
     * Create a new empty LinkedList
     */
    public MyLinkedList() {
        // TODO: Implement this method
        size = 0;
        head = new LLNode<>(null);
        tail = new LLNode<>(null);
        head.next = tail;
        tail.prev = head;
    }
    
    /**
     * Appends an element to the end of the list
     *
     * @param element The element to add
     */
    public boolean add(E element) {// TODO: WHY NOT "void"?
        // TODO: Implement this method
        if (element == null) {
            throw new NullPointerException("WEI XU INFORM that your input is a null, unaccepted");
        }
        LLNode<E> target = new LLNode<>(element);
        tail.prev.next = target;
        target.prev = tail.prev;
        tail.prev = target;
        target.next = tail;
        size++;
        
        return true;
    }
    
    /**
     * Get the element at position index
     *
     * @throws IndexOutOfBoundsException if the index is out of bounds.
     */
    public E get(int index) {
        // TODO: Implement this method.
        if (index > size - 1 || index < 0) {
            throw new IndexOutOfBoundsException(String.format("WEI XU INFORM that your index input \"%d\"" +
                    "is out of bound, the maximum index is \"%d\"", index, size - 1));
        }
        LLNode<E> target = head;
        for (int i = 0; i <= index; i++) {
            target = target.next;
        }
        
        return target.data;
    }
    
    /**
     * Add an element to the list at the specified index
     *
     * @param The     index where the element should be added
     * @param element The element to add
     */
    public void add(int index, E element) {  // todo: why not "boolean"?
        // TODO: Implement this method
        //find the current index node;
        if (element == null) {
            throw new NullPointerException("WEI XU INFORM that your input is a null, unaccepted");
        }
        if (index > size || index < 0) {//todo: otherwise, the emptylist cannot add element;
            throw new IndexOutOfBoundsException(String.format("WEI XU INFORM that your index input \"%d\"" +
                    "is out of bound, the maximum index is \"%d\"", index, size - 1));
        }
        LLNode<E> current = head;
        LLNode<E> target = new LLNode<>(element);
        for (int i = 0; i <= index; i++) {
            current = current.next;
        }
        current.prev.next = target;
        target.prev = current.prev;
        current.prev = target;
        target.next = current;
        size++;
        
    }
    
    
    /**
     * Return the size of the list
     */
    public int size() {
        // TODO: Implement this method
        return size;
    }
    
    /**
     * Remove a node at the specified index and return its data element.
     *
     * @param index The index of the element to remove
     * @return The data element removed
     * @throws IndexOutOfBoundsException If index is outside the bounds of the list
     */
    public E remove(int index) {
        // TODO: Implement this method
        if (index > size - 1 || index < 0) {
            throw new IndexOutOfBoundsException(String.format("WEI XU INFORM that your index input \"%d\"" +
                    "is out of bound, the maximum index is \"%d\"", index, size - 1));
        }
        LLNode<E> prevNode = head;
        LLNode<E> target;
        for (int i = 0; i < index; i++) {//find the preNode;
            prevNode = head.next;
            
        }
        target = prevNode.next;
        prevNode.next = prevNode.next.next;
        prevNode.next.prev = prevNode;
        size--;
        
        return target.data;
    }
    
    /**
     * Set an index position in the list to a new element
     *
     * @param index   The index of the element to change
     * @param element The new element
     * @return The element that was replaced
     * @throws IndexOutOfBoundsException if the index is out of bounds.
     */
    public E set(int index, E element) {
        // TODO: Implement this method
        if (element == null) {
            throw new NullPointerException("WEI XU INFORM that your input is a null, unaccepted");
        }
        if (index > size - 1 || index < 0) {
            throw new IndexOutOfBoundsException(String.format("WEI XU INFORM that your index input \"%d\"" +
                    "is out of bound, the maximum index is \"%d\"", index, size - 1));
        }
        LLNode<E> target = head;
        for (int i = 0; i <= index; i++) {
            target = head.next;
        }
        E oldData = target.data;
        target.data = element;
        //System.out.println(oldData);
        return oldData;
    }
    
    @Override
    public String toString() {
        System.out.println(super.toString());
        return super.toString();
    }
}


class LLNode<E> {
    LLNode<E> prev;
    LLNode<E> next;
    E data;
    
    // TODO: Add any other methods you think are useful here
    // E.g. you might want to add another constructor
    
    public LLNode(E e) {
        this.data = e;
        this.prev = null;
        this.next = null;
    }
    
}

