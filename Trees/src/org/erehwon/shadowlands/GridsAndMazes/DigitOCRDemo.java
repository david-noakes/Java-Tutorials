package org.erehwon.shadowlands.GridsAndMazes;

import java.util.BitSet;
import java.util.Vector;

public class DigitOCRDemo {

	static String[] six2 =
		   {"....................", 
	        "....................", 
	        "......##............", 
	        ".....#..............", 
	        ".....#..............", 
	        ".....#..............", 
	        ".....#..............", 
	        ".....#..............", 
	        ".....#..............", 
	        "....#...............", 
	        "....#...............", 
	        "....#..######.......", 
	        "....###.....#.......", 
	        "....##......#.......", 
	        "....#.......#.......", 
	        "....#.......#.......", 
	        "....##......#.......", 
	        ".....#######........", 
	        "....................", 
	        "...................."};
	static String[] one2 =
		    {"....................", 
			 "....................", 
			 "........##..........", 
			 "......#..#..........", 
			 ".........#..........", 
			 ".........#..........", 
			 ".........#..........", 
			 ".........#..........", 
			 ".........#..........", 
			 ".........#..........", 
			 ".........#..........", 
			 ".........#..........", 
			 ".........#..........", 
			 ".........#..........", 
			 ".........#..........", 
			 ".........#..........", 
			 ".........#..........", 
			 ".........#..........", 
			 ".......####.........", 
			 "...................."};
	static String[] eight2 = 
		    {"....................", 
			 "....................", 
			 "....................", 
			 "........####........", 
			 "......###...##......", 
			 ".....##......#......", 
			 "....#........#......", 
			 "....##......#.......", 
			 ".....#......#.......", 
			 ".......####.........", 
			 "........####........", 
			 ".......###..##......", 
			 "......#.......#.....", 
			 ".....#.........#....", 
			 "....#..........#....", 
			 "....##........##....", 
			 "......###...##......", 
			 ".........###........", 
			 "....................", 
			 "...................."};
	
