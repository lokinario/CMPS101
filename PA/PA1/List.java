/*
List.java
utilized by Lex.java
Robert Loquinario
rloquina
PA1
*/

public class List{
	
	//Node class to hold the numeric value
	private class Node {
		int data;
		Node next;
		Node previous;

		//default node constructor
		Node(int data) {
			this.data = data;
			next = null;
			previous = null;
		}
		//node constructor
		Node(int data, Node nextNode, Node prevNode) {
			this.data = data;
			next = nextNode;
			previous = prevNode;
		}
		//Overwrite Object's toString
		public String toString() {
			return String.valueOf(data);
		}
	}

	private Node front;
	private Node back;
	private Node cursor;
	private int index;
	private int length = 0;


	//List constructor, creates a new empty list.
	List() {
		front = null;
		back = null;
		cursor = null;
		index = -1;
		length = 0;
	}

	//returns the number of elements in this List.
	public int length() {
		return length;
	}

	//if cursor is defined, returns the index of the cursor element
	//returns -1 otherwise
	int index() {
		if (index > length - 1) {
			index = -1;
		}
		return index;
	}

	//returns front element. Pre: length()>0
	int front() throws RuntimeException {
		if ( length <= 0) {
			throw new RuntimeException("List Error: front() called on empty list.");
		} 
		return front.data;
	}
	
	// Returns back element. Pre: length()>0
	int back() throws RuntimeException {
		if ( length <= 0) {
			throw new RuntimeException("List Error: back() called on empty list.");
		} 
		return back.data;
	}
	
	// Returns cursor element. Pre: length()>0, index()>=0
	int get() {
		if (length() == 0){
			return -1;
		} else if (index == - 1){
			return -1;
		} else {
			return cursor.data;
		}

	}
	
	// Returns true if and only if this List and L are the same
 	// integer sequence. The states of the cursors in the two Lists
 	// are not used in determining equality.
	public boolean equals(List L) {
		boolean check = true;

		Node a = this.front;
		Node b = L.front;

		if (this.length == L.length) {
			while( check && a!= null) {
				check = (a.data == b.data);
				a = a.next;
				b = b.next;
			}
			return check;
		} return false;
	}

// Manipulation procedures

	// Resets this List to its original empty state.
	void clear() {
		cursor = front;
		if (cursor != null) {
			while (cursor.next != null){
				Node temp = cursor;
				cursor = cursor.next;
				temp.next = null;
				temp.previous = null;
			}
			cursor = null;
			front = null;
			back = null;
			length = 0;
			index = -1;
		}
	}
	
	// If List is non-empty, places the cursor under the front element,
 	// otherwise does nothing.
	void moveFront() {
		if (length > 0) {
			cursor = front;
			index = 0;
		}
	}
	
	// If List is non-empty, places the cursor under the back element,
	// otherwise does nothing.
	void moveBack() {
		if (length > 0) {
			cursor = back;
			index = length - 1;
		}
	}
	
	// If cursor is defined and not at front, moves cursor one step toward
 	// front of this List, if cursor is defined and at front, cursor becomes
	// undefined, if cursor is undefined does nothing.
	void movePrev() {
		if ( cursor != null && !cursor.equals(front)) {
			cursor = cursor.previous;
			index--;
		} else if (cursor != null && cursor.equals((front))) {
			cursor = null;
			index = -1;
		} else return;
	}

	// If cursor is defined and not at back, moves cursor one step toward
	// back of this List, if cursor is defined and at back, cursor becomes
	// undefined, if cursor is undefined does nothing.
	void moveNext() {
		if (cursor != null && !cursor.equals(back)) {
			cursor = cursor.next;
			index++;
		} else if (cursor != null && cursor.equals(back)) {
			cursor = null;
			index = -1;
		} else return;
	}

