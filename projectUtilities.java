package dbUtil;
/**
 * This class provides some basic methods for accessing a MariaDB database.
 * It uses Java JDBC and the MariaDB JDBC driver, mariadb-java-client-2.4.0.jar
 * to open an modify the DB.
 */

// You need to import the java.sql package to use JDBC methods and classes
import java.sql.*;

/**
 * @author DEBJM367
 * 
 */
public class projectUtilities
{
	private Connection conn = null; // Connection Object
	 
	/**
	 * @return the conn
	 */
	public Connection getConn() 
	{
		return conn;
	}

	/**  
	 * Purpsoe: Open the Database! 
	 * 
	 * @param username is a String that is the DB account username
	 * @param password is a String that is the password the account
	 */
	public void openDB (String username, String password)
	{
        Connection conn = null;
        
		// Connect to the database
		String url = "jdbc:mariadb://localhost:2000/company367_2020";
		String user = username; 
		String pass = password; 
	
        try 
        {
			conn = DriverManager.getConnection(url, user, pass); // PROMPT USER FOR USERNAME & PASSWORD HERE!
				
        } 
        catch (SQLException e) 
        {
					System.out.println("using url: "+ url + "?" + user + "&" + pass);
					System.out.println("problem connecting to MariaDB: " + e.getMessage());			
		}
	} // End of openDB() method

	/**
	 * Close the connection to the DB
	 */
    public void closeDB() 
    {
        try 
        {
			conn.close();
			conn = null;
        } 
        catch (SQLException e) 
        {
			System.err.println("Failed to close database connection: " + e);
		}
	} // End of closeDB() method

    ///////////////////////
    // OUR USER STORIES //
    /////////////////////

	/**  
	 * 1. Add a New Course to the Curriculum 
	 * 
     * Purpose: Add a new course to the existing courses available.
     *          It is assumed that this course is not already in
     *          the database. 
     * 
     * User Group: Faculty 
	 * 
	 * @param String courseName -- The name of the new course being added. 
     * @param String department -- The department offering the new course (CSCI, MATH, GEOS). 
     * @param String courseNumber -- The number associated with the new course.
     * @param String numCredits -- The number of credits the new course is worth. 
     * @param String description -- A brief overview of the new course.
     * @param String semester -- The semester the new course will be offered.
     * @param String year -- The year the new course will be offered. 
     * 
     * Result: If the insertion operation is successful, the courseName and 
     *         courseNumber will be displayed; however, an error message will
     *         be shown if the operation fails.
	 */
    public ResultSet addCourse(String courseName, String department, String courseNumber, 
                          String numCredits, String description, String semester, String year)
    {
        String sql = null;
        ResultSet result = null; 

        try 
        {
			// Create a Statement and an SQL string for the statement
			sql = "INSERT INTO COURSE" + 
                  "VALUES (Num = ?, Dept = ?, Semester = ?, Year = ?, Title = ?, Credits = ?, Description = ?)";
                  
			PreparedStatement pstmt = conn.prepareStatement(sql);

			pstmt.clearParameters();
            pstmt.setString(1, courseNumber); // Set 1st Parameter
            pstmt.setString(2, department); // Set 2nd Parameter
            pstmt.setString(3, semester); // Set 3rd Parameter
            pstmt.setString(4, year); // Set 4th Parameter  
            pstmt.setString(5, courseName); // Set 5th Parameter
            pstmt.setString(6, numCredits); // Set 6th Parameter
            pstmt.setString(7, description); // Set 7th Parameter

            result = pstmt.executeQuery();
            
          //  System.out.println("Successful Insertion!" + courseName + " " + courseNumber + " was added to the COURSE table.");
        } 
        
        catch (SQLException e) 
        {
            System.out.println("Insertion failed. Could not add " + courseName + " " + courseNumber + " to the COURSE table." + e.getMessage() + sql);
		}
        
        return result; 
    }
    // End of addCourse() method 

