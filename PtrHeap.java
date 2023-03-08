package algs24;

import java.util.NoSuchElementException;

import stdlib.StdIn;
import stdlib.StdOut;


/**
 *  The <tt>PtrHeap</tt> class is the priorityQ class from Question 2.4.24.
 *  It represents a priority queue of generic keys.
 *  
 *  It supports the usual <em>insert</em> and <em>delete-the-maximum</em>
 *  operations, along with methods for peeking at the maximum key,
 *  testing if the priority queue is empty, and iterating through
 *  the keys.
 *  For additional documentation, see <a href="http://algs4.cs.princeton.edu/24pq">Section 2.4</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 */

public class PtrHeap<K extends Comparable<? super K>> {
	
	static class Node<K> {
		K value;
		Node<K> parent;
		Node<K> lchild;
		Node<K> rchild;
	}
	
	private int size;
	private Node<K> root;
	
    boolean isRoot(Node<K> n){ return n == root; }

	boolean less(K u, K v) { return (u.compareTo(v) < 0); }
    
    Node<K> find(int n){ return null; } 
	
	void exch(Node<K> n1, Node<K> n2) { 
		K temp = n1.value;
		n1.value = n2.value;
		n2.value = temp;
	}
	
	@SuppressWarnings("unchecked")
	/** Create an empty priority queue  */
	public PtrHeap() {
		size = 0;
		root = null;
	}
    
	/** Is the priority queue empty? */
	public boolean isEmpty() { 
		return size == 0; 
	}
	

	/** Return the number of items on the priority queue. */
	public int size() { 
		return size; 
	}

	/**
	 * Return the largest key on the priority queue.
	 * Throw an exception if the priority queue is empty.
	 */
	public K max() {
		return root.value;
	}

	public void swim(Node<K> x){
		while (!(isRoot(x)) && less(x.parent.value, x.value)) {
			exch(x, x.parent);
			x = x.parent;
		}
	}

	public void sink(Node<K> x){
		while (x.lchild != null && x.rchild != null && (less(x.value, x.lchild.value) || (less(x.value, x.rchild.value)))){
			if (less(x.lchild.value, x.rchild.value)) {
				exch(x, x.rchild);
				x = x.rchild;
			}				
			else if (less(x.rchild.value, x.lchild.value)) {
				exch(x, x.lchild);
				x = x.lchild;
			}
		}
		if (x.lchild != null && less(x.value, x.lchild.value))
			exch(x, x.lchild);
			x = x.lchild;
	}

	/** Add a new key to the priority queue. */
	public void insert(K x) {
		if (isEmpty()){
			size++;
			root = new Node<K>();
			root.value = x;
		}
		else {
			size++;
			Node<K> curr = root;
			String binStr = Integer.toBinaryString(size);
			for (int i = 1; i < binStr.length(); i++) {
				if  (binStr.charAt(i) == '0') {
					if (curr.lchild == null){
						curr.lchild = new Node<K>();
						curr.lchild.value = x;
						curr.lchild.parent = curr;
						curr = curr.lchild;
						swim(curr);
					}
					else {
						curr = curr.lchild;
					}
				}
				else {
					if (curr.rchild == null){
						curr.rchild = new Node<K>();
						curr.rchild.value = x;
						curr.rchild.parent = curr;
						curr = curr.rchild;
						swim(curr);
					}
					else {
						curr = curr.rchild;
					}
				}
			}
		}
	}

	/**
	 * Delete and return the largest key on the priority queue.
	 * Throw an exception if the priority queue is empty.
	 */
	public K delMax() {
		if (isEmpty())
			throw new NoSuchElementException();
		else{
			K val = root.value;
			Node<K> curr = root;
			String binStr = Integer.toBinaryString(size);
			for (int i = 1; i < binStr.length(); i++){
				if  (binStr.charAt(i) == '0') {
					curr = curr.lchild;
					if (i == binStr.length()-1){
						curr.parent.lchild = null;
						exch(curr, root);
						sink(root);
						size--;
					}
				}
				else {
					curr = curr.rchild;
					if (i == binStr.length() - 1) {
						curr.parent.rchild = null;
						exch(curr, root);
						sink(root);
						size--;
					}
				}
			}
		return val;
		}
	}

	private void printHeap(Node<K> n){
		if(n == null)  return ;
		System.out.print(n.value+" ");
		printHeap(n.lchild);
		printHeap(n.rchild);
		}

	private void showHeap() { 
	    printHeap(root);
		System.out.println();
	}

	public static void main(String[] args) {
		PtrHeap<String> pq = new PtrHeap<>();
		StdIn.fromString("10 20 30 40 50 - - - 05 25 35 - - - 70 80 05 - - - - ");
		while (!StdIn.isEmpty()) {
			StdOut.print ("pq:  "); pq.showHeap();
			String item = StdIn.readString();
			if (item.equals("-")) StdOut.println("max: " + pq.delMax());
			else pq.insert(item);
		}
		StdOut.println("(" + pq.size() + " left on pq)");

	}

}

