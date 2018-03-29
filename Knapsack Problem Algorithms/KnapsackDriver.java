/*
 * Jack O'Neil
 * 7 November, 2017
 * Project #2
 */
import java.util.Random;

public class KnapsackDriver {
	public static void main(String[] args) {
		//instructorTestCases();
		greedyExperiment();
		System.out.println("\nDone!");
	}
	
	public static void greedyExperiment() {
		int nTrials = 10; 
		
		System.out.println("\n| GREEDY APPROXIMATE EXPERIMENT |");
		for (int i = 0; i < nTrials; i++) {
			Knapsack kp = randomKnapsack(new int[] {
				5, 15, 		// n bounds
				1, 25, 		// weight bounds
				1, 25, 		// benefit bounds
				25, 50});	// W bounds	
			System.out.printf("\n-----Trial %d-----\n", i + 1);
			
			printKnapsackInput(kp);	
		
			System.out.println("\tBrute Force Solution");  
			kp.BruteForceSolution();
			   
			System.out.println("\tGreedy Approximate Solution");
			kp.GreedyApproximateSolution();
		}
	}
	
	public static void instructorTestCases() {
		System.out.println("| TEST CASES |");
		// Test Case #1 (from instructions)
		System.out.println("\n-----Test Case #1 (from instructions)-----");
		testCase(new int[] {-1, 2, 4, 3, 4, 4, 1}, 				// weights
				new int[] { -1 , 1, 2, 3, 3, 3, 6},   			// benefits
				10, false, false);			// W, print input, print matrix
		// Test Case #1 (Instructor Testcases)
		System.out.println("\n-----Test Case #1 (Instructor Testcases)-----");
		testCase(new int[] {-1, 60, 50, 60, 50, 70, 70, 45},
				new int[] {-1, 180, 95, 40, 95, 40, 40, 105},
				100, false, false);
		// Test Case #2 (Instructor Testcases)
		System.out.println("\n-----Test Case #2 (Instructor Testcases)-----");
		testCase(new int[] {-1,25,4,2,5,6, 2,7,8,2,1, 1,3,5,8,9,  6,3,2},
				new int[] {-1,75,7,4,3,2,  6,8,7,9,6,  5,4,8,10,8,  1,2,2},
				39, false, false);
		// Test Case #3 (Instructor Testcases)
		System.out.println("\n-----Test Case #3 (Instructor Testcases)-----");
		testCase(new int[] {-1, 10,14,35,12,16, 20,13,7,2,4, 3,10,5,6,17, 
                7,9,3,4,3},
				new int[] {-1, 2,13,41,1,12, 5,31,2,41,16, 
                        2,12,1,13,4, 51,6,12,1,9},
				29, false, false);
		// Test Case #4 (Instructor Testcases)
		System.out.println("\n-----Test Case #4 (Instructor Testcases)-----");
		testCase(new int[] {-1, 2,5,3,2,5,3,7 },
				new int[] {-1, 5,10,5,20,15,5,10},
				10, false, true);
	}
	
	public static Knapsack randomKnapsack(int[] bounds) {
		// bounds: 
		// 0: min n 	2: min weight	4: min benefit 	6: min W 
		// 1: max n 	3: max weight 	5: max benefit	7: max W
		Random rand = new Random();
		int n = rand.nextInt((bounds[1] - bounds[0]) + 1) + bounds[0];
		int[] weights = new int[n + 1];
		int[] benefits = new int[n + 1];
		int W = rand.nextInt((bounds[7] - bounds[6]) + 1) + bounds[6];
		weights[0] = -1;
		benefits[0] = -1;
		for (int i = 1; i < n + 1; i++) {
			weights[i] = rand.nextInt((bounds[3] - bounds[2]) + 1) + bounds[2];
			benefits[i] = rand.nextInt((bounds[5] - bounds[4]) + 1) + bounds[4];
		}
		return new Knapsack(W, weights, benefits);
	}
	
	public static void printKnapsackInput(Knapsack kp) {
		System.out.printf("Number of items = %d Knapsack Capacity = %d"
				+ "\nInput weights:  [", kp.n, kp.W);
		for (int i = 0; i < kp.n; i++)
			System.out.printf("%d, ", kp.weights[i]);
		System.out.printf("%d]\nInput benefits: [", kp.weights[kp.n]);
		for (int i = 0; i < kp.n; i++)
			System.out.printf("%d, ", kp.benefits[i]);
		System.out.printf("%d]\n", kp.benefits[kp.n]);
	}
	
	public static void testCase(int[] weights, int[] benefits, int W, 
			boolean printInput, boolean printMatrix) {
		Knapsack kp = new Knapsack(W, weights, benefits);
		if (printInput) printKnapsackInput(kp);
		
		System.out.println("\n\tBrute Force Solution");  
		kp.BruteForceSolution();
		       
		System.out.println("\n\tDynamic Programming Solution");
		kp.DynamicProgrammingSolution(printMatrix);
		   
		System.out.println("\n\tGreedy Approximate Solution");
		kp.GreedyApproximateSolution();
	}
}