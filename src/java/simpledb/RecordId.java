package simpledb;

import java.io.Serializable;

/**
 * A RecordId is a reference to a specific tuple on a specific page of a
 * specific table.
 */
public class RecordId implements Serializable {

    private static final long serialVersionUID = 1L;
    private PageId pid;
    private int tupleno;

    public RecordId()
    {
	pid = new PageId()
	{
	    @Override
	    public int[] serialize()
	    	{return new int[0];}

	    @Override
	    public int getTableId()
	    	{return 0;}

	    @Override
	    public int pageNumber()
	    	{return 0;}
	};
	tupleno = 0;
    }
    /**
     * Creates a new RecordId referring to the specified PageId and tuple
     * number.
     * 
     * @param pid
     *            the pageid of the page on which the tuple resides
     * @param tupleno
     *            the tuple number within the page.
     */
    public RecordId(PageId pid, int tupleno) {
        // some code goes here
       this.pid = pid;
       this.tupleno = tupleno;
    }

    /**
     * @return the tuple number this RecordId references.
     */
    public int tupleno() {
        // some code goes here
        return tupleno;
    }

    /**
     * @return the page id this RecordId references.
     */
    public PageId getPageId() {
        // some code goes here
        return pid;
    }

    /**
     * Two RecordId objects are considered equal if they represent the same
     * tuple.
     * 
     * @return True if this and o represent the same tuple
     */
    @Override
    public boolean equals(Object o) {
        // some code goes here
	if(o == null)
	    return false;
	if(o == this)
	    return true;
	if(getClass() != o.getClass())
	    return false;

	RecordId recordId = (RecordId)o;
	
	return recordId.tupleno == this.tupleno && recordId.pid.equals(this.pid);
        //throw new UnsupportedOperationException("implement this");
    }

    /**
     * You should implement the hashCode() so that two equal RecordId instances
     * (with respect to equals()) have the same hashCode().
     * 
     * @return An int that is the same for equal RecordId objects.
     */
    @Override
    public int hashCode() {
        // some code goes here
        int result = 17;

	return 17*37+this.pid.hashCode()+tupleno;
        //throw new UnsupportedOperationException("implement this");

    }

}
