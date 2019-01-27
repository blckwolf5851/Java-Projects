
import java.io.File;
import java.io.IOException;
import org.ejml.data.DMatrixRMaj;
import org.ejml.equation.Equation;
import org.ejml.ops.MatrixIO;
import org.ejml.simple.SimpleMatrix;

public class LoadCSV {
	public static File filename;
	public static int m, n, labels;
	public static SimpleMatrix X, y, test;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	}
	LoadCSV(File filename){
		this.filename = filename;
	}
	
	public static void read() throws IOException {
		DMatrixRMaj data1 = MatrixIO.loadCSV(filename.getAbsolutePath(), true);
		Equation eq = new Equation();
		data1.print();
		SimpleMatrix data2 = SimpleMatrix.wrap(data1);//new SimpleMatrix(data1);
		data2.print();
		X = data2.extractMatrix(55, data2.numRows()-1, 0,  data2.numCols()-1);
		y = data2.extractMatrix(55,data2.numRows()-1,data2.numCols()-1,data2.numCols());
		test = data2.extractMatrix(data2.numRows()-1,data2.numRows(),0,data2.numCols()-1);
		m = X.numRows();
		n = X. numCols();
		eq.alias(y,"y");
		eq.process("labels = length(unique(y))");
		labels = eq.lookupInteger("labels");
		if(labels < 3) {
			labels = 1;
		}		
	}
	
	/*******
	 * ALLOW DATA ACCESS
	 * *********/
	public int getN() {
		return this.n;
	}
	public int getM() {
		return this.m;
	}
	public int getLabels() {
		return this.labels;
	}
	
	public SimpleMatrix getX() {
		return this.X;
	}
	public SimpleMatrix getY() {
		return this.y;
	}
	public SimpleMatrix getTest() {
		return this.test;
	}
	

}
