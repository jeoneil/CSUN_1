/*
 * Jack O'Neil
 * 7 November, 2017
 * Project #2
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Knapsack {
	
	int n;
	int W;
	int[] weights;
	int[] benefits;
	
	public Knapsack(int W, int[] w, int[] b) {
		this.W = W;
		this.weights = w;
		this.benefits = b;
		n = benefits.length - 1;
	}
	
	public static int[] generateSubset(int k, int n) {
		if (k < 0 || k > Math.pow(2, n) - 1) {
			System.out.printf("ERROR: k = %d > 2^%d - 1 or negative \n", k, n);
			return null;
		}
		String bitKStr;
		int[] bitK = new int[n];
		bitKStr = Integer.toBinaryString(k);
		while (bitKStr.length() < n)
			bitKStr = 0 + bitKStr;
		for (int i = 0; i < bitKStr.length(); i++)
			bitK[i] = Character.getNumericValue(bitKStr.charAt(i));
		return bitK;
	}
	
	public void BruteForceSolution() {
		final int MAX_K = (int)Math.pow(2, n) - 1;
		ArrayList<ArrayList<Integer>> optimalIndices = new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> allBestK = new ArrayList<Integer>(), temp = new ArrayList<Integer>();
		int[] kthSubset = new int[n];
		int[] weightSum = new int[MAX_K], benefitSum = new int[MAX_K];
		int bestBenefitSum = 0;
		boolean duplicate;
		
		// GENERATE SOLUTION
		// Try filling the knapsack for each subset k
		for (int k = 0; k < MAX_K; k++) {
			kthSubset = generateSubset(k, n);
			optimalIndices.add(new ArrayList<Integer>());
			for (int i = 0; i < n; i++) {
				int thisWeight = weights[i + 1];
				// Check for subset's feasibility here
				if (kthSubset[i] == 1 && (weightSum[k] + thisWeight <= W)) {
					optimalIndices.get(k).add(i);
					weightSum[k] += thisWeight;
					benefitSum[k] += benefits[i + 1];
				}
			}
			if (benefitSum[k] > bestBenefitSum) 	// Check if this subset produced the
				bestBenefitSum = benefitSum[k];		// best benefit sum thus far
		}
		
		// Find all optimal solutions
		for (int k = 0; k < MAX_K; k++)
			if (benefitSum[k] == bestBenefitSum) {
				duplicate = false;
				temp = optimalIndices.get(k);
				for (int i = 0; i < allBestK.size() && !duplicate; i++)
					if (temp.equals(optimalIndices.get(allBestK.get(i))))
						duplicate = true;
				if (!duplicate)
					allBestK.add(k);
			}
		
		// PRINT SOLUTIONS
		for (int j = 0; j < allBestK.size(); j++) {
			int k = allBestK.get(j);
			System.out.printf("Optimal Set = { ");
			for (int i = 0; i < optimalIndices.get(k).size() - 1; i++)
				System.out.printf("%d, ", optimalIndices.get(k).get(i) + 1);
			System.out.printf("%d }  weight sum = %d  benefit sum = %d\n", 
				optimalIndices.get(k).get(optimalIndices.get(k).size() - 1) + 1, 
				weightSum[k], benefitSum[k]);
		} // increment weightSum & benefitSum by one to one-index the items
		
	}
	
	public void DynamicProgrammingSolution(boolean printBmatrix) {
		ArrayList<Integer> optimalIndices = new ArrayList<Integer>();
		int[][] B = new int[n + 1][W + 1];
		int weightSum = 0, benefitSum = 0;
		
		// GENERATE SOLUTION
		// Fill in matrix with algorithm
		for (int i = 0; i < n + 1; i++)
			B[i][0] = 0;
		for (int i = 0; i < W + 1; i++)
			B[0][i] = 0;
		for (int k = 1; k < n + 1; k++)
			for (int w = 1; w < W + 1; w++) {
				if (weights[k] > w)
					B[k][w] = B[k - 1][w];
				else
					B[k][w] = Math.max(B[k - 1][w], 
							B[k - 1][w - weights[k]] + benefits[k]);
			}
		
		// Print matrix, if requested and not too big
		if (printBmatrix && n <= 10 && W <= 10)
			for (int k = 0; k < n + 1; k++)
				System.out.println(Arrays.toString(B[k]));
		
		// Backtrack through matrix to find solution
		for (int k = n, w = W; k > 0 & w > 0; k--)
			if (weights[k] <= w && 
					B[k - 1][w] < B[k - 1][w - weights[k]] + benefits[k]) {
	            	optimalIndices.add(k - 1);
	            	w -= weights[k];
	        }
		Collections.sort(optimalIndices);
		
		// Calculate optimal weight and benefit sums
		for (int i = 0; i < optimalIndices.size(); i++) {
			weightSum += weights[optimalIndices.get(i) + 1];
			benefitSum += benefits[optimalIndices.get(i) + 1];
		}
		
		// PRINT SOLUTION
		printSolution(optimalIndices, weightSum, benefitSum);
	}
	
	public void GreedyApproximateSolution() {
		double[] values = new double[n];
		Item[] sortedItems = new Item[n];
		ArrayList<Integer> optimalIndices = new ArrayList<Integer>();
		int weightSum = 0, benefitSum = 0, thisWeight = 0;
		 
		// GENERATE SOLUTION
		// calculate the value of each item
		for (int i = 0; i < n; i++)
			values[i] = (double)benefits[i + 1] / weights[i + 1];
		
		// sort values, keeping track of original indices with Item object
		for (int i = 0; i < n; i++)
			sortedItems[i] = new Item(values[i], i);
		Arrays.sort(sortedItems);
		
		// fill the knapsack greedily
		for (int i = n - 1; i >= 0; i--) {
			thisWeight = weights[sortedItems[i].index + 1];
			if (weightSum + thisWeight <= W) {
				optimalIndices.add(sortedItems[i].index);
				weightSum += thisWeight;
				benefitSum += benefits[sortedItems[i].index + 1];
			}
		}
		Collections.sort(optimalIndices);
		
		// PRINT SOLUTION
		printSolution(optimalIndices, weightSum, benefitSum);
		
	}
	
	private void printSolution(ArrayList<Integer> optimalIndices, 
			int weightSum, int benefitSum) {
		System.out.printf("Optimal Set = { ");
		for (int i = 0; i < optimalIndices.size() - 1; i++)
			System.out.printf("%d, ", optimalIndices.get(i) + 1);
		System.out.printf("%d }  weight sum = %d  benefit sum = %d\n",
				optimalIndices.get(optimalIndices.size() - 1) + 1, 
				weightSum, benefitSum);
	}	// increment weightSum & benefitSum by one to one-index the items
	
	private class Item implements Comparable<Item>{
		private double value;
		private int index;
		private Item(double value, int index) {
			this.value = value;
			this.index = index;
		}
		@Override public int compareTo(Item that) {
			if (this.value < that.value) return -1;
			if (this.value == that.value) return 0;
			else return 1;
		}
		@Override public String toString() {
			return "(" + String.valueOf(value) + ", " + String.valueOf(index) + ")";
		}
	}
}