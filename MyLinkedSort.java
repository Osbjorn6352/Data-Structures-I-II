package algs13;


import stdlib.*;

// PROBLEM 2.2.17
public class MyLinkedSort {
    static class Node {
        public Node() { }
        public double item;
        public Node next;
    }

    static int N;
    Node first;
    
    public MyLinkedSort () {
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

    private static void print (String s, Node b) {
        StdOut.print (s + ": ");
        for (Node x = b; x != null; x = x.next)
            StdOut.print (x.item + " ");
        StdOut.println ();
    }
    private static void print (String s, Node b, double i) {
        StdOut.print (s + ": ");
        for (Node x = b; x != null; x = x.next)
            StdOut.print (x.item + " ");
        StdOut.println (": " + i);
    }

    static public Node sort(Node l){ // 
        // base case: list is of size 1. reurn
        if (l.next == null){
            return l;
        }
        // otherwise use split to create two lists
        else {
            Node[] splitNode = split(l);

            // recursively sort each of them     
            Node NodeL = sort(splitNode[0]);
            Node NodeR = sort(splitNode[1]);
            
            // use merge to combine them, and return the result
            // return merge(splitNode[0], splitNode[1]);
            return merge(NodeL, NodeR);
        }
	}
		 
	static public Node[] split(Node l){

        // parameter is a linked List
	    // it returns an array of size 2
        Node[] result = new Node[2];
        Node curr = l;
        int size = 0;
        
        // getting the size of our linked list
        while(curr != null){
            size++;
            curr = curr.next;
        }

        //get our middle value
        int mid = size/2;

        //iterate through the list and split it in the middle
        //setting the left node equal to the first node
        //and the right node equal to the middle node + 1
        result[0] = l;
        curr = l;
        int count = 0;
        while (count < mid){
            count++;
            if (count < mid)
                curr = curr.next;
        }
        result[1] = curr.next;
        curr.next = null;
        return result;
	  }
	
	static public Node merge(Node lt, Node rt){
	
        // merge creates a new List
	    // whose elements come from the lt and rt MyLinkedLists

        Node curr;
        if (lt.item <= rt.item){            //start with whichever item is smallest
            curr = lt;
            lt = lt.next;
        }
        else {
            curr = rt;
            rt = rt.next;
        }
        Node start = curr;                  //save the first element
        while (lt != null || rt != null){
            if (rt == null){
                curr.next = lt;
                break;
            }
            else if (lt == null){
                curr.next = rt;
                break;
            }
            else if (lt.item <= rt.item){
                curr.next = lt;
                lt = lt.next;
            }
            else {
                curr.next = rt;
                rt = rt.next;
            }
            curr = curr.next;
        }
        return start;
	}

    public static void main (String args[]) {
        int[] a1 = new int[20];
		for (int i = 0; i < a1.length; i++)
			a1[i] = i;
		StdRandom.shuffle (a1);
        MyLinkedSort b1 = new MyLinkedSort ();
        for (int i:a1) b1.add(i);
        MyLinkedSort.print("before sort",b1.first);
        Node res1 = MyLinkedSort.sort(b1.first);
        MyLinkedSort.print("after sort",res1);

        int[] a2 = new int[200];
		for (int i = 0; i < a2.length; i++)
			a2[i] = i;
		StdRandom.shuffle (a2);
        MyLinkedSort b2 = new MyLinkedSort ();
        for (int i:a2) b2.add(i);
        MyLinkedSort.print("before sort",b2.first);
        Node res2 = MyLinkedSort.sort(b2.first);
        MyLinkedSort.print("after sort",res2);

       // write code for a doubling Test  
    }
}



