package simpledb;
import java.util.*;

/**
 * Knows how to compute some aggregate over a set of IntFields.
 */
public class IntegerAggregator implements Aggregator {

    private static final long serialVersionUID = 1L;

    private int gfield;
    private Type group_Type;
    private int afield;
    private Op what;
    private HashMap<Field,Integer> agg_values;
    private HashMap<Field,Integer> counter;
    /**
     * Aggregate constructor
     *
     * @param gbfield
     *            the 0-based index of the group-by field in the tuple, or
     *            NO_GROUPING if there is no grouping
     * @param gbfieldtype
     *            the type of the group by field (e.g., Type.INT_TYPE), or null
     *            if there is no grouping
     * @param afield
     *            the 0-based index of the aggregate field in the tuple
     * @param what
     *            the aggregation operator
     */

    public IntegerAggregator(int gbfield, Type gbfieldtype, int afield, Op what) {
        // some code goes here
    	this.gfield = gbfield;
    	this.group_Type = gbfieldtype;
    	this.afield = afield;
    	this.what= what;
    	agg_values= new HashMap<Field, Integer>();
    	counter= new HashMap<Field, Integer>();
    }



    /* * Merge a new tuple into the aggregate, grouping as indicated in the
     * constructor
     *
     * @param tup
     *            the Tuple containing an aggregate field and a group-by field
     */
    public void agg_operation(Aggregator.Op what,int agg_val_tuple,int agg_val_map,Field group_field)
    {
    	switch(what)
    	{
    	  case SUM: case AVG:
		     counter.put(group_field,counter.get(group_field)+1);
			 agg_val_map += agg_val_tuple;
			 break;
		  case COUNT:
			 agg_val_map++;
			 break;
    	  case MIN:
    			if(agg_val_tuple <agg_val_map)
    				agg_val_map=agg_val_tuple;
    			break;
    	  case MAX:
    		  if(agg_val_tuple>agg_val_map)
  				agg_val_map=agg_val_tuple;
  			break;
		  default:
				break;
    	}
    	agg_values.put(group_field, agg_val_map);
    }
    public void mergeTupleIntoGroup(Tuple tup) {
        // some code goes here
    	int agg_val_tuple = ((IntField) tup.getField(afield)).getValue();
    	Field group_field;
    	if(gfield==Aggregator.NO_GROUPING)
    		group_field=null;
    	else
    		group_field=tup.getField(gfield);

      if(!agg_values.containsKey(group_field))
    	{	if(what==Op.MAX)
    		  agg_values.put(group_field, Integer.MIN_VALUE);
    	    else if(what==Op.MIN)
    	      agg_values.put(group_field, Integer.MAX_VALUE);
    	    else
    	    	agg_values.put(group_field,0);
    	    counter.put(group_field, 0);
    	}

	    int agg_val_map = agg_values.get(group_field);
	   agg_operation(what,agg_val_tuple,agg_val_map,group_field);


    }



    /**
     * Create a DbIterator over group aggregate results.
     *
     * @return a DbIterator whose tuples are the pair (groupVal, aggregateVal)
     *         if using group, or a single (aggregateVal) if no grouping. The
     *         aggregateVal is determined by the type of aggregate specified in
     *         the constructor.
     */
    public DbIterator iterator() {
        // some code goes here
    	Type[] type;
    	String[] name;
    	if (gfield== Aggregator.NO_GROUPING)
    	{
    		name=new String[] {"aggregateValue"};
    		type=new Type[] {Type.INT_TYPE};
    	}
    	else
    	{
    		name=new String[] {"groupValue", "aggregateValue"};
    		type=new Type[] {group_Type,Type.INT_TYPE};
    	}

        ArrayList<Tuple> output_tuples = new ArrayList<Tuple>();
    	TupleDesc td = new TupleDesc(type,name);
    	for (Field group_val : agg_values.keySet())
    	{
    		int output_aggVal;
    		Tuple output_tup=new Tuple(td);
    		if (what == Op.AVG)
    			output_aggVal = agg_values.get(group_val)/counter.get(group_val);
    		else
    			output_aggVal = agg_values.get(group_val);


    		if (gfield == Aggregator.NO_GROUPING)
    			output_tup.setField(0, new IntField(output_aggVal));

    		else {
        		output_tup.setField(0, group_val);
        		output_tup.setField(1, new IntField(output_aggVal));
    		     }
    		output_tuples.add(output_tup);
    	}
	return new TupleIterator(td, output_tuples);
    }

}
