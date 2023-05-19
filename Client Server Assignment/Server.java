import java.io.*;
import java.net.*;
import java.util.ArrayList;

class Server {

	public static void main(String args[]) {
		try {

			// Create server Socket that listens/bonds to port/endpoint address 6666 (any port id of your choice, should be >=1024, as other port addresses are reserved for system use)
			// The default maximum number of queued incoming connections is 50 (the maximum number of clients to connect to this server)
			// There is another constructor that can be used to specify the maximum number of connections
			ServerSocket mySocket = new ServerSocket(6666);

			//The input value list
			ArrayList<Integer> inputValues = new ArrayList<Integer>();

			//The integer the client inputs
			int intData;


			System.out.println("Startup the server side over port 6666 ....");

			// use the created ServerSocket and accept() to start listening for incoming client requests targeting this server and this port
			// accept() blocks the current thread (server application) waiting until a connection is requested by a client.
			// the created connection with a client is represented by the returned Socket object.
			Socket connectedClient = mySocket.accept();


			// reaching this point means that a client established a connection with your server and this particular port.
			System.out.println("Connection established");


			// to interact (read incoming data / send data) with the connected client, we need to create the following:

			// BufferReader object to read data coming from the client
			BufferedReader br = new BufferedReader(new InputStreamReader(connectedClient.getInputStream()));

			// PrintStream object to send data to the connected client
			PrintStream ps = new PrintStream(connectedClient.getOutputStream());


			// Let's keep reading data from the client, as long as the client does't send "exit".
			String inputData;
			String temp;

			while (!(inputData = br.readLine()).equals("exit")) {    

				System.out.println("received a message from client: " + inputData);   //print the incoming data from the client

				//ps.println("Here is an acknowledgement from the server");              //respond back to the client

				temp = inputData;
				int sum;
				int max;
				int min;

				if(inputData.charAt(0) == 'A' | inputData.charAt(0) == 'R')
				{
					temp = inputData.substring(0, 3);
				}

				//System.out.println(temp);

				switch(temp) 
				{
				case "Add":
					intData = getInt(inputData); 					// Gets the integer from String input
					inputValues.add(intData);
					ps.println("Added successfully");
					System.out.println("Added successfully");
					System.out.println(inputValues);
					break;

				case "Rem":
					intData = getInt(inputData);					// Gets the integer from String input

					if(isNuminList(intData, inputValues) == true)	// Checks if integer is in inputValues array list
					{
						ArrayList<Integer> removeThis = new ArrayList<Integer>(); // New ArrayList to remove all instances of integer

						removeThis.add(intData);						// Adding desired integer to remove

						inputValues.removeAll(removeThis);			// Removes the desired integer from inputValues array list

						ps.println("Removed successfully");
						System.out.println("Removed successfully");
					}
					else
					{
						ps.println(intData + " is not in list.");
					}
					System.out.println(inputValues);
					break;

				case "Get_Summation":
					sum = summation(inputValues);

					if(inputValues.size() == 0 | sum == 0)
					{
						ps.println("The summation is null");
					}
					else {
						ps.println("The summation is " + sum);
						System.out.println(sum);
					}
					break;

				case "Get_Minimum":  
					min = findMin(inputValues);
					sum = summation(inputValues);

					if(inputValues.size() == 0 | sum == 0)
					{
						ps.println("The minimum is null.");
					}
					else {
						ps.println("The minimum is " + min);
						System.out.println(min);
					}
					break;

				case "Get_Maximum": 
					max = findMax(inputValues);
					sum = summation(inputValues);

					if(inputValues.size() == 0 | sum == 0)
					{
						ps.println("The maximum is null.");
					}
					else {
						ps.println("The maximum is " + max);	
						System.out.println(max);
					}
					break;
					//default:
					// Error
				}

			}

			System.out.println("Closing the connection and the sockets");

			// close the input/output streams and the created client/server sockets
			ps.close();
			br.close();
			mySocket.close();
			connectedClient.close();

		} catch (Exception exc) {
			System.out.println("Error :" + exc.toString());
		}

	}

	// Method that generates String with only integer, and turns it into an integer value
	// Returns integer 
	public static int getInt(String s)
	{
		String temp;
		int output;

		temp = s.replaceAll("[^0-9]", " ");	//Removes all characters, leaving integers

		temp = temp.replaceAll(" +", "");	//Removes all the spaces

		output = Integer.parseInt(temp);	//Converts string from inputData into an integer type

		return output;
	}

	//Method that sums all integers in the input values array list
	// Returns the sum of input values or 0
	public static int summation(ArrayList<Integer> a)
	{
		int sum = 0;

		if(a.size() > 0)
		{
			for(int i = 0; i < a.size(); i++)
			{
				sum = sum + a.get(i);
			}
		}  

		return sum;
	}

	// Method that finds the maximum integer in the input values array list
	// Returns maximum integer or 0
	public static int findMax(ArrayList<Integer> a)
	{
		int max = 0;

		if(a.size() > 0)
		{
			max = a.get(0);

			for(int i = 0; i < a.size(); i++)
			{
				if(max < a.get(i))
				{
					max = a.get(i);
				}
			}
		}

		return max;
	}

	// Method that finds the minimum integer in the input values array list
	// REturns minimum integer or 0
	public static int findMin(ArrayList<Integer> a)
	{
		int min = 0;

		if(a.size() > 0)
		{
			min = a.get(0);

			for(int i = 0; i < a.size(); i++)
			{
				if(min > a.get(i))
				{
					min = a.get(i);
				}
			} 
		}

		return min;	
	}

	public static boolean isNuminList(int a, ArrayList<Integer> l)
	{
		boolean numIsIn = false;

		for(int i = 0; i < l.size(); i++)
		{
			if(l.get(i) == a)
			{
				numIsIn = true;
			}
		}

		return numIsIn;
	}

}