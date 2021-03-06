package application;


import java.util.ArrayList;
import java.util.List;

//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title: Representation Tracker
// Files:
//
// Course: CS400, Fall 2019
//
// Author: Nate Goethel
// Email: ngoethel@wisc.edu
// Lecturer's Name: Andrew Kuemmel
//
///////////////////////////// CREDIT OUTSIDE HELP /////////////////////////////
//
// Students who get help from sources other than their partner must fully
// acknowledge and credit those sources of help here. Instructors and TAs do
// not need to be credited here, but tutors, friends, relatives, room mates,
// strangers, and others do. If you received no outside help from either type
// of source, then please explicitly indicate NONE.
//
// Persons: NONE
//
// Online Sources: https://condor.depaul.edu/slytinen/301w15/twothree/
// TwoThreeTree.java - helped get my 2-3 Tree implementation
// started
// https://pages.cs.wisc.edu/~deppeler/cs400/readings/
// 23Trees/#insert - helped me understand the algorithms for
// the major operations
// http://ranger.uta.edu/~kosmopo/cse5311/lectures/LeftLeaningRedBlackTrees.pdf
// https://www.geeksforgeeks.org/left-leaning-red-black-tree-insertion/
// https://medium.com/100-days-of-algorithms/day-76-2-3-tree-f20935b0e78b
// https://www.youtube.com/watch?v=9PiitpHLvRM
// https://stackoverflow.com/questions/18782110/
// best-structure-for-list-of-key-value-integer-string-to-be-shuffled
//
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////
/**
 * Represents a B-Tree
 * 
 * @author Nate
 *
 * @param <T>
 */
public class BTree<K extends Comparable<K>, V> implements BTreeADT<K, V> {

  /**
   * private inner class for the nodes of the tree
   * 
   * A node has 1, 2, or 3 values and 2, 3, or 4 children, respectively.
   * 
   * @author Nate
   *
   * @param <T>
   */
  // BTree instance variables
  private int size; // number of nodes in the tree
  private int numKeysTree; // number of keys in the tree
  private BTreeNode root;

  // inner class for B Tree Nodes
  private class BTreeNode {
    private int numKeys;
    private BTreeNode leftChild;
    private BTreeNode midLeftChild;
    private BTreeNode midRightChild;
    private BTreeNode rightChild;
    private Pair leftPair;
    private Pair midPair;
    private Pair rightPair;
    private BTreeNode parent;

    private BTreeNode() {
      this.numKeys = 0;
      this.leftChild = null;
      this.midLeftChild = null;
      this.midRightChild = null;
      this.rightChild = null;
      this.leftPair = null;
      this.leftPair = null;
      this.rightPair = null;
      this.parent = null;
    }

    private BTreeNode(Pair P, BTreeNode leftChild, BTreeNode rightChild) {
      this.leftChild = leftChild;
      this.midLeftChild = rightChild;

      if (this.leftChild != null) {
        this.leftChild.parent = this;
      }

      if (this.midLeftChild != null) {
        this.midLeftChild.parent = this;
      }

      this.setLeftPair(P);
      this.numKeys = 1;
    }

    /**
     * Gets the number of keys in the node
     * @return the number of keys 
     */
    public int getNumKeys() {
      return this.numKeys;
    }

    /**
     * Incremenets the number of keys in the node
     */
    public void addNumKeys() {
      this.numKeys++;
    }

    /**
     * Decrements the count of keys in the node
     */
    public void removeNumKeys() {
      this.numKeys--;
    }

    /**
     * Gets the number of children
     * @return the number of children
     */
    public int getNumChildren() {
      int count = 0;
      ArrayList<BTreeNode> children = new ArrayList<BTreeNode>();
      children.add(leftChild);
      children.add(midLeftChild);
      children.add(midRightChild);
      children.add(rightChild);

      for (BTreeNode child : children) {
        if (child != null) {
          count++;
        }
      }

      return count;
    }

    /**
     * Gets the left child
     * 
     * @return the left child
     */
    public BTreeNode getLeftChild() {
      return leftChild;
    }

    /**
     * Sets the left child
     * 
     * @param child the child to set
     */
    public void setLeftChild(BTreeNode child) {
      this.leftChild = child;
      if (child != null) {
        child.setParent(this);
      }
    }

    /**
     * Gets the middle left child
     * 
     * @return the middle left child
     */
    public BTreeNode getMidLeftChild() {
      return midLeftChild;
    }

    /**
     * Sets the middle left child
     * 
     * @param child the child to set
     */
    public void setMidLeftChild(BTreeNode child) {
      this.midLeftChild = child;
      if (child != null) {
        child.setParent(this);
      }
    }

    /**
     * Gets the middle right child
     * 
     * @return the middle right child
     */
    public BTreeNode getMidRightChild() {
      return midRightChild;
    }

