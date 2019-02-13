//Rohit Kulkarni, lab2, 12M, 1/24/19, FileReverse.java
//Takes in file with text, separates the file into tokens, reverses 
//those tokens, then prints them out onto a file called out.

import java.io.*;
import java.util.Scanner;

public class FileReverse {
	public static String stringReverse(String s, int n)
	{
		// if n==0 do nothing
	      
		if(n > 0) { 
	         return s.charAt(n-1) + stringReverse(s,n-1); // print nth element from left
	      }
		return "";
	}

	public static void main(String[] args)
	{
		Scanner in = null;
	    PrintWriter out = null;
	    String line = null;
	    String[] token = null;
	    int i, n, lineNumber = 0;
	    // check number of command line arguments is at least 2
	    if(args.length < 2){
	      System.out.println("Usage: FileCopy <input file> <output file>");
	      System.exit(1);
	    }

	    // open files
	    try {
			in = new Scanner(new File(args[0]));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	    try {
			out = new PrintWriter(new FileWriter(args[1]));
		} catch (IOException e) {
			e.printStackTrace();
		}
	    // read lines from in, extract and print tokens from each line
	    while( in.hasNextLine() ){
	      lineNumber++;
	      // trim leading and trailing spaces, then add one trailing space so
	      // split works on blank lines
	      line = in.nextLine().trim() + " ";
	      // split line around white space
	      token = line.split("\\s+");
	      // print out tokens
	      n = token.length;
	      for(i=0; i<n; i++){
	        out.println(stringReverse(token[i], token[i].length()));
	      }
	    }
	    // close files
	    in.close();
	    out.close();

	}
}
