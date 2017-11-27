/*
Lex.java
Main program that exports List.java
Robert Loquinario
rloquina
PA1
*/
import java.io.*;
import java.util.*;
public class Lex {
	public static void main(String[] args) throws IOException {
		if (args.length != 2) {
			System.err.println("You need to have 2 console arguments.");
			System.exit(1);
		}
			String[] fileArray;

			List L = new List();
			Scanner inputFile = new Scanner(new File(args[0]));
			PrintWriter output = new PrintWriter(args[1]);
			//gets how many lines
			int lines = 0;
        	while (inputFile.hasNextLine()) {
        		lines++;
        		inputFile.nextLine();
        	}
        	//makes array
        	fileArray = new String[lines];
        	//have to start from the start of the file
        	inputFile = new Scanner(new File(args[0]));
        	//insert the file 1 by 1 into array
        	for(int i = 0; i < lines;i++){
        		fileArray[i] = inputFile.nextLine();
        	}
			
			L = arrangeList(fileArray, L);
			
			//writes to output by indicies
			L.moveFront();
			output.print(fileArray[L.get()]);
			L.moveNext();			
			for(int i = 1;i < lines; i++) {
				output.println();
				output.print(fileArray[L.get()]);
				L.moveNext();
			}
			inputFile.close();
			output.close();
		
	}


	//arranges the list alphabetically by comparing 
	//the file to index numbers
	public static List arrangeList(String[] file, List A){
		A.append(0);
			for(int i = 1; i <file.length; i++) {
				A.moveFront();
					if(file[i].compareTo(file[A.get()]) <=0 ) {
						A.prepend(i);
						continue;
					}
					A.moveBack();
					if(file[i].compareTo(file[A.get()]) >=0 ) {
						A.append(i);
						continue;
					}

					while(file[i].compareTo(file[A.get()]) < 0){
						A.movePrev();
					}
			
				A.insertAfter(i);
			}
			return A;
	}


}
