import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URISyntaxException;
import java.util.ArrayList;
import javax.swing.Timer;
import java.util.TimerTask;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main {
	/*
	public int screenSize = 400;
	public int r = 8, width = 20, x = 60, y = screenSize/2 ;
	public GameDisplay game = new GameDisplay();
	
	public long second = 1;
	public int v = -15;
	public double a = 0.8;
	public int score = 0;
	public boolean gameover = false;
	public ArrayList<Integer> pipX = new ArrayList<Integer>();
	public ArrayList<Integer> pipH = new ArrayList<Integer>();
	public int difficultH = 40, difficultS = 80;
	public int space = screenSize/4;
	public int randSpace = (int)(Math.random()*space)+difficultS, randHeight = (int)(Math.random()*screenSize/2)+difficultH;
	public ArrayList<Integer> pipS = new ArrayList<Integer>();
	*/
	
	private JFrame frame;
	Var var = new Var();
	public GameDisplay game = new GameDisplay(var);
	public static void main(String[] arg)  
	{
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					Main window = new Main();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public Main()  {
		try {
			Window();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		//System.out.print(screenWidth);
	}
	/***********
	 * BUILD MAIN FRAME
	 * @throws URISyntaxException 
	 * *************/
	private void Window() throws URISyntaxException {
		//The main frame
		Timer timer = new Timer(40, game);
		timer.start();
		frame = new JFrame("2048");
		frame.setSize(var.screenSize, var.screenSize);
		//add game panel to main frame
		frame.add(game);
		
		//build a key event handling class
		//KeyHandle key = new KeyHandle();
		Event_Handler handler = new Event_Handler(var, game);
		//add to game panel
		game.addMouseListener(handler);
		//add to game panel
		
	}
	/*
	private class Event_Handler implements MouseListener{
		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			var.v = -30;
			//y += v;
			//v += a;
			game.repaint();
			
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
	}
	*/

}
