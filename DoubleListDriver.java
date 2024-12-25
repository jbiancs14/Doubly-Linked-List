import java.util.Iterator;

/**
 * Simple test driver for the DoubleList class.
 *
 * @author Farzana Rahman & Jacob Bianco
 * @version 9/21/2019
 *
 */
public class DoubleListDriver {

  public static void main(String[] args) {
    DoubleList<String> list = new DoubleList<String>();

    String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    for (int i = 0; i < letters.length(); i++) {
      list.append(letters.substring(i, i + 1));
    }

    System.out.println("Printing using indexed for loop:");
    for (int i = 0; i < list.size(); i++) {
      System.out.print(list.get(i) + " ");
    }

    System.out.println("\n\nPrinting using for-each loop:");
    for (String s : list) {
      System.out.print(s + " ");
    }

    System.out.println("\n\nRemoving all letters less than M");
    Iterator<String> it = list.iterator();
    while (it.hasNext()) {
      if (it.next().compareTo("M") < 0)
        it.remove();
    }

    System.out.println("Resulting size: " + list.size());

    System.out.println("Remaining letters");
    for (int i = 0; i < list.size(); i++) {
      System.out.print(list.get(i) + " ");
    }

    System.out.println("\n\nRemoving all remaining letters.");
    it = list.iterator();
    while (it.hasNext()) {
      it.next();
      it.remove();
    }
    System.out.println("Resulting size: " + list.size());

    // NOT TESTED YET:
    // Correct exception for remove before next
    // Correct exception for next past the end.
    // Still possible to add nodes after removals.

  }

}
