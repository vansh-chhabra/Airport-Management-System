package airport_management_system;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class BoardingPass {
	private Passenger passenger;
	private Flight flight;
	private float baggage;
	private int baggageCharge;
	private String seat;
	BoardingPass(Passenger p,Flight f,Float bgg,int bggpr,String seat){
		this.passenger = p;
		this.flight = f;
		this.baggage = bgg;
		this.baggageCharge = bggpr;
		this.seat = seat;
	}
	BoardingPass(String fromtxt){
		String[] s = fromtxt.split(",");
		this.passenger = new Passenger(Integer.parseInt(s[0]),s[1]);
		this.flight = Flight.fromFile(s[2],s[3],s[4],s[5]);
		this.baggage = Float.parseFloat(s[6]);
		this.baggageCharge = Integer.parseInt(s[7]);
		this.seat = s[8];
	}
	public Passenger getPassenger() {
		return passenger;
	}
	BoardingPass(){
		
	}
	public void setPassenger(Passenger passenger) {
		this.passenger = passenger;
	}
	public Flight getFlight() {
		return flight;
	}
	public void setFlight(Flight flight) {
		this.flight = flight;
	}
	public float getBaggage() {
		return baggage;
	}
	public void setBaggage(float baggage) {
		this.baggage = baggage;
	}
	public int getBaggageCharge() {
		return baggageCharge;
	}
	public void setBaggageCharge(int baggageCharge) {
		this.baggageCharge = baggageCharge;
	}
	public String getSeat() {
		return seat;
	}
	public void setSeat(String seat) {
		this.seat = seat;
	}
	public static void displayAll() {
		BoardingPass bp = null;
		try {
				Scanner output = new Scanner(new FileInputStream("boardingpass.txt"));
				while(output.hasNextLine()) {
					String s = output.nextLine();
					if(Integer.parseInt(s.split(",")[0])==0)
						continue;
					bp = new BoardingPass(s);
					bp.display();
				}
				output.close();
		} catch (FileNotFoundException e) {
			System.err.println("File Cannot Be Opened");
			System.exit(1);
		}
		
	}
	public void append() {
		try {
			String s = this.passenger.getId() + "," + this.passenger.getName() + ","  + this.flight.getCode() + "," + this.flight.getDepartureDate() + "," + this.flight.getDepartureCity().getCode() + "," + this.flight.getDestCity().getCode()+ "," + this.getBaggage() + ","  + this.getBaggageCharge() + ","  + this.getSeat();
			PrintWriter p = new PrintWriter(new FileOutputStream("boardingpass.txt",true));
			p.print("\n"+s);
			p.close();
		} catch (FileNotFoundException e) {
			System.err.println("Error Opening FIle");
			System.exit(1);
		}
	}
	public void display() {
		System.out.println("\nName: "+this.passenger.getName()+"\tFlight Code: "+this.flight.getCode()+"\tDestination Date: "+this.flight.getDepartureDate());
		System.out.println("From: "+this.flight.getDepartureCity().getName()+"\tTo: "+this.flight.getDestCity().getName()+"\tDeparture Time: "+this.flight.getDepartureTime()+"\tArrival Time: "+this.flight.getArrivalTime());
		System.out.println("Gate No: "+this.flight.getGate()+" \tSeat No: "+this.seat);
	}
}
