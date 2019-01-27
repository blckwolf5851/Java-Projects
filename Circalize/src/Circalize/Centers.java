/** A class that hold pin's x,y,speed position
 * @author Sherry Yuan
 * @version 1.0
 * @since 1.0
 * @date 2019/1/4
*/

package Circalize;

public class Centers {
	private int screenw, screenh;
	public int x = 400, y = (int) (Math.random() * 400);
	public int speed = (int)(Math.random() * 3) + 2;

	Centers(int screenw, int screenh) {
		this.screenw = screenw;
		this.screenh = screenh;
		x = screenw;
		y = (int) (Math.random() * screenh);
		speed = (int)(Math.random() * 3) + 2;
	}
}
