package diagonal2048;
/***********
 * A main program merge the grid to top right
 * 
 * @author Sherry Yuan
 * @version 1.0 (2018/6/2)
 * ****************
 */
public class MergeTopRight {
	/*
	public static void main(String[] arg) {
		int[][] grid = {{4,2,2,4,4},{4,0,2,4,4},{0,0,2,4,4},{4,0,4,4,2}, {0,2,4,4,2}};
		for(int i = 0; i < grid.length; i++) {
			for(int j = 0; j < grid[i].length; j++) {
				System.out.print(grid[i][j]+" ");
			}
			System.out.println();
		}
		System.out.println();
		int[][] mergedgrid = mergeTopRight(grid,grid.length, grid[0].length);
		for(int i = 0; i < mergedgrid.length; i++) {
			for(int j = 0; j < mergedgrid[i].length; j++) {
				System.out.print(mergedgrid[i][j]+" ");
			}
			System.out.println();
		}
		
	}
	*/
	/**
	 * mergeGrid to top right
	 * @param int[][], 2D array to be modified
	 * @return int[][], resulting 2D array
	 */
	public static int[][] mergeTopRight (int[][] grid, int totalRow, int totalCol) {
		int[][] merged = new int[grid.length][grid[0].length];
		int curRow = 1;
		//merge top left part
		//row from 0 - <curRow
		//column from curRow - >0
		//curRow <totalRow
		//row++, column --
		
		while (curRow < totalRow) {
			int row = 0;
			int column = curRow;
			while(row < curRow) {
				int[] nextInd = {row + 1, column - 1};
				while(nextInd[1] > 0) {
					if(grid[nextInd[0]][nextInd[1]] != 0) {
						break;
					}
					nextInd[0]++;
					nextInd[1]--;
				}
				if(grid[row][column] == 0) {
					if(row == 0) {
						grid[row][column] = grid[nextInd[0]][nextInd[1]];
						grid[nextInd[0]][nextInd[1]] = 0;
						row++;
						column--;
					}else {
						if(grid[row-1][column + 1] == grid[nextInd[0]][nextInd[1]] && merged[row-1][column + 1] == 0) {
							grid[row-1][column + 1] += grid[nextInd[0]][nextInd[1]];
							grid[nextInd[0]][nextInd[1]] = 0;
							merged[row-1][column + 1] = 1;
						
						}else {
							grid[row][column] = grid[nextInd[0]][nextInd[1]];
							grid[nextInd[0]][nextInd[1]] = 0;
							row++;
							column--;
						}
					}
				}
				else if(grid[row][column] != 0) {
					if(grid[row][column] == grid[nextInd[0]][nextInd[1]] && merged[row][column] == 0) {
						grid[row][column] += grid[nextInd[0]][nextInd[1]];
						grid[nextInd[0]][nextInd[1]] = 0;
						merged[row][column] = 1;
					}
					row++;
					column--;
				}
				
				
			}
			curRow++;
		}
		
		
		//merge bottom right part
		curRow = 1;
		while (curRow < totalRow-1) {
			
			int row = curRow;
			int column = totalCol -1;
			while(row < totalRow - 1) {
				
				int[] nextInd = {row + 1, column - 1};
				//System.out.println(row + " " + column);
				while(nextInd[0] < totalRow-1) {
					if(grid[nextInd[0]][nextInd[1]] != 0) {
						break;
					}
					nextInd[0]++;
					nextInd[1]--;
				}
				/*
				System.out.println(nextInd[0] + " " + nextInd[1]);
				System.out.println();
				*/
				if(grid[row][column] == 0) {
					if(column == totalCol - 1) {
						grid[row][column] = grid[nextInd[0]][nextInd[1]];
						grid[nextInd[0]][nextInd[1]] = 0;
						row++;
						column--;
					}else {
						if(grid[row-1][column + 1] == grid[nextInd[0]][nextInd[1]] && merged[row-1][column + 1] == 0) {
							grid[row-1][column + 1] += grid[nextInd[0]][nextInd[1]];
							grid[nextInd[0]][nextInd[1]] = 0;
							merged[row-1][column + 1] = 1;
						
						}else {
							grid[row][column] = grid[nextInd[0]][nextInd[1]];
							grid[nextInd[0]][nextInd[1]] = 0;
							row++;
							column--;
						}
					}
				}
				else if(grid[row][column] != 0) {
					if(grid[row][column] == grid[nextInd[0]][nextInd[1]] && merged[row][column] == 0) {
						grid[row][column] += grid[nextInd[0]][nextInd[1]];
						grid[nextInd[0]][nextInd[1]] = 0;
						merged[row][column] = 1;
					}
					row++;
					column--;
				}
				
			}
			curRow++;
		}
		
		
		
		
		return grid;
	}
}
