package critereArret;

import modeles.Population;

public class ArretDuree implements ICritereArret, Runnable {
	
	private int mTime;
	private int mCurrentTime;

	public ArretDuree(int timeInSecond) {
		this.mTime = timeInSecond;
		this.mCurrentTime = 0;
		
		Thread t = new Thread(this);
		t.start();
	}
	
	@Override
	public boolean algorithmShouldStop(Population pop) {
		if(this.mCurrentTime >= this.mTime) return true;
		return false;
	}

	@Override
	public void run() {
		while(!algorithmShouldStop(null)) {
			try {
				Thread.sleep(1000);
				this.mCurrentTime += 1;
				System.out.println("Temps restants : " + (this.mTime - this.mCurrentTime));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
