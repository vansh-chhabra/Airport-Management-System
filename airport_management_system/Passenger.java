package airport_management_system;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Passenger extends User{
	private int age;
	private char gender;
	private String aadharNo;
	public int getAge() {
		return age;
	}
	Passenger(int id,String name,int age, char gender, String aadharNo){
		this.setId(id);
		this.setName(name);
		this.age = age;
		this.gender = gender;
		this.aadharNo = aadharNo;
	}
	Passenger(int id,String name){
		int age = 0;
		char gender='x';
		String aadharNo=null;
		try {
			Scanner output = new Scanner(new FileInputStream("booking.txt"));
			while(output.hasNextLine()) {
				String s = output.nextLine();
				String[] s1 =s.split(",");
				if(id==Integer.parseInt(s1[0])&&name.equals(s1[1])) {
					age = Integer.parseInt(s1[2]);
					gender = s1[3].charAt(0);
					aadharNo = s1[4];
				}
			}
			output.close();
		} catch (FileNotFoundException e) {
			System.err.println("File Cannot Be Opened");
			System.exit(1);
			}
		this.setId(id);
		this.setName(name);
		this.age = age;
		this.gender = gender;
		this.aadharNo = aadharNo;
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
	
}
