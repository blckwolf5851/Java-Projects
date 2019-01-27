package diagonal2048;
/***********
 * A main program that run graphics and set up frame, key control
 * and mouse control
 * 
 * @author Sherry Yuan
 * @version 1.0 (2018/6/2)
 * ****************
 */

import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Scanner;
import java.awt.Toolkit;

public class MainGame {
	/***********
	 * Global variables
	 ***************/

	// key state used to update the pointing arrow
	public String keyState = " ";
	// initialize grid size & score
	public int row = 4, column = 4, score = 0;

	// initialize grid
	public int[][] grid = new int[row][column];

	// the 2D array storing for previous move
	public int[][] lastGrid;

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
	public int space = 5, nodeWidth = (screenWidth - space * (grid[0].length + 1)) / grid[0].length,
			nodeHeight = nodeWidth;

	// modifiable screen size (because row/column number is going to change)
	public int scrW = nodeWidth * column + (column + 1) * space, scrH = nodeWidth * row + (row + 1) * space;

	// standard buttonSize
	public int buttonSize = (int) (scrW / 4.4);
	
	//load file
	
	public File highScoref = new File("highestScore.txt");
	public File curScoref = new File("curScore.txt");
	public File gridf = new File("saveGrid.txt");

	/**
	 * RUN MAIN GAME// MAIN METHOD
	 */
	public static void main(String[] arg) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainGame window = new MainGame();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	/**
	 * read all numbers from input file, and seperate number by space
	 * @param file a directory to the file to be read
	 * @return String, content in file seperated by space
	 */
	public String read(File f) throws IOException {
		Scanner x = new Scanner(f);
		
		String s = "";
		
		while (x.hasNext()) {
			s += x.next() + " ";
		}
		return s;
		
	}
	/**
	 * clear the file and rewrite it with new numbers
	 * @param file a directory to the file to be read
	 * @param String a string to be write to file
	 */
	public void write(String s, File f) throws IOException {
		PrintWriter fw = new PrintWriter(f);
		fw.write(s);
		fw.close();
	}

	
	/**
	 * clear file, clear history
	 * @param file a directory to the file to be read
	 */
	public void clear(File f) throws IOException {
		PrintWriter fw = new PrintWriter(f);
		fw.write("0");
		fw.close();
	}

