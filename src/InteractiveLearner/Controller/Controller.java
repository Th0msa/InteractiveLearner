package InteractiveLearner.Controller;

public class Controller implements Runnable{
	private Thread thread;
	private NaiveBayes bayes;
	private boolean running = false;
	
	public Controller() {
		this.thread = new Thread(this);
		thread.start();
	}
	
	@Override
	public void run() {
		while (running) {
			
		}
		this.stop();
	}
	
	public synchronized void start() {
        if(!running) {
            running = true;
            this.run();
        }
    }
	
	public synchronized void stop() {
        if(running) {
            running = false;
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.exit(1);
        }
    }
	
	public static void main(String[] args) {
		Controller controller = new Controller();
		controller.start();
	}
}
