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

7) BufferPool: The *insertTuple()* and *deleteTuple()* methods was small, and it didn't take much time to implement them.

**Ram Aditya .S.**

The files assigned to me were :
Catalog,HeapFile, Aggregate, IntegerAggregator, Filter, Predicate, JoinPredicate.

Catalog:
It was  quite a simple file to work on. Rithesh extended his help for some of the issues.

HeapFile:
I spent a considerable amount of time and effort on this file.The readPage() method took the longest to implement as I kept running into errors.The problem arose due to the wrong implementation of try-catch block. Also, despite all our efforts we were unable to implement the iterator() method till the end.

Aggregate, IntegerAggegator:
Aggregate, IntegerAggregator and StringAggregator implement capabilities to run aggregate functions like Count, Sum, Max, etc. StringAggregator was implemented by Priyam. Aggregate was fairly simple and I only had to figure out the way to pass all tuples to either IntegerAggregator or StringAggregator. Also it had to be ensured that it would be compatible with StringAggregator. 

IntegerAggregator was trickier. I repeatedly got caught up in silly nuanced errors. Using HashMaps made the process simpler. With some help for the basic algorithm of the code,I used 2 HashMaps.One to keep count of the number of aggregate values for each unique value in group-by field and the other to store the aggregates for each group-by field. I learnt from the internet about the keyset() function of HashMaps that I could to use to get all the Keys of the Map. Then finally an arraylist containing the output tuples is created. The TupleIterator assigned to this is finally returned to Aggregate. It was done in about 2-3 days.

Filter, Predicate, JoinPredicate:
The Filter,Predicate and JoinPredicate was fairly simple. It consisted of simple methods. However  had issues with the fetchnext method. A lot of communication took place with the mentors, seniors and team-mates to clarify this issue. In the end it all came together and successfully ran all the tests. The files took about 2 days but stretched due to the issue with the fetchnext method.




**Manish Bhoopalam**
I implemented the following files: Bufferpool, SeqScan, Insert, Delete
1) Bufferpool.java: It was a small file to implement.Although I had to revisit the concept of HashMap.It took me around half a day.
2) SeqScan: This consisted of several methods to be implemented.Most of them were simple and were done very easily.The getTupleDesc() took a little longer time.
3)  Insert.java: It was pretty easy for me to modify the class, and I didn't take much time and effort   to complete all the methods. Within 2 days I had almost finished this class, when I found the fetchNext() method failing a test.After some clarification I had it corrected.
4} Delete.java: The class was almost the same as the Insert class, and I finished it quite easily. It didn't pose any problems and all the test cases ran very well.

