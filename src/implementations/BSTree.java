package implementations;

import utilities.Iterator;
import utilities.BSTreeADT;

import java.io.Serializable;

/**
 * Implementation of a Binary Search Tree (BST) based on the BSTreeADT contract.
 *
 * @param <E> the type of elements maintained by this BST
 */
public class BSTree<E extends Comparable<E>> implements BSTreeADT<E>, Serializable {
    private static final long serialVersionUID = 5907685409899986079L;
	private BSTreeNode<E> root;
    private int size;

    // Constructor
    public BSTree() {
        this.root = null;
        this.size = 0;
    }

    public boolean add(E element) {
        if (element == null) {
            throw new NullPointerException("Cannot add null element.");
        }
        if (root == null) {
            root = new BSTreeNode<>(element);
            size++;
            return true;
        }
        return addRecursive(root, element);
    }

    private boolean addRecursive(BSTreeNode<E> current, E element) {
        int comparison = element.compareTo(current.getElement());
        if (comparison == 0) {
            return false; // Duplicate element
        } else if (comparison < 0) {
            if (current.getLeft() == null) {
                current.setLeft(new BSTreeNode<>(element));
                size++;
                return true;
            }
            return addRecursive(current.getLeft(), element);
        } else {
            if (current.getRight() == null) {
                current.setRight(new BSTreeNode<>(element));
                size++;
                return true;
            }
            return addRecursive(current.getRight(), element);
        }
    }

    public boolean contains(E element) {
        if (element == null) {
            throw new NullPointerException("Cannot check for null element.");
        }
        return containsRecursive(root, element);
    }

    private boolean containsRecursive(BSTreeNode<E> current, E element) {
        if (current == null) {
            return false;
        }
        int comparison = element.compareTo(current.getElement());
        if (comparison == 0) {
            return true;
        } else if (comparison < 0) {
            return containsRecursive(current.getLeft(), element);
        } else {
            return containsRecursive(current.getRight(), element);
        }
    }

    public BSTreeNode<E> search(E element) {
        if (element == null) {
            throw new NullPointerException("Cannot search for null element.");
        }
        return searchRecursive(root, element);
    }

    private BSTreeNode<E> searchRecursive(BSTreeNode<E> current, E element) {
        if (current == null) {
            return null;
        }
        int comparison = element.compareTo(current.getElement());
        if (comparison == 0) {
            return current;
        } else if (comparison < 0) {
            return searchRecursive(current.getLeft(), element);
        } else {
            return searchRecursive(current.getRight(), element);
        }
    }

    public void clear() {
        root = null;
        size = 0;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public BSTreeNode<E> getRoot() {
        if (root == null) {
            throw new NullPointerException("Tree is empty.");
        }
        return root;
    }

    public Iterator<E> inorderIterator() {
    	BSTreeIterator<E> accumulator = new BSTreeIterator<E>();
        return inOrderIteratorRecursive(root, accumulator);
    }
    
    private BSTreeIterator<E> inOrderIteratorRecursive(BSTreeNode<E> node, BSTreeIterator<E> accumulator) {
    	if (node == null){
    		return accumulator;
    	}
    	accumulator = inOrderIteratorRecursive(node.getLeft(), accumulator);
    	accumulator.addElement(node.getElement());
    	return inOrderIteratorRecursive(node.getRight(), accumulator);
    }

    public Iterator<E> preorderIterator() {
        BSTreeIterator<E> accumulator = new BSTreeIterator<E>();
        return preOrderIteratorRecursive(root, accumulator);
    }
    
    private BSTreeIterator<E> preOrderIteratorRecursive(BSTreeNode<E> node, BSTreeIterator<E> accumulator) {
    	if (node == null) {
    		return accumulator;
    	}
    	
    	accumulator.addElement(node.getElement());
    	accumulator = preOrderIteratorRecursive(node.getLeft(), accumulator);
    	return preOrderIteratorRecursive(node.getRight(), accumulator);
    }

    public Iterator<E> postorderIterator() {
        BSTreeIterator<E> accumulator = new BSTreeIterator<E>();
        return postOrderIteratorRecursive(root, accumulator);
    }
    
    private BSTreeIterator<E> postOrderIteratorRecursive(BSTreeNode<E> node, BSTreeIterator<E> accumulator) {
    	if (node == null) {
    		return accumulator;
    	}
    	
    	accumulator = postOrderIteratorRecursive(node.getLeft(), accumulator);
    	accumulator = postOrderIteratorRecursive(node.getRight(), accumulator);
    	accumulator.addElement(node.getElement());
    	return accumulator;
    }

    @Override
    public BSTreeNode<E> removeMin() {
        if (root == null) {
            return null;
        }
        BSTreeNode<E> parent = null;
        BSTreeNode<E> current = root;
        while (current.getLeft() != null) {
            parent = current;
            current = current.getLeft();
        }
        if (parent == null) {
            root = current.getRight();
        } else {
            parent.setLeft(current.getRight());
        }
        size--;
        return current;
    }

    public BSTreeNode<E> removeMax() {
        if (root == null) {
            return null;
        }
        BSTreeNode<E> parent = null;
        BSTreeNode<E> current = root;
        while (current.getRight() != null) {
            parent = current;
            current = current.getRight();
        }
        if (parent == null) {
            root = current.getLeft();
        } else {
            parent.setRight(current.getLeft());
        }
        size--;
        return current;
    }

    public int getHeight() {
        return getHeightRecursive(root);
    }

    private int getHeightRecursive(BSTreeNode<E> current) {
        if (current == null) {
            return 0;
        }
        return 1 + Math.max(getHeightRecursive(current.getLeft()), getHeightRecursive(current.getRight()));
    }
}
