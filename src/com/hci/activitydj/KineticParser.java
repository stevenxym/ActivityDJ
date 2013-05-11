package com.hci.activitydj;

import android.util.Log;

public class KineticParser {

	enum energyStatus {
		LOW,
		LOW_CHECKING,
		HIGH,
		HIGH_CHECKING;
		
		private final static double energyThreshold = 100.0;
		
		static public energyStatus parseEnergy(double energy) {
			return energy < energyThreshold ? energyStatus.LOW_CHECKING : energyStatus.HIGH_CHECKING;
		}
		
		public energyStatus finishChecking() {
			switch(this) {
			case LOW_CHECKING:
				return LOW;
			case HIGH_CHECKING:
				return HIGH;
			default:
				return null;
			}
		}
		
		public boolean isHigh() {
			return this==HIGH || this==HIGH_CHECKING;
		}
	}
	
	class energyRecorder {
		
		private final static int timerInterval = 1000;		// 1 sec
		private final static int waitInterval = 3000;
		private long checkPoint;
		private long waitCheckPoint;
		
		private energyStatus eStatus;
		
		public energyRecorder() {
			checkPoint = -1;
			waitCheckPoint = -1;
		}
		
		public actionState setEnergyStatus(double energy) {
			
			if (this.waitCheckPoint != -1) {
				if (System.currentTimeMillis() <= (this.waitInterval + this.waitCheckPoint))
					return actionState.NONE;
				else
					this.waitCheckPoint = -1;
			}
			
			energyStatus current = energyStatus.parseEnergy(energy);
			if (this.checkPoint == -1) {
				this.eStatus = current.finishChecking();
				this.checkPoint = System.currentTimeMillis();
				//this.waitCheckPoint = System.currentTimeMillis();
				return this.eStatus.isHigh() ? actionState.KINETIC_ACT : actionState.KINETIC_REST;
			}
			
			if (this.eStatus.isHigh() == current.isHigh()) {
				if (this.eStatus == energyStatus.HIGH_CHECKING || this.eStatus == energyStatus.LOW_CHECKING) {
					if (System.currentTimeMillis() >= (this.checkPoint + this.timerInterval)) {
						this.eStatus = this.eStatus.finishChecking();
						this.waitCheckPoint = System.currentTimeMillis();
						Log.d("debug", "100 more than 1 sec\n");
						return this.eStatus.isHigh() ? actionState.KINETIC_ACT : actionState.KINETIC_REST;
					}
				}
			} else {
				this.checkPoint = System.currentTimeMillis();		// start new timer
				this.eStatus = current;
			}
			
			return actionState.NONE;
		}
	}
	
	enum positionStatus {
		IN_RANGE,
		IN_RANGE_CHECKING,
		OUT_RANGE,
		OUT_RANGE_CHECKING;
		
		private final static double lowerBound = -1.0;
		private final static double upperBound = 1.0;
		
		static public positionStatus parsePosition(double pos) {
			return (pos < upperBound && pos > lowerBound) ? positionStatus.IN_RANGE_CHECKING : positionStatus.OUT_RANGE_CHECKING;
		}
		
		public positionStatus finishChecking() {
			switch(this) {
			case IN_RANGE_CHECKING:
				return IN_RANGE;
			case OUT_RANGE_CHECKING:
				return OUT_RANGE;
			default:
				return null;
			}
		}
		
		public boolean isOutRange() {
			return this==OUT_RANGE || this==OUT_RANGE_CHECKING;
		}
	}
	
	class positionRecorder {
		
		private final static int timerInterval = 1000;		// 1 sec
		private final static int waitInterval = 3000;
		private long checkPoint;
		private long waitCheckPoint;
		
		private positionStatus pStatus;
		
		public positionRecorder() {
			checkPoint = -1;
		}
		
		public boolean setPositionStatus(double pos) {
			
			if (this.waitCheckPoint != -1) {
				if (System.currentTimeMillis() <= (this.waitInterval + this.waitCheckPoint))
					return false;
				else
					this.waitCheckPoint = -1;
			}
			
			positionStatus current = positionStatus.parsePosition(pos);
			if (this.checkPoint == -1) {
				this.pStatus = current.finishChecking();
				this.checkPoint = System.currentTimeMillis();
				return true;
			}
			
			if (this.pStatus.isOutRange() == current.isOutRange()) {
				if (this.pStatus == positionStatus.OUT_RANGE_CHECKING || this.pStatus == positionStatus.IN_RANGE_CHECKING) {
					if (System.currentTimeMillis() >= (this.checkPoint + this.timerInterval)) {
						this.pStatus = this.pStatus.finishChecking();
						this.waitCheckPoint = System.currentTimeMillis();
						return true;
					}
				}
			} else {
				this.checkPoint = System.currentTimeMillis();		// start new timer
				this.pStatus = current;
			}
			
			return false;
		}
	}
	
	private energyRecorder eRecorder;
	private positionRecorder pRecorder;
	private DJManager dj;
	private boolean start;
	
	public KineticParser(DJManager dj) {
		eRecorder = new energyRecorder();
		pRecorder = new positionRecorder();
		this.dj = dj;
		this.start = true;
	}
	
	public void parseKineticCommand(String cmd) {
		if (cmd == null || cmd.isEmpty())
			return;
		
		String cmdArray[] = cmd.split(" ");
		
		if (cmdArray[0].equals("R")) {
			
			if (cmdArray.length != 4)
				return;
			
//			actionState kineticState = this.eRecorder.setEnergyStatus(Double.valueOf(cmdArray[3]));
//				//Log.d("debug", "change song now\n");
//			if (kineticState != actionState.NONE)
//				dj.changeSong(kineticState);
			
			if (this.pRecorder.setPositionStatus(Double.valueOf(cmdArray[2]))) {
				if (start)
					dj.changeSong(actionState.KINETIC_REST);
				else
					dj.stop();
				start = !start;
			}
			
		} else if (cmdArray[0].equals("G")) {
			
			if (cmdArray.length != 2)
				return;
			
			//if (cmdArray[1].equals("1"))
				//dj.playQuick();
		}
	}
}
