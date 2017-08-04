# SimpleDB

This is a basic Database Management System implemented using Java. The contributors are:
```
1. Priyam Kumar
2. Ram Aditya S
3. Manish Bhoopalam
4. Rithesh K
```

Their contributions:

## Rithesh

- Tuple: 
It was a pretty simple class to implement, and I didn't spend much time on it. The *iterator()* method was a bit hard to crack  in the beginning, and I had to refer the Internet before I cracked it down.

- TupleDesc: 
The code was similar in the basic idea to the Tuple class. I created an ArrayList for the TDItems. There was a minor error in the *equals()* method, but then I resolved it soon, so this class too didn't take much time.

- RecordId: 
This was a small class to code, and so I had almost completed it but for the *hashCode()* method. I had to do some 
alterations (like adding 17*31, the reason for which I'm clueless), and finally got it working.

- Catalog: 
This class took some time for me to complete. I had very less knowledge of how to code the class. I took the help of the Internet (and a few suggestions from my group mates) to finally get the hang of it. Then it was easier, until the *tableIdIterator()* method, which took a little more time amd effort.

- HeapFile: 
I have modified the existing file by implementing the *insertTuple()* and *deleteTuple()* methods. The  methods were fairly 
simple. In the *insertTuple()*, I just had to write the contents in the tuple to the page, or create a new one if the page doesn't exist. In the *deleteTuple()*, I had to use the *deleteTuple()* from the HeapPage to remove the tuple. I have also implemented the *writePage()* method, which was fairly simple.

- HeapPage: 
Like HeapFile, I modified the class by implementing the same methods - *insertTuple()* and *deleteTuple()*. The  *deleteTuple()* was the easier of the two, having to reinitialising the tuple back to null and marking the slot empty. For the  *InsertTuple()*, I had to check if the existing page was full, for which I took the help of the length of the data. Then it was  as simple as just creating a new tuple. I have also implemented the *markDirty()*, *isDirty()*, *isSlotUsed()* and *markSlotUsed()*, which were very simple.

- BufferPool: 
The *insertTuple()* and *deleteTuple()* methods was small, and it didn't take much time to implement them.

## Ram Aditya .S.

The files assigned to me were :
Catalog,HeapFile, Aggregate, IntegerAggregator, Filter, Predicate, JoinPredicate.

- Catalog: 
It was quite a simple file to work on. Rithesh extended his help for some of the issues.

- HeapFile: 
I spent a considerable amount of time and effort on this file.The readPage() method took the longest to implement as I kept running into errors.The problem arose due to the wrong implementation of try-catch block. Also, despite all our efforts we were unable to implement the iterator() method till the end.

- Aggregate, IntegerAggegator:
Aggregate, IntegerAggregator and StringAggregator implement capabilities to run aggregate functions like Count, Sum, Max, etc. StringAggregator was implemented by Priyam. Aggregate was fairly simple and I only had to figure out the way to pass all tuples to either IntegerAggregator or StringAggregator. Also it had to be ensured that it would be compatible with StringAggregator. 

- IntegerAggregator 
This was trickier. I repeatedly got caught up in silly nuanced errors. Using HashMaps made the process simpler. With some help for the basic algorithm of the code,I used 2 HashMaps.One to keep count of the number of aggregate values for each unique value in group-by field and the other to store the aggregates for each group-by field. I learnt from the internet about the keyset() function of HashMaps that I could to use to get all the Keys of the Map. Then finally an arraylist containing the output tuples is created. The TupleIterator assigned to this is finally returned to Aggregate. It was done in about 2-3 days.

- Filter, Predicate, JoinPredicate:
The Filter,Predicate and JoinPredicate was fairly simple. It consisted of simple methods. However  had issues with the fetchnext method. A lot of communication took place with the mentors, seniors and team-mates to clarify this issue. In the end it all came together and successfully ran all the tests. The files took about 2 days but stretched due to the issue with the fetchnext method.

## Manish Bhoopalam

I implemented the following files: Bufferpool, SeqScan, Insert, Delete
- Bufferpool.java: 
It was a small file to implement.Although I had to revisit the concept of HashMap.It took me around half a day.
- SeqScan: 
This consisted of several methods to be implemented.Most of them were simple and were done very easily.The getTupleDesc() took a little longer time.
-  Insert.java: 
It was pretty easy for me to modify the class, and I didn't take much time and effort   to complete all the methods. Within 2 days I had almost finished this class, when I found the fetchNext() method failing a test.After some clarification I had it corrected.
- Delete.java: 
The class was almost the same as the Insert class, and I finished it quite easily. It didn't pose any problems and all the test cases ran very well.

## Priyam Kumar

Files Assigned: HeapPageId, HeapPage, PageEviction(BufferPool), Join and StringAggregator. 

- HeapPageId:
Implementing HeapPageId involved creating a Page Id structure for a specific table and was a fairly easy task. Most of the methods were pretty direct and involved returning parameters passed in. It didn’t take me much time to implement it. This was my first file i had implemented in simpledb which was a good starting point for me to get around the dbms structure.

- HeapPage:
In this task I was supposed to implement HeapPage. This took quite some time and reasearch to do it. It involved method like getNumTuples to retrieve the number of tuples on the page and getHeaderSize which were comparatively easy to implement. Other methods however took a lot of time and was confusing going around dbms. However at this point i started getting accustomed to java and the moving around the dbms made me more familiar for the end project.

- PageEviction:
Page Eviction involved implementing the flushPage() to implement the page eviction in BufferPool.java. It requred me to use the page interface and extract the value mapped for the Page Id. Using this i implemented the remaining code of flushing the page to the disk.

- Join
The join required us to implement the realtional join operation. The task took a lot of time for me to implement. Starting few methods were direct while other took a lot of time. There were get methods that required to return Field name. I used getTupleDesc() to return the tupleDesc for the child(iterator). Then i used getfieldname method to extract the field name. Though it sounds direct right now, it took time after a good break after mid project. The main challenege was the implementation of fetchNext() to return the next tuple generated by the join. I first checked for null conditions and once that was done used the filter to make sure the tuples satify the predicate. I extracted total no of fields from left and right tuple. I used this data to create an offset for a new tuple incorporating the new join/change.

- StringAggregator
I implemented StringAggregator which is responsible to compute aggregate over a set of StringFields. I used a hashmap to simplify this process, storing the field and integer mapped to it. On a whole it was challenging and the key was to use hashmap to simlify the process.

## From the team:
* Collaboration happened over github, phone, mail and we had to make multiple visits to  the team mates’ houses for discussion. It was a very enriching experience. Since this project has implemented a vast array of concepts in Java, we had hands-on experience working with many new areas in Java. We learnt a lot from our regular slack sessions about Database Management Systems and Cloud Computing. We took further interest to research more about topics like cloud computing, SQL Injection and Java Generics and HashMaps, Joins in SQL. We learnt many SQL commands from the w3schools website. Furthermore, we gained more experience at the git-bash terminal, unix terminal, using slack, phpmyadmin , Junit.
* The idea of completing the project as a group has enabled to make new friendships and team-efforts. We have got great contacts with several seniors whose support we will rely on for the subsequent projects. Overall, we are very glad to have taken up this Summer Project and have taken home enriching knowledge, skills and experiences! We are grateful to IEEE and our Mentors Salman and Hrishikesh who were great teachers and patient in clearing doubts.
