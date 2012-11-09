package Util;

public class Util {
	
	public static double euclidianDistance(double[] cordinate1, double[] cordinate2){
		double sqDistance = 0.0;

		for (int i=0; i<cordinate1.length; ++i)
		{
			sqDistance += Math.pow((cordinate1[i] - cordinate2[i]),2) ;
		}
 
		return Math.sqrt(sqDistance);

	}
}
