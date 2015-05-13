import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Priority Queue by Zach Eddy implemented as a Heap
 * Quick note: heap starts at index 1. I finished the homework before it was suggested to use index 0.
 */
public class PriorityQueue<T>
{
	public Object[] nodeList; //array to hold the nodes added to the priority queue
	public int[] score; //score associated with each node inserted into the priority queue
	private int capacity, currentSize, step, resizes; //track the number of resizes, maximum size, and current size 

	/**
	 * Main constructor for the priority queue
	 * @param starting size for the priority queue. Additionally, the size of the queue will never go below this number.
	 * @param step size for increasing and decreasing the size of the array.
	 */
	public PriorityQueue(int initialSize, int stepSize)
	{
		currentSize = 0; //starts out as empty.
		capacity = initialSize;
		step = stepSize;
		nodeList = new Object[initialSize];
		score = new int[initialSize];
	}
	
	/**
	 * Constructor for Priority Queue that takes no arguments and defaults the starting size to 30 and step to 10. 
	 */
	public PriorityQueue()
	{
		this(30,10);
	}
	
	/**
	 * This method adds an element to the priority queue
	 * @param node to be added
	 * @param score associated with node
	 */
	public void insert(T node, int nodeScore)
	{
		//add to the bottom of the heap, then have the node swim as far up as necessary.
		resize();
		currentSize++;
		nodeList[currentSize] = node; //add to end of array then move nodes around.
		score[currentSize] = nodeScore;
		swim(currentSize); 
	}

	/**
	 * This method removes the top element from the priority queue.
	 * @return top element of the priority queue.
	 */
	public T remove()
	{
		//remove top, then move last element to top to reheapify using sink().
		Object top = nodeList[1];
		swap(1,currentSize); //move last element to top.
		nodeList[currentSize] = null;
		score[currentSize] = 0;	
		currentSize--;
		resize(); 
		sink(1); //reheapify.
		return (T)top;
	}

	/**
	 * This method returns the current size of the priority queue.
	 * @return the size of the queue.
	 */
	private int size()
	{
		return currentSize;
	}

	/**
	 * When given two nodes, this method determines which is larger.
	 * @param first node.
	 * @param second node.
	 * @return the difference between the score of the two nodes.
	 */
	private boolean less(int firstNode, int secondNode)
	{	
		return (score[firstNode] < score[secondNode]);
	}

	/**
	 * This method moves a node upward (heapify) in the heap until its parent is larger.
	 * @param the position of the node in question.
	 */
	private void swim(int currentPosition)
	{
		//continue while the node isn't at the top and its parent has a smaller score. 
		while(currentPosition > 1 && less(currentPosition/2, currentPosition))
		{
			swap(currentPosition/2, currentPosition); //swap the nodes assuming the parent is smaller.
			currentPosition = currentPosition / 2; //adjust the node position.
		}
	}

	/**
	 * This method moves a node downward in the heap until its child is smaller. 
	 * @param starting position of the node that will sink down the heap.
	 */
	private void sink(int currentPosition)
	{
		//keep going assuming there are still nodes further down the tree.
		while(2 * currentPosition <= currentSize) 
		{
			int childPosition = 2 * currentPosition; //find the child's position. 
			if(childPosition < currentSize && less(childPosition, childPosition+1))  //compare to the left child.
				childPosition++;
			if(!less(currentPosition, childPosition)) //compare to the right child.
				return; //if the parent is larger, halt the looping process.
			swap(currentPosition, childPosition);
			currentPosition = childPosition;
		}
	}
	
	/**
	 * this method swaps two nodes in the heap.
	 * @param first node to swap.
	 * @param second node to swap.
	 */
	private void swap(int nodeOne, int nodeTwo)
	{
		//first swap the nodes themselves.
		Object tempNode = nodeList[nodeOne];
		nodeList[nodeOne] = nodeList[nodeTwo];
		nodeList[nodeTwo] = tempNode;

		//then swap the corresponding scores.
		int tempScore = score[nodeOne];
		score[nodeOne] = score[nodeTwo];
		score[nodeTwo] = tempScore;
	}

	/**
	 * This method checks to see if the heap needs to be resized.
	 */
	private void resize()
	{
		expandList();
		shrinkList();
	}
	
