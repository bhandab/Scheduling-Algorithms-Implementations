import java.util.*;
import java.io.*;
public class BHANDARI_BIDHAN_ALGORITHMS extends BHANDARI_BIDHAN_SCHEDULER{
	private int sysCount;
	//private int idleCount;
	private PrintWriter writer;
	private String schedulingAlgorithm;
	LinkedList<BHANDARI_BIDHAN_PROCESS> jobsRemoved;
	
	public BHANDARI_BIDHAN_ALGORITHMS(String fileName, String algo){
		super();
		try{
		writer  = new PrintWriter(new File(fileName));
		}
		catch(FileNotFoundException e){
			System.out.println("FILE NOT FOUND!");
		}
		sysCount = 0;
		schedulingAlgorithm = algo;
		jobsRemoved = new LinkedList<BHANDARI_BIDHAN_PROCESS>();
		//idleCount = 0;
	}
	public void scheduleProcesses(ArrayList<BHANDARI_BIDHAN_PROCESS>jobs){
		double totalTAT = 0;
		double totalWT = 0;
		int processCount = jobs.size();
		writer.println("CPU scheduling Algortithm: " + schedulingAlgorithm);
		writer.println("Total Number of CPU requests: "+jobs.size());
		writer.println(formatDot());
		if(schedulingAlgorithm.equals("fcfs")){
			FCFS(jobs);
		}
		else if (schedulingAlgorithm.equals("sjnnp")){
			SJNNP(jobs);
		}
		else if (schedulingAlgorithm.equals("pnp")){
			PNP(jobs);
		}
		writer.println("Turn-Around Time Computations");
		writer.println(" ");
		for (int i = 0; i<processCount; i++){
			writer.println("TAT("+jobsRemoved.get(i).getPID()+") = "+jobsRemoved.get(i).getTAT());
			totalTAT+=jobsRemoved.get(i).getTAT();
			
		}
		writer.println(" ");
		writer.printf("Average TAT = %.1f", totalTAT/processCount);
		writer.println(" ");
		writer.println(formatDot());
		writer.println("Wait Time Computations");
		writer.println(" ");
		for (int i = 0; i<processCount; i++){
			writer.println("WT("+jobsRemoved.get(i).getPID()+") = "+jobsRemoved.get(i).getWaitTime());
			totalWT+=jobsRemoved.get(i).getWaitTime();
			
		}
		writer.println(" ");
		writer.printf("Average WT = %.1f", totalWT/processCount);
		writer.println(" ");
		writer.flush();
		writer.close();
	}
	public void FCFS(ArrayList<BHANDARI_BIDHAN_PROCESS> jobs){
		LinkedList<BHANDARI_BIDHAN_PROCESS> tempJobs = new LinkedList<BHANDARI_BIDHAN_PROCESS>();
		for (int i = 0; i<jobs.size(); i++){
			tempJobs.add(jobs.get(i));
		}
		int totalProcess = jobs.size();
		int job = 0;
		int burstCounter = 0;
		BHANDARI_BIDHAN_PROCESS currentProcess;
		
		while(jobs.get(job).getTimeStamp()!=sysCount){
			sysCount++;
			//idleCount++;
		}
		
		//Adds the first process that arrives to the ready queue
		super.addProcess(jobs.get(job));
		job++;//increment to the next job to be added in ready queue
		currentProcess = super.getProcess(0);
		
		writer.println("Clock: 0");
		writer.println("Total number of CPU request(s): ");
		for (int i = 0; i<totalProcess; i++){
			writer.println(jobs.get(i).getPID()+" "+jobs.get(i).getTimeStamp()+" "+ jobs.get(i).getCPUBurst());
		}
		//Runs until all process are completed
		while(true){
			
			//checks if ready queue is empty while there are still process remaining to be processed in job pool
			while(job!=totalProcess && super.readyQueueEmpty()){
				if (jobs.get(job).getTimeStamp()==sysCount){
					super.addProcess(jobs.get(job));
					job++;
					currentProcess = super.getProcess(0);
					break;
				}
				else{
					sysCount++;
					//idleCount++;
				}
			}
			
			//Adds processes that have same time stamp into ready queue
			while(job!=totalProcess && jobs.get(job).getTimeStamp()==sysCount){
				super.addProcess(jobs.get(job));
				job++;
			}
			
			
			//If Given CPU Burst is equal to the burst counter value, the process is done
			if(burstCounter==currentProcess.getCPUBurst()){
				currentProcess.setCompletionTime(sysCount);
				currentProcess.setTAT(currentProcess.getCompletionTime()-currentProcess.getTimeStamp());
				currentProcess.setWaitTime(currentProcess.getCompletionTime()-currentProcess.getTimeStamp()-currentProcess.getCPUBurst());
				
				BHANDARI_BIDHAN_PROCESS jobRemoved = super.removeProcess(0);//Removes process from the ready queue
				tempJobs.remove(jobRemoved);
				jobsRemoved.add(jobRemoved);
				writer.println(" ");
				writer.println("\nCPU Request Serviced during this clock interval: "+currentProcess.getPID()+" "+currentProcess.getTimeStamp()+" "+ currentProcess.getCPUBurst());
				writer.println(formatDot());
				if (tempJobs.size()!=0){
					
					writer.println("Clock: "+sysCount);
					writer.println("Pending CPU request(s):");
				}
				
				for (int i = 0; i<tempJobs.size(); i++){
					writer.println(tempJobs.get(i).getPID()+" "+tempJobs.get(i).getTimeStamp()+" "+ tempJobs.get(i).getCPUBurst());
				}
				burstCounter = 0;//set burst counter to 0 for another process
				
				//Check if all processes are finished
				if (super.readyQueueEmpty()&& job == totalProcess){
					break;
				}
				else if(!super.readyQueueEmpty()){
					currentProcess = super.getProcess(0);
				}
			}
			else if (burstCounter!=currentProcess.getCPUBurst()){
				burstCounter++;
				sysCount++;
			}
		}
	}
	public void SJNNP(ArrayList<BHANDARI_BIDHAN_PROCESS>jobs){
		LinkedList<BHANDARI_BIDHAN_PROCESS> tempJobs = new LinkedList<BHANDARI_BIDHAN_PROCESS>();
		
		for (int i = 0; i<jobs.size(); i++){
			tempJobs.add(jobs.get(i));
		}
		int totalProcess = jobs.size();
		int job = 0;
		int burstCounter = 0;
		BHANDARI_BIDHAN_PROCESS currentProcess;
		
		while(jobs.get(job).getTimeStamp()!=sysCount){
			sysCount++;
			//idleCount++;
		}
		
		//Adds the first process that arrives to the ready queue
		super.addProcess(jobs.get(job));
		job++;//increment to the next job to be added in ready queue
		currentProcess = super.getProcess(0);
		BHANDARI_BIDHAN_PROCESS shortestProcess = super.getProcess(0);
		int shortestProcessIndex = 0;
		
		writer.println("Clock: 0");
		writer.println("Total number of CPU request(s): ");
		for (int i = 0; i<totalProcess; i++){
			writer.println(jobs.get(i).getPID()+" "+jobs.get(i).getTimeStamp()+" "+ jobs.get(i).getCPUBurst());
		}
		while(true){
			//checks if ready queue is empty while there are still process remaining to be processed in job pool
			while(job!=totalProcess && super.readyQueueEmpty()){
				if (jobs.get(job).getTimeStamp()==sysCount){
					super.addProcess(jobs.get(job));
					job++;
					currentProcess = super.getProcess(0);
					break;
				}
				else{
					sysCount++;
					//idleCount++;
				}
				
				}
			
			//Adds processes that have same time stamp into ready queue
			while(job!=totalProcess && jobs.get(job).getTimeStamp()==sysCount){
				super.addProcess(jobs.get(job));
				job++;

			}
			if (!super.readyQueueEmpty()){
				shortestProcess = currentProcess;
				shortestProcessIndex = 0;
			   for (int i = 0; i<super.readyQueueSize(); i++){
					if (super.getProcess(i).getCPUBurst()<shortestProcess.getCPUBurst()){
						shortestProcess = super.getProcess(i);
						shortestProcessIndex = super.getShortestProcessIndex(shortestProcess);
					}
				}	
			}
			if (burstCounter==shortestProcess.getCPUBurst()){
				shortestProcess.setCompletionTime(sysCount);
				shortestProcess.setTAT(shortestProcess.getCompletionTime()-shortestProcess.getTimeStamp());
				shortestProcess.setWaitTime(shortestProcess.getCompletionTime()-shortestProcess.getTimeStamp()-shortestProcess.getCPUBurst());
				BHANDARI_BIDHAN_PROCESS jobRemoved = super.removeProcess(shortestProcessIndex);
				tempJobs.remove(jobRemoved);
				jobsRemoved.add(jobRemoved);
				
				writer.println(" ");
				writer.println("\nCPU Request Serviced during this clock interval: "+shortestProcess.getPID()+" "+shortestProcess.getTimeStamp()+" "+ shortestProcess.getCPUBurst());
				writer.println(formatDot());
				if (tempJobs.size()!=0){
					
					writer.println("Clock: "+sysCount);
					writer.println("Pending CPU request(s):");
				}
				
				for (int i = 0; i<tempJobs.size(); i++){
					writer.println(tempJobs.get(i).getPID()+" "+tempJobs.get(i).getTimeStamp()+" "+ tempJobs.get(i).getCPUBurst());
				}
				burstCounter = 0;//set burst counter to 0 for another process

				//Check if all processes are finished
				if (super.readyQueueEmpty()&& job == totalProcess){
					break;
				}
				else if(!super.readyQueueEmpty()){
					currentProcess = super.getProcess(0);
				}

			}
			else if (burstCounter!=shortestProcess.getCPUBurst()){
				burstCounter++;
				sysCount++;
			}

		}
	}
	
