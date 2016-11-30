import java.util.*;


public class Scheduler {
	static ArrayList<Task> queue = new ArrayList<Task>();
	//No selection: 0
	//fifo: 1
	//sjf: 2
	//stcf: 3
	static int schedSelection = 0;
	final static int TIMESLICE = 1500;
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
//			sjf(task);
			stcf(task);

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
	public static void fifo(Task task) {
		schedSelection = 1;
		queue.add(task);
	}
	/*
	 * shortest job first scheduler
	 */
	public static void sjf(Task task) { //shortest job first
		schedSelection = 2;

		if ( queue.isEmpty() ) {
			System.out.println("adding " + task.getDuration() + " to the queue at position 0" );
			queue.add(task);
			System.out.println("");
		} else { //not empty
			insert(task);
		}

	}
	
	public synchronized static void stcf(Task task) {
		schedSelection = 3;

		if ( queue.isEmpty() ) {
			System.out.println("adding " + task.getDuration() + " to the queue at position 0" );
			queue.add(task);
		} else {
			insert(task);
		}
	}
	
	
	/*
	 * inserts the task into the 'queue' based on the duration of the queue
	 */
	public synchronized static void insert(Task task) {
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
//				reverseInsert(task);
				for (int j = queue.size(); j >= 0; j--) {
					if (task.getDuration() > queue.get(j-1).getDuration() ) {
						System.out.println("adding " + task.getDuration() + " to the queue at position " + j);
						queue.add(j, task);
						break;
					}
				}
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
	private synchronized static void reverseInsert(Task task) {
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
			if (tempTask.startTime == 0) {
				queue.get(0).setStartStime();
				queue.get(0).calcResponseTime();
			}

			if (schedSelection == 1 || schedSelection == 2) {
				queue.remove(0);
				return tempTask;
				
			} else if (schedSelection == 3) {
				queue.remove(0);
				System.out.println("Duration: " + tempTask.duration + "\nTimeslice: " + TIMESLICE);
				tempTask.duration = tempTask.duration - TIMESLICE;
				return tempTask;
//				if (tempTask.duration > 0) {
//					System.out.println("re-adding " + tempTask.getDuration());
//					insert(tempTask);
//					return tempTask;
//				} else if (tempTask.duration <= 0) {
//					System.out.println("About to crash...");
//				}
				
			}
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
