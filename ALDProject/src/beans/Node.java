package beans;

public class Node<Type> {
	protected Node<Type> leftChild;
	protected Node<Type> rightChild;
	protected Node<Type> parent;
	protected final Type value;
	
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

	Node(Type value){
		this.value = value;
		leftChild = null;
		rightChild = null;
		
	}
	
	

}
