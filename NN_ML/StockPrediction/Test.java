import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.*;
import org.ejml.data.DMatrixRMaj;
import org.ejml.equation.Equation;
import org.ejml.dense.row.CommonOps_DDRM;
import org.ejml.simple.SimpleMatrix;

public class Test {
	/******************
	 * inputSize = #feature num_hidden_Layer = neural network architecture
	 * hiddenSize = number of feature in the activation layer outputSize = number of
	 * classes
	 ******************/
	public static int m, n, labels;
	public static int[] architect;
	public static SimpleMatrix X, y, test;
	public static ArrayList<SimpleMatrix> thetas;
	public static ArrayList<Double> J = new ArrayList<Double>();

	public static void main(String[] arg) throws IOException {
		// load data from stockData.csv

		File file = new File("stockData.csv");
		LoadCSV loadData = new LoadCSV(file);
		loadData.read();
		n = loadData.getN();
		m = loadData.getM();
		labels = loadData.getLabels();
		X = loadData.getX();
		y = loadData.getY();
		test = loadData.getTest();
		/*
		double[][] dataX = { { 3, 6, 5, 8, 7, 6, 0.2, 5, 1, 0.9, 7 }, { 6, 1, 0.5, 7, 8, 0.4, 6, 1, 6, 3, 4 },
				{ 8, 3, 0.6, 1, 1, 5, 8, 3, 4, 5, 1 }, { 9, 2, 0.6, 6, 8, 2, 7, 2, 1, 4, 3 },
				{ 6, 1, 5, 7, 8, 4, 6, 0.1, 6, 3, 4 } };
		double[][] datay = { { 0 }, { 0 }, { 1 }, { 1 }, { 0 } };

		n = 11;
		labels = 1;
		X = new SimpleMatrix(dataX);
		y = new SimpleMatrix(datay);
		 */
		// set structure of neural network architecture
		int[] structure = { n, 10, labels };
		Test NNML = new Test(structure);

		// create 3D arraylist of thetas
		thetas = new ArrayList<SimpleMatrix>();
		for (int i = 0; i < architect.length - 1; i++) {
			Random rnd = new Random();
			SimpleMatrix theta = SimpleMatrix.random_DDRM(architect[i + 1], architect[i] + 1, -3, 3, rnd);
			thetas.add(theta);
		}

		double lambda = 0.01;
		train(120, 1.5, lambda, X, y);
		// ArrayList<SimpleMatrix> grad = costFunction(lambda);
		predict(X);

	}

	// allow set up Neural Network Architect
	Test(int[] architect) {
		this.architect = architect;
	}

