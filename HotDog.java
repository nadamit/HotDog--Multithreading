/*
*@Author: Amit Nadkarni
*@version: v1.0
*/
import java.util.*;

public class HotDog extends Thread {
	//For identification
	String name;
	//Contains the items placed on the table
	static List<String> table = new ArrayList<String>();
	//Item with Hot dog maker in order to complete a hot dog.
	String whatIhave;
	//Manufacturer will produce 2 items from the stock array.
	String stock[] = { "Sausage", "Mustard", "Bun" };
	//Hot Dog Constructor
	HotDog(String name, String whatIhave) {
		this.name = name;
		this.whatIhave = whatIhave;
	}
	//Method to Notify threads in the wait state	
	public void v() {
		table.notifyAll();
	}
	//Forces thread to go in the wait state, releasing the lock help by the thread
	public void p() {
		try {
			table.wait();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void run() {

		while (true) {
			//Providing access to only one thread at a time in a critical section.
			synchronized (table) {
				//If the incoming thread is Manufacturer
				if (this.name == "Manufacturer") {
					//If the table is not empty then manufacturer will wait untill all 
					// items are consumed
					if (!table.isEmpty()) {
						this.p();
					} else {
						if (table.size() == 0) {
							Random r = new Random();
							int res = Math.abs(r.nextInt() % 3);
							//Randomly generates item from the stock
							String item1 = stock[res];
							//Randomly generates item from the stock
							int res2 = Math.abs(r.nextInt() % 3);
							while (res2 == res) {
								res2 = Math.abs(r.nextInt() % 3);
							}
							String item2 = stock[res2];
							System.out.println("Producer produces  " + item1
									+ " " + item2);
							//Adds the items in the table
							table.add(item1);
							table.add(item2);
							//Manufacturer notifies all consumers waiting for resources
							this.v();
						}
					}

				}
					else {
						//If the thread is the consumer thread
					if (!table.isEmpty()) {
				
						if (table.contains(this.whatIhave)) {
							this.p();
						}

						else {
						//Will Consume the items placed on the table			
							System.out.println("Consumer:" + this.name
									+ "consumes");
							table.clear();
							this.v();
						//Will notify the manufacturer to produce resources
						}
					}

					else {
						//Wait
						this.p();
					}

				}
			}
		}

	}
	
/*
*Function Name: Main
*Parameters: args
*return value: null
*Description: Producer produces any two random items(Bun,Mustard,Sausage) and places on the table
*		Consumer who is waiting for the items to complete hotdog take those items from table,
*		where as in rest of the consumer go in the wait state.
*/
		public static void main(String args[]) {
		/*Creating the manufacturers thread*/
		HotDog obj1 = new HotDog("Manufacturer", null);
		/*Creating HotDog makers thread*/
		HotDog maker1 = new HotDog("Maker1", "Sausage");
		HotDog maker2 = new HotDog("Maker2", "Mustard");
		HotDog maker3 = new HotDog("Maker3", "Bun");
		/*Starting the manufacturer and hotdog maker threads*/		
		obj1.start();
		maker1.start();
		maker2.start();
		maker3.start();

	}

}

