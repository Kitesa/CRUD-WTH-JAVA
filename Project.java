import java.util.Scanner;
import java.io.*;

abstract class Record {
	protected String First_name, Last_name, Department, Id_number, Job_title, Year, Education_level;
	protected float Gpa, Salary;

	protected String emplClass(String emp_class){
		return emp_class;
	}	
}

class Employee extends Record{
	private int counter = 0;
	Employee(String first_name, String last_name, String id, String department, String education_level, String  job_title, float salary){
		super.First_name 		= first_name;
		super.Last_name 		= last_name;
		super.Id_number 		= id;
		super.Education_level 	= education_level;
		super.Job_title 		= job_title;
		super.Salary 			= salary;
		super.Department 		= department;
	}
}

class Student extends Record{
	Student(String first_name, String last_name, String id_number, String dept, String year, float gpa){
		super.First_name = first_name;
		super.Last_name = last_name;
		super.Id_number = id_number;
		super.Department = dept;
		super.Year = year;
		super.Gpa = gpa;
	}

	String studentStatus(){
		String status;
		if (this.Gpa > 3.00 && this.Gpa <= 4)
			status = "Distinction";
		else if(this.Gpa > 2.00 && this.Gpa <= 3.00)
			status = "Pass";
		else if(this.Gpa >= 1.75 && this.Gpa <= 2.00)
			status = "Warning";
		else if(this.Gpa > 0.00 && this.Gpa < 1.75)
			status = "Dismissed";
		else
			status = "Invalid";
		return status;
	}
}

class Operation{
	static File teacher = new File("src/employee.csv");
	static File admin = new File("src/admin.csv");
	static File student = new File("src/student.csv");
	
	//OPERATION METHOD TO DISPLAY THE DASHBOARD SELECTION
	static void StartUpMessage(){
		System.out.println("Go to?");
		System.out.println("1. Employee 	2. Students");
		printLine();
	}

	//OPERATION METHOD TO TAKE THE YES OR NO USER CHOICE
	static char proceedReject(){
		Scanner proceed_reject = new Scanner(System.in);
		char reject_proceed = proceed_reject.nextLine().charAt(0);
		char returned;
		if(reject_proceed == 'Y')
			returned = 'Y';
		else
			returned = 'N';
		return returned;
		
	}

	//operation method to prompt where the user is in.
	static void workingClass(){
		Scanner class_selection = new Scanner(System.in);
		int selected_class = class_selection.nextInt();
		switch(selected_class){
			case 1:
				System.out.println("*******Employee Dashboard*******");
				employeesOperationSelection();
				break;
			case 2:
				System.out.println("*******Students Dashboard*******");
				studentsOperationSelection();
				break;
			default:
				System.out.println("*******No dashboard with this Id*******");
				workingClass();
		}		
	}

	//OPERATION METHOD TO DSPLAY LINE FOR SEPARATION
	static void printLine(){
		for (int i = 0; i <= 70; i++) System.out.print("-");System.out.print("\n");
	}
	
	//OPERATION METHOD TO DSPLAY OPTION UNDER SELECTED DASHBOARD
	static void printPropmt() {
		System.out.println("1. Add a record.	2 Retreave.	3. Update	4. Delete *. Back");
		printLine();
	}

	//OPERATION METHODS ON A TEACHER.
	static void employeesOperationSelection(){
		printPropmt();
		while(true){
			Scanner operation_selection = new Scanner(System.in);
			char selected_operation = operation_selection.nextLine().charAt(0);
			char yes_or_no;
			switch(selected_operation){
				case '1':
					System.out.println("Do you want to add new record (Y/N)? (*-HOME)");
					yes_or_no = proceedReject();
					if(yes_or_no == 'Y'){
						addRecord();
						while(true){
							System.out.println("Continue adding a record?(Y-to proceed/N-to go back)");
							char leave_or_continue;
							Scanner continue_or_leave = new Scanner(System.in);
							leave_or_continue = continue_or_leave.nextLine().charAt(0);
							if(leave_or_continue == 'Y'){
								addRecord();
							}else{
								employeesOperationSelection();
							} 
						}
					}else if(yes_or_no == 'N'){
						employeesOperationSelection();
					}else if(yes_or_no == '*'){
						StartUpMessage();
					} else {
						System.out.println("Invalid selection we bring you back to home...");
						StartUpMessage();						
					}
					break;
				case '2':
					System.out.println("You want to retreave(Y/N)?");
					yes_or_no = proceedReject();
					if(yes_or_no == 'Y')
						retreaveRecord();
					else
						employeesOperationSelection();
					break;
				case '3':
					System.out.println("You want to update(Y/N)?");
					yes_or_no = proceedReject();
					if(yes_or_no == 'Y')
						updateRecord();
					else
						employeesOperationSelection();
					break;
				case '4':
					System.out.println("You want to delete(Y/N)?");
					yes_or_no = proceedReject();
					if(yes_or_no == 'Y')
						deleteRecord();
					else
						employeesOperationSelection();
					break;
				case '*':
					StartUpMessage();
				default:
					System.out.println("Invalid selection");
					employeesOperationSelection();
			}
		}
	}

