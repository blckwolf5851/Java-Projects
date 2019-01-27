import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Action_Handler implements ActionListener {

	Var var = new Var();
	Main main = new Main();
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		var.y += 20;
		var.y += var.v;
		var.v += var.a;
		var.second++;

		main.game.repaint();
		if (var.second % 20 == 0) {
			System.out.println(var.score);
			var.score++;
			var.pipX.add(var.screenSize);
			var.pipH.add(var.randHeight);
			var.pipS.add(var.randSpace);
			var.randSpace = (int) (Math.random() * 120) + 80;
			var.randHeight = (int) (Math.random() * 150) + 40;
			System.out.println(var.score);
		}

		for (int i = 0; i < var.pipX.size(); i++) {
			int value = var.pipX.get(i);
			var.difficultS -= 4;
			var.space -= 10;

			var.pipX.set(i, value - 10);

		}

		if (var.pipX.size() != 0 && var.pipX.get(0) < 0 - var.width) {
			var.pipX.remove(0);
			var.pipH.remove(0);
			var.pipS.remove(0);
		}

		for (int i = 0; i < var.pipX.size(); i++) {
			if (var.x + 2 * var.r < var.pipX.get(i) + var.width && var.x + 2 * var.r > var.pipX.get(i)) {
				if (var.y < var.pipH.get(i) || var.y + 2 * var.r > var.pipH.get(i) + var.pipS.get(i)) {
					var.gameover = true;
				}
			}
		}

		if (var.y < 0 || var.y + 2 * var.r > var.screenSize) {
			var.gameover = true;
		}

		main.game.repaint();
	}
}
