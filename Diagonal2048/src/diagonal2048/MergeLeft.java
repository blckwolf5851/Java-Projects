package diagonal2048;
/***********
 * A main program merge the grid to left
 * 
 * @author Sherry Yuan
 * @version 1.0 (2018/6/2)
 * ****************
 */
public class MergeLeft {
	/*
	public static void main(String[] arg) {
		int[][] grid = {{32,16,16,4},{2,0,2,4}};
		int[][] mergedgrid = mergeLeft(grid);
		for(int i = 0; i < mergedgrid.length; i++) {
			for(int j = 0; j < mergedgrid[i].length; j++) {
				System.out.print(mergedgrid[i][j]+" ");
			}
			System.out.println();
		}
		
	}
	*/
	/**
	 * mergeGrid to Left
	 * @param int[][], 2D array to be modified
	 * @return int[][], resulting 2D array
	 */
	public static int[][] mergeLeft (int[][] grid) {
		//for each row, shift left
		//keep track of the merge, make sure each column merge 1 time maximum
		int[][] merged = new int[grid.length][grid[0].length];
		for(int row = 0; row < grid.length; row++) {
			//for each column in each row
			int column = 0;
			while (column < grid[row].length - 1){
				//System.out.println(column);
				int replaceInd = column + 1;
				//find index after current zero index that is non-zero
				for(int afterColumn = column + 1; afterColumn < grid[row].length; afterColumn ++) {
					if(grid[row][afterColumn] != 0) {
						replaceInd = afterColumn;
						break;
					}
				}
				if(grid[row][column] == 0) {
					
					//initialize the first index to be non zero
					if(column == 0) {
						grid[row][column] = grid[row][replaceInd];
						grid[row][replaceInd] = 0;
						column++;
					}
					//if column > 0
					else {
						//if the non zero index after current position is equal with the last position, then merge
						if(merged[row][column-1]!= 1 && grid[row][replaceInd] == grid[row][column-1]) {
							grid[row][column-1] += grid[row][replaceInd];
							grid[row][replaceInd] = 0;
							merged[row][column-1] = 1;
						}
						//else just replace the zero
						else {
							grid[row][column] = grid[row][replaceInd];
							grid[row][replaceInd] = 0;
							column ++;
						}       
					}
					
				}
				else {
					if(grid[row][replaceInd]==grid[row][column] && merged[row][column]==0){
						grid[row][column]+=grid[row][replaceInd];
						grid[row][replaceInd] = 0;
						merged[row][column] = 1;
						
					}
					column++;
				}
			}
		}
		return grid;
	}
}
