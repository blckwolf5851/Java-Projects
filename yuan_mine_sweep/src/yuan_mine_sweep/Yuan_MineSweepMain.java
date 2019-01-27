package yuan_mine_sweep;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import java.util.*;

public class Yuan_MineSweepMain {
	/***********
	 * Global variables
	 ***************/
	// background image
	// public BufferedImage image = ImageIO.read(new File("state1Background.jpg"));

	// initialize grid size & score
	public int row = 30, column = 30, score = 0;

	// initialize game Grid and bomb position
	// grid || display: -1 = light, 0 = dark, > 0 is number of bomb beside
	// bomb || bomb position: 1 = bomb, 0 = no bomb
	public int[][] grid = new int[row][column], bomb = new int[row][column];
	public static int totalBomb = 0;

	// initialize game states
	public boolean gameOver = false, win = false;

	// home = 0, instruction = 1, game = 2, change dimension = 3, result = 4
	public int state = 0;

	// game panel
	public GameDisplay game = new GameDisplay();

	// main frame
	private JFrame frame;

	// screen size
	public int screenWidth = 500, screenHeight = 600;

	// ellipse radius
	public int r = screenWidth / 7;

	// game grid layouts
	public int space = 0, nodeWidth = (screenWidth - space * (grid[0].length + 1)) / grid[0].length,
			nodeHeight = nodeWidth;

	// modifiable screen size (because row/column number is going to change)
	public int scrW = nodeWidth * column + (column + 1) * space, scrH = nodeWidth * row + (row + 1) * space;

	// standard buttonSize
	public int buttonSize = (int) (scrW / 4.4);

	/***************
	 * RUN MAIN GAME// MAIN METHOD
	 ***************/
	public static void main(String[] arg) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Yuan_MineSweepMain window = new Yuan_MineSweepMain();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/*******
	 * RANDOMIZE BOMB POSITION
	 ******/
	static void rndBomb(int[][] bomb) {
		for (int i = 0; i < bomb.length; i++) {
			for (int j = 0; j < bomb[i].length; j++) {
				int value = (int) (Math.random() * 6);
				if (value == 0) {
					bomb[i][j] = 1;
					totalBomb++;
				}

			}
		}
	}

	/***********
	 * INITIALIZATION OF GRID WITH RIGHT FRAME SIZE AND COLUMN/ROW NUMBER
	 ************/
	public void Initialize() {
		// reset game grid
		grid = new int[row][column];
		bomb = new int[row][column];
		totalBomb = 0;
		// randomize bombs
		rndBomb(bomb);
		if (row > column) {
			nodeWidth = (screenHeight - (row + 1) * space) / row;
			nodeHeight = nodeWidth;
		} else {
			nodeWidth = (screenWidth - (column + 1) * space) / column;
			nodeHeight = nodeWidth;
		}
		// change game screen size according to #nodes
		scrW = nodeWidth * column + (column + 1) * space;
		scrH = nodeWidth * row + (row + 1) * space;
		frame.setSize(scrW + 10, scrH + 115);

		buttonSize = (int) (scrW / 4.4);// reset button size according to new screenSize

		// initialize game start
		gameOver = false;
		win = false;
	}

	/***************
	 * CONSTRUCTION CLASS
	 ****************/
	public Yuan_MineSweepMain() {
		Window();
	}

	/***********
	 * BUILD MAIN FRAME
	 *************/
	private void Window() {
		// The main frame
		frame = new JFrame("Sweepy");
		frame.setSize(scrW + 10, scrH + 115);
		// add game panel to main frame
		frame.add(game);
		// mouse handling class
		Handlerclass handler = new Handlerclass();
		// add to game panel
		game.addMouseListener(handler);
	}

	// mouse event handler
	private class Handlerclass implements MouseListener {
		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			/***********
			 * In home page state
			 *********/
			if (state == 0) {
				// if game button pressed
				if (e.getY() > screenHeight / 2 - 15 && e.getY() < screenHeight / 2 - 15 + 2 * r) {
					if (e.getX() > screenWidth / 2 - 3 * r && e.getX() < screenWidth / 2 - 3 * r + 2 * r) {
						state = 2; // game state
						Initialize();
						game.repaint();
					}
					// if instruction button pressed
					else if (e.getX() > screenWidth / 2 + r && e.getX() < screenWidth / 2 + 3 * r) {
						state = 1;// instruction state
						game.repaint();
					}
				}
				// if change dimension state pressed
				else if (e.getY() > screenHeight / 2 - 15 + 2 * r + 10 && e.getY() < screenHeight / 2 - 20 + 3 * r) {
					if (e.getX() > screenWidth / 2 - 3 * r && e.getX() < screenWidth / 2 - 3 * r + 2 * r) {
						state = 3;
						game.repaint();
					}
				}

			}

