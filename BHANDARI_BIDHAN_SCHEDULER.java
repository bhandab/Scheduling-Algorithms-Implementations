import java.util.LinkedList;


public class BHANDARI_BIDHAN_SCHEDULER {
private LinkedList<BHANDARI_BIDHAN_PROCESS> readyQueue;

	
	public BHANDARI_BIDHAN_SCHEDULER(){
		//constructor of class
		//when super() is called from children classes, a new CLL of Processes is created
		readyQueue = new LinkedList<BHANDARI_BIDHAN_PROCESS>();
	}
	
	public void addProcess(BHANDARI_BIDHAN_PROCESS p){
		readyQueue.add(p); //add a process to the ready queue
	}
	
	public BHANDARI_BIDHAN_PROCESS removeProcess(int p){
		return readyQueue.remove(p); //remove a process from the ready queue
	}
	
	public BHANDARI_BIDHAN_PROCESS getProcess(int p){
		return readyQueue.get(p); //get a process from a specific position in the ready queue
	}
	
	public boolean readyQueueEmpty(){
		return readyQueue.isEmpty(); //check if the ready queue is empty
	}
	
	public int readyQueueSize(){
		return readyQueue.size(); //get the size of the ready queue
	}
	
	/*public void rotateReadyQueue(){
		readyQueue.rotate(); //rotate the process at the from of the ready queue to the back of the ready queue (RR)
	}*/
	
	public int getShortestProcessIndex(BHANDARI_BIDHAN_PROCESS p){
		return readyQueue.indexOf(p); //get the position of a process in the ready queue (sjnnp)
	}
	public int getHighestPriorityProcessIndex(BHANDARI_BIDHAN_PROCESS p){
		return readyQueue.indexOf(p); //get the position of a process in the ready queue (pnp)
	}
}
