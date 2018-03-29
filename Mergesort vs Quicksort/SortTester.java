/*
 * Jack O'Neil
 * 26 September, 2017
 * Project #1
 */
import java.util.Arrays;
import java.util.Random;

public class SortTester {
	public static void main(String[] args) {
										// the array sizes to be tested nTrials each
		int n[] = {10, 100, 1000, 10000, 100000, 1000000, 2000000};
		
		int nTrials = 20;				// number of trials
		
		int max = 1000000;				// maximum integer value of each array
										// (minimum is always 0)
		
		boolean printOut = true;		// set true to print out results of experiment #1
		boolean showMeanCalc = true;	// set true to display the calculations behind
										// the mean values in experiment #2
		
		long[][][] allTimes;			// all the completion times
		
		// output the sort of the array provided in directions
		exampleSort();
		
		// run experiment #1 for nTrials = 20
		allTimes = experiment1(n, nTrials, max, printOut);
		System.out.println("\n\n");
		
		
		// get new times for nTrials = 1000 > 20 using experiment #1
		printOut = false;
		nTrials = 100;
		allTimes = experiment1(n, nTrials, max, printOut);
		experiment2(n, nTrials, max, allTimes, showMeanCalc);
		System.out.println("Done!");
	}
	
	// Runs experiment #1.
	public static long[][][] experiment1(int[] n, int nTrials, int max, boolean printOut) {
		int[][] allWins = new int[n.length][nTrials];
		long [][][] allTimes = new long[n.length][nTrials][2];
		
		// run the experiment
		for (int i = 0; i < n.length; i++) {;
			allTimes[i] = runNTrials(nTrials, n[i], max);
		}
		
		// tally the wins
		for (int i = 0; i < n.length; i++) {
			allWins[i] = tallyWins(nTrials, allTimes[i]);
		}
		
		// print out results
		if (printOut) {
			System.out.println( "\t\t\tExperiment #1\n" + 
								"----------------------------------------------------------------\n" + 
								"n\t\tnTrials\t# mergeSort Wins\t# quickSort Wins\n" +
								"----------------------------------------------------------------");
			for (int i = 0; i < n.length; i++) {
				System.out.println(n[i] + "\t\t" + nTrials + "\t\t" +
						allWins[i][0] + "\t\t\t" + allWins[i][1]);
			}
			System.out.println( "----------------------------------------------------------------");
		}
		
		return allTimes;
	}
	
	// Runs experiment #2.
	public static void experiment2(int[] n, int nTrials, int max, long[][][] allTimes, boolean showMeanCalc) {
		double[][] allMeans = new double[n.length][4];
		
		// calculate means
		for (int i = 0; i < n.length; i++) {
			allMeans[i] = calcMeans(nTrials, n[i], allTimes[i], showMeanCalc);
		}
		
		// print out results
		System.out.println( "\t\t\tExperiment #2\n" + 
							"-------------------------------------------------------------------------------\n" + 
							"n\tmergeSort:\tmergeSort:\t\tquickSort:\tquickSort:\n" +
							"\tmean runtime\tmean runtime/\t\tmean runtime\tmean runtime /\n" +
							"\t(nanosecs)\t(n*log2(n))\t\t(nanosecs)\t(n*log2(n))\n" +
							"-------------------------------------------------------------------------------");
		for (int i = 0; i < n.length; i++) {
			System.out.printf("%d\t%.2f\t\t%.10f\t\t%.2f\t\t%.10f\n", 
					n[i], allMeans[i][0], allMeans[i][1], allMeans[i][2], allMeans[i][3]);
		}
		System.out.println( "-------------------------------------------------------------------------------");
		
	}
		
	// Compares all mergeSort and quickSort times 
	// and returns array describing the sort type that was fastest for each trial.
	// Used in experiment #1.
	public static int[] tallyWins(int nTrials, long[][] timesForThisN) {
		int[] winsForThisN = new int[2];
		for (int i = 0; i < nTrials; i++) {
			if (timesForThisN[i][0] <= timesForThisN[i][1]) {
				winsForThisN[0]++;
			}
			else {
				winsForThisN[1]++;
			}
		}
		return winsForThisN;
	}
	