	//OPERATION METHOD TO TAKE INPUT PROCESS AND REPLACE IT WITH NEW UPDATE VALUE
	static void recordInfoUpdate(){
	 	String id_to_be_updated;
	 	Scanner update_id = new Scanner(System.in);
	 	id_to_be_updated = update_id.nextLine();
		
	 	try{
	 		FileReader reader = new FileReader("src/employee.csv");
	 		BufferedReader buffer_reader = new BufferedReader(reader);
	 		String line  = buffer_reader.readLine();

	 		File random_file = new File("src/random.csv");
 			FileWriter random_writter = new FileWriter(random_file, true);
 			boolean record_exist = false;
 			String[] to_be_updated;

 			//COPY ALL RECORDS EXCEPT THE RECORD TO BE UPDATED TO TEMPRARY FILE
 			while(line != null) {
 				if(line.split("\t")[0].equals(id_to_be_updated) == false){
	            	random_writter.append(line);
	            	random_writter.append("\n");
 				} else{
 					record_exist = true;
 					to_be_updated = line.split("\t");
 					printLine();
 					System.out.println("To be updated : ");
 					System.out.println("****UPDATE FROM****");
 					String[] line_key = {"Id Number\t", "First Name\t", "Last name\t", "Department\t", "Job Title\t", "Educ Level\t", "Salary \t"};
 					for(int i = 0; i < to_be_updated.length; i++){
 						System.out.println(line_key[i] + " : " + to_be_updated[i]);
 					}
 						
 				}
 				line = buffer_reader.readLine();
        	}
        	random_writter.close();
        	if(record_exist == false){
        		System.out.println("No record with this ID please check the id.");
        		printLine();
        		updateRecord();
        	}else{
        		printLine();
        		System.out.println("****TO****");
        		System.out.print("ID Number:\t");
			 	Scanner record_id_number = new Scanner(System.in);
			 	String entered_id_number = record_id_number.nextLine();


        		System.out.print("First Name:\t");
			 	Scanner record_first_name = new Scanner(System.in);
			 	String entered_first_name = record_first_name.nextLine();

			 	System.out.print("Last Name:\t");
			 	Scanner record_last_name = new Scanner(System.in);
			 	String entered_last_name = record_last_name.nextLine();

			 	System.out.print("Department:\t");
			 	Scanner record_department = new Scanner(System.in);
			 	String entered_record_department = record_department.nextLine();

			 	System.out.print("Job Title:\t");
			 	Scanner record_job_title = new Scanner(System.in);
			 	String entered_job_title = record_job_title.nextLine();
			 	
			 	System.out.print("Education Level:\t");
			 	Scanner record_education_level = new Scanner(System.in);
			 	String entered_education_level = record_education_level.nextLine();

			 	System.out.print("Salary:\t");
			 	Scanner record_salary = new Scanner(System.in);
			 	Float entered_record_salary = record_salary.nextFloat();
        		//CREATE AN INSTANCE FOR NEWLY ENTERED TEACHER DATA
			 	Employee new_teacher = new Employee(entered_first_name, 
			 									entered_last_name, 
			 									entered_id_number, 
			 									entered_record_department, 
			 									entered_job_title,
			 									entered_education_level,
			 									entered_record_salary
			 									);
			 	try{
			 		File random_file_update = new File("src/random.csv");
 					FileWriter random_writter_update = new FileWriter(random_file_update, true);
			 		random_writter_update.append(new_teacher.Id_number+ "\t" +
		 							new_teacher.First_name + "\t" +
		 							new_teacher.Last_name + "\t" +
		 							new_teacher.Job_title+ "\t" +
		 							new_teacher.Education_level+ "\t" +
		 							new_teacher.Salary+"\n");
				 	random_writter_update.close();
			 	} catch(IOException e){
			 		System.out.println("Some error happens");
			 	}
        	}    	

        	//CLEAN EMPLOYEE FILE
    		FileWriter emp_cleaner = new FileWriter("src/employee.csv");
 			emp_cleaner.write("");
 			emp_cleaner.close();


 			//COPY ALL RECORD FROM TEMPORARY FILE AND WRITE IT TO EMPLOYEE
	 		FileReader back_reader = new FileReader("src/random.csv");
	 		BufferedReader back_buffer_reader = new BufferedReader(back_reader);
	 		String back_line  = back_buffer_reader.readLine();

	 		File back_random_file = new File("src/employee.csv");
 			FileWriter back_random_writter = new FileWriter(back_random_file, true);
 			
        	while(back_line != null) {
	            	back_random_writter.append(back_line+"\n");
            		back_line = back_buffer_reader.readLine();
        	}
        	back_random_writter.close(); 			

 			//CLEAN EMPLOYEE FILE
 			FileWriter random_writter_after_copy = new FileWriter(random_file);
 			random_writter_after_copy.write("");
 			random_writter_after_copy.close();

        	System.out.println("User with an id of "+ id_to_be_updated + "is successfully updated");
        	employeesOperationSelection();
	 	} catch(IOException e){
	 		System.out.println("INVALID FILE");
	 	}
	}


