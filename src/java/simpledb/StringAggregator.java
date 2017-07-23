package simpledb;
import java.util.*;;

/**
 * Knows how to compute some aggregate over a set of StringFields.
 */
public class StringAggregator implements Aggregator {

    private static final long serialVersionUID = 1L;

    /**
     * Aggregate constructor
     * @param gbfield the 0-based index of the group-by field in the tuple, or NO_GROUPING if there is no grouping
     * @param gbfieldtype the type of the group by field (e.g., Type.INT_TYPE), or null if there is no grouping
     * @param afield the 0-based index of the aggregate field in the tuple
     * @param what aggregation operator to use -- only supports COUNT
     * @throws IllegalArgumentException if what != COUNT
     */
    
    private boolean noGrouping;
    private int gbFieldIndex;
    private Type gbFieldType;
    private int aFieldIndex;
    private Op operatorType;
    private HashMap<Field, Integer> counter;

    public StringAggregator(int gbfield, Type gbfieldtype, int afield, Op what) {
        // some code goes here
    	gbFieldIndex = gbfield;
    	gbFieldType = gbfieldtype;
    	aFieldIndex = afield;
    	operatorType = what;
    	counter = new HashMap<Field, Integer>();
    }

    /**
     * Merge a new tuple into the aggregate, grouping as indicated in the constructor
     * @param tup the Tuple containing an aggregate field and a group-by field
     */
    public void mergeTupleIntoGroup(Tuple tup) {
        // some code goes here
    	Field gbFieldTuple = null;
    	if (gbFieldIndex != NO_GROUPING )
    		gbFieldTuple = tup.getField(gbFieldIndex);
    	if (!counter.containsKey(gbFieldTuple)){
    		counter.put(gbFieldTuple, 0);
    	}
    	int count = counter.get(gbFieldTuple);
    	counter.put(gbFieldTuple, count+1);
    }

    /**
     * Create a DbIterator over group aggregate results.
     *
     * @return a DbIterator whose tuples are the pair (groupVal,
     *   aggregateVal) if using group, or a single (aggregateVal) if no
     *   grouping. The aggregateVal is determined by the type of
     *   aggregate specified in the constructor.
     */
    
    private TupleDesc createGroupByTupleDesc()
    {
    	String[] names;
    	Type[] types;
    	if (gbFieldIndex == Aggregator.NO_GROUPING)
    	{
    		names = new String[] {"aggregateValue"};
    		types = new Type[] {Type.INT_TYPE};
    	}
    	else
    	{
    		names = new String[] {"groupValue", "aggregateValue"};
    		types = new Type[] {gbFieldType, Type.INT_TYPE};
    	}
    	return new TupleDesc(types, names);
    }
    
    public DbIterator iterator() {
        // some code goes here
    	ArrayList<Tuple> tuples = new ArrayList<Tuple>();
    	TupleDesc tupledesc = createGroupByTupleDesc();
    	Tuple tuple;
    	for (Field key : counter.keySet())
    	{
    		int aggregateVal = counter.get(key);
    		tuple = new Tuple(tupledesc);
    		if (gbFieldIndex == Aggregator.NO_GROUPING){
    			tuple.setField(0, new IntField(aggregateVal));
    		}
    		else {
        		tuple.setField(0, key);
        		tuple.setField(1, new IntField(aggregateVal));    			
    		}
    		tuples.add(tuple);
    	}
    	return new TupleIterator(tupledesc, tuples);
    }

}
