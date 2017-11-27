/*
* Sparse.java
* Robert Loquinario
* rloquina
* PA3
*/

import java.io.*;
import java.util.*;

public class Sparse {
	
	public static void main(String[] args) throws IOException{
		if(args.length != 2) 
			System.err.println( "Sparse needs two arguments");
		
		Scanner in = new Scanner(new File(args[0]));
		PrintWriter out = new PrintWriter(new FileWriter(args[1]));

		int size = in.nextInt();
		Matrix A = new Matrix(size);
		Matrix B = new Matrix(size);
		int nnzA = in.nextInt();
		int nnzB = in.nextInt();

		//while(in.hasNext()){
			for(int i = 0; i < nnzA ; i++) {
				int rowA = in.nextInt();
				int colA = in.nextInt();
				double valA = in.nextDouble();
				A.changeEntry(rowA,colA,valA);
			}
			for(int j = 0 ; j < nnzB ; j++) {
				int rowB = in.nextInt();
				int colB = in.nextInt();
				double valB = in.nextDouble();
				B.changeEntry(rowB,colB,valB);
			}
		//}

		out.println("A has "+ A.getNNZ() +" non-zero entries:");
		out.println(A);
		
		out.println("B has "+ B.getNNZ() +" non-zero entries:");
		out.println(B);
		
		out.println("(1.5)*A =");
		out.println(A.scalarMult(1.5));
		
		out.println("A+B =");
		out.println(A.add(B));
		
		out.println("A+A =");
		out.println(A.add(A));
		
		out.println("B-A =");
		out.println(B.sub(A));
		
		out.println("A-A =");
		out.println(A.sub(A));
		
		out.println("Transpose(A) =");
		out.println(A.transpose());
		
		out.println("A*B =");
		out.println(A.mult(B));
		
		out.println("B*B =");
		out.println(B.mult(B));
		
		in.close();
		out.close();
	}
}