	static void recordRegisterInfo(){
		System.out.print("First Name:\t");
	 	Scanner record_first_name = new Scanner(System.in);
	 	String entered_first_name = record_first_name.nextLine();

	 	System.out.print("Last Name:\t");
	 	Scanner record_last_name = new Scanner(System.in);
	 	String entered_last_name = record_last_name.nextLine();

	 	System.out.print("ID Number:\t");
	 	Scanner record_id_number = new Scanner(System.in);
	 	String entered_id_number = record_id_number.nextLine();

	 	System.out.print("Department:\t");
	 	Scanner record_department = new Scanner(System.in);
	 	String entered_record_department = record_department.nextLine();

	 	System.out.print("Job Title:\t");
	 	Scanner record_job_title = new Scanner(System.in);
	 	String entered_job_title = record_job_title.nextLine();
	 	
	 	System.out.print("Education Level:\t");
	 	Scanner record_education_level = new Scanner(System.in);
	 	String entered_education_level = record_education_level.nextLine();

	 	System.out.print("Salary:\t");
	 	Scanner record_salary = new Scanner(System.in);
	 	Float entered_record_salary = record_salary.nextFloat();
		try{
	 		FileReader reader = new FileReader("src/employee.csv");
	 		BufferedReader buffer_reader = new BufferedReader(reader);
	 		String line  = buffer_reader.readLine();
        	String[] employeeInfo;
         	while(line != null) {
	            employeeInfo = line.split("\t");
	            if(employeeInfo[0].equals(entered_id_number)){
	            	System.out.println("------------------\n|\t**ID in use**\t|\n Please check the record id");
	            	record_id_number = new Scanner(System.in);
	            	entered_id_number = record_id_number.nextLine();
 				}else{
            		line = buffer_reader.readLine();
 				}
        	}
        	buffer_reader.close();
        	System.out.println("====================================");
        }catch(IOException e){
        	 System.out.println("Sorry registration fails...");
		} 
	 	Record new_emp = new Employee(entered_first_name, 
	 									entered_last_name, 
	 									entered_id_number, 
	 									entered_record_department, 
	 									entered_job_title,
	 									entered_education_level,
	 									entered_record_salary
	 									);
	 	System.out.println("Ready to be committed record!");
	 	printLine();
	 	System.out.println("Id Number\t"+new_emp.Id_number + "\n" + 
	 						"First Name\t"+new_emp.First_name + "\n"+  
	 						"Last name\t"+new_emp.Last_name + "\n" +
	 						"Department\t"+new_emp.Department + "\n" + 
	 						"Job Title\t"+new_emp.Job_title + "\n"+
	 						"Educ Level\t"+new_emp.Education_level + "\n" +
	 						"Salary \t"+new_emp.Salary
	 	);
	 	//ENCAPTULATION
	 	System.out.println(new_emp.emplClass("admin"));
	 	System.out.print("Commit?(Y/N)\t");
	 	char choice = proceedReject();
	 	switch(choice){
	 		case 'Y':
	 			try{
		 			FileWriter writter = new FileWriter(teacher, true);
		 			writter.append(new_emp.Id_number+ "\t" +
		 					new_emp.First_name + "\t" +
		 							new_emp.Last_name + "\t" +
		 							new_emp.Job_title+ "\t" +
		 							new_emp.Education_level+ "\t" +
		 							new_emp.Salary+"\n");
				 	writter.close();

			 	} catch(IOException e){
			 		System.out.println("Oops error");
			 	}
		 		System.out.println("Record Committed Successfully");
		 		break;
		 	case 'N':
		 		System.out.println("We bring you back...");
		 		recordRegisterInfo();
		 		break;
		 	default:
		 		System.out.println("Invalid choice(Y/N only )...");
		 		System.out.println("We bring you back...");
		 		recordRegisterInfo();
		}
	}

