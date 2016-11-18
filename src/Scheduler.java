import java.util.*;


public class Scheduler {
	static ArrayList<Task> queue = new ArrayList<Task>();
	
	public Scheduler(int num_of_tasks) {
		createTasks(num_of_tasks);		
	}
	
	private static void createTasks(int quantity) {
		
		for (int i = 0; i < quantity; i++) {
			int random = rng();
			Task task = new Task(random, quantity, i);
			System.out.println("new task created: " + task.getDuration());
//			fifo(task);
			sjf(task);

		}
	}
	
	private static int rng() {
		Random rand = new Random();
		int randomNumber = rand.nextInt(10000) + 1;
		return randomNumber;
	}
	
	private static void fifo(Task task) {
		queue.add(task);
	}
	
	private static void sjf(Task task) { //shortest job first
		if ( queue.isEmpty() ) {
			System.out.println("adding " + task.getDuration() + " to the queue at position 0" );
			queue.add(task);
			System.out.println("");
		} else { //not empty
			insert(task);
		}

	}
	
	private static void insert(Task task) {
		System.out.println("Scanning...");
		for (int i = 0; i < queue.size(); i++) {
			System.out.println("Check index: " + i);
			System.out.println("Value: " + queue.get(i).getDuration());
			if ( task.getDuration() <= queue.get(i).getDuration() ) {
				System.out.println("adding " + task.getDuration() + " to the queue at position " + i);
				queue.add(i, task);
				break;
			} else {
				System.out.println("rev insert");
				reverseInsert(task);
				break;
			} 

		}
		System.out.println("");
	}
	private static void reverseInsert(Task task) {
		// TODO Auto-generated method stub
		for (int i = queue.size(); i >= 0; i--) {
			if (task.getDuration() > queue.get(i-1).getDuration() ) {
				System.out.println("adding " + task.getDuration() + " to the queue at position " + i);
				queue.add(i, task);
				break;
			}
		}
		
	}

	public void printQueue() {
		System.out.println("End of Sorting\n");
		System.out.println("\nQueue:\n");
		for (int i = 0; i < queue.size(); i++) {
			System.out.print(queue.get(i).getDuration() + " ");
		}
		System.out.println("\n");
	}

	public synchronized Task getTask() {
		if (!queue.isEmpty()) {
			Task tempTask = queue.get(0);
			queue.get(0).setStartStime();
			queue.get(0).calcResponseTime();
			queue.remove(0);
			return tempTask;
		}
		
		return null;
		
		
		
	}

	public boolean empty() {
		// TODO Auto-generated method stub
		return queue.isEmpty();
	}
}
