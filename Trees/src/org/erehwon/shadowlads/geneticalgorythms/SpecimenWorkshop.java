package org.erehwon.shadowlads.geneticalgorythms;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

// Class that performs main actions with the specimens and the population
public class SpecimenWorkshop
{
    static // Random generator
    Random rnd = new Random();

    // Number of specimens in the population
    public int PopulationSize;

    // Number of specimens in the selection 
    public int SelectionSize;

    // Power of mutations
    // 0 - no mutations
    // 100 - mutations up to whole gene value
    // 200 - mutations up to twice gene value
    public double MaxMutationPercent;

    // Likelyhood of mutation
    // 0 - no mutations, 100 - mutations for all cases
    public int MutationLikelyhoodPercent;

    // Maximal affinity that is considered 
    // for the solution found
    public double Epsilon;

    // Generate initial population
    public Specimen[] GeneratePopulation()
    {
        // Creates array representing the population
        Specimen[] p = new Specimen[PopulationSize];

        // Creates specimens
        // Mutation of all specimens in initial population
        // increases variance that increases chance to
        // get better instance.
        for (int i = 0; i < PopulationSize; i++)
        {
            p[i] = new Specimen();
            Mutate(p[i]);

            // Calculate Affinity for new specimens
            p[i].CalculateAffinity();
        }

        return p;
    }

    // Generate population by reproduction of selection
    public Specimen[] GeneratePopulation(Specimen[] selection)
    {
        // Creates array representing the population
        Specimen[] p = new Specimen[PopulationSize];

        // Copy instances from the selection to keep them
        // in new generation of population
        for (int i = 0; i < SelectionSize; i++)
        {
            p[i] = selection[i];
        }

        // Creates new specimens by reproducing two parents
        // Parents are selected randomly from the selection.
        int child_index = SelectionSize;
        int parent1_index;
        int parent2_index;

        while(child_index < PopulationSize)
        {
            // Slect two parents randomly in way
            // they are different instances
            do
            {
                parent1_index = rnd.nextInt(selection.length);
                parent2_index = rnd.nextInt(selection.length);
            } while (parent1_index == parent2_index);

            // Creates new specimen
            p[child_index] = ReproduceNew(selection[parent1_index], selection[parent2_index]);

            child_index++;
        }

        return p;
    }

    // Reproduce new specimen on base of two parents
    public Specimen ReproduceNew(Specimen a, Specimen b)
    {
        Specimen s= new Specimen();

        // Iherit genes as the average oh the parents' genes
        s.Genes[0] = (a.Genes[0] + b.Genes[0]) / 2;

        // Mutate if likelyhoo allows
        int ml = rnd.nextInt(101);
        if (ml <= MutationLikelyhoodPercent)
        {
            Mutate(s);
        }

        // Calculate Affinity for new specimen
        s.CalculateAffinity();

        return s;
    }

    // Select the best specimens from the population
    public Specimen[] Select(Specimen[] population)
    {
        // Sort population by increasing the affinity
        // The best specimens are moving to start of the array
        Sort(population);

        // Create set of selected specimens
        Specimen[] selected = new Specimen[SelectionSize];

        // Copy best specimens into the selection
        for (int i = 0; i < SelectionSize; i++)
        {
            selected[i] = population[i];
        }

        return selected;
    }

    // Sort the population
    public void Sort(Specimen[] population)
    {
        
//    *** original bubble sort ***
//        for (int i = 0; i < PopulationSize; i++)
//        {
//        	for (int j = 0; j < PopulationSize; j++)
//        	{
//        		if (population[i].Affinity < population[j].Affinity)
//        		{
//        			temp = population[i];
//        			population[i] = population[j];
//        			population[j] = temp;
//        		}
//        	}
//        } 
        
//    *** sort ascending         
        Arrays.sort(population);
    }

    // Mutate the specimen
    public void Mutate(Specimen sp)
    {
        // Calculate Mutation Factor that is random value between 0 and MaxMutationPercent
        // calculates as ratio between 0 and 1
        double MutationFactor = (MaxMutationPercent / 100.0) * (rnd.nextInt(1001) / 1000.0);

        // Set mutation to negative with 50% likelyhood
        if (rnd.nextInt(10) < 5)
        {
            MutationFactor = -MutationFactor;
        }

        // Calculate new gene
	    sp.Genes[0] = sp.Genes[0] * (1 + MutationFactor);
	    
	}
}
