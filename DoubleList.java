import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Doubly-linked list class
 *
 * @author Jacob Bianco
 * @version 10/30/2024
 */
public class DoubleList<E> implements Iterable<E> {

  private Link<E> head; // Pointer to list header
  private Link<E> tail; // Pointer to last node
  private int listSize; // Size of list

  /**
   * Create an empty LList.
   */
  DoubleList() {
    clear();
  }

  /**
   * Return the element at the provided index. This method will iterate from the
   * head or the tail depending on which will require fewer steps.
   */
  public E get(int pos) {
    if (pos < 0 || pos >= listSize) {
      throw new IndexOutOfBoundsException();
    }

    if (pos < listSize / 2) {
      return forward(pos).element();
    } else {
      return backward(pos).element();
    }
  }

  /**
   * Helper method for iterating forward from the head.
   */
  private Link<E> forward(int pos) {
    Link<E> current = head.next();
    for (int i = 0; i < pos; i++) {
      current = current.next();
    }
    return current;
  }

  /**
   * Helper method for iterating backward from the tail.
   */
  private Link<E> backward(int pos) {
    Link<E> current = tail.prev();
    for (int i = 0; i < (listSize - 1) - pos; i++) {
      current = current.prev();
    }
    return current;
  }

  /**
   * Remove the provided link from the list.
   */
  private void removeHelper(Link<E> link) {
    Link<E> prev = link.prev();
    Link<E> next = link.next();
    prev.setNext(link.next());
    next.setPrev(link.prev());
    listSize--;

  }

  /**
   * Return the number of elements stored in the list.
   */
  public int size() {
    return listSize;
  }
  


/**
   * Remove all elements in this list.
   */
  public void clear() {
   //COMPLETE THIS
    head.setPrev(null);
    tail.setNext(null);
    tail.setPrev(head);
    head.setNext(tail);
    listSize = 0;
  }

  /**
   * Append item to the end of the list.
   */
  public void append(E item) {
    
    //COMPLETE THIS
     Link<E> newNode = new Link<>(item,null,null); 
     if (size() == 0){ //empty DLL
      newNode.setNext(tail);
      newNode.setPrev(head);
      head.setNext(newNode);
      tail.setPrev(newNode);
      ++listSize;
     }
     // all other cases, at least one element already in the list
     newNode.setNext(tail);
     newNode.setPrev(tail.prev());
     tail.prev().setNext(newNode);
     tail.setPrev(newNode);
     ++listSize;

  }

  /**
   * Add the item at the specified index.
   */
  public void add(int index, E item) {
    
    //COMPLETE THIS
    Link<E> newNode = new Link<>(item,null,null);
    // invalid index case:
    if (index < 0 || index >= listSize) {
      throw new IndexOutOfBoundsException();
    }
    // empty DLL case
    if (head.next() == tail){
      newNode.setPrev(head);
      newNode.setNext(tail);
      head.setNext(newNode);
      tail.setPrev(newNode);
      ++listSize;
    }
    // add at beggining case
    if (index == 0){
      newNode.setNext(head.next());
      newNode.setPrev(head);
      head.next().setPrev(newNode);
      head.setNext(newNode);
      ++listSize;
    }
    // add at end case 
    if (index == listSize - 1){
      newNode.setNext(tail);
      newNode.setPrev(tail.prev());
      newNode.prev().setNext(newNode);
      tail.setPrev(newNode);
      ++listSize;
    }
    // general case: add somewhere in the first half of the DLL at an index
    if (index < listSize / 2){
      newNode.setNext(forward(index));
      newNode.setPrev(forward(index).prev());
      newNode.prev().setNext(newNode);
      newNode.next().setPrev(newNode);
      ++listSize;
    }
    else{ // general case: add somewhere in the second half of the DLL at an index
      newNode.setNext(backward(index));
      newNode.setPrev(backward(index).prev());
      newNode.prev().setNext(newNode);
      newNode.next().setPrev(newNode);
      ++listSize;
    }

  }

  /**
   * Remove and return the item at the specified index.
   */
  public E remove(int index) {
    
    //COMPLETE THIS
    // remove an item from a list with only one item
    if (head.next().next() == tail){
      head.next().setPrev(null);
      tail.prev().setNext(null);
      head.setNext(tail);
      tail.setPrev(head);
      --listSize;
    }
    // removing the first item
    if (index == 0){
      head.next().setPrev(null);
      head.setNext(head.next().next());
      head.next().prev().setNext(null);
      head.next().setPrev(head);
      --listSize;
    }
    // remove from the back of the DLL
    if (index == listSize - 1){
      tail.prev().prev().setNext(tail);
      tail.prev().setNext(null);
      tail.prev().setPrev(null);
      tail.setPrev(tail.prev().prev());
      --listSize;
    }
    // remove from first half of list
    if (index < listSize / 2){
      forward(index).next().setPrev(forward(index).prev());
      forward(index).prev().setNext(forward(index).next());
      forward(index).setNext(null);
      forward(index).setPrev(null);
      --listSize;
    }
    // remove from second half of list
    else{
      backward(index).next().setPrev(forward(index).prev());
      backward(index).prev().setNext(forward(index).next());
      backward(index).setNext(null);
      backward(index).setPrev(null);
      --listSize;
    }
    return null;
  }

  /**
   * Reverse the list
   */
  public void reverse() {
    Link<E> current = head;
    Link<E> oneBehind = current.prev();
    while (current != tail) {
      current.setPrev(current.next());
      current.setNext(oneBehind);
      current.setPrev(current);
    }

    if (oneBehind != null){
      oneBehind.setPrev(head);
    }

    //COMPLETE THIS
    

  }


  /**
   * Iterates forward through the list. Remove operation is supported.
   */
  @Override
  public Iterator<E> iterator() {
    return new DoubleIterator();
  }
  
  private class DoubleIterator implements Iterator<E> {

    private Link<E> current;
    private boolean canRemove;

    public DoubleIterator() {
      current = head;
      canRemove = false;
    }

    @Override
    public boolean hasNext() {
      return current.next() != tail;
    }

    @Override
    public E next() {
      if (hasNext()) {
        current = current.next();
        canRemove = true;
        return current.element();
      } else {
        throw new NoSuchElementException();
      }
    }

    @Override
    public void remove() {
      if (!canRemove) {
        throw new IllegalStateException();
      }
      removeHelper(current);
      canRemove = false;
    }

  }

}
