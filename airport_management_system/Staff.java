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

public class Staff extends User{
	private int age;
	private char gender;
	private Airline airline;
	private City homeCity;
	public static int noOfStaff;
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
	public Airline getAirline() {
		return airline;
	}
	public void setAirline(Airline airline) {
		this.airline = airline;
	}
	public City getHomeCity() {
		return homeCity;
	}
	public void setHomeCity(City homeCity) {
		this.homeCity = homeCity;
	}
	Staff(String name,String pwd,int age,char gender,String airline,String homecity){
		super(name,pwd);
		this.age = age;
		this.gender = gender;
		setno();
		this.setId(noOfStaff+1);
		Airline a = new Airline(airline);
		this.setAirline(a);
		City hc;
		hc = City.fromCode(homecity);
		this.setHomeCity(hc);
	}
	Staff(String fromTxt){
		super(fromTxt.split(",")[0],fromTxt.split(",")[1],fromTxt.split(",")[2]);
		this.age = Integer.parseInt(fromTxt.split(",")[3]);
		this.gender = fromTxt.split(",")[4].charAt(0);
		this.airline = new Airline(fromTxt.split(",")[5]);
		this.homeCity = City.fromCode(fromTxt.split(",")[6]);
	}
	public static void setno(){
		try {
			Scanner output = new Scanner(new FileInputStream("staff.txt"));
			int n=0;
			while(output.hasNextLine()) {
				String s = output.nextLine();
				String[] s1 = s.split(",");
				if(Integer.parseInt(s1[0])==0)
					continue;
				n++;
			}
			noOfStaff=n;
			output.close();
		} catch (FileNotFoundException e) {
			System.err.println("File Cannot Be Opened");
			System.exit(1);
		}
	}
	public void append() {
		try {
			String reg = this.getId() + "," + this.getName() + ","  + this.getPwd() + "," + this.age + "," + this.gender + "," + this.airline.getCode()+","+this.homeCity.getCode();
			PrintWriter p = new PrintWriter(new FileOutputStream("staff.txt",true));
			p.print("\n"+reg);
			p.close();
		} catch (FileNotFoundException e) {
			System.err.println("Error Opening FIle");
			System.exit(1);
		}
	}
	public static void register() {
		System.out.print("\nStaff Registration ");
		System.out.print("\nEnter Name: ");
		Scanner scn = new Scanner(System.in);
		String name = scn.nextLine();
		System.out.print("Select a Password: ");
		String pwd = scn.nextLine();
		System.out.print("Enter Your Age: ");
		int age = scn.nextInt();
		scn.nextLine();
		System.out.print("Enter Your Gender(M/F/N): ");
		char gender = scn.nextLine().charAt(0);
		System.out.print("Enter Airline Code: ");
		String airline = scn.nextLine();
		String homecity = "DLI";
		Staff s1 = new Staff(name,pwd,age,gender,airline,homecity);
		System.out.println("Their ID is "+s1.getId());
		s1.append();
		System.out.println("Registered!");
	}	
	public static void login() {
		Scanner scn = new Scanner(System.in);
		
		int trial = 1;
		char flag = 'n';
		String staff=null;
		while(trial<=3)
		{
			System.out.print("\nLogin Page ");
			System.out.print("\nEnter ID: ");
			String id  = scn.nextLine();
			System.out.print("Enter Password: ");
			String pwd = scn.nextLine();
			try {
				Scanner output = new Scanner(new FileInputStream("staff.txt"));
				while(output.hasNextLine()) {
					String str = output.nextLine();
					String[] str1 = str.split(",");
					if(Integer.parseInt(str1[0])==0)
						continue;
					if(str1[0].equals(id) && str1[2].equals(pwd)) {
						flag = 'p';
						staff = str;
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
		Staff s = new Staff(staff);
		s.loginMenu();
	}
	public void loginMenu() {
		while(true)
		{   
			int choice;
			Scanner scn = new Scanner(System.in);
			System.out.println("\nStaff Menu");
			System.out.println("1.Check In Passenger");
			System.out.println("2.View Boarding Pass");
			System.out.println("3.View Flights");
			System.out.println("4.Edit Profile");
			System.out.println("0.Log Out");
			System.out.print("Enter Choice: ");
			choice=scn.nextInt();
			char logout = 'n';
			switch(choice)
			{
				case 1:
					this.checkin();
					break;
				case 2:
					this.viewBoardingPass();
					break;
				case 3:
					this.viewFlights();
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
	public void checkin() {
		Booking b = null;
		Scanner scn = new Scanner(System.in);
		System.out.print("\nEnter Customer ID: ");	String c_id = scn.nextLine();
		System.out.print("Enter Name: ");	String name = scn.nextLine();
		System.out.print("Enter Aadhar No(XXXX-XXXX-XXXX): ");	String aadharNo = scn.nextLine();
		System.out.print("Enter Flight ID(Last Digit Only): ");	
		String f_code = this.airline.getCode()+" "+scn.nextLine();		
		System.out.print("Enter Departure Date(DD/MM/YYYY): ");	String depDate = scn.nextLine();
		String depCity = this.homeCity.getCode();
		System.out.print("Enter Destination City Code: ");	String destCity = scn.nextLine();
		b = verifyBooking(c_id,name,aadharNo,f_code,depDate,depCity,destCity);
		if(b!=null) {
			createBoardingPass(b);
		}
		if(b==null) {
			System.out.println("No Such Booking Exists.");
		}
	}
	public Booking verifyBooking(String c_id,String name,String aadharNo,String f_code,String depDate,String depCity,String destCity) {
		Booking b = null;
		try {
			Scanner output = new Scanner(new FileInputStream("booking.txt"));
			while(output.hasNextLine()) {
				String str = output.nextLine();
				String[] s = str.split(",");
				if(Integer.parseInt(s[0])==0)
					continue;
				if(s[0].equals(c_id)&&s[1].equals(name)&&s[4].equals(aadharNo)&&s[5].equals(f_code)&&s[6].equals(depDate)&&s[7].equals(depCity)&&s[8].equals(destCity)){
					b = new Booking(str);
				}
			}
			output.close();
		} catch (FileNotFoundException e) {
			System.err.println("File Cannot Be Opened");
			System.exit(1);
		}
		return b;
	}
	public void viewBoardingPass() {
		Scanner scn = new Scanner(System.in);
		System.out.print("\nEnter Customer ID: ");
		String id= scn.nextLine();
		System.out.print("Enter Passenger Name: ");
		String name= scn.nextLine();
		System.out.print("Enter Departure Date(DD/MM/YYYY): ");
		String depDate= scn.nextLine();
		BoardingPass bp = null;
		try {
				Scanner output = new Scanner(new FileInputStream("boardingpass.txt"));
				while(output.hasNextLine()) {
					String s = output.nextLine();
					String[] str = s.split(",");
					if(str[0].equals(id)&&str[1].equals(name)&&str[3].equals(depDate)) {
						bp = new BoardingPass(s);
						bp.display();
					}
				}
				output.close();
		} catch (FileNotFoundException e) {
			System.err.println("File Cannot Be Opened");
			System.exit(1);
		}
	}
	public void createBoardingPass(Booking b) {
		Scanner scn = new Scanner(System.in);
		System.out.print("Enter Baggage Weight: ");	float bgg = scn.nextFloat();	scn.nextLine();
		int baggPrice;
		if(bgg<=8)
			baggPrice = 0;
		else if(bgg<=12)
			baggPrice = (b.getFl().getFare()/20) * ((int)bgg-8);
		else if(bgg<=25)
			baggPrice = (b.getFl().getFare()/20)*(4)+(b.getFl().getFare()/10) * ((int)bgg-12);
		else if(bgg<=50)
			baggPrice = (b.getFl().getFare()/20)*(4)+(b.getFl().getFare()/10) * (13)+(b.getFl().getFare()/8) * ((int)bgg-25);
		else
			baggPrice = (b.getFl().getFare()/8) * ((int)bgg);
		int seatno = b.getFl().getSeatCounter();
		seatno++;
		b.getFl().incSeatCounter();
		String seat = String.valueOf((seatno/7)+1) + (char)(seatno%7+64);
		BoardingPass bp= new BoardingPass(b.getPass(),b.getFl(),bgg,baggPrice,seat);
		bp.append();
		System.out.println("Boarding Pass Created!");
		bp.display();
		System.out.println("Your Baggage Charge is: "+bp.getBaggageCharge());
	}
	public void viewFlights() {
		System.out.print("\n"+this.airline.getName()+" Airlines");
		Flight f;
		try {
			Scanner scn = new Scanner(new FileInputStream("flight.txt"));
			while(scn.hasNextLine()) {
				String f1 = scn.nextLine();
				String[] f2 = f1.split(",");
				if(f2[0].split(" ")[0].equals(this.airline.getCode())){
					f = new Flight(f1);
					f.display();
				}
			}
			scn.close();
		} catch (FileNotFoundException e) {
			System.err.println("Error Opening FIle");
			System.exit(1);
		}
		
	}
	public void editProfile() {
		Scanner inputStream = null;
		Staff s=null;
		try {
				inputStream = new Scanner(new FileInputStream("staff.txt"));
		}
		catch(FileNotFoundException e){
			System.exit(0);
		}
		PrintWriter outputStream = null;
		try {
			outputStream = new PrintWriter(new FileOutputStream("temporaryStaff.txt"));
		}
		catch(FileNotFoundException e){
			System.exit(0);
		}
	    
		while(inputStream.hasNextLine()) {
	    	String []staff_data;
	    	String currentLine = inputStream.nextLine();
	   		staff_data = currentLine.split(","); 	
	   		if(Integer.parseInt(staff_data[0])==0) {
	   			outputStream.print(currentLine);
				continue;
			}
	    	if(this.getId()==Integer.parseInt(staff_data[0])) {
	    		s = new Staff(currentLine);
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
		} 
		catch (IOException e) {
			System.err.println("Error Replacing File");
			System.exit(1);
		}    	
		int choice;
		Scanner scn = new Scanner(System.in);
		System.out.println("\nEdit Profile");
		System.out.println("1.Name");
		System.out.println("2.Age");
		System.out.println("3.Gender");
		System.out.println("4.Password");
		System.out.print("\nEnter Choice: ");
		choice=scn.nextInt();
		scn.nextLine();
		switch(choice)
		{
			case 1:
				System.out.print("\nEnter Name: ");
				s.setName(scn.nextLine());
				break;
			case 2:
				System.out.print("\nEnter Age: ");
				s.setAge(scn.nextInt());
				scn.nextLine();
				break;
			case 3:
				System.out.print("\nEnter Gender(M/F/N): ");
				s.setGender(scn.nextLine().charAt(0));
				break;
			case 4:
				System.out.print("\nEnter New Password: ");
				s.setPwd(scn.nextLine());
				break;
			default:
				System.out.println("Invalid Input");
		}
		s.append();
		
	}
	public static void displayAll(){
		try {
				Scanner output = new Scanner(new FileInputStream("staff.txt"));
				while(output.hasNextLine()) {
					String s = output.nextLine();
					String[] s1 =s.split(",");
					if(Integer.parseInt(s1[0])==0)
						continue;
					
					System.out.println("ID: "+s1[0]+"\tName: "+s1[1]+"\tPassword: "+s1[2]+"\tAge: "+s1[3]+"\tGender: "+s1[4]+"\tAirLine Code: "+s1[5]+"\tHomeCity Code: "+s1[6]);
				}
				output.close();
		} catch (FileNotFoundException e) {
			System.err.println("File Cannot Be Opened");
			System.exit(1);
		}
	}

}
