/*
 * Jack O'Neil
 * 26 September, 2017
 * Project #1
 */
public class Sorts {

	//returns true only if a is sorted from smallest to largest values
	public static boolean isSorted(int[] a) {
		for (int i = 0; i < a.length - 1; i++) {
			if (a[i] > a[i + 1])
				return false;
		}
		return true;
	}

	/* --------------------MergeSort --------------------*/
	//merges sorted slices a[i.. j] and a[j + 1 …  k] for 0<=  i <=j < k < a.length
	public static void merge(int[] a, int i, int j, int k) { 
		int pointer1 = i;					// starts at beginning of slice a[i.. j]
		int pointer2 = j + 1;				// starts at beginning of slice a[j + 1 …  k]
		int pointerTemp = 0;				// pointer for temporary array
		int[] temp = new int[k - i + 1];	// temporary array
		while (pointer1 <= j && pointer2 <= k) {
			if (a[pointer1] <= a[pointer2]) {		// compare integers between the two slices
				temp[pointerTemp++] = a[pointer1++];	// and sorts them into temp
			}
			else {
				temp[pointerTemp++] = a[pointer2++];
			}
		}
		while (pointer1 <= j) {						// add whatever is left into temp
			temp[pointerTemp++] = a[pointer1++];
		}
		while (pointer2 <= k) {
			temp[pointerTemp++] = a[pointer2++];
		}
		for (int x = 0; x < temp.length; x++) {		// replaces the two original slices with
			a[x + i] = temp[x];						// the sorted temporary array
		}
	}

	//sorts  a[ i .. k]  for 0<=i <= k < a.length
	public static void mergeSort(int[] a, int i , int k) {   
		if (i < k) {
			mergeSort(a, i, (i + k) / 2);
			mergeSort(a, ((i + k) / 2) + 1, k);
			merge(a, i, (i + k) / 2, k);
		}
	}
	
	//Sorts the array a using mergesort 
	public static void mergeSort(int[] a) { 
		mergeSort(a, 0, a.length - 1);
	}

	/* ----------   QuickSort ---------------------------------------------- */
	 
	//Implements in-place partition from text. Partitions subarray s[a..b] into s[a..l-1] and s[l+1..b] 
	// so that all elements of  s[a..l-1] are less than each element in s[l+1..b]

	public static int partition(int[] s, int a, int b) {
		int pivot = s[b];		// the pivot
		int left = a;			// scans rightward
		int right = b - 1;		// scans leftward
		int k = 0;				// for swaps
		while (left <= right) {	// find an element larger than the pivot
			while (left <= right && s[left] <= pivot) {
				left++;
			}
			while (right >= left && s[right] >= pivot) {	// find an element smaller than the pivot
				right--;
			}
			if (left < right) {
				k = s[right];
				s[right] = s[left];
				s[left] = k;
			}
		}
		k = s[left];			// put the pivot into its final place
		s[left] = s[b];
		s[b] = k;
		return left;
	}

	//quick sorts subarray a[i..j]
	public static void quickSort(int[] a, int i, int j) { 
		if (i >= j) return;
		int l = partition(a, i, j);
		quickSort(a, i, l - 1);
		quickSort(a, l + 1, j);
	}

	//quick sorts array a
	public static void quickSort(int[] a) {
		quickSort(a, 0, a.length - 1);
	}
}
