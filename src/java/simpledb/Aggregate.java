package simpledb;

import java.util.*;

/**
 * The Aggregation operator that computes an aggregate (e.g., sum, avg, max,
 * min). Note that we only support aggregates over a single column, grouped by a
 * single column.
 */
public class Aggregate extends Operator {

    private static final long serialVersionUID = 1L;

    private DbIterator child,agg_it=null;
    private int afield;
    private int  gfield;
    private Aggregator.Op aop;
    private Aggregator aggregator;
    private Type group_Type,agg_Type;
    /**
     * Constructor.
     *
     * Implementation hint: depending on the type of afield, you will want to
     * construct an {@link IntAggregator} or {@link StringAggregator} to help
     * you with your implementation of readNext().
     *
     *
     * @param child
     *            The DbIterator that is feeding us tuples.
     * @param afield
     *            The column over which we are computing an aggregate.
     * @param gfield
     *            The column over which we are grouping the result, or -1 if
     *            there is no grouping
     * @param aop
     *            The aggregation operator to use
     * @throws TransactionAbortedException 
     * @throws DbException 
     */
    public Aggregate(DbIterator child, int afield, int gfield, Aggregator.Op aop) throws DbException, TransactionAbortedException {
	// some code goes here
	  this.child = child;
      this.afield  = afield;
      this.gfield= gfield;
      this.aop= aop;
      if (!(gfield== Aggregator.NO_GROUPING))
            group_Type = child.getTupleDesc().getFieldType(gfield);
      else
            group_Type=null;
      agg_Type = child.getTupleDesc().getFieldType(afield);
      if(agg_Type==Type.INT_TYPE)
    			aggregator = new IntegerAggregator(gfield, group_Type,afield, aop);
      else
    			aggregator = new StringAggregator(gfield, group_Type,afield, aop);
     child.open();
     while(child.hasNext())
    	 aggregator.mergeTupleIntoGroup(child.next());

    agg_it = aggregator.iterator();
    child.close();


    }

    /**
     * @return If this aggregate is accompanied by a groupby, return the groupby
     *         field index in the <b>INPUT</b> tuples. If not, return
     *         {@link simpledb.Aggregator#NO_GROUPING}
     * */
    public int groupField() {
	// some code goes here
	return gfield;
    }

    /**
     * @return If this aggregate is accompanied by a group by, return the name
     *         of the groupby field in the <b>OUTPUT</b> tuples If not, return
     *         null;
     * */
    public String groupFieldName() {
	// some code goes here
	if (gfield== Aggregator.NO_GROUPING)
	    return null;
	return child.getTupleDesc().getFieldName(gfield);
    }

    /**
     * @return the aggregate field
     * */
    public int aggregateField() {
	// some code goes here
	return afield;
    }

    /**
     * @return return the name of the aggregate field in the <b>OUTPUT</b>
     *         tuples
     * */
    public String aggregateFieldName() {
	// some code goes here
	return child.getTupleDesc().getFieldName(afield);
    }

    /**
     * @return return the aggregate operator
     * */
    public Aggregator.Op aggregateOp() {
	// some code goes here
	return aop;
    }

    public static String nameOfAggregatorOp(Aggregator.Op aop) {
	return aop.toString();
    }

    public void open() throws NoSuchElementException, DbException,
	    TransactionAbortedException {
	// some code goes here
	super.open();
    child.open();
    agg_it.open();
    
    }


    /**
     * Returns the next tuple. If there is a group by field, then the first
     * field is the field by which we are grouping, and the second field is the
     * result of computing the aggregate, If there is no group by field, then
     * the result tuple should contain one field representing the result of the
     * aggregate. Should return null if there are no more tuples.
     */
    protected Tuple fetchNext() throws TransactionAbortedException, DbException {
	// some code goes here
	if(agg_it.hasNext())
         return agg_it.next();
    return null;

    }

    public void rewind() throws DbException, TransactionAbortedException {
	// some code goes here
	agg_it.rewind();
    }

    /**
     * Returns the TupleDesc of this Aggregate. If there is no group by field,
     * this will have one field - the aggregate column. If there is a group by
     * field, the first field will be the group by field, and the second will be
     * the aggregate value column.
     *
     * The name of an aggregate column should be informative. For example:
     * "aggName(aop) (child_td.getFieldName(afield))" where aop and afield are
     * given in the constructor, and child_td is the TupleDesc of the child
     * iterator.
     */
    public TupleDesc getTupleDesc() {
	// some code goes here
	TupleDesc td=child.getTupleDesc();
	if(gfield!=-1)
    	{    Type[] ty=new Type[2];
	     	String[] st=new String[2];
    		 
    	     ty[0]=td.getFieldType(gfield);
    	     ty[1]=Type.INT_TYPE;
    	     st[0]=aop.toString()+" "+td.getFieldName(afield);
    	     st[1]=td.getFieldName(afield);
             return new TupleDesc(ty,st);
    	}
    	     
   else
    	{  
    		Type[] ty=new Type[1];
    		String[] st=new String[1];
    	    ty[0]=Type.INT_TYPE;
    		st[0]=aop.toString()+" "+td.getFieldName(afield);
    		return new TupleDesc(ty,st);
    	}
    }

    public void close() {
	// some code goes here
	super.close();
    child.close();
	agg_it.close();
    }

    @Override
    public DbIterator[] getChildren() {
	// some code goes here
	return new DbIterator[] {agg_it};
    }

    @Override
    public void setChildren(DbIterator[] children) {
	// some code goes here
	agg_it = children[0];
    }

}