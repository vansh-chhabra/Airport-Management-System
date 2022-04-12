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

public class Booking {
	private Customer cust;
	private Flight fl;
	private Passenger pass;
	public Customer getCust() {
		return cust;
	}
	Booking(Customer cust,Flight fl,Passenger pass){
		this.cust = cust;
		this.fl = fl;
		this.pass = pass;
	}
	public void setCust(Customer cust) {
		this.cust = cust;
	}
	public Flight getFl() {
		return fl;
	}
	public void setFl(Flight fl) {
		this.fl = fl;
	}
	public Passenger getPass() {
		return pass;
	}
	public void setPass(Passenger pass) {
		this.pass = pass;
	}
	public void append() {
		try {
			PrintWriter p = new PrintWriter(new FileOutputStream("booking.txt",true));
			String str = this.cust.getId()+","+this.pass.getName()+","+this.pass.getAge()+","+this.pass.getGender()+","+this.pass.getAadharNo()+","+this.fl.getCode()+","+this.fl.getDepartureDate()+","+this.fl.getDepartureCity().getCode()+","+this.fl.getDestCity().getCode(); 
			p.print("\n"+str);
			p.close();
		} catch (FileNotFoundException e) {
			System.err.println("Error Opening FIle");
			System.exit(1);
		}
	}
	Booking(String fromtxt){
		String[] s = fromtxt.split(",");
		this.cust = Customer.fromFile(Integer.parseInt(s[0]));
		this.fl = Flight.fromFile(s[5],s[6],s[7],s[8]);
		this.pass = new Passenger(Integer.parseInt(s[0]),s[1],Integer.parseInt(s[2]),s[3].charAt(0),s[4]);
	}
	public void display() {
		System.out.println("\nName :"+pass.getName()+"\tAge: "+pass.getAge()+"\tGender: "+pass.getGender());
		System.out.println("Flight Code :"+fl.getCode()+"\tDeparture Date: "+fl.getDepartureDate());
		System.out.println("Departure From :"+fl.getDepartureCity().getName()+"\tTo: "+fl.getDestCity().getName());
		System.out.println("Departure At :"+fl.getDepartureTime()+"\tArrival Time: "+fl.getArrivalTime());
	}
	public static void view(int id) {
		try {
			Scanner output = new Scanner(new FileInputStream("booking.txt"));
			if(!output.hasNextLine())
				System.out.println("No Bookings Yet!");
			while(output.hasNextLine()) {
				String str = output.nextLine();
				if(Integer.parseInt(str.split(",")[0])==0)
					continue;
				if(Integer.parseInt(str.split(",")[0])==id) {
					Booking b = new Booking(str);
					b.display();
				}		
			}
			output.close();
		} catch (FileNotFoundException e) {
			System.err.println("File Cannot Be Opened");
			System.exit(1);
		}
	}
	public static void remove(int c_id,String f_id,String passName,String depdate,String depCity,String destCity) {
		Scanner inputStream = null;
		char flag = 'n';
		try {
				inputStream = new Scanner(new FileInputStream("booking.txt"));
		}
		catch(FileNotFoundException e){
			System.err.println("Error Opening FIle");
			System.exit(1);
		}
		
		PrintWriter outputStream = null;
		try {
			outputStream = new PrintWriter(new FileOutputStream("temporaryBooking.txt"));
		}
		catch(FileNotFoundException e){
			System.err.println("Error Opening FIle");
			System.exit(1);
		}
		
		
		while(inputStream.hasNextLine()) {
			
	    	String []booking_data;
	    	String currentLine = inputStream.nextLine();
	    	if(Integer.parseInt(currentLine.split(",")[0])==0) {
	    		outputStream.print(currentLine);
				continue;
			}
	    	booking_data = currentLine.split(",");
	    	Flight f = Flight.fromFile(booking_data[5],booking_data[6],booking_data[7],booking_data[8]);
	    	if((c_id==Integer.parseInt(booking_data[0]))&&(f_id.equals(booking_data[5]))&&(f.getDepartureDate().equals(depdate))&&(f.getDepartureCity().getCode().equals(depCity))&&(passName.equals(booking_data[1]))&&(f.getDestCity().getCode().equals(destCity))) {
	    		flag = 'p';
	    		f.incSeat();
	    		continue;
	    	}
	    	outputStream.print("\n"+currentLine);	
	    }
		inputStream.close();
		outputStream.close();
		File source = new File("temporaryBooking.txt");
		File dest = new File("booking.txt");
		try {
			Files.move(source.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			System.err.println("Error Replacing File");
			System.exit(1);
		}
		if(flag=='n')
			System.out.println("No Such Booking Exists.");
		else if(flag=='p')
			System.out.println("Booking Removed!");
	}
	public static void displayAll(){
		try {
				Scanner output = new Scanner(new FileInputStream("booking.txt"));
				while(output.hasNextLine()) {
					String s = output.nextLine();
					String[] s1 =s.split(",");
					if(Integer.parseInt(s1[0])==0)
						continue;
					System.out.println("Customer ID: "+s1[0]+"\tPassenger Name: "+s1[1]+"\tAge: "+s1[2]+"\tGender: "+s1[3]+"\tAadhar No: "+s1[4]+"\nFlight Code: "+s1[5]+"\tDeparture Date: "+s1[6]+"\tDeparture City: "+s1[6]+"\tDestination City: "+s1[6]);
				}
				output.close();
		} catch (FileNotFoundException e) {
			System.err.println("File Cannot Be Opened");
			System.exit(1);
		}
	}

}
