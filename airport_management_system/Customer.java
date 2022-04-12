package airport_management_system;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Scanner;

public class Customer extends User {
	int age;
	char gender;
	String aadharNo;
	static int noOfCust;
	Customer(String name,String pwd,int age,char gender,String aadharNo){
		super(name,pwd);
		this.age = age;
		this.gender = gender;
		this.aadharNo= aadharNo;
		setno();
		this.setId(noOfCust+1); 
	}
	Customer(String fromTxt){
		super(fromTxt.split(",")[0],fromTxt.split(",")[1],fromTxt.split(",")[2]);
		this.age = Integer.parseInt(fromTxt.split(",")[3]);
		this.gender = fromTxt.split(",")[4].charAt(0);
		this.aadharNo = fromTxt.split(",")[5];
	}
	Customer(){
		
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public char getGender() {
		return gender;
	}
	public void setGender(char gender) {
		this.gender = gender;
	}
	public String getAadharNo() {
		return aadharNo;
	}
	public void setAadharNo(String aadharNo) {
		this.aadharNo = aadharNo;
	}
	public static void setno(){
		try {
			Scanner output = new Scanner(new FileInputStream("customer.txt"));
			int n=0;
			String s;
			while(output.hasNextLine()) {
				s = output.nextLine();
				String[] s1 = s.split(",");
				if(Integer.parseInt(s1[0])==0)
					continue;
				n++;
			}
			noOfCust=n;
			output.close();
		} catch (FileNotFoundException e) {
			System.err.println("File Cannot Be Opened");
			System.exit(1);
		}
	}
	public void append() {
		try {
			String reg = this.getId() + "," + this.getName() + ","  + this.getPwd() + "," + this.age + "," + this.gender + "," + this.aadharNo;
			PrintWriter p = new PrintWriter(new FileOutputStream("customer.txt",true));
			p.print("\n"+reg);
			p.close();
		} catch (FileNotFoundException e) {
			System.err.println("Error Opening FIle");
			System.exit(1);
		}
	}
	public static void register() {
		System.out.print("\nCustomer Registration ");
		System.out.print("\nEnter Name: ");
		Scanner scn = new Scanner(System.in);
		String name = scn.nextLine();
		System.out.print("Select a Password: ");
		String pwd = scn.nextLine();
		System.out.print("Enter Your Age(int): ");
		int age = scn.nextInt();
		scn.nextLine();
		System.out.print("Enter Your Gender(M/F/N): ");
		char gender = scn.nextLine().charAt(0);
		System.out.print("Enter Aadhar No(XXXX-XXXX-XXXX): ");
		String aadharNo = scn.nextLine();
		Customer c1 = new Customer(name,pwd,age,gender,aadharNo);
		System.out.println("Your ID is "+c1.getId());
		c1.append();
		System.out.println("Registered!");
	}	
	public static void login() {
		Scanner scn = new Scanner(System.in);
		
		int trial = 1;
		char flag = 'n';
		String cust=null;
		while(trial<=3)
		{
			System.out.print("\nLogin Page ");
			System.out.print("\nEnter ID: ");
			String id  = scn.nextLine();
			System.out.print("Enter Password: ");
			String pwd = scn.nextLine();
			try {
				Scanner output = new Scanner(new FileInputStream("customer.txt"));
				while(output.hasNextLine()) {
					String str = output.nextLine();
					String[] str1 = str.split(",");
					if(Integer.parseInt(str1[0])==0)
						continue;
					if(str1[0].equals(id) && str1[2].equals(pwd)) {
						flag = 'p';
						cust = str;
					}
				}
				if(flag=='n')
					System.out.println("Incorrect ID or Password");
				output.close();
			} catch (FileNotFoundException e) {
				System.err.println("File Cannot Be Opened");
				System.exit(1);
			}
			trial++;	
			if(flag=='p')
				break;
		}
		if(flag=='n') {
			System.err.println("Tries Over");
			scn.close();
			System.exit(1);
		}
		Customer c = new Customer(cust);
		c.loginMenu();
	}
	public void loginMenu() {
		while(true)
		{   
			int choice;
			Scanner scn = new Scanner(System.in);
			System.out.println("\nCustomer Menu");
			System.out.println("1.Search Flight");
			System.out.println("2.View Bookings");
			System.out.println("3.Cancel Booking");
			System.out.println("4.Edit Profile");
			System.out.println("0.Log Out");
			System.out.print("Enter Choice: ");
			choice=scn.nextInt();
			char logout = 'n';
			switch(choice)
			{
				case 1:
					Scanner scr = new Scanner(System.in);
					ArrayList<Flight> flights = new ArrayList<Flight>(searchFlight());
					ListIterator<Flight> itr = flights.listIterator();
					if(!(itr.hasNext())) {
						System.out.println("No Flights Available");
						break;
					}	
					System.out.print("Available Flights: ");
					while(itr.hasNext()) {
						System.out.println(itr.next().getCode());
					}
					while(true) {
						char flag2='n';
						System.out.print("\nDo You Want To View Details?(Y/N): ");
						char ans = scr.nextLine().charAt(0);
						if(ans!='Y'&&ans!='y')
							break;
						System.out.print("Enter Flight Code: ");
						String fc = scr.nextLine();
						ListIterator<Flight> itr2 = flights.listIterator();
						while(itr2.hasNext()) {
							Flight f2 = itr2.next();
							if(f2.getCode().equals(fc)){
								this.viewFlightDetails(f2);
								flag2 = 'p';
							}
						}
						if(flag2=='n')
							System.out.println("Enter Valid Flight Code");
						if(flag2=='p')
							break;
					}
					break;
				case 2:
					this.viewBookings();
					break;
				case 3:
					this.cancelBooking();
				 	break;
				case 4:
					this.editProfile();
					break;
				case 0:
					System.out.println("Logging Out");
					scn.nextLine();
					logout = 'y';
					break;
				default:
					System.out.println("Invalid Input");
			}
			if(logout=='y')
				break;
		}
	
	}
	public static ArrayList<Flight> searchFlight(){
		Scanner scn = new Scanner(System.in);
		System.out.print("\nSearch Flights");
		System.out.print("\nEnter Source City Name: ");
		String srccity = scn.nextLine();
		System.out.print("Enter Destination City Name: ");
		String destcity = scn.nextLine();
		System.out.print("Enter Departure Date(DD/MM/YYYY): ");
		String depdate = scn.nextLine();
		System.out.print("Enter No of Seats: ");
		int sn = scn.nextInt();
		scn.nextLine();
		ArrayList<Flight> flights = new ArrayList<Flight>();
		try {
			Scanner output = new Scanner(new FileInputStream("flight.txt"));
			while(output.hasNextLine()) {
				String s = output.nextLine();
				String[] s1 = s.split(",");
				if(s1[0].equals("no"))
					continue;
				City c1 = new City(srccity);
				City c2 = new City(destcity);
				if(s1[2].equals(c1.getCode())&&s1[3].equals(c2.getCode())&&s1[1].equals(depdate)&&(Integer.parseInt(s1[7])>=sn)) {
					Flight f = new Flight(s);
					flights.add(f);
				}
			}
			output.close();
		} catch (FileNotFoundException e) {
			System.err.println("File Cannot Be Opened");
			System.exit(1);
		}
		return flights;
	}
	public void viewFlightDetails(Flight x) {
		x.display();
		Scanner scr = new Scanner(System.in);
		System.out.print("\nDo You Want To Book Ticktets?(Y/N): ");
		char ans = scr.nextLine().charAt(0);
		if(ans=='y'||ans=='Y') 
			bookTicket(x);
	}
	public void bookTicket(Flight x) {
		Scanner scr = new Scanner(System.in);
		System.out.print("\nEnter No of Passengers: ");
		int n = scr.nextInt();
		scr.nextLine();
		for(int i=0;i<n;i++) {
			int j = i+1;
			System.out.print("\nEnter Passenger "+j+"'s Name: ");	String name = scr.nextLine();
			System.out.print("Enter Passenger's Age: ");	int age = scr.nextInt();	scr.nextLine();
			System.out.print("Enter Passenger's Gender(M/F/N): ");	char gender = scr.nextLine().charAt(0);
			System.out.print("Enter Passenger's Aadhar No(XXXX-XXXX-XXXX): ");	String aadharNo = scr.nextLine();
			int id = this.getId();
			Passenger p = new Passenger(id,name,age,gender,aadharNo);
			Booking b = new Booking(this,x,p);
			b.append();
			x.reduceSeat();
		}
		System.out.println("Booking Confirmed!");
		System.out.println("You have to pay: "+n*x.getFare());
	}
	public void viewBookings() {;
		Booking.view(this.getId());
	}
	public void cancelBooking() {
		Scanner scn = new Scanner(System.in);
		System.out.print("\nEnter Flight ID: ");
		String flight_id = scn.nextLine();
		System.out.print("Enter Passenger Name: ");
		String passengerName = scn.nextLine();
		System.out.print("Enter Departure Date(DD/MM/YYYY): ");
		String departureDate = scn.nextLine();
		System.out.print("Enter Departure City Code: ");
		String departureCity = scn.nextLine();
		System.out.print("Enter Desination City Code: ");
		String destCity = scn.nextLine();
		Booking.remove(this.getId(),flight_id,passengerName,departureDate,departureCity,destCity);
		
	}
	public void editProfile() {
		Scanner inputStream = null;
		Customer c=null;
		try {
				inputStream = new Scanner(new FileInputStream("customer.txt"));
		}
		catch(FileNotFoundException e){
			System.err.println("Error Opening File");
			System.exit(1);
		}
		PrintWriter outputStream = null;
		try {
			outputStream = new PrintWriter(new FileOutputStream("temporaryCustomer.txt"));
		}
		catch(FileNotFoundException e){
			System.err.println("Error Opening File");
			System.exit(1);
		}
	    
		while(inputStream.hasNextLine()) {
	    	String currentLine = inputStream.nextLine();
	   		String[] cust_data = currentLine.split(",");
	   		if(Integer.parseInt(cust_data[0])==0) {
				outputStream.print(currentLine);
	   			continue;
	   		}
	    	if(this.getId()==Integer.parseInt(cust_data[0])) {
	    		c = new Customer(currentLine);
	    		continue;
	   		}  	
	    	outputStream.print("\n"+currentLine);
	   	} 
		outputStream.close();
		inputStream.close();
		File source = new File("temporaryCustomer.txt");
		File dest = new File("customer.txt");
		try {
			Files.move(source.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
		} 
		catch (IOException e) {
			System.err.println("Error Replacing File");
			System.err.println(e);
			
			System.exit(1);
		}    	
		int choice;
		Scanner scn = new Scanner(System.in);
		System.out.println("\nEdit Profile");
		System.out.println("1.Name");
		System.out.println("2.Age");
		System.out.println("3.Gender");
		System.out.println("4.Aadhar No");
		System.out.println("5.Password");
		System.out.print("Enter Choice: ");
		choice=scn.nextInt();
		scn.nextLine();
		switch(choice)
		{
			case 1:
				System.out.print("\nEnter Name: ");
				c.setName(scn.nextLine());
				break;
			case 2:
				System.out.print("Enter Age: ");
				c.setAge(scn.nextInt());
				scn.nextLine();
				break;
			case 3:
				System.out.print("Enter Gender(M/F/N): ");
				c.setGender(scn.nextLine().charAt(0));
				break;
			case 4:
				System.out.print("Enter Aadhar Number(XXXX-XXXX-XXXX): ");
				c.setAadharNo(scn.nextLine());			
				break;
			case 5:
				System.out.print("Enter New Password: ");
				c.setPwd(scn.nextLine());
				break;
			default:
				System.out.println("Invalid Input");
		}
		c.append();	
		System.out.println("Profile Updated.");
	}
	public static Customer fromFile(int id) {
		Customer c=null;
		try {
			Scanner sc = new Scanner(new FileInputStream("customer.txt"));
			while(sc.hasNextLine()) {
				String str = sc.nextLine();
				if(Integer.parseInt(str.split(",")[0])==0)
					continue;
				if(Integer.parseInt(str.split(",")[0])==id) {
					c = new Customer(str); 
				}
			}
			sc.close();
		} catch (FileNotFoundException e) {
			System.err.println("Error Opening File");
			System.exit(1);
		}
		return c;
	}
	public static void displayAll(){
		try {
				Scanner output = new Scanner(new FileInputStream("customer.txt"));
				while(output.hasNextLine()) {
					String s = output.nextLine();
					String[] s1 =s.split(",");
					if(Integer.parseInt(s1[0])==0)
							continue;
					System.out.println("ID: "+s1[0]+"\tName: "+s1[1]+"\tPassword: "+s1[2]+"\tAge: "+s1[3]+"\tGender: "+s1[4]+"\tAadhar No: "+s1[5]);
				}
				output.close();
		} catch (FileNotFoundException e) {
			System.err.println("File Cannot Be Opened");
			System.exit(1);
		}
	}
}