	// Calculates data for experiment #2 based on the times taken from experiment #1.
	public static double[] calcMeans (int nTrials, int n, long[][] timesForThisN, boolean showMeanCalc) {
		double[] means = new double[4];
		long mSum = 0, qSum = 0;
		
		if (showMeanCalc) {
			System.out.println("For n = " + n);
			System.out.printf("   mergeSort mean runtime =\n     (");
		}
		
		// calculate mergeSort mean runtime (ns)
		for (int i = 0; i < nTrials; i++) {
			mSum += timesForThisN[i][0];
			if (showMeanCalc) {
				System.out.print(timesForThisN[i][0] + " + ");
			}
		}
		means[0] = (double)mSum / (double)nTrials;
		
		// calculate mergeSort mean runtime / n*log(n)
		means[1] = (double)means[0] / (double)(n * (Math.log10(n) / Math.log10(2)));
		
		if (showMeanCalc) {
			System.out.printf(" 0) / " + nTrials + "\n      = " + means[0]
					+ "\n   mergeSort mean runtime / (n*log2(n)) =\n     " + means[0] + "/ " + n + "*log("
					+ n + ") = %.10f\n   quickSort mean runtime =\n     (", means[1]);
		}
		
		// calculate quickSort mean runtime (ns)
		for (int i = 0; i < nTrials; i++) {
			qSum += timesForThisN[i][1];
			if (showMeanCalc) {
				System.out.print(timesForThisN[i][1] + " + ");
			}
		}
		means[2] = (double)qSum / (double)nTrials;
		
		// calculate quickSort mean runtime / n*log(n)
		means[3] = (double)means[2] / (n * (Math.log10(n) / (double)Math.log10(2)));
		
		if (showMeanCalc) {
			System.out.printf (" 0) / " + nTrials  + "\n      = " + means[1]
					+ "\n   quickSort mean runtime / (n*log2(n)) =\n     " + means[2] + "/ " + n + "*log("
					+ n + ") = %.10f\n\n", means[3]);
		}
		
		return means;
	}
	
	// Sorts a random n-element array with mergeSort and quickSort, for nTrials 
	public static long[][] runNTrials(int nTrials, int n, int max) {
		Random rand = new Random();
		long startTime = 0, finishTime = 0;
		long[][] times = new long[nTrials][2];
		// run the trials for given number of times
		for (int j = 0; j < nTrials; j++) {
			// initialize array
			int[] array1 = new int[n];		// for mergeSort
			int[] array2;					// for quickSort
			
			for (int i = 0; i < n; i++) {
				array1[i] = rand.nextInt(max + 1);
			}
			array2 = Arrays.copyOf(array1, array1.length);		// array2 is copy of array1
			
			// mergeSort and time array1
			startTime = System.nanoTime();
			Sorts.mergeSort(array1);
			finishTime = System.nanoTime();
			times[j][0] = finishTime - startTime;
			
			// check if array1 is sorted
			sortWarning(array1, j + 1, true); 
			
			// quickSort and time array2
			startTime = System.nanoTime();
			Sorts.quickSort(array2);
			finishTime = System.nanoTime();
			times[j][1] = finishTime - startTime;
			
			// check if array2 is sorted
			sortWarning(array2, j + 1, false); 
		}
		return times;
	}
	
	// Prints the given array to console.
	public static void printArray(int[] a) {
		System.out.print("[ ");
		for (int i = 0; i < a.length - 1; i++) {
			if (a[i] == 10)
				System.out.print(a[i] + " , ");
			else 
				System.out.print(a[i] + " ,  ");
		}
		if (a[a.length - 1] >= 10)
			System.out.println(a[a.length - 1] + " ]");
		else 
			System.out.println(a[a.length - 1] + "  ]");
	}
	
	// Checks if given array is sorted; outputs a warning to the console if it isn't.
	public static void sortWarning(int[] a, int trial, boolean isMergeSort) {
		if (!Sorts.isSorted(a)) {
			System.out.print("WARNING: Trial " + trial + " failed to sort. Sort type: ");
			if (isMergeSort)
				System.out.println("mergeSort");
			else
				System.out.println("quickSort");
		}
	}
	
	// Demonstrates the sorting of the array provided in the directions
	public static void exampleSort() {
		// two identical arrays from directions
		int a[] = {34, 67, 23, 19, 122, 300, 2, 5, 17, 18, 5, 4, 3, 19, -40, 23};
		int b[] = a;
		System.out.println("----- Example Sort (from directions) -----");
		
		// display original array
		System.out.print("          Original array: ");
		printArray(a);
		
		// sort array a with mergeSort
		Sorts.mergeSort(a);
		
		// sort array b with mergeSort
		Sorts.quickSort(b);
		
		// print sorted arrays and verify they are sorted
		System.out.print("Sorted array (mergeSort): ");
		printArray(a);
		System.out.print("  isSorted? -- ");
		if (Sorts.isSorted(a))				// is a sorted?
			System.out.println("YES");
		else
			System.out.println("NO");
		
		System.out.print("Sorted array (quickSort): ");
		printArray(b);
		System.out.print("  isSorted? -- ");
		if (Sorts.isSorted(b))				// is b sorted?
			System.out.println("YES");
		else
			System.out.println("NO");
	}
}
