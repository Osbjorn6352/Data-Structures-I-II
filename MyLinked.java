package algs13;
import stdlib.*;

public class MyLinked {
    static class Node { //Node class subset of the MyLinked class
        public Node() { }
        public double item;
        public Node next;
    }

    static int N;
    static Node first;
    
    public MyLinked () {
        first = null;
        N = 0;
        checkInvariants ();
    }

    private void myassert (String s, boolean b) { if (!b) throw new Error ("Assertion failed: " + s); }
    private void checkInvariants() {
        myassert("Empty <==> first==null", (N == 0) == (first == null));
        Node x = first;
        for (int i = 0; i < N; i++) {
            if (x==null) {
                throw new Error ("List too short!");
            }
            x = x.next;
        }
        myassert("EndOfList == null", x == null);
    }

    public boolean isEmpty () { return first == null; }
    public int size () { return N; }
    public void add (double item) {
        Node newfirst = new Node ();
        newfirst.item = item;
        newfirst.next = first;
        first = newfirst;
        N++;
    }

    // return Double.NEGATIVE_INFINITY if the linked list is empty
    public double max () { return max (first); }
    private static double max (Node x) {
		if (x == null) return Double.NEGATIVE_INFINITY;
		double result = first.item;                     // Set max to have our first point of comparison
		Node curr = first.next;                         // Start at the second node because we already accounted for first.item above
		while (curr != null){
			if (curr.item > result) result = curr.item; // If item at current node > than current max, set current max to item at current node.
			curr = curr.next;                           // Visit the next node
		}
		return result;
    }

    public double maxRecursive () { return maxRecursive (first, Double.NEGATIVE_INFINITY); }
    private static double maxRecursive (Node x, double result) { 
        if (x == null)                              //our end case is when we reach the null element of our linked list
            return result;                          //in which case we return result
        else {
            if (x.item > result) result = x.item;   //If the item in this node of x is greater than the result, we change the result to be equal to the item   
            return maxRecursive(x.next, result);    //Our recursion will continue node by node until the null element of x is reached
        }
    }

    // delete the kth element
    public void delete (int k) {
        if (k < 0 || k >= N) throw new IllegalArgumentException ();
        N--;
        if (k == 0) {
            first = first.next;         //special case cutting out the first position
        } 
        else {
            Node curr = first;          // Node position zero
            int nextTracker = 1;        // Node position one
            while (nextTracker != k) {
            nextTracker++; 
            curr = curr.next;           // Increment tracker and current node to see if curr.next is to be deleted (at tracker == k)
            }
            curr.next = curr.next.next; // When tracker == k, cut the node after curr (node at position k) out from the list by setting curr.next = curr.next.next
        }
        checkInvariants ();
    }

    // reverse the list "in place"... without creating any new nodes
    public void reverse () {
        if (first == null){
            ;                                   // if the linked list is null do nothing
        }
        else{
            Node prev = null;                   //this will be our new end of the list (starting from the beginning)
            while (first != null){
                Node following = first.next;    //save a reference to the following value
                first.next = prev;              //change the .next value for our current node (now that our reference to the following element has been saved) to point to the previous node
                prev = first;                   //move our reference to our previous node to that of our current node before we increment the current node or it will be lost
                first = following;              //move our reference to our current node to that of our next
            }
            first = prev;
        }
        checkInvariants ();
    }

    // remove 
    public void remove (double item) {
        if (first == null){ // if the first node is null, we should do nothing
            ;
        }
        else{
            Node curr = first;                         // set a tracker node                         
            while (curr != null && curr.item == item){ // if first itself is equal to item
               first = curr.next;                      // change first to the next Node
               curr = first;                           // reset curr equal to first again
               N--;                                    // Removes item one, so we must decrement N  
            }
            if (first != null) {                       // Checks if first equals null in case first.item != item
                curr = first;                          // Set curr == first
                while (curr.next != null) {            // In case curr.next, curr.next will remain unchanged, so only while loop up until that point
                    if (curr.next.item == item) {      // If item of the next node is equal to target,
                        curr.next = curr.next.next;    // cut it out,
                        N--;                           // and decrement N.
                    }
                    else {                             // Else, continue the loop.
                        curr = curr.next;
                    }
                }
            }

        }
        checkInvariants ();
    }


    private static void print (String s, MyLinked b) {
        StdOut.print (s + ": ");
        for (Node x = b.first; x != null; x = x.next)
            StdOut.print (x.item + " ");
        StdOut.println ();
    }
    private static void print (String s, MyLinked b, double i) {
        StdOut.print (s + ": ");
        for (Node x = b.first; x != null; x = x.next)
            StdOut.print (x.item + " ");
        StdOut.println (": " + i);
    }

    private static void testMax () {
        MyLinked b = new MyLinked ();
        print ("empty", b, b.max());
        b.add (-1);
        print ("singleton", b, b.max());
        b.add (-2);
        b.add (-3);
        b.add (-4);
        print ("at end", b, b.max());
        b.add (5);
        print ("at beginning", b, b.max());
        b.add (3);
        b.add (2);
        b.add (4);
        print ("in the middle", b, b.max());
    }
    private static void testMaxRecursive () {
        MyLinked b = new MyLinked ();
        print ("empty", b, b.maxRecursive());
        b.add (-1);
        print ("singleton", b, b.maxRecursive());
        b.add (-2);
        b.add (-3);
        b.add (-4);
        print ("at end", b, b.maxRecursive());
        b.add (5);
        print ("at beginning", b, b.maxRecursive());
        b.add (3);
        b.add (2);
        b.add (4);
        print ("in the middle", b, b.maxRecursive());
    }
    private static void testDelete () {
        MyLinked b = new MyLinked ();
        b.add (1);
        print ("singleton", b);
        b.delete (0);
        print ("deleted", b);
        for (double i = 1; i < 13; i++) {
            b.add (i);
        }
        print ("bigger list", b);
        b.delete (0);
        print ("deleted at beginning", b);
        b.delete (10);
        print ("deleted at end", b);
        b.delete (4);
        print ("deleted in middle", b);
    }
    private static void testReverse () {
        MyLinked b = new MyLinked ();
        b.reverse ();
        print ("reverse empty", b);
        b.add (1);
        print ("singleton", b);
        b.reverse ();
        print ("reverse singleton", b);
        b.add (2);
        print ("two", b);
        b.reverse ();
        print ("reverse two", b);
        b.reverse ();
        print ("reverse again", b);
        for (double i = 3; i < 7; i++) {
            b.add (i);
            b.add (i);
        }
        print ("bigger list", b);
        b.reverse ();
        print ("reversed", b);
    }
    private static void testRemove () {
        MyLinked b = new MyLinked ();
        b.remove (4);
        print ("removed 4 from empty", b);
        b.add (1);
        b.remove (4);
        print ("removed 4 from singelton", b);
        b.remove (1);
        print ("removed 1 from singelton", b);
        for (double i = 1; i < 5; i++) {
            b.add (i);
            b.add (i);
        }
        for (double i = 1; i < 5; i++) {
            b.add (i);
            b.add (i);
            b.add (i);
            b.add (i);
            b.add (i);
        }
        print ("longer list", b);
        b.remove (9);
        print ("removed all 9s", b); // does nothing
        b.remove (3);
        print ("removed all 3s", b);
        b.remove (1);
        print ("removed all 1s", b);
        b.remove (4);
        print ("removed all 4s", b);
        b.remove (2);
        print ("removed all 2s", b); // should be empty
    }

    public static void main (String args[]) {
        testMax ();
        testMaxRecursive ();
        testDelete ();
        testReverse ();
        testRemove ();
    }
}

































