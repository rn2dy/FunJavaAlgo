
public class TwoThreeTree {
	TreeNode root = null;
	final int EMPTYKEY = Integer.MAX_VALUE;
	//the successor's special case
	public boolean special = false;
	//for storing the sorted output
	private String str = "";
	private String str2 = "";
	//Insertion
	public boolean insert(int key){
		//search of the position where to insert,
		TreeNode currNode = root;
		while (currNode != null){// try while(currNode != null && currNode.getLeftChild() != null)
			if(currNode.getKey1() == key || currNode.getKey2() == key){
				return false;
			}
			else if(key < currNode.getKey1()){
				if (currNode.getLeftChild() == null)
					break;
				else
					currNode = currNode.getLeftChild();
			}
			else if(key > currNode.getKey2() ){
				if (currNode.getRightChild() == null)
					break;
				else
					currNode = currNode.getRightChild();
			}
			else if(currNode.getDegree() == 2){
				if (currNode.getRightChild() == null)
					break;
				else
					currNode = currNode.getRightChild();
			}
			else{
				if (currNode.getMidChild() == null)
					break;
				else
					currNode = currNode.getMidChild();
			}
		}
		//case 1
		//leaf == null means root is null
		//add new root to the tree
		if (currNode == null && currNode == root){
			TreeNode newNode = new TreeNode(key, EMPTYKEY);
			root = newNode;
			return true;
		}
		//case 2
		//leaf degree is 2, means only one data item
		//add the item to the node
		else if(currNode.getDegree() == 2){
			if (key > currNode.getKey1()){
				currNode.setKey2(key);
			}
			else{
				currNode.setKey2(currNode.getKey1());
				currNode.setKey1(key);
			}
			currNode.setDegree(3);
			return true;
		}
		//case 3
		//leaf degree is 3
		//split(leaf, item)
		else{
			split(currNode, key);
			return true;
		}
	}
	//split method
	public void split(TreeNode node, int key){
		TreeNode parent;
		//
		if (node == root){
			parent = new TreeNode(-1,EMPTYKEY);
		}
		else{
			parent = node.getParent();
		}
		
		//split the node into two nodes n1 and n2
		//n1 is the smallest value of the node
		TreeNode n1 = new TreeNode(-1,EMPTYKEY);
		//n2 is the largest value of the node
		TreeNode n2 = new TreeNode(-1,EMPTYKEY);
		//set n1 n2 parent
		n1.setParent(parent);
		n2.setParent(parent);
		int x;
		if (key < node.getKey1()){
			x = node.getKey1();
			n1.setKey1(key);
			n2.setKey1(node.getKey2());
		}
		else if(key > node.getKey2()){
			x = node.getKey2();
			n1.setKey1(node.getKey1());
			n2.setKey1(key);
		}
		else{
			x = key;
			n1.setKey1(node.getKey1());
			n2.setKey1(node.getKey2());
		}
		//if node is not leaf node
		if (node.getLeftChild() != null){
			//n1 becomes the parent of node's two leftmost children
			n1.setLeftChild(node.getLeftChildX());
				node.getLeftChildX().setParent(n1);
			n1.setRightChild(node.getLeftChild());
				node.getLeftChild().setParent(n1);
				
			//n2 becomes the parent of node's two rightmost children
			n2.setLeftChild(node.getMidChild());
				node.getMidChild().setParent(n2);
			n2.setRightChild(node.getRightChild());
				node.getRightChild().setParent(n2);
		}
		//if add the x would cause the split process
		if (parent.getDegree() == 3){
			if(parent.getMidChild() == node){
				parent.setLeftChildX(parent.getLeftChild());
				parent.setLeftChild(n1);
				parent.setMidChild(n2);
			}
			else if(parent.getRightChild() == node){
				parent.setLeftChildX(parent.getLeftChild());
				parent.setLeftChild(parent.getMidChild());
				parent.setMidChild(n1);
				parent.setRightChild(n2);
			}
			else{
				parent.setLeftChildX(n1);
				parent.setLeftChild(n2);
			}
			//split again
			split(parent, x);
		}
		
		//if node's parent's degree is 2
		else{
			//
			if (node == root){
				parent.setKey1(x);
				parent.setLeftChild(n1);
				parent.setRightChild(n2);
				root = parent;
			}
			//add x to the node's parents if parent is not root with degree 2
			else if (parent.getLeftChild() == node){
				parent.setKey2(parent.getKey1());
				parent.setKey1(x);
				parent.setLeftChild(n1);
				parent.setMidChild(n2);
				parent.setDegree(3);
			}
			else{
				parent.setKey2(x);
				parent.setMidChild(n1);
				parent.setRightChild(n2);
				parent.setDegree(3);
			}
		}
	}
	//delete a specific key
	public boolean delete(int key){
		TreeNode currNode = root;
		while (currNode != null){
			if(currNode.getKey1() == key || currNode.getKey2() == key){
				break;
			}
			else if(key < currNode.getKey1()){
				currNode = currNode.getLeftChild();
			}
			else if(key > currNode.getKey2() ){
				currNode = currNode.getRightChild();
			}
			else if(currNode.getDegree() == 2){
				currNode = currNode.getRightChild();
			}
			else	
				currNode = currNode.getMidChild();
		}
		TreeNode leafNode;
		if (currNode != null){
			//if the node is not a leaf node
			if (currNode.getLeftChild() != null){
				//find the successor
				TreeNode succ = successor(key, currNode);
				//swap currNode with its successor
				int temp = succ.getKey1();
				if (currNode.getKey1() == key){
					succ.setKey1(currNode.getKey1());
					currNode.setKey1(temp);
					leafNode = succ;
				}
				else{
					succ.setKey1(currNode.getKey2());
					currNode.setKey2(temp);
					leafNode = succ;
				}
			}
			else
				leafNode = currNode;
			//leafNode contains no element now
			if (leafNode.getDegree() == 2){
				if (leafNode == root){
					root = null;
				}
				else
					fix(leafNode);
			}
			//leafNode contains two elements
			//key2 is the one to be delete
			else if (leafNode.getKey2() == key){
				leafNode.setKey2(EMPTYKEY);
				leafNode.setDegree(2);
			}
			//leafNode contains two elements
			//key1 is to be deleted
			else{
				leafNode.setKey1(leafNode.getKey2());
				leafNode.setKey2(EMPTYKEY);
				leafNode.setDegree(2);
			}
			return true;
		}
		else
			return false;
	}
	
