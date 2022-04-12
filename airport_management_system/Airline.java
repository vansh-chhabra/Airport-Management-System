package airport_management_system;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Airline {
	private String code;
	private String name;
	public String getCode() {
		return code;
	}
	public String getName() {
		return name;
	}
	Airline(String code){
		try {
			Scanner output = new Scanner(new FileInputStream("airline.txt"));
			while(output.hasNextLine()) {
				String s = output.nextLine();
				if(s.split(",")[0].equals(code)) {
					this.code = s.split(",")[0];
					this.name = s.split(",")[1];
				}
			}
			output.close();
		} catch (FileNotFoundException e) {
			System.err.println("File Cannot Be Opened");
			System.exit(1);
		}
	}
	public static void displayAll(){
		try {
				Scanner output = new Scanner(new FileInputStream("airline.txt"));
				while(output.hasNextLine()) {
					String s = output.nextLine();
					String[] s1 =s.split(",");
					System.out.println("Name: "+s1[1]+"\tCode: "+s1[0]);
				}
				output.close();
		} catch (FileNotFoundException e) {
			System.err.println("File Cannot Be Opened");
			System.exit(1);
		}
	}
}
