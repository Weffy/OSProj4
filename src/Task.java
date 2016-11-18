
public class Task {
	int duration;
	int name;
	static int count;
	
	static long totalTurnaround = 0;
	static long totalResponse = 0;
	
	static long totalLength = 0;
	
	long arrivalTime = 0;
	long startTime = 0;
	long completionTime = 0;
	
	long turnaroundTime = 0;
	long responseTime = 0;
	
	public Task(int duration, int count, int name) {
		this.name = name;
		this.duration = duration;
		this.count = count;
		totalLength = totalLength + duration;
		arrivalTime = System.currentTimeMillis();
	}
		
	public int getDuration() {
		return duration;
	}

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
	public void individualStats() {
		System.out.println("\n---------------------------------------------------------------");
		System.out.println("task " + this.name + ":\nResponse Time: " + responseTime + " mS.\nTurnaround Time: " + turnaroundTime + " mS.");
		System.out.println("---------------------------------------------------------------");
		
	}
	public void totalStats() {
		System.out.println("\n---------------------------------------------------------------");
		System.out.println("\nTask stats:");
		System.out.println("Total Tasks: " + count);
		System.out.println("task_" + this.name + ":\nResponse Time: " + responseTime);
		System.out.println("Average Response Time: " + (totalResponse / count) + " mS.");
		System.out.println("Average Turnaround Time: " + (totalTurnaround / count) + " mS.");
		System.out.println("Average Duration Per Task: " + (totalLength / count) + " mS.");
		System.out.println("---------------------------------------------------------------\n");
	}
	
}
