package polyDisplay;

import java.util.ArrayList;

public class Factor {
	public boolean checkRoot(double num, int[] poly)
	{
		double result = num*poly[0]+poly[1];
		for(int i = 2; i < poly.length;i++)
		{
			result *= num;
			result += poly[i];
			
		}
		
		//System.out.println(num);
		//System.out.println(result);
		if(result == 0.0) 
		{
			return true;
		}
		return false;
	}
	//A function that finds the root of the polynomial
	public double[] factor(String polynomial) 
	{
		String[] strArr = polynomial.split(" ");
		int[] polyno = new int[strArr.length];
		for(int i = 0; i < strArr.length; i ++) {
			polyno[i] = Integer.parseInt(strArr[i]);
		}
		ArrayList<Double> root = new ArrayList<Double>();
		int aInd = 1;
		int a = polyno[0];
		while(a ==0 && a < polyno.length) {
			
			a = polyno[aInd];
			aInd++;
		}
		int cInd = polyno.length-2;
		int c = polyno[polyno.length-1];
		while(c == 0 && cInd >= 0) {
			root.add((double) c);
			c = polyno[cInd];
			
			cInd--;
		}
		ArrayList<Double> C = new ArrayList<Double>();
		ArrayList<Double> A = new ArrayList<Double>();
		
		//find factor of c
		int cFac = 1;
		int aFac = 1;
		while (cFac <= Math.abs(c)) {
			if(Math.abs(c) % cFac == 0) {
				C.add((double)cFac);
			}
			cFac++;
		}
		while(aFac <= Math.abs(a)) {
			if (Math.abs(a) % aFac == 0) {
				A.add((double)aFac);
			}
			aFac++;
		}
		
		
		System.out.println(C);
		System.out.println(A);
		
		for(int i = 0; i< A.size(); i++)
		{
			for(int j = 0; j < C.size(); j++)
			{
				double num = C.get(j)/A.get(i);
				
				if(checkRoot(num,polyno) == true)
				{
					root.add(num);
				}
				if(checkRoot(-num,polyno) == true)
				{
					root.add(-num);
				}
			}
		}
		// convert from list to array
		double[] roots = root.stream().mapToDouble(i->i).toArray();
		//System.out.println(Arrays.toString(roots));
		return roots;
	}
}
