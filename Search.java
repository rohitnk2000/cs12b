package pa1;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;	
public class Search {

	static int binarySearch(String[] A, int p, int r, String t)
	{
		int q;
		if(p >= r)
			return -1;
		else{
			q = (p+r)/2;
			if(A[q].compareTo(t) == 0)
				return q;
			else if(A[q].compareTo(t) < 0)
				return binarySearch(A, p, q, t);
			else // target > A[q]
				return binarySearch(A, q+1, r, t);
		}
	}
	static void mergeSort(String[] word, int[] lineNumber, int p, int r) {
		int q;
		if (p < r) {
			q=(p+r)/2;
			mergeSort(word, lineNumber, p, q);
			mergeSort(word, lineNumber, q+1, r);
			merge(word, lineNumber, p, q, r);
		}
	}
	static void merge(String[] word, int[] lineNumber, int p, int q, int r) {
		int n1 = q-p+1;
		int n2 = r-q;
		String[] left = new String[n1];
		String[] right = new String[n2];
		int[] leftNum = new int[n1];
		int[] rightNum = new int[n2];
		int i, j, k;

		for(i=0; i<n1; i++){
			left[i] = word[p+i];
			leftNum[i] = lineNumber[p+i];
		}
		for(j=0; j<n2; j++){
			right[j] = word[q+j+1];
			rightNum[j] = lineNumber[q+j+1];
		}
		i = 0;
		j = 0;
	
		for(k=p; k<=r; k++){
			if( i<n1 && j<n2){
				if( left[i].compareTo(right[j])>0 ){
					word[k] = left[i];
					lineNumber[k] = leftNum[i]; 
					i++;
				}
				else{
					word[k] = right[j];
					lineNumber[k] = rightNum[j];
					j++;
				} 
			}
			else if( i<n1){
				word[k] = left[i];
				lineNumber[k] = leftNum[i];
				i++;
			}
			else{  // j<n2
				word[k] = right[j];
				lineNumber[k] = rightNum[j];
				j++;
			} 
		}
	}

	public static void main(String[] args) throws FileNotFoundException  {
		int n = 0;
		String line = null;

		if(args.length < 2) {
			System.err.println("Usage: Search file target1 [target2 target3 ..]");
			System.exit(1);
		}

		Scanner in = new Scanner(new File(args[0]));
		while(in.hasNextLine()) {
			line = in.nextLine();
			n++;
		}

		String[] A = new String[n];
		int[] lineNumber = new int[n];
		try {
			in = new Scanner(new File(args[0]));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		for(int i=1; i<=lineNumber.length; i++) lineNumber[i-1] = i;

			for(int i = 0; in.hasNextLine(); i++) {
				line = in.nextLine();
				A[i] = line;
			}

			mergeSort(A, lineNumber, 0, A.length-1);
			for(int i=1; i<args.length; i++)
				if(binarySearch(A, 0, A.length-1, args[i]) != -1)
				{
					System.out.println(args[i] + " found on line " + 
							lineNumber[binarySearch(A, 0, A.length-1, args[i])]);
				}
				else
				{
					System.out.println(args[i] + " not found");
				}
			in.close();
		}
	     
	}
	