	/**
	 * save the current grid to saveGrid.txt
	 */ 
	public void saveGrid() throws IOException {
		
		String s = "";
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				String num = Integer.toString(grid[i][j]) + " ";
				s += num;
			}
		}
		write(s, gridf);
	}

	/**
	 * save the current score to be resume next game
	 */ 
	public void saveScore() throws IOException {
		
		String s = Integer.toString(score);

		write(s, curScoref);
	}

	/**
	 * save the highest score to highestScore.txt
	 * @param int score, the current score
	 */ 
	public void highestScore(int score) throws IOException {
		write(Integer.toString(score), highScoref);
	}
	/**
	 * copy the grid(current move) array into lastGrid(previous move)
	 */ 
	public void copyArray() {
		lastGrid = new int[grid.length][grid[0].length];
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				lastGrid[i][j] = grid[i][j]; // rewrite each node
			}
		}
	}

	/**
	 * INITIALIZATION OF GRID WITH RIGHT FRAME SIZE AND COLUMN/ROW NUMBER, and
	 * resume the last game
	 * 
	 * @throws IOException
	 */
	public void dimensions() throws IOException {
		// initialize grid with right size
		grid = new int[row][column];

		// change node size according to screen size
		if (row > column) {
			nodeWidth = (screenHeight - (row + 1) * space) / row;
			nodeHeight = nodeWidth;
		} else {
			nodeWidth = (screenWidth - (column + 1) * space) / column;
			nodeHeight = nodeWidth;
		}

		// load the grid file, replace the current grid with the grid that was stored in
		// the file
		
		String[] lastgrid = read(gridf).split(" "); // the previous game history
		int count = 0;
		if (row * column == lastgrid.length) {
			if (lastgrid.length != 0) {
				// write last game information into current grid
				for (int row = 0; row < grid.length; row++) {
					for (int column = 0; column < grid[0].length; column++) {
						grid[row][column] = Integer.parseInt(lastgrid[count]);
						count++;
					}
				}
			}
			// rewrite the score, with last game's score
			
			score = Integer.parseInt(read(curScoref).split(" ")[0]);

		}

		copyArray(); // copy the lastMove into lastGrid
		grid = Spawn.spawn(grid); // spawn a node at beginning of each game

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
	 * 
	 * @throws URISyntaxException
	 ****************/
	public MainGame() throws URISyntaxException {
		Window();
	}

	/***********
	 * BUILD MAIN FRAME
	 * 
	 * @throws URISyntaxException
	 *************/
	private void Window() throws URISyntaxException {
		// The main frame
		frame = new JFrame("2048");
		frame.setSize(scrW + 10, scrH + 115);

		// add game panel to main frame
		frame.add(game);

		// build a key event handling class
		KeyHandle key = new KeyHandle();

		// mouse handling class
		Handlerclass handler = new Handlerclass();

		// add to game panel
		game.addMouseListener(handler);
		game.addKeyListener(key);

	}

	/**
	 * check the equality of 2D array
	 * @param array, the first array to be compared
	 * @param array2, the second array to be compared
	 */
	public static boolean equal(int[][] arr1, int[][] arr2) {
		if (arr1 == null) {
			return false;
		}
		if (arr2 == null) {
			return false;
		}
		if (arr1.length != arr2.length) {
			return false;
		}
		for (int i = 0; i < arr1.length; i++) {
			// check every dimension if array is equal
			if (!Arrays.equals(arr1[i], arr2[i])) {
				return false;
			}
		}
		return true;
	}

	// key event handler class
	private class KeyHandle implements KeyListener {

		/**
		 * modify the grid each time a valid key is pressed
		 * @param keyEvent, A,S,D,W,Q,E,Z,C
		 */
		
		public void keyPressed(KeyEvent e) {
			if (!gameOver) {
				// if game panel is a square,then allow to merge diagonally
				if (row == column) {
					if (e.getKeyCode() == KeyEvent.VK_Q) { // when q is pressed merge top left
						grid = MergeTopLeft.mergeTopLeft(grid, row, column); // modify grid according to key
						if (!equal(grid, lastGrid)) { // if current move change the grid, then update score and spawn
														// new node
							score += 1;
							grid = Spawn.spawn(grid);
						}
						keyState = "Q"; // update keyState
						copyArray(); // copy the lastMove
						game.repaint(); // update graphic
						/************
						 * Same comment applies to all the key event below
						 ************/

					} else if (e.getKeyCode() == KeyEvent.VK_Z) { // when z is pressed merge bottom left

						grid = MergeBotLeft.mergeBotLeft(grid, row, column);
						if (!equal(grid, lastGrid)) {
							score += 1;
							grid = Spawn.spawn(grid);
						}
						keyState = "Z";
						copyArray(); // copy the lastMove
						game.repaint();
					} else if (e.getKeyCode() == KeyEvent.VK_C) { // when c is pressed merge bottom right

						grid = MergeBotRight.mergeBotRight(grid, row, column);

						if (!equal(grid, lastGrid)) {
							score += 1;
							grid = Spawn.spawn(grid);
						}
						copyArray(); // copy the lastMove
						keyState = "C";

						game.repaint();
					} else if (e.getKeyCode() == KeyEvent.VK_E) {// when e is pressed merge top right

						grid = MergeTopRight.mergeTopRight(grid, row, column);

						if (!equal(grid, lastGrid)) {
							score += 1;
							grid = Spawn.spawn(grid);
						}
						keyState = "E";
						copyArray(); // copy the lastMove
						game.repaint();
					}

				}

				if (e.getKeyCode() == KeyEvent.VK_W) { // if w is pressed, merge to top
					grid = MergeUp.mergeUp(grid);

					if (!equal(grid, lastGrid)) {
						score += 1;
						grid = Spawn.spawn(grid);
					}
					keyState = "W";
					copyArray(); // copy the lastMove
					game.repaint();
				}

				else if (e.getKeyCode() == KeyEvent.VK_S) {// if S pressed merge down

					grid = MergeDown.mergeDown(grid);
			
					if (!equal(grid, lastGrid)) {
						score += 1;
						grid = Spawn.spawn(grid);
					}
					keyState = "S";
					copyArray(); // copy the lastMove
					game.repaint();
				} else if (e.getKeyCode() == KeyEvent.VK_A) { // if a pressed, merge left

					grid = MergeLeft.mergeLeft(grid);
					if (!equal(grid, lastGrid)) {
						score += 1;
						grid = Spawn.spawn(grid);
					}
					copyArray(); // copy the lastMove
					keyState = "A";
					game.repaint();
				} else if (e.getKeyCode() == KeyEvent.VK_D) { // if d pressed merge left

					grid = MergeRight.mergeRight(grid);

					if (!equal(grid, lastGrid)) {
						score += 1;
						grid = Spawn.spawn(grid);
					}
					copyArray(); // copy the lastMove
					keyState = "D";
					game.repaint();
				}
				// Press R to reset game grid
				if (e.getKeyCode() == KeyEvent.VK_R) { // if R pressed reset game grid

					grid = new int[row][column];
					grid = Spawn.spawn(grid);
					score = 0;
					// clear current score and grid
					
					try {
						clear(curScoref);
						clear(gridf);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					game.repaint();
				}

			}

		}
		/**
		 * check if game is over
		 * @param keyEvent, A,S,D,W,Q,E,Z,C
		 */
		
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			if (!gameOver) {
				// won = 2, loss = 1, game not end = 0
				// if won
				if (GameOver.gameOver(grid) == 2) {
					win = true;
					gameOver = true;

					// update highest score in the txt file
					try {
						// refresh the history if game end
						clear(gridf);
						// update highest score if the current score greater than history highest score
						if (read(highScoref).length() != 0) {
							if (score > Integer.parseInt(read(highScoref).split(" ")[0])) {
								highestScore(score);
							}
						} else if (read(highScoref).length() == 0) {
							highestScore(score);
						}
					} catch (IOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}

					// stop for 2 second after win
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					// change to result state
					state = 4;
					game.repaint();
				}

				// if diagonal check = lose
				else if (row == column) {
					if (GameOver.gameOver(grid) == 1 && GameOver.diagGameover(grid)) {
						gameOver = true;
						// clear the game history if loss
						try {
							
							clear(gridf);
							// update highest score
							if (read(highScoref).length() != 0) {
								if (score > Integer.parseInt(read(highScoref).split(" ")[0])) {
									highestScore(score);
								}
							} else if (read(highScoref).length() == 0) {
								highestScore(score);
							}
						} catch (IOException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}

						// stop for 2 seconds if the game ends
						try {
							Thread.sleep(2000);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

						// change to result state
						state = 4;
						game.repaint();
						
					}
				}
				// if straight check = lose
				else {
					// same comment as above
					if (GameOver.gameOver(grid) == 1) {
						gameOver = true;

						// update highest score and game history
						try {
							clear(gridf);
							if (read(highScoref).length() != 0) {
								if (score > Integer.parseInt(read(highScoref).split(" ")[0])) {
									highestScore(score);
								}
							} else if (read(highScoref).length() == 0) {
								highestScore(score);
							}
						} catch (IOException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}

						// stop for 2 seconds if the game ends
						try {
							Thread.sleep(2000);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

						// change to result state
						state = 4;
						game.repaint();
						
					}
				}

			}

		}

		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub

		}

	}

	// mouse event handler
	private class Handlerclass implements MouseListener {
		/**
		 * click buttons
		 * @param mouseEvent e
		 */
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
						try {
							dimensions();

						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
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
					
					try {
						dimensions();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					game.repaint();

				}
			}
			// game state
			else if (state == 2) {
				// if quit is pressed
				if (e.getX() > scrW - buttonSize - 15 && e.getX() < scrW - buttonSize - 15 + buttonSize
						&& e.getY() > scrH && e.getY() < scrH + 50) {
					frame.setSize(screenWidth, screenHeight);
					//reset score
					
					state = 0; // return to home
					// Save grid to saveGrid.txt if quit button pressed
					try {
						saveGrid();
						saveScore();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					score = 0;
					keyState = " ";
					scrW = screenWidth;
					scrH = screenHeight;
					game.repaint();
				}

			}
			// game end state
			else if (state == 4) {

				if (e.getY() > scrH / 2 + scrH / 8 && e.getY() < scrH / 2 + scrH / 8 + scrH / 7) {
					// replay button
					if (e.getX() > scrW / 16 && e.getX() < scrW / 16 + scrW / 2 - scrW / 8) {
						//initialize the game again
						try {
							dimensions();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						//reset score
						score = 0;
						//go back to game state
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

		
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub

		}
	}

	/**********
	 * THE DRAW FUNCTION
	 **********/
	class GameDisplay extends JPanel {
		private Image winImg, topLeft, topRight, botLeft, botRight, left, right, top, bot, bgimg, buttonImg,
				dimensionImg, instImg, sizeImg, img0, img2, img4, img8, img16, img32, img64, img128, img256, img512,
				img1024, img2048, img4096, img8192, gameImg;
		/**
		 * load images
		 * @see Image
		 */
		public void createImage() {
			// image for nodes with different number
			img0 = new ImageIcon(this.getClass().getResource("img\\0.png")).getImage();
			img2 = new ImageIcon(this.getClass().getResource("img\\2.png")).getImage();
			img4 = new ImageIcon(this.getClass().getResource("img\\4.png")).getImage();
			img8 = new ImageIcon(this.getClass().getResource("img\\8.png")).getImage();
			img16 = new ImageIcon(this.getClass().getResource("img\\16.png")).getImage();
			img32 = new ImageIcon(this.getClass().getResource("img\\32.png")).getImage();
			img64 = new ImageIcon(this.getClass().getResource("img\\64.png")).getImage();
			img128 = new ImageIcon(this.getClass().getResource("img\\128.png")).getImage();
			img256 = new ImageIcon(this.getClass().getResource("img\\256.png")).getImage();
			img512 = new ImageIcon(this.getClass().getResource("img\\512.png")).getImage();
			img1024 = new ImageIcon(this.getClass().getResource("img\\1024.png")).getImage();
			img2048 = new ImageIcon(this.getClass().getResource("img\\2048.png")).getImage();
			img4096 = new ImageIcon(this.getClass().getResource("img\\4096.png")).getImage();
			img8192 = new ImageIcon(this.getClass().getResource("img\\8192.png")).getImage();

			// Arrow image
			topLeft = new ImageIcon(this.getClass().getResource("img\\topLeft.png")).getImage();
			topRight = new ImageIcon(this.getClass().getResource("img\\topRight.png")).getImage();
			botLeft = new ImageIcon(this.getClass().getResource("img\\botLeft.png")).getImage();
			botRight = new ImageIcon(this.getClass().getResource("img\\botRight.png")).getImage();
			top = new ImageIcon(this.getClass().getResource("img\\top.png")).getImage();
			bot = new ImageIcon(this.getClass().getResource("img\\bot.png")).getImage();
			left = new ImageIcon(this.getClass().getResource("img\\left.png")).getImage();
			right = new ImageIcon(this.getClass().getResource("img\\right.png")).getImage();

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
			dimensionImg = new ImageIcon(this.getClass().getResource("img\\dimensionButton.png")).getImage(); // change dimension button image
			buttonImg = new ImageIcon(this.getClass().getResource("img\\button.png")).getImage();// ellipse
																												// button
																												// image
			bgimg = new ImageIcon(this.getClass().getResource("img\\state1Background.jpeg")).getImage(); // home
																														// state
																														// back
																														// ground
																														// image
		}
		/**
		 * draw function
		 * @see shapes, Image
		 */
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
				// You just pressed
				if (keyState.equals("Q"))
					g.drawImage(topLeft, scrW / 2 - 40, scrH, 60, 60, this);
				if (keyState.equals("E"))
					g.drawImage(topRight, scrW / 2 - 40, scrH, 60, 60, this);
				if (keyState.equals("Z"))
					g.drawImage(botLeft, scrW / 2 - 40, scrH, 60, 60, this);
				if (keyState.equals("C"))
					g.drawImage(botRight, scrW / 2 - 40, scrH, 60, 60, this);
				if (keyState.equals("A"))
					g.drawImage(left, scrW / 2 - 40, scrH, 60, 60, this);
				if (keyState.equals("D"))
					g.drawImage(right, scrW / 2 - 40, scrH, 60, 60, this);
				if (keyState.equals("S"))
					g.drawImage(bot, scrW / 2 - 40, scrH, 60, 60, this);
				if (keyState.equals("W"))
					g.drawImage(top, scrW / 2 - 40, scrH, 60, 60, this);
				// g.drawString("You just pressed: "+
				// keyState,scrW/2-g.getFontMetrics().stringWidth("You just pressed: "+
				// keyState)/2, scrH+ 25 + fontHight);

				/*******************************************
				 * graphical representation of grid
				 ****************************************/
				g.setFont(new Font("TimesRoman", Font.PLAIN, nodeWidth / 4));
				for (int row = 0; row < grid.length; row++) {

					for (int column = 0; column < grid[0].length; column++) {

						String num = Integer.toString(grid[row][column]);
						int drawInt = grid[row][column];

						// draw different image according to node number
						switch (drawInt) {
							case 0:
								g.drawImage(img0, column * (nodeWidth + space), row * (nodeHeight + space), nodeWidth,
										nodeHeight, this);
								break;
							case 2:
								g.drawImage(img2, column * (nodeWidth + space), row * (nodeHeight + space), nodeWidth,
										nodeHeight, this);
								break;
							case 4:
								g.drawImage(img4, column * (nodeWidth + space), row * (nodeHeight + space), nodeWidth,
										nodeHeight, this);
								break;
							case 8:
								g.drawImage(img8, column * (nodeWidth + space), row * (nodeHeight + space), nodeWidth,
										nodeHeight, this);
								break;
							case 16:
								g.drawImage(img16, column * (nodeWidth + space), row * (nodeHeight + space), nodeWidth,
										nodeHeight, this);
								break;
							case 32:
								g.drawImage(img32, column * (nodeWidth + space), row * (nodeHeight + space), nodeWidth,
										nodeHeight, this);
								break;
							case 64:
								g.drawImage(img64, column * (nodeWidth + space), row * (nodeHeight + space), nodeWidth,
										nodeHeight, this);
								break;
							case 128:
								g.drawImage(img128, column * (nodeWidth + space), row * (nodeHeight + space), nodeWidth,
										nodeHeight, this);
								break;
							case 256:
								g.drawImage(img256, column * (nodeWidth + space), row * (nodeHeight + space), nodeWidth,
										nodeHeight, this);
								break;
							case 512:
								g.drawImage(img512, column * (nodeWidth + space), row * (nodeHeight + space), nodeWidth,
										nodeHeight, this);
								break;
							case 1024:
								g.drawImage(img1024, column * (nodeWidth + space), row * (nodeHeight + space), nodeWidth,
										nodeHeight, this);
								break;
							case 2048:
								g.drawImage(img2048, column * (nodeWidth + space), row * (nodeHeight + space), nodeWidth,
										nodeHeight, this);
								break;
							case 4096:
								g.drawImage(img4096, column * (nodeWidth + space), row * (nodeHeight + space), nodeWidth,
										nodeHeight, this);
								break;
							case 8192:
								g.drawImage(img8192, column * (nodeWidth + space), row * (nodeHeight + space), nodeWidth,
										nodeHeight, this);
								break;
						}

						if (grid[row][column] != 0) {
							g.setColor(buttonColor);// set node text color and draw node number
							g.drawString(num,
									column * (nodeWidth + space) + nodeWidth / 2 - g.getFontMetrics().stringWidth(num) / 2,
									row * (nodeHeight + space) + nodeHeight / 2 + fontHight);
						}

					}
				}
			}

			/**********
			 * DIMENSION STATE
			 ***********/
			else if (state == 3) {
				g.drawImage(bgimg, 0, 0, this); //draw background image
				g.setColor(buttonColor);
				//initialize the position of change in printing buttons
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
						10 + buttonSize + space + buttonSize / 2 - g.getFontMetrics().stringWidth(Integer.toString(row)) / 2,
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
						10 + buttonSize + space + buttonSize / 2 - g.getFontMetrics().stringWidth(Integer.toString(column)) / 2,
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
				try {
					if (read(highScoref).length() != 0) {
						if (Integer.parseInt(read(highScoref).split(" ")[0]) != 0) {

							Integer.parseInt(read(highScoref).split(" ")[0]);
							g.drawString("Highest Score: " + read(highScoref).split(" ")[0], scrW / 2
									- g.getFontMetrics().stringWidth("Highest Score: " + read(highScoref).split(" ")[0]) / 2,
									(int) (scrH / 1.685));
						}
					}

				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
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