    /**
     * Sets the middle right child
     * 
     * @param child the child to set
     */
    public void setMidRightChild(BTreeNode child) {
      this.midRightChild = child;
      if (child != null) {
        child.setParent(this);
      }
    }

    /**
     * Gets the right child node
     * 
     * @return the right child node
     */
    public BTreeNode getRightChild() {
      return rightChild;
    }

    /**
     * Sets the right child node
     * 
     * @param child the node to set
     */
    public void setRightChild(BTreeNode child) {
      this.rightChild = child;
      if (child != null) {
        child.setParent(this);
      }
    }

    /**
     * Gets the left pair
     * 
     * @return the left pair
     */
    public Pair getLeftPair() {
      return leftPair;
    }

    /**
     * Sets the left pair
     * 
     * @param leftPair the pair to set
     */
    public void setLeftPair(Pair leftPair) {
      this.leftPair = leftPair;
    }

    /**
     * Gets middle pair
     * 
     * @return the middle pair
     */
    public Pair getMidPair() {
      return this.midPair;
    }

    /**
     * Sets the middle pair
     * 
     * @param midPair pair to set
     */
    public void setMidPair(Pair midPair) {
      this.midPair = midPair;
    }

    /**
     * Get's the node's right pair
     * 
     * @return the node's right pair
     */
    public Pair getRightPair() {
      return this.rightPair;
    }

    /**
     * Set's the node's right pair
     * 
     * @param rightPair the pair to set
     */
    public void setRightPair(Pair rightPair) {
      this.rightPair = rightPair;
    }

    /**
     * Gets the node's parent
     * 
     * @return the node's parent
     */
    public BTreeNode getParent() {
      return parent;
    }

    /**
     * Set's the node's parent
     * 
     * @param parent the node to set
     */
    public void setParent(BTreeNode parent) {
      this.parent = parent;
    }

    /**
     * Checks if the node is full
     * 
     * @return true if so, false otherwise
     */
    public boolean isFullNode() {
      if (this.getNumKeys() == 3) {
        return true;
      }
      return false;
    }

    /**
     * Checks whether the node is a leaf
     * 
     * @return true if so, false otherwise
     */
    public boolean isLeaf() {

      if (this.getLeftChild() == null && this.getMidLeftChild() == null
          && this.getMidRightChild() == null && this.getRightChild() == null) {
        return true;
      }

      return false;
    }

    /**
     * Checks whether the node contains a certain key
     * 
     * @param key the key to check for
     * @return true if any of the key/value pairs in the node contain the key, false otherwise
     */
    public boolean containsKey(K key) {

      if (this.isEmpty()) {
        return false;
      }

      ArrayList<Pair> pairs = new ArrayList<Pair>();
      pairs.add(this.getLeftPair());
      pairs.add(this.getMidPair());
      pairs.add(this.getRightPair());

      for (Pair p : pairs) {
        if (p != null && p.key.equals(key)) {
          return true;
        }
      }

      return false;
    }

    /**
     * Checks whether the node has no keys
     * 
     * @return true if all keys are null, false otherwise
     */
    public boolean isEmpty() {

      if (this.leftPair == null && this.midPair == null && this.rightPair == null) {
        return true;
      }

      return false;
    }

    /**
     * Gets the node's keys
     * 
     * @return the node's keys
     */
    public List<K> getKeys() {
      ArrayList<K> keys = new ArrayList<K>();
      ArrayList<Pair> pairs = new ArrayList<Pair>();
      pairs.add(this.getLeftPair());
      pairs.add(this.getMidPair());
      pairs.add(this.getRightPair());

      for (Pair p : pairs) {
        if (p != null) {
          keys.add(p.getKey());
        }
      }

      return keys;
    }

    /**
     * Returns the node's children's pairs
     * 
     * @return the node's children's pairs
     */
    public List<Pair> getChildren() {
      ArrayList<Pair> children = new ArrayList<Pair>();

      children.add(this.leftPair);
      children.add(this.midPair);
      children.add(this.getRightPair());

      return children;
    }
  }

  // inner class for key value pairs
  private class Pair {
    private K key;
    private V value;

    private Pair() {
      this.key = null;
      this.value = null;
    }

    private Pair(K key, V value) {
      this.key = key;
      this.value = value;
    }

    /**
     * Gets the key
     * 
     * @return the key
     */
    public K getKey() {
      return key;
    }

    /**
     * Sets the key
     * 
     * @param key the key to set
     */
    public void setKey(K key) {
      this.key = key;
    }

    /**
     * Gets the value
     * 
     * @return the value
     */
    public V getValue() {
      return value;
    }

    /**
     * Sets the value
     * 
     * @param value the value to set
     */
    public void setValue(V value) {
      this.value = value;
    }
  }

  // constructors
  public BTree() {
    this.root = null;
    this.size = 0;
    this.numKeysTree = 0;
  }

  public BTree(int branchingFactor) {
    this.root = null;
    this.size = 0;
    this.numKeysTree = 0;
  }

