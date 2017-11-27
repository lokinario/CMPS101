/*
* Matrix.java
* Robert Loquinario
* rloquina
* pa3
*/

public class Matrix {
	
	private class Entry {
		double value;
		int column;

		Entry(double value, int column) {
			this.value = value;
			this.column = column;
		}

		public boolean equals(Object x){
			boolean equals = false;
			Entry temp;
			if (x instanceof Entry) {
				temp = (Entry) x;
				equals = (this.column == temp.column && this.value == temp.value);
			}
			return equals;
		}

		public String toString() {
			//System.out.println("(" + String.valueOf(column) + ", " + String.valueOf(value) + ")");
			return ("(" + String.valueOf(column) + ", " + String.valueOf(value) + ")");
		}
	}
	private List[] row;
	private int size;
	private int NNZ;
	// Makes a new n x n zero Matrix. pre: n>=1
	Matrix(int n) {
		size = n;
		row = new List[n];
		for(int i = 0;i < n; i++)
			row[i] = new List();
		NNZ = 0;
	}

// Access functions ---------------------------------------------------
	// Returns n, the number of rows and columns of this Matrix 
	int getSize() {
		return size;
	} 

	// Returns the number of non-zero entries in this Matrix
	int getNNZ() {
		return NNZ;
	}

	// overrides Object's equals() method
	public boolean equals(Object x) {
		if(!(x instanceof Matrix)) {
			throw new RuntimeException("ERROR: calling equals() with non-matrix");
		}
		Matrix M;
		boolean equal = false;

		M = (Matrix) x;
		if( M.getSize() == size && M.getNNZ() == NNZ) {
			for(int i = 0; i < size; i++) {
				if(row[i].length() != M.row[i].length()) return false;

				row[i].moveFront();
				M.row[i].moveFront();
				while(row[i].index() != -1) {
					if (!(row[i].get().equals(M.row[i].get()))){
						return false;
					}
					row[i].moveNext();			
					M.row[i].moveNext();
				}
			}
			return true;
		}
		
		return false;
	}


// Manipulation procedures --------------------------------------------
	// sets this Matrix to the zero state
	void makeZero() {
		if(size>1)
			for(int i = 0; i < size ; i++)
				row[i].clear();
		NNZ = 0;
	}

	// returns a new Matrix having the same entries as this Matrix
	Matrix copy() {
		Matrix copy = new Matrix(size);
        for (int i = 0; i < size; i++) {
            for(row[i].moveFront(); row[i].index() != -1; row[i].moveNext()){
                Entry newEntry = new Entry(((Entry) row[i].get()).value, ((Entry) row[i].get()).column);
                copy.row[i].append(newEntry);
                copy.NNZ++;
                if (copy.NNZ == this.NNZ)
                    return copy;
            }
        }
        return copy;
	} 

