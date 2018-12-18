package org.erehwon.shadowlads.geneticalgorythms;


import java.io.IOException;

public class Program1 {

    public static void main(String[] args) throws IOException {
    	Solver so = new Solver();
        so.Initialize();
        so.Run();

		System.out.println("*** Done ***");
//		System.in.read();
    }
}
