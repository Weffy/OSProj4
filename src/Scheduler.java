import java.util.*;


public class Scheduler {
	static ArrayList<Task> queue = new ArrayList<Task>();
	
	/*
	 * I suppose I could've just put all of the contents of createTasks in the constructor here
	 * could go back and clean up by doing so...
	 */
	public Scheduler(int num_of_tasks) {
		createTasks(num_of_tasks);		
	}
	/*
	 * creates a task object with a random duration
	 * selection of schedulers are done here.
	 * I don't like how I had to handle the task constructor here
	 * more info in task class.
	 */
	private static void createTasks(int quantity) {
		
		for (int i = 0; i < quantity; i++) {
			int random = rng();
			Task task = new Task(random, quantity, i);
			System.out.println("new task created: " + task.getDuration());
//			fifo(task);
			sjf(task);

		}
	}
	/*
	 * random number generator
	 */
	private static int rng() {
		Random rand = new Random();
		int randomNumber = rand.nextInt(10000) + 1;
		return randomNumber;
	}
	
	/*
	 * first in, first out scheduler
	 */
	private static void fifo(Task task) {
		queue.add(task);
	}
	/*
	 * shortest job first scheduler
	 */
	private static void sjf(Task task) { //shortest job first
		if ( queue.isEmpty() ) {
			System.out.println("adding " + task.getDuration() + " to the queue at position 0" );
			queue.add(task);
			System.out.println("");
		} else { //not empty
			insert(task);
		}

	}
	/*
	 * inserts the task into the 'queue' based on the duration of the queue
	 */
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
	/*
	 * ran into a problem when a number was greater than in the arraylist.
	 * you would need to check if the number we want to insert is greater than the current index
	 * but less than the next index
	 * however, this lead to out of boudns errors
	 * just implemented a new method to start from the back of the list and check in reverse
	 */
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
/*
 * prints the queue
 */
	public void printQueue() {
		System.out.println("End of Sorting\n");
		System.out.println("\nQueue:\n");
		for (int i = 0; i < queue.size(); i++) {
			System.out.print(queue.get(i).getDuration() + " ");
		}
		System.out.println("\n");
	}

	/*
	 * retrieval and removal of tasks are done atomically
	 * synchronized flag will only let one thread into this method at a time
	 */
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
/*
 * checks if the queue is empty
 */
	public boolean empty() {
		// TODO Auto-generated method stub
		return queue.isEmpty();
	}
}
