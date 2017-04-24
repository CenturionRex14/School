import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * An indexed unsorted list that uses double linked nodes.
 * @author Nathan King
 *
 * @param <T>
 */
public class IUDoubleLinkedList<T> implements IndexedUnsortedList<T> {

	private LinearNode<T> head, tail;
	private int size, modCount;

	public IUDoubleLinkedList() {
		head = tail = null;
		size = modCount = 0;
	}

	@Override
	public void addToFront(T element) {
		LinearNode<T> newNode = new LinearNode<T>(element);
		if(isEmpty()){
			head = tail = newNode;
		}else{
			newNode.setNext(head);
			head.setPrevious(newNode);
			head = newNode;
		}
		size++;
		modCount++;
	}

	@Override
	public void addToRear(T element) {
		LinearNode<T> newNode = new LinearNode<T>(element);
		if(isEmpty()){
			head = tail = newNode;
		}else{
			newNode.setPrevious(tail);
			tail.setNext(newNode);
			tail = newNode;
		}
		size++;
		modCount++;
	}

	@Override
	public void add(T element) {
		addToRear(element);
	}

	@Override
	public void addAfter(T element, T target) {
		LinearNode<T> current = head;
		LinearNode<T> newNode = new LinearNode<T>(element);
		while(current != null && !current.getElement().equals(target)){
			current = current.getNext();
		} //current holds target element (?)
		if(current == null) {
			throw new NoSuchElementException();
		}else if(current == head){ //target is first element
			newNode.setPrevious(head);
			newNode.setNext(head.getNext());
			if(head.getNext() != null){
				head.getNext().setPrevious(newNode);
			}else{
				tail = newNode;
			}
			head.setNext(newNode);
			modCount++;
			size++;
		}else if(current == tail){ //target is last element
			addToRear(element);
		}else{ //target somewhere in the middle
			newNode.setPrevious(current);
			newNode.setNext(current.getNext());
			current.getNext().setPrevious(newNode);
			current.setNext(newNode);
			modCount++;
			size++;
		}
	}

	@Override
	public void add(int index, T element) {
		if(index < 0 || index > size) {
			throw new IndexOutOfBoundsException();
		}
		LinearNode<T> current = head;
		for(int i = 0; i < index; i++){
			current = current.getNext();
		}
		// at index insert before
		if(index == 0){
			addToFront(element);
		}else if(index == size){
			add(element);
		}else{
			LinearNode<T> newNode = new LinearNode<T>(element);
			newNode.setNext(current);
			newNode.setPrevious(current.getPrevious());
			current.getPrevious().setNext(newNode);
			current.setPrevious(newNode);
			size++;
			modCount++;
		}
	}

	@Override
	public T removeFirst() {
		if(isEmpty()){
			throw new IllegalStateException();
		}
		T retVal = head.getElement();
		head = head.getNext();
		if(head == null){
			tail = null; 
		}else{
			head.setPrevious(null);
		}
		modCount++;
		size--;
		return retVal;
	}

	@Override
	public T removeLast() {
		if(isEmpty()){
			throw new IllegalStateException();
		}
		T retVal = tail.getElement();
		tail = tail.getPrevious();
		if(tail == null){
			head = null;
		}else{
			tail.setNext(null);
		}
		modCount++;
		size--;
		return retVal;
	}

	@Override
	public T remove(T element) {
		LinearNode<T> current = head;
		while(current != null && !current.getElement().equals(element)){
			current = current.getNext();
		}//current contains element
		if(current == null) {
			throw new NoSuchElementException();
		}
		if(current == head){
			head = head.getNext();
			if(head != null) {
				head.setPrevious(null);
			}
		}else{
			current.getPrevious().setNext(current.getNext());
		}
		if(current == tail) {
			tail = tail.getPrevious();
			if(tail != null){
				tail.setNext(null);
			}
		}else{
			current.getNext().setPrevious(current.getPrevious());
		}
		size--;
		modCount++;
		return current.getElement();
	}

	@Override
	public T remove(int index) {
		if(index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}
		ListIterator<T> lit = listIterator(index);
		T retVal = lit.next();
		lit.remove();
		return retVal;
	}

	@Override
	public void set(int index, T element) {
		if(index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}
		ListIterator<T> lit = listIterator(index);
		lit.next();
		lit.set(element);
	}

	@Override
	public T get(int index) {
		if(index < 0 || index >= size){
			throw new IndexOutOfBoundsException();
		}
		ListIterator<T> lit = listIterator(index);
		return lit.next();
	}

	@Override
	public int indexOf(T element) {
		int index = -1;
		int i = 0;
		ListIterator<T> lit = listIterator();
		while(lit.hasNext() && index < 0){
			if(lit.next().equals(element)){
				index = i;
			}
			i++;
		}
		return index;
	}

	@Override
	public T first() {
		if(isEmpty()){
			throw new IllegalStateException();
		}
		return head.getElement();
	}

	@Override
	public T last() {
		if(isEmpty()){
			throw new IllegalStateException();
		}
		return tail.getElement();
	}

	@Override
	public boolean contains(T target) {
		LinearNode<T> current = head;
		while(current != null){
			if(current.getElement().equals(target)){
				return true;
			}
			current = current.getNext();
		}
		return false;
	}

