package model.data_structures;

import java.util.NoSuchElementException;

/**
 * Clase del arbol RedBlackBST
 * @author Julian Padilla - Pablo Pastrana
 * Usamos metodos del Libro Algorithms 4 edition los autores son: Robert Sedgewick y Kevin Wayne.
 * @param <Key> Key de tipo generico
 * @param <Value> Value de tipo generico
 */
public class RedBlackBST <Key extends Comparable<Key>, Value> 
{
	// Constants

	/**
	 * Constant boolean - RED
	 */
	private static final boolean RED   = true;

	/**
	 * Constant boolean - BLACK
	 */
	private static final boolean BLACK = false;

	// Atributes

	/**
	 * root of the BST
	 */
	private Node root;     

	// Class Node for the RedBlackBST

	/**
	 * BST helper node data type
	 * @author Julian Padilla - Pablo Pastrana
	 * This class was obtained for the book: Algorithms 4 edition, the authors for this class are: Robert Sedgewick and Kevin Wayne
	 */
	private class Node 
	{
		// Atributes

		/**
		 * key
		 */
		private Key key;         

		/**
		 * associated data
		 */
		private Value val;         

		/**
		 * links to left and right subtrees
		 */
		private Node left, right;  

		/**
		 * color of parent link
		 */
		private boolean color;     

		/**
		 * subtree count
		 */
		private int size;         

		// Construct Method

		/**
		 * Construct method of the node for the RedBlackBST
		 * @param key Key of the RedBlackBST
		 * @param val Value of the RedBlackBST
		 * @param color Color of the branch of RedBlackBST
		 * @param size Size of the RedBlackBST
		 */
		public Node(Key key, Value val, boolean color, int size) 
		{
			this.key = key;
			this.val = val;
			this.color = color;
			this.size = size;
		}
	}

	/**
	 * Initializes an empty symbol table.
	 */
	public RedBlackBST() 
	{

	}

	/**
	 * is node x red; false if x is null ?
	 * @param x Node
	 * @return Is the node red?
	 */
	private boolean isRed(Node x) 
	{
		if (x == null) return false;
		return x.color == RED;
	}

	/**
	 * number of node in subtree rooted at x; 0 if x is null
	 * @param x Node
	 * @return The size of the RedBlackBST
	 */
	private int size(Node x) 
	{
		if (x == null) return 0;
		return x.size;
	} 


	/**
	 * Returns the number of key-value pairs in this symbol table.
	 * @return the number of key-value pairs in this symbol table
	 */
	public int size() 
	{
		return size(root);
	}

	/**
	 * Is this symbol table empty?
	 * @return {@code true} if this symbol table is empty and {@code false} otherwise
	 */
	public boolean isEmpty() 
	{
		return root == null;
	}
	
	/**
     * Returns the value associated with the given key.
     * @param key the key
     * @return the value associated with the given key if the key is in the symbol table and {@code null} if the key is not in the symbol table
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public Value get(Key key) 
    {
        if (key == null) throw new IllegalArgumentException("argument to get() is null");
        return get(root, key);
    }

    /**
     * Value associated with the given key in subtree rooted at x; null if no such key
     * @param x Node
     * @param key Key of the RedBlackBST
     * @return Get the value from the RedBlackBST
     */
    private Value get(Node x, Key key) 
    {
        while (x != null) {
            int cmp = key.compareTo(x.key);
            if      (cmp < 0) x = x.left;
            else if (cmp > 0) x = x.right;
            else              return x.val;
        }
        return null;
    }