  /**
   * Adds a new key/value pair to the tree
   */
  @Override
  public void addKey(K key, V value) throws IllegalArgumentException {

    if (key == null) {
      throw new IllegalArgumentException();
    }

    root = addKey(root, key, value);
    while (root.parent != null) {
      root = root.parent;
    }

  }

  /**
   * Private helper method for addKey() above.
   * 
   * @param current the current node
   * @param key     the key to add
   * @param value   the value to add
   * @return the newly added node
   */
  private BTreeNode addKey(BTreeNode current, K key, V value) {

    if (current == null) {
      current = new BTreeNode();
      return addKeyLeaf(current, key, value);
    }

    // check whether the key already exists in the tree
    if (current.containsKey(key)) {
      return null;
    }

    if (current.isFullNode()) { // the current node is full, split it
      current = BTreeSplit(this, current);
    }

    if (current.isLeaf() == false) { // if the current node isn't a leaf node

      // if the key is smaller than current's smallest key, go to the left child
      if (key.compareTo(current.getLeftPair().getKey()) < 0) {
        return addKey(current.getLeftChild(), key, value);

        // if the current node's middle key is null or key is less than the current node's middle
        // key,
        // go to the middle left child
      } else if (current.getMidPair() == null || key.compareTo(current.getMidPair().getKey()) < 0) {
        return addKey(current.getMidLeftChild(), key, value);

        // if the current node's largest key is null or key is less than the largest key, go
        // to the middle right child
      } else if (current.getRightPair() == null
          || key.compareTo(current.getRightPair().getKey()) < 0) {
        return addKey(current.getMidRightChild(), key, value);

        // otherwise, go to the right child
      } else {
        return addKey(current.getRightChild(), key, value);
      }
    } else {
      addKeyLeaf(current, key, value);
    }
    return current;
  }

  /**
   * Removes a key from the tree
   */
  @Override
  public void removeKey(K key) throws IllegalArgumentException, KeyNotFoundException {

    if (key == null) {
      throw new IllegalArgumentException();
    }

    if (contains(key) == false) {
      throw new KeyNotFoundException();
    }

    removeKey(root, key);

  }


  /**
   * Recursively find the node to remove
   * 
   * @param node the node to start searching from
   * @param key  the key to search for
   * @return true if the key was removed, false otherwise
   */
  private boolean removeKey(BTreeNode node, K key) {

    if (node == null) {
      return false;
    }

    if (root.isLeaf() && root.getNumKeys() == 1 && root.containsKey(key)) {
      root = null;
      numKeysTree--;
      return true;
    }


    // preemptive merge
    if (node.getNumKeys() == 1 && node.equals(root) == false) {
      node = BTreeMerge(node);
    }

    int keyIndex = getKeyIndex(node, key);

    // if the key isn't in the node, keep recurring down the tree
    if (keyIndex == -1) {
      if (key.compareTo(node.leftPair.getKey()) < 0) { // go to left child
        return removeKey(node.getLeftChild(), key);

        // go to the middle left child
      } else if (node.getMidPair() == null || key.compareTo(node.getMidPair().getKey()) < 0) {
        return removeKey(node.getMidLeftChild(), key);

        // go to the middle right child
      } else if (node.getRightPair() == null || key.compareTo(node.getRightPair().getKey()) < 0) {
        return removeKey(node.getMidRightChild(), key);

      } else { // go to right child
        return removeKey(node.getRightChild(), key);
      }
    } else {
      if (node.isLeaf()) {
        removeKeyFromIndex(node, keyIndex);
        node.removeNumKeys();
        return true;
      } else {
        BTreeNode tmpChild = getChild(node, keyIndex + 1);
        Pair tmpKey = getMinKey(tmpChild);
        try {
          removeKey(tmpKey.getKey());
        } catch (IllegalArgumentException | KeyNotFoundException e) {
   
          e.printStackTrace();
        }
        keySwapPair(root, find(root, key), tmpKey);
        return true;
      }

    }
  }

  /**
   * Determines whether the tree contains a certain key
   * 
   * @param key the key to check for
   * @return true if the key is already in the tree, false otherwise
   */
  public boolean contains(K key) {
    List<K> keyList = this.getAllKeys();

    if (keyList.contains(key)) {
      return true;
    }

    return false;
  }

  /**
   * Finds a value given a key
   * 
   * @param key the key of the key/value pair to search for
   * @return the value associated with the key, null otherwise
   * @throws KeyNotFoundException
   */
  public V findValue(K key) throws KeyNotFoundException {

    if (contains(key) == false) {
      throw new KeyNotFoundException();
    }

    V valueToReturn = null;
    Pair foundPair = find(root, key);

    if (foundPair != null) {
      valueToReturn = foundPair.getValue();
    }

    return valueToReturn;
  }

  /**
   * Returns a list of all of the objects in the tree.
   * 
   * @return a list of all objects in the tree
   */
  public List<V> getAllValues() {
    List<V> valueList = new ArrayList<V>();
    return getAllValues(root, valueList);
  }

