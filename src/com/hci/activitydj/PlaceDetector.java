package com.hci.activitydj;


import android.content.Context;
import android.location.Location;
import android.widget.Toast;



public class PlaceDetector {
Location locationA = new Location("point A");
	
	Location location1 = new Location("point A");
	Location location2 = new Location("point A");
	Location location3 = new Location("point A");
	Location location4 = new Location("point A");
	
	double a1 = 40.80967;
	double b1 = -73.960356;
	double c1 = 40.809333;
	double d1 = -73.959565;
	
	double a2 = 40.809489;
	double b2 = -73.960476;
	double c2 = 40.809162;
	double d2 = -73.959696;
	
	double width=0;
	double length=0;
		
//	mudd
//	Latitude, Longitude
//	40.80967,-73.960356
//
//	Latitude, Longitude
//	40.809333,-73.959565
//	
//	Latitude, Longitude
//	40.809489,-73.960476
//	
//	Latitude, Longitude
//	40.809162,-73.959696
	
//	Hamilton
//	a1b1
//	Latitude, Longitude
//	40.807053,-73.961978
//	c1d1
//	Latitude, Longitude
//	40.806769,-73.961302
//	a2b2
//	Latitude, Longitude
//	40.806923,-73.962096
//	c2d2
//	Latitude, Longitude
//	40.806631,-73.961431
	
	// GPSTracker class
	GPSTracker gps;
	
	public void placeDetector(Context context)
	{
		 location1.setLatitude(a1);
	    	location1.setLongitude(b1);
	    	
	    	location2.setLatitude(c1);
	    	location2.setLongitude(d1);
	    	
	    	location3.setLatitude(a2);
	    	location3.setLongitude(b2);
	    	
	    	location4.setLatitude(c2);
	    	location4.setLongitude(d2);
	    	
	    	
	    	width = location1.distanceTo(location2);
	    	length = location1.distanceTo(location3);
	        
	    	 gps = new GPSTracker(context);

				// check if GPS enabled		
		        if(gps.canGetLocation()){
		        	
		        	Location locationB = new Location("point B");
		        	
		        	double latitude = gps.getLatitude();
		        	double longitude = gps.getLongitude();
		        	
//		        	locationB.setLatitude(latitude);
//		        	locationB.setLongitude(longitude);
		        	
		        	DistanceToLine dis = new DistanceToLine();
		        	
		        	dis.distanceToLine(a1, b1, c1, d1, latitude, longitude);
		        	double dis1 = dis.getDistance();
		        	
		        	//Toast.makeText(getApplicationContext(), "Distance to border is: " + dis.getDistance(), Toast.LENGTH_LONG).show();
		        	
		        	dis.distanceToLine(a2, b2, c2, d2, latitude, longitude);
		        	double dis2 = dis.getDistance();
		        	
		        	//Toast.makeText(getApplicationContext(), "Distance to border is: " + dis.getDistance(), Toast.LENGTH_LONG).show();
		        	
		        	dis.distanceToLine(a1, b1, a2, b2, latitude, longitude);
		        	double dis3 = dis.getDistance();
		        	
		        	//Toast.makeText(getApplicationContext(), "Distance to border is: " + dis.getDistance(), Toast.LENGTH_LONG).show();
		        	
		        	dis.distanceToLine(c1, d1, c2, d2, latitude, longitude);
		        	double dis4 = dis.getDistance();
		        	
		        	//Toast.makeText(getApplicationContext(), "Distance to border is: " + dis.getDistance(), Toast.LENGTH_LONG).show();
		        	
		        	
		        	
		        	if(Math.abs(dis1-dis2)<=length+2 && Math.abs(dis3-dis4)<=width+2)
		        	{
		        		Toast.makeText(context, "Inside the building"  ,Toast.LENGTH_LONG).show();	
		        		
		        	}
		        	else
		        	{
		        		Toast.makeText(context, "Outside the building", Toast.LENGTH_LONG).show();	
		        	}
		        	
		        	// \n is for new line
		        	//Toast.makeText(getApplicationContext(), "Distance to border is: " + dis.getDistance(), Toast.LENGTH_LONG).show();	
		        
		        }else{
		        	// can't get location
		        	// GPS or Network is not enabled
		        	// Ask user to enable GPS/network in settings
		        	gps.showSettingsAlert();
		        }
		
	}

}
