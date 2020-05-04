//Programmer's Name: Medha Aiyah
//Net Id: mva170001
/*
In this project, the user is able to reserve seats in three auditoriums. There will be a prompt used to display which
auditorium the user wants to make the reservation. From there the user will be able to display the row of the seat, a
certain column, and the number of adult, child, and senior tickets it wants to purchase.

If the seats are available, it will display the available seats but if the seats are unavailable it will display the
best seats and ask the user whether or not the user wants those seats. If the user says "y" it will update the ticket
system, if the user types "n" it will not update the reservation ticket system.

At the end it will output the total seats in the auditorium, the total tickets sold, the adult tickets sold, the child
tickets sold, and the total ticket sales for each auditorium it is looking at.
 */

import java.util.*;
import java.util.Scanner.*;
import java.io.*;

public class Main{

    public static void main(String args[])
    {

        //get the names of the auditorium files you have
        System.out.println("Current Dir: " + System.getProperty("user.dir"));
        File folder = new File(System.getProperty("user.dir"));
        File[] listOfFiles = folder.listFiles();
        ArrayList<String> auditoriumFileNames = new ArrayList<String>();

        //Add all the auditorium files into an ArrayList
        for (int i =0; i < listOfFiles.length; i++)
        {
            File file = listOfFiles[i];
            if (file.isFile() && file.getName().toLowerCase().endsWith(".txt"))
            {
                auditoriumFileNames.add(file.getName());
            }

        }


        //ArrayList that holds the 2D arrays for each of the Auditoriums
        ArrayList<char[][]> auditoriumArrays = new ArrayList<char[][]>();


        for (int file = 0; file < auditoriumFileNames.size(); file++){
            String fileName = auditoriumFileNames.get(file);

            char[][] array = createAuditoriumArray(fileName);
            auditoriumArrays.add(createAuditoriumArray(fileName));
        }



        //Display menu and repeat until 2 is entered

        Scanner input = new Scanner(System.in);
        System.out.println("1. Reserve Seats");
        System.out.println("2. Exit");
        System.out.println("Enter a desired menu entry: ");
        int number = input.nextInt();

        //This will go to the reservation system

        while(number == 1){

            //Choose an Auditorium to reserve the tickets

            System.out.println("Choose an auditorium to reserve seats from: ");
            for (int i = 0; i < auditoriumArrays.size(); i++)
            {
                System.out.println((i+1) + ": Auditorium " + (i+1));
            }

            //This is used to choose the auditorium

            int auditoriumNum = input.nextInt();
            while(auditoriumNum > auditoriumArrays.size() || auditoriumNum < 1){
                System.out.println("This auditorium does not exist. Choose another auditorium.");
                for (int i = 0; i < auditoriumArrays.size(); i++)
                {
                    System.out.println((i+1) + ": Auditorium " + (i+1));
                }
                auditoriumNum = input.nextInt();
            }
            auditoriumNum--;

            //Retrieve the auditorium they asked for
            char [][] auditorium = auditoriumArrays.get(auditoriumNum);

            //Display the auditorium on the screen
            displayAuditoriumArray(auditorium);

            //Prompt user for row

            System.out.println("You can book multiple seats in the same row.");
            System.out.println("Row of your desired seats:");
            int userRow = input.nextInt();
            while (userRow <= 0 || userRow > auditorium.length) {
                System.out.println("This is an invalid row");
                System.out.println("Row of your desired seats:");
                userRow = input.nextInt();
            }

            //This is used to determine the starting seat letter

            System.out.println("Starting seat letter:");
            char userColumn = input.next().charAt(0);
            userColumn = Character.toUpperCase(userColumn);
            String currentLine = "";
            while (userColumn > (auditorium[0].length - 1 + 65)) {
                System.out.println("This is an invalid column");
                System.out.println("Starting seat letter:");
                currentLine = input.next();
                userColumn = currentLine.charAt(0);
            }

            //This is used to determine the number of adult tickets

            System.out.println("Number of adult tickets: ");
            int numAdultTickets = input.nextInt();
            while (numAdultTickets < 0) {
                System.out.println("This is an invalid number of tickets");
                System.out.println("Number of adult tickets: ");
                numAdultTickets = input.nextInt();
            }

            //This is used to determine the number of child tickets

            System.out.println("Number of child tickets: ");
            int numChildTickets = input.nextInt();
            while (numChildTickets < 0) {
                System.out.println("This is an invalid number of tickets");
                System.out.println("Number of child tickets: ");
                numChildTickets = input.nextInt();
            }

            //This is used to determine the number of the senior tickets

            System.out.println("Number of senior tickets: ");
            int numSeniorTickets = input.nextInt();
            while (numSeniorTickets < 0) {
                System.out.println("This is an invalid number of tickets");
                System.out.println("Number of senior tickets: ");
                numSeniorTickets = input.nextInt();
            }

            //This will check to see whether or not the seats that the user asked for is available or not

            boolean available = true;
            int startIndex = userColumn - 65;
            for (int i = startIndex; i < startIndex + (numAdultTickets + numChildTickets + numSeniorTickets) && i < auditorium[0].length; i++) {
                if (auditorium[userRow - 1][i] == 'A'|| auditorium[userRow - 1][i] == 'C' || auditorium[userRow - 1][i] == 'S') {
                    available = false;
                }
            }

            //This is what will happen if the seats are available

            if (available == true) {
                System.out.println("Yay your seats are available!");
                System.out.println("The changes that you made were received");
                int currentColumn = userColumn - 65;

                //This for loop here updates the file with the accurate type of ticket that the user wanted

                for(int i = currentColumn; i < numAdultTickets + numChildTickets + numSeniorTickets + currentColumn; i++)
                {

                    for (int j = 0; j < numAdultTickets; j++) {
                        auditorium[userRow - 1][i] = 'A';
                        updateFile(auditoriumFileNames.get(auditoriumNum), auditorium);
                        i++;
                    }
                    for (int k = 0; k < numChildTickets; k++) {
                        auditorium[userRow - 1][i] = 'C';
                        updateFile(auditoriumFileNames.get(auditoriumNum), auditorium);
                        i++;
                    }
                    for (int l = 0; l < numSeniorTickets; l++) {
                        auditorium[userRow - 1][i] = 'S';
                        updateFile(auditoriumFileNames.get(auditoriumNum), auditorium);
                        i++;
                    }
                }
                System.out.println();
            }

            //This is what will happen if the seats that the user inputs are not available
            //NOTE: For some reason this only works for certain cases. It does not work for other cases!!!

            if (available == false) {
                System.out.println("The seats that you are looking for are unavailable.");
                System.out.println("Finding the best available seats.");

                //This is used where it looks at a certain bound. From there it will create an array of true and false
                //To see which one is available or not.

                int totalNumTickets = numAdultTickets + numChildTickets + numSeniorTickets;
                int leftBound = 0;
                int rightBound = leftBound + totalNumTickets - 1;
                boolean [] availabilityOfSeats = new boolean[auditorium[0].length - totalNumTickets + 1];
                while(rightBound < auditorium[0].length)
                {
                    boolean seatsAvailableInRow = true;
                    for(int x = leftBound; x <= rightBound; x++)
                    {
                        if(auditorium[userRow][x] != '.')
                        {
                            seatsAvailableInRow = false;
                        }
                        availabilityOfSeats[leftBound] = seatsAvailableInRow;
                    }
                    leftBound++;
                    rightBound++;
                }
                int middle = (availabilityOfSeats.length / 2) + 1;

                boolean successfulUpdate = false;

                while(!successfulUpdate) {
                    boolean checkLeft = true;
                    int incrementVal = 1;

                    if (availabilityOfSeats[middle])
                    {
                        leftBound = middle + 3;
                        rightBound = leftBound + totalNumTickets;
                        System.out.println("These are the best available seats");
                        for(int x = leftBound; x < rightBound; x++)
                        {
                            //This is where it will display the seats that are available
                            System.out.println(userRow + "" + (char)(x + 65));
                        }
                        System.out.println("Do you want these seats? y/n");
                        String bestSeatResponse = input.next();

                        if (bestSeatResponse.equals("y")) {
                            System.out.println("Changes were made to the file");
                            for(int i = leftBound; i < numAdultTickets + numChildTickets + numSeniorTickets + leftBound; i++)
                            {

                                for (int j = 0; j < numAdultTickets; j++) {
                                    auditorium[userRow - 1][i] = 'A';
                                    updateFile(auditoriumFileNames.get(auditoriumNum), auditorium);
                                    i++;
                                }
                                for (int k = 0; k < numChildTickets; k++) {
                                    auditorium[userRow - 1][i] = 'C';
                                    updateFile(auditoriumFileNames.get(auditoriumNum), auditorium);
                                    i++;
                                }
                                for (int l = 0; l < numSeniorTickets; l++) {
                                    auditorium[userRow - 1][i] = 'S';
                                    updateFile(auditoriumFileNames.get(auditoriumNum), auditorium);
                                    i++;
                                }
                            }
                            successfulUpdate = true;
                        }
                        else
                        {
                            successfulUpdate = true;
                            //if they enter "n"
                            break;
                        }
                    }
                    else
                    {
                        if(checkLeft) {
                            middle = (availabilityOfSeats.length / 2) + 1;
                            middle -= incrementVal;
                            checkLeft = !checkLeft;
                        }
                        else {
                            middle = (availabilityOfSeats.length / 2) + 1;
                            middle += incrementVal;
                            checkLeft = !checkLeft;
                            incrementVal ++;

                            if(incrementVal ==  (availabilityOfSeats.length / 2)-2) {
                                //no seats available
                                System.out.println("There are no available seats in this row");
                                successfulUpdate = true;
                                break;

                            }
                        }
                    }
                }



            }

            System.out.println();

            //Display the menu and take in input
            System.out.println("1. Reserve Seats");
            System.out.println("2. Exit");
            System.out.println("Enter a desired menu entry: ");
            number = input.nextInt();

        }

        //Print formatted report
        outputData(auditoriumArrays);

    }