  private List<V> getAllValues(BTreeNode current, List<V> valueList) {


    if (current != null) {
      if (current.getNumChildren() == 1) {
        getAllValues(current.getLeftChild(), valueList);
      } else if (current.getNumChildren() == 2) {
        getAllValues(current.getMidLeftChild(), valueList);
        for (Pair p : current.getChildren()) {
          if (p != null) {
            valueList.add(p.getValue());
          }
        }
        getAllValues(current.getLeftChild(), valueList);
      } else if (current.getNumChildren() == 3) {
        getAllValues(current.getMidRightChild(), valueList);
        for (Pair p : current.getChildren()) {
          if (p != null) {
            valueList.add(p.getValue());
          }
        }
        getAllValues(current.getMidLeftChild(), valueList);
        getAllValues(current.getLeftChild(), valueList);
      } else {
        getAllValues(current.getRightChild(), valueList);
        getAllValues(current.getMidRightChild(), valueList);
        for (Pair p : current.getChildren()) {
          if (p != null) {
            valueList.add(p.getValue());
          }
        }
        getAllValues(current.getMidLeftChild(), valueList);
        getAllValues(current.getLeftChild(), valueList);
      }
    }


    return valueList;
  }

  /**
   * Creates a list of all keys in the tree
   * 
   * @return a list of all keys
   */
  public List<K> getAllKeys() {
    List<K> keyList = new ArrayList<K>();
    return getAllKeys(root, keyList);
  }

  /**
   * Private helper for getAllkeys() above
   * 
   * @param current the node to start from
   * @param keyList a list of all of the keys in the tree
   * @return a list of all keys in the tree
   */
  private List<K> getAllKeys(BTreeNode current, List<K> keyList) {
    if (current != null) {
      if (current.getNumChildren() == 1) {
        getAllKeys(current.getLeftChild(), keyList);
      } else if (current.getNumChildren() == 2) {
        getAllKeys(current.getMidLeftChild(), keyList);
        for (Pair p : current.getChildren()) {
          if (p != null) {
            keyList.add(p.getKey());
          }
        }
        getAllKeys(current.getLeftChild(), keyList);
      } else if (current.getNumChildren() == 3) {
        getAllKeys(current.getMidRightChild(), keyList);
        for (Pair p : current.getChildren()) {
          if (p != null) {
            keyList.add(p.getKey());
          }
        }
        getAllKeys(current.getMidLeftChild(), keyList);
        getAllKeys(current.getLeftChild(), keyList);
      } else {
        getAllKeys(current.getRightChild(), keyList);
        getAllKeys(current.getMidRightChild(), keyList);
        for (Pair p : current.getChildren()) {
          if (p != null) {
            keyList.add(p.getKey());
          }
        }
        getAllKeys(current.getMidLeftChild(), keyList);
        getAllKeys(current.getLeftChild(), keyList);
      }
    }
    return keyList;
  }

  /**
   * Find the number of keys in the tree
   * 
   * @return the number of keys in the tree
   */
  public int getNumKeys() {
    return numKeysTree;
  }

  /**
   * source: Building Java Programs, 4thEd., by Reges and Stepp, Ch. 17
   * 
   * Prints the tree sideways
   */
  public void printSideways() {
    System.out.println("------------------------------------------");
    printSideways(this.root, "");
    System.out.println("------------------------------------------");
  }

  /**
   * private recursive helper method for printSideways() above.
   * 
   * @param current the node to print from
   * @param indent
   */
  private void printSideways(BTreeNode current, String indent) {
    if (current != null) {
      if (current.getNumChildren() == 1) {
        printSideways(current.getLeftChild(), indent + "    ");
      } else if (current.getNumChildren() == 2) {
        printSideways(current.getMidLeftChild(), indent + "    ");
        System.out.println(indent + current.getKeys());
        printSideways(current.getLeftChild(), indent + "    ");
      } else if (current.getNumChildren() == 3) {
        printSideways(current.getMidRightChild(), indent + "    ");
        System.out.println(indent + current.getKeys());
        printSideways(current.getMidLeftChild(), indent + "    ");
        printSideways(current.getLeftChild(), indent + "    ");
      } else {
        printSideways(current.getRightChild(), indent + "    ");
        printSideways(current.getMidRightChild(), indent + "    ");
        System.out.println(indent + current.getKeys());
        printSideways(current.getMidLeftChild(), indent + "    ");
        printSideways(current.getLeftChild(), indent + "    ");
      }
    }
  }