	/**
	 * 2. Delete a Student's Advisee Record 
	 * 
     * Purpose: Delete a student's advisee record after they have switched out
     *          of this major. It is assumed that the student is currently attending
     *          the university and pursuing a Computer Science degree. 
     * 
     * User Group: Faculty Advisor of Student switching their major. 
	 * 
	 * @param studentIDNum -- The student's unique ID number. 
	 *
     * Result: The advising relationship data is deleted from the database. The 
     *         declared relationship is deleted from the database. The taking relationship
     *         is deleted from the database. If the operation is unsuccessful, an error
     *         message will be displayed.  
	 */
    public void deleteStudent(String studentID)
    { 
        String sql = null;
        ResultSet result = null; 

        try 
        {
			// Create a Statement and an SQL string for the statement
            sql = "DELETE FROM STUDENT " +
                  "WHERE IdNumber = ?"; 
                  
			PreparedStatement pstmt = conn.prepareStatement(sql);

			pstmt.clearParameters();
            pstmt.setString(1, studentID); // Set 1st Parameter

            result = pstmt.executeQuery();
            
            System.out.println("Successful Deletion! The student with ID: " + studentID + " has been deleted.");
        } 
        
        catch (SQLException e) 
        {
            System.out.println("Deletion failed. The student with ID: " + studentID + " still exists in the database." + e.getMessage());
		}
    } // End of deleteStudent() method

    ////////////////////////////
    ///// NEED TO FINISH 4 /////
    ////////////////////////////
	/**
	* 4. Show and replace CSCI 300+ Elective
	 * 
     * Purpose: List all courses that could replace this personâ€™s 300+ elective 
     *          based on their major and course already taken. A valid course is 
     *          then selected and replaces â€˜electiveâ€™ with the actual course info 
     *          (num, dept, title, etc.).
     * 
     * User Group: Student 
	 * 
     * @param IDNumber -- The student's ID number.
	 * @param courseTitle -- The name of the course.
	 * @param replaceTitle -- The name of replacement course
     * 
     * @return the number of tuples successfully replaced in the requires table
     * 
     * Note: We need to do an 'except' to remove the courses the student has 
     *       already taken. Essentially, we need to view a studentâ€™s required 
     *       courses (minus the ones theyâ€™ve already taken). From that table we 
     *       have created, we need to select a course title from one of the â€˜300+1â€™ 
     *       requires and replace it with the course info that matches the title.
	 * @throws SQLException 
	 */
    public int replace300Elective(String IDNumber, String courseTitle, String replaceTitle) throws SQLException
    {
      
    	
    	String firstState = "SELECT count (*)" +
    			"FROM student as s join requires as r on s.majorType = r.Type" +
    			"WHERE IdNumber = ? and WHERE r.reqCourse LIKE ‘%300+%’";
    	
    	PreparedStatement pstmt = conn.prepareStatement(firstState);
        pstmt.clearParameters();
        pstmt.setString(1, IDNumber); 
        ResultSet rset = pstmt.executeQuery();
        
    	int count = Integer.parseInt(rset.getString(1));
        
    	if (count > 0) {
    		String userReplace = "?";
    		
    		String secondState = "SELECT DISTINCT replacement" +
    				"FROM Replaces" +
    				"WHERE replaced LIKE" + userReplace;
    		
    	    pstmt = conn.prepareStatement(secondState);
            pstmt.clearParameters();
            pstmt.setString(2, replaceTitle); 
            ResultSet rset1 = pstmt.executeQuery();
            
            System.out.println(rset1.toString());

            String userReplacement = "?";
            
            String deleteState = "DELETE" + 
            		"FROM Requires" + 					
            		"WHERE reqCourse LIKE" + userReplace;
            
            pstmt = conn.prepareStatement(deleteState);
            pstmt.clearParameters();
            pstmt.setString(3, courseTitle); 
            ResultSet rset2 = pstmt.executeQuery();
            
            String addState  = "INSERT INTO Requires" +				
            "VALUES (" + userReplace.substring(0,1) + "," + userReplacement + ", (SELECT Year FROM Courses WHERE" + userReplacement + 
            " = Title), (SELECT Semester FROM Courses WHERE" + userReplacement + " = Title))";
            
            Statement stm = conn.createStatement();
            ResultSet rset3 = stm.executeQuery(addState);
            
    	} else {
    		System.out.println("There are no 300+ courses to replace!");
    	}
    	
    	return 0;
    }
    