	// Insert new element into this List. If List is non-empty,
	// insertion takes place before front element.
	void prepend(int data) {
		if ( length > 0) {
			Node newElement = new Node(data, front, null);
			front.previous = newElement;
			front = newElement;
			length++;
			index++;
		} else {
			Node newElement = new Node(data) ;
			front = newElement;
			back = newElement;
			length++;
			index++;
		}
	}

	// Insert new element into this List. If List is non-empty,
	// insertion takes place after back element.
	void append(int data) {
		if (length > 0) {
			Node newElement = new Node(data, null, back);
			back.next = newElement;
			back = newElement;
			length++;
		} else {
			Node newElement = new Node(data);
			front = newElement;
			back = newElement;
			length++;
		}
	}
	// Insert new element before cursor.
	// Pre: length()>0, index()>=0
	void insertBefore(int data) {
		if (length() <= 0) {
			return;
		} else if (index() < 0) {
			return;
		} 
		Node newElement = new Node(data);
		if (index() == 0) {
			prepend(data);
		} else {
			newElement.next = cursor;
			newElement.previous = cursor.previous;
			cursor.previous.next = newElement;
			cursor.previous = newElement;
			index++;
			length++;
		}
	}
	// Inserts new element after cursor.
	// Pre: length()>0, index()>=0
	void insertAfter(int data) {
		if (length() <= 0) {
			return;
		} else if (index() < 0) {
			return;
		}
		Node newElement = new Node(data);
		if ( index() == length() - 1) {
			append(data);
		} else {
			newElement.previous = cursor;
			newElement.next = cursor.next;
			cursor.next.previous = newElement;
			cursor.next = newElement;
			length++;
		}
	}
	
	// Deletes the front element. Pre: length()>0
	void deleteFront() {
		if(length == 1){
			clear();
		} 
		else{
			if(cursor == front){
				cursor = null;
				index =-1;
			} else if(cursor != null){
				index--;
			}
			front = front.next;
			front.previous = null;
			length--;
		} 
	}

	// Deletes the back element. Pre: length()>0
	void deleteBack() {
		if(length == 1) {
			clear();
		} else if(length > 0) {
			if(cursor == back){
				index =-1;
			}
			back = back.previous;
			if(back != null) {
				back.next = null;
			}
			length--;
		} else {
			back = null;
		}
	}
	
	// Deletes cursor element, making cursor undefined.
	// Pre: length()>0, index()>=0
	void delete() {
		if(cursor == front){
			deleteFront();
		} else if (cursor == back){
			deleteBack();
		} else if (length > 0 && index >= 0) {
			cursor.next.previous = cursor.previous;
			cursor.previous.next = cursor.next;
			cursor = null;
			index = -1;
			length --;
		}
	}

//other methods 
	// Returns a new List representing the same integer sequence as this
 	// List. The cursor in the new list is undefined, regardless of the
 	// state of the cursor in this List. This List is unchanged.
	List copy() {
		List newList = new List();
		if (this.length == 0) {
			return newList;
		}
		Node temp = this.front;
		while(temp.next != null) {
			newList.append(temp.data);
			temp = temp.next;
		}
		newList.append(temp.data);

		return newList;
	}

	// Returns a new List which is the concatenation of
 	// this list followed by L. The cursor in the new List
 	// is undefined, regardless of the states of the cursors
 	// in this List and L. The states of this List and L are
 	// unchanged.

 	List concat(List L) {
 		List newList = new List();
 		Node temp = this.front;
 		while(temp.next != null) {
 			newList.append(temp.data);
 			temp = temp.next;
 		}
 		newList.append(temp.data);

 		temp = L.front;
 		while(temp.next != null) {
 			newList.append(temp.data);
 			temp = temp.next;
 		}
 		newList.append(temp.data);

 		return newList;
 	}
	//Overrides Object's toString method
	public String toString() {
		String str = "";
		for (Node N = front; N != null; N = N.next) {
			if(N == back){
				str+= N.toString();
			}
			else {
				str += N.toString() + " ";
			}
		}
		return str;
	}



}