  /**
   * Adds a key to a leaf node
   * 
   * @param current the current node
   * @param key     the key to add
   * @param value   the value to add
   * @return the new node, null otherwise
   */
  private BTreeNode addKeyLeaf(BTreeNode current, K key, V value) {

    if (current.isEmpty()) {
      Pair newPair = new Pair(key, value);
      current.setLeftPair(newPair);
      current.addNumKeys();
      numKeysTree++;
      return current;
    }


    // case 1: New key equals an existing key in the node
    if (current.containsKey(key)) {
      return null;
    }

    // case 2: New key is < node's first key -> Existing keys are shifted right
    if (key.compareTo(current.getLeftPair().getKey()) < 0) {
      Pair newPair = new Pair(key, value); // create a new key/pair value
      current.setRightPair(current.getMidPair());
      current.setMidPair(current.getLeftPair());
      current.setLeftPair(newPair);
      current.addNumKeys();
      numKeysTree++;
      return current;
    }

    // case 3: Node has only 1 key or new key is < node's middle key -> new key is middle key
    if (current.getNumKeys() == 1 || key.compareTo(current.getMidPair().getKey()) < 0) {
      Pair newPair = new Pair(key, value);
      if (current.getMidPair() != null) { // if middle pair isn't null, move it to the right
        current.setRightPair(current.getMidPair());
        current.setMidPair(newPair); // insert the new pair in the middle position
        current.addNumKeys(); // increment the number of keys in the node
        numKeysTree++; // increment the number of keys in the tree
        return current;
      } else { // if the tree has only one key
        current.setMidPair(newPair); // set the middle key to the new pair and increment counts
        current.addNumKeys();
        numKeysTree++;
        return current;
      }
    }

    // case 4: None of the above -> New key becomes last key
    Pair newPair = new Pair(key, value);
    current.setRightPair(newPair);;
    current.addNumKeys();
    numKeysTree++;

    return current;
  }


  /**
   * Adds a key to an interior node
   * 
   * @param parent     the current node
   * @param P          the pair to add
   * @param leftChild  the left child node
   * @param rightChild the right child node
   */
  private void addKeyInterior(BTreeNode parent, Pair P, BTreeNode leftChild, BTreeNode rightChild) {

    // if the key is smaller than the parent's smallest key, push parent's keys to the right
    if (P.getKey().compareTo(parent.getLeftPair().getKey()) < 0) {
      parent.setRightPair(parent.getMidPair());
      parent.setMidPair(parent.getLeftPair());
      parent.setLeftPair(P);
      parent.addNumKeys();

      // set the children
      parent.setRightChild(parent.getMidRightChild());
      parent.setMidRightChild(parent.getMidLeftChild());
      parent.setMidLeftChild(rightChild);
      parent.setLeftChild(leftChild);

    } else if (parent.getMidPair() == null
        || P.getKey().compareTo(parent.getMidPair().getKey()) < 0) {
      parent.setRightPair(parent.getMidPair());
      parent.setMidPair(P);
      parent.addNumKeys();
      parent.setRightChild(parent.getMidRightChild());
      parent.setMidRightChild(rightChild);
      parent.setMidLeftChild(leftChild);
    } else {
      parent.setRightPair(P);
      parent.addNumKeys();
      parent.setRightChild(rightChild);
      parent.setMidRightChild(leftChild);
    }

  }

  /**
   * finds a Pair with a given key
   * 
   * @param node the node to start searching from
   * @param key  the key to find
   * @return the Pair associated with the key, null otherwise
   */
  private Pair find(BTreeNode node, K key) {


    Pair pairToReturn = null;

    if (node != null && node.containsKey(key)) {// base case
      if (node.getLeftPair().getKey().equals(key)) {
        pairToReturn = node.getLeftPair();
        return pairToReturn;
      } else if (node.getMidPair().getKey().equals(key)) {
        pairToReturn = node.getMidPair();
        return pairToReturn;
      } else {
        pairToReturn = node.getRightPair();
        return pairToReturn;
      }

    } else { // recursive part
      if (key.compareTo(node.getLeftPair().getKey()) < 0) { // go to left child
        pairToReturn = find(node.getLeftChild(), key);
        // go to the middle left child
      } else if (node.getMidPair() == null || key.compareTo(node.getMidPair().getKey()) < 0) {
        pairToReturn = find(node.getMidLeftChild(), key);

        // go to the middle right child
      } else if (node.getRightPair() == null || key.compareTo(node.getRightPair().getKey()) < 0) {
        pairToReturn = find(node.getMidRightChild(), key);

      } else { // go to right child
        pairToReturn = find(node.getRightChild(), key);
      }
    }

    return pairToReturn;

  }

  /**
   * Moves a key from the given node to the parent and moves a key from the parent to the node's
   * sibling.
   * 
   * @param node the node to rotate
   */
  private void leftRotate(BTreeNode node) {

    BTreeNode leftSibling = getLeftSibling(node);
    Pair leftSibKey = getParentKeyOfLeftChild(node.parent, node);
    addKeyAndChild(leftSibling, node.getLeftChild(), leftSibKey);
    setParentKeyOfLeftChild(node.parent, node, node.getLeftPair());
    removeKeyFromIndex(node, 0);

  }

