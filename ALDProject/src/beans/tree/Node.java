package beans.tree;

public class Node<Type> implements Comparable<Node<Type>>{
	protected Node<Type> leftChild;
	protected Node<Type> rightChild;
	protected Node<Type> parent;
	protected final Type value;
	protected int id;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Type getValue() {
		return value;
	}

	public Node<Type> getLeftChild() {
		return leftChild;
	}

	public void setLeftChild(Node<Type> leftChild) {
		this.leftChild = leftChild;
	}

	public Node<Type> getRightChild() {
		return rightChild;
	}

	public void setRightChild(Node<Type> rightChild) {
		this.rightChild = rightChild;
	}

	public Node<Type> getParent() {
		return parent;
	}

	public void setParent(Node<Type> parent) {
		this.parent = parent;
	}

	public Node(Type value,int idnr){
		this.value = value;
		leftChild = null;
		rightChild = null;
		this.id = idnr;
		
	}

	@Override
	public int compareTo(Node<Type> o) {
		// return getValue().compareTo(o.getValue());
		String cmp1 = getValue().toString();
		String cmp2 = o.getValue().toString();
		return cmp1.compareTo(cmp2);
	}

	@Override
	public String toString() {
		return "Node [value=" + value + "]";
	}


	
	

}
