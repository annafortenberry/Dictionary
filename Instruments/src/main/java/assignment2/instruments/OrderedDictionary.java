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
            throw new DictionaryException("There is no record matching the given key.")
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
                if (!hasLeftChild()) {
                    throw new DictionaryException("There is no record matching the given key.");
                }
                curr = curr.getLeftChild(); // set equal to leftChild (root of left subtree)
            } else if (-1 == comparison) {
                // The DataKey of curr is smaller than the parameter DataKey.
                // This means, if the node exists, it would be stored in the
                // right subtree, in an ordered BST.
                if (!hasRightChild()) {
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

    // private utility function for public insert() function
    private Node recurInsert(Node n, InstrumentRecord r) throws DictionaryException {

        // case 1: first element inserted into BST

        if (n.isEmpty()) {
            return n;           // don't need to return new Node bc root = new Node at init?
        }

        // case 2: BST has already been initialized
        // access the DataKey of current node and compare to parameter DataKey
        // If DataKey k == curr's (this) DataKey: compareTo() returns 0.
        // If DataKey k >  curr's (this) DataKey: compareTo() returns -1.
        // If DataKey k <  curr's (this) DataKey: compareTo() returns 1.

        int comparison = n.getData().getDataKey().compareTo(r);

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
    public void remove(DataKey k) throws DictionaryException {

        // use find function to locate and return node to be removed

        Node rm = find(k);
        Node parent = rm.getParent();
        boolean hasOneChild = ( (rm.hasLeftChild() && !rm.hasRightChild()) || (!rm.hasLeftChild() && rm.hasRightChild()) )

        // case 1: only the root node exists
        if (rm == root) {

            rm.setData(null);

        }
        // case 2: leaf node, will not enter if only root node exists
        else if (rm.isLeaf()) {

            if (parent.getLeftChild() == rm) {
                parent.setLeftChild(null);
            }
            else {
                parent.setRightChild(null);
            }

        }
        // case 3: one child, not a leaf node
        else if (hasOneChild) {
            if (rm.hasLeftChild()) {
                rm.setParent(rm.getLeftChild());
                //rm.setLeftChild(null);
            }
            else {
                rm.setParent(rm.getRightChild());
            }

        }
        // case 4: must have two children, will not be root node
        else {

            // If root DataKey k == rm's (this) DataKey: compareTo() returns 0.
            //     Should not occur because insert function prevents repeats of elements.
            // If root DataKey k >  rm's (this) DataKey: compareTo() returns -1.
            // If root DataKey k <  rm's (this) DataKey: compareTo() returns 1.

            int comparison = rm.getData().getDataKey().compareTo(root.getData().getDataKey());

            // data to left of root is smaller than root - left subtree case
            if (comparison == -1) {




            }
            // data to right of root is greater than root - right subtree case
            else {

            }


        }
    }

    // Func: Successor
    /* Parameters:  none
       Return:      DataKey k, (data, left child, right child)
       Desc:        This function traverses through the tree and returns the successor.
                    This is the smallest element in the right subtree.
     */
    public InstrumentRecord successor() throws DictionaryException {

        Node s = root.getRightChild();

        while (s.hasLeftChild()) {
            s = s.getLeftChild();
        }

        return s.getData().getDataKey();
    }

    // Func: Predecessor
    /* Parameters:  none
       Return:      DataKey k, (data, left child, right child)
       Desc:        This function traverses through the tree and returns the predecessor.
                    This is the largest element in the left subtree.
     */
    public InsrumentRecord predecessor(Node n) throws DictionaryException {

        Node temp = n.getLeftChild();

        while (temp.hasRightChild()) {
            temp = temp.getRightChild();
        }

        return temp.getData().getDataKey();
    }

    public InstrumentRecord smallest() throws DictionaryException {

        Node smallest = root;

        while (smallest.hasLeftChild()) {

            smallest = smallest.getLeftChild();
        }

        return smallest.getData().getDataKey();
    }

    public InstrumentRecord largest() throws DictionaryException {

        Node largest = root;

        while (largest.hasRightChild()) {

            largest = largest.getRightChild();
        }

        return largest.getData().getDataKey();
    }

    public boolean isEmpty() {

        if (root.getData().getDataKey() == null)
            return true;
        else
            return false;
    }
}
