public class BHANDARI_BIDHAN_PROCESS {
	private int PID;
	private int timeStamp;
	private int CPUBurst;
	private int priority;
	private int completionTime;
	private int TAT;
	private int waitTime;
	
	public BHANDARI_BIDHAN_PROCESS(int PID, int timeStamp, int CPUBurst){
		this.PID = PID;
		this.timeStamp = timeStamp;
		this.CPUBurst = CPUBurst;
	}
	public BHANDARI_BIDHAN_PROCESS(int PID, int timeStamp, int CPUBurst, int priority){
		this.PID = PID;
		this.timeStamp = timeStamp;
		this.CPUBurst = CPUBurst;
		this.priority = priority;
	}
	public int getPID(){
		return PID;
	}
	public int getTimeStamp(){
		return timeStamp;
	}
	public int getCPUBurst(){
		return CPUBurst;
	}
	public int getPriority(){
		return priority;
	}
	public void setCompletionTime(int cT){
		completionTime = cT;
	}
	public int getCompletionTime(){
		return completionTime;
	}
	public int getTAT(){
		return TAT;
	}
	public void setTAT(int tat){
		TAT = tat;
	}
	public int getWaitTime(){
		return waitTime;
	}
	public void setWaitTime(int wt){
		waitTime = wt;
	}
}