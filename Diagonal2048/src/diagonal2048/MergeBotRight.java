package diagonal2048;
/***********
 * A main program merge the grid to bottom right
 * 
 * @author Sherry Yuan
 * @version 1.0 (2018/6/2)
 * ****************
 */
public class MergeBotRight {
	/*
	public static void main(String[] arg) {
		int[][] grid = {{4,2,2,4,2},{4,0,2,4,0},{0,0,2,4,4},{4,0,4,4,2}, {0,2,4,4,2}};
		for(int i = 0; i < grid.length; i++) {
			for(int j = 0; j < grid[i].length; j++) {
				System.out.print(grid[i][j]+" ");
			}
			System.out.println();
		}
		System.out.println();
		int[][] mergedgrid = mergeBotRight(grid,grid.length, grid[0].length);
		for(int i = 0; i < mergedgrid.length; i++) {
			for(int j = 0; j < mergedgrid[i].length; j++) {
				System.out.print(mergedgrid[i][j]+" ");
			}
			System.out.println();
		}
		
	}
	*/
	/**
	 * mergeGrid to bottom right
	 * @param int[][], 2D array to be modified
	 * @return int[][], resulting 2D array
	 */
	public static int[][] mergeBotRight (int[][] grid, int totalRow, int totalCol) {
		//calculate the column
		int[][] merged = new int[grid.length][grid[0].length];
		int curRow = totalRow - 2;
		
		while(curRow >= 0) {
			int row = totalRow-1;
			while(row > curRow) {
				int column = row - curRow;
				
				//find the next index in the grid that is non zero
				int[] nextInd = {row - 1,row - 1-curRow};
				
				while(nextInd[1] > 0) {
					//nextInd[1] = nextInd[0] - curRow;
					if(grid[nextInd[0]][nextInd[1]] != 0) {
						break;
					}
					
					nextInd[0]--;
					nextInd[1]--;
				}
				//System.out.println(nextInd[0]);
				/*
				System.out.println(row + " " + column);
				System.out.println(grid[row][column]);
				System.out.println(nextInd[0]);
				System.out.println(nextInd[1]);
				System.out.println(grid[nextInd[0]][nextInd[1]]);
				System.out.println();
				*/
				if(grid[row][column] == 0) {
					if(row == totalRow - 1) {
						grid[row][column] = grid[nextInd[0]][nextInd[1]];
						grid[nextInd[0]][nextInd[1]] = 0;
						row--;
						
					
					}else {
						//if the last index is the same with next non zero
						if(grid[row+1][column+1] == grid[nextInd[0]][nextInd[1]] && merged[row+1][column+1] == 0) {
							grid[row+1][column+1] += grid[nextInd[0]][nextInd[1]];
							grid[nextInd[0]][nextInd[1]] = 0;
							merged[row+1][column+1] = 1;
						}
						else {
							grid[row][column] = grid[nextInd[0]][nextInd[1]];
							grid[nextInd[0]][nextInd[1]] = 0;
							row--;
						}
					}
					//if index is non zero and equal to next non zero, then merge
				}else {
					if(grid[row][column] == grid[nextInd[0]][nextInd[1]] && merged[row][column] == 0) {
						grid[row][column] += grid[nextInd[0]][nextInd[1]];
						grid[nextInd[0]][nextInd[1]] = 0;
						merged[row][column] = 1;
							
					}
					row--;
				}
				
			}
			curRow--;
		}
		
		curRow = totalRow - 2;
		
		int d = 1;
		while(curRow > 0) {
			
			int row = curRow;
			while(row > 0) {
				int column = row + d;
				
				//find the next index in the grid that is non zero
				int[] nextInd = {row - 1,row - 1 + d};
				
				while(nextInd[0] > 0) {
					if(grid[nextInd[0]][nextInd[1]] != 0) {
						break;
					}
					
					nextInd[0]--;
					nextInd[1]--;
				}
				
				
				
				if(grid[row][column] == 0) {
					if(row == curRow) {
						grid[row][column] = grid[nextInd[0]][nextInd[1]];
						grid[nextInd[0]][nextInd[1]] = 0;
						row--;
						
					
					}else {
						//if the last index is the same with next non zero
						if(grid[row+1][column+1] == grid[nextInd[0]][nextInd[1]] && merged[row+1][column+1] == 0) {
							grid[row+1][column+1] += grid[nextInd[0]][nextInd[1]];
							grid[nextInd[0]][nextInd[1]] = 0;
							merged[row+1][column+1] = 1;
						}
						else {
							grid[row][column] = grid[nextInd[0]][nextInd[1]];
							grid[nextInd[0]][nextInd[1]] = 0;
							row--;
						}
					}
					//if index is non zero and equal to next non zero, then merge
				}else {
					if(grid[row][column] == grid[nextInd[0]][nextInd[1]] && merged[row][column] == 0) {
						grid[row][column] += grid[nextInd[0]][nextInd[1]];
						grid[nextInd[0]][nextInd[1]] = 0;
						merged[row][column] = 1;
					}
					row--;
				}
				 
			}
			curRow--;
			d++;
		}
		return grid;
	}
}
