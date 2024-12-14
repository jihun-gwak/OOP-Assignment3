package implementations;

public class BSTreeNode<E extends Comparable<? super E>> {
	private E element;
	private BSTreeNode<E> left;
	private BSTreeNode<E> right;
	
	public E getElement() {
		return element;
	}

	public void setElement(E element) {
		this.element = element;
	}

	public BSTreeNode<E> getLeft() {
		return left;
	}

	public void setLeft(BSTreeNode<E> left) {
		this.left = left;
	}

	public BSTreeNode<E> getRight() {
		return right;
	}

	public void setRight(BSTreeNode<E> right) {
		this.right = right;
	}

	public BSTreeNode(E element) {
		this.element = element;
	}
}
