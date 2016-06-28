package beans;

import java.util.List;
import java.util.Comparator;

public class VertexTree<Type>{
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
	public void add(Type elem,int id) {
		Node<Type> neu = new Node<Type>(elem,id);
		if (root == null) {			// Fall 1: Baum ist leer
			root = neu;
			return;
		}
		Node<Type> node = root;				// Fall 2: Baum ist nicht leer
		while (true) {
			int vgl = node.compareTo(neu);// Add Compare
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
	 * 
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
		int vgl = compare(current.getValue(),needle);// ADD COMPARE Method!!!compare(needle, current.getValue());
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
	
	private int compare(Type val, Type needle) {
		//is this required?
		String cmp1 = val.toString();
		String cmp2 = needle.toString();
		return cmp1.compareTo(cmp2);
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
		System.out.println(prefix + current.getValue().toString());
		printTree(current.getLeftChild(), prefix + " L ");
		printTree(current.getRightChild(), prefix + " R ");
	}

}
