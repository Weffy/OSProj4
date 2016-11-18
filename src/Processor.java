import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Processor extends Thread {
	final static int NUMOFTASKS = 10;
	final static int CPUS = 4;
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
	
	private static void createCPUs(int count) {
		// TODO Auto-generated method stub
		for (int i = 0; i < count; i++) {
			Processor cpu = new Processor("cpu_" + i);
			cpuArr.add(cpu);
		}
	}
	public void zzz(long duration) {
		try {
			TimeUnit.MILLISECONDS.sleep(duration);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public String threadName() {
		return name;
	}
	public Processor(String name) {
		this.name = name;
	}
	public static void printTiming() {
		System.out.println("\n---------------------------------------------------------------");
		System.out.println("\nNumber of Processors: " + CPUS);
		System.out.println("Number of Tasks: " + NUMOFTASKS);
		System.out.println("Total Elapsed Time: " + (endTime - startTime) + " mS.\n");
		System.out.println("---------------------------------------------------------------\n");

		
	}
	public void run() {
		Task task = null;
		while (!sched.empty()) {
	 		task = sched.getTask();
//	 		zzz(900);

			System.out.println(this.name + " pulled a " + task.getDuration() + "mS task.");
			zzz(task.getDuration());
			task.setCompletionTime();
			task.calcTurnaroundTime();
			System.out.println(this.name + " completed the task.");
			task.individualStats();
		}
		active_cpus--;
		if (active_cpus == 0) {
			task.totalStats();
			endTime = System.currentTimeMillis();
			printTiming(); //cpu timing
		}
	}
}
