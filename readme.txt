CS4551 Multimedia Software Systems
@ Aliasing, Dictionary and Main class edits Author: Abraham Vega

@ Main and Image class Author (and readme format): Elaine Kang
Computer Science Department
California State University, Los Angeles


Task 1: Aliasing
Complete

Task 2: Dictionary Coding
Encoding: Created Dictionary. Unable to encode properly, only encodes single and double letter entries from dictionary.
Decoding: Complete


Compile requirement
======================================
JDK Version 7.0 or above


Compile Instruction on Command Line:
======================================
javac CS4551_Vega.java Aliasing.java Dictionary.java Image.java
or 
javac *.java


Execution Instruction on Command Line:
======================================
java CS4551_Vega 


Using Aliasing, Encoding and Decoding Program
======================================
Input 1 for Aliasing 
- Will create and save files of circles


Input 2 for Encoding and Decoding 
- Will display values in console


Aliasing
======================================
1. Input integer value for radius of circles
2. Input integer value for thickness of circles. Circles will be displayed
3. Input integer value how many times to reduce the image size by (2, 4, 8, or 16). Circles will be displayed

Files will be stored in:
circles.ppm	- Plain circles
noFilter.ppm	- Resize with no filter
filter1.ppm	- Resize utilizing filter 1
filter2.ppm	- Resize utilizing filter 2


Encoding and Decoding 
======================================
1. Input text file name.
	Example: LZW_test4.txt
2. Dictionary is created. Text encoded and then decoded.

MISCALCULATION NOTE: Only single and double digit items in dictionary are encoded. Leading to correct decoding but wonky encoding.