	//fix the violation by either removing the root,
	//redistributing values,
	//or merging nodes.
	private void fix(TreeNode node){
		if (node == root){
			//remove the root
			//set new root pointer
			TreeNode newPointer = root.getLeftChild() != null ? root.getLeftChild(): root.getRightChild();
			root = newPointer;
		}
		//node is not root
		else{
			TreeNode p = node.getParent();
			
			//if any of the siblings of node has two items
			
			// if parent is a 3 node
			if (p.getDegree() == 3){
				//for convenience
				TreeNode left = p.getLeftChild();
				TreeNode mid = p.getMidChild();
				TreeNode right = p.getRightChild();
				//if any of the siblings is a 3 degree node
				if (left.getDegree() == 3 || 
						mid.getDegree() == 3 || 
							right.getDegree() == 3){
					//redistribute the element.
					//if  node is leftChild
					if (node == left){
						//if middle child is 3
						if (mid.getDegree() == 3){
							node.setKey1(p.getKey1());
							p.setKey1(mid.getKey1());
							mid.setKey1(mid.getKey2());
							mid.setKey2(EMPTYKEY);
							
							mid.setDegree(2);
							
							//redistribute the children if node is not leaf node
							if (mid.getLeftChild() != null){
								if (node.getRightChild() != null){
									node.setLeftChild(node.getRightChild());
								}
								node.setRightChild(mid.getLeftChild());
								mid.setLeftChild(mid.getMidChild());
								mid.setMidChild(null);
							}
						}
						else if (right.getDegree() == 3){
							node.setKey1(p.getKey1());
							p.setKey1(mid.getKey1());
							mid.setKey1(p.getKey2());
							p.setKey2(right.getKey1());
							right.setKey1(right.getKey2());
							right.setKey2(EMPTYKEY);
							
							right.setDegree(2);
							
							//redistribute the children if node is not a leaf node
							if (right.getLeftChild() != null){
								if (node.getRightChild() != null){
									node.setLeftChild(node.getRightChild());
								}
								node.setRightChild(mid.getLeftChild());
								mid.setLeftChild(mid.getRightChild());
								mid.setRightChild(right.getLeftChild());
								right.setLeftChild(right.getMidChild());
								right.setMidChild(null);
							}
						}
					}//if node is leftChild
					
					//if node is middle child
					else if (node == mid){
						if (right.getDegree() == 3){
							node.setKey1(p.getKey2());
							p.setKey2(right.getKey1());
							right.setKey1(right.getKey2());
							right.setKey2(EMPTYKEY);
							
							right.setDegree(2);
							
							if (right.getLeftChild() != null){
								if (node.getRightChild() != null){
									node.setLeftChild(node.getRightChild());
								}
								node.setRightChild(right.getLeftChild());
								right.setLeftChild(right.getMidChild());
								right.setMidChild(null);
							}
						}
						else if(left.getDegree() == 3){
							node.setKey1(p.getKey1());
							p.setKey1(left.getKey2());
							left.setKey2(EMPTYKEY);
							
							left.setDegree(2);
							
							if (left.getLeftChild() != null){
								if (node.getLeftChild() != null){
									node.setRightChild(node.getLeftChild());
								}
								node.setLeftChild(left.getRightChild());
								left.setRightChild(left.getMidChild());
								left.setMidChild(null);
							}
						}
					}//if  node is middle child
					
					//else if node is a right child
					else if(node == right){
						if (mid.getDegree() == 3){
							node.setKey1(p.getKey2());
							p.setKey2(mid.getKey2());
							mid.setKey2(EMPTYKEY);
							
							mid.setDegree(2);
							
							if (mid.getLeftChild() != null){
								if (node.getLeftChild() != null){
									node.setRightChild(node.getLeftChild());
								}
								node.setLeftChild(mid.getRightChild());
								mid.setRightChild(mid.getMidChild());
								mid.setMidChild(null);
							}
						}
						else if (left.getDegree() == 3){
							node.setKey1(p.getKey2());
							p.setKey2(mid.getKey1());
							mid.setKey1(p.getKey1());
							p.setKey1(left.getKey2());
							left.setKey2(EMPTYKEY);

							left.setDegree(2);
							
							if (left.getLeftChild() != null){
								if (node.getLeftChild() != null){
									node.setRightChild(node.getLeftChild());
								}
								node.setLeftChild(mid.getRightChild());
								mid.setRightChild(mid.getLeftChild());
								mid.setLeftChild(left.getRightChild());
								left.setRightChild(left.getMidChild());
								left.setMidChild(null);
							}
						}
					}//else if node is a right child
				}//if any of the siblings has two elements
				
				//if none of the siblings has two elements
				else{//merge nodes
					/*
					 * choose an adjacent sibling s of n(left first principle)
					 * bring he appropriate item down form p into s
					 */
					if (node == left){
						node.setKey1(p.getKey1());
						node.setKey2(mid.getKey1());
						node.setDegree(3);
						p.setKey1(p.getKey2());
						p.setKey2(EMPTYKEY);
						p.setMidChild(null);
						p.setDegree(2);
						if (right.getLeftChild() != null){
							if (node.getRightChild() != null){
								node.setLeftChild(node.getRightChild());
							}
							node.setMidChild(mid.getLeftChild());
							node.setRightChild(mid.getRightChild());
						}
					}
					else if (node == mid){
						left.setKey2(p.getKey1());
						left.setDegree(3);
						p.setKey1(p.getKey2());
						p.setKey2(EMPTYKEY);
						p.setMidChild(null);
						p.setDegree(2);
						if (left.getLeftChild() != null){
							TreeNode r = (node.getRightChild() != null ? 
								node.getRightChild(): node.getLeftChild());
							left.setMidChild(left.getRightChild());
							left.setRightChild(r);
						}
					}
					else{
						left.setKey2(p.getKey1());
						left.setDegree(3);
						right.setKey1(p.getKey2());
						p.setKey1(mid.getKey1());
						p.setKey2(EMPTYKEY);
						p.setMidChild(null);
						p.setDegree(2);
						if (left.getLeftChild() != null){
							if (right.getLeftChild() != null){
								right.setRightChild(right.getLeftChild());
							}
							left.setMidChild(left.getRightChild());
							left.setRightChild(mid.getLeftChild());
							right.setLeftChild(mid.getRightChild());
						}
					}
				}//if none of the siblings has two elements
				
			}// if parent is a 3 node
//-----------------------------------------------------------
			//if parent is a 2 degree node
			else{
				TreeNode left = p.getLeftChild();
				TreeNode right = p.getRightChild();
				//if any of the siblings has two elements
				if (left.getDegree() == 3 || right.getDegree() == 3){
					if (left.getDegree() == 3){
						node.setKey1(p.getKey1());
						p.setKey1(left.getKey2());
						left.setKey2(EMPTYKEY);
						left.setDegree(2);
						if (left.getLeftChild() != null){
							if (node.getLeftChild() != null){
								node.setRightChild(node.getLeftChild());
							}
							node.setLeftChild(left.getRightChild());
							left.setRightChild(left.getMidChild());
							left.setMidChild(null);
						}
					}//left node has two elements
					else if (right.getDegree() == 3){
						node.setKey1(p.getKey1());
						p.setKey1(right.getKey1());
						right.setKey1(right.getKey2());
						right.setKey2(EMPTYKEY);
						right.setDegree(2);
						if (right.getLeftChild() != null){
							if (node.getRightChild() != null){
								node.setLeftChild(node.getRightChild());
							}
							node.setRightChild(right.getLeftChild());
							right.setLeftChild(right.getMidChild());
							right.setMidChild(null);
						}
					}//right node has two elements
				}//if any of the siblings has two elements
				
				//if none of the siblings has two elements
				else{//merge nodes
					if (left == node){
						node.setKey1(p.getKey1());
						node.setKey2(right.getKey1());
						p.setRightChild(null);
						node.setDegree(3);
						if (right.getLeftChild() != null){
							if (node.getRightChild() != null ){
								node.setLeftChild(node.getRightChild());
							}
							node.setMidChild(right.getLeftChild());
							node.setRightChild(right.getRightChild());
						}
					}
					else{
						left.setKey2(p.getKey1());
						left.setDegree(3);
						p.setRightChild(null);
						if (left.getLeftChild() != null){
							TreeNode r = (node.getRightChild() != null ? 
									node.getRightChild(): node.getLeftChild());
							left.setMidChild(left.getRightChild());
							left.setRightChild(r);
						}
					}
					fix(p);
				}//if none of the siblings has two elements
			}//if parent is a two degree node
			
		}//node is not root
	}
	
