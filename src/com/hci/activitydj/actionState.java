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
		default:
			return -1;
		}
	}
}
