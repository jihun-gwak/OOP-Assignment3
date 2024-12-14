package utilities;

import utilities.BSTreeADT;
import java.util.*;

public class BSTreeADT<E extends Comparable<? super E>> implements BSTreeADT<E> {

    private BSTreeNode<E> root;
    private int size;

    public BSTreeADT() {
        this.root = null;
        this.size = 0;
    }

    public BSTreeNode<E> getRoot() throws NullPointerException {
        if (root == null) {
            throw new NullPointerException("The tree is empty.");
        }
        return root;
    }

    public int getHeight() {
        return calculateHeight(root);
    }

    private int calculateHeight(BSTreeNode<E> node) {
        if (node == null) {
            return -1;
        }
        return 1 + Math.max(calculateHeight(node.getLeft()), calculateHeight(node.getRight()));
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        root = null;
        size = 0;
    }

    public boolean contains(E entry) throws NullPointerException {
        if (entry == null) {
            throw new NullPointerException("Cannot search for null.");
        }
        return search(entry) != null;
    }

    public BSTreeNode<E> search(E entry) throws NullPointerException {
        if (entry == null) {
            throw new NullPointerException("Cannot search for null.");
        }
        return searchNode(root, entry);
    }

    private BSTreeNode<E> searchNode(BSTreeNode<E> node, E entry) {
        if (node == null || node.getData().equals(entry)) {
            return node;
        }
        if (entry.compareTo(node.getData()) < 0) {
            return searchNode(node.getLeft(), entry);
        } else {
            return searchNode(node.getRight(), entry);
        }
    }

    public boolean add(E newEntry) throws NullPointerException {
        if (newEntry == null) {
            throw new NullPointerException("Cannot add null.");
        }
        if (root == null) {
            root = new BSTreeNode<>(newEntry);
            size++;
            return true;
        }
        boolean added = addNode(root, newEntry);
        if (added) {
            size++;
        }
        return added;
    }

    private boolean addNode(BSTreeNode<E> node, E newEntry) {
        if (newEntry.compareTo(node.getData()) < 0) {
            if (node.getLeft() == null) {
                node.setLeft(new BSTreeNode<>(newEntry));
                return true;
            }
            return addNode(node.getLeft(), newEntry);
        } else if (newEntry.compareTo(node.getData()) > 0) {
            if (node.getRight() == null) {
                node.setRight(new BSTreeNode<>(newEntry));
                return true;
            }
            return addNode(node.getRight(), newEntry);
        }
        return false; // Duplicate entry
    }

    @Override
    public BSTreeNode<E> removeMin() {
        if (root == null) {
            return null;
        }
        BSTreeNode<E> minNode = findMinNode(root);
        root = removeNode(root, minNode.getData());
        size--;
        return minNode;
    }

    private BSTreeNode<E> findMinNode(BSTreeNode<E> node) {
        while (node.getLeft() != null) {
            node = node.getLeft();
        }
        return node;
    }

    @Override
    public BSTreeNode<E> removeMax() {
        if (root == null) {
            return null;
        }
        BSTreeNode<E> maxNode = findMaxNode(root);
        root = removeNode(root, maxNode.getData());
        size--;
        return maxNode;
    }

    private BSTreeNode<E> findMaxNode(BSTreeNode<E> node) {
        while (node.getRight() != null) {
            node = node.getRight();
        }
        return node;
    }

    private BSTreeNode<E> removeNode(BSTreeNode<E> node, E entry) {
        if (node == null) {
            return null;
        }
        if (entry.compareTo(node.getData()) < 0) {
            node.setLeft(removeNode(node.getLeft(), entry));
        } else if (entry.compareTo(node.getData()) > 0) {
            node.setRight(removeNode(node.getRight(), entry));
        } else {
            if (node.getLeft() == null) {
                return node.getRight();
            } else if (node.getRight() == null) {
                return node.getLeft();
            }
            BSTreeNode<E> minNode = findMinNode(node.getRight());
            node.setData(minNode.getData());
            node.setRight(removeNode(node.getRight(), minNode.getData()));
        }
        return node;
    }

    public Iterator<E> inorderIterator() {
        List<E> elements = new ArrayList<>();
        inorderTraversal(root, elements);
        return (Iterator<E>) elements.iterator();
    }

    private void inorderTraversal(BSTreeNode<E> node, List<E> elements) {
        if (node != null) {
            inorderTraversal(node.getLeft(), elements);
            elements.add(node.getData());
            inorderTraversal(node.getRight(), elements);
        }
    }

    public Iterator<E> preorderIterator() {
        List<E> elements = new ArrayList<>();
        preorderTraversal(root, elements);
        return (Iterator<E>) elements.iterator();
    }

    private void preorderTraversal(BSTreeNode<E> node, List<E> elements) {
        if (node != null) {
            elements.add(node.getData());
            preorderTraversal(node.getLeft(), elements);
            preorderTraversal(node.getRight(), elements);
        }
    }

    public Iterator<E> postorderIterator() {
        List<E> elements = new ArrayList<>();
        postorderTraversal(root, elements);
        return (Iterator<E>) elements.iterator();
    }

    private void postorderTraversal(BSTreeNode<E> node, List<E> elements) {
        if (node != null) {
            postorderTraversal(node.getLeft(), elements);
            postorderTraversal(node.getRight(), elements);
            elements.add(node.getData());
        }
    }
}
