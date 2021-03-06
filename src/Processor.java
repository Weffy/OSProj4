import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Processor extends Thread {
	final static int NUMOFTASKS = 200;
	final static int CPUS = 16;
	static int active_cpus = CPUS;
	static ArrayList<Processor> cpuArr = new ArrayList<Processor>();
	static long startTime;
	static long endTime;

	String name;
	Thread process = new Thread();
	static Scheduler sched;
	
	public static void main(String[] args) {
		startTime = System.currentTimeMillis();
		sched = new Scheduler(NUMOFTASKS);
		createCPUs(CPUS);
		sched.printQueue();
		for (int i = 0; i < cpuArr.size(); i++) {
			System.out.println("Starting: " + cpuArr.get(i).name);
			cpuArr.get(i).start();
		}

	}
	/*
	 * Creates the specified number of cpus/threads
	 */
	private static void createCPUs(int count) {
		// TODO Auto-generated method stub
		for (int i = 0; i < count; i++) {
			Processor cpu = new Processor("cpu_" + i);
			cpuArr.add(cpu);
		}
	}
	/*
	 * used to sleep a thread for the duration of the task
	 */
	public void zzz(long duration) {
		try {
			TimeUnit.MILLISECONDS.sleep(duration);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/*
	 * accessor for name
	 */
	public String threadName() {
		return name;
	}
	/*
	 * constructor
	 */
	public Processor(String name) {
		this.name = name;
	}
	/*
	 * prints the CPU statistics
	 */
	public static void printTiming() {
		System.out.println("\n---------------------------------------------------------------");
		System.out.println("\nNumber of Processors: " + CPUS);
		System.out.println("Number of Tasks: " + NUMOFTASKS);
		System.out.println("Total Elapsed Time: " + (endTime - startTime) + " mS.\n");
		System.out.println("---------------------------------------------------------------\n");

		
	}
	/*
	 * Run method for the threads
	 */
	public void run() {
		Task task = null;
		while (!sched.empty()) {
			System.out.println(this.name + " getting a task...");
	 		task = sched.getTask();
//	 		zzz(900);
	 		if (task == null) {
	 			continue;
	 		}
	 		/*
	 		 * Provides updates as the threads pull tasks
	 		 */
			//System.out.println(this.name + " pulled a " + task.getDuration() + "mS task.");
			if (sched.schedSelection == 1 || sched.schedSelection == 2) {
				zzz(task.getDuration());
				task.setCompletionTime();
				task.calcTurnaroundTime();
				System.out.println(this.name + " completed the task.");
				task.individualStats();
			} else if (sched.schedSelection == 3) {
				zzz(sched.TIMESLICE);
				if (task.getDuration() <= 0) {
					task.setCompletionTime();
					task.calcTurnaroundTime();
					System.out.println(this.name + " completed the task.");
					task.individualStats();
				} else if (task.getDuration() > 0) {
					System.out.println("Duration greater than 0");
					sched.roundRobin(task);
//					continue;
				} else {
					System.out.println("Task " + task.name + " has " + task.getDuration() + "mS remaining...");
				}
			}
			System.out.println(this.name + " at the end of the loop...");
		}
		/*
		 * once all cpus are no longer active, print the statistics
		 */
		active_cpus--;
		System.out.println("Active CPUS: " + active_cpus);
		if (active_cpus == 0) {
			task.totalStats();
			endTime = System.currentTimeMillis();
			printTiming(); //cpu timing
		}
	}
}