  /**
   * Moves a key from the given node to the parent and moves a key from the parent to the node's
   * sibling.
   * 
   * @param node the node to rotate
   */
  private void rightRotate(BTreeNode node) {

    BTreeNode rightSibling = getRightSibling(node);
    Pair rightSibKey = getParentKeyOfRightChild(node.parent, node);
    addKeyAndChild(rightSibling, node.getRightChild(), rightSibKey);
    setParentKeyOfRightChild(node.parent, node, node.getRightPair());
    removeKeyFromIndex(node, 2);
  }

  /**
   * Splits a node
   * 
   * @param tree the tree that holds the node
   * @param node the node to split
   * @return the new node created by the splits
   */
  private BTreeNode BTreeSplit(BTree<K, V> tree, BTreeNode node) {

    if (node.isFullNode() == false) { // if the node isn't full, don't split and return.
      return null;
    }

    BTreeNode parent = node.parent;
    BTreeNode splitLeft =
        new BTreeNode(node.getLeftPair(), node.getLeftChild(), node.getMidLeftChild());
    BTreeNode splitRight =
        new BTreeNode(node.getRightPair(), node.getMidRightChild(), node.getRightChild());

    if (parent != null) {
      addKeyInterior(parent, node.getMidPair(), splitLeft, splitRight);
    } else {
      parent = new BTreeNode(node.getMidPair(), splitLeft, splitRight);
      splitLeft.setParent(parent);
      splitRight.setParent(parent);


    }

    return parent;

  }

  /**
   * Merges a node if necessary
   * 
   * @param node the node to merge
   * @return the merged node
   */
  private BTreeNode BTreeMerge(BTreeNode node) {

    BTreeNode leftSibling = getLeftSibling(node);
    BTreeNode rightSibling = getRightSibling(node);

    if (leftSibling != null && leftSibling.getNumKeys() >= 2) {
      rightRotate(leftSibling);
    } else if (rightSibling != null && rightSibling.getNumKeys() >= 2) {
      leftRotate(rightSibling);
    } else {
      if (leftSibling == null) {
        node = fuse(node, rightSibling);
      } else {
        node = fuse(leftSibling, node);
      }
    }

    return node;
  }

  /**
   * Returns the left sibling of the given node
   * 
   * @param node the node whose sibling to find
   * @return the left sibling of the node, null otherwise
   */
  private BTreeNode getLeftSibling(BTreeNode node) {

    BTreeNode parent = node.getParent();

    if (parent != null) {
      // if node is parent's left child, return null
      if (parent.getLeftChild() != null && parent.getLeftChild().equals(node)) {
        return null;
      }

      // if node is parent's middle left child, return left child
      if (parent.getMidLeftChild() != null && parent.getMidLeftChild().equals(node)) {
        return parent.getLeftChild();
      }

      // if node is parent's middle right child, return the middle left child
      if (parent.getMidRightChild() != null && parent.getMidRightChild().equals(node)) {
        return parent.getMidLeftChild();
      }

      // if node is parent's right child, return the middle right child
      if (parent.getRightChild() != null && parent.getRightChild().equals(node)) {
        return parent.getMidRightChild();
      }
    }

    return null;
  }

  /**
   * Returns the right sibling of the given node
   * 
   * @param node the node whose sibling to find
   * @return the right sibling of the node, null otherwise
   */
  private BTreeNode getRightSibling(BTreeNode node) {

    // get the parent
    BTreeNode parent = node.getParent();

    // check that the node isn't root
    if (parent != null) {
      // if node is parent's right child, return null
      if (parent.getRightChild() != null && parent.getRightChild().equals(node)) {
        return null;
      }

      // if node is parent's middle right child, return right child
      if (parent.getMidRightChild() != null && parent.getMidRightChild().equals(node)) {
        return parent.getRightChild();
      }

      // if node is parent's middle left child, return the middle right child
      if (parent.getMidLeftChild() != null && parent.getMidLeftChild().equals(node)) {
        return parent.getMidRightChild();
      }

      // if node is parent's left child, return the middle left child
      if (parent.getLeftChild() != null && parent.getLeftChild().equals(node)) {
        return parent.getMidLeftChild();
      }
    }

    return null;

  }

  /**
   * Returns the key that is immediately left of the child
   * 
   * @param parent the parent node
   * @param child  the child node
   * @return the key to the left of the child, null otherwise
   */
  private Pair getParentKeyOfLeftChild(BTreeNode parent, BTreeNode child) {

    // if child is the left child, return null
    if (parent.getLeftChild().equals(child)) {
      return null;
    }

    // if child is the middle left child, return the left key
    if (parent.getMidLeftChild().equals(child)) {
      return parent.getLeftPair();
    }

    // if child is the middle right child, return the middle key
    if (parent.getMidRightChild().equals(child)) {
      return parent.getMidPair();
    }

    // if child is the right child, return the right key
    if (parent.getRightChild().equals(child)) {
      return parent.getRightPair();
    }


    return null;
  }

