import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import javax.swing.*;
import javax.swing.border.TitledBorder;
public class ShortestPathPanel extends JPanel{
	ShortestPath sp = new ShortestPath();
	//labels1
	private JLabel jlbInput1 = new JLabel("Source:");
	private JLabel jlbInput2 = new JLabel("Destination:");
	
	//the text field
	private JTextField jtfInput1 = new JTextField(3);
	private JTextField jtfInput2 = new JTextField(3);
	//text area
	private JTextArea jta = new JTextArea();
	//all buttons
	private JButton jbtB_Ford = new JButton("B.Ford");
	private JButton jbtDijk = new JButton("Dijkstra");
	
	private JButton jbtInput = new JButton("Run");
	
	private JButton jbtExit = new JButton("Exit");
	//panels
	private PaintRoute jspPanel = new PaintRoute();
	private JPanel jplControl = new JPanel(new BorderLayout(5,5));
	private JPanel jplButton1 = new JPanel(new GridLayout(2,2,5,5));
	private JPanel jplButton2 = new JPanel(new GridLayout(2,2,5,5));
	
	private JPanel jplText = new JPanel(new BorderLayout());
	
	//some important variable
	boolean initialized1 = false;
	boolean initialized2 = false;
	boolean found = false;
	//messages
	String message1 = "Input Source and\nDest then choose\nthe alg.";
	String message2 = "Click Execute\nbutton to run\nthe program";
	public ShortestPathPanel(){
		
		//input area
		jplButton1.add(jlbInput1);
		jplButton1.add(jtfInput1);
		jplButton1.add(jlbInput2);
		jplButton1.add(jtfInput2);
		jplButton1.setBorder(new TitledBorder("Input"));
		
		//button area
		jplButton2.add(jbtB_Ford);
		jplButton2.add(jbtDijk);
		jbtInput.setBackground(Color.BLUE);
		jbtInput.setForeground(Color.WHITE);
		jplButton2.add(jbtInput);
		jplButton2.add(jbtExit);
		jplButton2.setBorder(new TitledBorder("Control"));
		
		//text area
		jplText.add(jta, BorderLayout.CENTER);
		jta.setPreferredSize(new Dimension(100,70));
		jta.setText(message1);
		jplText.setBorder(new TitledBorder("Txt Area"));
		
		jplControl.add(jplButton1, BorderLayout.WEST);
		jplControl.add(jplButton2, BorderLayout.CENTER);
		jplControl.add(jplText, BorderLayout.EAST);
		jplControl.setBorder(new TitledBorder(""));
		jspPanel.setBorder(new TitledBorder("Display"));
		jspPanel.setBackground(Color.BLACK);
		this.setLayout(new BorderLayout());
		this.add(jspPanel, BorderLayout.CENTER);
		this.add(jplControl, BorderLayout.SOUTH);
		
		jbtB_Ford.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		int key1 = Integer.parseInt(jtfInput1.getText())-1;
	    		int key2 = Integer.parseInt(jtfInput2.getText())-1;
	    		
	    		if (key1 < 0 || key2 < 0 || 
	    				key1 > 6 || key2 > 6 || 
	    								key1 == key2)
	    			JOptionPane.showMessageDialog(null, 
	    					"Input must be diffirent positive integers at most 6!");
	    		else{
	    			sp.setSource(key1);
	    			//System.out.println("Source isA:" + key1);
	    			sp.setDest(key2);
	    			try {
						sp.initialize1();
						initialized1 = true;
						initialized2 = false;
						jta.setText(message2);
						jspPanel.repaint();
					} catch (IOException e1) {}
					
	    		}
	    		
	    	}
	    });
		
		jbtDijk.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		int key1 = Integer.parseInt(jtfInput1.getText())-1;
	    		int key2 = Integer.parseInt(jtfInput2.getText())-1;
	    		
	    		if (key1 < 0 || key2 < 0 || 
	    				key1 > 6 || key2 > 6 || 
	    								key1 == key2)
	    			JOptionPane.showMessageDialog(null, 
	    					"Input must be diffirent positive integers at most 6!");
	    		else{
	    			sp.setSource(key1);
	    			//System.out.println("Source isB:" + key1);
	    			sp.setDest(key2);
	    			try {
						sp.initialize2();
						initialized2 = true;
						initialized1 = false;
						jta.setText(message2);
						jspPanel.repaint();
					} catch (IOException e1) {}
					
					
	    		}
	    		
	    	}
	    });
		
		jbtInput.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		//bell-ford
	    		if (initialized1){
	    			try {
	    				if(sp.search1()){
	    					found = true;
	    					jspPanel.repaint();
	    					jta.setText(message1);
	    				}
					} catch (IOException e1) {}
	    		}
	    		//Dijkstra
	    		if (initialized2){
	    			try {
						sp.search2();
						found = true;
						jspPanel.repaint();
						jta.setText(message1);
					} catch (IOException e1) {}
	    		}
	    	}
	    });
		
		jbtExit.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		System.exit(-1);
	    	}
	    });
		
		
	}
	
	class PaintRoute extends JPanel{
		private int radius = 15;
		private Map<Integer, int[]> vIndex = new HashMap<Integer, int[]>();
		protected void paintComponent(Graphics g){
			super.paintComponent(g);
			g.setColor(Color.blue);
			//draw the nodes
			int centerX = getWidth()/2;
			int centerY = getHeight()/2;
			drawNodes(g, centerX, centerY, radius);
			//store the nodes' coordinates
			coordinateMap(centerX, centerY);
			
			if (initialized1 || initialized2){
				for (int i = 0; i < 6; i++){
					for(int j = 0 ; j < 6 ; j++){
						if (sp.getMatrix()[i][j] != 100){
							paintArrow(g, vIndex.get(i)[0], vIndex.get(i)[1],
									vIndex.get(j)[0], vIndex.get(j)[1]);
						}
					}
				}
			}
			if (found){
				drawPath(g, sp.getPath());
				found = false;
			}
		}
		private void drawPath(Graphics g, Stack stk){
			g.setColor(Color.WHITE);
			int x = (Integer) stk.pop();
			int y = (Integer) stk.pop();
			x = x -1;
			y = y -1;
			paintArrow(g, vIndex.get(x)[0], vIndex.get(x)[1],
					vIndex.get(y)[0], vIndex.get(y)[1]);
			while(!stk.isEmpty()){
				x = y;
				y = (Integer) stk.pop();
				y = y - 1;
				paintArrow(g, vIndex.get(x)[0], vIndex.get(x)[1],
					vIndex.get(y)[0], vIndex.get(y)[1]);
			}
			g.setColor(Color.BLUE);
			
		}
		private void coordinateMap(int centerX, int centerY){
			/* 
			 * node1 (centerX-150, centerY)
			 * node2 (centerX-70, centerY-90)
			 * node3 (centerX-70, centerY+90)
			 * node4 (centerX+70, centerY-90)
			 * node5 (centerX+70, centerY+90)
			 * node6 (centerX+150, centerY) 
			 */ 
			int[] node1  = {centerX-150, centerY};
			int[] node2  = {centerX-70, centerY-90};
			int[] node3  = {centerX-70, centerY+90};
			int[] node4  = {centerX+70, centerY-90};
			int[] node5  = {centerX+70, centerY+90};
			int[] node6  = {centerX+150, centerY};
			vIndex.put(0, node1);
			vIndex.put(1, node2);
			vIndex.put(2, node3);
			vIndex.put(3, node4);
			vIndex.put(4, node5);
			vIndex.put(5, node6);
		}
		private void paintArrow(Graphics g, int x0, int y0, int x1,int y1){
			int deltaX = x1 - x0;
			int deltaY = y1 - y0;
			double frac = 0.05;

			g.drawLine(x0,y0,x1,y1);
			g.drawLine(x0 + (int)((1-frac)*deltaX + frac*deltaY),
				   y0 + (int)((1-frac)*deltaY - frac*deltaX),
				   x1, y1);
			g.drawLine(x0 + (int)((1-frac)*deltaX - frac*deltaY),
				   y0 + (int)((1-frac)*deltaY + frac*deltaX),
				   x1, y1);
		}
		
		private void drawNodes(Graphics g , int centerX, int centerY, int radius){
			//node1
			g.drawOval(centerX-150-2*radius, centerY-radius, 2*radius, 2*radius);
			g.drawString("1", centerX-152-radius, centerY+4);
			//node2
			g.drawOval(centerX-70-radius, centerY-90-2*radius, 2*radius, 2*radius);
			g.drawString("2", centerX-70-2, centerY-90-radius+4);
			//node3
			g.drawOval(centerX-70-radius, centerY+90, 2*radius, 2*radius);
			g.drawString("3", centerX-70-2, centerY+90+radius+4);
			//node4
			g.drawOval(centerX+70-radius,  centerY-90-2*radius, 2*radius, 2*radius);
			g.drawString("4", centerX+70-2,  centerY-90-radius+4);
			//node5
			g.drawOval(centerX+70-radius, centerY+90, 2*radius, 2*radius);
			g.drawString("5", centerX+70-2, centerY+90+radius+4);
			//node6
			g.drawOval(centerX+150, centerY-radius, 2*radius, 2*radius);
			g.drawString("6", centerX+148+radius, centerY+4);
		}
	}
	
	public static void main(String[] args){
		JFrame frame = new JFrame();
		frame.add(new ShortestPathPanel());
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("ShortestPath");
		frame.setSize(500, 500);
		frame.setVisible(true);
	}
	
}