	/**
	 * This method checks to see if the heap needs to be expanded
	 * True when capacity is equal to the current size of the heap
	 */
	private void expandList()
	{
		int length = nodeList.length;
		if(currentSize == (length - 1))
		{
			//make two larger arrays for the objects and their scores 
			Object[] expandNodeList = new Object[length + step];
			int[] expandScore = new int[length + step];
			for(int i = 0; i < length; i++) //add the existing nodes to the new and expanded array
			{
				expandScore[i] = score[i];
				expandNodeList[i] = nodeList[i];
			}
			score = expandScore;
			nodeList = expandNodeList;
			resizes++;
		}
	}
	
	/**
	 * This method checks to see if the heap needs to be shrunk
	 * True when the current size == (capacity - 2 * step) 
	 */
	private void shrinkList()
	{
		int length = nodeList.length;
		if(currentSize < (length - 2*step))
		{
			if(capacity > (currentSize - 2*step)) //ensure it never gets smaller than the initial size
				return;
			//make two smaller arrays for the objects and their scores
			Object[] shrinkNodeList = new Object[(length - 2*step)];
			int[] shrinkScore = new int[(length - 2*step)];
			for(int i = 0; i < currentSize; i++) //add the existing nodes to the new and shrunk array
			{
				shrinkNodeList[i] = nodeList[i];
				shrinkScore[i] = score[i];
			}
			nodeList = shrinkNodeList;
			score = shrinkScore;
			resizes++;
		}
	}

	/**
	 * This method returns the number of resizes
	 * @return the number of resizes
	 */
	public int getNumResizes()
	{
		return resizes;
	}
	
	/**
	 * Main method to test the priority queue
	 * @param terminal arguments
	 */
	public static void main(String[] args)
	{	
		PriorityQueue<String> playerQueue; //declare a new priority queue
		if(args.length == 0) //case for no arguments
		{
			System.out.println("\nYou must input more than one argument.\n");
			return;
		}
		if(args.length == 2) //case for two arguments
		{
			System.out.println("\nYou must enter a '.txt' file followed by zero or two integers.\n");
			return;
		}
		if(args.length > 3) //case for too many arguments
		{
			System.out.println("\nToo many arguments given.\n");
			return;
		}
		else if(args.length == 3) //case for .txt followed by initial size and step params
		{
			try
			{
				//read the arguments from the command line
				int arg1 = Integer.parseInt(args[1]);
				int arg2 = Integer.parseInt(args[2]);
				playerQueue = new PriorityQueue<String>(arg1,arg2);
			}
			catch(NumberFormatException ne) //output an error message if the user doesn't enter integer arguments
			{
				System.out.println("\nArguments 2 and 3 must be integer values\n");
				return;
			}
		}
		else //otherwise just create the default queue (only a .txt file provided)
		{
			playerQueue = new PriorityQueue<String>();
		}
		
		String filename = args[0];
		try
		{
			System.out.println("\nCreating a priority queue with initial size " + playerQueue.capacity + " and a step of " + playerQueue.step + "...\n");
			Scanner scanner = new Scanner(new File(filename));
			while(scanner.hasNext()) //continue while there are still players to be added
			{
				String event = scanner.nextLine();
				if(event.equals("GO!")) //case when a player enters the game
				{
					if(playerQueue.size() == 0)
					{
						System.out.println("No one is ready!");
						continue;
					}
					String player = playerQueue.remove();
					System.out.println(player + " enters the game.");
				}
				else //otherwise a player is added to the bench
				{
					try
					{
						String[] player = event.split("/"); //generates an array as [player's name, player's score]
						playerQueue.insert(player[0], Integer.parseInt(player[1]));
					}
					catch(Exception e) //handle the error when the .txt file doesn't match the parsing pattern
					{
						System.out.println("\n**Error during parsing: file does not match expected pattern**\n");
						return;
					}
				}
			}
			System.out.println("\nAt the end, there were " + playerQueue.currentSize + " players left.");
			System.out.println("The array was resized " + playerQueue.getNumResizes() + " times.\n");
		}
		catch(FileNotFoundException fe) //bad file name
		{
			System.out.print("File '" + filename + "' not found.");
		}
	}		
}