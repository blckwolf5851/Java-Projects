import java.util.ArrayList;

public class Var {

	public int screenSize = 400;
	public int r = 8, width = 20, x = 60, y = screenSize / 2;
	public long second = 1;
	public int v = -15;
	public double a = 0.8;
	public int score = 0;
	public boolean gameover = false;
	public ArrayList<Integer> pipX = new ArrayList<Integer>();
	public ArrayList<Integer> pipH = new ArrayList<Integer>();
	public int difficultH = 40, difficultS = 80;
	public int space = screenSize / 4;
	public int randSpace = (int) (Math.random() * space) + difficultS,
			randHeight = (int) (Math.random() * screenSize / 2) + difficultH;
	public ArrayList<Integer> pipS = new ArrayList<Integer>();
	//public GameDisplay game = new GameDisplay();
	public Var(int screenSize) {
		this.screenSize = screenSize;
	}

	public Var() {

	}

}