			/***********
			 * In instruction page state
			 *********/
			else if (state == 1) {
				// if back button pressed
				if (e.getX() > screenWidth / 2 - r && e.getX() < screenWidth / 2 + r) {
					state = 0;
					game.repaint();
				}
			}

			/***********
			 * In dimension page state
			 *********/
			else if (state == 3) {
				if (e.getX() > 10 + 2 * (buttonSize + space) && e.getX() < 10 + 2 * (buttonSize + space) + buttonSize) {
					// increase number of rows
					if (e.getY() > screenHeight / 2 - buttonSize && e.getY() < screenHeight / 2) {
						row++;
						game.repaint();
					}
					// increase number of columns
					else if (e.getY() > screenHeight / 2 && e.getY() < screenHeight / 2 + buttonSize) {
						column++;
						game.repaint();

					}
				} else if (e.getX() > 10 + 3 * (buttonSize + space)
						&& e.getX() < 10 + 3 * (buttonSize + space) + buttonSize) {
					// decrease number of rows
					if (e.getY() > screenHeight / 2 - buttonSize && e.getY() < screenHeight / 2) {

						if (row > 0) {
							row--;
						}

						game.repaint();

					}
					// decrease number of column
					else if (e.getY() > screenHeight / 2 && e.getY() < screenHeight / 2 + buttonSize) {
						if (column > 0) {
							column--;
						}
						game.repaint();

					}
				}
				// Start button
				if (e.getY() > screenHeight / 2 + 2 * space + buttonSize
						&& e.getY() < screenHeight / 2 + 2 * space + buttonSize + buttonSize / 2
						&& e.getX() > screenWidth / 2 - buttonSize && e.getX() < screenWidth / 2 + buttonSize) {
					state = 2; // go to game state
					Initialize();
					game.repaint();

				}
			}
			// game state
			else if (state == 2) {
				// if quit is pressed
				if (e.getX() > scrW - buttonSize - 15 && e.getX() < scrW - buttonSize - 15 + buttonSize
						&& e.getY() > scrH && e.getY() < scrH + 50) {
					frame.setSize(screenWidth, screenHeight);
					// reset score

					state = 0; // return to home
					// Save grid to saveGrid.txt if quit button pressed
					score = 0;
					scrW = screenWidth;
					scrH = screenHeight;
					game.repaint();
				}
				// corresponding index of mouseX and Y
				int[] index = GridOpt.getIndex(e.getX(), e.getY(), nodeWidth);
				if (!gameOver) {
					if (GridOpt.gameOver(bomb, index)) {
						gameOver = true;
						// stop for 0.5 seconds if the game ends
						try {
							Thread.sleep(500);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						// change to result state
						state = 4;
						game.repaint();
						// System.out.println(GameOver.gameOver(grid));
					} else if (GridOpt.winnion(bomb, grid, totalBomb)) {
						// set win
						gameOver = true;
						win = true;
					} else {
						// discover area
						System.out.println("X, Y: " + e.getX() + ", " + e.getY());
						GridOpt.BFS(bomb, grid, index);

						game.repaint();
					}
				}

			}
			// game end state
			else if (state == 4) {

				if (e.getY() > scrH / 2 + scrH / 8 && e.getY() < scrH / 2 + scrH / 8 + scrH / 7) {
					// replay button
					if (e.getX() > scrW / 16 && e.getX() < scrW / 16 + scrW / 2 - scrW / 8) {
						// initialize the game again
						Initialize();
						// reset score
						score = 0;
						// go back to game state
						state = 2;

						game.repaint();
					}
					// home button
					else if (e.getX() > scrW / 2 + scrW / 16 && e.getX() < scrW / 2 + scrW / 16 + scrW / 2 - scrW / 8) {
						state = 0;
						game.repaint();
					}
				}
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

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub

		}
	}

	/**********
	 * THE DRAW FUNCTION
	 **********/
	class GameDisplay extends JPanel {
		private Image winImg, bgimg, buttonImg,soil, grass, dimensionImg, instImg, sizeImg, gameImg;

