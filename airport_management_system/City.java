package airport_management_system;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class City {
	private String code;
	private String name;
	private int coodx;
	private int coody;
	City(){
		
	}
	City(String name){
		try {
			Scanner output = new Scanner(new FileInputStream("city.txt"));
			while(output.hasNextLine()) {
				String s = output.nextLine();
				if(s.split(",")[1].equals(name)) {
					this.code = s.split(",")[0];
					this.name = s.split(",")[1];
					this.coodx = Integer.parseInt(s.split(",")[2]);
					this.coody = Integer.parseInt(s.split(",")[3]);
				}
			}
			output.close();
		} catch (FileNotFoundException e) {
			System.err.println("File Cannot Be Opened");
			System.exit(1);
		}
	}
	City(String code,String name,int coodx,int coody){
		this.code = code;
		this.name = name;
		this.coodx = coodx;
		this.coody = coody;
	}
	public static City fromCode(String code) {
		City c = new City();
		try {
			Scanner output = new Scanner(new FileInputStream("city.txt"));
			while(output.hasNextLine()) {
				String s = output.nextLine();
				String[] s1 =s.split(",");
				if(s1[0].equals(code)) {
					c = new City(s1[0],s1[1],Integer.parseInt(s1[2]),Integer.parseInt(s1[3]));
				}
			}
			output.close();
		} catch (FileNotFoundException e) {
			System.err.println("File Cannot Be Opened");
			System.exit(1);
		}
		return c;
	}
	public String getCode() {
		return code;
	}
	public String getName() {
		return name;
	}
	public int getCoodx() {
		return coodx;
	}
	public int getCoody() {
		return coody;
	}
	public static void displayAll(){
		try {
				Scanner output = new Scanner(new FileInputStream("city.txt"));
				while(output.hasNextLine()) {
					String s = output.nextLine();
					String[] s1 =s.split(",");
					System.out.println("Name: "+s1[1]+"\tCode: "+s1[0]+"\tCoordinate X: "+s1[2]+"\tY: "+s1[3]);
				}
				output.close();
		} catch (FileNotFoundException e) {
			System.err.println("File Cannot Be Opened");
			System.exit(1);
		}
	}
}
