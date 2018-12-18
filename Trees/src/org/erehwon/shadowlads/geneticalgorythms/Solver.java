package org.erehwon.shadowlads.geneticalgorythms;

//Class that implements the Genetic Algorithm
//at the heighest absraction level
// iterate until the specimin.affinity is < specified epsilon
public class Solver
{
// Current Population
Specimen[] Current_Population;

// Current Selection
Specimen[] Current_Selection;

// Current Proximity
double Current_Proximity;

// Current Iteration
int Current_Iteration;

SpecimenWorkshop sw;

// Initialize the algorithm
public void Initialize()
{
   // Set up options for the algorithm
	 sw = new SpecimenWorkshop();
   sw.PopulationSize = 1000; // 10000;
   sw.SelectionSize = 100; //1000;
   sw.MaxMutationPercent = 400;//500;
   sw.MutationLikelyhoodPercent = 82; //82; //90;
   sw.Epsilon = 0.000000001;

   // Generate initial population
   Current_Population = sw.GeneratePopulation();
   Current_Iteration = 0;
}

// Run the algorithm
public void Run()
{
   // Set Current_Proximity to the biggest value
   Current_Proximity = Double.MAX_VALUE;

   System.out.println(
     		"Current Iteration" + "\t" + 
            "Current Proximity" + "\t\t" + 
      		"Current Value");

   // Loop while Current_Proximity is not less than the Epsilon
   while (sw.Epsilon <= Current_Proximity)
   {
       // Select the best specimens
       Current_Selection = sw.Select(Current_Population);

       // Calculate proximity for the top-selected (the best) specimen
       Current_Proximity = Current_Selection[0].Affinity;

       // Report proximity and found solution for the current iteration
       System.out.println(
        		Current_Iteration + "\t\t\t"
        				+ "" + 
               Current_Proximity + "\t\t" + 
         		Current_Selection[0].Genes[0]);

       // End the calculations if Current_Proximity is less than the Epsilon
       if (Current_Proximity < sw.Epsilon)
       {
           break;
       }

       // Generate new population by reproducing specimens from the selection
       Current_Population = sw.GeneratePopulation(Current_Selection);

       Current_Iteration++;                
   }
}
}
