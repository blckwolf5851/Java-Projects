package diagonal2048;
/***********
 * A main program merge the grid to bottom right
 * 
 * @author Sherry Yuan
 * @version 1.0 (2018/6/2)
 * ****************
 */
public class MergeRight {
	/*
	public static void main(String[] arg) {
		int[][] grid = {{0,4,4,8},{2,0,2,4},{2,2,0,2},{2,2,2,2}};
		int[][] mergedgrid = mergeRight(grid);
		for(int i = 0; i < mergedgrid.length; i++) {
			for(int j = 0; j < mergedgrid[i].length; j++) {
				System.out.print(mergedgrid[i][j]+" ");
			}
			System.out.println();
		}
		
	}
	*/
	/**
	 * mergeGrid to right
	 * @param int[][], 2D array to be modified
	 * @return int[][], resulting 2D array
	 */
	public static int[][] mergeRight (int[][] grid) {
		
		//keep track of the merge, make sure each column merge 1 time maximum
		int[][] merged = new int[grid.length][grid[0].length];
		
		//for each row, shift right
		for(int row = 0; row < grid.length; row++) {
			
			//for each column in each row
			int column = grid[row].length -1;
			while (column > 0){
				//System.out.println(column);
				int replaceInd = column - 1;
				//find index after current zero index that is non-zero
				int afterColumn = column - 1;
				while(afterColumn >= 0) {
					if(grid[row][afterColumn] != 0) {
						replaceInd = afterColumn;
						break;
					}
					afterColumn--;
				}
				
				//if current index is 0
				if(grid[row][column] == 0) {
					
					//initialize the first index to be non zero
					if(column == grid[row].length -1 ) {
						grid[row][column] = grid[row][replaceInd];
						grid[row][replaceInd] = 0;
						column--;
					}
					//if column > 0
					else {
						//if the non zero index after current position is equal with the last position, then merge
						if(merged[row][column+1]==0 && grid[row][replaceInd] == grid[row][column+1]) {
							grid[row][column+1] += grid[row][replaceInd];
							grid[row][replaceInd] = 0;
							merged[row][column+1] = 1;
						}
						//else just replace the zero, then move on
						else {
							grid[row][column] = grid[row][replaceInd];
							grid[row][replaceInd] = 0;
							column --;
						}       
					}
					
				}
				
				//if current index is non-zero, then see if the next non zero index is the same, if true, then merge
				else {
					if(grid[row][replaceInd] == grid[row][column] && merged[row][column]==0){
						grid[row][column]+=grid[row][replaceInd];
						grid[row][replaceInd] = 0;
						merged[row][column] = 1;
					}
					column--;
				}
			}
		}
		return grid;
	}
}
