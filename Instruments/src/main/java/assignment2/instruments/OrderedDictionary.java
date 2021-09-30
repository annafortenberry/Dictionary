package assignment2.instruments;

/* Spencer Smith, Anna Fortenberry
 * sms0697, akf0099
 * This file implements the functions declared in OrderedDictionaryADT. It serves as a simple database manager
 * that stores objects in an ordered dictionary using a binary search tree.
 */

public class OrderedDictionary implements OrderedDictionaryADT {

    Node root;

    OrderedDictionary() {
        root = new Node();
    }

    // Func: Find
    /* Parameters:  DataKey k, (data, left child, right child)
       Return:      DataKey k, (data, left child, right child)
       Desc:        This function first calls the .isEmpty() function to check whether the tree is empty.
                    If the tree is not empty, it uses the compareTo() function to compare each node in the
                    tree with the given DataKey k.
     */
    public InstrumentRecord find(DataKey k) throws DictionaryException {

        Node curr = root;       // set curr equal to root to start
        int comparison;         // placeholder for return value of comparison

        // If the root is empty, no reason to call search function.

        if (root.isEmpty()) {
            throw new DictionaryException("There is no record matching the given key.");
        }

        // Otherwise, traverse the tree, updating the current node. Use compareTo() with the
        // current node and the passed DataKey k.

        while (true) {

            // If DataKey k == curr's (this) DataKey: compareTo() returns 0.
            // If DataKey k >  curr's (this) DataKey: compareTo() returns -1.
            // If DataKey k <  curr's (this) DataKey: compareTo() returns 1.

            comparison = curr.getData().getDataKey().compareTo(k);

            if (0 == comparison) {
                return curr.getData();
            }
            if (1 == comparison) {
                // The DataKey of curr is larger than the parameter DataKey.
                // This means, if the node exists, it would be stored in the
                // left subtree, in an ordered BST.
                if (!curr.hasLeftChild()) {
                    throw new DictionaryException("There is no record matching the given key.");
                }
                curr = curr.getLeftChild(); // set equal to leftChild (root of left subtree)
            } else if (-1 == comparison) {
                // The DataKey of curr is smaller than the parameter DataKey.
                // This means, if the node exists, it would be stored in the
                // right subtree, in an ordered BST.
                if (!curr.hasRightChild()) {
                    throw new DictionaryException("There is no record matching the given key.");
                }
                curr = curr.getRightChild(); // set equal to rightChild (root of right subtree)
            }

            // Exit iteration of loop with either left or right child if it exists.
            // Repeat comparison process until compareTo() returns 0 (find successful)
            // or an exception is thrown (find unsuccessful)
        }
    }

    // Func: Insert / Recursive Insert
    /* Parameters:  DataKey k, (data, left child, right child)
       Return:      void
       Desc:        This function calls the recursive function recurInsert to find the correct
                    place in the BST to insert a new node and updates the tree accordingly.
     */
    public void insert(InstrumentRecord r) throws DictionaryException {
        root = recurInsert(root, r);
    }

    // utility function for insert function
    public Node recurInsert(Node n, InstrumentRecord r) throws DictionaryException {

        // case 1: first element inserted into BST

        if (n.isEmpty()) {
            return n;           // don't need to return new Node bc root = new Node at init?
        }

        // case 2: BST has already been initialized
        // access the DataKey of current node and compare to parameter DataKey
        // If DataKey k == curr's (this) DataKey: compareTo() returns 0.
        // If DataKey k >  curr's (this) DataKey: compareTo() returns -1.
        // If DataKey k <  curr's (this) DataKey: compareTo() returns 1.

        int comparison = n.getData().getDataKey().compareTo(r.getDataKey());

        if (1 == comparison)
            n.setLeftChild(recurInsert(n.getLeftChild(), r));
        else if (-1 == comparison)
            n.setRightChild(recurInsert(n.getRightChild(), r));
        else
            return n;           // value already exists, no repeat insertion

        return n;
    }

