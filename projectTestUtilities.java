package dbUtil;
/**
 * This program is used to test the projectTestUtilities class
 */
 
// You need to import the java.sql package to use JDBC
import java.sql.*; 
 
import java.util.Scanner;

/**
 * @author DEBJM367
 */
public class projectTestUtilities 
{
	// Global variables
	static projectUtilities testObj = new projectUtilities(); // projectTestUtilities object for testing
    static Scanner keyboard = new Scanner(System.in); // Standard input
    
    public static void main(String[] args) throws SQLException 
    {
		// Variables Needed For The Menu
		int choice;
		boolean done = false;

        while (!done) 
        {
			System.out.println();
			displaymenu();
			choice = getChoice();
            switch (choice) 
            {
			case 1: {
				openDB();
				break;
			}
			case 2: {
				addCourse();
				break;
			}
			case 3: {
				deleteStudent();
				break;
			}
			case 4: {
			    replace300Elective();
				break;
			}
			case 5: {
				availableCourses();
				break;
			}
			case 6: {
				getAdvisees();
				break;
			}
			case 7: {
				editReport();
				break;
			}
			case 8: {
				viewCourses();
				break;
			}
			case 9: {
				testObj.closeDB(); // Close the DB connection 
				break;
			}
			case 10: {
				done = true;
				System.out.println("Good bye");
				break;
            }
			case 11: {
				viewGradPlan();
				break;
			}
			case 12: {
				createGradPlan();
				break;
			}
		} // Switch
	}

	} // Main

    // Display Menu! 
    static void displaymenu() 
    {
		System.out.println("1)  Call openDB(String, String)");
		System.out.println("2)  Call addCourse(String, String, String, String, String, String, String)");
		System.out.println("3)  Call deleteStudent(String)");
		System.out.println("4)  Call replace300Elective()");
		System.out.println("5)  Call availableCourses(String)");
		System.out.println("6)  Call getAdvisees(String)");
		System.out.println("7)  Call editReport(String, String, String)");
		System.out.println("8)  Call viewCourses()");
		System.out.println("9)  Close the DB");
		System.out.println("10) Quit");
		System.out.println("11) Call viewGradPlan()");
		System.out.println("12) Call createGradPlan()"); 
    }

    // Retrieves which method the user would like to run. 
    static int getChoice() 
    {
		String input;
		int i = 0;
        while (i < 1 || i > 12) 
        {
			try {
				System.out.print("Please enter an integer between 1-12: ");
				input = keyboard.nextLine();
				i = Integer.parseInt(input);
				System.out.println();
			} catch (NumberFormatException e) {
				System.out.println("I SAID AN INTEGER!!!!");
			}
		}
		return i;
    }
    
    ////////////////
    // OUR TESTS //
    //////////////

    // Test openDB() Method 
	static void openDB() throws SQLException 
	{
		System.out.print("Please enter DB Username: ");
		String username = keyboard.nextLine();
		System.out.print("Please enter DB Password: ");
		String password = keyboard.nextLine();
        testObj.openDB(username, password);
    }
    
    // Test addCourse(String courseName, String department, String courseNumber, 
	// String numCredits, String description, String semester, String year) Method 
    static void addCourse() throws SQLException
    {
    	String courseName = "Cloud Computing";
    	String department = "CSCI";
    	String courseNumber = "388";
    	String numCredits = "4";
    	String description = "Introduction to cloud computing techniques.";
    	String semester = "F";
    	String year = "1";
    	
    	testObj.addCourse(courseNumber, department, semester, year, courseName, numCredits, description);
    }

    // Test deleteStudent(String studentID) Method
    static void deleteStudent() throws SQLException
    {
    	String id = "123456789";
    	
    	testObj.deleteStudent(id);
    }

    // Test replace300Elective() Method
    static void replace300Elective() throws SQLException
    {

    }

    // Test availableCourses(String) Method
    static void availableCourses() throws SQLException
    {
    	String courseN = "390";
    	String dept = "CSCI";
    	
    	testObj.availableCourses(courseN, dept);
    }

    // Test getAdvisees(String) Method
    static void getAdvisees() throws SQLException
    {
    	String facID = "333444555";
    	
    	testObj.getAdvisees(facID);
    }

    // Test editReport(String, String, String) Method
    static void editReport() throws SQLException
    {
    	String studentID = "123456789";
    	String courseTitle = "Cloud Computing";
    	String cYear = "0";
    	String currentSemester = "F";
    	
    	testObj.editReport(studentID, courseTitle, cYear, currentSemester);
    }
    
    // Test viewCourses() Method
    static void viewCourses() throws SQLException
    {
    	String semesterChoice = "S";
    	
    	testObj.viewCourses(semesterChoice);
    }
    
    // Test viewGradPlan() Method
    static void viewGradPlan() throws SQLException
    {
    	String studentID = "123456789";
    	
    	testObj.viewGradPlan(studentID);
    }
    
    // Test createGradPlan() Method 
    static void createGradPlan() throws SQLException
    {
    	String studentID = "123456789";
    	
    	testObj.createGradPlan(studentID);
    }
		
} // End of projectTestUtilities 