	//search a specific key
	public TreeNode search(int key){
		TreeNode currNode = root;
		while (currNode != null){
			if(currNode.getKey1() == key || currNode.getKey2() == key){
				break;
			}
			else if(key < currNode.getKey1()){
				currNode = currNode.getLeftChild();
			}
			else if(key > currNode.getKey2() ){
				currNode = currNode.getRightChild();
			}
			else if(currNode.getDegree() == 2){
				currNode = currNode.getRightChild();
			}
			else	
				currNode = currNode.getMidChild();
		}
		if (currNode == null){
			return null;
		}
		else
			return currNode;
	}
	
	//find the successor
	
	public TreeNode successor(int key, TreeNode currNode){
		//if node is not a leaf node
		if (currNode.getLeftChild() != null){
			if (currNode.getDegree() == 2){
				currNode = currNode.getRightChild();
			}
			else if (currNode.getKey1() == key){
				currNode = currNode.getMidChild();
			}
			else{
				currNode = currNode.getRightChild();
			}
			while (currNode.getLeftChild() != null){
				currNode = currNode.getLeftChild();
			}
			return currNode;
		}
		//if node is a leaf node
		else{
			//if node is the max key it will not have a successor
			if (currNode.getDegree() == 3 && currNode.getKey1() == key){
				this.setSpecial(true);
				return currNode;
			}
			boolean found = false;
			if (currNode.getParent() != null){
				
				if (currNode.getParent().getLeftChild() == currNode){
					found = true;
					currNode = currNode.getParent();
				}
				else if (currNode.getParent().getRightChild() == currNode){
					currNode = currNode.getParent();
					System.out.println("flag0:" + currNode);
					while(currNode.getParent() != null){
						if (currNode.getParent().getLeftChild() == currNode){
							currNode = currNode.getParent();
							found = true;
							break;
						}
						else
							currNode = currNode.getParent();
					}
				}
				else if (currNode.getParent().getMidChild() == currNode){
					found = true;
					this.setSpecial(true);
					currNode = currNode.getParent();
					System.out.println("flag1:" + currNode);
				}
			}
			if (found == true){
				System.out.println(special);
				return currNode;
			}
			else{
				System.out.println("This node does not have a successor!");
				return null;
			}
		}
	}
	public TreeNode successor2(int key){
		TreeNode currNode = root;
		boolean found = false;
		while (currNode != null){
			if(currNode.getKey1() == key || currNode.getKey2() == key){
				found = true;
				break;
			}
			else if(key < currNode.getKey1()){
				if (currNode.getLeftChild() == null)
					break;
				else
					currNode = currNode.getLeftChild();
			}
			else if(key > currNode.getKey2() ){
				if (currNode.getRightChild() == null)
					break;
				else
					currNode = currNode.getRightChild();
			}
			else if(currNode.getDegree() == 2){
				if (currNode.getRightChild() == null)
					break;
				else
					currNode = currNode.getRightChild();
			}
			else{
				if (currNode.getMidChild() == null)
					break;
				else
					currNode = currNode.getMidChild();
			}
		}
		if ( currNode!= null && found){
			//System.out.println(currNode);
			return successor(key, currNode);
		}
		else
			return null;
	}
	public TreeNode minimum(){
		if (root != null){
			TreeNode node = root;
			while (node.getLeftChild() != null){
				node = node.getLeftChild();
			}
			return node;
		}
		else
			return null;
	}
	
