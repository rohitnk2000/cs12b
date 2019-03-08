import java.io.*;
import java.util.Scanner;
import java.lang.String;
import java.text.DecimalFormat;

public class Simulation {

	public static Job getJob(Scanner in) {
		String[] s = in.nextLine().split(" ");
		int a = Integer.parseInt(s[0]);
		int d = Integer.parseInt(s[1]);
		return new Job(a, d);
	}

	public static void main(String[] args) throws IOException {
		Queue tempStorage = new Queue();
		Queue processor = new Queue();
		Queue finishedJobs = null;
		boolean printTrace = false;
		int time, totalWait, maxWait, waitTime;
		double averageWait;
		Job A = null;
		Job B = null;
		Job C = null;
		Job D = null;

		Scanner sc = new Scanner(new File(args[0]));
		PrintWriter report = new PrintWriter(new FileWriter(args[0] + ".rpt"));
		PrintWriter trace = new PrintWriter(new FileWriter(args[0] + ".trc"));

		if (args.length != 1) {
			System.out.println("Usage: Simulation <input file>");
			System.out.println();
			System.exit(1);
		}

		int job = Integer.parseInt(sc.nextLine());
		Queue[] processorQueue = null;

		while (sc.hasNextLine()) {
			Job getJob = getJob(sc);
			tempStorage.enqueue(getJob);
			processor.enqueue(getJob);
		}

		trace.println("Trace file: " + args[0] + ".trc");
		trace.println(job + " Jobs:");
		trace.println(tempStorage.toString());
		trace.println();

		report.println("Report file: " + args[0] + ".rpt");
		report.println(job + " Jobs:");
		report.println(tempStorage.toString());
		report.println();
		report.println("***********************************************************");

		for (int count = 1; count < job; count++) {

			time = 0;
			totalWait = 0;
			maxWait = 0;
			averageWait = 0;
			processorQueue = new Queue[count];
			for (int i = 0; i < count; i++) {
				processorQueue[i] = new Queue();
			}
			finishedJobs = new Queue();
			trace.println("*****************************");
			if (count == 1) {
				trace.println((count + " processor:"));
			} else {
				trace.println((count + " processors:"));
			}
			trace.println("*****************************");
			
			while (finishedJobs.length() != job) {
				
				for (int i = 0; i < count; i++) {
					if (!processorQueue[i].isEmpty()) {
						B = (Job) processorQueue[i].peek();
						if (B.getFinish() == time) {
							totalWait += B.getWaitTime();
							finishedJobs.enqueue(processorQueue[i].dequeue());
							printTrace = true;
							if (B.getWaitTime() > maxWait) {
								maxWait = B.getWaitTime();
							}
							if (processorQueue[i].isEmpty() != true) {
								A = (Job) processorQueue[i].peek();
								A.computeFinishTime(time);
							}
						}
					}
				}

				if (tempStorage.isEmpty() != true) {

					D = (Job) tempStorage.peek();
					int arrivalTime = D.getArrival();
					while (arrivalTime == time) {
						printTrace = true;
						C = (Job) tempStorage.dequeue();
						int x = 0;
						int minSize = processorQueue[0].length();
						for (int i = 0; i < count; i++) {
							if (processorQueue[i].length() < minSize) {
								minSize = processorQueue[i].length();
								x = i;
							}
						}

						processorQueue[x].enqueue(C);

						if (processorQueue[x].length() == 1) {
							A = (Job) processorQueue[x].peek();
							A.computeFinishTime(time);
						}
						if (tempStorage.isEmpty() != true) {
							D = (Job) tempStorage.peek(); 
							arrivalTime = D.getArrival(); 
						} else {
							arrivalTime = 0; 
						}
					}
				}

				if ((printTrace == true) || (time == 0)) {
					trace.println("time=" + time);
					trace.println("0: " + tempStorage.toString() + finishedJobs.toString());
					for (int i = 0; i < count; i++) {
						trace.println(i + 1 + ": " + processorQueue[i].toString());
					}
					trace.println();
				}
				time++;
				printTrace = false;
			}

			DecimalFormat round = new DecimalFormat("#0.00");
			averageWait = ((double) totalWait / job);
			averageWait = Math.round(averageWait * 100.0) / 100.0;

			if (count == 1) {
				report.println("1 processor: totalWait=" + totalWait + ", maxWait=" + maxWait + ", averageWait="
						+ round.format(averageWait));
			} else {
				report.println(count + " processors: totalWait=" + totalWait + ", maxWait=" + maxWait + ", averageWait="
						+ round.format(averageWait));
			}

			for (int i = 1; i <= job; i++) {
				Job resetJob = (Job) processor.dequeue();
				resetJob.resetFinishTime();
				tempStorage.enqueue(resetJob);
				processor.enqueue(resetJob);
			}
		}
		sc.close();
		report.close();
		trace.close();

	}

}
