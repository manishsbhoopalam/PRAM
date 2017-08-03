# SimpleDB

This is a basic Database Management System implemented using Java. The contributors are:

1. **Priyam Kumar**
2. **Ram Aditya S**
3. **Manish Bhoopalam**
4. **Rithesh K**

Their contributions:

**Rithesh:-**

1) Tuple: It was a pretty simple class to implement, and I didn't spend much time on it. The *iterator()* method was a bit hard to crack 
in the beginning, and I had to refer the Internet before I cracked it down.

2) TupleDesc: THe code was similar in the basic idea to the Tuple class. I created an ArrayList for the TDItems. There was a minor error 
in the *equals()* method, but then I resolved it soon, so this class too didn't take much time.

3) RecordId: This was a small class to code, and so I had almost completed it but for the *hashCode()* method. I had to do some 
alterations (like adding 17*31, the reason for which I'm clueless), and finally got it working.

4) Catalog: This class took some time for me to complete. I had very less knowledge of how to code the class. I took the help of the 
Internet (and a few suggestions from my group mates) to finally get the hang of it. Then it was easier, until the *tableIdIterator()*
method, which took a little more time amd effort.

5) HeapFile: I have modified the existing file by implementing the *insertTuple()* and *deleteTuple()* methods. The  methods were fairly 
simple. In the *insertTuple()*, I just had to write the contents in the tuple to the page, or create a new one if the page doesn't exist. 
In the *deleteTuple()*, I had to use the *deleteTuple()* from the HeapPage to remove the tuple. I have also implemented the *writePage()* 
method, which was fairly simple.

6) HeapPage: Like HeapFile, I modified the class by implementing the same methods - *insertTuple()* and *deleteTuple()*. The 
*deleteTuple()* was the easier of the two, having to reinitialising the tuple back to null and marking the slot empty. For the 
*InsertTuple()*, I had to check if the existing page was full, for which I took the help of the length of the data. Then it was 
as simple as just creating a new tuple. I have also implemented the *markDirty()*, *isDirty()*, *isSlotUsed()* and *markSlotUsed()*,
which were very simple.

7)BufferPool: The *insertTuple()* and *deleteTuple()* methods was small, and it didn't take much time to implement them.