	@Override
	public boolean isEmpty() {
		return head == null;
	}

	@Override
	public int size() {
		return size;
	}
	
	/**
	 * Prints the contents of the list to the consol
	 */
	public String toString(){
		String retVal = "[";
		ListIterator<T> lit = listIterator();
		while(lit.hasNext()){
			retVal += lit.next().toString() + " ";
		}
		retVal += "]";
		return retVal;
	}

	@Override
	public Iterator<T> iterator() { //Is this suppose to throw an unsupportedOperationException?
		ListIterator<T> iter = new DLLIterator();
		return iter;
	}

	@Override
	public ListIterator<T> listIterator() {
		ListIterator<T> iter = new DLLIterator();
		return iter;
	}

	@Override
	public ListIterator<T> listIterator(int startingIndex) {
		ListIterator<T> iter = new DLLIterator(startingIndex);
		return iter;
	}
	
	/**
	 * Inner class ListIterator that is used in ListIterator methods
	 * @author Nathan King
	 *
	 */
	private class DLLIterator implements ListIterator<T> {

		private LinearNode<T> nextNode, lastReturned;
		private int nextIndex, iterModCount;
		
		/**
		 * alternate contructor for DLLIterator
		 */
		public DLLIterator() {
			this(0);
		}
		/**
		 * Contructor for DLLIterator
		 * @param startingIndex
		 */
		public DLLIterator(int startingIndex) {
			if(startingIndex < 0 || startingIndex > size) {
				throw new IndexOutOfBoundsException();
			}
			nextNode = head;
			nextIndex = 0;
			iterModCount = modCount;
			lastReturned = null;
			for(int i = 0; i < startingIndex; i++) {
				nextNode = nextNode.getNext();
				nextIndex++;
			}
		}

		@Override
		public void add(T e) {
			if(iterModCount != modCount) {
				throw new ConcurrentModificationException();
			}
			LinearNode<T> newNode = new LinearNode<T>(e);
			if(isEmpty()){
				head = tail = newNode;
			}else if(nextNode == head){ //new head
				newNode.setNext(head);
				head.setPrevious(newNode);
				head = newNode;
			}else if(nextNode == null){ // new tail
				tail.setNext(newNode);
				newNode.setPrevious(tail);
				tail = newNode;
			}else{ //somewhere in the middle
				newNode.setNext(nextNode);
				newNode.setPrevious(nextNode.getPrevious());
				nextNode.getPrevious().setNext(newNode);
				nextNode.setPrevious(newNode);
			}
			modCount++;
			iterModCount++;
			size++;
			nextIndex++;
			lastReturned = null; //set lastReturned to null!
		}

		@Override
		public boolean hasNext() {
			if(iterModCount != modCount) {
				throw new ConcurrentModificationException();
			}
			return (nextNode != null);
		}

		@Override
		public boolean hasPrevious() {
			if(iterModCount != modCount) {
				throw new ConcurrentModificationException();
			}
			return (nextNode != head);
		}

		@Override
		public T next() {
			if(iterModCount != modCount) {
				throw new ConcurrentModificationException();
			}
			if(!hasNext()){
				throw new NoSuchElementException();
			}
			T retVal = nextNode.getElement();
			lastReturned = nextNode;
			nextNode = nextNode.getNext();
			nextIndex++;
			return retVal;
		}

		@Override
		public int nextIndex() {
			if(iterModCount != modCount) {
				throw new ConcurrentModificationException();
			}
			return nextIndex;
		}

		@Override
		public T previous() {
			if(iterModCount != modCount) {
				throw new ConcurrentModificationException();
			}
			if(!hasPrevious()){
				throw new NoSuchElementException();
			}
			if(nextNode == null){
				nextNode = tail;
			}else{
				nextNode = nextNode.getPrevious();
			}
			nextIndex--;
			lastReturned = nextNode;
			return nextNode.getElement();
		}

		@Override
		public int previousIndex() {
			if(iterModCount != modCount) {
				throw new ConcurrentModificationException();
			}
			return nextIndex - 1;
		}

		@Override
		public void remove() {
			if(iterModCount != modCount) {
				throw new ConcurrentModificationException();
			}
			if(lastReturned == null) {
				throw new IllegalStateException();
			}
			//skips next linkage past node 
			if(lastReturned == head){
				head = head.getNext();
				if(head != null){
					head.setPrevious(null);
				}
			}else{
				lastReturned.getPrevious().setNext(lastReturned.getNext());
			}
			//skips previous linkage past nade
			if(lastReturned == tail) {
				tail = tail.getPrevious();
				if(tail != null){
					tail.setNext(null);
				}
			}else{
				lastReturned.getNext().setPrevious(lastReturned.getPrevious());
			}
			//if last move was next, nextIndex needs to decremented
			//else, nextNode needs to be updated (incremented)
			if(nextNode != lastReturned) { //last move was next()
				nextIndex--;
			}else{
				nextNode = nextNode.getNext();
			}
			modCount++;
			iterModCount++;
			size--;
			lastReturned = null;
		}

		@Override
		public void set(T e) {
			if(iterModCount != modCount) {
				throw new ConcurrentModificationException();
			}
			if(lastReturned == null) {
				throw new IllegalStateException();
			}
			lastReturned.setElement(e);
			modCount++;
			iterModCount++;
		}

	}

}
