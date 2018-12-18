package org.erehwon.shadowlands.GridsAndMazes;

public class StudyOrSleep {

	
	/*
	 * forumula (100-famil)*0.2*t -  ? (hRem-t) < 8: 5*(8 - (hRem-t) ): 0
	 */
int studyOrSleep(int f, int h) {
	int t = 0;
	float s = -1;
	float z;
	int tMax = 0;
	while (t <= h) {
		z = c(f, h, t);
		if (z > s) {
			s = z;
			tMax = t;
			t++;
		} else {
			t = h+1;
		}
	}
	
	return tMax;
}

float c(int f, int h, int t) {
	float u = (100-f);
	float r = 0;
	double b = t; double r1;
	r = (float) (u * Math.pow(0.8, b));
//	r = (float) (f * Math.pow(1.2, b));
//	for (int i = 1; i <= t; i++) {
//		r += (u*0.2);
//		u -= (u*0.2);
//	}
	r = (u - r);
//	r = (100 - r)/5;
	float x = 0;
	if (t > 0 && (h-t)<8) {
		x -= (h >= 8) ?5 * (8-(h-t)) : 5 * t;
//		if (hRem >= 8) {
//			x -= 5 * (8-(hRem-t)) ;
//		} else {
//			x -= 5 * t;
//		}
	}
	return (float) (r+x);
}
}