  /**
   * Sets the key in the parent node that is immediately left of the child node
   * 
   * @param parent the parent node
   * @param child  the child node
   * @param p      the pair (key) to set
   */
  private void setParentKeyOfLeftChild(BTreeNode parent, BTreeNode child, Pair p) {

    // if child is the left child, do nothing

    // if child is the middle left child, set the parent's left pair
    if (parent.getMidLeftChild().equals(child)) {
      parent.setLeftPair(p);
      return;
    }

    // if child is the middle right child, set the parent's middle pair
    if (parent.getMidRightChild().equals(child)) {
      parent.setMidPair(p);
      return;
    }

    // if child is the right child, set the parent's right pair

    // System.out.println("Right child is: " + parent.getRightChild().getKeys());
    if (parent.getRightChild().equals(child)) {
      parent.setRightPair(p);
      return;
    }
  }

  /**
   * Get the key/pair in the parent that is immediately to the right of the child node
   * 
   * @param parent the parent node
   * @param child  the child node
   * @return the pair to the right of child
   */
  private Pair getParentKeyOfRightChild(BTreeNode parent, BTreeNode child) {
    // if the child is the right child, return null
    if (parent.getRightChild().equals(child)) {
      return null;
    }

    // if the child is the middle right child, return the right key
    if (parent.getMidRightChild().equals(child)) {
      return parent.getRightPair();
    }

    // if the child is the middle left child, return the middle key
    if (parent.getMidLeftChild().equals(child)) {
      return parent.getMidPair();
    }

    // if the child is the left child, return the left key
    if (parent.getLeftChild().equals(child)) {
      return parent.getLeftPair();
    }

    return null;
  }

  /**
   * Sets the key in the parent node that is immediately right of the child node
   * 
   * @param parent the parent node
   * @param child  the child node
   * @param p      the pair to set
   */
  private void setParentKeyOfRightChild(BTreeNode parent, BTreeNode child, Pair p) {

    // if child is the right child, do nothing

    // if child is the middle right child, set the parent's right pair
    if (parent.getMidRightChild().equals(child)) {
      parent.setRightPair(p);
    }

    // if the child is the middle left child, set the parent's middle pair
    if (parent.getMidLeftChild().equals(child)) {
      parent.setMidPair(p);
    }

    // if the child is the left child, set the parent's left pair
    if (parent.getLeftChild().equals(child)) {
      parent.setLeftPair(p);
    }


  }

  /**
   * Adds one new key and one new child to the parent node. The new key must be greater than the
   * other keys in the node, and the child subtree must be greater than the key.
   * 
   * Ex: If parent has 1 key, the new key becomes the middle key and the child becomes the middle
   * right child. If parent has 2 keys, the new key becomes the right key and the child becomes the
   * right child.
   * 
   * @param node  the node to modify
   * @param child the child node to add
   * @param p     the key to add
   */
  private void addKeyAndChild(BTreeNode node, BTreeNode child, Pair p) {

    // make sure that the node we're modifying isn't full and isn't empty
    if (node.isFullNode() == false && node.isEmpty() == false) {
      if (node.getNumKeys() == 1) {
        node.setMidPair(p);
        node.setMidRightChild(child);
        node.addNumKeys();
      } else if (node.getNumKeys() == 2) {
        node.setRightPair(p);
        node.setRightChild(child);
        node.addNumKeys();
      }
    }
  }

  /**
   * Removes a key from the given node.
   * 
   * @param node     the node to remove a key from
   * @param keyIndex the index of the key
   */
  private void removeKeyFromIndex(BTreeNode node, int keyIndex) {

    // remove left key
    if (keyIndex == 0) {
      node.setLeftPair(node.getMidPair());
      node.setMidPair(node.getRightPair());
      node.setRightPair(null);
      node.numKeys--;
      numKeysTree--;


      node.setLeftChild(node.getMidLeftChild());
      node.setMidLeftChild(node.getMidRightChild());
      node.setMidRightChild(node.getRightChild());
      node.setRightChild(null);

      // remove middle key
    } else if (keyIndex == 1) {
      node.setMidPair(node.getRightPair());
      node.setRightPair(null);
      node.numKeys--;
      numKeysTree--;

      node.setMidRightChild(node.getRightChild());
      node.setRightChild(null);

      // remove right key
    } else if (keyIndex == 2) {
      node.setRightPair(null);
      node.numKeys--;
      numKeysTree--;


      node.setRightChild(null);
    }
  }

  /**
   * Performs a fusion on the root node
   * 
   * @param node the node to fuse
   */
  private BTreeNode fuseRoot(BTreeNode root) {
    BTreeNode oldLeft = root.getLeftChild();
    BTreeNode oldMidLeft = root.getMidLeftChild();

    root.setMidPair(root.getLeftPair());
    root.setLeftPair(oldLeft.getLeftPair());
    root.setRightPair(oldMidLeft.getLeftPair());


    root.setLeftChild(oldLeft.getLeftChild());
    root.setMidLeftChild(oldLeft.getMidLeftChild());
    root.setMidRightChild(oldMidLeft.getLeftChild());
    root.setRightChild(oldMidLeft.getMidLeftChild());

    return root;
  }

