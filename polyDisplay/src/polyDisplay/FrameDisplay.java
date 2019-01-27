package polyDisplay;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;
public class FrameDisplay {
	
	/*********
	 * Global Variables
	 * ************/
	public PolyDisplay window = new PolyDisplay();
	private JFrame frame  = new JFrame("AcdamicFunction");;
	private JButton graph;
	public static int screenWidth = 400, screenHeight = 400, columns = 13;
	public String poly1 = "4 1 3 2", poly2 = "2 3 1 5", poly = "1 0 0";
	public int[] origen = {screenWidth/2, screenHeight / 2};
	// 0 = home, 1 = instruction, 2 = polynomial
	public int state = 0;
	/**************
	 * Main Method
	 * *************/
	public static void main(String[] arg) 
	{
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrameDisplay window = new FrameDisplay();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
	/***************
	 * Construction Class
	 * ****************/
	public FrameDisplay() {
		Window();
	}
	
	public void Home() {
		
	}
	
	/*******************
	 * Window Class: Displaying the main window
	 * *******************/
	public void Window()
	{
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize((screenWidth+20 )*2, screenHeight + 45);
		frame.getContentPane().setLayout(new GridLayout(1, 2, 5, 5));
		
		//the draw function
		//PolyDisplay window = new PolyDisplay();
		
		//make a new panel at the left side of the frame
		JPanel input = new JPanel();
		input.setLayout(new GridLayout(5, 2, 5, 5));
		
		//the row where polynomial 1 will be displayed
		JLabel polyno1 =  new JLabel("Polynomial 1: ");
		JTextField inpP1 = new JTextField();
		inpP1.setColumns(10);
		
		//the row where polynomial 2 will be displayed
		JLabel polyno2 =  new JLabel("Polynomial 2: ");
		JTextField inpP2 = new JTextField();
		inpP2.setColumns(10);
		
		//the row where result polynomial will be displayed
		JLabel multResult =  new JLabel("Multiplied: ");
		JTextField inpResult = new JTextField();
		inpResult.setColumns(10);
		
		//the button to graph the polynomial
		JButton factor = new JButton("Factor");
		graph = new JButton("Graph");
		
		// the row where roots are displayed
		JLabel factorResult =  new JLabel("Roots: ");
		JTextField inpFactor = new JTextField();
		inpFactor.setColumns(10);
		inpFactor.setEditable(false);
		
		//Event Handlers
		factor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Factor findRoot = new Factor();
				double[] root = findRoot.factor(poly);
				System.out.println(Arrays.toString(root));
				String roots = "";
				for (int i = 0; i < root.length; i++) {
					roots += Double.toString(root[i]) + " ";
				}
				if(roots.length() == 0) {
					inpFactor.setText("None");
				}else {
					inpFactor.setText(roots);
				}				
			}
		});
		
		graph.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				window.repaint();
			}
		});
		
		//action for poly1 input
		inpP1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				poly1 = inpP1.getText();
			}
		});
		//action for poly2 input
		inpP2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				poly2 = inpP2.getText();
				if(poly1.length() != 0) {
					polyMult mult = new polyMult();
					
					String proPoly = "";
					for(int i = 0; i < mult.read(poly1,poly2).length; i++) {
						proPoly += Integer.toString(mult.read(poly1,poly2)[i]) + " ";
					}
					poly = proPoly;
					inpResult.setText(poly);

				}
			}
		});
		//action for multPoly input
		inpResult.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				poly = inpResult.getText();
			}
		});
			
		//add components to JPanel
		input.add(polyno1);
		input.add(inpP1);
		input.add(polyno2);
		input.add(inpP2);
		input.add(multResult);
		input.add(inpResult);
		input.add(factor);
		input.add(graph);
		input.add(factorResult);
		input.add(inpFactor);
		
		//add mouse handling to panel
		Handlerclass handler = new Handlerclass();
		window.addMouseListener(handler);
		
		//add to components main frame
		frame.add(input);
		frame.add(window);
		
	}
	
	/***********
	 * Mouse event handling
	 * *********/
	private class Handlerclass implements MouseListener{
		//if mouse clicked it return the position on the graph
		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			if(e.isShiftDown()==false && e.isControlDown() == false) {
				double scale = screenWidth/(2*columns);
				System.out.print(Math.round((e.getX()-origen[0])/scale*100)/100.0 + ", ");
				System.out.println(Math.round((origen[1]-e.getY())/scale*100)/100.0);
			}	
		}
		
		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		//if mouse pressed with shift down, it zoom in
		//else if mouse pressed with controll down, it zoom out
		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			if(e.isShiftDown() && columns > 9) {
				columns--;
				window.repaint();				
			}
			else if(e.isControlDown() && columns < 24){
				columns++;
				window.repaint();	
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	/*******************
	 * The class that graph the polynomial
	 * ******************/
	class PolyDisplay extends JPanel{
		// reads the input from the global variable: poly
		public double[] readInp() 
		{
			
			//System.out.println(input1);
			//System.out.println(input2);
			String[] strArr1 = poly.split(" ");
			
			Collections.reverse(Arrays.asList(strArr1));

			//create array with max length
			double[] polynomial1 = new double[strArr1.length];


			//fill in integer array poly1
			for(int i = 0; i < strArr1.length; i++)
			{
				polynomial1[i] = Double.parseDouble(strArr1[i]);
			}
			//fill in integer array poly2
			//System.out.print(Arrays.toString(poly1) +" "+ Arrays.toString(poly2));

			//System.out.println(Arrays.toString(brute(a1,a0,b1,b0,1)));
			return polynomial1;
		}
		
		//prepare cordinates based on given polynomial
		public double[][] cordinates(double[] poly) 
		{
			
			double[][] cord = new double[(columns*2 + 1)*10][2];
			int n = 0;
			for(double i = 0; i < columns*2 +1 ; i+= 0.1) 
			{
				double x = i-10;
				cord[n][0] = x;
				double y = 0;
				for(int j =0; j < poly.length;j++) 
				{
					y += poly[j] * Math.pow(x, j);
				}
				cord[n][1] = y;
				n++;
			}
			return cord;
		}
		
		//Draw the coordinates out based on coordinates function
		public void paintComponent(Graphics g) 
		{
			
			
			int scale = screenWidth/(2*columns);
			
			super.paintComponent(g);
			this.setBackground(Color.WHITE);
			g.setColor(Color.GRAY);
			//draw origin line, set up Conventional Coordinate System
			g.drawLine(origen[0], 0, origen[0], screenHeight);
			g.drawLine(0, origen[1], screenWidth,  origen[1]);
			
			int r = (int)(scale * 0.1);
			int hor = -columns;
			g.setFont(new Font("Serif", Font.PLAIN, (int)(scale * 0.9)));
			//print horizontal coordinates
			for(int i = 0; i < columns*2 +1 ; i++) 
			{
				g.fillOval(origen[0]+hor*scale-r, origen[1]-r, 2*r, 2*r);
				String scl = Integer.toString(i-columns);
				g.drawString(scl, origen[0]+hor*scale - g.getFontMetrics().stringWidth(scl)/2, origen[1]+g.getFontMetrics().getAscent());
				hor++;
			}
			//print vertical coordinates
			int num = columns;
			int ver = -columns;
			for(int i = 0; i < columns*2 +1 ; i++) 
			{
				String scl = Integer.toString(num);
				// prevent 2 zero being drawn
				if(num != 0) 
				{
					g.fillOval(origen[0]-r, origen[1]+ver*scale-r, 2*r, 2*r);
					g.drawString(scl, origen[0]+5, origen[1]+ver*scale + g.getFontMetrics().getAscent()/2);
				}
				ver++;
				num--;
			}
			
			//using polyline to connect all computed coordinates
			double[] polynomial = readInp();
			double[][] cord = cordinates(polynomial);
			//prepare x,y cordinates for polyline
			int[] x = new int[cord.length];
			int[] y = new int[x.length];
			int n = 0;
			int rightLen = 0;
			for(int i = 0; i < cord.length; i ++) 
			{
				
				x[n] = (int)Math.round(cord[i][0]*scale + origen[0]);
				y[n] = (int)Math.round(origen[1] -cord[i][1]*scale);
				n++;
				rightLen ++;
				
			}
			g.setColor(Color.BLACK);
			g.drawPolyline(x, y, rightLen);
	}
		
	}
}