    /**
     * Does this symbol table contain the given key?
     * @param key the key
     * @return {@code true} if this symbol table contains {@code key} and {@code false} otherwise
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public boolean contains(Key key) 
    {
        return get(key) != null;
    }

    /**
     * Inserts the specified key-value pair into the symbol table, overwriting the old 
     * value with the new value if the symbol table already contains the specified key.
     * Deletes the specified key (and its associated value) from this symbol table
     * if the specified value is {@code null}.
     * @param key the key
     * @param val the value
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public void put(Key key, Value val)
    {
        if (key == null) throw new IllegalArgumentException("first argument to put() is null");
        if (val == null) {
            delete(key);
            return;
        }

        root = put(root, key, val);
        root.color = BLACK;
        // assert check();
    }

    /**
     * Insert the key-value pair in the subtree rooted at h
     * @param h Node
     * @param key Key of the node RedBlackBST
     * @param val Value of the node RedBlackBST
     * @return Put the node inside the RedBlackBST
     */
    private Node put(Node h, Key key, Value val) 
    { 
        if (h == null) return new Node(key, val, RED, 1);

        int cmp = key.compareTo(h.key);
        if      (cmp < 0) h.left  = put(h.left,  key, val); 
        else if (cmp > 0) h.right = put(h.right, key, val); 
        else              h.val   = val;

        // fix-up any right-leaning links
        if (isRed(h.right) && !isRed(h.left))      h = rotateLeft(h);
        if (isRed(h.left)  &&  isRed(h.left.left)) h = rotateRight(h);
        if (isRed(h.left)  &&  isRed(h.right))     flipColors(h);
        h.size = size(h.left) + size(h.right) + 1;

        return h;
    }
    
    /**
     * Removes the smallest key and associated value from the symbol table.
     * @throws NoSuchElementException if the symbol table is empty
     */
    public void deleteMin() 
    {
        if (isEmpty()) throw new NoSuchElementException("BST underflow");

        // if both children of root are black, set root to red
        if (!isRed(root.left) && !isRed(root.right))
            root.color = RED;

        root = deleteMin(root);
        if (!isEmpty()) root.color = BLACK;
    }

    /**
     * Delete the key-value pair with the minimum key rooted at h
     * @param h Node
     * @return Delete the min node in the RedBlackBST
     */
    private Node deleteMin(Node h) 
    { 
        if (h.left == null)
            return null;

        if (!isRed(h.left) && !isRed(h.left.left))
            h = moveRedLeft(h);

        h.left = deleteMin(h.left);
        return balance(h);
    }

    /**
     * Removes the largest key and associated value from the symbol table.
     * @throws NoSuchElementException if the symbol table is empty
     */
    public void deleteMax() 
    {
        if (isEmpty()) throw new NoSuchElementException("BST underflow");

        // if both children of root are black, set root to red
        if (!isRed(root.left) && !isRed(root.right))
            root.color = RED;

        root = deleteMax(root);
        if (!isEmpty()) root.color = BLACK;
        // assert check();
    }

    /**
     * Delete the key-value pair with the maximum key rooted at h
     * @param h Node 
     * @return Delete the max node in the RedBlackBST
     */
    private Node deleteMax(Node h)
    { 
        if (isRed(h.left))
            h = rotateRight(h);

        if (h.right == null)
            return null;

        if (!isRed(h.right) && !isRed(h.right.left))
            h = moveRedRight(h);

        h.right = deleteMax(h.right);

        return balance(h);
    }

    /**
     * Removes the specified key and its associated value from this symbol table     
     * (if the key is in this symbol table).   
     * @param  key the key
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public void delete(Key key) 
    { 
        if (key == null) throw new IllegalArgumentException("argument to delete() is null");
        if (!contains(key)) return;

        // if both children of root are black, set root to red
        if (!isRed(root.left) && !isRed(root.right))
            root.color = RED;

        root = delete(root, key);
        if (!isEmpty()) root.color = BLACK;
        // assert check();
    }

    /**
     * Delete the key-value pair with the given key rooted at h
     * @param h Node 
     * @param key Key of the node RedBlackBST
     * @return Delete the node from the RedBlackBST
     */
    private Node delete(Node h, Key key) 
    { 
        // assert get(h, key) != null;

        if (key.compareTo(h.key) < 0) 
        {
            if (!isRed(h.left) && !isRed(h.left.left))
                h = moveRedLeft(h);
            h.left = delete(h.left, key);
        }
        else {
            if (isRed(h.left))
                h = rotateRight(h);
            if (key.compareTo(h.key) == 0 && (h.right == null))
                return null;
            if (!isRed(h.right) && !isRed(h.right.left))
                h = moveRedRight(h);
            if (key.compareTo(h.key) == 0) {
                Node x = min(h.right);
                h.key = x.key;
                h.val = x.val;
                // h.val = get(h.right, min(h.right).key);
                // h.key = min(h.right).key;
                h.right = deleteMin(h.right);
            }
            else h.right = delete(h.right, key);
        }
        return balance(h);
    }
    

