package com.hci.activitydj;

public enum actionState {
	KINETIC_REST,
	KINETIC_ACT,
	KINETIC_GESTURE,
	REST,
	MOVE,
	NONE;
	
	public int getStateIndex() {
		switch(this) {
		case KINETIC_REST:
			return 0;
		case KINETIC_ACT:
			return 1;
		case REST:
			return 2;
		case MOVE:
			return 3;
		case KINETIC_GESTURE:
			return 4;
		default:
			return -1;
		}
	}
	
	public boolean isKineticState() {
		return this == KINETIC_REST || this == KINETIC_ACT;
	}
}
