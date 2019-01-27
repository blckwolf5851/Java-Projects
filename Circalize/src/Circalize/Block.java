/** The block that ball should dodge
 * @author Sherry Yuan
 * @version 1.0
 * @since 1.0
 * @date 2019/1/4
*/

package Circalize;

public class Block {
	int x, y, w = 10, h;

	Block(int screenh) {
		this.x = screenh;
		this.y = (int) (Math.random() * screenh);
		this.h = (int) (Math.random() * screenh / 4) + screenh / 6;
	}
}