    // Func: Remove
    /* Parameters:  DataKey k, (data, left child, right child)
       Return:      void
       Desc:        This function calls the search function given a DataKey k. When it finds the
                    matching key, it removes it from the BST and updates it accordingly. The
                    function cannot be called unless there is at least one element in the BST.
     */
    public void remove(InstrumentRecord r) throws DictionaryException {
        root = recurRemove(root, r);
    }

    // utility function for remove function
    public Node recurRemove(Node n, InstrumentRecord r) throws DictionaryException {

        // If r.getDataKey() == n.getData().getDataKey(): compareTo() returns 0.
        // If r.getDataKey() > n.getData().getDataKey(): compareTo() returns -1.
        // If r.getDataKey() < n.getData().getDataKey():: compareTo() returns 1.

        int comparison = n.getData().getDataKey().compareTo(r.getDataKey());

        if (1 == comparison)
            n.setLeftChild(recurRemove(n.getLeftChild(), r));
        else if (-1 == comparison)
            n.setRightChild(recurRemove(n.getRightChild(), r));
        else { // compareTo() returned 0, node to remove has been located

            // Case 1: leaf node or only has one child

            if (!n.hasLeftChild())
                return n.getRightChild();
            else if (!n.hasRightChild())
                return n.getLeftChild();

            // Case 2: has two children
            // 1. set the InstrumentRecord of the node to delete to the InstrumentRecord of its predecessor.
            // 2.
            n.setData(predecessor(n.getData().getDataKey()));
            n.setRightChild(recurRemove(n.getRightChild(), n.getData()));
        }
        return n;
    }

    // Func: Successor
    /* Parameters:  none
       Return:      DataKey k, (data, left child, right child)
       Desc:        This function traverses through the tree and returns the successor.
                    This is the smallest element in the right subtree.
     */
    public InstrumentRecord successor(DataKey k) throws DictionaryException {
        return (findSuccessor(root, k).getData());
    }

    // utility function for successor
    public Node findSuccessor(Node n, DataKey k) throws DictionaryException {

        int comparison = n.getData().getDataKey().compareTo(k);

        if (1 == comparison)
            n.setLeftChild(findSuccessor(n.getLeftChild(), k));
        else if (-1 == comparison)
            n.setRightChild(findSuccessor(n.getRightChild(), k));
        else { // compareTo() returned 0, node to start from has been located
            while (n.hasRightChild()) {
                n = n.getLeftChild();
            }
        }

        return n;
    }

    // Func: Predecessor
    /* Parameters:  none
       Return:      DataKey k, (data, left child, right child)
       Desc:        This function traverses through the tree and returns the predecessor.
                    This is the largest element in the left subtree.
     */
    public InstrumentRecord predecessor(DataKey k) throws DictionaryException {
        return (findPredecessor(root, k).getData());
    }

    // utility function for predecessor
    public Node findPredecessor(Node n, DataKey k) throws DictionaryException {

        int comparison = n.getData().getDataKey().compareTo(k);

        if (1 == comparison)
            n.setLeftChild(findSuccessor(n.getLeftChild(), k));
        else if (-1 == comparison)
            n.setRightChild(findSuccessor(n.getRightChild(), k));
        else { // compareTo() returned 0, node to start from has been located
            while (n.hasLeftChild()) {
                n = n.getRightChild();
            }
        }

        return n;
    }

    public InstrumentRecord smallest() throws DictionaryException {

        Node smallest = root;

        while (smallest.hasLeftChild()) {

            smallest = smallest.getLeftChild();
        }

        return smallest.getData();
    }

    public InstrumentRecord largest() throws DictionaryException {

        Node largest = root;

        while (largest.hasRightChild()) {

            largest = largest.getRightChild();
        }

        return largest.getData();
    }

    public boolean isEmpty() {
        return root.getData() == null;
    }
}