	public static ArrayList costFunction(double lambda, SimpleMatrix X, SimpleMatrix y) {

		ArrayList<SimpleMatrix> grad = new ArrayList<SimpleMatrix>();
		ArrayList<SimpleMatrix> activation = new ArrayList<SimpleMatrix>();
		ArrayList<SimpleMatrix> error = new ArrayList<SimpleMatrix>();

		Equation eq = new Equation();
		/*
		 * System.out.println("PRINT THETAS"); System.out.println(thetas.size());
		 * for(int i = 0 ; i < thetas.size(); i++) { thetas.get(i).print();
		 * System.out.println(); }
		 */
		int m = X.numRows();
		eq.alias(X, "X", m, "m");
		/********
		 * SETTING UP LABELS
		 *****/
		double[][] tempy = new double[m][labels];
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < labels; j++) {
				if (labels > 1) {
					tempy[i][(int) (y.get(i, 0)) - 1] = 1;
				} else {
					if ((int) (y.get(i, 0)) == 1) {
						tempy[i][j] = 1;
					}
				}
			}
		}
		SimpleMatrix tempY = new SimpleMatrix(tempy);
		eq.alias(X, "X", m, "m", tempY, "y");

		/*******
		 * FORWARD PROPAGATION
		 ******/
		activation.add(X.transpose());
		for (int i = 0; i < thetas.size(); i++) {
			/******************************************************
			 * compute "(1 + exp(-(theta * [ones(1, m); a]))) .^ -1"
			 ******************************************************/
			// set up activation
			eq.alias(activation.get(activation.size() - 1), "a");
			eq.process("a1 = [ones(1, m); a]");
			SimpleMatrix a = eq.lookupSimple("a1");

			// setup theta
			SimpleMatrix theta = thetas.get(i);

			// calculation & append
			SimpleMatrix act = ((theta.mult(a).scale(-1)).elementExp().plus(1)).elementPower(-1);
			activation.add(act);
		}

		/********
		 * Compute Cost
		 **********/
		SimpleMatrix h = activation.get(activation.size() - 1); // output layer
		eq.alias(h, "h");
		eq.process("J = (0.1/m)*sum(((-y) .* log(h') - (1-y).*log(1- h'))(:))");
		double sumtheta = 0;
		for (int i = 0; i < thetas.size(); i++) {
			eq.alias(thetas.get(i), "theta");
			eq.process("sumTheta = sum(theta(:,1:)(:) .^ 2)");
			sumtheta += eq.lookupDouble("sumTheta");
		}
		System.out.println("sumTheta = " + sumtheta * (lambda / m));
		System.out.println("J = " + eq.lookupDouble("J"));
		J.add(sumtheta * (lambda / m) + eq.lookupDouble("J"));

		/*******
		 * BACKPROPAGATION
		 ******/
		error.add(h.minus(tempY.transpose()));
		for (int i = activation.size() - 2; i >= 1; i--) {
			eq.alias(error.get(0), "d", activation.get(i), "a", thetas.get(i), "theta");

			// System.out.println("error process a,d,t");
			// activation.get(i).print();
			// error.get(0).print();
			// thetas.get(i).print();

			eq.process("temp_theta = [theta(:,1:)]'");
			eq.process("a1 = 1-a");
			SimpleMatrix theta = eq.lookupSimple("temp_theta");
			SimpleMatrix a = activation.get(i);
			SimpleMatrix a_temp = eq.lookupSimple("a1");
			SimpleMatrix d = error.get(0);
			SimpleMatrix nextD = theta.mult(d).elementMult(a.elementMult(a_temp));
			// System.out.println("theta*d");
			// eq.lookupSimple("test").print();
			// eq.process("nextD = ([theta(:,1:)]' * d) .* (a .* (1 - a))");
			error.add(0, nextD);
		}
		/*
		 * System.out.println("PRINT ERROR"); System.out.println(error.size()); for(int
		 * i = 0 ; i < error.size(); i++) { error.get(i).print(); System.out.println();
		 * }
		 */

		/********
		 * DERIVATIVE TERM
		 ******/
		for (int i = 0; i < thetas.size(); i++) {
			eq.alias(activation.get(i), "a", error.get(i), "d", thetas.get(i), "theta");
			/*
			 * System.out.println("Activation"); activation.get(i).print();
			 * System.out.println("Error"); error.get(i).print();
			 * System.out.println("Theta"); thetas.get(i).print();
			 */

			String numrow = Integer.toString(architect[i + 1]);
			String equa = "theta_temp = [zeros(" + numrow + ",1), theta(:, 1:)]";
			eq.process(equa);
			eq.process("a_temp = [ones(1,m); a]'");

			SimpleMatrix theta = eq.lookupSimple("theta_temp");
			SimpleMatrix a = eq.lookupSimple("a_temp");
			SimpleMatrix d = error.get(i);
			// SimpleMatrix derivative = d.mult(a).scale(1.0/m);
			SimpleMatrix D = d.mult(a).scale(1.0 / m);

			SimpleMatrix derivative = D.plus(theta.scale((lambda / m)));

			// eq.process("grad = (1/m) * (d * [ones(1,m); a]') + (" + lam + "/m) *
			// [zeros("+numrow+",1), theta(:, 1:)]");
			grad.add(derivative);
		}
		/*
		 * System.out.println("PRINT DERIVATIVE"); System.out.println(grad.size());
		 * for(int i = 0 ; i < grad.size(); i++) { grad.get(i).print();
		 * System.out.println(); }
		 */

		return grad;

	}

	public static void train(int maxIter, double alpha, double lambda, SimpleMatrix X, SimpleMatrix y) {
		int i = 0;
		if (J.size() > 0) {
			while (J.get(J.size() - 1) > Math.pow(10, -9) && i < maxIter) {
				ArrayList<SimpleMatrix> grad = costFunction(lambda, X, y);
				for (int j = 1; j < grad.size(); j++) {
					SimpleMatrix theta = thetas.get(j);
					SimpleMatrix gradient = grad.get(j);
					thetas.set(j, theta.minus(gradient.scale(alpha)));
				}
				i++;
			}
		} else {
			while (i < maxIter) {
				ArrayList<SimpleMatrix> grad = costFunction(lambda, X, y);
				for (int j = 0; j < grad.size(); j++) {
					SimpleMatrix theta = thetas.get(j);
					SimpleMatrix gradient = grad.get(j);
					thetas.set(j, theta.minus(gradient.scale(alpha)));
				}
				i++;
			}
		}
		System.out.println(J.get(J.size() - 1));
		System.out.println(i);
	}

	public static void predict(SimpleMatrix test) {
		ArrayList<SimpleMatrix> activation = new ArrayList<SimpleMatrix>();
		Equation eq = new Equation();
		activation.add(test.transpose());
		int numExp = test.numRows();
		for (int i = 0; i < thetas.size(); i++) {
			/******************************************************
			 * compute "(1 + exp(-(theta * [ones(1, m); a]))) .^ -1"
			 ******************************************************/
			// set up activation
			eq.alias(activation.get(activation.size() - 1), "a", numExp, "m");
			eq.process("a1 = [ones(1, m); a]");
			SimpleMatrix a = eq.lookupSimple("a1");

			// setup theta
			SimpleMatrix theta = thetas.get(i);

			// calculation & append
			SimpleMatrix act = ((theta.mult(a).scale(-1)).elementExp().plus(1)).elementPower(-1);
			activation.add(act);
		}
		SimpleMatrix h = activation.get(activation.size() - 1); // output layer

		if (labels == 1) {
			for (int i = 0; i < h.numRows(); i++) {
				for (int j = 0; j < h.numCols(); j++) {
					h.set(i, j, Math.round(h.get(i, j)));
				}
			}
			h.print();

		} else {
			eq.alias(h, "h");
			eq.process("yval = max(h,1)");
			SimpleMatrix pred = eq.lookupSimple("yval");
		}

	}

}
