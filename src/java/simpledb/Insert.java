package simpledb;

import java.io.IOException;

/**
 * Inserts tuples read from the child operator into the tableid specified in the
 * constructor
 */
public class Insert extends Operator {
	TransactionId tid;
	DbIterator iterate;
	int table;
	TupleDesc td;
	Boolean ifCalled=false;
	
	

    private static final long serialVersionUID = 1L;

    /**
     * Constructor.
     * 
     * @param t
     *            The transaction running the insert.
     * @param child
     *            The child operator from which to read tuples to be inserted.
     * @param tableid
     *            The table in which to insert tuples.
     * @throws DbException
     *             if TupleDesc of child differs from table into which we are to
     *             insert.
     */
    public Insert(TransactionId t,DbIterator child, int tableid)
            throws DbException {
        // some code goes here
    	tid=t;
    	iterate=child;
    	table=tableid;  
    	Type[] ty = {Type.INT_TYPE};
    	String[] na = {"No. of inserted tuples"};
        td = new TupleDesc(ty,na);
    	
    	
    }

    public TupleDesc getTupleDesc() {
        // some code goes here
    	
        return td;
    }

    public void open() throws DbException, TransactionAbortedException {
        // some code goes here
    	iterate.open();
    	 super.open();
    }

    public void close() {
        // some code goes here
    	iterate.close();
    	 super.close();
    }

    public void rewind() throws DbException, TransactionAbortedException {
        // some code goes here
    	iterate.rewind();
    }

    /**
     * Inserts tuples read from child into the tableid specified by the
     * constructor. It returns a one field tuple containing the number of
     * inserted records. Inserts should be passed through BufferPool. An
     * instances of BufferPool is available via Database.getBufferPool(). Note
     * that insert DOES NOT need check to see if a particular tuple is a
     * duplicate before inserting it.
     * 
     * @return A 1-field tuple containing the number of inserted records, or
     *         null if called more than once.
     * @see Database#getBufferPool
     * @see BufferPool#insertTuple
     */
    protected Tuple fetchNext() throws TransactionAbortedException, DbException {
        // some code goes here
    	Tuple toReturn = null ;
    	Tuple tuple;
    	int inc =0;
    	if(ifCalled){
    		return null;
    	}
    	    	
    		ifCalled=true;
    	
    	while(iterate.hasNext()){
    		inc++;
    		tuple = iterate.next();
    		try {
				Database.getBufferPool().insertTuple(tid, table, tuple);
				
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}    		
    		
    	}
    	toReturn = new Tuple(td);
    	Field f = new IntField(inc);
		toReturn.setField(0,f);
        return toReturn;
    	
    }

    @Override
    public DbIterator[] getChildren() {
        // some code goes here
    	DbIterator[] chi = {iterate};
        return chi;
    }

    @Override
    public void setChildren(DbIterator[] children) {
        // some code goes here
    	iterate=children[0];
    }
}