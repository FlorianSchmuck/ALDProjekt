package beans;

import java.util.List;

public abstract class VertexTree<Type> {

	//private City[] vertextTreeArray;
	private int count = 0;
	private int index = 0 ;
	private int capicity;
	private Node<Type> root;
	
	public Node<Type> getRoot() {
		return root;
	}
	public VertexTree(){
		root = null;	
	}
	/**
	 * Neues Element hinzufügen
	 * @param elem Hinzuzufügendes Element
	 */
	public void add(Type elem) {
		Node<Type> neu = new Node<Type>(elem);
		if (root == null) {			// Fall 1: Baum ist leer
			root = neu;
			return;
		}
		Node<Type> node = root;				// Fall 2: Baum ist nicht leer
		while (true) {
			int vgl = 0;//compare(elem, node.getValue()); Add Compare
			if (vgl < 0) {					// kleiner
				if (node.getLeftChild() == null) {
					node.setLeftChild(neu);
					neu.setParent(node);
					return;
				}
				node = node.getLeftChild();
			}
			else if (vgl > 0) {				// größer
				if (node.getRightChild() == null) {
					node.setRightChild(neu);
					neu.setParent(node);
					return;
				}
				node = node.getRightChild();
			}
			else {							// gleich (nicht nochmal einfügen)
				return;
			}
		}
	}

	/**
	 * Element im Baum finden (startet bei Root-Node)
	 * @param needle Zu suchendes Element
	 * @return Knoten des Elements
	 */
	public Node<Type> find(Type needle) {
		return find(root, needle);
	}
	
	/**
	 * Element in Teilbaum finden (startet bei current-Node)
	 * @param current Startknoten
	 * @param needle  Zu suchendes Element
	 * @return Knoten des Elements
	 */
	public Node<Type> find(Node<Type> current, Type needle) {
		if (current == null) {
			return null;
		}
		int vgl = 0;// ADD COMPARE Method!!!compare(needle, current.getValue());
		if (vgl == 0) {		// Gefunden
			return current;
		}
		else if (vgl < 0) {	// Links
			return find(current.getLeftChild(), needle);
		}
		else {				// Rechts
			return find(current.getRightChild(), needle);
		}
	}
	
	/**
	 * Funktion zur Ausgabe des gesamten Baums.
	 */
	public void printTree() {
		printTree(root, "");
	}
	
	/**
	 * Funktion zur Ausgabe des Baums unterhalb eines Knotens
	 * @param current Knoten, dessen Teilbaum ausgegeben werden soll
	 * @param prefix  Zur Einrückung
	 */
	public void printTree(Node<Type> current, String prefix) {
		if (current == null) {
			return;
		}
		System.out.println(prefix + current.getValue());
		printTree(current.getLeftChild(), prefix + " L ");
		printTree(current.getRightChild(), prefix + " R ");
	}

}
