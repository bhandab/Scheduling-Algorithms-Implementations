import java.util.*;
import java.io.*;
public class BHANDARI_BIDHAN_CPU_Sched {
	public static void main(String[] args){
		ArrayList<BHANDARI_BIDHAN_PROCESS> jobs = new ArrayList<BHANDARI_BIDHAN_PROCESS>();
		//BHANDARI_BIDHAN_PROCESS process;
		String algorithm = "";
		try{
			Scanner in = new Scanner(new FileInputStream("in.txt"));
			String firstLine = in.nextLine();
			Scanner algoInfo = new Scanner(firstLine);
			algoInfo.next();
			algorithm = algoInfo.next();
			algoInfo.close();
			while(in.hasNext()){
				String line = in.nextLine();
				Scanner info = new Scanner(line);
				info.next();
				int pid = Integer.parseInt(info.next());
				int arrivalTime = Integer.parseInt(info.next());
				int cBurst = Integer.parseInt(info.next());
				if (!algorithm.equals("pnp")){
					jobs.add(new BHANDARI_BIDHAN_PROCESS(pid, arrivalTime,cBurst));
				}
				else{
					int priority = Integer.parseInt(info.next());
					jobs.add(new BHANDARI_BIDHAN_PROCESS(pid, arrivalTime, cBurst, priority));
				}
				info.close();
			}
			in.close();
		}
		catch(FileNotFoundException e){
			System.out.println("File Not Found");
		}
      BHANDARI_BIDHAN_ALGORITHMS schedular = new BHANDARI_BIDHAN_ALGORITHMS(algorithm+"_out.txt",algorithm);
      schedular.scheduleProcesses(jobs);
			}
	
}
