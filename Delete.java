package simpledb;

import java.io.IOException;

/**
 * The delete operator. Delete reads tuples from its child operator and removes
 * them from the table they belong to.
 */
public class Delete extends Operator {
	TransactionId tid;
	DbIterator dbi;
	DbFileIterator dbfi;
	DbFile fi;
	TupleDesc td;
	

    private static final long serialVersionUID = 1L;

    /**
     * Constructor specifying the transaction that this delete belongs to as
     * well as the child to read from.
     * 
     * @param t
     *            The transaction this delete runs in
     * @param child
     *            The child operator from which to read tuples for deletion
     */
    public Delete(TransactionId t, DbIterator child) {
        // some code goes here
    	tid=t;
    	dbi=child;
    	Type[] ty = {Type.INT_TYPE};
    	String[] na = {"No. of deleted tuples"};
        td = new TupleDesc(ty,na);
    }

    public TupleDesc getTupleDesc() {
        // some code goes here
    	
        return td;
    }

    public void open() throws DbException, TransactionAbortedException {
        // some code goes here
    	dbi.open();
    }

    public void close() {
        // some code goes here
    	dbi.close();
    }

    public void rewind() throws DbException, TransactionAbortedException {
        // some code goes here
    	dbi.rewind();
    }

    /**
     * Deletes tuples as they are read from the child operator. Deletes are
     * processed via the buffer pool (which can be accessed via the
     * Database.getBufferPool() method.
     * 
     * @return A 1-field tuple containing the number of deleted records.
     * @see Database#getBufferPool
     * @see BufferPool#deleteTuple
     */
    protected Tuple fetchNext() throws TransactionAbortedException, DbException {
        // some code goes here
    	Tuple toReturn = null ;
    	int inc =0;
    	    	
    	while(dbi.hasNext()){
    		inc++;
    		Tuple tuple = dbi.next();
    		Database.getBufferPool().deleteTuple(tid, tuple);    		
    		
    	}
    	
    	toReturn = new Tuple(td);
    	Field f = new IntField(inc);
		toReturn.setField(0,f);
        return toReturn;
    	
    }

    @Override
    public DbIterator[] getChildren() {
        // some code goes here
    	DbIterator[] chi = {dbi};
        return chi;
    }

    @Override
    public void setChildren(DbIterator[] children) {
        // some code goes here
    	dbi=children[0];
    }

}
