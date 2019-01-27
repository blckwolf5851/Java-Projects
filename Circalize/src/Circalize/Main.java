/** An executor class that holds the JFrame
 * @author Sherry Yuan
 * @version 1.0
 * @since 1.0
 * @date 2019/1/4
*/

package Circalize;

import java.awt.EventQueue;
import java.io.File;
import java.net.URISyntaxException;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.Timer;

public class Main {
	private JFrame frame;
	Var var = new Var();
	public Paint game = new Paint(var);

	public static void main(String[] arg) {

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

	public Main() {
		var.init();
		try {
			Window();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// System.out.print(screenWidth);
	}

	/***********
	 * BUILD MAIN FRAME
	 * 
	 * @throws URISyntaxException
	 *************/
	private void Window() throws URISyntaxException {
		// The main frame
		Timer timer = new Timer(40, game);
		timer.start();
		frame = new JFrame("Circalize");
		frame.setSize(var.scrW, var.scrH);
		// add game panel to main frame
		frame.add(game);

		// build a key event handling class
		// KeyHandle key = new KeyHandle();
		Events handler = new Events(var, game);
		// add to game panel
		game.addMouseListener(handler);
		game.addKeyListener(handler);
		// add to game panel

	}
}
