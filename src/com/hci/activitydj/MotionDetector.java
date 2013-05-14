package com.hci.activitydj;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;


public class MotionDetector {

	private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    
    private Queue<Double> queue = new LinkedList<Double>();
	private double var = 0;
	private double avg = 0;
	private double old = 0;
	private int dataCounter = 0;
	private final int queueSize = 10;
	private int motion = 0; //0:initial 1:stop 2:walk 3:fast walk 4: run
	
	private DJManager dj;
	
	public void motionDetector(Context context, DJManager newDj)
	{
		this.dj = newDj;
		mSensorManager = (SensorManager) context.getSystemService(context.SENSOR_SERVICE);
		mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		//mSensorManager.registerListener(context, mAccelerometer , SensorManager.SENSOR_DELAY_NORMAL);
		mSensorManager.registerListener(new SensorEventListener() {
			@Override
			public void onSensorChanged(SensorEvent event) {
				// TODO Auto-generated method stub
				dataCounter++;
//				TextView tv1= (TextView)findViewById(R.id.textView1);
//				
//				TextView tv2= (TextView)findViewById(R.id.textView3);
//				
				double x = event.values[0];
				double y = event.values[1];
				double z = event.values[2];
				
				double tri = Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2);
				tri = Math.sqrt(tri);
				
				queue.add((double) tri);
				
				if(queue.size()<=queueSize)
				{
					avg = (avg*(queue.size()-1) + tri )/queue.size();
			
				}
				else
				{
					old = queue.element();
					queue.remove();

					avg = (avg*queue.size() - old + tri )/queue.size();
					
				}
				
				if(dataCounter % queueSize == 0)
				{
					Iterator<Double> it=queue.iterator();
					var = 0;
					
					while(it.hasNext())
			        {
						var = var +  Math.pow((it.next()-avg), 2);
			        }
					
					var = var / (queueSize-1);
					//tvX.setText(Double.toString(var));
					//System.out.println(dataCounter + ": " + var);
				}
				
//				tv1.setText(Double.toString(var));
				
				if(var < 5 ) // indicate almost no movement
				{
//					tv2.setText("stop"); // no music
					
//					dj.stop();

					motion =1;
				}
				else if(var < 20)
				{
//					tv2.setText("walk");
					
					if(motion!=2)
					{
//						dj.changeSong();
					}
					
					motion =2;
				}
				else if(var < 50)
				{
//					tv2.setText("fast walk");
					
					if(motion!=3)
					{
//						dj.changeSong();
					}
					motion =3;
				}
				else
				{
//					tv2.setText("run");
					
					if(motion!=4)
					{
//						dj.changeSong();
					}
					motion =4;
				}
				
			}

			@Override
			public void onAccuracyChanged(Sensor sensor, int accuracy) {
				// TODO Auto-generated method stub
				
				
			}
		}, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
	}

}
