import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
public class BarberShop
{
    public static void main(String[] args) 
    {
        // code application logic here
        Scanner sc = new Scanner(System.in);                  //object's are created
        Queue<Integer> queue=new LinkedList<Integer>();
        ReentrantLock Lock=new ReentrantLock(true);
        Condition cond=Lock.newCondition();
        System.out.println("Enter the number of Barber in shop");  // input from the user are collected
        int number = sc.nextInt();
        System.out.println("Enter the mean time taken to cut customer hair in seconds");
        double mean_TimeB = sc.nextDouble();
        System.out.println("Enter the standard deviation of the time taken to cut customer hair");
        double standard_DeviationB = sc.nextDouble();
        System.out.println("Enter the size of the waiting queue");
        int size = sc.nextInt();
        System.out.println("Enter the mean arrival time of customer in seconds");
        double mean_TimeC = sc.nextDouble();
        System.out.println("Enter the standard deviation of the arrival time for the customer in seconds");
        double standard_DeviationC = sc.nextDouble();
        ExecutorService service = Executors.newFixedThreadPool(number+1);
        for(int i = 0;i <= number; i++)
        {
           if(i==0)
           { 
        	   service.submit(new Customer(Lock, cond, queue, size,mean_TimeC,standard_DeviationC));
               //Starting the customer Thread. only one Thread. All the parameter all passed and collected by the constructor
           }
           else
           {
        	   service.submit(new Barber(Lock, cond, queue,mean_TimeB,standard_DeviationB));
               //Starting the barber thread. number of thread will depend on the number of input from user's
           }
           
        }
       
     }
}