    //FUNCTION that takes auditorium file name and returns a 2d array with the information contained in the file
    public static char[][] createAuditoriumArray(String fileName)
    {
        try {
            //Scanner input object
            Scanner fileInput = new Scanner(new File(fileName));

            //Store lines of input in ArrayList
            ArrayList<String> linesOfInput = new ArrayList<String>();
            while (fileInput.hasNextLine()) {
                String line = fileInput.nextLine();
                linesOfInput.add(line);
            }
            int numRows = linesOfInput.size();
            int numSeatsPerRow = linesOfInput.get(0).length();


            //Auditorium 2D array
            char[][] auditoriumArray = new char[numRows][numSeatsPerRow];

            //Put input lines in array
            for (int row = 0; row < numRows; row++) {
                for (int seat = 0; seat < numSeatsPerRow; seat++) {
                    auditoriumArray[row][seat] = linesOfInput.get(row).charAt(seat);
                }
            }
            fileInput.close();
            //return 2d charArray
            return auditoriumArray;
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
        char[][] auditoriumArray = new char[0][0];
        return auditoriumArray;
    }

    //Function that displays an auditorium on the screen
    public static void displayAuditoriumArray(char[][] auditoriumArray){ //NEEDS formatting

        int numSeatsPerRow = auditoriumArray[0].length;
        int numRows = auditoriumArray.length;

        //Display seat letters
        System.out.print("  ");
        for (int letter = 65; letter < (65 + numSeatsPerRow); letter++)
        {
            char character = (char)letter;
            System.out.print(character);
        }
        System.out.print("\n");

        //Print the actual seat assignments
        for (int row = 0; row < numRows; row++)
        {
            //Label the row
            System.out.print((row+1) + " ");
            for(int seat = 0; seat < numSeatsPerRow; seat++)
            {
                //Replace letters with  '#'
                char character = auditoriumArray[row][seat];
                if (character == 'A' || character == 'C' || character == 'S')
                {
                    System.out.print('#');
                }
                else{
                    System.out.print(character);
                }
            }
            System.out.print("\n");
        }

    }

    //Function that displays an auditorium on the screen
    public static void updateFile(String fileName, char[][] auditoriumArray) {
        try {
            PrintWriter outputFile = new PrintWriter(new File(fileName));
            for (int row = 0; row < auditoriumArray.length; row++) {
                for (int seat = 0; seat < auditoriumArray[0].length; seat++) {
                    outputFile.print(auditoriumArray[row][seat]);
                }
                outputFile.println();
            }
            outputFile.close();
        }catch (FileNotFoundException fnf) {
            System.out.println("could not open file");
        }
    }

    //Function that prints out formatted report

    public static void outputData(ArrayList<char[][]> auditoriumArrays){
        int totalAdultsInA = 0;
        int totalSeniorsInA = 0;
        int totalChildrenInA = 0;
        int totalSeatsInA = 0;
        int totalTicketsInA = 0;
        double totalTicketSalesInA = 0;

        //Loop through each of the auditoriums and print out the details
        for (int i = 0; i < auditoriumArrays.size(); i++)
        {
            char[][] auditoriumArray =  auditoriumArrays.get(i);
            //Loop through the array and count the number of seats, adult/children/senior tickets, and total tickets sold
            int totalSeats = 0;
            int totalAdults = 0;
            int totalChildren = 0;
            int totalSeniors = 0;
            double totalTicketSales = 0;

            for (int row = 0; row < auditoriumArray.length; row++)
            {
                for (int seat = 0; seat < auditoriumArray[0].length; seat++)
                {
                    totalSeats++; //add up total seats
                    if (auditoriumArray[row][seat] == 'A') //Adult ticket
                    {
                        totalAdults++;
                        totalTicketSales += 10;
                    }
                    else if (auditoriumArray[row][seat] == 'C') //Child ticket
                    {
                        totalChildren++;
                        totalTicketSales += 5;
                    }
                    else if (auditoriumArray[row][seat] == 'S') //Senior ticket
                    {
                        totalSeniors++;
                        totalTicketSales += 7.5;
                    }

                }
            }

            //Print out info for this auditorium
            System.out.println("Report for Auditorium " + (i + 1) + ":");
            System.out.println("Total seats in this Auditorium: " + totalSeats);
            System.out.println("Total Tickets Sold: " + (totalAdults + totalChildren + totalSeniors));
            System.out.println("Total Adult Tickets Sold: " + totalAdults);
            System.out.println("Total Child Tickets Sold: " + totalChildren);
            System.out.println("Total Senior Tickets Sold: " + totalSeniors +"\n");

            System.out.println("Total Ticket Sales: $" + totalTicketSales);
            System.out.print("\n");

            //Add totals to the main totals for the entire auditorium
            totalSeatsInA += totalSeats;
            totalTicketsInA += (totalAdults + totalChildren + totalSeniors);
            totalAdultsInA += totalAdults;
            totalChildrenInA += totalChildren;
            totalSeniorsInA += totalSeniors;
            totalTicketSalesInA += totalTicketSales;


        }

        //Print out every thing in all the auditoriums

        System.out.println("Report for All Auditoriums: ");
        System.out.println("Total seats: " + totalSeatsInA);
        System.out.println("Total Tickets Sold: " + totalTicketsInA);
        System.out.println("Total Adult Tickets Sold: " + totalAdultsInA);
        System.out.println("Total Child Tickets Sold: " + totalChildrenInA);
        System.out.println("Total Senior Tickets Sold: " + totalSeniorsInA);
        System.out.println("Total Ticket Sales: $" + totalTicketSalesInA);

    }
}
