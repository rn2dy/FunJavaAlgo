import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class TwoThreeTreePanel extends JPanel{
	
	//the twoThree tree
	private TwoThreeTree tree;
	//labels
	private JLabel jlbInput = new JLabel("Input:");
	//all buttons
	private JButton jbtInsert = new JButton("Insert");
	private JButton jbtDelete = new JButton("Delete");
	private JButton jbtSearch = new JButton("Search");
	private JButton jbtSucc = new JButton("Successor");
	private JButton jbtMin = new JButton("Min");
	private JButton jbtSort = new JButton("Sort");
	private JButton jbtOutput = new JButton("Output");
	
	private JButton jbtExit = new JButton("Exit");
	
	//the text field associated with the buttons
	private JTextField jtfInput = new JTextField(5);
	private JTextArea jta = new JTextArea();
	
	//Panels
	private PaintTree jspPanel = new PaintTree();
	private JPanel jplButton0 = new JPanel();
	private JPanel jplButton1 = new JPanel(new GridLayout(2,3,5,5));
	private JPanel jplButton2 = new JPanel(new GridLayout(2,2,5,5));
	private JPanel jplText = new JPanel(new BorderLayout());
	
	public TwoThreeTreePanel(){
		tree = new TwoThreeTree();
		//button area0
		jplButton0.setLayout(new FlowLayout(FlowLayout.LEFT,10,0));
		jplButton0.add(jlbInput);
		jtfInput.setBackground(Color.lightGray);
		jplButton0.add(jtfInput);
		//button area1
		jplButton1.setBorder(new TitledBorder(""));
		jplButton1.add(jbtInsert);
		jplButton1.add(jbtDelete);
		jplButton1.add(jbtSearch);
		jplButton1.add(jbtSucc);
		
		//button area2
		jplButton2.setBorder(new TitledBorder(""));
		jplButton2.add(jbtMin);
		jplButton2.add(jbtSort);
		jplButton2.add(jbtOutput);
		jplButton2.add(jbtExit);
		//hold control area
		JPanel p = new JPanel(new BorderLayout(5,10));
		p.setBorder(new TitledBorder("Control Panel"));
		p.add(jplButton0, BorderLayout.NORTH);
		p.add(jplButton1, BorderLayout.CENTER);
		p.add(jplButton2, BorderLayout.EAST);
		
		JPanel p1 = new JPanel(new BorderLayout());
		jspPanel.setBorder(new TitledBorder("Display"));
		p1.add(jspPanel, BorderLayout.CENTER);
		p1.add(p, BorderLayout.SOUTH);
		
		this.setLayout(new BorderLayout());
		add(p1, BorderLayout.CENTER);
		jplText.setBorder(new TitledBorder("Txt Output"));
		jta.setBackground(Color.lightGray);
		jta.setPreferredSize(new Dimension(100,440));
		jplText.add(jta, BorderLayout.CENTER);
		add(jplText, BorderLayout.EAST);
		
		//add listeners
		jbtInsert.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		int key = Integer.parseInt(jtfInput.getText());
	    		if (key < 0)
	    			JOptionPane.showMessageDialog(null, "Input must be a positive integer!");
	    		else{
	    			tree.insert(key);
	    			jspPanel.repaint();
	    		}
	    	}
	    });
		
		jbtDelete.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		int key = Integer.parseInt(jtfInput.getText());
	    		if (key < 0)
	    			JOptionPane.showMessageDialog(null, "Input must be a positive integer!");
	    		else if (tree.search(key) == null){
	    			JOptionPane.showMessageDialog(null, 
	    					key + " is not in the tree!");
	    		}
	    		else{
	    			tree.delete(key);
	    			jspPanel.repaint();
	    		}
	    		
	    	}
	    });
		jbtSearch.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		int key = Integer.parseInt(jtfInput.getText());
	    		if (key < 0)
	    			JOptionPane.showMessageDialog(null, "Input must be a positive integer!");
	    		else{ 
	    			TreeNode node = tree.search(key);
	    			if (node == null){
	    				JOptionPane.showMessageDialog(null, 
	    						key + " is not in the tree!");
	    			}
	    			else
	    				jta.setText("Found the node : \n" + node + "\n");
	    		}
	    	}
	    });
		jbtSucc.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		int key = Integer.parseInt(jtfInput.getText());
	    		if (key < 0)
	    			JOptionPane.showMessageDialog(null, "Input must be a positive integer!");
	    		else{ 
	    			TreeNode node = tree.successor2(key);
	            	if (node != null ){
	            		if (tree.getSpecial())
	            			jta.setText("The successor of " + key + 
	            					"\n" + node.getKey2()+"\n");
	            		else
	            			jta.setText("The successor of " + key + 
	            					"\n" + node.getKey1()+"\n");
	            		tree.setSpecial(false);
	            	}
	    		}
	    	}
	    });
		jbtMin.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		if (tree.getRoot() != null)
	    			jta.setText("The minimum key \n" + 
		  	    			tree.minimum()+"\n");
	    		else
	    			JOptionPane.showMessageDialog(null, "Tree is empty!");
	    	}
	    });
		jbtSort.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		if (tree.getRoot() != null){
	    			tree.sort();
	    			jta.setText(tree.getSort() + "\n");
	    		}
	    		else
	    			JOptionPane.showMessageDialog(null, "Tree is empty!");
	    	}
	    });
		jbtOutput.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		if (tree.getRoot() != null){
	    			tree.output();
	    			jta.setText(tree.getOutput() + "\n");
	    		}
	    		else
	    			JOptionPane.showMessageDialog(null, "Tree is empty!");
	    	}
	    });
		jbtExit.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		System.exit(-1);
	    	}
	    });
	}
	
	//inner class paintTree for displaying a tree on a scroll panel
	class PaintTree extends JPanel{
		private int radius = 20; //tree node radius
		private int vGap = 50;//gap between two levels in the tree
		
		protected void paintComponent(Graphics g){
			super.paintComponent(g);
			
			if (tree.getRoot() != null){
				//draw the tree recursively
				drawTree(g, tree.getRoot(), getWidth()/2, 30, getWidth()/4);
			}
		}
		private void drawTree(Graphics g, TreeNode root, int x, int y, int hGap){
			//draw the root---------
			g.drawOval(x-radius, y-radius, 2*radius, 2*radius);
			if (root.getDegree() == 2){
				if (root.getKey1() < 10)
					g.drawString(root.getKey1() + "", x-3, y+4);
				else
					g.drawString(root.getKey1() + "", x-6, y+4);
			}
			else{
				if (root.getKey1() < 10)
					g.drawString(root.getKey1() + " | " + root.getKey2(), x-15, y+4);
				else
					g.drawString(root.getKey1() + " | " + root.getKey2(), x-18, y+4);
			}
				//----------------------
			if(root.getLeftChild() != null){
				//draw a line to the left node
				connectLeftChild(g, x - hGap, y + vGap, x, y);
				//draw the left subtree recursively
				drawTree(g, root.getLeftChild(), x-hGap, y+vGap, hGap/2);
			}
			if(root.getMidChild() != null){
				//draw a line to the left node
				connectMidChild(g, y + vGap, x, y);
				//draw the left subtree recursively
				drawTree(g, root.getMidChild(), x, y+vGap, hGap/2);
			}
			if(root.getRightChild() != null){
				//draw a line to the left node
				connectRightChild(g, x + hGap, y + vGap, x, y);
				//draw the left subtree recursively
				drawTree(g, root.getRightChild(), x+hGap, y+vGap, hGap/2);
			}
		}
		private void connectLeftChild(Graphics g, int x1, int y1, int x2, int y2){
			double d = Math.sqrt(vGap * vGap + (x2 - x1) * (x2 - x1));
		    int x11 = (int)(x1 + radius * (x2 - x1) / d);
		    int y11 = (int)(y1 - radius * vGap / d);
		    int x21 = (int)(x2 - radius * (x2 - x1) / d);
		    int y21 = (int)(y2 + radius * vGap / d);
		    g.drawLine(x11, y11, x21, y21);
		}
		private void connectMidChild(Graphics g, int y1, int x2, int y2) {
			int x11 = x2;
			int y11 = y1 - radius;
			int x21 = x2;
			int y21 = y2 + radius;
		    g.drawLine(x11, y11, x21, y21);
		}
		private void connectRightChild(Graphics g, int x1, int y1, int x2, int y2) {
		    double d = Math.sqrt(vGap * vGap + (x2 - x1) * (x2 - x1));
		    int x11 = (int)(x1 - radius * (x1 - x2) / d);
		    int y11 = (int)(y1 - radius * vGap / d);
		    int x21 = (int)(x2 + radius * (x1 - x2) / d);
		    int y21 = (int)(y2 + radius * vGap / d);
		    g.drawLine(x11, y11, x21, y21);
		}
	}
}
