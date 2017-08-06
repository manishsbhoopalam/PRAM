package simpledb;

import java.io.Serializable;
import java.util.*;

/**
 * TupleDesc describes the schema of a tuple.
 */
public class TupleDesc implements Serializable, Iterable<TupleDesc.TDItem> {

    /**
     * A help class to facilitate organizing the information of each field
     * */

    private List<TDItem> tdItems = new ArrayList();

    public TupleDesc(){

    }

    public static class TDItem implements Serializable {

        private static final long serialVersionUID = 1L;

        /**
         * The type of the field
         * */
        Type fieldType;
        
        /**
         * The name of the field
         * */
        String fieldName;

        public TDItem(Type t, String n) {
            this.fieldName = n;
            this.fieldType = t;
        }

        public String toString() {
            return fieldName + "(" + fieldType + ")";
        }
    }

    /**
     * @return
     *        An iterator which iterates over all the field TDItems
     *        that are included in this TupleDesc
     * */
    public Iterator<TDItem> iterator() 
   {
        // some code goes here
	  if (tdItems.size() > 0) 
	{
            return new Iterator<TDItem>() 
	   {
                private Integer key = 0;

                @Override
                public boolean hasNext() 
		{
                    return key < tdItems.size();
                }

                @Override
                public TDItem next() 
		{
                    int num = key;
                    key++;
                    return tdItems.get(num);
                }
           };
        }
	
	else 
	{
            return null;
        }
   }

    private static final long serialVersionUID = 1L;

    /**
     * Create a new TupleDesc with typeAr.length fields with fields of the
     * specified types, with associated named fields.
     * 
     * @param typeAr
     *            array specifying the number of and types of fields in this
     *            TupleDesc. It must contain at least one entry.
     * @param fieldAr
     *            array specifying the names of the fields. Note that names may
     *            be null.
     */
    public TupleDesc(Type[] typeAr, String[] fieldAr) 
   {
        // some code goes here
	int l =0;
        if (typeAr.length!=fieldAr.length) 
	{	
	   System.out.println("Data is not matching");
  	   return;
	}

        l = typeAr.length;

        for (int i=0;i<l;i++)
	{
            TDItem tdi = new TDItem(typeAr[i],fieldAr[i]);
            this.tdItems.add(tdi);
        }
   }

    /**
     * Constructor. Create a new tuple desc with typeAr.length fields with
     * fields of the specified types, with anonymous (unnamed) fields.
     * 
     * @param typeAr
     *            array specifying the number of and types of fields in this
     *            TupleDesc. It must contain at least one entry.
     */
    public TupleDesc(Type[] typeAr) 
    {
        // some code goes here
	String anon = "anon";
        for (int i=0;i<typeAr.length;i++){
            TDItem tdi = new TDItem(typeAr[i],anon+i);
            this.tdItems.add(tdi);
        }
    }

    /**
     * @return the number of fields in this TupleDesc
     */
    public int numFields() 
   {
        // some code goes here

	int count = 0;
        for (TDItem tdItem:tdItems)
	{
            if (tdItem.fieldType == Type.INT_TYPE)
	   {
                count++;
           }
        }
        return count;
   }

    /**
     * Gets the (possibly null) field name of the ith field of this TupleDesc.
     * 
     * @param i
     *            index of the field name to return. It must be a valid index.
     * @return the name of the ith field
     * @throws NoSuchElementException
     *             if i is not a valid field reference.
     */
    public String getFieldName(int i) throws NoSuchElementException 
    {
        // some code goes here

	if ((i >= 0) && (i < this.tdItems.size()))
	{
            return this.tdItems.get(i).fieldName;
        }
        return null;
    }

