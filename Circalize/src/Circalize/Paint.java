/** Drawer class
 * @author Sherry Yuan
 * @version 1.0
 * @since 1.0
 * @date 2019/1/4
*/
package Circalize;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Paint extends JPanel implements ActionListener {
	Var var;

	Paint(Var var) {
		this.var = var;
	}

	private Image state0_bg, state0_button, state2_bg;
	Color backgroundColor = new Color(159, 97, 100);
	Color textColor = Color.WHITE;// new Color(24, 248, 242);
	Color buttonColor = new Color(111, 54, 98);
	Color color = new Color(248, 222, 189);

	/** createImage()
	 * load images
	 * @see Image
	 */
	public void createImage() {
		/*
		state0_bg = new ImageIcon(this.getClass().getResource("img\\state0_bg.jpg")).getImage();
		state0_button = new ImageIcon(this.getClass().getResource("img\\state0_button.png")).getImage();
		state2_bg = new ImageIcon(this.getClass().getResource("img\\state2_bg.png")).getImage();*/
		// C:\Users\deyun\Documents\HomeWork\programming\Java program\Circalize\src\Circalize\img
		state0_bg = new ImageIcon("src\\Circalize\\state0_bg.jpg").getImage();
		state0_button = new ImageIcon("src\\Circalize\\state0_button.png").getImage();
		state2_bg = new ImageIcon("src\\Circalize\\state2_bg.png").getImage();
		
	}

	// GameDisplay game = new GameDisplay();
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		createImage();
		this.setBackground(Color.darkGray);
		this.setFocusable(true);
		this.setSize(var.scrW, var.scrH);
		// g.setColor(Color.BLACK);
		// g.fillRect(0, 0, var.screenSize, var.screenSize);
		if (var.state == 0) {
			intro(g);
		} else if (var.state == 1)
			instruct(g);
		else if (var.state == 2)
			game(g);
		if (var.state == 3)
			gameover(g);
	}
	/** intro(Graphics g)
	 * draw home state graphics
	 * */
	public void intro(Graphics g) {
		String game = "Start", inst = "Instruction";

		g.setFont(new Font("TimesRoman", Font.PLAIN, 80));
		// print game title
		g.drawImage(state0_bg, 0, 0, var.scrW, var.scrH, this);
		g.setColor(buttonColor);
		int fontHight = g.getFontMetrics().getAscent() / 2;
		g.drawString("Circalize", var.scrW / 2 - g.getFontMetrics().stringWidth("Circalize") / 2,
				var.scrH / 3 - fontHight);

		/*********** print button shape *************/
		// game button //++++++
		System.out.println("draw button");
		g.drawImage(state0_button, var.scrW / 2 - 3 * var.buttonr, var.scrH / 2 - 15, var.buttonr * 2, var.buttonr * 2,
				this);
		// instruction button //++++++
		g.drawImage(state0_button, var.scrW / 2 + var.buttonr, var.scrH / 2 - 15, var.buttonr * 2, var.buttonr * 2,
				this);

		/*************** print names of buttons ******************/
		g.setFont(new Font("TimesRoman", Font.PLAIN, 25));
		fontHight = g.getFontMetrics().getAscent() / 2;
		g.setColor(textColor);
		// game button
		g.drawString(game, var.scrW / 2 - 3 * var.buttonr + var.buttonr - g.getFontMetrics().stringWidth(game) / 2,
				var.scrH / 2 - 15 + var.buttonr + g.getFontMetrics().getHeight() / 4);
		// instruction button
		g.drawString(inst, var.scrW / 2 + 2 * var.buttonr - g.getFontMetrics().stringWidth(inst) / 2,
				var.scrH / 2 - 15 + var.buttonr + g.getFontMetrics().getHeight() / 4);
	}
	/** instruct(Graphics g)
	 * draw instruction state graphics
	 * */
	public void instruct(Graphics g) {
		g.drawImage(state0_bg, 0, 0, var.scrW, var.scrH, this);

		g.setFont(new Font("TimesRoman", Font.PLAIN, 26));

		// text of instruction
		String instruction = "Press mouse to release/attach string with the pin. The ball can undergo circular motion only if ball's motion path is tangent to the circle.Press g to switch between gravitation mode and normal mode.";
		String[] instInfo = instruction.split(" ");

		// track where to print each word
		int row = 100, column = 15;
		int fontHight = g.getFontMetrics().getAscent() / 2;
		// fill instruction test in the screen
		g.setColor(buttonColor);
		for (int i = 0; i < instInfo.length; i++) {

			String word = instInfo[i] + " ";
			int stringWidth = g.getFontMetrics().stringWidth(word);
			// continue print word to that row
			if (column + stringWidth < var.scrW) {
				g.drawString(word, column, row);
				column += stringWidth;

			}
			// else switch to a new row
			else {
				row += fontHight * 2;
				column = 15;
				g.drawString(word, column, row);
				column += stringWidth;

			}

		}
		g.setFont(new Font("TimesRoman", Font.PLAIN, 25));
		fontHight = g.getFontMetrics().getAscent() / 2;
		// draw button that goes back to home
		String back = "<-Back";
		g.setColor(buttonColor);
		g.drawImage(state0_button, var.scrW / 2 - var.buttonr, var.scrH / 2 - 15, 2 * var.buttonr, 2 * var.buttonr,
				this);
		g.setColor(textColor);
		g.drawString(back, var.scrW / 2 - g.getFontMetrics().stringWidth(back) / 2,
				var.scrH / 2 - 15 + var.buttonr + fontHight);
	}

	/** game(Graphics g)
	 * draw game state graphics
	 * */
	public void game(Graphics g) {
		// draw line paper
		g.drawImage(state2_bg, 0, 0, var.scrW, var.scrH, this);
		
		if(var.g > 0) {
			g.setColor(Color.BLACK);
			g.setFont(new Font("TimesRoman", Font.PLAIN, 30));
			g.drawString("G", 20, 30);
		}
		g.drawString(Integer.toString(var.score), var.scrW-g.getFontMetrics().stringWidth(Integer.toString(var.score))-50, 30);
		// draw ball
		drawBall(var.x, var.y, var.r, (int) (Math.random() * 255), (int) (Math.random() * 255),
				(int) (Math.random() * 255), g);
		// g.fillOval((int) Math.round(var.x - var.r / 2), (int) Math.round(var.y -
		// var.r / 2), var.r, var.r);
		if (!var.release) {
			// change string color, if ball is attached
			if (var.attach) {
				g.setColor(Color.RED);
			}
			// drawstring that attaches the ball
			g.drawLine((int) Math.round(var.x), (int) Math.round(var.y), var.curx(), var.cury());
		}
		// Draw shadows
		Color myColour = new Color(0, 0, 0, 127);
		g.setColor(myColour);
		for (int i = 0; i < var.shadows.size(); i++) {

			g.fillOval(var.shadows.get(i).x - i / 2, var.shadows.get(i).y - i / 2, i, i);
		}
		// draw pins
		for (int i = 0; i < var.center.size(); i++) {
			// if the pin is attached to a ball, set to another color
			if (i == var.curCenter) {
				drawPin(var.center.get(i).x, var.center.get(i).y, Color.RED, g);
			} else {
				drawPin(var.center.get(i).x, var.center.get(i).y, Color.BLUE, g);
			}
		}
		// draw block
		g.setColor(Color.darkGray);
		for (int i = 0; i < var.blocks.size(); i++) {
			g.fillRect(var.blocks.get(i).x, var.blocks.get(i).y, var.blocks.get(i).w, var.blocks.get(i).h);
		}
	}
	/** drawPin(int x, int y, Color c, Graphics g)
	 * draw pin point
	 * */
	private void drawPin(int x, int y, Color c, Graphics g) {
		int size = 25;
		g.setColor(Color.GRAY);
		// draw metal part
		for (int i = 0; i < size; i++) {
			g.fillOval(x - i, y - i, (int) Math.sqrt(i), (int) Math.sqrt(i));
		}
		// draw end part
		g.setColor(c);
		g.fillOval(x - size, y - size, size / 2, size / 2);
	}

	/** drawBall(double x, double y, double r, int red, int green, int blue, Graphics g)
	 * recursive draw function
	 */
	public void drawBall(double x, double y, double r, int red, int green, int blue, Graphics g) {
		g.setColor(new Color(red, green, blue));
		g.fillOval((int) (x - r), (int) (y - r), (int) r * 2, (int) r * 2);
		if (r > 3) {
			drawBall(x, y, (int) (r / 1.618), (int) (red / 1.618), (int) (green / 1.618), (int) (blue / 1.618), g);
		}
	}

	/**
	 * gameover(Graphics g) 
	 * draw gameover screen
	 * @param Graphics g using same graphic component to draw
	 */
	public void gameover(Graphics g) {
		// background image
		g.drawImage(state0_bg, 0, 0, var.scrW, var.scrH, this);
		g.setFont(new Font("TimesRoman", Font.PLAIN, var.scrW / 12));
		// button sizes
		int bW = var.scrW / 2 - var.scrW / 8, bH = var.scrH / 7;
		g.setColor(buttonColor);
		// print highest score
		try {
			g.drawString("Highest Score: " + var.read(var.highScoref),
					var.scrW / 2 - g.getFontMetrics().stringWidth("Highest Score: " + var.read(var.highScoref) / 2) / 2,
					(int) (var.scrH / 1.685));

		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		g.setFont(new Font("TimesRoman", Font.PLAIN, var.scrW / 9));
		int fontHight = g.getFontMetrics().getAscent() / 2;
		// print result score
		g.drawString("Game Over", var.scrW / 2 - g.getFontMetrics().stringWidth("Game Over") / 2,
				var.scrH / 3 - fontHight);
		g.drawString("Your Score is: " + Integer.toString(var.score),
				var.scrW / 2 - g.getFontMetrics().stringWidth("Your Score is: " + Integer.toString(var.score)) / 2,
				var.scrH / 2 - fontHight);
		// print REPLAY button
		g.setColor(new Color(255, 71, 71));
		g.fillRect(var.scrW / 16, var.scrH / 2 + var.scrH / 8 + 10, bW, bH);

		// print home button
		g.fillRect(var.scrW / 2 + var.scrW / 16, var.scrH / 2 + var.scrH / 8 + 10, bW, bH);
		g.setColor(textColor);
		// draw strings onto buttons
		g.setFont(new Font("TimesRoman", Font.PLAIN, bW / 5));
		fontHight = g.getFontMetrics().getAscent() / 2;
		g.drawString("Replay", var.scrW / 16 + bW / 2 - g.getFontMetrics().stringWidth("Replay") / 2,
				var.scrH / 2 + var.scrH / 8 + bH / 2 + fontHight);
		g.drawString("Home", var.scrW / 2 + var.scrW / 16 + bW / 2 - g.getFontMetrics().stringWidth("Home") / 2,
				var.scrH / 2 + var.scrH / 8 + bH / 2 + fontHight);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		// if its game state and game is not over
		if (var.state == 2 && !var.gameover) {
			// Game starts
			if (var.start == 1) {
				// if ball is released, x moves at constant velocity, y affected by gravity
				if (var.release) {
					var.x -= spin_sign() * var.vx;
					var.y -= spin_sign() * var.vy;
					var.vy -= spin_sign() * var.g;
				}
				// if ball is not released
				else if (!var.release) {
					// if ball is not attached to a pin, then same thing as released ball
					if (!var.attach) {
						var.x -= spin_sign() * var.vx;
						var.y -= spin_sign() * var.vy;
						var.vy -= spin_sign() * var.g;
					}
					// if ball's motion path is tangent to the circular path around the pin, then
					// ball undergo circular motion
					if (!var.attach && ifTangent()) {
						var.score++;
						// re-calculate velocity
						var.v = Math.sqrt(Math.pow(var.vx, 2) + Math.pow(var.vy, 2));
						double deltay = (var.cury() - var.y);
						double deltax = (var.x - var.curx());
						// prevent division by zero
						if (deltax == 0) {
							deltax = 0.0001;
						}
						// re-calculate radius and angular velocity
						var.radius = Math.sqrt(Math.pow(deltax, 2) + Math.pow(deltay, 2));
						var.spin = spin_sign() * degree(var.v / var.radius * 1.6);
						var.attach = true;
					}
					// if ball is attached, undergo circular motion
					else if (var.attach) {
						var.x = var.curx() + Math.cos(radian(var.alpha)) * var.radius;
						var.y = var.cury() - Math.sin(radian(var.alpha)) * var.radius;
						var.alpha += var.spin;
						var.x -= var.center.get(var.curCenter).speed;
						var.spin -= Math.cos(radian(var.alpha)) * var.g;
					}

				}

			}
			// Game not started yet, Ball attach to the first pin
			else if (var.start == 0) {
				var.x = (int) Math.round(var.curx() + Math.cos(radian(var.alpha)) * var.radius);
				var.y = (int) Math.round(var.cury() - Math.sin(radian(var.alpha)) * var.radius);
				var.alpha += var.spin;
				var.spin -= var.spin / Math.abs(var.spin) * Math.cos(radian(var.alpha)) * var.g;
			}
			// keep adding shadows to the end, if the limit reach, keep stripping the tail
			var.shadows.add(new Shadow((int) (var.x), (int) (var.y)));
			if (var.shadows.size() > var.shadow_length) {
				var.shadows.remove(0);
			}

			var.second++;

			super.repaint();
			// spawn new pins at rate of 20
			if (var.second % 20 == 0) {
				var.center.add(new Centers(var.scrW, var.scrH));

			}
			// spawn new block
			if (var.second % 70 == 0) {
				var.blocks.add(new Block(var.scrH));
				var.second = 1;
			}
			// update pin's position
			for (int i = 1 - var.start; i < var.center.size(); i++) {
				var.center.get(i).x -= var.center.get(i).speed;
			}
			// update blocks position
			for (int i = 0; i < var.blocks.size(); i++) {
				var.blocks.get(i).x -= 2;
			}
			// if pin is off bound, remove the pin
			if (var.center.size() != 0 && var.center.get(0).x < 0) {
				var.center.remove(0);
				var.curCenter -= 1;
			}
			// if block is off bound, remove the block
			for (int i = 0; i < var.blocks.size(); i++) {
				boolean collision = false;
				if(var.y + var.r > var.blocks.get(i).y && var.y - var.r < var.blocks.get(i).y + var.blocks.get(i).h) {
					if(var.x < var.blocks.get(i).x) {
						if(var.x + var.r > var.blocks.get(i).x) {
							collision = true;
						}
					}else if(var.x > var.blocks.get(i).x) {
						if(var.x - var.r < var.blocks.get(i).x + var.blocks.get(i).w) {
							collision = true;
						}
					}
					if(collision) {
						try {
							if (var.score > var.read(var.highScoref)) {
								var.highestScore(var.score);
							}
						} catch (NumberFormatException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						var.gameover = true;
						var.state = 3;
					}
				}
				
				// remove blocks that is off screen
				if (var.blocks.size() != 0 && var.blocks.get(i).x < 0) {
					var.blocks.remove(i);
					break;
				}
			}
			// if the current pin is less than 1, detach
			if (var.curCenter == -1) {
				var.release = true;
				var.attach = false;
				var.curCenter = 0;
			}

			// if ball is off bound, game over
			if ((var.x < 0 || var.x > var.scrW || var.y < 0 || var.y > var.scrH) && (var.release || !var.attach)) {
				// update highest score
				try {
					if (var.score > var.read(var.highScoref)) {

						var.highestScore(var.score);
					}
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				var.gameover = true;
				var.state = 3;
			}

			super.repaint();

		}

	}

	/**
	 * double radian(double beta) 
	 * convert degree to radian
	 * @param beta degree in degree
	 * @return beta * Math.PI / 180 beta in radian
	 */
	static double radian(double beta) {
		return beta * Math.PI / 180;
	}

	/**
	 * degree(double beta) 
	 * convert radian to degree
	 * @param beta degree in radian
	 * @return beta / Math.PI * 180 beta in degree
	 */
	static double degree(double beta) {
		return beta / Math.PI * 180;
	}

	/**
	 * ifTangent() 
	 * check if ball's path is tangent to the circle around the pin
	 * @return xy_abs > ag_abs - 5 && xy_abs < ag_abs + 5
	 **/
	boolean ifTangent() {
		double deltax = (var.x - var.curx());
		double deltay = var.cury() - var.y;
		// prevent division by zero
		if (deltax == 0) {
			deltax = spin_sign() * 0.001;
		} else if (deltay == 0) {
			deltay = spin_sign() * 0.001;
		}
		// calculate angle of vx & vy, and the angle of relative position between pin
		// and ball
		double xy_abs = degree(Math.tanh(deltay / deltax));
		double ag_abs = degree(theta());
		// degree allowance = 3
		if (xy_abs > ag_abs - 5 && xy_abs < ag_abs + 5) {
			// calculate spin direction and starting angle accordingly to quadrant
			if (spin_sign() * var.vx / (spin_sign() * var.vy) > 0) {
				// quadrant 1 & 3
				System.out.println("Q1/Q3");
				if (deltax < 0 && deltay < 0) {
					var.alpha = degree(theta()) - 180;
					var.spin = Math.abs(var.spin) * -spin_sign() * var.vy / Math.abs(spin_sign() * var.vy);
					System.out.println("Q3:" + var.alpha + " " + spin_sign());
				} else if (deltax > 0 && deltay > 0) {
					var.alpha = degree(theta());
					var.spin = Math.abs(var.spin) * spin_sign() * var.vy / Math.abs(spin_sign() * var.vy);
					System.out.println("Q1:" + var.alpha + " " + spin_sign());
				}
			} else if (spin_sign() * var.vx / (spin_sign() * var.vy) < 0) {
				// quadrant 2 & 4
				System.out.println("Q2/Q4");
				if (deltax < 0 && deltay > 0) {
					var.alpha = degree(theta()) - 180;
					var.spin = Math.abs(var.spin) * -spin_sign() * var.vy / Math.abs(spin_sign() * var.vy);
					;
					System.out.println("Q2:" + var.alpha + " " + spin_sign());
				} else if (deltax > 0 && deltay < 0) {
					var.alpha = degree(theta());
					var.spin = Math.abs(var.spin) * spin_sign() * var.vy / Math.abs(spin_sign() * var.vy);
					;
					System.out.println("Q4:" + var.alpha + " " + spin_sign());
				}
			}
		}
		return xy_abs > ag_abs - 5 && xy_abs < ag_abs + 5;
	}

	/** spin_sign()
	 * return the sign of spin (+ve / -ve)
	 * @return (var.spin / Math.abs(var.spin))
	 *     if the ball is spinning clockwise / counter-clockwise
	 */
	int spin_sign() {
		return (int) (var.spin / Math.abs(var.spin));
	}

	/** theta()
	 * return the angle of velocity
	 * @return Math.tanh(spin_sign() * var.vx / (spin_sign() * var.vy))
	 *     return path's angle
	 */
	double theta() {
		return Math.tanh(spin_sign() * var.vx / (spin_sign() * var.vy));
	}
}
