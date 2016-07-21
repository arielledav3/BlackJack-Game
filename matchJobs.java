
public class matchJobs {
	public static void main(String [] args){
		String[] employeeAnswer = {"a7", "a4","a1", "a5","a7","a2","a3","a8","a9","a10","a11","a12","a13","a14","a15"};
		String[] jobAnswer = {"a12", "a4","a1", "a5","a7","a3","a10","a13","a2","a15","a9","a12","a8","a14","a15"};
		
		boolean Done = false;
		int matches = 0;
		for(int i = 0; i < employeeAnswer.length - 10; i++){
			System.out.println("Checking, " + employeeAnswer[i] + jobAnswer[i]);
			if(employeeAnswer[i].equals(jobAnswer[i])){
				matches++;
				System.out.println("match, " + matches);
			}
		}//end loop
		if(matches >= 4){
			System.out.println("Yup its a match");
		}else{
			System.out.println("No its not a match");
		}//end if
		

	}

}

