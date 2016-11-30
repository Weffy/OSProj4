
public class Task {
	int duration;
	int name;
	static int count;
	/*
	 * is this even necessary?
	 * C++ has a tendency to give very weird numbers
	 * because of trash data that is sitting in a memory location
	 * when you first initialize the variable...
	 * do we need to set variables to 0 in java?
	 * or does it do that for us???
	 */
	static long totalTurnaround = 0;
	static long totalResponse = 0;
	
	static long totalLength = 0;
	
	long arrivalTime = 0;
	long startTime = 0;
	long completionTime = 0;
	
	long turnaroundTime = 0;
	long responseTime = 0;
	
	/*
	 * didn't like the implementation of the constructor
	 * specifically regarding the count.
	 * the count is the number of the tasks that the cpu will be handling.
	 * however, the task class doesn't have access to the queue
	 * because the task object gets placed in the queue
	 * but, I needed the count in order to calculate statistics
	 * the static int count is constantly overwritten with the same number
	 * every time we make a new task object
	 */
	public Task(int duration, int count, int name) {
		this.name = name;
		this.duration = duration;
		this.count = count;
		totalLength = totalLength + duration;
		arrivalTime = System.currentTimeMillis();
	}
		/*
		 * accessor for duration
		 */
	public int getDuration() {
		return duration;
	}
/*
 * methods used to time the threads
 */
	public void setStartStime() {
		// TODO Auto-generated method stub
		this.startTime = System.currentTimeMillis();
	}

	public void setCompletionTime() {
		// TODO Auto-generated method stub
		this.completionTime = System.currentTimeMillis();
	}
	public void calcTurnaroundTime() {
		// TODO Auto-generated method stub
		turnaroundTime = completionTime - arrivalTime;
		totalTurnaround = totalTurnaround + turnaroundTime;
	}
	public void calcResponseTime() {
		// TODO Auto-generated method stub
		responseTime = startTime - arrivalTime;
		totalResponse = totalResponse + responseTime;
	}
	
	/*
	 * statistics methods
	 * just prints output for us to use to gather data
	 */
	public void individualStats() {
		System.out.println("\n---------------------------------------------------------------");
		System.out.println("task " + this.name + ":\nResponse Time: " + responseTime + " mS.\nTurnaround Time: " + turnaroundTime + " mS.");
		System.out.println("---------------------------------------------------------------");
		
	}
	public void totalStats() {
		System.out.println("\n---------------------------------------------------------------");
		System.out.println("\nTask stats:");
		System.out.println("Total Tasks: " + count);
//		System.out.println("task_" + this.name + ":\nResponse Time: " + responseTime);
		System.out.println("Average Response Time: " + (totalResponse / count) + " mS.");
		System.out.println("Average Turnaround Time: " + (totalTurnaround / count) + " mS.");
		System.out.println("Average Duration Per Task: " + (totalLength / count) + " mS.");
		System.out.println("---------------------------------------------------------------\n");
	}
	
}
