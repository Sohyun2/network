package thread;

public class MultiThreadEx01 {
	
	public static void main(String[] args) {
		Thread thread = new DigitThread();
		thread.start();
		
		for(char c='a'; c<='z'; c++) {
			System.out.println(c);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
