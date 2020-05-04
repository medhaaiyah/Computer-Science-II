//Programmer's Name: Medha Aiyah
//Programmer's Net Id: mva170001
/*
Purpose of program: This program helps user reserve tickets and acts as a ticket reservation system. It reads in the
 *   A1.txt. It then prompts the user to enter what auditorium they want
 * 	to select seats in and displays the seating chart. It then asks the user what seat they would prefer and the number
 * 	of adults, children and senior citizens in their party. If the preferred seats are unavailable, a function in the
 * 	program will generate the next best available seats for the user. If the user confirms these seats, a function in the
 * 	program writes out the auditorium with the newly reserved seats back to the file. The program loops the main prompt;
 * 	after the user finishes reserving seats, they are asked if they want to reserve more, or they can exit - which will
 * 	show them the statistics for tickets sold in all the auditoriums.
 * 	The program utilizes multiple functions, stores the seating information in a linked list (look at auditorium class),
    and uses algorithms to come up with finding the best available seating for the user.
 *
 */

import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main (String [] args) throws FileNotFoundException {

        //This called the auditorium class

        Auditorium auditorium = new Auditorium();

        mainMenu();
        //This is where it takes a look at whether the user inputs 1 or 2
        Scanner input = new Scanner(System.in);
        //int number = input.nextInt();

        //Validates the number the user puts
        boolean tOrf = true;
        while(tOrf == true)
        {
            try{
                int number = input.nextInt();
                tOrf = false;
                //This is what will happen if the user inputs 1 (will refer to functions in the auditorium class)

                while(number == 1)
                {
                    //These functions will print out the display and will print the prompt to ask for info the user wants

                    auditorium.programPrint();
                    auditorium.promptRowNumber();
                    auditorium.promptStartingSeat();
                    auditorium.promptNumOfAdultTickets();
                    auditorium.promptNumOfChildTickets();
                    auditorium.promptNumOfSeniorTickets();

                    //This is what will happen if the seat the user wants is available

                    if(auditorium.checkAvailability() == true)
                    {
                        System.out.println("Yay your seats are available!");
                        System.out.println("The changes that you made were received");
                        auditorium.isAvailable();
                    }

                    //This is what will happen if the seat the user wants is not available

                    else if(auditorium.checkAvailability() == false)
                    {
                        auditorium.isNotAvailable();
                    }
                    auditorium.programPrint();

                    //It will recall the main menu in the file until the user types in 2

                    mainMenu();
                    number = input.nextInt();

                }

                //This is where it will output the information and will close the scanners used in the program

                auditorium.outputData();
                auditorium.closeScanner();

            }
            catch(Exception e)
            {
                System.out.println("This is an invalid type to ask for. Try again.");
                input.nextLine();
                tOrf = true;
            }
        }
    }

    //Function: creating the menu

    public static void mainMenu()
    {
        System.out.println("1. Reserve Seats");
        System.out.println("2. Exit");
        System.out.println("Enter a desired menu entry: ");
    }
}
