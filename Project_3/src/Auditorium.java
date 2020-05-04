//Programmer's Name: Medha Aiyah
//Programmer's Net Id: mva170001
/*
This is where all the magic happens :). In the auditorium class we will create the grid that will be used to store the
contents in the file into the linked list. From there in this class we created a plethora of functions to make sure that
certain aspects of the program is working. I will go in depth with what each function does.
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Auditorium {

    //These are the member variables in the auditorium class

    private TheaterSeat first;
    private int userRow;
    private char userColumn;
    private int numAdultTickets;
    private int numChildTickets;
    private int numSeniorTickets;

    //This is the file that I am using in this program

    static String fileName = "A1.txt";
    static Scanner fileInput;

    static {
        try {
            fileInput = new Scanner(new File(fileName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    //This is used to parse through the file row by row

    static ArrayList<String> linesOfInput = new ArrayList<String>();

    //This is used to get the number of rows

    public static int getNumRows()
    {
        while (fileInput.hasNextLine()) {
            String line = fileInput.nextLine();
            linesOfInput.add(line);
        }
        int numRows = linesOfInput.size();

        return numRows;
    }

    //This is used to get the number of columns

    public static int getNumSeatsPerRow()
    {
        while (fileInput.hasNextLine()) {
            String line = fileInput.nextLine();
            linesOfInput.add(line);
        }
        int numSeatsPerRow = linesOfInput.get(0).length();

        return numSeatsPerRow;
    }

    //These are the global variables that we are using in this class

    static int numRows = getNumRows();
    static int numSeatsPerRow = getNumSeatsPerRow();

    //Constructor: builds auditorium grid and fills in each node based on the file input

    public Auditorium() throws FileNotFoundException {

        //These are the variables that we are going to be using in the constructor

        TheaterSeat current = null;
        TheaterSeat firstInRow = null;
        TheaterSeat previousRow = null;
        TheaterSeat next;

        boolean reserved;
        char type;

        //Put input lines into the linked list

        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numSeatsPerRow-1; col++) {
                //This is what will happen when the column is 0
                if(col == 0)
                {
                    type = linesOfInput.get(row).charAt(col);
                    if(type == '.')
                    {
                        reserved = false;
                    }
                    else
                        reserved = true;
                    current = new TheaterSeat(1, (char)(col + 65), reserved, type, null,null,null,null);
                    firstInRow = current;

                    //This is what will happen when the row is 0 and the column is 0

                    if(row == 0){
                        first = current;
                        previousRow = current;
                    }
                }
                //This is what will happen when the column is greater than zero
                else
                    current = current.getRight();
                type = linesOfInput.get(row).charAt(col+1);
                //This is used to determine whether or not it is reserved
                if(type == '.')
                {
                    reserved = false;
                }
                else
                    reserved = true;
                next = new TheaterSeat(row+1, (char)(col+66),reserved, type,null,null,null,null);
                //This is where I set the up and down methods in the theater seat to parse through the linkedList
                if(row>0){
                    current.setUp(previousRow);
                    previousRow.setDown(current);
                    previousRow = previousRow.getRight();
                }
                current.setRight(next);
                next.setLeft(current);
            }
            previousRow = firstInRow;
        }
    }


    //This is the getter in the auditorium class

    public TheaterSeat getFirst() {
        return first;
    }

    //This is the setter in the auditorium class

    public void setFirst(TheaterSeat first) {
        this.first = first;
    }

    //------ADDITIONAL FUNCTIONS INCLUDED IN THE AUDITORIUM CLASS------//

    //Function: prints out the current seat availability with a dot or #

    public void programPrint()
    {
        //Setting the row variable to 1
        int row = 1;

        //Getting the column header

        System.out.print("  ");
        for (int letter = 65; letter < (65 + numSeatsPerRow); letter++)
        {
            char character = (char)letter;
            System.out.print(character);
        }
        System.out.print("\n");

        //This is the row number that will be printed out and will be increm

        System.out.print(row + " ");

        //Printing the contents in the linked lists for that row and will print out the row number accordingly

        TheaterSeat current = first;
        while(current != null)
        {
            while(current.getRight() != null)
            {
                if(current.getTicketType() == 'A' || current.getTicketType() == 'C' || current.getTicketType() == 'S')
                {
                    System.out.print("#");
                }
                else
                {
                    System.out.print(current.getTicketType());
                }
                current = current.getRight();
            }
            if(current.getTicketType() == 'A' || current.getTicketType() == 'C' || current.getTicketType() == 'S')
            {
                System.out.println("#");
                if(numRows >= ++row)
                {
                    System.out.print(row + " ");
                }
            }
            else
            {
                System.out.println(current.getTicketType());
                if(numRows >= ++row)
                {
                    System.out.print(row + " ");
                }
            }
            while(current.getSeat() != 'A')
            {
                current = current.getLeft();
            }

            //This is when it will go to the next row

            current = current.getDown();
        }
    }

    //Function: prompting and storing the row number the user wants

    Scanner userInput = new Scanner(System.in);

    public void promptRowNumber()
    {
        boolean tOrf = true;
        while(tOrf == true)
        {
            System.out.println("You can book multiple seats in the same row.");
            System.out.println("Row of your desired seats:");
            try{
                userRow = userInput.nextInt();
                tOrf = false;
            }
            catch(Exception e)
            {
                System.out.println("This is an invalid type to ask for. Try again.");
                userInput.nextLine();
                tOrf = true;
            }
        }

        //validating whether or not it is valid

        while (userRow <= 0 || userRow > linesOfInput.size()) {
            System.out.println("This is an invalid row");
            System.out.println("Row of your desired seats:");
            userRow = userInput.nextInt();
        }
    }

    //Function: prompting and storing the starting seat letter the user wants

    public void promptStartingSeat()
    {
        String currentLine = "";

        boolean tOrf = true;
        while(tOrf == true)
        {
            System.out.println("Starting seat letter:");
            try{
                userColumn = userInput.next().charAt(0);
                userColumn = Character.toUpperCase(userColumn);
                tOrf = false;
            }
            catch(Exception e)
            {
                System.out.println("This is an invalid type to ask for. Try again.");
                userInput.nextLine();
                tOrf = true;
            }
        }

        //validating whether or not it is valid

        while (userColumn > (numSeatsPerRow - 1 + 65)) {
            System.out.println("This is an invalid column");
            System.out.println("Starting seat letter:");
            currentLine = userInput.next();
            userColumn = currentLine.charAt(0);
        }
    }

    //Function: prompting and storing the number of adult seat tickets the user wants

    public void promptNumOfAdultTickets()
    {
        boolean tOrf = true;
        while(tOrf == true)
        {
            System.out.println("Number of adult tickets: ");
            try{
                numAdultTickets = userInput.nextInt();
                tOrf = false;
            }
            catch(Exception e)
            {
                System.out.println("This is an invalid type to ask for. Try again.");
                userInput.nextLine();
                tOrf = true;
            }
        }

        while (numAdultTickets < 0) {
            System.out.println("This is an invalid number of tickets");
            System.out.println("Number of adult tickets: ");
            numAdultTickets = userInput.nextInt();
        }

    }

    //Function: prompting and storing the number of child seat tickets the user wants

    public void promptNumOfChildTickets()
    {
        boolean tOrf = true;
        while(tOrf == true)
        {
            System.out.println("Number of child tickets: ");
            try{
                numChildTickets = userInput.nextInt();
                tOrf = false;
            }
            catch(Exception e)
            {
                System.out.println("This is an invalid type to ask for. Try again.");
                userInput.nextLine();
                tOrf = true;
            }
        }

        //validating whether or not it is valid

        while (numChildTickets < 0) {
            System.out.println("This is an invalid number of tickets");
            System.out.println("Number of child tickets: ");
            numChildTickets = userInput.nextInt();
        }
    }

    //Function: prompting and storing the number of senior seat tickets the user wants

    public void promptNumOfSeniorTickets()
    {
        boolean tOrf = true;
        while(tOrf == true)
        {
            System.out.println("Number of senior tickets: ");
            try{
                numSeniorTickets = userInput.nextInt();
                tOrf = false;
            }
            catch(Exception e)
            {
                System.out.println("This is an invalid type to ask for. Try again.");
                userInput.nextLine();
                tOrf = true;
            }
        }

        //validating whether or not it is valid

        while (numSeniorTickets < 0) {
            System.out.println("This is an invalid number of tickets");
            System.out.println("Number of senior tickets: ");
            numSeniorTickets = userInput.nextInt();
        }
    }

    //Function: used to determine whether or not the seat the user wants are available

    public boolean checkAvailability()
    {
        //These are the variables that we are using in this functions
        TheaterSeat current = first;
        boolean available = true;
        int startIndex = userColumn - 65;

        //This is where it will be going horizontally to the column

        for(int i = 0; i < startIndex; i++)
        {
            if (current != null && current.getRight() != null)
            {
                current = current.getRight();
            }
        }

        //This is where it will be going vertically to the row

        for(int i = 0; i < userRow-1; i++)
        {
            if (current != null && current.getDown() != null)
            {
                current = current.getDown();
            }
        }

        //This is where it checks whether or not the seats are available or not

        for(int i = 0; i < (numSeniorTickets + numChildTickets + numAdultTickets); i++ )
        {
            if (current != null)
            {
                if (current.getTicketType() == 'A' || current.getTicketType() == 'C' || current.getTicketType() == 'S') {
                    available = false;
                }
                if(current.getRight() != null)
                {
                    current = current.getRight();
                }
                else
                {
                    available = false;
                }
                if(startIndex + (numSeniorTickets + numChildTickets + numAdultTickets) == numSeatsPerRow)
                {
                    available = true;
                }
            }
        }

        return available;
    }

    //Function: this is what will happen if it is available

    public void isAvailable()
    {
        int currentColumn = userColumn - 65;
        TheaterSeat current = first;

        //This is where it will be going horizontally to the column

        for(int i = 0; i < currentColumn; i++)
        {
            if (current != null && current.getRight() != null)
            {
                current = current.getRight();
            }
        }

        //This is where it will be going vertically to the row

        for(int i = 0; i < userRow - 1; i++)
        {
            if (current != null && current.getDown() != null)
            {
                current = current.getDown();
            }
        }

        //This for loop here updates the file with the accurate type of ticket that the user wanted

        if (current != null)
        {
            for (int j = 0; j < numAdultTickets; j++) {
                current.setTicketType('A');
                if(current.getRight() != null)
                {
                    current = current.getRight();
                }
            }
            for (int k = 0; k < numChildTickets; k++) {
                current.setTicketType('C');
                if(current.getRight() != null)
                {
                    current = current.getRight();
                }

            }
            for (int l = 0; l < numSeniorTickets; l++) {
                current.setTicketType('S');
                if(current.getRight() != null)
                {
                    current = current.getRight();
                }
            }
        }
        updateFile();
        System.out.println();
    }

    //Function: this function is used to update the file

    public void updateFile()
    {
        try {
            PrintWriter outputFile = new PrintWriter(new File(fileName));
            TheaterSeat current = first;

            //This is the while loop which will be print the contents into the output

            while(current != null)
            {
                while(current.getRight() != null)
                {
                    outputFile.print(current.getTicketType());
                    current = current.getRight();
                }
                outputFile.println(current.getTicketType());
                while(current.getSeat() != 'A')
                {
                    current = current.getLeft();
                }
                current = current.getDown();
            }
            outputFile.close();

            //This is what will happen if there is an error in the file

        }catch (FileNotFoundException fnf) {
            System.out.println("could not open file");
        }
    }

    //Function: this is what will happen if it is not available

    public void isNotAvailable()
    {
        System.out.println("\nThe seats you selected are unavailable. Finding the next best available seats!\n");

        //Referring to the bestAvailableSeat function to determine whether or not we can find available seats

        char index = bestAvailableSeats();

        if(index == '\0')  // if no seats are found in the desired now
        {
            System.out.println("No available seats on your desired row. Please try again!");
        }
        else {
            System.out.print("The next best available seats are: ");

            // display best available seat numbers
            char column = userColumn;
            for (int i = 0; i < (numAdultTickets + numChildTickets + numSeniorTickets); i++) {
                System.out.print(userRow);
                System.out.print(column + "  ");
                column++;
            }

            char response;  // user decides if they want the next best available seats

            System.out.print("\nWould you like to confirm these seats? (Enter Y for yes and N for no): ");
            response = userInput.next().charAt(0);

            if (response == 'Y' || response == 'y')  // if user types Y store the seats
            {
                System.out.println("Changes were made to the file");
                isAvailable();
            }
        }
    }

    //Function: this function is used to determine the best available seats

    public char bestAvailableSeats() {
        int midOffset = ((numAdultTickets + numChildTickets + numSeniorTickets) - 1) / 2;  // midpoint of the total number of people
        int midRow = (numSeatsPerRow - 1) / 2;  // midpoint of the row
        int currentSeat = midRow;  // variable to store value of midRow.

        // this variable is for the algorithm.
        int change = 1;

        // while loop to check the seats
        while (((currentSeat - midOffset) >= 0) && ((currentSeat - midOffset + (numAdultTickets + numChildTickets + numSeniorTickets) - 1) < numSeatsPerRow)) {
            userColumn = (char) ((currentSeat - midOffset) + 65);

            if (checkAvailability() == true) {
                return userColumn;
            }
            if (change % 2 == 0) {
                currentSeat -= change;
            } else {
                currentSeat += change;
            }
            change++;
        }
        return '\0';
    }

    //Function: this will be used to print out the output

    public void outputData() {

        //These are the variables that I am using in this function

        TheaterSeat current = first;
        TheaterSeat firstInRow = first;

        int totalSeats = 0;
        int totalAdults = 0;
        int totalChildren = 0;
        int totalSeniors = 0;
        float totalTicketSales = 0;

        //Loop through the linked list and count the number of seats, adult/children/senior tickets, and total tickets sold

        for (int row = 0; row < numRows; row++) {
            current = firstInRow;
            for (int seat = 0; seat < numSeatsPerRow; seat++) {
                if (current != null)
                {
                    totalSeats++; //add up total seats
                    if (current.getTicketType() == 'A') //Adult ticket
                    {
                        totalAdults++;
                        totalTicketSales += 10;
                    } else if (current.getTicketType() == 'C') //Child ticket
                    {
                        totalChildren++;
                        totalTicketSales += 5;
                    } else if (current.getTicketType() == 'S') //Senior ticket
                    {
                        totalSeniors++;
                        totalTicketSales += 7.5;
                    }
                    current = current.getRight();
                }
            }
            firstInRow = firstInRow.getDown();
        }
        //Print out info for this auditorium
        System.out.println("Report for Auditorium: ");
        System.out.println("Total seats in this Auditorium: " + totalSeats);
        System.out.println("Total Tickets Sold: " + (totalAdults + totalChildren + totalSeniors));
        System.out.println("Total Adult Tickets Sold: " + totalAdults);
        System.out.println("Total Child Tickets Sold: " + totalChildren);
        System.out.println("Total Senior Tickets Sold: " + totalSeniors + "\n");

        System.out.println("Total Ticket Sales: $" + totalTicketSales);
        System.out.print("\n");
    }
    //Function: checking to see if it is available
    public void closeScanner()
    {
        fileInput.close();
        userInput.close();
    }
}