	public void PNP(ArrayList<BHANDARI_BIDHAN_PROCESS>jobs){
		LinkedList<BHANDARI_BIDHAN_PROCESS> tempJobs = new LinkedList<BHANDARI_BIDHAN_PROCESS>();
		
		for (int i = 0; i<jobs.size(); i++){
			tempJobs.add(jobs.get(i));
		}
		int totalProcess = jobs.size();
		int job = 0;
		int burstCounter = 0;
		BHANDARI_BIDHAN_PROCESS currentProcess;
		
		while(jobs.get(job).getTimeStamp()!=sysCount){
			sysCount++;
			//idleCount++;
		}
		
		//Adds the first process that arrives to the ready queue
		super.addProcess(jobs.get(job));
		job++;//increment to the next job to be added in ready queue
		currentProcess = super.getProcess(0);
		BHANDARI_BIDHAN_PROCESS priorityProcess = super.getProcess(0);
		int priorityProcessIndex = 0;
		
		writer.println("Clock: 0");
		writer.println("Total number of CPU request(s): ");
		for (int i = 0; i<totalProcess; i++){
			writer.println(jobs.get(i).getPID()+" "+jobs.get(i).getTimeStamp()+" "+ jobs.get(i).getCPUBurst()+" "+jobs.get(i).getPriority());
		}
		while(true){
			//checks if ready queue is empty while there are still process remaining to be processed in job pool
			while(job!=totalProcess && super.readyQueueEmpty()){
				if (jobs.get(job).getTimeStamp()==sysCount){
					super.addProcess(jobs.get(job));
					job++;
					currentProcess = super.getProcess(0);
					break;
				}
				else{
					sysCount++;
					//idleCount++;
				}
				
				}
			
			//Adds processes that have same time stamp into ready queue
			while(job!=totalProcess && jobs.get(job).getTimeStamp()==sysCount){
				super.addProcess(jobs.get(job));
				job++;

			}
			if (!super.readyQueueEmpty()){
				priorityProcess = currentProcess;
				priorityProcessIndex = 0;
				for (int i = 0; i<super.readyQueueSize(); i++){
					if (super.getProcess(i).getPriority()<priorityProcess.getPriority()){
						priorityProcess = super.getProcess(i);
						priorityProcessIndex = super.getHighestPriorityProcessIndex(priorityProcess);
					}
				}
			}
			if (burstCounter==priorityProcess.getCPUBurst()){
				priorityProcess.setCompletionTime(sysCount);
				priorityProcess.setTAT(priorityProcess.getCompletionTime()-priorityProcess.getTimeStamp());
				priorityProcess.setWaitTime(priorityProcess.getCompletionTime()-priorityProcess.getTimeStamp()-priorityProcess.getCPUBurst());
				BHANDARI_BIDHAN_PROCESS jobRemoved = super.removeProcess(priorityProcessIndex);
				tempJobs.remove(jobRemoved);
				jobsRemoved.add(jobRemoved);
				
				writer.println(" ");
				writer.println("\nCPU Request Serviced during this clock interval: "+priorityProcess.getPID()+" "+priorityProcess.getTimeStamp()+" "+ priorityProcess.getCPUBurst()+" "+priorityProcess.getPriority());
				writer.println(formatDot());
				if (tempJobs.size()!=0){
					
					writer.println("Clock: "+sysCount);
					writer.println("Pending CPU request(s):");
				}
				
				for (int i = 0; i<tempJobs.size(); i++){
					writer.println(tempJobs.get(i).getPID()+" "+tempJobs.get(i).getTimeStamp()+" "+ tempJobs.get(i).getCPUBurst()+" "+tempJobs.get(i).getPriority());
				}
				burstCounter = 0;//set burst counter to 0 for another process

				//Check if all processes are finished
				if (super.readyQueueEmpty()&& job == totalProcess){
					break;
				}
				else if(!super.readyQueueEmpty()){
					currentProcess = super.getProcess(0);
				}

			}
			else if (burstCounter!=priorityProcess.getCPUBurst()){
				burstCounter++;
				sysCount++;
			}

		}
	}
	private String formatDot(){
		String temp = "";
		for (int i = 0; i<56; i++){
			temp+="-";
		}
		return temp;
	}
}