    /**
     * Make a left-leaning link lean to the right
     * @param h Node 
     * @return Rotate rigth
     */
    private Node rotateRight(Node h) 
    {
        // assert (h != null) && isRed(h.left);
        Node x = h.left;
        h.left = x.right;
        x.right = h;
        x.color = x.right.color;
        x.right.color = RED;
        x.size = h.size;
        h.size = size(h.left) + size(h.right) + 1;
        return x;
    }

    /**
     * Make a right-leaning link lean to the left
     * @param h Node
     * @return Rotate left
     */
    private Node rotateLeft(Node h) 
    {
        // assert (h != null) && isRed(h.right);
        Node x = h.right;
        h.right = x.left;
        x.left = h;
        x.color = x.left.color;
        x.left.color = RED;
        x.size = h.size;
        h.size = size(h.left) + size(h.right) + 1;
        return x;
    }

    /**
     * Flip the colors of a node and its two children
     * @param h Node
     */
    private void flipColors(Node h) 
    {
        // h must have opposite color of its two children
        // assert (h != null) && (h.left != null) && (h.right != null);
        // assert (!isRed(h) &&  isRed(h.left) &&  isRed(h.right))
        //    || (isRed(h)  && !isRed(h.left) && !isRed(h.right));
        h.color = !h.color;
        h.left.color = !h.left.color;
        h.right.color = !h.right.color;
    }

    /**
     * Assuming that h is red and both h.left and h.left.left are black, make h.left or one of its children red.
     * @param h Node
     * @return Move red Left
     */
    private Node moveRedLeft(Node h) 
    {
        // assert (h != null);
        // assert isRed(h) && !isRed(h.left) && !isRed(h.left.left);

        flipColors(h);
        if (isRed(h.right.left))
        { 
            h.right = rotateRight(h.right);
            h = rotateLeft(h);
            flipColors(h);
        }
        return h;
    }

    /**
     * Assuming that h is red and both h.right and h.right.left are black, make h.right or one of its children red.
     * @param h Node 
     * @return Move black right
     */
    private Node moveRedRight(Node h) 
    {
        // assert (h != null);
        // assert isRed(h) && !isRed(h.right) && !isRed(h.right.left);
        flipColors(h);
        if (isRed(h.left.left)) 
        { 
            h = rotateRight(h);
            flipColors(h);
        }
        return h;
    }

    /**
     * Restore red-black tree invariant
     * @param h Node
     * @return Balance the RedBlackBST
     */
    private Node balance(Node h) 
    {
        // assert (h != null);

        if (isRed(h.right))                      h = rotateLeft(h);
        if (isRed(h.left) && isRed(h.left.left)) h = rotateRight(h);
        if (isRed(h.left) && isRed(h.right))     flipColors(h);

        h.size = size(h.left) + size(h.right) + 1;
        return h;
    }

    /**
     * Returns the height of the BST (for debugging).
     * @return the height of the BST (a 1-node tree has height 0)
     */
    public int height() 
    {
        return height(root);
    }
    private int height(Node x) 
    {
        if (x == null) return -1;
        return 1 + Math.max(height(x.left), height(x.right));
    }

    /**
     * Returns the smallest key in the symbol table.
     * @return the smallest key in the symbol table
     * @throws NoSuchElementException if the symbol table is empty
     */
    public Key min() 
    {
        if (isEmpty()) throw new NoSuchElementException("calls min() with empty symbol table");
        return min(root).key;
    } 

    /**
     * The smallest key in subtree rooted at x; null if no such key
     * @param x Node 
     * @return Node min
     */
    private Node min(Node x) 
    { 
        // assert x != null;
        if (x.left == null) return x; 
        else                return min(x.left); 
    } 

    /**
     * Returns the largest key in the symbol table.
     * @return the largest key in the symbol table
     * @throws NoSuchElementException if the symbol table is empty
     */
    public Key max() 
    {
        if (isEmpty()) throw new NoSuchElementException("calls max() with empty symbol table");
        return max(root).key;
    } 

