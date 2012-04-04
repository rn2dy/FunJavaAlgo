
public class TreeNode {
	//fields with key1 < key2 
	private int key1;
	private int key2;
	private TreeNode parent;
	private TreeNode leftChildX;
	private TreeNode leftChild;
	private TreeNode rightChild;
	private TreeNode midChild;
	//for identify the node type with potential value of 2 or 3
	private int degree;
	//constructor 
	public TreeNode(int kl, int kr){
		key1 = kl;
		key2 = kr;
		init();
	}
	//for convenience
	public void init(){
		parent = null;
		leftChildX = null;
		leftChild = null;
		rightChild = null;
		midChild = null;
		degree = 2;
	}
	
	//set method
	public void setKey1(int k1){
		key1 = k1;
	}
	public void setKey2(int k2){
		key2 = k2;
	}
	public void setDegree(int d){
		degree = d;
	}
	public void setParent(TreeNode p){
		parent = p;
	}
	public void setLeftChildX(TreeNode x){
		leftChildX = x;
	}
	public void setLeftChild(TreeNode l){
		leftChild = l;
	}
	public void setRightChild(TreeNode r){
		rightChild = r;
	}
	public void setMidChild(TreeNode m){
		midChild = m;
	}
	//get method
	public int getKey1(){
		return key1;
	}
	public int getKey2(){
		return key2;
	}
	public int getDegree(){
		return degree;
	}
	public TreeNode getParent(){
		return parent;
	}
	public TreeNode getLeftChildX(){
		return leftChildX;
	}
	public TreeNode getLeftChild(){
		return leftChild;
	}
	public TreeNode getRightChild(){
		return rightChild;
	}
	public TreeNode getMidChild(){
		return midChild;
	}
	//to string
	public String toString(){
		if (key2 == Integer.MAX_VALUE)
			return "(" + key1 + ")";
		else
			return "(" + key1 + "," + key2 + ")";
 	}
}
