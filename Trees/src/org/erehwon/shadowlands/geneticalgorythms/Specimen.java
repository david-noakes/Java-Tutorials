package org.erehwon.shadowlands.geneticalgorythms;

public class Specimen implements Comparable<Specimen> {
    // The genes
    public double[] Genes;

    // Affinity to the solution
    public double Affinity;

    // Constructor that creates the Specimen
    // instance with initial genes
    public Specimen()
    {
        // Assume one double value as the genes
        Genes = new double[1];

        // Set up initial value to the genes
        Genes[0] = 10;
    }

   // Calculates affinity of this instance to the solution
    // Contains the equation formula
    public void CalculateAffinity()
    {
        double s1 = Math.pow(Genes[0], 5) * 19.39281272;
        double s2 = Math.pow(Genes[0], 4) *  7.82018991;
        double s3 = Math.pow(Genes[0], 3) * 35.12849546;
        double s4 = Math.pow(Genes[0], 2) * 28.09127103;
        double s5 = Genes[0] * 3.30129489;

        double f = s1 + s2 + s3 - s4 + s5;

        // Need positive value to evaluate proximity
        // 0 is the best value
        Affinity = Math.abs(f - 20351.07006276);
    }

	@Override
	public int compareTo(Specimen arg0) {
		//  -1 = this is LT
		//  0  = this is EQ
		//  1  = this is GT
		if (this.Affinity>arg0.Affinity) 
			return 1;
		if (this.Affinity==arg0.Affinity)
		    return 0;
		return -1;
	}

}
