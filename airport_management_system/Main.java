package airport_management_system;

import java.io.IOException;
import java.util.Scanner;
public class Main {
	public static void promptEnterKey(){
		   System.out.println("Press \"ENTER\" to continue...");
		   try{System.in.read();}
		           catch(Exception e){}	  
		   Scanner in = new Scanner(System.in);
		   in.nextLine();
	}
	public static void main(String[] args) throws IOException{
		for(int i=0; i<=100;i++)	
			System.out.print("*");
		System.out.print("\n");
		System.out.print("\n\n\n\n\n\n\n\n\n\n\n\n\t\t\t\tAirport Ticket Booking System\n");
		for(int i=0; i<=100;i++)	
			System.out.print("*");
		System.out.print("\n");		
		promptEnterKey();
		
		while(true)
		{   
			int choice;
			System.out.println("\nMenu");
			System.out.println("1.Register Customer");
			System.out.println("2.Customer Login");
			System.out.println("3.Staff Login");
			System.out.println("4.Admin");
			System.out.println("0.Exit");
			System.out.print("\nEnter Choice: ");
			Scanner in = new Scanner(System.in);	
			choice=in.nextInt();
			switch(choice)
			{
				case 1:
			 		Customer.register();
					break;
				case 2:
			 		Customer.login();
					break;
				case 3:
				 	Staff.login();
					break;
				case 4:
				 	Admin.login();
					break;
				case 0:
					System.out.println("Thank You for using our program.");
					in.nextLine();
					in.close();
					System.exit(0);		
					break;
				default:
					System.out.println("Invalid Input");
			}
		}
	}
}