	/**
	 * 5. Course Availability Look-Up
	 * 
     * Purpose: To display when a course is offered during the school year. 
     *          (EG. Fall, Spring, Odd, Even, Every).
     * 
     * User Groups: Student and Faculty 
	 * 
	 * @param courseNumber -- A composite attribute composed of the course number
     *                        and the department offering the course. 
     * @param department -- The department offering the course. 
     * 
     * @return ResultSet that displays the Title, Num, Dept, Semester, and Year based 
     *         off the courseNumber input. 
     *      
     * Result: The attributes of the course associated with courseNumber will be displayed. 
     *         If the operation is unsuccessful, an error message will be shown
     *         with the cause. 
	 */
    public ResultSet availableCourses(String courseNumber, String department)
    {
        ResultSet rset = null;
		String sql = null;

        try 
        {
			// Create a Statement and an SQL string for the statement
            sql = "SELECT Title, Num, Dept, Semester, Year" + 
                  "FROM COURSE " + 
                  "WHERE Num = ? AND Dept = ?" + 
                  "ORDER BY Semester";
		
			PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.clearParameters();
            pstmt.setString(1, courseNumber); // Set the 1st parameter
            pstmt.setString(2, department);
            
            rset = pstmt.executeQuery();
			
        } 
        catch (SQLException e) 
        {
			System.out.println("Could NOT execute availableCourses() " + e.getMessage() + sql);
		}
		
		return rset; 
    } // End of availableCourses() method

	/**
	 * 6. Show all advisees.
     * 
     * Purpose: List all students that this faculty member advises.
     * 
     * User Group: Faculty
     * 
	 * @param facultyID -- The Faculty member's unique ID number. 
     * 
	 * @return ResultSet that has fname, lname, IdNumber, gradYear, majorType,
     *         and advisorID for each student who this faculty member advises.  
	 */

    public ResultSet getAdvisees(String facultyID)
    {  
        ResultSet rset = null;
		String sql = null;

        try 
        {
			// Create a Statement and an SQL string for the statement
            sql = "SELECT s.FName, s.LName, s.IdNumber, s.GradYear, s.MajorType, s.AdvisorId" + 
                  "FROM Student s, Faculty f" + 
                  "WHERE s.AdvisorID = f.IdNumber and f.IdNumber = ?"; 

		
			PreparedStatement pstmt = conn.prepareStatement(sql);

			pstmt.clearParameters();
			pstmt.setString(1, facultyID); // Set the 1st parameter

			rset = pstmt.executeQuery();
			
        } 
        catch (SQLException e) 
        {
			System.out.println("Could NOT execute getAdvisees() " + e.getMessage() + sql);
		}
		
        return rset;
    } // End of getAdvisees() method

	/**
	 * 7. Edit a Report AFTER it has been generated
     * 
     * Purpose: Allow faculty to edit a graduation plan to their liking after 
     *          generating by adding, removing, or updating courses. These changes 
     *          will be saved to the current graduation plan and will not generate 
     *          a new  one.
	 * 
	 * User Group: Faculty
     *
	 * @param studentID -- The student's unique ID number.
     * @param courseTitle -- The title of the course.
     * @param cYear -- The year the course is offered.
     * @param currentSemester -- The semester the course is offered.
     * 
	 * Result: The report is altered to the user's specifications and saved. 
	 */
    public ResultSet editReport(String studentID, String courseTitle, String cYear, String currentSemester)
    {
        ResultSet rset = null;
        ResultSet rset2 = null;
        ResultSet rset3 = null; 
        String sql = null;
        String sql2 = null;
        String sql3 = null; 

        try 
        {
			// Create a Statement and an SQL string for the statement
            sql = "INSERT INTO TAKES" +
                  "VALUES (StudentID = ?, CourseT = ?, courseYear = ?, Csemester = ?)"; 
           
            sql2 = "DELETE FROM TAKES" + 
                    "WHERE StudentID = ? AND CourseT = ?";

            sql3 = "UPDATE TAKES" + 
                   "SET IF (TAKES.Csemester = 'F' OR TAKES.Csemester = 'S' or TAKES.Csemester = 'B')" +
                   "WHERE TAKES.Csemester IS NOT NULL";
            
            PreparedStatement pstmt = conn.prepareStatement(sql);
            PreparedStatement pstmt2 = conn.prepareStatement(sql2);
            PreparedStatement pstmt3 = conn.prepareStatement(sql3);

            // Set the Parameters for the INSERT
            pstmt.clearParameters();
            pstmt.setString(1, studentID);
            pstmt.setString(2, courseTitle);
            pstmt.setString(3, cYear);
            pstmt.setString(4, currentSemester);

            // Set the Parameters for the DELETE
            pstmt2.clearParameters();
            pstmt2.setString(1, studentID);
            pstmt2.setString(2, courseTitle);
            
            pstmt3.clearParameters();

            rset = pstmt.executeQuery();
            rset2 = pstmt2.executeQuery();
            rset3 = pstmt3.executeQuery();
        } 
        catch (SQLException e) 
        {
			System.out.println("Could NOT execute editReport() " + e.getMessage() + sql);
        }
        
        return rset3;
    } // End of editReport() method