	static void addRecord(){
	 	System.out.println("Record info.| You are adding a record");
	 	recordRegisterInfo();
	 	printLine();

	}

	static void updateRecord(){
	 	System.out.println("Enter Id of the record to be updated");
	 	System.out.println("=================================================");
	 	recordInfoUpdate();
	}

	static void deleteRecord(){
	 	System.out.println("Enter Id of the record to be deleted");
	 	String id_to_be_deleted;
	 	Scanner delete_id = new Scanner(System.in);
	 	id_to_be_deleted = delete_id.nextLine();
	 	try{
	 		FileReader reader = new FileReader("src/employee.csv");
	 		BufferedReader buffer_reader = new BufferedReader(reader);
	 		String line  = buffer_reader.readLine();

	 		File random_file = new File("src/random.csv");
 			FileWriter random_writter = new FileWriter(random_file, true);
 			boolean record_exist = false;
 			String full_name_to_be_deleted = "";
 			//COPY ALL RECORDS EXCEPT THE RECORD TO BE DELETED TO TEMPRARY FILE
 			while(line != null) {
 				if(line.split("\t")[0].equals(id_to_be_deleted) == false){
	            	random_writter.append(line);
	            	random_writter.append("\n");
	            	line = buffer_reader.readLine();
 				} else{
 					record_exist = true;
 					full_name_to_be_deleted = line.split("\t")[1] + " " + line.split("\t")[2];
 					line = buffer_reader.readLine();	
 				}
        	}
        	random_writter.close();
        	if(record_exist == false){
        		System.out.println("No record with this ID please check the id.");
        		printLine();
        		deleteRecord();
        	}else{
        	System.out.println("You are going to delete " + full_name_to_be_deleted +" continue?(Y/N)");
		 	char continue_or_leave = proceedReject();
		 	switch(continue_or_leave){
		 		case 'Y':
				 			//CLEAN EMPLOYEE FILE
		    		FileWriter emp_cleaner = new FileWriter("src/employee.csv");
		 			emp_cleaner.write("");
		 			emp_cleaner.close();


		 			//COPY ALL RECORD FROM TEMPORARY FILE AND WRITE IT TO EMPLOYEE
			 		FileReader back_reader = new FileReader("src/random.csv");
			 		BufferedReader back_buffer_reader = new BufferedReader(back_reader);
			 		String back_line  = back_buffer_reader.readLine();

			 		File back_random_file = new File("src/employee.csv");
		 			FileWriter back_random_writter = new FileWriter(back_random_file, true);
		 			
		        	while(back_line != null) {
			            	back_random_writter.append(back_line);
			            	back_random_writter.append("\n");
		            		back_line = back_buffer_reader.readLine();
		        	}
		        	back_random_writter.close(); 			

		 			//CLEAN EMPLOYEE FILE
		 			FileWriter random_writter_after_copy = new FileWriter(random_file);
		 			random_writter_after_copy.write("");
		 			random_writter_after_copy.close();
		 			back_buffer_reader.close();
		        	System.out.println("Record with an id of "+ id_to_be_deleted + "is successfully deleted");
				 	printLine();
				 	System.out.println("Continue deleting record(Y/N)");
				 	char cont_or_leave = proceedReject();
				 	switch(cont_or_leave){
				 		case 'Y':
				 			deleteRecord();
				 			break;
				 		case 'N':
				 			employeesOperationSelection();
				 			break;
				 		default:
				 			employeesOperationSelection();
				 			break;
				 	}
		 			break;
		 		case 'N':
		 			employeesOperationSelection();
		 			break;
		 		default:
		 			employeesOperationSelection();
		 			break;
		 	}
        	
        	}   	
	 	} catch(IOException e){
	 		System.out.println("INVALID FILE");
	 	}
	}