    /**
     * Gets the type of the ith field of this TupleDesc.
     * 
     * @param i
     *            The index of the field to get the type of. It must be a valid
     *            index.
     * @return the type of the ith field
     * @throws NoSuchElementException
     *             if i is not a valid field reference.
     */
    public Type getFieldType(int i) throws NoSuchElementException 
    {
        // some code goes here

	if ((i >= 0)&&(i < this.tdItems.size()))
	{
            return this.tdItems.get(i).fieldType;
        }
        return null;
    }

    /**
     * Find the index of the field with a given name.
     * 
     * @param name
     *            name of the field.
     * @return the index of the field that is first to have the given name.
     * @throws NoSuchElementException
     *             if no field with a matching name is found.
     */
    public int fieldNameToIndex(String name) throws NoSuchElementException {
        // some code goes here

	for (int i=0;i < this.tdItems.size();i++)
	{
            if (this.tdItems.get(i).fieldName.equals(name))
		{return i;}
        }
	throw new NoSuchElementException ();
    }

    /**
     * @return The size (in bytes) of tuples corresponding to this TupleDesc.
     *         Note that tuples from a given TupleDesc are of a fixed size.
     */
    public int getSize() {
        // some code goes here

	int count=0;

        for (int i=0;i<tdItems.size();i++)
	{
            if (tdItems.get(i).fieldType == Type.INT_TYPE)
	   {
                count += Type.INT_TYPE.getLen();
           }
	    else
	   {
                count += Type.STRING_TYPE.getLen();
           }
        }
        return count;
    }

    /**
     * Merge two TupleDescs into one, with td1.numFields + td2.numFields fields,
     * with the first td1.numFields coming from td1 and the remaining from td2.
     * 
     * @param td1
     *            The TupleDesc with the first fields of the new TupleDesc
     * @param td2
     *            The TupleDesc with the last fields of the TupleDesc
     * @return the new TupleDesc
     */
    public static TupleDesc merge(TupleDesc td1, TupleDesc td2) {
        // some code goes here

	TupleDesc td = new TupleDesc();
        int l1 = td1.tdItems.size();
        int l2 = td2.tdItems.size();

        for (int i = 0; i<l1; i++)
	{
            td.tdItems.add(td1.tdItems.get(i));
        }
        for (int i = 0; i<l2; i++)
	{
            td.tdItems.add(td2.tdItems.get(i));
        }
        return td;
    }

    /**
     * Compares the specified object with this TupleDesc for equality. Two
     * TupleDescs are considered equal if they are the same size and if the n-th
     * type in this TupleDesc is equal to the n-th type in td.
     * 
     * @param o
     *            the Object to be compared for equality with this TupleDesc.
     * @return true if the object is equal to this TupleDesc.
     */
    public boolean equals(Object o) {
        // some code goes here

	if(o == null)
	   return false;
        if (o == this)
	   return true;
        if (getClass() != o.getClass())
	{
            return false;
        }
        TupleDesc td = (TupleDesc) o;
        if(td.tdItems.size()==this.tdItems.size())
	{
	    for(int i=0; i<this.tdItems.size(); i++)
	    {
		TDItem b1 = this.tdItems.get(i);
		TDItem b2 = td.tdItems.get(i);
		if(b1.fieldType != b2.fieldType)
		    return false;
	    }
	    return true;
	}
	return false;
    }

    public int hashCode() {
        // If you want to use TupleDesc as keys for HashMap, implement this so
        // that equal objects have equals hashCode() results
        throw new UnsupportedOperationException("unimplemented");
    }

    /**
     * Returns a String describing this descriptor. It should be of the form
     * "fieldType[0](fieldName[0]), ..., fieldType[M](fieldName[M])", although
     * the exact format does not matter.
     * 
     * @return String describing this descriptor.
     */
    public String toString() {
        // some code goes here

	StringBuilder sb = new StringBuilder();
        for (int i=0; i<tdItems.size(); i++)
	{
            sb.append(tdItems.get(i).fieldType.toString());
            sb.append(tdItems.get(i).fieldName.toString());
            sb.append(",");
            sb.append(" ");
        }
        return sb.toString();
    }
}