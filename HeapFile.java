package simpledb;

import java.io.*;
import java.util.*;

/**
 * HeapFile is an implementation of a DbFile that stores a collection of tuples
 * in no particular order. Tuples are stored on pages, each of which is a fixed
 * size, and the file is simply a collection of those pages. HeapFile works
 * closely with HeapPage. The format of HeapPages is described in the HeapPage
 * constructor.
 * 
 * @see simpledb.HeapPage#HeapPage
 * @author Sam Madden
 */
public class HeapFile implements DbFile {
	
	private File myFile;
	private TupleDesc myTd;

    /**
     * Constructs a heap file backed by the specified file.
     * 
     * @param f
     *            the file that stores the on-disk backing store for this heap
     *            file.
     */
    public HeapFile(File f, TupleDesc td) {
        // some code goes here
    	myFile = f;
    	myTd = td;
    }

    /**
     * Returns the File backing this HeapFile on disk.
     * 
     * @return the File backing this HeapFile on disk.
     */
    public File getFile() {
        // some code goes here
        return myFile;
    }

    /**
     * Returns an ID uniquely identifying this HeapFile. Implementation note:
     * you will need to generate this tableid somewhere ensure that each
     * HeapFile has a "unique id," and that you always return the same value for
     * a particular HeapFile. We suggest hashing the absolute file name of the
     * file underlying the heapfile, i.e. f.getAbsoluteFile().hashCode().
     * 
     * @return an ID uniquely identifying this HeapFile.
     */
    public int getId() {
        // some code goes here
    	return myFile.getAbsolutePath().hashCode();
        //throw new UnsupportedOperationException("implement this");
    }

    /**
     * Returns the TupleDesc of the table stored in this DbFile.
     * 
     * @return TupleDesc of this DbFile.
     */
    public TupleDesc getTupleDesc() {
        // some code goes here
    	return myTd;
        // throw new UnsupportedOperationException("implement this");
    }

    // see DbFile.java for javadocs
    public Page readPage(PageId pid) {
        // some code goes here
    	RandomAccessFile rFile = null;
    	try{
    		rFile = new RandomAccessFile(getFile(), "r");
    	}
    	catch(IOException err){
    		err.printStackTrace();
    	}
    	try {
            byte[] bl = new byte[BufferPool.PAGE_SIZE];
            int cur;
            cur=BufferPool.PAGE_SIZE * pid.pageNumber();
            rFile.seek(cur);
            rFile.read(bl, 0, BufferPool.PAGE_SIZE);
            rFile.close();
            HeapPageId heapPageId=new HeapPageId(pid.getTableId(), pid.pageNumber());
            HeapPage heapPage=new HeapPage(heapPageId , bl);
            return heapPage;
        } catch(IOException e) {
		throw new IllegalArgumentException();
    }

    // see DbFile.java for javadocs
    public void writePage(Page page) throws IOException {
        // some code goes here
	RandomAccessFile newraf = new RandomAccessFile(this.myFile, "rw");
	newraf.seek(BufferPool.PAGE_SIZE * page.getId().pageNumber());
	newraf.write(page.getPageData(), 0, BufferPool.PAGE_SIZE);
	newraf.close();
        // not necessary for proj1
    }

    /**
     * Returns the number of pages in this HeapFile.
     */
    public int numPages() {
        // some code goes here
    	return (int) (myFile.length()/BufferPool.PAGE_SIZE);
    }

    private HeapPage getFreePage(TransactionId tid) throws TransactionAbortedException, DbException
    {
	for(int i=0; i<this.numPages(); i++)
	{
	    PageId pid = new HeapPageId(this.getId(), i);
	    HeapPage page = (HeapPage) Database.getBufferPool().getPage(tid, pid, Permissions.READ_WRITE);
       	    
	    if (hpage.getNumEmptySlots() > 0)
		return hpage;	
	}
	return null;
    }
    // see DbFile.java for javadocs
    public ArrayList<Page> insertTuple(TransactionId tid, Tuple t)
            throws DbException, IOException, TransactionAbortedException {
        // some code goes here
        HeapPage page = getFreePage(tid);
        if (page != null) 
	{
                 page.insertTuple(t);
                 return new ArrayList<Page> (Arrays.asList(page));
	}
	
	HeapPage newHeapPage = new HeapPage(new HeapPageId(this.getId(),this.numPages()), HeapPage.createEmptyPageData());
        newHeapPage.insertTuple(t);
 
        RandomAccessFile newraf = new RandomAccessFile(this.myFile, "rw");
        newraf.seek(BufferPool.PAGE_SIZE * this.numPages());
        newraf.write(newHeapPage.getPageData(), 0, BufferPool.PAGE_SIZE);
        newraf.close();
 
        return new ArrayList<Page> (Arrays.asList(newHeapPage));
        // not necessary for proj1
    }

    // see DbFile.java for javadocs
    public Page deleteTuple(TransactionId tid, Tuple t) throws DbException,
            TransactionAbortedException {
        // some code goes here
        PageId pid = t.getRecordId().getPageId();
        HeapPage page = (HeapPage) Database.getBufferPool().getPage(tid, pid,    Permissions.READ_WRITE);
        page.deleteTuple(t);
        return new ArrayList<Page>(Arrays.asList(page));        
        // not necessary for proj1
    }

    // see DbFile.java for javadocs
    public DbFileIterator iterator(TransactionId tid) {
        // some code goes here
        return null;
    }

}

