import java.util.Queue;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

//Below is the Customer Thread
class Customer extends Thread
{

    ReentrantLock  Lock;
    Condition cond;
    Queue<Integer> queue;
    Random rd = new Random();
    double mean_TimeC,standard_DeviationC;
    int round,interval;
    double time;
    int size;
    int id =0;
    
    //Below constructor calling idea has been taken from stack overflow.used in producer - consumer problem.link is available in report
    public Customer(ReentrantLock Lock, Condition cond, Queue<Integer> queue, int size,double mean_TimeC,double standard_DeviationC) //parameterized constructor is created
    {
        this.Lock = Lock;
        this.cond = cond;
        this.queue = queue;
        this.size=size;
        this.mean_TimeC = mean_TimeC;
        this.standard_DeviationC = standard_DeviationC;
    }
    //up to here 
    public void run()                         //run method of class thread
    {
    	
        while(true)                         // Infinite loop
        {
            Lock.lock();                   // locks are used to provide synchronization to the code below.
            if(queue.size()==size)     // only execute when size of queue is full.
            {
                try 
                {
                	id++;
                	Thread.sleep(2500);
                	System.out.println("Queue is full");
                	time = rd.nextGaussian()*standard_DeviationC + mean_TimeC;                //Random time is generated which is normally distributed.
                    round = (int)Math.round(time);
                    interval = Math.abs(round*100)+100;
                	System.out.println("Customer @"+id+" arrive after "+interval+" seconds and left ");
                	System.out.println("waiting queue :"+queue);
                	System.out.println();
                	cond.signalAll();
                	cond.await();                                      //Enter into waiting state
                } catch (InterruptedException ex) {
                    //Logger.getLogger(Customer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else 
            {
                id++;
                queue.add(id);
                time = rd.nextGaussian()*standard_DeviationC + mean_TimeC;                        //Random time is generated which is normally distributed.
                round = (int)Math.round(time);
                interval = Math.abs(round*100)+100;
                try {
				     Thread.sleep(2500);
				     System.out.println("Customer  @"+id+" arrive after "+interval+" seconds and is sleeping in queue");
				     System.out.println("Waiting queue "+queue);
				     System.out.println();
				     Thread.sleep(2500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			   }
            }
            Lock.unlock();
        }
    }
}
