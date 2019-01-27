/** Initialize and hold all variables
 * @author Sherry Yuan
 * @version 1.0
 * @since 1.0
 * @date 2019/1/4
*/

package Circalize;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Var {
	public int scrW = 600, scrH = 600;
	public int r = 8;
	public long second = 1;
	public int score = 0;
	public boolean gameover = false;
	public int state;

	// circle game
	public boolean release = false, attach = true;
	public double g = 0.13, radius = 40, alpha = 270, spin = 18, v = radius * spin * Math.PI / 360, x = 170,
			y = scrH / 2, vx = Math.sin(radian(alpha)) * v, vy = Math.cos(radian(alpha)) * v;
	public ArrayList<Centers> center = new ArrayList<Centers>();
	public int curCenter = 0, start = 0, buttonr = scrH / 8, shadow_length = 13;
	//public String path = "C:\\Users\\deyun\\Documents\\HomeWork\\programming\\Java program\\Circalize\\src\\Circalize\\img\\";
	public File highScoref = new File("src\\Circalize\\highestScore.txt");
	public ArrayList<Shadow> shadows = new ArrayList<Shadow>();
	public ArrayList<Block> blocks = new ArrayList<Block>();

	public void init() {
		gameover = false;
		shadows = new ArrayList<Shadow>();
		blocks = new ArrayList<Block>();
		center = new ArrayList<Centers>();
		curCenter = 0;
		start = 0;
		release = false;
		attach = true;
		radius = 40;
		alpha = 270;
		spin = 18;
		score = 0;
		v = radius * spin * Math.PI / 360;
		x = 170;
		y = scrH / 2;
		vx = Math.sin(radian(alpha)) * v;
		vy = Math.cos(radian(alpha)) * v;
		center.add(new Centers(scrW, scrH));
		center.get(0).x = 70;
		x = (center.get(curCenter).x);
		y = (int) (center.get(curCenter).y + radius);
		
	}
	public Var(int scrW, int scrH) {
		this.scrW = scrW;
		this.scrH = scrH;
	}
	public Var() {

	}
	/**
	 * double radian(double beta) 
	 * convert degree to radian
	 * @param beta degree in degree
	 * @return beta * Math.PI / 180 beta in radian
	 */
	public int curx() {
		return center.get(curCenter).x;
	}
	/**
	 * double radian(double beta) 
	 * convert degree to radian
	 * @param beta degree in degree
	 * @return beta * Math.PI / 180 beta in radian
	 */
	public int cury() {
		return center.get(curCenter).y;
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

	/**write(String s, File f)
	 * clear the file and rewrite it with new numbers
	 * 
	 * @param file   a directory to the file to be read
	 * @param String a string to be write to file
	 */
	public void write(String s, File f) throws IOException {
		PrintWriter fw = new PrintWriter(f);
		fw.write(s);
		fw.close();
	}
	/**highestScore(int score)
	 * write highest score to the file
	 * 
	 * @param score
	 *     write score to file
	 */
	public void highestScore(int score) throws IOException {
		write(Integer.toString(score), highScoref);
	}
	/** read(File f)
	 * read the score
	 * 
	 * @param file   a directory to the file to be read
	 */
	public int read(File f) throws IOException {
		Scanner x = new Scanner(f);
		String s = "";

		while (x.hasNext()) {
			s += x.next();
		}
		return Integer.parseInt(s);

	}

}
