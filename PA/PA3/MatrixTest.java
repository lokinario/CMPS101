/*
* Robert Loquinario
* rloquina
* pa 3
* MatrixTest.java
*/

public class MatrixTest {
    public static void main(String[] args) {
        int size = 10;
        Matrix A = new Matrix(size);
        Matrix B = new Matrix(size);

        for(int i = 1; i<=size; i++)
        {
            for(int j = 1; j<=size; j++)
            {
                A.changeEntry(i,j,size-j);
                B.changeEntry(i,j,j);
            }
        }

        System.out.println("A has "+ A.getNNZ() +" non-zero entries:");
        System.out.println(A);
        System.out.println("B has "+ B.getNNZ() +" non-zero entries:");
        System. out.println(B);
        System. out.println("(1.5)*A =");
        System. out.println(A.scalarMult(1.5));
        System. out.println("A+B =");
        System. out.println(A.add(B));
        System.out.println("A+A =");
        System.out.println(A.add(A));
        System.out.println("B-A =");
        System.out.println(B.sub(A));
        System.out.println("A-A =");
        System.out.println(A.sub(A));
        System.out.println("Transpose(A) =");
        System. out.println(A.transpose());
        System. out.println("A*B =");
        System. out.println(A.mult(B));
        System. out.println("B*B =");
        System. out.println(B.mult(B));

        System. out.println("C is a copy of A");
        Matrix C = A.copy();
        System.out.println("C has "+ C.getNNZ() +" non-zero entries:");
        System.out.println(C);
        System.out.println("Does C equal to A?");
        System.out.println(A.equals(C));
        System.out.println("Does B equal to A?");
        System.out.println(A.equals(B));
        System.out.println("Does A equal to A?");
        System.out.println(A.equals(A));

        System.out.println("====> Making C zero");
        C.makeZero();
        System.out.println("C has "+ C.getNNZ() +" non-zero entries:");
        System.out.println(C);

        A = new Matrix(10);
        B = new Matrix(10);
        A.changeEntry(1, 1, 4);
        A.changeEntry(1, 2, 2);
        A.changeEntry(1, 3, 0);
        A.changeEntry(2, 1, 2);
        A.changeEntry(3, 1, 0);
        A.changeEntry(2, 2, 2);
        A.changeEntry(3, 3, 0);
        C = A.add(A);
        System.out.println(A.add(A));
        System.out.println("Does it have 4 NNZ ?");
        if (C.getNNZ() != 4 || A.getNNZ() != 4) System.out.println("False");
        else System.out.println("true");
        B.changeEntry(1, 1, -4);
        B.changeEntry(1, 2, 0);
        B.changeEntry(2, 1, 0);
        B.changeEntry(2, 2, -2);
        B.changeEntry(2, 4, 2);
        B.changeEntry(3, 1, 0);
        B.changeEntry(3, 2, 2);
        B.changeEntry(7, 8, 5);
        C = A.add(B);
        System.out.println(A.add(B));
        System.out.println("Does it have 5 NNZ ?");
        if (C.getNNZ() != 5) System.out.println("False");
        else System.out.println("true");
        A = new Matrix(10);
        B = new Matrix(10);
        A.changeEntry(1, 1, -4);
        A.changeEntry(1, 2, -2);
        A.changeEntry(1, 3, 0);
        A.changeEntry(2, 5, 4);
        A.changeEntry(2, 1, -2);
        A.changeEntry(3, 1, 2);
        A.changeEntry(2, 2, -2);
        A.changeEntry(3, 3, 0);
        C = A.sub(A);
        System.out.println(A.sub(A));
        System.out.println("Does it have 0 NNZ or does A have 6 NNZ?");
        if (C.getNNZ() != 0 || A.getNNZ() != 6) System.out.println("False");
        else System.out.println("true");
        B.changeEntry(1, 1, -4);
        B.changeEntry(1, 2, 0);
        B.changeEntry(2, 1, 0);
        B.changeEntry(2, 2, -2);
        B.changeEntry(2, 4, 2);
        B.changeEntry(3, 1, 2);
        B.changeEntry(3, 2, 2);
        B.changeEntry(7, 8, 5);
        C = A.sub(B);
        System.out.println(A.sub(B));
        System.out.println("Does it have 6 NNZ ?");
        if (C.getNNZ() != 6) System.out.println("false");
        else System.out.println("True");

    }
}