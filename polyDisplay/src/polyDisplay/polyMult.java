package polyDisplay;

import java.util.Arrays;
import java.util.Collections;

public class polyMult {
	private int[] mult(int[] poly1, int[] poly2, int n)
	{
		int mid = n/2;
		int[] a0 = Arrays.copyOfRange(poly1, 0, mid); //left half lower power of poly1
		int[] a1 = Arrays.copyOfRange(poly1, mid,n);//right half higher power of poly1
		int[] b0 = Arrays.copyOfRange(poly2, 0, mid);//left half lower power of poly2
		int[] b1 =Arrays.copyOfRange(poly2, mid, n);//right half higher power of poly2
		if(n <= 2)
		{
			return brute(a1,a0,b1,b0,n);
		}
		
		//recursion
		mult(a0,b0,mid);
		mult(a1,b1,mid);
		
		return brute(a1,a0,b1,b0,n);


	}
	//involved in the divide and conquer mult function to help mutiplication
	private int[] brute(int[] a1,int[] a0,int[] b1,int[] b0,int n)
	{
		
		int[] R = new int[2*n];
		int[] sumA = new int[n/2];
		int[] sumB = new int[n/2];
		
		for(int i = 0; i < n/2; i++)
		{
			sumA[i] = a1[i]+a0[i];
			sumB[i] = b1[i]+b0[i];
		}
		int[] a1b1 = new int[2*a1.length];
		int[] sumAsumB = new int[2*a1.length];
		int[] a0b0 = new int[2*a1.length];
		/*
		System.out.println(Arrays.toString(sumA));
		System.out.println(Arrays.toString(sumB));
		*/
		for(int i = 0; i < n/2;i++)
		{
			for(int j = 0; j < n/2; j++)
			{
				a1b1[i+j]+=a1[i]*b1[j];
				sumAsumB[i+j] += sumA[i]*sumB[j]-a1[i]*b1[j]-a0[i]*b0[j];
				a0b0[i+j] += a0[i]*b0[j];
			}
		}
		for(int i = 0; i < a1b1.length;i++)
		{
			R[i] += a0b0[i];
			R[i+n/2] += sumAsumB[i];
			R[i+n] += a1b1[i];
		}
		return R;
		
	}
	
	private int[] readInp(String input1, String input2) 
	{
		
		//System.out.println(input1);
		//System.out.println(input2);
		String[] strArr1 = input1.split(" ");
		String[] strArr2 = input2.split(" ");
		Collections.reverse(Arrays.asList(strArr1));
		Collections.reverse(Arrays.asList(strArr2));
		//System.out.println(Arrays.toString(strArr1));
		//System.out.println(Arrays.toString(strArr2));
		int len = 0;
		//the length of input array has to be the power of 2, otherwise recursion won't work
		if (input1.split(" ").length < input2.split(" ").length)
		{
			len = (int) Math.pow(2,Math.ceil(Math.log(strArr2.length)/Math.log(2)));
		}
		else 
		{
			len = (int) Math.pow(2,Math.ceil(Math.log(strArr1.length)/Math.log(2)));
		}
		//create array with max length
		int[] poly1 = new int[len];
		int[] poly2 = new int[len];

		//fill in integer array poly1
		for(int i = 0; i < strArr1.length; i++)
		{
			poly1[i] = Integer.parseInt(strArr1[i]);
		}
		//fill in integer array poly2
		for(int i = 0; i < strArr2.length; i++)
		{
			poly2[i] = Integer.parseInt(strArr2[i]);
		}
		//System.out.print(Arrays.toString(poly1) +" "+ Arrays.toString(poly2));

		//System.out.println(Arrays.toString(brute(a1,a0,b1,b0,1)));
		int[] result = mult(poly1,poly2,len);
		//System.out.println(Arrays.toString(mult(poly1,poly2,len)));
		result = Arrays.copyOfRange(result, 0, strArr1.length + strArr2.length-1);
		
		//Reverse the result array
		for(int i = 0; i < result.length / 2; i++)
		{
		    int temp = result[i];
		    result[i] = result[result.length - i - 1];
		    result[result.length - i - 1] = temp;
		}
		
		return result;
	}
	
	public int[] read(String p1,String p2) {
		String input1 = p1;
		String input2 = p2;
		//int[] sample = {1,4,4};
		return readInp(input1,input2);
	}
}
