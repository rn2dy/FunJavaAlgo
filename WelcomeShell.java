import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

public class WelcomeShell extends JFrame {
	//Fonts
	Font font1 = new Font("Cambria", Font.BOLD, 30);
	Font font2 = new Font("Comic Sans MS", Font.BOLD+Font.ITALIC, 25);
	Font font3 = new Font("Cambria", Font.BOLD+Font.ITALIC, 10);
	//labels
	JLabel jlbT = new JLabel("CS430   Project", JLabel.CENTER);
	JLabel jlbA = new JLabel("Application I", JLabel.CENTER);
	JLabel jlbB = new JLabel("Application II", JLabel.CENTER);
	//date label
	Date date = new Date();
	JLabel jlbTime = new JLabel(date.toString());
	//signature
	JLabel jlbN = new JLabel("Author: penguinlover");
	//button1  2-3 tree
	//button2 shortest path
	JButton jbtA = new JButton("2-3-Tree");
	JButton jbtB = new JButton("Shortest Path");
	//boxes container
	Box box = Box.createVerticalBox();
	Box box2 = Box.createHorizontalBox();
	//create frames for holding the two application
	private JFrame tree = new JFrame();
	private JFrame shortest = new JFrame();
	
	//the 2-3 tree panel and shortest path panel
	private TwoThreeTreePanel tp= new TwoThreeTreePanel();
	private ShortestPathPanel sp = new ShortestPathPanel();
	//constructor
	public WelcomeShell(){
		
		
		jlbT.setFont(font1);
		jbtA.setFont(font2);
		jbtB.setFont(font2);
		jlbTime.setFont(font3);
		jlbN.setFont(font3);
		JPanel p1 = new JPanel();
		//add title part
		jlbT.setBorder(new MatteBorder(15,15,15,15, 
				new ImageIcon("image/caIcon.gif")));
		
		//add buttons
		box.add(Box.createVerticalStrut(35));
		jlbA.setAlignmentX(Component.CENTER_ALIGNMENT);
		box.add(jlbA);
		jbtA.setAlignmentX(Component.CENTER_ALIGNMENT);
		box.add(jbtA);
		box.add(Box.createVerticalStrut(25));
		jlbB.setAlignmentX(Component.CENTER_ALIGNMENT);
		box.add(jlbB);
		jbtB.setAlignmentX(Component.CENTER_ALIGNMENT);
		box.add(jbtB);
		box.add(Box.createVerticalStrut(10));
		//box.setBorder(new LineBorder(Color.BLUE, 2));
		box.setBorder(new MatteBorder(15,15,15,15, 
				new ImageIcon("image/flagIcon5.gif")));
		box.setPreferredSize(new Dimension(350, 270));
		p1.add(box);
		
		//date and signature part
		JPanel p2 = new JPanel();
		p2.setLayout(new BoxLayout(p2, BoxLayout.LINE_AXIS));
		p2.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
		p2.add(Box.createHorizontalGlue());
		p2.add(jlbTime);
		p2.add(Box.createRigidArea(new Dimension(10, 0)));
		p2.add(jlbN);
		
		//add all panels to the fram
		this.add(jlbT, BorderLayout.NORTH);
		this.add(p1, BorderLayout.CENTER);
		this.add(p2, BorderLayout.SOUTH);
		
		//register listeners
		jbtA.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				tree.setVisible(true);
			}
		});
		
		jbtB.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				shortest.setVisible(true);
			}
		});
		//two three tree
		//tp.setPreferredSize(new Dimension(500,500));
		tree.add(tp);
		tree.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		tree.setSize(500,500);
		tree.setTitle("2-3-Tree");
		
		//shortest path
		shortest.add(sp);
		shortest.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		shortest.setSize(500,500);
		shortest.setTitle("Shortest Path");
	}
	
	//for testing
	public static void main(String[] args){
		WelcomeShell display = new WelcomeShell();
		display.setLocationRelativeTo(null);
		display.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		display.setTitle("WelcomeShell");
		display.setSize(500, 500);
		display.setVisible(true);
	}
}
