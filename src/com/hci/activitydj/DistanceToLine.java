package com.hci.activitydj;

public class DistanceToLine {

	public static double distance=0;
	
	public static void distanceToLine(double a, double b, double c, double d, double x, double y) 
	{	
//		double a=0;
//		double b=0;
//		double c=2;
//		double d=0;
//		
//		double x=0;
//		double y=2;
		
		distance = Math.abs((b-d)*x+(c-a)*y + a*d-b*c) / Math.sqrt(Math.pow(b-d, 2) + Math.pow(c-a, 2));
		
		
		//System.out.println(distance);
		//Math.pow(b-d, 2)
		
	}
	
	public double getDistance()
	{
		return distance*111000;
	}

}