    /**
	 * 8. View Courses
     * 
     * Purpose: View all of the available courses based on their Fall/Spring availability. 
     * 
     * User Groups: Student and Faculty. 
	 * 
	 * @return ResultSet that has the Title, Num, Dept, Semester, and Year for all
     *         available courses in the database. 
	 */
	public ResultSet viewCourses(String semester)
	{
		ResultSet rset = null;
		String sql = null;

        try 
        {
			// Create a Statement and an SQL string for the statement
			sql = "SELECT Title, Num, Dept, Semester, Year " + 
                  "FROM COURSE" +
                  "WHERE cSemester = ? OR WHERE cSemester = 'B'" +
                  "ORDER BY Semester"; 
            
			PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.clearParameters();
            pstmt.setString(1, semester);

			rset = pstmt.executeQuery();
        } 
        catch (SQLException e) 
        {
			System.out.println("Could NOT execute viewCourses() " + e.getMessage() + sql);
		}
		
		return rset; 
	} // End of viewCourses() method
	
	/**
	 * 9. View Grad Plan
     * 
     * Purpose: Allows a faculty member to view a specific student’s grad plan. 
     *          There is nothing changed, just shows the current grad plan for the student.
     * 
     * User Group: Faculty
     * 
     * @param studentID -- The student's unique ID number 
	 * 
	 * Result: Full grad plan with the list of classes that need to be completed, and 
	 *         and the list of classes that have already been taken(without duplicates).
	 */
	public ResultSet viewGradPlan(String studentID)
	{
		ResultSet rset = null;
		ResultSet rset2 = null; 
		String sql = null;
		String sql2 = null;

        try 
        {
			// Create a Statement and an SQL string for the statement
			sql = "SELECT * " + 
					"FROM STUDENT as s join REQUIRES as r on s.majorType = r.Type" + 
					"WHERE IdNumber = ?" + 
					"ORDER BY r.cSemester";
					
			sql2 =	"SELECT * " + 
					"FROM STUDENT as s join TAKES as t on s.IdNumber = t.StudentId" + 
					"WHERE s.IdNumber = ?" + 
					"ORDER BY r.Csemester"; 
            
			PreparedStatement pstmt = conn.prepareStatement(sql);
			PreparedStatement pstmt2 = conn.prepareStatement(sql2);

            pstmt.clearParameters();
            pstmt.setString(1, studentID);
            
            pstmt2.clearParameters();
            pstmt2.setString(1,  studentID);

			rset = pstmt.executeQuery();
			rset2 = pstmt2.executeQuery();
        } 
        catch (SQLException e) 
        {
			System.out.println("Could NOT execute viewCourses() " + e.getMessage() + sql);
		}
		
		return rset2; 
	} // End of viewGradPlan() method
	
	/**
	 * 10. Generate a New Grad Plan for a Student 
     * 
     * Purpose: A faculty member can create a new grad plan for a specified student.
     * 
     * User Group: Faculty
     * 
     * @param studentID -- The student's unique ID number 
	 * 
	 * Result: Newly generated grad plan for a new CS student.
	 * 
	 * Note: Assumes the student has already been added. 
	 */
	public ResultSet createGradPlan(String studentID)
	{
		ResultSet rset = null;
		String sql = null;

        try 
        {
			// Create a Statement and an SQL string for the statement
			sql = "SELECT * " + 
					"FROM STUDENT as s join REQUIRES as r on s.majorType = r.Type" + 
					"WHERE IdNumber = ?" + 
					"ORDER BY r.cSemester"; 
            
			PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.clearParameters();
            pstmt.setString(1, studentID);

			rset = pstmt.executeQuery();
        } 
        catch (SQLException e) 
        {
			System.out.println("Could NOT execute viewCourses() " + e.getMessage() + sql);
		}
		
		return rset; 
	} // End of createGradPlan() method

}// Utilities class
