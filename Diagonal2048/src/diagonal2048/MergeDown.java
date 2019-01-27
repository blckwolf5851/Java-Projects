package diagonal2048;
/***********
 * A main program merge the grid to down
 * 
 * @author Sherry Yuan
 * @version 1.0 (2018/6/2)
 * ****************
 */
public class MergeDown {
	/*
	public static void main(String[] arg) {
		int[][] grid = {{4,2,2,4},{4,0,2,4}};
		int[][] mergedgrid = mergeDown(grid);
		for(int i = 0; i < mergedgrid.length; i++) {
			for(int j = 0; j < mergedgrid[i].length; j++) {
				System.out.print(mergedgrid[i][j]+" ");
			}
			System.out.println();
		}
		
	}
	*/
	/**
	 * mergeGrid to bottom
	 * @param int[][], 2D array to be modified
	 * @return int[][], resulting 2D array
	 */
	public static int[][] mergeDown (int[][] grid) {
		//for each column, shift down
		//keep track of the merge, make sure each node merge 1 time maximum
		int[][] merged = new int[grid.length][grid[0].length];
		for(int column = 0; column < grid[0].length; column++) {
			//for each row of column
			int row = grid.length-1;
			while (row > 0){
				//System.out.println(column);
				int replaceInd = row - 1;
				//find index after current zero index that is non-zero
				int afterRow = row - 1;
				while(afterRow >= 0) {
					if(grid[afterRow][column] != 0) {
						replaceInd = afterRow;
						break;
					}
					afterRow --;
				}
				if(grid[row][column] == 0) {
					
					//initialize the first index to be non zero
					if(row == grid.length - 1) {
						grid[row][column] = grid[replaceInd][column];
						grid[replaceInd][column] = 0;
						row--;
					}
					//if row > 0
					else {
						//if the non zero index after current position is equal with the last position, then merge
						if(merged[row+1][column] == 0 && grid[replaceInd][column] == grid[row+1][column]) {
							grid[row+1][column] += grid[replaceInd][column];
							grid[replaceInd][column] = 0;
							merged[row+1][column] = 1;
						}
						//else just replace the zero
						else {
							grid[row][column] = grid[replaceInd][column];
							grid[replaceInd][column] = 0;
							row --;
						}       
					}
					
				}
				else {
					if(grid[replaceInd][column]==grid[row][column] && merged[row][column] == 0){
						grid[row][column]+=grid[replaceInd][column];
						grid[replaceInd][column] = 0;
						merged[row][column] = 1;
						
					}
					row--;
				}
			}
		}
		return grid;
	}
}