  /**
   * Fuses two non-root nodes together
   * 
   * @param node1 the first node to fuse
   * @param node2 the second node to fuse
   */
  private BTreeNode fuse(BTreeNode node1, BTreeNode node2) {
    BTreeNode parent = node1.getParent();

    if (parent.getParent() == null && parent.getNumKeys() == 1) {
      return fuseRoot(parent);
    }

    Pair midKey = getParentKeyOfLeftChild(parent, node2);
    BTreeNode fusedNode = new BTreeNode();
    fusedNode.setLeftPair(node1.getLeftPair());
    fusedNode.setMidPair(midKey);
    fusedNode.setRightPair(node2.getLeftPair());
    fusedNode.setLeftChild(node1.getLeftChild());
    fusedNode.setMidLeftChild(node1.getMidLeftChild());
    fusedNode.setMidRightChild(node2.getLeftChild());
    fusedNode.setRightChild(node2.getMidLeftChild());
    int keyIndex = getKeyIndex(parent, midKey.getKey());
    removeKeyFromIndex(parent, keyIndex);
    setChild(parent, fusedNode, keyIndex);

    return fusedNode;
  }

  /**
   * Sets the left, middle left, middle right, or right child of the parent node.
   * 
   * @param parent the node whose child to set
   * @param child  the node to become a child of parent
   * @param index  the index (0-3) at which to set the child. 0 = left, 1 = middle left, etc.
   */
  private void setChild(BTreeNode parent, BTreeNode child, int index) {

    if (index == 0) {
      parent.setLeftChild(child);
    }

    if (index == 1) {
      parent.setMidLeftChild(child);
    }

    if (index == 2) {
      parent.setMidRightChild(child);
    }

    if (index == 3) {
      parent.setRightChild(child);
    }


  }

  /**
   * Returns a given node's child based on it's index
   * 
   * @param node
   * @param index
   * @return the child node
   */
  private BTreeNode getChild(BTreeNode node, int index) {

    if (index == 0) {
      return node.getLeftChild();
    }

    if (index == 1) {
      return node.getMidLeftChild();
    }

    if (index == 2) {
      return node.getMidRightChild();
    }

    if (index == 3) {
      return node.getRightChild();
    }

    return null;
  }

  /**
   * Returns the index of the key in the node
   * 
   * @param node the node to search
   * @param p    the pair/key to find
   * @return the index (0-2) of the pair in the node, -1 otherwise
   */
  private int getKeyIndex(BTreeNode node, K key) {



    if (node.getLeftPair() != null && node.getLeftPair().getKey().equals(key)) {
      return 0;
    }


    if (node.getMidPair() != null && node.getMidPair().getKey().equals(key)) {
      return 1;
    }


    if (node.getRightPair() != null && node.getRightPair().getKey().equals(key)) {
      return 2;
    }


    return -1;
  }

  /**
   * Returns the smallest key in a subtree
   * 
   * @param node the node to start searching from.
   * @return
   */
  private Pair getMinKey(BTreeNode node) {
    BTreeNode current = node;

    while (current.getLeftChild() != null) {
      current = current.getLeftChild();
    }

    return current.getLeftPair();
  }

  /**
   * Returns the next node that would be visited in a traversal
   * 
   * @param node
   * @param p
   * @return
   */
  private BTreeNode nextNode(BTreeNode node, K key) {

    if (key.compareTo(node.getLeftPair().getKey()) < 0) {
      return node.getLeftChild();
    } else if (node.getMidPair() == null || key.compareTo(node.getMidPair().getKey()) < 0) {
      return node.getMidLeftChild();
    } else if (node.getRightPair() == null || key.compareTo(node.getRightPair().getKey()) < 0) {
      return node.getMidRightChild();
    } else {
      return node.getRightChild();
    }

  }

  /**
   * Swaps keys between two nodes
   * 
   * @param node     node to swap pairs
   * @param currPair old pair to swap
   * @param newPair  new pair
   * @return true if the swap was successful, false otherwise
   */
  private boolean keySwapPair(BTreeNode node, Pair currPair, Pair newPair) {
    if (node == null) {
      return false;
    }

    int keyIndex = getKeyIndex(node, currPair.getKey());

    if (keyIndex == -1) {
      BTreeNode next = nextNode(node, currPair.getKey());
      return keySwapPair(next, currPair, newPair);
    } else if (keyIndex == 0) {
      node.setLeftPair(newPair);
      return true;
    } else if (keyIndex == 1) {
      node.setMidPair(newPair);
      return true;
    } else if (keyIndex == 2) {
      node.setRightPair(newPair);
      return true;
    }

    return false;
  }
}