	// changes ith row, jth column of this Matrix to x
    // pre: 1<=i<=getSize(), 1<=j<=getSize()
	void changeEntry(int i, int j, double x) {
		if (i <= 0 || i > size || j > size || j <= 0 || size == 0) {
            throw new RuntimeException("Error: calling changeEntry() w/ out of bounds indexes");
        } //check if changeEntry should be possible

        double half = j / size; //for faster checking if beginning half or end half
        if (half < 0.5) {// check if column is in first half of row
            for (row[i - 1].moveFront(); row[i - 1].index() != -1; row[i - 1].moveNext()) {
                if (((Entry) row[i - 1].get()).column == j) {
                    if (x == 0) { // if value to be inserted is 0
                        NNZ--;
                        boolean deleted = false;
                        if (row[i - 1].front() == row[i - 1].get()) {
                            (row[i - 1]).deleteFront();
                            deleted = true;
                        }//if front then deletes it
                         else if (row[i - 1].back() == row[i - 1].get()) {
                            (row[i - 1]).deleteBack();
                            deleted = true;
                        }//if back then deletes it
                         else if (!deleted) {
                            (row[i - 1]).delete();
                        }//else in middle just delete
                        return;
                    }
                    ((Entry) row[i - 1].get()).value = x;

                    return; //if x != 0 then just change value
                } else if (((Entry) row[i - 1].get()).column > j) {
                    if (x != 0) {
                        NNZ++;
                        Entry newEntry = new Entry(x, j);
                        if((row[i-1].get()) == (row[i-1].front())) {
                        	row[i-1].prepend(newEntry);
                        } else 
                        	row[i - 1].insertBefore(newEntry);
                    }
                    return;
                }
            }
            if (x != 0) {
                NNZ++;
                Entry newEntry = new Entry(x, j);
                row[i - 1].append(newEntry);
            }
            return;
        } else { //checks end half 
            for (row[i - 1].moveBack(); row[i - 1].index() != -1; row[i - 1].movePrev()) {
                if (((Entry) row[i - 1].get()).column == j) {
                    if (x == 0) {
                        NNZ--;
                        boolean deleted = false;
                        if (row[i - 1].front() == row[i - 1].get()) {
                            (row[i - 1]).deleteFront();
                            deleted = true;
                        }
                        else if (row[i - 1].back() == row[i - 1].get()) {
                            (row[i - 1]).deleteBack();
                            deleted = true;
                        }
                        else if (!deleted) {
                            (row[i - 1]).delete();
                        }
                        return;
                    }
                    ((Entry) row[i - 1].get()).value = x;
                    return;
                } else if (((Entry) row[i - 1].get()).column < j) {
                    if (x != 0) {
                        NNZ++;
                        Entry newEntry = new Entry(x, j);

                        if ((row[i - 1].get()) == (row[i - 1].back())) {
                            row[i - 1].append(newEntry);
                        } else
                            row[i - 1].insertAfter(newEntry);
                        
                    }
                    return;
                }
            }
            if (x != 0) {
                NNZ++;
                Entry newEntry = new Entry(x, j);
                row[i - 1].prepend(newEntry);
            }
            return;
        }
	}
    
    // returns a new Matrix that is the scalar product of this Matrix with x 
	Matrix scalarMult(double x) {
		if(this.getSize() == 0) {
			throw new RuntimeException("ERROR: Calling scalarMult() on matrices of different sizes");
		}
		Matrix temp = new Matrix(getSize());
		for(int i = 0; i < getSize() ; i++) {
			for(row[i].moveFront(); row[i].index() != -1; row[i].moveNext()) {
				Entry newEntry = (Entry) row[i].get();
                temp.changeEntry(i + 1, newEntry.column, newEntry.value * x);
				if(temp.NNZ == NNZ) return temp;
			}
		}
		return temp;
	}
	
	// returns a new Matrix that is the sum of this Matrix with M
    // pre: getSize()==M.getSize()
	Matrix add(Matrix M) {
		if(size != M.getSize()){
			throw new RuntimeException("ERROR: Calling add() on matrices of different sizes");
		}
		if(M == this) return this.copy().scalarMult(2.0);
		
		Matrix addM = new Matrix(getSize());
		for(int i = 0; i < getSize(); i++){
			List A = new List();
			row[i].moveFront();
			M.row[i].moveFront();
			while(row[i].index() >= 0|| M.row[i].index() >= 0) {
				if(row[i].index() >= 0 && M.row[i].index() >= 0) {
					Entry a = (Entry) row[i].get();
					Entry b = (Entry) M.row[i].get();
					if(a.column > b.column) {
						A.append(new Entry(b.value, b.column));
						addM.NNZ++;
						M.row[i].moveNext();
					} else if( a.column < b.column) {
						A.append(new Entry(a.value, a.column));
						addM.NNZ++;
						row[i].moveNext();
					}else if(a.column == b.column) {
						if(a.value + b.value != 0) {
							A.append(new Entry(a.value + b.value, a.column));
							addM.NNZ++;
						}
						row[i].moveNext();
						M.row[i].moveNext();
					}
				}else if(row[i].index() >= 0) {
					Entry a = (Entry) row[i].get();
					A.append(new Entry(a.value, a.column));
					addM.NNZ++;
					row[i].moveNext();
				} else {
					Entry b = (Entry) M.row[i].get();
					A.append(new Entry((b.value), b.column));
					addM.NNZ++;
					M.row[i].moveNext();
				}
			}
			addM.row[i] = A;
		}
		return addM;
	}
    
