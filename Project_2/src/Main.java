//Programmer's Name: Medha Aiyah
//Programmer's Net Id: mva170001
/*
In this project there are three files, the customer.dat, orders.dat,  and preferred.dat. The first thing this class will
do is to put all the contents in the customer.dat into the regularCustomerArray and put all the contents in the
preferred.dat into the preferredCustomerArray. Once the contents are saved in the appropriate array it will take a look
at the orders.dat file. Firstly it will check to see if the orders.dat has invalid data (if there is invalid data it will
ignore that contents). From there it will determine the status of each customer by referring to the ID. It will also
calculate the amount spent for each of the customers.

Depending on the status and the amount spent, there are many possibilities. If a regular customer spends $50 or more it
becomes a gold member. If a regular customer spends $150 or more it becomes a platinum member. If a gold customer
spends $200 or more it becomes a platinum customer. Based on how much the customer spends, it will make the appropriate
changes in the array. Once the changes are made to the two arrays, it will then update the specific files.
 */

import java.io.*;
import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    //These are the two arrays that are going to be used in this class

    static Customer [] regularCustomerArray;

    static {
        try {
            regularCustomerArray = regularCustomerArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    static Customer [] preferredCustomerArray;

    static {
        try {
            preferredCustomerArray = preferredCustomerArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Main() throws FileNotFoundException {
    }

    //This main function is used to bring all of the functions and update the two arrays according

    public static void main(String[] args) throws IOException {

        //System.out.println(Arrays.toString(preferredCustomerArray));

        //This used to refer to the order.dat file

        File ordersPath = new File("src/orders.dat");
        Scanner orders = new Scanner(ordersPath);

        //There will be a while loop to take into account the order's information

        int index = 0;

        while(orders.hasNextLine())
        {
            String line = orders.nextLine();
            String[] lineArr = line.split(" ");


            //This is used to determine whether there is invalid data in the orders.dat

            if (invalidData(lineArr) == false)
            {
                continue;
            }

            //Create a variable that adds the spent drink with the spent graphic

            float totalAmountSpent = amountSpentDrink(lineArr) + amountSpentGraphic(lineArr);

            //These series of if statements are used to determine how to update the arrays
            //This is used to determine status of each customer

            String status = currentStatus(lineArr);

            //This is the series of if statements (or options) that can happen for the Regular customer

            if(status == "Regular")
            {
                //System.out.println(Arrays.toString(lineArr));
                if(totalAmountSpent >=50 && totalAmountSpent < 200)
                {
                    regularToGold(lineArr);
                    if(totalAmountSpent >= 75)
                    {
                        applyGoldDiscount(totalAmountSpent, lineArr);
                    }
                }
                if(totalAmountSpent >= 200)
                {
                    regularToPlatinum(lineArr);
                    updateBonusBucks(totalAmountSpent, lineArr);
                }
            }

            //This is the series of if statements (or options) that can happen for the Gold customer

            else if(status == "Gold")
            {
                applyGoldDiscount(totalAmountSpent, lineArr);
                if(totalAmountSpent >= 200)
                {
                    goldToPlatinum(lineArr);
                    updateBonusBucks(totalAmountSpent, lineArr);
                }
            }

            //This is the series of if statements (or options) that can happen for the Platinum customer

            else if(status == "Platinum")
            {
                updateBonusBucks(totalAmountSpent, lineArr);
                applyBonusBucks(totalAmountSpent, lineArr);
            }

            index++;
        }

        //This function is called at the end to update the customer.dat file and the preferred.dat file

        updateFiles();
    }

    //This function is used to look at the customer.dat file and include it in the array

    public static Customer [] regularCustomerArray() throws FileNotFoundException {

        //This is going to refer to the customer.dat file

        File customerPath = new File("src/customer.dat");


        Scanner customer = new Scanner(customerPath);
        Scanner customerOpen = new Scanner(customerPath);

        //This is used to increment and figure out how many rows there are in the customer.dat file

        int newLineRegularCustomer = 0;

        while(customer.hasNext())
        {
            //System.out.println("Hi One");
            customer.nextLine();
            newLineRegularCustomer++;
        }

        //This is the regular customer array where it stores the contents

        Customer [] regularCustomer = new Customer[newLineRegularCustomer];
        customer.close();

        int customerIndex = 0;

        //This is while loop that is used to where it stores the pieces of each line into regularCustomer array
        while (customerOpen.hasNextLine())
        {
            //System.out.println("Hi Two");
            Customer customerOne = new Customer();
            String line = customerOpen.nextLine();
            String [] lineArr = line.split(" ");
            customerOne.setFirstName(lineArr[1]);
            customerOne.setLastName(lineArr[2]);
            customerOne.setGuestId(lineArr[0]);
            customerOne.setAmountSpent(Float.valueOf(lineArr[3]));
            regularCustomer[customerIndex] = customerOne;
            //regularCustomer[customerIndex].print();


            customerIndex++;
        }

        //This is going to return the regularCustomer array

        return regularCustomer;
    }

    //This function is used to look at the preferred.dat file and include it in the array

    public static Customer [] preferredCustomerArray() throws FileNotFoundException
    {
        //This is going to refer to the preferred.dat file

        File preferredPath = new File("src/preferred.dat");
        Scanner preferred = new Scanner(preferredPath);
        Scanner preferredOpen = new Scanner(preferredPath);

        //This is used to increment and figure out how many rows there are in the preferred.dat file

        int newLinePreferredCustomer = 0;

        while (preferred.hasNext()) {
            //System.out.println("HI One");
            preferred.nextLine();
            newLinePreferredCustomer++;
        }

        //This is the preferred customer array which will store the info in the file

        Customer[] preferredCustomer = new Customer[newLinePreferredCustomer];
        preferred.close();

        int preferredCustomerIndex = 0;

        //This while loop with store the contents in the array line by line

        while (preferredOpen.hasNext()) {
            //System.out.println("HI two");
            String line = preferredOpen.nextLine();
            String[] lineArr = line.split(" ");

            //It will store a gold object into the array

            if (lineArr[4].contains("%")) {
                String[] percentageSign = lineArr[4].split("%");

                PreferredCustomerGold preferCustomerGold = new PreferredCustomerGold(lineArr[1], lineArr[2], lineArr[0], Float.valueOf(lineArr[3]), Integer.valueOf(percentageSign[0]));
                preferredCustomer[preferredCustomerIndex] = preferCustomerGold;
                //preferredCustomer[preferredCustomerIndex].print();
            }

            //It will store a platinum object into the array

            else {
                PreferredCustomerPlatinum preferCustomerPlatinum = new PreferredCustomerPlatinum(lineArr[1], lineArr[2], lineArr[0], Float.valueOf(lineArr[3]), Integer.valueOf(lineArr[4]));
                preferredCustomer[preferredCustomerIndex] = preferCustomerPlatinum;
                //preferredCustomer[preferredCustomerIndex].print();
            }
            preferredCustomerIndex++;
        }

        //It will return the preferredCustomer array

        return preferredCustomer;

    }

    //This function will determine whether there is invalid data in the orders.dat file

    public static boolean invalidData(String[] arr)
    {
        boolean validity = true;
        int saveRegularCustomerIndex = -1;
        int savePreferredCustomerIndex = -1;

        //Checking invalid # of fields

        if(arr.length != 5)
        {
            //System.out.println("Invalid Number of Fields");
            validity = false;
        }
        else
        {
            for(int i = 0; i < regularCustomerArray.length; i++ )
            {
                if(regularCustomerArray[i].getGuestId().equals(arr[0]))
                {
                    saveRegularCustomerIndex = i;
                    break;
                }
            }
            for(int i = 0; i < preferredCustomerArray.length; i++ )
            {
                preferredCustomerArray[i].print();
                if( preferredCustomerArray[i] != null)
                {
                    savePreferredCustomerIndex = -1;
                    break;
                }
                else if(preferredCustomerArray[i].getGuestId().equals(arr[0]))
                {
                    savePreferredCustomerIndex = i;
                    break;
                }
            }

            //Checking invalid id

            if(savePreferredCustomerIndex == -1 && saveRegularCustomerIndex == -1)
            {
                //System.out.println("Invalid ID");
                validity = false;
            }

            //Check invalid drink size

            if(!arr[1].equals("S") && !arr[1].equals("M") && !arr[1].equals("L"))
            {
                //System.out.println("Invalid drink size");
                validity = false;
            }

            //Check invalid drink type

            if(!arr[2].equals("soda") && !arr[2].equals("punch") && !arr[2].equals("tea"))
            {
                //System.out.println("Invalid drink type");
                validity = false;
            }

            //check for garbage characters

            try
            {
                Float.valueOf(arr[3]);
                Integer.valueOf(arr[4]);
            }catch(Exception e)
            {
                validity = false;
            }
        }
        return validity;
    }

    //This function is used to calculate the amountSpentDrink in terms of ounces

    public static float amountSpentDrink(String[] arr)
    {
        float amountSpent = 0;

        //These are the calculation options if the size of the drink is a small

        if(arr[1].equals("S"))
        {
            if(arr[2].equals("soda"))
            {
                amountSpent = (float) (0.20 * 12 * Float.valueOf(arr[4]));
            }
            else if(arr[2].equals("punch"))
            {
                amountSpent = (float) (0.12 * 12 * Float.valueOf(arr[4]));
            }
            else if(arr[2].equals("tea"))
            {
                amountSpent = (float) (0.15 * 12 * Float.valueOf(arr[4]));
            }
        }

        //These are the calculation options if the size of the drink is a medium

        else if(arr[1].equals("M"))
        {
            if(arr[2].equals("soda"))
            {
                amountSpent = (float) (0.20 * 20 * Float.valueOf(arr[4]));
            }
            else if(arr[2].equals("punch"))
            {
                amountSpent = (float) (0.12 * 20 * Float.valueOf(arr[4]));
            }
            else if(arr[2].equals("tea"))
            {
                amountSpent = (float) (0.15 * 20 * Float.valueOf(arr[4]));
            }
        }

        //These are the calculation options if the size of the drink is a large

        else if(arr[1].equals("L")) {
            if (arr[2].equals("soda")) {
                amountSpent = (float) (0.20 * 32 * Float.valueOf(arr[4]));
            } else if (arr[2].equals("punch")) {
                amountSpent = (float) (0.12 * 32 * Float.valueOf(arr[4]));
            } else if (arr[2].equals("tea")) {
                amountSpent = (float) (0.15 * 32 * Float.valueOf(arr[4]));
            }
        }

        //It will return the calculated amountSpent variable

        return amountSpent;
    }

    //This function is used to determine the total cost of the personalization of the glass

    public static float amountSpentGraphic(String[] arr)
    {
        //These are the variables used in this function

        float area = 0;
        float price = 0;

        //These are the used to determine the area based on the cup sizes

        if(arr[1].equals("S"))
        {
            area = (float) (2 * 3.14 * (4/2) * 4.5);
        }
        else if(arr[1].equals("M"))
        {
            area = (float) (2 * 3.14 * (4.5/2) * 5.75);
        }
        else if(arr[1].equals("L"))
        {
            area = (float) (2 * 3.14 * (5.5/2) * 7);
        }

        //This is used to determine the price based on the area times the price

        price = area * Float.valueOf(arr[3]);

        //It will return the price value

        return price;
    }

    //This is used to determine the currentStatus of the customer

    public static String currentStatus(String[] arr)
    {

        //This is checking if the status of the customer is Regular

        for(int j = 0; j < regularCustomerArray.length; j++)
        {
            if(regularCustomerArray[j].getGuestId().equals(arr[0]))
            {
                return "Regular";
            }
        }

        //This is checking if the status of the customer is Gold and Platinum

        for(int j = 0; j < preferredCustomerArray.length; j++)
        {
            if(preferredCustomerArray[j].getGuestId().equals(arr[0]))
            {
                if(arr[4].contains("%"))
                {
                    return "Gold";
                }
                else
                {
                    return "Platinum";
                }
            }
        }

        return "";
    }

    //This is dealing with moving the contents from the regular array into the gold array

    public static void regularToGold(String[] arr)
    {
        //Create the new arrays that we are going to deal with in this function

        Customer[] newRegularCustomer = new Customer[regularCustomerArray.length - 1];
        Customer[] newPreferredCustomerArr = new Customer[preferredCustomerArray.length + 1];
        Customer newPreferredCustomer = new PreferredCustomerGold();

        boolean person = false;

        //This is dealing with updating the regular customer

        for(int i =0; i < regularCustomerArray.length; i++){
            //System.out.println(i);
            if(regularCustomerArray[i].getGuestId().equals(arr[0])) {
                newPreferredCustomer = new PreferredCustomerGold(regularCustomerArray[i].getFirstName(), regularCustomerArray[i].getLastName(),regularCustomerArray[i].getGuestId(), regularCustomerArray[i].getAmountSpent());
                person = true;
            }
            else if(person == false)
            {
                newRegularCustomer[i] = regularCustomerArray[i];
            }
            else
            {
                newRegularCustomer[i - 1] = regularCustomerArray[i];
            }
        }

        //This is updating the regularCustomerArray with the contents

        regularCustomerArray = newRegularCustomer;

        //This is dealing with moving it to a preferred customer

        for(int i = 0; i < preferredCustomerArray.length; i++){
            newPreferredCustomerArr[i] = preferredCustomerArray[i];
        }

        //This is updating the preferredCustomerArray with the contents

        newPreferredCustomerArr[newPreferredCustomerArr.length - 1] = newPreferredCustomer;

        preferredCustomerArray = newPreferredCustomerArr;

    }

    //This is dealing with moving the contents from the regular array into the platinum array

    public static void regularToPlatinum(String[] arr)
    {
        //Create the new arrays that we are going to deal with in this function

        Customer[] newRegularCustomer = new Customer[regularCustomerArray.length - 1];
        Customer[] newPreferredCustomerArr = new Customer[preferredCustomerArray.length + 1];
        Customer newPreferredCustomer = new PreferredCustomerPlatinum();

        boolean person = false;

        //This is dealing with updating the regular customer

        for(int i = 0; i < regularCustomerArray.length; i++){
            //System.out.println(i);
            if(regularCustomerArray[i].getGuestId().equals(arr[0])) {
                newPreferredCustomer = new PreferredCustomerPlatinum(regularCustomerArray[i].getFirstName(), regularCustomerArray[i].getLastName(),regularCustomerArray[i].getGuestId(), regularCustomerArray[i].getAmountSpent());
                person = true;
            }
            else if(person == false)
            {
                for(int j = 0; j < newRegularCustomer.length; j++)
                {
                    newRegularCustomer[j] = regularCustomerArray[j];
                }
            }
            else
            {
                newRegularCustomer[i - 1] = regularCustomerArray[i];
            }
        }

        //This is updating the regularCustomerArray with the contents

        regularCustomerArray = newRegularCustomer;

        //This is dealing with moving it to a preferred customer

        for(int i = 0; i < preferredCustomerArray.length; i++){
            newPreferredCustomerArr[i] = preferredCustomerArray[i];
        }

        //This is updating the preferredCustomerArray with the contents

        newPreferredCustomerArr[newPreferredCustomerArr.length - 1] = newPreferredCustomer;

        preferredCustomerArray = newPreferredCustomerArr;
    }

    //This is moving a gold object to a platinum object

    public static void goldToPlatinum(String[] arr)
    {
        //will move the gold object and convert it to platinum

        for(int i = 0; i < preferredCustomerArray.length; i++)
        {
            if(preferredCustomerArray[i].getGuestId() == arr[0])
            {
                Customer newPreferredCustomer;
                newPreferredCustomer = new PreferredCustomerPlatinum(preferredCustomerArray[i].getFirstName(), preferredCustomerArray[i].getLastName(), preferredCustomerArray[i].getGuestId(), preferredCustomerArray[i].getAmountSpent());
                preferredCustomerArray[i] = newPreferredCustomer;
            }
        }
    }

    //This will apply the discount for the gold customer

    public static float applyGoldDiscount(float amountSpent, String[] arr)
    {
        float amount = 0;
        if(currentStatus(arr).equals("Regular"))
        {
            amount = amountSpent - ((amountSpent * (5)) / 100);
        }
        else if(currentStatus(arr).equals("Gold"))
        {
            Integer discount = 0;
            for(int i = 0; i < preferredCustomerArray.length; i++)
            {
                if(preferredCustomerArray[i].getGuestId().equals(arr[0]))
                {
                    PreferredCustomerGold newGoldCustomer = (PreferredCustomerGold) preferredCustomerArray[i];
                    discount = newGoldCustomer.getDiscountPercentage();
                }
            }

            amount = amountSpent - ((amountSpent * discount) / 100);

        }
        return amount;
    }

    //This will update the bonus bucks that are needed for the platinum object

    public static void updateBonusBucks(float amountSpent, String[] arr)
    {
        Integer bonusBucks;
        float totalAmountSpent;
        for(int i = 0; i < preferredCustomerArray.length; i++)
        {
            if(preferredCustomerArray[i].getGuestId().equals(arr[0]))
            {
                System.out.println("amountSpent" + " " + amountSpent);
                System.out.println(preferredCustomerArray[i].getAmountSpent());
                totalAmountSpent = amountSpent + preferredCustomerArray[i].getAmountSpent();
                bonusBucks = (int)((totalAmountSpent - 200)/5);
                PreferredCustomerPlatinum newPlatinumCustomer = (PreferredCustomerPlatinum) preferredCustomerArray[i];

                System.out.println("bonusBucks" + " " + bonusBucks);

                newPlatinumCustomer.setBonusBucks(bonusBucks);
                //preferredCustomerArray[i].setBonusBucks(bonusBucks);
            }

        }
    }

    //This will apply the bonus bucks and will only be called if it a platinum status

    public static float applyBonusBucks(float amountSpent, String[] arr)
    {
        //Inside we get the current bonus bucks
        Integer bonusBucks = 0;
        float amount = 0;
        for(int i = 0; i < preferredCustomerArray.length; i++)
        {
            if(preferredCustomerArray[i].getGuestId() == arr[0])
            {
                PreferredCustomerPlatinum newPlatinumCustomer = (PreferredCustomerPlatinum) preferredCustomerArray[i];
                bonusBucks = newPlatinumCustomer.getBonusBucks();
                //bonusBucks = preferredCustomerArray[i].getBonusBucks();
                amount = amountSpent - bonusBucks;
                newPlatinumCustomer.setBonusBucks(0);
                //preferredCustomerArray[i].setBonusBucks(0);
            }

        }

        return amount;
    }

    //This is updating the array contents to the appropriate .dat files

    public static void updateFiles() throws IOException {

        File customerPath = new File("src/customer2.dat");
        FileWriter customer = new FileWriter(customerPath);

        File preferredPath = new File("src/preferred2.dat");
        FileWriter preferred = new FileWriter(preferredPath);

        for(int i = 0; i < preferredCustomerArray.length; i++)
        {
            //System.out.println(preferredCustomerArray[i].print());
            preferred.write(preferredCustomerArray[i].print() + "\n");
        }

        preferred.close();

        for(int i = 0; i < regularCustomerArray.length; i++)
        {
            //System.out.println(regularCustomerArray[i].print());
            customer.write(regularCustomerArray[i].print() + "\n");
        }

        customer.close();

    }

}