	static void retreaveRecord(){
		String selected_retreaval;
	 	System.out.println("* For all, ID for single");
	 	Scanner retreaval_selection = new Scanner(System.in);
	 	selected_retreaval = retreaval_selection.nextLine();
	 		try{
		 		FileReader reader = new FileReader("src/employee.csv");
		 		BufferedReader buffer_reader = new BufferedReader(reader);
		 		String line  = buffer_reader.readLine();
	        	String[] employeeInfo;
	        	int counter = 1;
	    	 	if (selected_retreaval.equals("*")){
		        	System.out.println("=======LIST OF ALL EMPLOYEES========");
		         	while(line != null) {
		         		System.out.print(counter+". ");
			            employeeInfo = line.split("\t");
			            for(String tempStr : employeeInfo) {
			               System.out.print(tempStr + " ");
			            }
		            	line = buffer_reader.readLine();
		            	counter++;
		            	System.out.println();
	            	}
	            	System.out.println("====================================");
	    	 	} else {
	    	 		System.out.println("=======RECORD WITH THIS INFO========");
	    	 		while(line != null) {
			            employeeInfo = line.split("\t");
			            if(employeeInfo[0].equals(selected_retreaval)) {
			               System.out.println(line);
			               System.out.println("====================================");
			               return;
		            	}
		            	line = buffer_reader.readLine();
	            	}
	    	 	}
	    	 	buffer_reader.close();
	    	 	
            }catch(IOException e){
            	 System.out.println("No file");
			} 
		}
		
//OPERATION METHODS ON A STUDENT BEGINS HERE
			
			static void studentsOperationSelection(){
				printPropmt();
				while(true){
					char selected_operation;
					Scanner operation_selection = new Scanner(System.in);
					selected_operation = operation_selection.nextLine().charAt(0);
					char yes_or_no;
					switch(selected_operation){
						case '1':
							System.out.println("Do you want to continue adding new record (Y/N)? (*-HOME)");
							yes_or_no = proceedReject();
							if(yes_or_no == 'Y'){
								addStudentRecord();
								while(true){
									System.out.println("Continue adding a record?(Y-to proceed/N-to go back)");
									char leave_or_continue;
									Scanner continue_or_leave = new Scanner(System.in);
									leave_or_continue = continue_or_leave.nextLine().charAt(0);
									if(leave_or_continue == 'Y'){
										addStudentRecord();
									}else{
										studentsOperationSelection();
									} 
									continue_or_leave.close();
								}
							}else if(yes_or_no == 'N'){
								studentsOperationSelection();
							}else if(yes_or_no == '*'){
								StartUpMessage();
							} else {
								System.out.println("Invalid selection we bring you back to home...");
								StartUpMessage();						
							}
							break;
						case '2':
							System.out.println("You want to retreave(Y/N)?");
							yes_or_no = proceedReject();
							if(yes_or_no == 'Y')
								retreaveStudentRecord();
							else
								studentsOperationSelection();
							break;
						case '3':
							System.out.println("You want to update(Y/N)?");
							yes_or_no = proceedReject();
							if(yes_or_no == 'Y') {
								updateStudentRecord();
								return;
							}
							else
								studentsOperationSelection();
							break;
						case '4':
							System.out.println("You want to delete(Y/N)?");
							yes_or_no = proceedReject();
							if(yes_or_no == 'Y')
								deleteStudentRecord();
							else
								employeesOperationSelection();
							break;
						case '*':
							StartUpMessage();
						default:
							System.out.println("Invalid selection");
							employeesOperationSelection();
					}
				}
			}
			
