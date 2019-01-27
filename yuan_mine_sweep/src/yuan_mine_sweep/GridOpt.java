package yuan_mine_sweep;

/***
 * Operations on Game grid
 * Include: discovering spaces, check nearby bombs, and convert userInput pixel into index
 * 
 * @author Sherry Yuan
 * @version 1.0 (2018/10/19)
 * **/
import java.util.ArrayList;
import java.util.Arrays;

public class GridOpt {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public static int[] getIndex(int x, int y, int box_pixel) {
		int[] pos = { (int) (y / box_pixel), (int) (x / box_pixel) };
		return pos;
	}

	public static int checkNearbyBomb(int[] ind, int[][] bomb) {
		int sum_bomb = 0;
		int[] combination = { -1, 0, 1 };
		for (int i = 0; i < combination.length; i++) {
			for (int j = 0; j < combination.length; j++) {
				int nearby_row = ind[0] + combination[i], nearby_col = ind[1] + combination[j];
				if (nearby_row >= 0 && nearby_row < bomb.length && nearby_col >= 0 && nearby_col < bomb[0].length) {
					if (bomb[nearby_row][nearby_col] == 1) {
						sum_bomb++;
					}
				}
			}
		}
		return sum_bomb;
	}

	static void explore(ArrayList<int[]> queue, int[] ind, int[][] grid, int[][] bomb) {
		int[] combination = { -1, 0, 1 };
		for (int i = 0; i < combination.length; i++) {
			for (int j = 0; j < combination.length; j++) {
				// int nearby_row = row + combination[i], nearby_col = col + combination[j];

				int[] index = { ind[0] + combination[i], ind[1] + combination[j] };
				if (index[0] >= 0 && index[0] < grid.length && index[1] >= 0 && index[1] < grid[0].length)
					if (grid[index[0]][index[1]] == 0 && bomb[index[0]][index[1]] == 0) // explore only if the index is
																						// not visited / no bomb on it
						queue.add(index);

			}
		}
	}

	/**
	 * Breadth first search
	 */
	public static void BFS(int[][] bomb, int[][] grid, int[] origen) {
		// grid || display: -1 = discovered, 0 = dark, > 0 is number of bomb beside
		// works only if [row][col] is not on bomb
		ArrayList<int[]> queue = new ArrayList<int[]>();
		queue.add(origen);
		while (queue.size() > 0) {
			// System.out.println("Size: "+queue.size());
			int[] ind = queue.get(0);
			// System.out.println(Arrays.toString(ind));
			queue.remove(0);

			if (checkNearbyBomb(ind, bomb) > 0) {
				grid[ind[0]][ind[1]] = checkNearbyBomb(ind, bomb);
			} else {
				grid[ind[0]][ind[1]] = -1;
				explore(queue, ind, grid, bomb);
			}

		}

	}

	/******
	 * Check if win the game
	 ****/
	public static boolean winnion(int[][] bomb, int[][] grid, int totalBomb) {
		int num_discover = 0;
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				if (grid[i][j] == -1) {
					num_discover++;
				}
			}
		}
		if (grid.length * grid[0].length - totalBomb == num_discover) {
			return true;
		} else {
			return false;
		}

	}

	public static boolean gameOver(int[][] bomb, int[] ind) {
		// bomb || bomb position: 1 = bomb, 0 = no bomb
		if (bomb[ind[0]][ind[1]] == 1) {
			return true;
		} else {
			return false;
		}
	}

}