    /**
     * the largest key in the subtree rooted at x; null if no such key
     * @param x Node
     * @return Node max
     */
    private Node max(Node x)
    { 
        // assert x != null;
        if (x.right == null) return x; 
        else                 return max(x.right); 
    } 


    /**
     * Returns the largest key in the symbol table less than or equal to {@code key}.
     * @param key the key
     * @return the largest key in the symbol table less than or equal to {@code key}
     * @throws NoSuchElementException if there is no such key
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public Key floor(Key key) 
    {
        if (key == null) throw new IllegalArgumentException("argument to floor() is null");
        if (isEmpty()) throw new NoSuchElementException("calls floor() with empty symbol table");
        Node x = floor(root, key);
        if (x == null) throw new NoSuchElementException("argument to floor() is too small");
        else           return x.key;
    }    

    /**
     * The largest key in the subtree rooted at x less than or equal to the given key
     * @param x Node 
     * @param key Key of the node
     * @return Floor
     */
    private Node floor(Node x, Key key) 
    {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp == 0) return x;
        if (cmp < 0)  return floor(x.left, key);
        Node t = floor(x.right, key);
        if (t != null) return t; 
        else           return x;
    }

    /**
     * Returns the smallest key in the symbol table greater than or equal to {@code key}.
     * @param key the key
     * @return the smallest key in the symbol table greater than or equal to {@code key}
     * @throws NoSuchElementException if there is no such key
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public Key ceiling(Key key) 
    {
        if (key == null) throw new IllegalArgumentException("argument to ceiling() is null");
        if (isEmpty()) throw new NoSuchElementException("calls ceiling() with empty symbol table");
        Node x = ceiling(root, key);
        if (x == null) throw new NoSuchElementException("argument to ceiling() is too small");
        else           return x.key;  
    }

    /**
     * The smallest key in the subtree rooted at x greater than or equal to the given key
     * @param x Node
     * @param key Key of the node
     * @return Ceiling
     */
    private Node ceiling(Node x, Key key) 
    {  
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp == 0) return x;
        if (cmp > 0)  return ceiling(x.right, key);
        Node t = ceiling(x.left, key);
        if (t != null) return t; 
        else           return x;
    }

    /**
     * Return the key in the symbol table of a given {@code rank}.
     * This key has the property that there are {@code rank} keys in
     * the symbol table that are smaller. In other words, this key is the
     * ({@code rank}+1)st smallest key in the symbol table.
     * @param  rank the order statistic
     * @return the key in the symbol table of given {@code rank}
     * @throws IllegalArgumentException unless {@code rank} is between 0 and
     */
    public Key select(int rank) 
    {
        if (rank < 0 || rank >= size()) 
        {
            throw new IllegalArgumentException("argument to select() is invalid: " + rank);
        }
        return select(root, rank);
    }

    /**
     * Return key in BST rooted at x of given rank.
     * Precondition: rank is in legal range.
     * @param x Node
     * @param rank
     * @return Select the node from the RedBlackBST
     */
    private Key select(Node x, int rank) {
        if (x == null) return null;
        int leftSize = size(x.left);
        if      (leftSize > rank) return select(x.left,  rank);
        else if (leftSize < rank) return select(x.right, rank - leftSize - 1); 
        else                      return x.key;
    }

    /**
     * Return the number of keys in the symbol table strictly less than {@code key}.
     * @param key the key
     * @return the number of keys in the symbol table strictly less than {@code key}
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public int rank(Key key) 
    {
        if (key == null) throw new IllegalArgumentException("argument to rank() is null");
        return rank(key, root);
    } 

    /**
     * Number of keys less than key in the subtree rooted at x
     * @param key Key of the node
     * @param x Node
     * @return Rank
     */
    private int rank(Key key, Node x) 
    {
        if (x == null) return 0; 
        int cmp = key.compareTo(x.key); 
        if      (cmp < 0) return rank(key, x.left); 
        else if (cmp > 0) return 1 + size(x.left) + rank(key, x.right); 
        else              return size(x.left); 
    } 

    /**
     * Returns all keys in the symbol table as an {@code Iterable}.
     * To iterate over all of the keys in the symbol table named {@code st},
     * use the foreach notation: {@code for (Key key : st.keys())}.
     * @return all keys in the symbol table as an {@code Iterable}
     */
    public Iterable<Key> keys() 
    {
        if (isEmpty()) return new LinkedQueue<Key>();
        return keys(min(), max());
    }
    
    /**
     * Returns all values in the symbol table as an {@code Iterable}.
     * To iterate over all of the keys in the symbol table named {@code st},
     * use the foreach notation: {@code for (Key key : st.keys())}.
     * @return all keys in the symbol table as an {@code Iterable}
     */
    public Iterable<Value> Values() 
    {
        if (isEmpty()) return new LinkedQueue<Value>();
        return values(min(), max());
    }

    /**
     * Returns all keys in the symbol table in the given range,
     * as an {@code Iterable}.
     * @param  lo minimum endpoint
     * @param  hi maximum endpoint
     * @return all keys in the symbol table between {@code lo} 
     *    (inclusive) and {@code hi} (inclusive) as an {@code Iterable}
     * @throws IllegalArgumentException if either {@code lo} or {@code hi}
     *    is {@code null}
     */
    public Iterable<Key> keys(Key lo, Key hi) 
    {
        if (lo == null) throw new IllegalArgumentException("first argument to keys() is null");
        if (hi == null) throw new IllegalArgumentException("second argument to keys() is null");

        LinkedQueue<Key> queue = new LinkedQueue<Key>();
        // if (isEmpty() || lo.compareTo(hi) > 0) return queue;
        keys(root, queue, lo, hi);
        return queue;
    } 

    // add the keys between lo and hi in the subtree rooted at x
    // to the queue
    private void keys(Node x, LinkedQueue<Key> queue, Key lo, Key hi) 
    { 
        if (x == null) return; 
        int cmplo = lo.compareTo(x.key); 
        int cmphi = hi.compareTo(x.key); 
        if (cmplo < 0) keys(x.left, queue, lo, hi); 
        if (cmplo <= 0 && cmphi >= 0) queue.enqueue(x.key); 
        if (cmphi > 0) keys(x.right, queue, lo, hi); 
    } 
    
    /**
     * Returns all values in the symbol table in the given range,
     * as an {@code Iterable}.
     * @param  lo minimum endpoint
     * @param  hi maximum endpoint
     * @return all keys in the symbol table between {@code lo} 
     *    (inclusive) and {@code hi} (inclusive) as an {@code Iterable}
     * @throws IllegalArgumentException if either {@code lo} or {@code hi}
     *    is {@code null}
     */
    public Iterable<Value> values(Key lo, Key hi) 
    {
        if (lo == null) throw new IllegalArgumentException("first argument to keys() is null");
        if (hi == null) throw new IllegalArgumentException("second argument to keys() is null");

        LinkedQueue<Value> queue = new LinkedQueue<Value>();
        // if (isEmpty() || lo.compareTo(hi) > 0) return queue;
        values(root, queue, lo, hi);
        return queue;
    } 
    
    // add the keys between lo and hi in the subtree rooted at x
    // to the queue
    private void values(Node x, LinkedQueue<Value> queue, Key lo, Key hi) 
    { 
        if (x == null) return; 
        int cmplo = lo.compareTo(x.key); 
        int cmphi = hi.compareTo(x.key); 
        if (cmplo < 0) values(x.left, queue, lo, hi); 
        if (cmplo <= 0 && cmphi >= 0) queue.enqueue(x.val); 
        if (cmphi > 0) values(x.right, queue, lo, hi); 
    } 

    /**
     * Returns the number of keys in the symbol table in the given range.
     * @param  lo minimum endpoint
     * @param  hi maximum endpoint
     * @return the number of keys in the symbol table between {@code lo} (inclusive) and {@code hi} (inclusive)
     * @throws IllegalArgumentException if either {@code lo} or {@code hi} is {@code null}
     */
    public int size(Key lo, Key hi) 
    {
        if (lo == null) throw new IllegalArgumentException("first argument to size() is null");
        if (hi == null) throw new IllegalArgumentException("second argument to size() is null");

        if (lo.compareTo(hi) > 0) return 0;
        if (contains(hi)) return rank(hi) - rank(lo) + 1;
        else              return rank(hi) - rank(lo);
    }
}