		public void createImage() {
			// image for nodes with different number
			grass = new ImageIcon(this.getClass().getResource("img\\grassBlock.png")).getImage();
			soil = new ImageIcon(this.getClass().getResource("img\\soilBlock.png")).getImage();
			winImg = new ImageIcon(this.getClass().getResource("img\\winImg.jpg")).getImage(); // win game
																								// state
																								// backgorund
			gameImg = new ImageIcon(this.getClass().getResource("img\\gameback.jpg")).getImage(); // game
																									// state
																									// background
																									// image
			sizeImg = new ImageIcon(this.getClass().getResource("img\\sizeBut.png")).getImage();// change
																								// row
																								// column
																								// size
																								// button
																								// image
			instImg = new ImageIcon(this.getClass().getResource("img\\state2Background.jpg")).getImage(); // instruction
																											// state
																											// background
			dimensionImg = new ImageIcon(this.getClass().getResource("img\\dimensionButton.png")).getImage(); // change
																												// dimension
																												// button
																												// image
			buttonImg = new ImageIcon(this.getClass().getResource("img\\button.png")).getImage();// ellipse
																									// button
																									// image
			bgimg = new ImageIcon(this.getClass().getResource("img\\state1Background.jpeg")).getImage(); // home
																											// state
																											// back
																											// ground
																											// image
		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			// set color variable
			Color backgroundColor = new Color(159, 97, 100);
			Color textColor = new Color(248, 248, 242);
			Color buttonColor = new Color(111, 54, 98);
			Color color = new Color(248, 222, 189);

			// load all images
			createImage();

			// allow for repaint to work
			this.setFocusable(true);

			// leave extra space for score display
			this.setSize(scrW, scrH + 100);
			int fontHight = g.getFontMetrics().getAscent() / 2;

			/**********
			 * HOME STATE
			 ***********/
			if (state == 0) {
				g.drawImage(bgimg, 0, 0, this);
				String game = "Start", inst = "Instruction";

				g.setFont(new Font("TimesRoman", Font.PLAIN, 80));

				// print game title

				g.setColor(buttonColor);

				/*********** print button shape *************/
				// game button //++++++
				g.drawImage(buttonImg, screenWidth / 2 - 3 * r, screenHeight / 2 - 15, 2 * r, 2 * r, this);
				// g.fillOval(screenWidth/2 - 3*r, screenHeight/2 - 15, 2*r, 2*r);
				// instruction button //++++++
				g.drawImage(buttonImg, screenWidth / 2 + r, screenHeight / 2 - 15, 2 * r, 2 * r, this);
				// g.fillOval(screenWidth/2 + r, screenHeight/2 - 15, 2*r, 2*r);
				// change dimension button //++++++
				g.drawImage(dimensionImg, screenWidth / 2 - 3 * r, screenHeight / 2 - 15 + 2 * r + 10, 2 * r, r - 15,
						this);
				// g.fillRoundRect(screenWidth/2-3*r, screenHeight/2-15+2*r + 10, 2*r, r-15, 12,
				// 12);

				/*************** print names of buttons ******************/
				g.setFont(new Font("TimesRoman", Font.PLAIN, 25));
				g.setColor(textColor);
				// game button
				g.drawString(game, screenWidth / 2 - 3 * r + r - g.getFontMetrics().stringWidth(game) / 2,
						screenHeight / 2 - 15 + r + g.getFontMetrics().getHeight() / 4);
				// instruction button
				g.drawString(inst, screenWidth / 2 + 2 * r - g.getFontMetrics().stringWidth(inst) / 2,
						screenHeight / 2 - 15 + r + g.getFontMetrics().getHeight() / 4);
				// dimension button
				g.drawString("Dimension", screenWidth / 2 - 3 * r + r - g.getFontMetrics().stringWidth("Dimension") / 2,
						screenHeight / 2 - 15 + 2 * r + 10 + (r - 15) / 2 + g.getFontMetrics().getHeight() / 4);
			}

			/**********
			 * INSTRUCTION STATE
			 ***********/
			else if (state == 1) {
				g.drawImage(gameImg, 0, 0, screenWidth, screenHeight, this);

				g.setFont(new Font("TimesRoman", Font.PLAIN, 14));

				// text of instruction
				String instruction = "A, S, D, W for straight merging of the game panel, use Q, E, Z, C to merge diagonally, and R to refresh the game panel. If your game panel is full, then game over! The probability of getting 2 is 50%, 4 is 33%, 8 is 17%.";
				String[] instInfo = instruction.split(" ");

				// track where to print each word
				int row = 100, column = 15;

				// fill instruction test in the screen
				g.setColor(textColor);
				for (int i = 0; i < instInfo.length; i++) {

					String word = instInfo[i] + " ";
					int stringWidth = g.getFontMetrics().stringWidth(word);
					int stringHeight = g.getFontMetrics().getHeight();
					if (column + stringWidth < screenWidth) {
						g.drawString(word, column, row);
						column += stringWidth;

					} else {
						row += stringHeight * 1.5;
						column = 15;
						g.drawString(word, column, row);
						column += stringWidth;

					}

				}
				g.setFont(new Font("TimesRoman", Font.PLAIN, 25));
				// draw button that goes back to home
				String back = "<-Back";
				g.setColor(buttonColor);
				g.drawImage(buttonImg, screenWidth / 2 - r, screenHeight / 2 - 15, 2 * r, 2 * r, this);
				// g.fillOval(screenWidth/2 - r, screenHeight/2 - 15, 2*r, 2*r); //++++++
				g.setColor(textColor);
				g.drawString(back, screenWidth / 2 - g.getFontMetrics().stringWidth(back) / 2,
						screenHeight / 2 - 15 + r + fontHight);
			}
			/**********
			 * GAME STATE
			 ***********/
			else if (state == 2) {
				g.drawImage(gameImg, 0, 0, scrW, scrH + 100, this);

				g.setFont(new Font("TimesRoman", Font.PLAIN, buttonSize / 5));

				// draw quit button
				g.setColor(buttonColor);
				g.drawImage(dimensionImg, scrW - buttonSize - 15, scrH, buttonSize, 50, this);
				// g.fillRoundRect(scrW - buttonSize - 15, scrH, buttonSize, 50, 12, 12);
				// //++++++
				g.setColor(textColor);// set quit text color

				g.drawString("Quit", scrW - buttonSize / 2 - 15 - g.getFontMetrics().stringWidth("Quit") / 2,
						scrH + 25 + fontHight);
				// draw score at bottom-left corner
				g.drawString("Score: " + Integer.toString(score), 15, scrH + 25 + g.getFontMetrics().getHeight() / 4);

				/*******************************************
				 * graphical representation of grid
				 ****************************************/
				g.setFont(new Font("TimesRoman", Font.PLAIN, (int) (nodeWidth / 1.2)));
				for (int row = 0; row < grid.length; row++) {

					for (int column = 0; column < grid[0].length; column++) {

						// String num = Integer.toString(grid[row][column]);
						Color dis_color = new Color(242, 246, 247);
						Color undis_color = new Color(207, 212, 214);
						String num = Integer.toString(grid[row][column]);
						if (grid[row][column] > 0) {
							
							g.drawImage(soil, column * nodeWidth, row * nodeHeight, nodeWidth, nodeHeight, this);
							g.setColor(dis_color);
							g.drawString(num,
									column * (nodeWidth + space) + nodeWidth / 2
											- g.getFontMetrics().stringWidth(num) / 2,
									row * (nodeHeight + space) + nodeHeight / 2 + fontHight);
						} else {
							if (grid[row][column] == 0) {
								/*
								 * if(bomb[row][column] == 1) { g.setColor(buttonColor); }else {
								 * g.setColor(undis_color); }
								 */

								
								g.drawImage(grass, column * nodeWidth, row * nodeHeight, nodeWidth, nodeHeight, this);

							} else if (grid[row][column] == -1) {
								g.setColor(dis_color);
								g.drawImage(soil, column * nodeWidth, row * nodeHeight, nodeWidth, nodeHeight, this);

							}
						}
					}
				}
			}

			/**********
			 * DIMENSION STATE
			 ***********/
			else if (state == 3) {
				g.drawImage(bgimg, 0, 0, this); // draw background image
				g.setColor(buttonColor);
				// initialize the position of change in printing buttons
				int firstRow = screenHeight / 2 - buttonSize, secondRow = firstRow + buttonSize + space;
				int strHeight = g.getFontMetrics().getHeight() / 4;
				g.setFont(new Font("TimesRoman", Font.PLAIN, 25));

				/*************
				 * FirstRow: asking for row size
				 ************/
				// sizeImg
				// draw string "Row" in side a round rectangle
				g.drawImage(sizeImg, 10, firstRow, buttonSize, buttonSize, this);
				// draw the current row number in a round rectangle
				g.drawImage(sizeImg, 10 + buttonSize + space, firstRow, buttonSize, buttonSize, this);
				// draw + in side round rectangle
				g.drawImage(sizeImg, 10 + 2 * (buttonSize + space), firstRow, buttonSize, buttonSize, this);
				// draw - in side round rectangle
				g.drawImage(sizeImg, 10 + 3 * (buttonSize + space), firstRow, buttonSize, buttonSize, this);
				// draw all text of first row
				g.setColor(textColor);
				g.drawString("Row", 10 + buttonSize / 2 - g.getFontMetrics().stringWidth("Row") / 2,
						firstRow + buttonSize / 2 + strHeight);
				g.drawString(Integer.toString(row),
						10 + buttonSize + space + buttonSize / 2
								- g.getFontMetrics().stringWidth(Integer.toString(row)) / 2,
						firstRow + buttonSize / 2 + strHeight);
				g.drawString("+",
						10 + 2 * (buttonSize + space) + buttonSize / 2 - g.getFontMetrics().stringWidth("+") / 2,
						firstRow + buttonSize / 2 + strHeight);
				g.drawString("-",
						10 + 3 * (buttonSize + space) + buttonSize / 2 - g.getFontMetrics().stringWidth("-") / 2,
						firstRow + buttonSize / 2 + strHeight);

				/**
				 * SecondRow: asking for column size
				 **/
				// draw string "Column" in side a round rectangle
				g.setColor(buttonColor);
				g.drawImage(sizeImg, 10, secondRow, buttonSize, buttonSize, this);
				// draw the current Column number in a round rectangle
				g.drawImage(sizeImg, 10 + buttonSize + space, secondRow, buttonSize, buttonSize, this);
				// draw + in side round rectangle
				g.drawImage(sizeImg, 10 + 2 * (buttonSize + space), secondRow, buttonSize, buttonSize, this);
				// draw - in side round rectangle
				g.drawImage(sizeImg, 10 + 3 * (buttonSize + space), secondRow, buttonSize, buttonSize, this);
				// draw all string of the second row
				g.setColor(textColor);
				g.drawString("Column", 10 + buttonSize / 2 - g.getFontMetrics().stringWidth("Column") / 2,
						secondRow + buttonSize / 2 + strHeight);
				g.drawString(Integer.toString(column),
						10 + buttonSize + space + buttonSize / 2
								- g.getFontMetrics().stringWidth(Integer.toString(column)) / 2,
						secondRow + buttonSize / 2 + strHeight);
				g.drawString("+",
						10 + 2 * (buttonSize + space) + buttonSize / 2 - g.getFontMetrics().stringWidth("+") / 2,
						secondRow + buttonSize / 2 + strHeight);
				g.drawString("-",
						10 + 3 * (buttonSize + space) + buttonSize / 2 - g.getFontMetrics().stringWidth("-") / 2,
						secondRow + buttonSize / 2 + strHeight);

				/**
				 * draw start game button
				 **/
				g.setColor(buttonColor);
				g.drawImage(dimensionImg, screenWidth / 2 - buttonSize, screenHeight / 2 + 2 * space + buttonSize,
						2 * buttonSize, buttonSize / 2, this);
				g.setColor(textColor);
				g.drawString("Start->", screenWidth / 2 - g.getFontMetrics().stringWidth("Start->") / 2,
						screenHeight / 2 + 2 * space + buttonSize + buttonSize / 4 + strHeight);
			}

			/**********
			 * RESULT STATE
			 ***********/
			else if (state == 4) {
				// win game
				if (win == true) {
					g.drawImage(winImg, 0, 0, scrW, scrH + 100, this);
				} else {
					// loss game
					g.drawImage(instImg, 0, 0, scrW, scrH + 100, this);
				}

				g.setFont(new Font("TimesRoman", Font.PLAIN, scrW / 12));
				int bW = scrW / 2 - scrW / 8, bH = scrH / 7;
				g.setColor(buttonColor);

				// print highest score if highest score is not zero
				g.setFont(new Font("TimesRoman", Font.PLAIN, scrW / 9));

				// print result score
				g.drawString("Your Score is: " + Integer.toString(score),
						scrW / 2 - g.getFontMetrics().stringWidth("Your Score is: " + Integer.toString(score)) / 2,
						scrH / 2 - fontHight);
				// print new game button
				g.drawImage(dimensionImg, scrW / 16, scrH / 2 + scrH / 8, bW, bH, this);

				// print home button
				g.drawImage(dimensionImg, scrW / 2 + scrW / 16, scrH / 2 + scrH / 8, bW, bH, this);
				g.setColor(textColor);
				// draw strings onto buttons
				g.setFont(new Font("TimesRoman", Font.PLAIN, bW / 5));
				g.drawString("Replay", scrW / 16 + bW / 2 - g.getFontMetrics().stringWidth("Replay") / 2,
						scrH / 2 + scrH / 8 + bH / 2 + fontHight * 2);
				g.drawString("Home", scrW / 2 + scrW / 16 + bW / 2 - g.getFontMetrics().stringWidth("Home") / 2,
						scrH / 2 + scrH / 8 + bH / 2 + fontHight * 2);
			}
		}
	}

}
