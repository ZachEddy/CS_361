import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public abstract class UnionFinder
{
	protected int componentCount; //number of total components in the graph
	protected int[] nodeId; //unique identifier given to each node in the graph

	/*
	* @param nodes - the number of nodes in the graph
	*/
	public UnionFinder(int nodes)
	{
		componentCount = nodes;
		nodeId = new int[nodes];
		for(int i = 0; i < nodes; i++) //go through nodeId and assign each node its own unique identifier incrementally
			nodeId[i] = i;   
	}
	
	/*
	* This method connects two nodes when given their identifiers.
	* @param p - specific identifier of a node
	* @param q - specific identifier of a different node
	*/
	public abstract void unite(int p, int q);

	/*
	* @param p - find the component that a node is currently connected to
	* @return - the integer that identifies which component a node is connected to
	*/
	public abstract int find(int p);
	
	/*
	* This method determines whether two nodes in a graph are connected
	* @param p - identifier for the first node
	* @param q - identifier for the second node
	*/
	public boolean isConnected(int p, int q) 
	{
		return find(p) == find(q); //if they have the same component identifier, they're connected
	}
	
	/*
	* This method determines how many components exist in a graph
	* @return - the number of components in a graph, as defined using componentCount
	*/
	public int countComponents()
	{
		return componentCount;
	}
	
	/*
	* main method to run UnionFinder
	* @param args - command-line arguments
	*/
	public static void main(String[] args)
	{
		UnionFinder finder;
		String operation = args[0];
		String filename = args[1];
		
		try //assuming the filename exists and has been typed correctly
		{
			Scanner scanner = new Scanner(new File(filename)); //create a scanner to traverse the file
			//create the specific type of UnionFinder based on the command-line input
			if(operation.equals("F"))
			{
				finder = new QFUnionFinder(scanner.nextInt()); 
			}
			else if(operation.equals("U"))
			{
				finder = new QUUnionFinder(scanner.nextInt());
			}
			else if(operation.equals("W"))
			{
				finder = new WQUUnionFinder(scanner.nextInt());
			}
			else //make sure they actually entered a valid input
			{
				System.out.println("\nIncorrect letter input. 'F', 'U', and 'W' are all acceptable choices.\n");
				return;
			}
			
			System.out.println("\nSuccessfully created new UnionFinder \n");
			long time = System.currentTimeMillis(); //start tracking time
			
			while(scanner.hasNextInt()) //iterate through the entire text file
			{
				
				int p = scanner.nextInt();
				int q = scanner.nextInt();
				
				if(!finder.isConnected(p,q)) //if two nodes don't already have a connection, unite them
				{
					finder.unite(p,q);
					System.out.println(p + " " + q); //print out the new connection
				}
			}
			
			time = System.currentTimeMillis() - time; //stop tracking because the algorithm is complete
			System.out.println("\n"+ finder.componentCount + " components found in " + time + " milliseconds using " + finder.getClass().getSimpleName() + " with " + filename + "\n");
		}
		catch(FileNotFoundException fe) //if the file name doesn't exist (or nothing is entered), let the user know
		{
			if(filename.equals(""))
      {
      	System.out.println("No filename entered");
      }
			System.out.println("\nFile '" + filename + "' not found\n");
		}
	}
	
}
