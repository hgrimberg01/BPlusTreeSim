BPlusTreeSim
============

B Plus Tree Simulation with Simple Paging



Usage
---------------

You must have Apache ANT installed as it the Java equivalent of make.


ant clean -> same as make clean
ant build -> builds all classes
ant BPlusTreeSim -> executes the simulator, ingesting input.txt and outputting a output.txt in the same directory
ant APlusTreeTest -> runs unit tests on main B+ tree implementation

There are other tests for Paging, Nodes, etc. but those are not nearly as relevant as the above.

To run
1. Make Sure input.txt exists in the directory.
2. ant clean
3. ant build
4. ant BPlusTreeSim




Licensed under the MIT License
Copyright (c) 2013 Howard Grimberg and Qi Chen
