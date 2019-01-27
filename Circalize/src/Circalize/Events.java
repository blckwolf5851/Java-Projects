/** Event listener class
 * @author Sherry Yuan
 * @version 1.0
 * @since 1.0
 * @date 2019/1/4
*/

package Circalize;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Events implements MouseListener, KeyListener {
	Var var;
	Paint game;
	Modify modify;

	Events(Var var, Paint game) {
		this.var = var;
		this.game = game;
		this.modify = new Modify(var);
	}

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
		if (var.state == 0) {
			// if game button pressed
			if (e.getY() > var.scrW / 2 - 15 && e.getY() < var.scrH / 2 - 15 + 2 * var.buttonr) {
				if (e.getX() > var.scrW / 2 - 3 * var.buttonr
						&& e.getX() < var.scrW / 2 - 3 * var.buttonr + 2 * var.buttonr) {
					var.state = 2; // game state
					var.init();
					game.repaint();
				}
				// if instruction button pressed
				else if (e.getX() > var.scrW / 2 + var.buttonr && e.getX() < var.scrW / 2 + 3 * var.buttonr) {
					var.state = 1;// instruction state
					game.repaint();
				}
			}
		}

		else if (var.state == 1) {
			// if back button pressed
			if (e.getX() > var.scrW / 2 - var.buttonr && e.getX() < var.scrW / 2 + var.buttonr) {
				var.state = 0;
				game.repaint();
			}
		} else if (var.state == 1) {
			var.state = 2;
			game.repaint();
		} else if (var.state == 2) {
			if (var.start == 0)
				var.start = 1;
			if (var.start == 1) {
				if (!var.release) {
					if (var.attach) {
						var.vx = Math.sin(Var.radian(var.alpha)) * var.v;
						var.vy = Math.cos(Var.radian(var.alpha)) * var.v;
					}
					var.release = true;
					var.attach = false;

				}

				else if (var.release && !var.attach) {

					var.release = false;
					// System.out.println("newcenter");
					modify.closest_center();
					// var.v = var.radius*var.spin*Math.PI/360;
				}
			}

			game.repaint();
		} else if (var.state == 3) {
			if (e.getY() > var.scrH / 2 + var.scrH / 8 && e.getY() < var.scrH / 2 + var.scrH / 8 + var.scrH / 7) {
				// replay button
				if (e.getX() > var.scrW / 16 && e.getX() < var.scrW / 16 + var.scrW / 2 - var.scrW / 8) {
					var.init();
					// go back to game state
					var.state = 2;

					game.repaint();
				}
				// home button
				else if (e.getX() > var.scrW / 2 + var.scrW / 16
						&& e.getX() < var.scrW / 2 + var.scrW / 16 + var.scrW / 2 - var.scrW / 8) {
					var.state = 0;
					var.init();
					game.repaint();
				}
			}
		}

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent k) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		// switch between gravitation and normal mode (this is one of the game strategy)
		if (e.getKeyCode() == KeyEvent.VK_G) {
			if (var.g != 0) {
				var.g = 0;
			} else {
				var.g = 0.13;
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

}
