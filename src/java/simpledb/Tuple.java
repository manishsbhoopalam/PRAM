package simpledb;

import java.io.Serializable;
import java.util.*;
import java.util.function.Consumer;

/**
 * Tuple maintains information about the contents of a tuple. Tuples have a
 * specified schema specified by a TupleDesc object and contain Field objects
 * with the data for each field.
 */
public class Tuple implements Serializable {

    public List<Field> tuple = new ArrayList();

    TupleDesc myTupleDesc = new TupleDesc();
    RecordId recordId = new RecordId();

    private static final long serialVersionUID = 1L;

    /**
     * Create a new tuple with the specified schema (type).
     * 
     * @param td
     *            the schema of this tuple. It must be a valid TupleDesc
     *            instance with at least one field.
     */
    public Tuple(TupleDesc td) {
        // some code goes here
	myTupleDesc = td;
	System.out.println(""+myTupleDesc.toString());
        int i=-1;
        Iterator<TupleDesc.TDItem> myTDItem = td.iterator();

        while (myTDItem.hasNext()) 
	{
            TupleDesc.TDItem newTd = myTDItem.next();

            if (newTd.fieldType == Type.INT_TYPE) 
                tuple.add(new IntField(0));
            else 
                tuple.add(new StringField("null", 255));
        }
    }

    /**
     * @return The TupleDesc representing the schema of this tuple.
     */
    public TupleDesc getTupleDesc() {
        // some code goes here
        return myTupleDesc;
    }

    /**
     * @return The RecordId representing the location of this tuple on disk. May
     *         be null.
     */
    public RecordId getRecordId() {
        // some code goes here
        return this.recordId;
    }

    /**
     * Set the RecordId information for this tuple.
     * 
     * @param rid
     *            the new RecordId for this tuple.
     */
    public void setRecordId(RecordId rid) {
        // some code goes here
	this.recordId = rid;
    }

    /**
     * Change the value of the ith field of this tuple.
     * 
     * @param i
     *            index of the field to change. It must be a valid index.
     * @param f
     *            new value for the field.
     */
    public void setField(int i, Field f) {
        // some code goes here
	if(i>=0 && i<this.tuple.size())
	   this.tuple.set(i, f);
	
    }

    /**
     * @return the value of the ith field, or null if it has not been set.
     * 
     * @param i
     *            field index to return. Must be a valid index.
     */
    public Field getField(int i) {
        // some code goes here
	if(i>=0 && i<this.tuple.size())
	    return this.tuple.get(i);
        return null;
    }

    /**
     * Returns the contents of this Tuple as a string. Note that to pass the
     * system tests, the format needs to be as follows:
     * 
     * column1\tcolumn2\tcolumn3\t...\tcolumnN\n
     * 
     * where \t is any whitespace, except newline, and \n is a newline
     */
    public String toString() {
        // some code goes here
	
	StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < this.tuple.size(); i++) 
	{
            stringBuilder.append(tuple.get(i).toString());
            stringBuilder.append("\t");
        }
        
        return  stringBuilder.toString();
        //throw new UnsupportedOperationException("Implement this");
    }
    
    /**
     * @return
     *        An iterator which iterates over all the fields of this tuple
     * */
    public Iterator<Field> fields()
    {
        // some code goes here
        if (tuple.size() > 0) 
	{
            return new Iterator<Field>() 
	    {
                private int key = 0;
                @Override
                public boolean hasNext() 
		{
                    return key<tuple.size();
                }

                @Override
                public Field next() 
		{
                    int count = key;
                    key++;
                    return tuple.get(count);

                }
            };
        }
        else return null;
    }
}