    // returns a new Matrix that is the difference of this Matrix with M
    // pre: getSize()==M.getSize()
	Matrix sub(Matrix M) {
		if(size != M.getSize()){
			throw new RuntimeException("ERROR: Calling sub() on matrices of different sizes");
		}
		if(M == this) return new Matrix(getSize());

		Matrix subM = new Matrix(getSize());
		for(int i = 0; i < getSize(); i++){
			List A = new List();
			row[i].moveFront();
			M.row[i].moveFront();
			while(row[i].index() >= 0|| M.row[i].index() >= 0) {
				if(row[i].index() >= 0 && M.row[i].index() >= 0) {
					Entry a = (Entry) row[i].get();
					Entry b = (Entry) M.row[i].get();
					if(a.column > b.column) {
						A.append(new Entry(-b.value, b.column));
						subM.NNZ++;
						M.row[i].moveNext();
					}else if(a.column < b.column){
						A.append(new Entry(a.value, a.column));
						subM.NNZ++;
						row[i].moveNext();
					}else if(a.column == b.column) {
						if((a.value - b.value!= 0)) {
							A.append(new Entry((a.value - b.value), a.column));
							subM.NNZ++;
						}
						row[i].moveNext();
						M.row[i].moveNext();
					}
				}else if(row[i].index() >= 0) {
					Entry a = (Entry) row[i].get();
					A.append(new Entry(a.value, a.column));
					subM.NNZ++;
					row[i].moveNext();
				} else {
					Entry b = (Entry) M.row[i].get();
					A.append(new Entry((-b.value), b.column));
					subM.NNZ++;
					M.row[i].moveNext();
				}
			}
			subM.row[i] = A;
		}

		return subM;
	}
	
	// returns a new Matrix that is the transpose of this Matrix 
	Matrix transpose() {
		Matrix M = new Matrix(size);
		for(int i = 0; i < size ; i++) {
			for(row[i].moveFront(); row[i].index() != -1; row[i].moveNext()) {
				Entry cell = (Entry) row[i].get();
				int col = cell.column;
				M.changeEntry(col, i+1, cell.value);
			}
		}
		return M;
	}
	
	// returns a new Matrix that is the product of this Matrix with M 
	// pre: getSize()==M.getSize()
	Matrix mult(Matrix M) {
		if(M.getSize() != getSize()) {
			throw new RuntimeException("ERROR: Calling mult() on matrices of different sizes");
		}
		Matrix temp = new Matrix(size);
		Matrix A = M.transpose();
		for(int i = 0; i < getSize() ; i++) {
			if(row[i].length() == 0 ) continue;
			for(int j = 0 ; j < getSize(); j++) {
				if(A.row[j].length() == 0) continue;
				temp.changeEntry(i +1, j +1, dot(row[i],A.row[j]));
			}
		}
		return temp;
	}

	private static double dot(List A, List B) {
		double product = 0.0;
		for(A.moveFront(); A.index() != -1; A.moveNext()){
			Entry a = (Entry) A.get();
			for(B.moveFront(); B.index() != -1; B.moveNext()){
				Entry b = (Entry) B.get();
				if(a.column == b.column) {
					product += a.value * b.value;
					break;
				}
			}
		}
		return product;
	}
	
// Other functions ----------------------------------------------------
	// overrides Object's toString() method
	public String toString() {
		String str = "";
		for(int j = 0; j < size ; j++) {
			if(row[j].length() == 0){
				continue;
			}
			str = str + (j + 1) + ": " + row[j].toString() + "\n";
		}
		return str;
	}
}



