package airport_management_system;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Scanner;

public class Admin {
	public static void login() {
		Scanner sc = new Scanner(System.in);
		System.out.print("\nLogin Page\n");
		int trial = 1;
		char flag = 'n';
		while(trial<=3)
		{
			System.out.print("Enter Password: ");
			String pwd = sc.nextLine();
			if(pwd.equals("admin")) {
				flag = 'p';
				break;
			}
			if(flag=='n')
				System.out.println("Incorrect ID or Password");
			trial++;
			
		}
		if(flag=='n') {
			System.err.println("Tries Over");
			sc.close();
			System.exit(1);
		}
		Admin.loginMenu();
	}
	public static void loginMenu() {
		while(true)
		{   
			int choice;
			Scanner scan = new Scanner(System.in);
			System.out.println("\nAdmin Menu");
			System.out.println("1.Register Staff");
			System.out.println("2.New Flight");
			System.out.println("3.View City Details");
			System.out.println("4.View Airline Details");
			System.out.println("5.View Customer Details");
			System.out.println("6.View Staff Details");
			System.out.println("7.View Flight Details");
			System.out.println("8.View Booking Details");
			System.out.println("9.View Boarding Pass Details");
			System.out.println("10.Remove Flight");
			System.out.println("11.Remove Customer");
			System.out.println("12.Remove Staff");
			System.out.println("0.Log Out");
			System.out.print("Enter Choice: ");
			choice=scan.nextInt();
			char logout = 'n';
			switch(choice)
			{
				case 1:
					Staff.register();
			 		break;
				case 2:
					newFlight();
			 		break;
				case 3:
					viewCityDetails();
				 	break;
				case 4:
					viewAirlineDetails();
			 		break;
				case 5:
					viewCustomerDetails();
			 		break;
				case 6:
					viewStaffDetails();
				 	break;
				case 7:
					viewFlightDetails();
				 	break;
				case 8:
					viewBookingDetails();
			 		break;
				case 9:
					viewBoardingPassDetails();
			 		break;
				case 10:
					removeFlight();
				 	break;
				case 11:
					removeCustomer();
				 	break;
				case 12:
					removeStaff();
					break;
				case 0:
					System.out.println("Logging Out");
					scan.nextLine();
					logout = 'y';
					break;
				default:
					System.out.println("Invalid Input");
			}
			if(logout=='y')
				break;
		}
	}
	public static void newFlight() {
		Scanner sc = new Scanner(System.in);
		System.out.print("\nNew Flight Details ");
		System.out.print("\nEnter Airline Code: ");
		String code = sc.nextLine();
		System.out.print("Enter Departure Date(DD/MM/YYYY): ");
		String depdate = sc.nextLine();
		System.out.print("Enter Departure Time(HH:MM): ");
		String deptime = sc.nextLine();
		System.out.print("Enter Departure City Code: ");
		String depcity = sc.nextLine();
		System.out.print("Enter Destination City Code: ");
		String destcity = sc.nextLine();
		System.out.print("Enter Arrival Time(HH:MM): ");
		String arrtime = sc.nextLine();
		System.out.print("Enter Gate: ");
		int gate = sc.nextInt();
		sc.nextLine();
		Flight f1 = new Flight(code,depdate,depcity,destcity,deptime,arrtime,gate);
		f1.append();
		System.out.println("Flight Added");
	}
	public static void viewCityDetails() {
		City.displayAll();
	}
	public static void viewAirlineDetails(){
		Airline.displayAll();
	}
	public static void viewCustomerDetails() {
		Customer.displayAll();
	}
	public static void viewStaffDetails() {
		Staff.displayAll();
	}
	public static void viewFlightDetails() {
		Flight.displayAll();
	}
	public static void viewBookingDetails() {
		Booking.displayAll();
	}
	public static void viewBoardingPassDetails() {
		BoardingPass.displayAll();
		
	}
	public static void removeFlight() {
		Scanner scn = new Scanner(System.in);
		System.out.print("Enter Flight ID: ");
		String flight_id = scn.nextLine();
		System.out.print("Enter Departure Date(DD/MM/YYYY): ");
		String flight_departureDate = scn.nextLine();
		System.out.print("Enter Departure City Code: ");
		String flight_departureCity = scn.nextLine();
		System.out.print("Enter Destination City Code: ");
		String flight_destCity = scn.nextLine();
		
			Scanner inputStream = null;
			try {
					inputStream = new Scanner(new FileInputStream("flight.txt"));
			}
			catch(FileNotFoundException e){
				System.err.println("File Cannot Be Opened");
				System.exit(1);
			}
			
			PrintWriter outputStream = null;
			try {
				outputStream = new PrintWriter(new FileOutputStream("temporaryFlight.txt"));
			}
			catch(FileNotFoundException e){
				System.err.println("File Cannot Be Opened");
				System.exit(1);
			}
			
    		
			while(inputStream.hasNextLine()) {
				
		    	String []flight_data;
		    	String currentLine = inputStream.nextLine();
		    	flight_data = currentLine.split(",");
		    	if(flight_data[0].equals("no")) {
					outputStream.print(currentLine);
		    		continue;
		    	}
		    	if((flight_id.equals(flight_data[0]))&&(flight_departureDate.equals(flight_data[1]))&&(flight_departureCity.equals(flight_data[2]))&&(flight_destCity.equals(flight_data[3]))) {
		    		continue;
		    	}
		    	outputStream.print("\n"+currentLine);	
		    }
			inputStream.close();
			outputStream.close();
			File source = new File("temporaryFlight.txt");
			File dest = new File("flight.txt");
			try {
				Files.move(source.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				System.err.println("File Cannot Be Opened");
				System.exit(1);
			}	
			System.out.println("Flight Removed!");
	}
	public static void removeCustomer() {
		Scanner scn = new Scanner(System.in);
		System.out.print("Enter Customer ID: ");
		String cust_id = scn.nextLine();
		
			Scanner inputStream = null;
			try {
					inputStream = new Scanner(new FileInputStream("customer.txt"));
			}
			catch(FileNotFoundException e){
				System.err.println("File Cannot Be Opened");
				System.exit(1);
			}
			
			PrintWriter outputStream = null;
			try {
				outputStream = new PrintWriter(new FileOutputStream("temporaryCustomer.txt"));
			}
			catch(FileNotFoundException e){
				System.err.println("File Cannot Be Opened");
				System.exit(1);
			}
			
    		
			while(inputStream.hasNextLine()) {
				
		    	String []cust_data = new String[6];
		    	String currentLine = inputStream.nextLine();
		    	cust_data = currentLine.split(",");
		    	if(Integer.parseInt(cust_data[0])==0) {
					outputStream.print(currentLine);
		    		continue;
		    	}	
		    	if(cust_id.equals(cust_data[0])) {
		    		continue;
		    	}
		    	
		    	outputStream.print("\n"+currentLine);
			    	
		    }
			
			inputStream.close();
			outputStream.close();
			File source = new File("temporaryCustomer.txt");
			File dest = new File("customer.txt");
			try {
				Files.move(source.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				System.err.println("File Cannot Be Opened");
				System.exit(1);
			}
			System.out.println("Customer Removed!");
	}
	public static void removeStaff() {
		Scanner scn = new Scanner(System.in);
		System.out.print("Enter staff ID: ");
		String staff_id = scn.nextLine();
		
			Scanner inputStream = null;
			try {
					inputStream = new Scanner(new FileInputStream("staff.txt"));
			}
			catch(FileNotFoundException e){
				System.err.println("File Cannot Be Opened");
				System.exit(1);
			}
			
			PrintWriter outputStream = null;
			try {
				outputStream = new PrintWriter(new FileOutputStream("temporaryStaff.txt"));
			}
			catch(FileNotFoundException e){
				System.err.println("File Cannot Be Opened");
				System.exit(1);
			}
			
    		
			while(inputStream.hasNextLine()) {
				
		    	String []staff_data = new String[7];
		    	String currentLine = inputStream.nextLine();
		    	staff_data = currentLine.split(",");
		    	if(Integer.parseInt(staff_data[0])==0) {
					outputStream.print(currentLine);
		    		continue;
		    	}
		    	if(staff_id.equals(staff_data[0])) {
		    		continue;
		    	}
		    	
		    	outputStream.print("\n"+currentLine);
			    	
		    }
			
			inputStream.close();
			outputStream.close();
			File source = new File("temporaryStaff.txt");
			File dest = new File("staff.txt");
			try {
				Files.move(source.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				System.err.println("File Cannot Be Opened");
				System.exit(1);
			}
			System.out.println("Staff Removed!");
	}
}