	public static String[] image01 =    {"....................", 
			 "....................", 
			 "........#####.......", 
			 ".......###.###......", 
			 "......###...###.....", 
			 ".....##.......##....", 
			 "....###.......###...", 
			 "....##.........##...", 
			 "....##.........##...", 
			 "....##.........##...", 
			 "....##.........##...", 
			 "....##.........##...", 
			 "....##.........##...", 
			 "....###.......###...", 
			 ".....##.......##....", 
			 "......###...###.....", 
			 ".......###.###......", 
			 "........#####.......", 
			 "....................", 
			 "...................."};
public static String[] image04 = {"....................", 
"........###.........", 
"......##...##.......", 
".....#.......#......", 
"....#.........#.....", 
"....#.........#.....", 
"...#...........#....", 
"...#...........#....", 
"...#...........#....", 
"..#.............#...", 
"..#.............#...", 
"..#.............#...", 
"...#...........#....", 
"...#...........#....", 
"...#...........#....", 
"....#.........#.....", 
"....#.........#.....", 
".....#.......#......", 
"......##...##.......", 
"........###........."};
public static String[] image05 = {"....................", 
"....................", 
".......####.........", 
".....###..###.......", 
".....#......##......", 
"....##.......##.....", 
"....#.........##....", 
"...##..........##...", 
"...#............#...", 
"...#............#...", 
"..#..............#..", 
"..#..............#..", 
"..#..............#..", 
"..##.............#..", 
"...#............#...", 
"...##...........#...", 
".....#.........##...", 
"......##.....##.....", 
"........#####.......", 
"...................."};
public static String[] image11 =     {"....................", 
				 "....................", 
				 "........####........", 
				 "........####........", 
				 "........####........", 
				 "........####........", 
				 "........####........", 
				 "........####........", 
				 "........####........", 
				 "........####........", 
				 "........####........", 
				 "........####........", 
				 "........####........", 
				 "........####........", 
				 "........####........", 
				 "........####........", 
				 "........####........", 
				 "........####........", 
				 "........####........", 
				 "...................."};
public static String[] image14 = {"....................", 
"..........#.........", 
".........##.........", 
"..........#.........", 
"..........#.........", 
"..........#.........", 
"..........#.........", 
"..........#.........", 
"..........#.........", 
"..........#.........", 
"..........#.........", 
"..........#.........", 
"..........#.........", 
"..........#.........", 
"..........#.........", 
"..........#.........", 
"..........#.........", 
"..........#.........", 
"........#####.......", 
"...................."};
public static String[] image15 = {"....................", 
"....................", 
".........#..........", 
".........#..........", 
".........#..........", 
".........#..........", 
".........#..........", 
".........#..........", 
"........#...........", 
"........#...........", 
"........#...........", 
"........#...........", 
".......##...........", 
".......#............", 
".......#............", 
".......#............", 
".......#............", 
".......#............", 
"......##............", 
"...................."};
public static String[] image12 = {"....................", 
"....................", 
".......#............", 
".......#............", 
"........#...........", 
"........#...........", 
"........#...........", 
"........#...........", 
"........#...........", 
".........#..........", 
".........#..........", 
".........#..........", 
".........#..........", 
".........#..........", 
".........#..........", 
".........##.........", 
"..........#.........", 
"..........#.........", 
"..........#.........", 
"...................."};
public static String[] image16 = {"....................", 
"....................", 
".........#..........", 
".......###..........", 
"......##.#..........", 
".........#..........", 
".........#..........", 
".........#..........", 
".........#..........", 
".........#..........", 
".........#..........", 
".........#..........", 
".........#..........", 
".........#..........", 
".........#..........", 
".........#..........", 
".........#..........", 
"......#######.......", 
"....................", 
"...................."};
public static String[] image21 =      {"....................", 
			   "....................", 
              "........####........", 
              ".....####.####......", 
              "....##.......##.....", 
              "...##.........##....", 
              "..###.........###...", 
              "...............##...", 
              "...............##...", 
              "..............###...", 
              "..............##....", 
              "............###.....", 
              "...........###......", 
              ".........###........", 
              ".....#####..........", 
              "..##########.....##.", 
              "..####...#########..", 
              "..##.........###....", 
              "....................", 
              "...................."};
public static String[] image22 = 	{"....................", 
				 "....................", 
				 ".......####.........", 
				 ".....##....##.......", 
				 "....#........##.....", 
				 "..............##....", 
				 "...............#....", 
				 "...............#....", 
				 "..............#.....", 
				 ".............##.....", 
				 ".............#......", 
				 "............#.......", 
				 "............#.......", 
				 "...........#........", 
				 ".........##.........", 
				 ".........#..........", 
				 ".......##...........", 
				 ".....##.............", 
				 ".....############...", 
				 "...................."};
public static String[] image24 = {"....................", 
".......#####........", 
".....##.....##......", 
"....#.........#.....", 
"...#...........#....", 
"...#...........#....", 
"................#...", 
"................#...", 
"................#...", 
"................#...", 
"...............#....", 
"...............#....", 
"..............#.....", 
"............##......", 
".........###........", 
".......##...........", 
".....##.............", 
"....#...............", 
"...#................", 
"...##############..."};
public static String[] image31 =    {"....................", 
    		 "........####........", 
			 "......########......", 
			 ".....#.......##.....", 
			 "....##........##....", 
			 "..............##....", 
			 "..............##....", 
			 "..............##....", 
			 ".............##.....", 
			 ".........####.......", 
			 "........####........", 
			 "...........###......", 
			 "............###.....", 
			 ".............##.....", 
			 ".............##.....", 
			 "...##.........##....", 
			 "....##.......##.....", 
			 ".....##....##.......", 
			 "......#####.........", 
			 "...................."};
public static String[] image32 = {"....................", 
			 "....................", 
			 "..........####......", 
			 ".......####..#......", 
			 "....###.....#.......", 
			 "...........##.......", 
			 "..........##........", 
			 "..........#.........", 
			 ".........#..........", 
			 "........#######.....", 
			 "..............##....", 
			 "...............#....", 
			 "...............#....", 
			 "..............##....", 
			 ".............##.....", 
			 "...........###......", 
			 "......#####.........", 
			 "....##..............", 
			 "....................", 
			 "...................."};
public static String[] image35 = {"....................", 
"....................", 
".....######.........", 
".....#....###.......", 
"............#.......", 
"............##......", 
"............##......", 
"............#.......", 
"...........##.......", 
"..........#.........", 
".........#####......", 
".............#......", 
".............#......", 
".............#......", 
".............#......", 
".............#......", 
"...##.......#.......", 
"....#######.........", 
"....................", 
"...................."};
public static String[] image34 = {"....................", 
"....############....", 
"...#............#...", 
"................#...", 
"...............#....", 
"..............#.....", 
".............#......", 
"............#.......", 
"...........#........", 
"..........#.........", 
".........###........", 
"............##......", 
"..............#.....", 
"...............#....", 
"................#...", 
"................#...", 
"................#...", 
"....#..........#....", 
".....##......##.....", 
".......######......."};
public static String[] image36 = {"....................", 
"....................", 
".......##...........", 
"......##.###........", 
"...........##.......", 
"............#.......", 
"............#.......", 
"............#.......", 
"..........##........", 
".........###........", 
"............##......", 
".............#......", 
".............##.....", 
"..............#.....", 
".............##.....", 
".............#......", 
"....#.......#.......", 
"....########........", 
"....................", 
"...................."};
public static String[] image41 = {"....................", 
         "....................", 
         "....................", 
         "...........##.......", 
         "..........##........", 
         ".........##.........", 
         "........##..........", 
         ".......##...........", 
         ".......##...........", 
         "......##............", 
         ".....##.....##......", 
         "....##.....###......", 
         "...##......###......", 
         "...##############...", 
         "...........###......", 
         "...........###......", 
         "...........###......", 
         "...........###......", 
         "....................", 
         "...................."};
public static String[] image42 =  {"....................", 
			 ".........#..........", 
			 ".........#..........", 
			 "........#...........", 
			 ".......##...........", 
			 ".......#............", 
			 "......##............", 
			 "......#.............", 
			 ".....##.............", 
			 ".....#..............", 
			 "....##......#.......", 
			 "...##.......#.......", 
			 "...#........#.......", 
			 "...#........#.......", 
			 "..###############...", 
			 "............#.......", 
			 "............#.......", 
			 "............#.......", 
			 "............#.......", 
			 "...................."};
public static String[] image43 = {"....................", 
"....................", 
"........#...........", 
".......#............", 
"......##............", 
"......#.............", 
".....#..............", 
"....##..............", 
"....#........#......", 
"...##........#......", 
"...#.........#......", 
"...#.........#......", 
"..##.........#......", 
".##..........#......", 
"..#########.##..#...", 
"..........#######...", 
"............#.......", 
"............#.......", 
"............##......", 
".............#......"};
public static String[] image44 =
{"....................", 
".........#..........", 
".........#..........", 
"........#...........", 
".......#............", 
".......#............", 
"......#.............", 
".....#..............", 
".....#......#.......", 
"....#.......#.......", 
"...#........#.......", 
"...#........#.......", 
"..##############....", 
"............#.......", 
"............#.......", 
"............#.......", 
"............#.......", 
"............#.......", 
"............#.......", 
"...................."};
public static String[] image45 = 
{"....................", 
"....................", 
".........#..........", 
"........##..........", 
".......#............", 
"......##............", 
"......#.............", 
".....##.............", 
"....##..............", 
"....#...............", 
"...##.....#.........", 
"..##......#.........", 
"..#.......#.........", 
".##############.....", 
"..........#.........", 
".........##.........", 
".........#..........", 
".........#..........", 
"....................", 
"...................."};
public static String[] image51 = {"....................", 
         "....................", 
         "....................", 
         ".....###########....", 
         ".....###............", 
         ".....###............", 
         "....###.............", 
         "....###.............", 
         "....##..............", 
         "...#########........", 
         "............##......", 
         "..............##....", 
         "..............##....", 
         "..............##....", 
         "..............##....", 
         "...##.......###.....", 
         "....#####.###.......", 
         ".......####.........", 
         "....................", 
         "...................."};
public static String[] image52 = {"....................", 
			 "....................", 
			 "..........#########.", 
			 ".....#####..........", 
			 "....##..............", 
			 "...##...............", 
			 "...#................", 
			 "..##................", 
			 "..#.................", 
			 ".########...........", 
			 ".#.......####.......", 
			 ".............##.....", 
			 "..............##....", 
			 "...............#....", 
			 "...............#....", 
			 "...............#....", 
			 "...#..........##....", 
			 "...###.......##.....", 
			 ".....#########......", 
			 "...................."};
public static String[] image53 =    {"....................", 
			 "....................", 
			 ".......############.", 
			 ".......#............", 
			 "......##............", 
			 "......#.............", 
			 "......#.............", 
			 "......#.............", 
			 "......######........", 
			 "............##......", 
			 ".............##.....", 
			 "..............#.....", 
			 "..............##....", 
			 "...............#....", 
			 "..............#.....", 
			 "...##.........#.....", 
			 ".....###..####......", 
			 ".......####.........", 
			 "....................", 
			 "...................."};
public static String[] image54 =
{"................#...", 
".....###########....", 
"....#...............", 
"....#...............", 
"...#................", 
"...#................", 
"..#.................", 
"..#.................", 
"..######............", 
"........#####.......", 
".............##.....", 
"...............#....", 
"................#...", 
"................#...", 
"................#...", 
"................#...", 
"...............#....", 
"...............#....", 
"....#.........#.....", 
".....#########......"};
public static String[] image55 = 
{"....................", 
"....................", 
"...#############....", 
"...#................", 
"...#................", 
"..##................", 
"..#.................", 
"..#.................", 
".##.................", 
".###############....", 
"...............##...", 
"................##..", 
".................#..", 
".................#..", 
"................#...", 
".....#.........##...", 
".....###......##....", 
".......########.....", 
"....................", 
"...................."};
public static String[] image56 = {"....................", 
"....................", 
"..............##....", 
".....#########......", 
".....#..............", 
".....#..............", 
"....#...............", 
"....#...............", 
"....#...............", 
"....########........", 
"............##......", 
"..............#.....", 
"..............##....", 
"...............#....", 
"..............#.....", 
"....#.......##......", 
"....#####.##........", 
"....................", 
"....................", 
"...................."};
public static String[] image61 = {"....................", 
			 "....................", 
			 ".........###........", 
			 "........##..........", 
			 ".......#............", 
			 "......##............", 
			 ".....##.............", 
			 ".....##.............", 
			 ".....##.............", 
			 ".....##...###.......", 
			 ".....##.###.####....", 
			 "......###......##...", 
			 "......##.......##...", 
			 "......##.......##...", 
			 ".......##......##...", 
			 ".......##......##...", 
			 "........###..###....", 
			 "..........####......", 
			 "....................", 
			 "...................."};
public static String[] image64 = {"....................", 
"............##......", 
"..........##........", 
".........#..........", 
"........#...........", 
".......#............", 
"......#.............", 
".....#..............", 
".....#..............", 
"....#.....##........", 
"....#...##..##......", 
"...#...#......#.....", 
"...#..#.......#.....", 
"...#.#.........#....", 
"...#.#.........#....", 
"....#..........#....", 
"....#..........#....", 
".....#........#.....", 
"......##....##......", 
"........####........"};
public static String[] image71 = {"....................", 
       "....................", 
       "....................", 
       "...###############..", 
       "..##............##..", 
       "...............##...", 
       "...............#....", 
       "..............#.....", 
       ".............##.....", 
       ".............#......", 
       "............##......", 
       "............#.......", 
       "........#########...", 
       "........#..#........", 
       "..........##........", 
       "..........#.........", 
       ".........#..........", 
       "........##..........", 
       "........#...........", 
       ".......##..........."};
public static String[] image72 = {"....................", 
		 "....................", 
		 "....................", 
		 "....############....", 
		 "..............##....", 
		 ".............##.....", 
		 "............##......", 
		 "............##......", 
		 "...........##.......", 
		 "...........##.......", 
		 "...........##.......", 
		 "..........##........", 
		 "..........##........", 
		 "..........##........", 
		 ".........##.........", 
		 ".........##.........", 
		 "........##..........", 
		 "....................", 
		 "....................", 
		 "...................."};
public static String[] image73 = {"....................", 
"....................", 
"....############....", 
"....############....", 
"....############....", 
".............##.....", 
".............##.....", 
"............##......", 
"............##......", 
"...........##.......", 
"...........##.......", 
"...........##.......", 
"..........##........", 
"..........##........", 
"..........##........", 
".........##.........", 
".........##.........", 
"........##..........", 
"........##..........", 
"...................."};

public static String[] image74 = {"....................", 
"...##############...", 
"..#..............#..", 
".................#..", 
"................#...", 
"...............#....", 
"..............#.....", 
".............#......", 
"............#.......", 
"............#.......", 
".......########.....", 
"...........#........", 
"..........#.........", 
"..........#.........", 
".........#..........", 
".........#..........", 
"........#...........", 
"........#...........", 
"........#...........", 
"........#..........."};	


public static String[] image81 = {"....................", 
"....................", 
"........###.........", 
"......###.####......", 
".....##......#......", 
".....#.......#......", 
"....##.......#......", 
"....#........#......", 
"....##......#.......", 
".....#......#.......", 
"......######........", 
"..........##........", 
".......###..##......", 
"......#.......#.....", 
".....#.........#....", 
".....#.........#....", 
"....#..........#....", 
"....##........##....", 
"......###....#......", 
"..........###......."};
public static String[] image83 =
{"....................", 
"....................", 
"........###.........", 
"......###.####......", 
".....##......###....", 
"....##.......###....", 
"....##.......###....", 
"....##.......###....", 
".....##.....###.....", 
"......######........", 
".......#####........", 
".......###.###......", 
".....##......##.....", 
"...##..........##...", 
"...##..........##...", 
"....##........##....", 
"......###...###.....", 
".......#######......", 
"....................", 
"...................."};
public static String[] image84 = {"....................", 
"........###.........", 
"......##...##.......", 
".....#.......#......", 
"....#.........#.....", 
"....#.........#.....", 
"....#.........#.....", 
".....#.......#......", 
"......#.....#.......", 
".......#####........", 
".....##.....##......", 
"....#.........#.....", 
"...#...........#....", 
"...#...........#....", 
"..#.............#...", 
"..#.............#...", 
"..#.............#...", 
"...#...........#....", 
"....##.......##.....", 
"......#######......."};
public static String[] image91 =  {"....................", 
          "....................", 
          ".......####.........", 
          ".....##...###.......", 
          "....#.......#.......", 
          "....#.......##......", 
          "....#........#......", 
          "....#........#......", 
          "....#......##.......", 
          ".....########.......", 
          "............#.......", 
          "...........##.......", 
          "...........#........", 
          "..........##........", 
          "..........#.........", 
          ".........##.........", 
          ".........#..........", 
          "........##..........", 
          ".......##...........", 
          "...................."};
public static String[] image92 =    {"....................", 
			 "....................", 
			 ".......#######......", 
			 "......#......##.....", 
			 ".....##.......##....", 
			 ".....##.......##....", 
			 ".....##......###....", 
			 ".....##.....###.....", 
			 "......######.##.....", 
			 "........###..##.....", 
			 ".............##.....", 
			 "............###.....", 
			 "............##......", 
			 "............##......", 
			 "...........##.......", 
			 "...........##.......", 
			 "..........##........", 
			 ".........##.........", 
			 "....................", 
			 "...................."};
public static String[] image93 = 
{"....................", 
".............###....", 
".....#######.##.....", 
"....##......###.....", 
"....##.......##.....", 
"....##.......##.....", 
"....##......###.....", 
"....##.....####.....", 
".....######..##.....", 
".............##.....", 
".............##.....", 
"............###.....", 
"............###.....", 
"............###.....", 
"............###.....", 
"............###.....", 
"............###.....", 
"...........####.....", 
"....................", 
"...................."};
public static String[] image94 = {"....................", 
".......#####........", 
".....##.....##......", 
"....#.........#.....", 
"....#.........#.....", 
"...#...........#....", 
"...#...........#....", 
"...#...........#....", 
"....#.........#.....", 
"....#.........#.....", 
".....##.....##......", 
".......#####.#......", 
".............#......", 
".............#......", 
"............#.......", 
"............#.......", 
"............#.......", 
"...........#........", 
"...........#........", 
"...........#........"};

public static void main(String[] args) {
// TODO Auto-generated method stub
//DigitOCR dd = new DigitOCR();
//BitSet bs = dd.toBitSet(image31);
//byte[] ba = bs.toByteArray();
//System.out.println(dim2Str(ba));
String[][] images = {image01, image04, image05, image11, image12, image14 ,image15, image16, image21,image22, image24, image31, image32, image34, image35, image36, image41, image42, image43, image44, image45, image51, image52, image53, image54, image55, image56, image61, image64, image71, image72, image73, image74, image81, image83, image84, image91, image92, image93, image94};
	int[] vals = {0, 0, 0, 1, 1, 1, 1, 1, 2, 2, 2, 3, 3, 3, 3, 3, 4, 4, 4, 4, 4, 5, 5, 5, 5, 5, 5, 6, 6, 7, 7, 7, 7, 8, 8, 8, 9, 9, 9, 9};
	for (int i = 0;i<vals.length;i++) {
		runTest(images[i], vals[i], i+1);
	}
}
 	static void runTest(String[] img, int exp, int tst) {
 		DigitOCR dd = new DigitOCR();
// 		int res = dd.whiteBoardOCR(img);  // 12 fails
//		int res = dd.wbOCR(img);  // 12 fails
		int res = dd.wbOCR1(img); // 12 fails
//		int res = dd.wbOCR3(img); // 12 fails // add in bs36 and 35 and 38 fail
		if (exp == res) {
			System.out.println("Test" + tst + ": Equal:"+res);
		} else {
		 	System.out.println("Test" + tst + ": Expected: " + exp + ",  Results : " + res);
		}
	}
	
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		DigitOCR dd = new DigitOCR();
//		BitSet bs = dd.toBitSet(eight2);
//		byte[] ba = bs.toByteArray();
//		for (int i = 0; i < ba.length;i++) {
//			System.out.print(ba[i] + ", "); 
//		}
//	}
	 public static String dim2Str(byte[] ba) {
		 	StringBuilder s= new StringBuilder("[");
		 	for (int i=0;i<ba.length;i++) {
		 		if (i>0) {
		 			s.append(' ');
		 		}
		 		//s.append('"');
		 		s.append(ba[i]);
		 		//s.append('"');
		 		if (i<(ba.length-1)) {
		 			s.append(", ");
		 		}
		 	}
		 	s.append("]");
		 	return s.toString();
		 }


}
