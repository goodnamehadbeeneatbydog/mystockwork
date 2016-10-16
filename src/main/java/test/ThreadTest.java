package test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ThreadTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		ExecutorService singleThreadPool = Executors.newSingleThreadExecutor(); 
		
		 Future f =singleThreadPool.submit(new Runnable() {
			
			@Override
			public void run() {
				int i=0;
				while(true){
					System.out.println(Thread.currentThread().getName()+":"+(i++));
						try {
							Thread.sleep(1000);
							if(i==10)
								break;
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					
				}
				
			}
		});
		singleThreadPool.shutdown();
			System.out.println("end" );
	}

}
