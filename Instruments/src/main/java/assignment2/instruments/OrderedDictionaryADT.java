package assignment2;

/* Spencer Smith, Anna Fortenberry
 * sms0697, akf0099
 * This file declares functions necessary for the implemenation of a simple database manager that
 * stores objects in an ordered dictionary using a binary search tree.
 */

public interface OrderedDictionaryADT {

    public InstrumentRecord find(DataKey k) throws DictionaryException;

    public void insert(InstrumentRecord r) throws DictionaryException;
    private Node recurInsert(Node n, InstrumentRecord r) throws DictionaryException;

    public void remove(DataKey k) throws DictionaryException;

    public InstrumentRecord successor(Node n) throws DictionaryException;

    public InstrumentRecord predecessor(Node n) throws DictionaryException;

    public InstrumentRecord smallest() throws DictionaryException;

    public InstrumentRecord largest() throws DictionaryException;

    public boolean isEmpty();
}
