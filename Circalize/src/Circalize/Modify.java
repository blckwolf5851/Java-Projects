/** A class to background computation
 * @author Sherry Yuan
 * @version 1.0
 * @since 1.0
 * @date 2019/1/4
*/

package Circalize;

public class Modify {
	Var var;

	Modify(Var var) {
		this.var = var;
	}

	public void closest_center() {
		double minDist = 100000;
		for (int i = 0; i < var.center.size(); i++) {
			if (dist((int) Math.round(var.x), (int) Math.round(var.y), var.center.get(i).x,
					var.center.get(i).y) < minDist) {
				// System.out.println("set smallest center");
				minDist = dist((int) Math.round(var.x), (int) Math.round(var.y), var.center.get(i).x,
						var.center.get(i).y);
				var.curCenter = i;
			}
		}

	}

	public double dist(double x1, double y1, int x2, int y2) {
		return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
	}

}
