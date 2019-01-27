package polyDisplay;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.Arrays;

class YuanPolyDisplay extends JPanel{
	public static int screenWidth = 300, screenHeight = 300, columns = 10;
	public double[][] cordinates(double[] poly) 
	{
		
		double[][] cord = new double[(columns*2+1 )*10][2];
		int n = 0;
		for(double i = 0; i < columns*2 +1 ; i+= 0.1) 
		{
			double x = i-10;
			cord[n][0] = x;
			double y = 0;
			for(int j =0; j < poly.length;j++) 
			{
				y += poly[j] * Math.pow(x, j);
			}
			cord[n][1] = y;
			//System.out.println(x +" "+ y);
			n++;
		}
		/*
		for(int i = 0; i < cord.length; i++) 
		{
			System.out.println(Arrays.toString(cord[i]));
		}
		*/
		
		return cord;
	}

	public void paintComponent(Graphics g/*, double[] poly*/) 
	{
		double[] poly = {3,2,5};
		int[] origen = {screenWidth/2, screenHeight / 2};
		int scale = screenWidth/2/columns;
		
		super.paintComponent(g);
		this.setBackground(Color.WHITE);
		
		g.drawLine(origen[0], 0, origen[0], screenHeight);
		g.drawLine(0, origen[1], screenWidth,  origen[1]);
		
		int r = (int)(columns * 0.1);
		Font myF = new Font("Serif", Font.PLAIN, (int)(columns * 0.9));
		g.setFont(myF);
		//print horizontal cordinates
		for(int i = 0; i < columns*2 +1 ; i++) 
		{
			g.drawOval(i*scale-r, origen[1]-r, 2*r, 2*r);
			String scl = Integer.toString(i-10);
			g.drawString(scl, i*scale, origen[1]+g.getFontMetrics().getAscent());
		}
		//print vertical cordinates
		int num = columns;
		for(int i = 0; i < columns*2 +1 ; i++) 
		{
			String scl = Integer.toString(num);
			// prevent 2 zero being drawn
			if(num != 0) 
			{
				g.drawOval(origen[0]-r, i*scale-r, 2*r, 2*r);
				g.drawString(scl, origen[0]+g.getFontMetrics().stringWidth(scl)-3, i*scale);
			}
			
			num--;
		}
		
		double[][] cord = cordinates(poly);
		//prepare x,y cordinates for polyline
		int[] x = new int[cord.length];
		int[] y = new int[x.length];
		int n = 0;
		int rightLen = 0;
		for(int i = 0; i < cord.length; i ++) 
		{
			if (cord[i][1] <= columns+1 && cord[i][1] > -columns-1) 
			{
				x[n] = (int)Math.round(cord[i][0]*scale + origen[0]);
				y[n] = (int)Math.round(origen[1] -cord[i][1]*scale);
				n++;
				rightLen ++;
			}
		}
		g.drawPolyline(x, y, rightLen);
		

}
	
}