			//A METHOD TO MANAGE PROMPT FOR DTUDENT REGISTRATION INFO
			static void studentRegisterInfo(){
				System.out.print("First Name:\t");
			 	Scanner record_first_name = new Scanner(System.in);
			 	String entered_first_name = record_first_name.nextLine();

			 	System.out.print("Last Name:\t");
			 	Scanner record_last_name = new Scanner(System.in);
			 	String entered_last_name = record_last_name.nextLine();

			 	System.out.print("ID Number:\t");
			 	Scanner record_id_number = new Scanner(System.in);
			 	String entered_id_number = record_id_number.nextLine();

			 	System.out.print("Department:\t");
			 	Scanner record_department = new Scanner(System.in);
			 	String entered_record_department = record_department.nextLine();


			 	System.out.print("Year:\t");
			 	Scanner year = new Scanner(System.in);
			 	String entered_year = year.nextLine();
			 	
			 	System.out.print("GPA:\t");
			 	Scanner gpa = new Scanner(System.in);
			 	Float entered_gpa = gpa.nextFloat();
			 	
				try{
			 		FileReader reader = new FileReader("src/student.csv");
			 		BufferedReader buffer_reader = new BufferedReader(reader);
			 		String line  = buffer_reader.readLine();
		        	String[] studentInfo;
		         	while(line != null) {
		         		studentInfo = line.split("\t");
			            if(studentInfo[0].equals(entered_id_number)){
			            	System.out.println("------------------\n|\t**ID in use**\t|\n Please check the record id");
			            	record_id_number = new Scanner(System.in);
			            	entered_id_number = record_id_number.nextLine();
		 				}else{
		            		line = buffer_reader.readLine();
		 				}
		        	}
		        	buffer_reader.close();
		        	System.out.println("====================================");
		        }catch(IOException e){
		        	 System.err.println("Sorry something went wrong...");
				} 
			 	Record new_student = new Student(entered_first_name, 
			 									entered_last_name, 
			 									entered_id_number, 
			 									entered_record_department, 
			 									entered_year,
			 									entered_gpa
			 									);
			 	
			 	System.out.println("Student info ready to be committed to the file!");
			 	printLine();
			 	System.out.println("Id Number\t"+new_student.Id_number + "\n" + 
			 						"First Name\t"+new_student.First_name + "\n"+  
			 						"Last name\t"+new_student.Last_name + "\n" +
			 						"Department\t"+new_student.Department + "\n" + 
			 						"Year\t"+new_student.Year + "\n"+
			 						"GPA\t"+new_student.Gpa + "\n"
			 	);

			 	System.out.print("Commit?(Y/N)\t");
			 	char choice = proceedReject();
			 	switch(choice){
			 		case 'Y':
			 			try{
				 			FileWriter writter = new FileWriter(student, true);
				 			writter.append(new_student.Id_number+ "\t" +
				 							new_student.First_name + "\t" +
				 							new_student.Last_name + "\t" +
				 							new_student.Department+ "\t" +
				 							new_student.Year+ "\t" +
				 							new_student.Gpa+"\n");
						 	writter.close();
						 	System.out.println("Record Committed Successfully");
						 	printLine();
						 	System.out.println("Continue adding new student record(Y/N)");
						 	char continue_or_leave = proceedReject();
						 	switch(continue_or_leave){
						 		case 'Y':
						 			studentRegisterInfo();
						 			break;
						 		case 'N':
						 			studentsOperationSelection();
						 			break;
						 		default:
						 			studentsOperationSelection();
						 			break;
						 	}
					 	} catch(IOException e){
					 		System.out.println("Oops error");
					 	}
				 		
				 		break;
				 	case 'N':
				 		System.out.println("We bring you back...");
				 		studentRegisterInfo();
				 		break;
				 	default:
				 		System.out.println("Invalid choice(Y/N only )...");
				 		System.out.println("We bring you back...");
				 		studentRegisterInfo();
				}
			 	record_first_name.close();
			 	record_last_name.close();
			 	record_id_number.close();
			 	record_department.close();
			 	year.close();
			 	gpa.close();
			}
			