	public void sort(){
		inorder(root);
	}
	private void inorder(TreeNode root){
		if (root == null){
			return;
		}
		//---------------------
		inorder(root.getLeftChild());
		if (root.getDegree() == 3 && root.getLeftChild() == null){
			str += root.getKey1() + "\n" + root.getKey2() + "\n";
		}
		else{
			str += root.getKey1() + "\n";
		}
		//---------------------
		if ( root.getMidChild() != null){
			inorder(root.getMidChild());
			if (root.getDegree() == 3 && root.getLeftChild() == null){
				str += root.getKey1() + "\n" + root.getKey2() + "\n";
			}
			else
				str += root.getKey2() + "\n";
		}
		inorder(root.getRightChild());
	}
	//get the in order walk out put
	public String getSort(){
		return str;
	}
	//output
	public void output(){
		//use a queue
		if (root != null){
			LQueue queue = new LQueue();
			queue.enqueue(root);
			while(true){
				TreeNode node = (TreeNode)queue.dequeue();
				if (node.getLeftChild() != null && node.getDegree() == 3){
					str2 += node + " /" + node.getLeftChild() 
							+ "," + node.getMidChild() + "," + node.getRightChild() + "\n";
					queue.enqueue(node.getLeftChild());
					queue.enqueue(node.getMidChild());
					queue.enqueue(node.getRightChild());
				}
				else if (node.getLeftChild() != null && node.getDegree() == 2){
					str2 += node + " /" + node.getLeftChild()+ "," + node.getRightChild() + "\n";
					queue.enqueue(node.getLeftChild());
					queue.enqueue(node.getRightChild());
				}
				else if (node.getLeftChild() == null && node == root){
						str2 += node + "\n";
				}
				if (queue.isEmpty()){
					break;
				}
			}
		}
	}
	public String getOutput(){
		return str2;
	}
	public void setSpecial(boolean b){
		special = b;
	}
	public boolean getSpecial(){
		return special;
	}
	public void setRoot(TreeNode r){
		root = r;
	}
	public TreeNode getRoot(){
		return root;
	}
}
