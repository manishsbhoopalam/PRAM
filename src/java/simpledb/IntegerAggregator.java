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
    private HashMap<Field,ArrayList<Integer>> agg_values;
    private TupleDesc td_child;

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
       agg_values=new HashMap<Field,ArrayList<Integer>>();
    }

    /**
     * Merge a new tuple into the aggregate, grouping as indicated in the
     * constructor
     *
     * @param tup
     *            the Tuple containing an aggregate field and a group-by field
     */
    public void mergeTupleIntoGroup(Tuple tup) {
        // some code goes here
        td_child=tup.getTupleDesc();
        Field group_field;
    	if(gfield==Aggregator.NO_GROUPING)
    		if(group_Type==Type.INT_TYPE)
    			group_field=new IntField(1);
    		else
    			group_field=new StringField("s",1);
    	else
    		group_field=tup.getField(gfield);

        if(!agg_values.containsKey(group_field))
        { 	ArrayList<Integer> temp=new ArrayList<Integer>();
        	agg_values.put(group_field,temp);
          
        }
	
        agg_values.get(group_field).add(((IntField)tup.getField(afield)).getValue());
       
      


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
        //Create TupleDesc for output tuples
        Type[] ty;
        String[] st;
        ArrayList<Tuple> output_tuplist=new ArrayList<Tuple>();
	    if(gfield!=-1)
    	{    ty=new Type[2];
             st=new String[2];

    	     ty[0]=group_Type;
    	     ty[1]=Type.INT_TYPE;
    	     st[0]=what.toString()+" "+td_child.getFieldName(afield);
    	     st[1]=td_child.getFieldName(afield);

    	}

        else
    	{
    		ty=new Type[1];
    		st=new String[1];
    	    ty[0]=Type.INT_TYPE;
    		st[0]=what.toString()+" "+td_child.getFieldName(afield);

    	}

        for (Field group_val : agg_values.keySet())
        { int res = 0;
          switch(what)
            {
    	   case SUM:
                  
                    for(Integer i:agg_values.get(group_val))
                        res+=i;
                    break;
           case AVG:
                    int count=agg_values.get(group_val).size();
                    int sum=0;
                    for(Integer i:agg_values.get(group_val))
                        sum+=i;
                    res=sum/count;
                    break;

          case COUNT:
                    res=agg_values.get(group_val).size();
                    break;
    	   case MIN:
                    int min=agg_values.get(group_val).get(0);
                    for(Integer i:agg_values.get(group_val))
                        if(i<min)
                            min=i;
                    res=min;
                    break;
    	   case MAX:
                    int max=agg_values.get(group_val).get(0);
                    for(Integer i:agg_values.get(group_val))
                        if(i>max)
                            max=i;
                    res=max;
                    break;

		   default:
			     	break;

            }
            TupleDesc td=new TupleDesc(ty,st);
            Tuple output_tup=new Tuple(td);
            if (gfield == Aggregator.NO_GROUPING)
    			output_tup.setField(0, new IntField(res));

    		else {
        		output_tup.setField(0, group_val);
        		output_tup.setField(1, new IntField(res));
    		     }
    		output_tuplist.add(output_tup);

        }
        return new TupleIterator(new TupleDesc(ty,st),output_tuplist);

   }

}