			//A STUDENT INFORMATION UPDATING BEGINS HERE
			static void studentRecordInfoUpdate(){
			 	String id_to_be_updated;
			 	Scanner update_id = new Scanner(System.in);
			 	id_to_be_updated = update_id.nextLine();
				
			 	try{
			 		FileReader reader = new FileReader("src/student.csv");
			 		BufferedReader buffer_reader = new BufferedReader(reader);
			 		String line  = buffer_reader.readLine();

			 		File random_file = new File("src/random.csv");
		 			FileWriter random_writter = new FileWriter(random_file, true);
		 			boolean record_exist = false;
		 			String[] to_be_updated;

		 			//COPY ALL RECORDS EXCEPT THE RECORD TO BE UPDATED TO TEMPRARY FILE
		 			while(line != null) {
		 				if(line.split("\t")[0].equals(id_to_be_updated) == false){
			            	random_writter.append(line);
			            	random_writter.append("\n");
		 				} else{
		 					record_exist = true;
		 					to_be_updated = line.split("\t");
		 					printLine();
		 					System.out.println("To be updated : ");
		 					System.out.println("****UPDATE FROM****");
		 					String[] line_key = {"Id Number\t", "First Name\t", "Last name\t", "Department\t", "Year\t", "GPA \t"};
		 					for(int i = 0; i < to_be_updated.length; i++){
		 						System.out.println(line_key[i] + " : " + to_be_updated[i]);
		 					}
		 						
		 				}
		 				line = buffer_reader.readLine();
		        	}
		        	random_writter.close();
		        	buffer_reader.close();
		        	if(record_exist == false){
		        		System.err.println("No student record with this ID please check the id.");
		        		printLine();
		        		updateStudentRecord();
		        	}else{
		        		printLine();
		        		System.out.println("****TO****");
		        		System.out.print("ID Number:\t");
					 	Scanner record_id_number = new Scanner(System.in);
					 	String entered_id_number = record_id_number.nextLine();


		        		System.out.print("First Name:\t");
					 	Scanner record_first_name = new Scanner(System.in);
					 	String entered_first_name = record_first_name.nextLine();

					 	System.out.print("Last Name:\t");
					 	Scanner record_last_name = new Scanner(System.in);
					 	String entered_last_name = record_last_name.nextLine();

					 	System.out.print("Department:\t");
					 	Scanner record_department = new Scanner(System.in);
					 	String entered_record_department = record_department.nextLine();

					 	System.out.print("Year:\t");
					 	Scanner record_year = new Scanner(System.in);
					 	String entered_year = record_year.nextLine();
					 	
					 	System.out.print("GPA:\t");
					 	Scanner record_gpa = new Scanner(System.in);
					 	float entered_gpa = record_gpa.nextFloat();

		        		//CREATE AN INSTANCE FOR NEWLY ENTERED STUDENT DATA
					 	Record new_student = new Student(entered_first_name, 
					 									entered_last_name, 
					 									entered_id_number, 
					 									entered_record_department, 
					 									entered_year,
					 									entered_gpa
					 									);
					 	record_id_number.close();
					 	record_first_name.close();
					 	record_last_name.close();
					 	record_department.close();
					 	record_year.close();
					 	record_gpa.close();
					 	try{
					 		File random_file_update = new File("src/random.csv");
		 					FileWriter random_writter_update = new FileWriter(random_file_update, true);
					 		random_writter_update.append(new_student.Id_number+ "\t" +
					 				new_student.First_name + "\t" +
					 				new_student.Last_name + "\t" +
					 				new_student.Department+ "\t" +
					 				new_student.Year+ "\t" +
					 				new_student.Gpa+"\n");
						 	random_writter_update.close();
					 	} catch(IOException e){
					 		System.out.println("Some error happens");
					 	}
					 	update_id.close();
		        	}    	

		        	//CLEAN EMPLOYEE FILE
		    		FileWriter emp_cleaner = new FileWriter("src/student.csv");
		 			emp_cleaner.write("");
		 			emp_cleaner.close();


		 			//COPY ALL RECORD FROM TEMPORARY FILE AND WRITE IT TO EMPLOYEE
			 		FileReader back_reader = new FileReader("src/random.csv");
			 		BufferedReader back_buffer_reader = new BufferedReader(back_reader);
			 		String back_line  = back_buffer_reader.readLine();

			 		File back_random_file = new File("src/student.csv");
		 			FileWriter back_random_writter = new FileWriter(back_random_file, true);
		 			
		        	while(back_line != null) {
			            	back_random_writter.append(back_line);
			            	back_random_writter.append("\n");
		            		back_line = back_buffer_reader.readLine();
		        	}
		        	back_random_writter.close(); 			

		 			//CLEAN EMPLOYEE FILE
		 			FileWriter random_writter_after_copy = new FileWriter(random_file);
		 			random_writter_after_copy.write("");
		 			random_writter_after_copy.close();

		        	System.out.println("User with an id of "+ id_to_be_updated + " is successfully updated");
		        	
			 	} catch(IOException e){
			 		System.out.println("INVALID FILE");
			 	}
			 	
			}
			
			//A METHOD TO RETREAVE A STUDENT RECORD FROM FILE
			static void retreaveStudentRecord(){
				String selected_retreaval;
			 	System.out.println("* For all, ID for single");
			 	Scanner retreaval_selection = new Scanner(System.in);
			 	selected_retreaval = retreaval_selection.nextLine();
			 		try{
				 		FileReader reader = new FileReader("src/student.csv");
				 		BufferedReader buffer_reader = new BufferedReader(reader);
				 		String line  = buffer_reader.readLine();
			        	String[] employeeInfo;
			        	int counter = 1;
			    	 	if (selected_retreaval.equals("*")){
				        	System.out.println("=======LIST OF ALL STUDENTS========");
				         	while(line != null) {
				         		System.out.print(counter+". ");
					            employeeInfo = line.split("\t");
					            for(String tempStr : employeeInfo) {
					               System.out.print(tempStr + " ");
					            }
				            	line = buffer_reader.readLine();
				            	counter++;
				            	System.out.println();
			            	}
			            	System.out.println("====================================");
			    	 	} else {
			    	 		while(line != null) {
					            employeeInfo = line.split("\t");
					            if(employeeInfo[0].equals(selected_retreaval)) {
					               System.out.println("=======RECORD WITH THIS INFO========");
					               System.out.println(line);
					               System.out.println("====================================");
					               retreaveStudentRecord();
					               return;
				            	}
				            	line = buffer_reader.readLine();
				            }
			    	 	}
			    	 	buffer_reader.close();
		            }catch(IOException e){
		            	 System.out.println("No file");
					} 
				}
			
