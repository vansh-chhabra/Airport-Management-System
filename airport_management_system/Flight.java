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

public class Flight {
	private String code;
	private String departureDate;
	private City departureCity;
	private City destCity;
	private String departureTime;
	private String arrivalTime;
	private int gate;
	private int seats = 180;
	private int fare;
	private int seatCounter;
	public int getSeatCounter() {
		return seatCounter;
	}
	public void setSeatCounter(int seatCounter) {
		this.seatCounter = seatCounter;
	}
	Flight(String fromtxt){
		String[] s = fromtxt.split(",");
		this.code = s[0];
		this.departureDate = s[1];
		City c1 = City.fromCode(s[2]);
		City c2 = City.fromCode(s[3]);
		this.departureCity = c1;
		this.destCity = c2;
		this.departureTime = s[4];
		this.arrivalTime = s[5];
		this.gate = Integer.parseInt(s[6]);
		this.seats = Integer.parseInt(s[7]);
		this.fare = Integer.parseInt(s[8]);
		this.seatCounter = Integer.parseInt(s[9]);
	}
	Flight(String airlineCode, String depdate, String depcity, String destcity, String deptime, String arrtime, int gate){
		setCode(airlineCode);
		setDepartureDate(depdate);
		setcities(depcity,destcity);
		setDepartureTime(deptime);
		setArrivalTime(arrtime);
		setGate(gate);
		setFareValue();
		seatCounter = 0;
	}
	public void setFare(int fare) {
		this.fare = fare;
	}
	public void setcities(String a,String b) {
		City city1 = new City();
		City city2 = new City();
		try {
			Scanner scnCity = new Scanner(new FileInputStream("city.txt"));
			while(scnCity.hasNextLine()) {
				String c1 = scnCity.nextLine();
				String[] c2 = c1.split(",");
				if(c2[0].equals(a))
					city1 = new City(c2[0],c2[1],Integer.parseInt(c2[2]),Integer.parseInt(c2[3]));
				if(c2[0].equals(b))
					city2 = new City(c2[0],c2[1],Integer.parseInt(c2[2]),Integer.parseInt(c2[3]));
			}
			scnCity.close();
		} catch (FileNotFoundException e) {
			System.err.println("Error Opening File");
			System.exit(1);
		}
		setDepartureCity(city1);
		setDestCity(city2);
	}
	public String getCode() {
		return code;
	}
	public void setCode(String a) {
		int n=0;
		try {
			Scanner scn = new Scanner(new FileInputStream("flight.txt"));
			while(scn.hasNextLine()) {
				String f1 = scn.nextLine();
				String[] f2 = f1.split(",");
				if(f2[0].split(" ")[0].equals(a))
					n++;
			}
			scn.close();
		} catch (FileNotFoundException e) {
			System.err.println("Error Opening File");
			System.exit(1);
		}	
		n++;
		this.code=a+" "+n;
	}
	public String getDepartureDate() {
		return departureDate;
	}
	public void setDepartureDate(String departureDate) {
		this.departureDate = departureDate;
	}
	public City getDepartureCity() {
		return departureCity;
	}
	public void setDepartureCity(City departureCity) {
		this.departureCity = departureCity;
	}
	public City getDestCity() {
		return destCity;
	}
	public void setDestCity(City destCity) {
		this.destCity = destCity;
	}
	public String getDepartureTime() {
		return departureTime;
	}
	public void setDepartureTime(String departureTime) {
		this.departureTime = departureTime;
	}
	public String getArrivalTime() {
		return arrivalTime;
	}
	public void setArrivalTime(String arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
	public int getGate() {
		return gate;
	}
	public void setGate(int gate) {
		this.gate = gate;
	}
	public int getSeats() {
		return seats;
	}
	public void setSeats(int seats) {
		this.seats = seats;
	}
	public int getFare() {
		return fare;
	}
	public void setFareValue() {
		int x1 = this.getDepartureCity().getCoodx();
		int x2 = this.getDestCity().getCoodx();
		int y1 = this.getDepartureCity().getCoody();
		int y2 = this.getDestCity().getCoody();
		int dist = Math.abs(x2-x1)+Math.abs(y2-y1);
		fare = 1000;
		for(int i=2;i<=dist;i++)
			fare+=500;
	}
	public void display() {
		System.out.println("\nCode: "+this.code+"\tDeparture Date: "+this.departureDate);
		System.out.println("Departure City: "+this.departureCity.getName()+"\tDestination City: "+this.destCity.getName());
		System.out.println("Departure Time: "+this.departureTime+"\tArrival Time: "+this.arrivalTime);
		System.out.println("Gate: "+this.gate+"\t\tSeats Left: "+this.seats+"\tFare: "+this.fare);		
	}
	public void append() {
		try {
			String fl1 = this.code+","+this.departureDate+","+this.departureCity.getCode()+","+this.destCity.getCode()+","+this.departureTime+","+this.arrivalTime+","+this.gate+","+this.seats+","+this.fare+","+this.seatCounter;
			PrintWriter printFlight = new PrintWriter(new FileOutputStream("flight.txt",true));
			printFlight.print("\n"+fl1);
			printFlight.close();
		} catch (FileNotFoundException e) {
			System.err.println("Error Opening File");
			System.exit(1);
		}
	}
	public static Flight fromFile(String a,String b, String c,String d) {
		Flight f=null;
		try {
			Scanner sc = new Scanner(new FileInputStream("flight.txt"));
			while(sc.hasNextLine()) {
				String str = sc.nextLine();
				String[] s = str.split(",");
				if(s[0].equals(a)&&s[1].equals(b)&&s[2].equals(c)&&s[3].equals(d)) {
					f = new Flight(str); 
				}
			}
			sc.close();
		} catch (FileNotFoundException e) {
			System.err.println("Error Opening File");
			System.exit(1);
		}
		return f;
	}
	public void reduceSeat() {
		Scanner inputStream = null;
		Flight f=null;
		try {
				inputStream = new Scanner(new FileInputStream("flight.txt"));
		}
		catch(FileNotFoundException e){
			System.err.println("Error Opening File");
			System.exit(1);
		}
		
		PrintWriter outputStream = null;
		try {
			outputStream = new PrintWriter(new FileOutputStream("temporaryFlight.txt"));
		}
		catch(FileNotFoundException e){
			System.err.println("Error Opening File");
			System.exit(1);		
		}
	    while(inputStream.hasNextLine()) {
	    	String currentLine = inputStream.nextLine();
	    	String[] s = currentLine.split(",");
	    	if(s[0].equals("no")) {
	    		outputStream.print(currentLine);
	    		continue;
	    	}
	    	if(this.code.equals(s[0])&&this.departureDate.equals(s[1])&&this.departureCity.getCode().equals(s[2])&&this.destCity.getCode().equals(s[3])) {
	    		f = new Flight(currentLine);
	    		continue;
	    	}
	    	outputStream.print("\n"+currentLine);
	    }
		inputStream.close();
		outputStream.close();
	    f.setSeats(f.getSeats()-1);
		File source = new File("temporaryFlight.txt");
		File dest = new File("flight.txt");
		try {
			Files.move(source.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			System.err.println("Error Replacing File");
			System.exit(1);
		}    	
    	f.append(); 	
	}
	public static void displayAll(){
		try {
				Scanner output = new Scanner(new FileInputStream("flight.txt"));
				while(output.hasNextLine()) {
					String s = output.nextLine();
					String[] s1 =s.split(",");
					if(s1[0].equals("no"))
						continue;
					System.out.println("Code: "+s1[0]+"\tDeparture Date: "+s1[1]+"\tDeparture City: "+s1[2]+"\tDestination City: "+s1[3]+"\nDeparture Time: "+s1[4]+"\tArrival Time: "+s1[5]+"\nGate: "+s1[6]+"\tSeats Left: "+s1[7]+"\tFare: "+s1[8]+"\tSeat Counter: "+s1[9]);
				}
				output.close();
		} catch (FileNotFoundException e) {
			System.err.println("File Cannot Be Opened");
			System.exit(1);
		}
	}
	public void incSeatCounter() {
		Scanner inputStream = null;
		Flight f=null;
		try {
				inputStream = new Scanner(new FileInputStream("flight.txt"));
		}
		catch(FileNotFoundException e){
			System.err.println("Error Opening File");
			System.exit(1);
		}
		
		PrintWriter outputStream = null;
		try {
			outputStream = new PrintWriter(new FileOutputStream("temporaryFlight.txt"));
		}
		catch(FileNotFoundException e){
			System.err.println("Error Opening File");
			System.exit(1);		
		}
	    while(inputStream.hasNextLine()) {
	    	String currentLine = inputStream.nextLine();
	    	String[] s = currentLine.split(",");
	    	if(s[0].equals("no")) {
	    		outputStream.print(currentLine);
	    		continue;
	    	}
	    	if(this.code.equals(s[0])&&this.departureDate.equals(s[1])&&this.departureCity.getCode().equals(s[2])&&this.destCity.getCode().equals(s[3])) {
	    		f = new Flight(currentLine);
	    		continue;
	    	}
	    	outputStream.print("\n"+currentLine);
	    }
	    inputStream.close();
		outputStream.close();	
	    f.seatCounter = f.seatCounter+1;
		File source = new File("temporaryFlight.txt");
		File dest = new File("flight.txt");
		try {
			Files.move(source.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			System.err.println("Error Replacing File");
			System.exit(1);
		}    	
    	f.append(); 
		
	}
	public void incSeat() {
		Scanner inputStream = null;
		Flight f=null;
		try {
				inputStream = new Scanner(new FileInputStream("flight.txt"));
		}
		catch(FileNotFoundException e){
			System.err.println("Error Opening File");
			System.exit(1);
		}
		
		PrintWriter outputStream = null;
		try {
			outputStream = new PrintWriter(new FileOutputStream("temporaryFlight.txt"));
		}
		catch(FileNotFoundException e){
			System.err.println("Error Opening File");
			System.exit(1);		
		}
	    while(inputStream.hasNextLine()) {
	    	String currentLine = inputStream.nextLine();
	    	String[] s = currentLine.split(",");
	    	if(s[0].equals("no")) {
	    		outputStream.print(currentLine);
	    		continue;
	    	}
	    	if(this.code.equals(s[0])&&this.departureDate.equals(s[1])&&this.departureCity.getCode().equals(s[2])&&this.destCity.getCode().equals(s[3])) {
	    		f = new Flight(currentLine);
	    		continue;
	    	}
	    	outputStream.print("\n"+currentLine);
	    }
	    inputStream.close();
		outputStream.close();	
	    f.seats = f.seats+1;
		File source = new File("temporaryFlight.txt");
		File dest = new File("flight.txt");
		try {
			Files.move(source.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			System.err.println("Error Replacing File");
			System.exit(1);
		}    	
    	f.append(); 
		
	}
	
}
