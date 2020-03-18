import java.util.Queue;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

//Below is the Barber's Thread
class Barber extends Thread
{
    ReentrantLock Lock;
    Condition cond;
    Random rd = new Random();
    Queue<Integer> queue;
    double mean_TimeB,standard_DeviationB,time,round,interval;
    long a;
    int remove,next;

    //The below constructor calling idea can been taken from Stack overflow. link is available in report
    public Barber(ReentrantLock Lock, Condition cond, Queue<Integer> queue,double mean_TimeB, double standard_DeviationB)     //parameterized constructor is created
    {
        this.Lock = Lock;
        this.cond = cond;
        this.queue = queue;
        this.mean_TimeB = mean_TimeB;
        this.standard_DeviationB =standard_DeviationB;
    }
    // Unto here.
    public void run()
    { 
    	a = Thread.currentThread().getId();                            //The variable a will contain the unique id of the barber.
        while(true)                                                   //Infinite Loop
        {
           Lock.lock();                                              // Synchronized block. Each Thread can access it one at a time.
           while(queue.size()==0)                                   //when there is no customer this block of code will execute.
           {
        	   try {
               System.out.println("No one in shop");
			   System.out.println("Barber with id #"+a+" is sleeping in his chair");
			   System.out.println();
			   cond.await();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}                                    // Will stop and wait for other Thread to signal him.
			   
           }
            // When there are people waiting in queue this code will execute.
            this.remove = queue.remove();
            System.out.println("Barber with id #"+a+" wake customer @"+remove+" and is giving haircut to customer ");
            System.out.println("customer with id @"+remove+" is sleeping in barber's chair with id #"+a);
            System.out.println("Waiting queue :"+queue);
            System.out.println();
            try                                                     // Thread will sleep and notify all thread before going into the waiting state.
            {
				Thread.sleep(2500);
				cond.signalAll();
				cond.await();                                     // this will send the Thread to the waiting state and once signal it will begin execution from here.
				time = rd.nextGaussian()*standard_DeviationB + mean_TimeB;             //Random time is generated which is normally distributed.
		        round = (int)Math.round(time);
		        interval = Math.abs(round*100)+100;			
            }
            catch (InterruptedException e1) {
				e1.printStackTrace();
			}
            System.out.println("Barber with id #"+a+" is done with customer @"+remove+" after "+interval+" seconds and holding exit door for him");
            try {
				 Thread.sleep(2500);
			} 
            catch (InterruptedException e1) {
				e1.printStackTrace();
			}
            cond.signalAll();
			//cond.await();
			Lock.unlock();
          }
    }
}