			//A METHOD TO DELETE A STUDENT RECORD FROM A FILE
			static void deleteStudentRecord(){
			 	System.out.println("Enter Id of the student to be deleted");
			 	String id_to_be_deleted;
			 	Scanner delete_id = new Scanner(System.in);
			 	id_to_be_deleted = delete_id.nextLine();
			 	String to_be_deleted = "";
			 	try{
			 		FileReader reader = new FileReader("src/student.csv");
			 		BufferedReader buffer_reader = new BufferedReader(reader);
			 		String line  = buffer_reader.readLine();

			 		File random_file = new File("src/random.csv");
		 			FileWriter random_writter = new FileWriter(random_file, true);
		 			boolean record_exist = false;
		 			
		 			//COPY ALL RECORDS EXCEPT THE RECORD TO BE DELETED TO TEMPRARY FILE
		 			while(line != null) {
		 				if(line.split("\t")[0].equals(id_to_be_deleted) == false){
			            	random_writter.append(line);
			            	random_writter.append("\n");
			            	line = buffer_reader.readLine();
		 				} else{
		 					record_exist = true;
		 					to_be_deleted = line.split("\t")[1] + " " + line.split("\t")[2];
		 					line = buffer_reader.readLine();	
		 				}
		        	}
		        	random_writter.close();
		        	buffer_reader.close();
		        	if(record_exist == false){
		        		System.err.println("No record with this ID please check the id.");
		        		printLine();
		        		deleteStudentRecord();
		        	}
		        	if(to_be_deleted.equals("") == false) {
		        		System.err.println("You are going to delete " + to_be_deleted + ". CONTINUE?(Y/N)");
		        		char yes_or_no = proceedReject();
		        		if(yes_or_no == 'Y') {

				        	//CLEAN STUDENT FILE
				    		FileWriter std_cleaner = new FileWriter("src/student.csv");
				 			std_cleaner.write("");
				 			std_cleaner.close();


				 			//COPY ALL RECORD FROM TEMPORARY FILE AND WRITE IT TO STUDENT
					 		FileReader back_reader = new FileReader("src/random.csv");
					 		BufferedReader back_buffer_reader = new BufferedReader(back_reader);
					 		String back_line  = back_buffer_reader.readLine();

					 		File back_random_file = new File("src/student.csv");
				 			FileWriter back_random_writter = new FileWriter(back_random_file, true);
				 			
				        	while(back_line != null) {
					            	back_random_writter.append(back_line);
					            	back_random_writter.append("\n");
				            		back_line = back_buffer_reader.readLine();
				        	}
				        	back_random_writter.close(); 			
				        	back_buffer_reader.close();
				 			//CLEAN RANDOM FILE
				 			FileWriter random_writter_after_copy = new FileWriter(random_file);
				 			random_writter_after_copy.write("");
				 			random_writter_after_copy.close();

				        	System.out.println("Student with an id of "+ id_to_be_deleted + " is successfully deleted");
				        	deleteStudentRecord();
		        		}else {
		        			//CLEAN RANDOM FILE
				 			FileWriter random_writter_after_copy = new FileWriter(random_file);
				 			random_writter_after_copy.write("");
				 			random_writter_after_copy.close();
		        			deleteStudentRecord();
		        		}
		        	}
		        	delete_id.close();
			 	} catch(IOException e){
			 		System.out.println("INVALID FILE");
			 	}
			}
			
			

			static void addStudentRecord(){
			 	System.out.println("Record info.| You are adding a record");
			 	studentRegisterInfo();
			 	printLine();
			}
			
			static void updateStudentRecord(){
			 	System.out.println("Enter Id of the record to be updated");
			 	System.out.println("=================================================");
			 	studentRecordInfoUpdate();
			}
			
			
			//STUDENTS OPERATION ENDS HERE
}

public class Project{
	public static void main(String[] args) {
		System.out.println("SIMPLE STUDENT AND EMPLOYEE INFO MANAGEMENT SYSTEM");
		Operation start_service = new Operation();
		start_service.printLine();
		start_service.StartUpMessage();
		start_service.workingClass();
		start_service.employeesOperationSelection();
	}
}
