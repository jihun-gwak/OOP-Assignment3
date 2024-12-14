package implementations;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import utilities.Iterator;

public class BSTreeIterator<E> implements Iterator<E> {
	
	private int size;
	private int currentIndex;
	private ArrayList<E> elements;
	
	public BSTreeIterator() {
		size = 0;
		currentIndex = 0;
		elements = new ArrayList<E>();
	}
	
	public void addElement(E element) {
		elements.add(element);
		size++;
	}

	@Override
	public boolean hasNext() {
		return currentIndex < size;
	}

	@Override
	public E next() throws NoSuchElementException {
		E returned = elements.get(currentIndex);
		currentIndex++;
		return returned;
	}

}