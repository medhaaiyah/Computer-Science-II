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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.SQLOutput;
import java.util.Scanner;
import java.io.RandomAccessFile;

public class MainOne {

    //These are the two arrays that are going to be used in this class

    Customer [] regularCustomerArray = regularCustomerArray();
    Customer [] preferredCustomerArray = preferredCustomerArray();

    public MainOne() throws FileNotFoundException {
    }

    //This main function is used to bring all of the functions and update the two arrays according

    public void main(String[] args) throws FileNotFoundException {

        //This used to refer to the order.dat file

        File ordersPath = new File("src/orders.dat");
        Scanner orders = new Scanner(ordersPath);

        //There will be a while loop to take into account the order's information

        while(orders.hasNextLine())
        {
            String line = orders.nextLine();
            String[] lineArr = line.split(" ");

            //Create a variable that adds the spent drink with the spent graphic

            float totalAmountSpent = amountSpentDrink(lineArr) + amountSpentGraphic(lineArr);

            //These series of if statements are used to determine how to update the arrays

            //This is used to determine whether there is invalid data in the orders.dat

            if (invalidData(lineArr) == false)
            {
                continue;
            }

            //This is used to determine status of each customer

            String status = currentStatus(lineArr);

            //This is the series of if statements (or options) that can happen for the Regular customer

            if(status == "Regular")
            {
                if(totalAmountSpent >=50)
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
        }

        //This function is called at the end to update the customer.dat file and the preferred.dat file

        updateFiles();
    }

    //This function is used to look at the customer.dat file and include it in the array

    public Customer [] regularCustomerArray () throws FileNotFoundException {

        //This is going to refer to the customer.dat file

        File customerPath = new File("src/customer.dat");
        Scanner customer = new Scanner(customerPath);
        Scanner customerOpen = new Scanner(customerPath);

        //This is used to increment and figure out how many rows there are in the customer.dat file

        int newLineRegularCustomer = 0;

        while(customer.hasNextLine())
        {
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
            Customer customerOne = new Customer();
            String line = customerOpen.nextLine();
            String [] lineArr = line.split(" ");
            customerOne.setFirstName(lineArr[1]);
            customerOne.setLastName(lineArr[2]);
            customerOne.setGuestId(lineArr[0]);
            customerOne.setAmountSpent(Float.valueOf(lineArr[3]));
            regularCustomer[customerIndex] = customerOne;

            customerIndex++;
        }

        //This is going to return the regularCustomer array

        return regularCustomer;
    }

    //This function is used to look at the preferred.dat file and include it in the array

    public Customer [] preferredCustomerArray () throws FileNotFoundException
    {
        //This is going to refer to the preferred.dat file

        File preferredPath = new File("src/preferred.dat");
        Scanner preferred = new Scanner(preferredPath);
        Scanner preferredOpen = new Scanner(preferredPath);

        //This is used to increment and figure out how many rows there are in the preferred.dat file

        int newLinePreferredCustomer = 0;

        while (preferred.hasNextLine()) {
            preferred.nextLine();
            newLinePreferredCustomer++;
        }

        //This is the preferred customer array which will store the info in the file

        Customer[] preferredCustomer = new Customer[newLinePreferredCustomer];
        preferred.close();

        int preferredCustomerIndex = 0;

        //This while loop with store the contents in the array line by line

        while (preferredOpen.hasNextLine()) {
            String line = preferredOpen.nextLine();
            String[] lineArr = line.split(" ");

            //It will store a gold object into the array

            if (lineArr[4].contains("%")) {
                String[] percentageSign = lineArr[4].split("%");

                PreferredCustomerGold preferCustomerGold = new PreferredCustomerGold(lineArr[1], lineArr[2], lineArr[0], Float.valueOf(lineArr[3]), Integer.valueOf(percentageSign[0]));
                preferredCustomer[preferredCustomerIndex] = preferCustomerGold;
            }

            //It will store a platinum object into the array

            else {
                PreferredCustomerPlatinum preferCustomerPlatinum = new PreferredCustomerPlatinum(lineArr[1], lineArr[2], lineArr[0], Float.valueOf(lineArr[3]), Integer.valueOf(lineArr[4]));
                preferredCustomer[preferredCustomerIndex] = preferCustomerPlatinum;
            }
            preferredCustomerIndex++;
        }

        //It will return the preferredCustomer array

        return preferredCustomer;

    }

    //This function will determine whether there is invalid data in the orders.dat file

    public boolean invalidData (String [] arr)
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
            if(preferredCustomerArray[i].getGuestId().equals(arr[0]))
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

        return validity;
    }

    //This function is used to calculate the amountSpentDrink in terms of ounces

    public float amountSpentDrink(String [] arr)
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

    public float amountSpentGraphic(String [] arr)
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

        if(arr[2].equals("soda"))
        {
            price = (float) (area * 0.20);
        }
        else if(arr[2].equals("punch"))
        {
            price = (float)(area * 0.15);
        }
        else if(arr[2].equals("tea"))
        {
            price = (float)(area * 0.12);
        }

        //It will return the price value

        return price;
    }

    //This is used to determine the currentStatus

    public String currentStatus(String [] arr)
    {

        String status = null;
        if (arr[0].equals(String.valueOf(regularCustomerArray[0].getGuestId())))
        {
            status = "Regular";
        }
        else if (arr[0].equals(String.valueOf(preferredCustomerArray[0].getGuestId())))
        {
            if(arr[4].contains("%"))
            {
                status = "Gold";
            }
            else
            {
                status = "Platinum";
            }
        }
        return status;
    }
    public void regularToGold(String [] arr)
    {
        Customer[] newRegularCustomer = new Customer[regularCustomerArray.length-1];
        Customer[] newPreferredCustomerArr = new Customer[preferredCustomerArray.length+1];
        Customer newPreferredCustomer = new PreferredCustomerGold();
        int newArrayIndex=0;
        for(int i =0; i<regularCustomerArray.length; i++){
            if(regularCustomerArray[i].getGuestId() == arr[0]) {
                newPreferredCustomer = new PreferredCustomerGold(regularCustomerArray[i].getFirstName(), regularCustomerArray[i].getLastName(),regularCustomerArray[i].getGuestId(), regularCustomerArray[i].getAmountSpent());
            }
            else{
                newRegularCustomer[newArrayIndex] = regularCustomerArray[i];
                newArrayIndex++;
            }
        }
        regularCustomerArray = newRegularCustomer;
        for(int i = 0; i<preferredCustomerArray.length; i++){
            newPreferredCustomerArr[i] = preferredCustomerArray[i];
        }

        newPreferredCustomerArr[newPreferredCustomerArr.length - 1] = newPreferredCustomer;

        preferredCustomerArray = newPreferredCustomerArr;

    }
    public void regularToPlatinum(String [] arr)
    {
        Customer[] newRegularCustomer = new Customer[regularCustomerArray.length-1];
        Customer[] newPreferredCustomerArr = new Customer[preferredCustomerArray.length+1];
        Customer newPreferredCustomer = new PreferredCustomerPlatinum();
        int newArrayIndex=0;
        for(int i =0; i<regularCustomerArray.length; i++){
            if(regularCustomerArray[i].getGuestId() == arr[0]) {
                newPreferredCustomer = new PreferredCustomerPlatinum(regularCustomerArray[i].getFirstName(), regularCustomerArray[i].getLastName(),regularCustomerArray[i].getGuestId(), regularCustomerArray[i].getAmountSpent());
            }
            else{
                newRegularCustomer[newArrayIndex] = regularCustomerArray[i];
                newArrayIndex++;
            }
        }
        regularCustomerArray = newRegularCustomer;
        for(int i = 0; i<preferredCustomerArray.length; i++){
            newPreferredCustomerArr[i] = preferredCustomerArray[i];
        }

        newPreferredCustomerArr[newPreferredCustomerArr.length - 1] = newPreferredCustomer;

        preferredCustomerArray = newPreferredCustomerArr;
    }
    public void goldToPlatinum(String [] arr)
    {
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
    public float applyGoldDiscount(float amountSpent, String [] arr)
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
    public void updateBonusBucks(float amountSpent, String [] arr)
    {
            Integer bonusBucks = 0;
            float totalAmountSpent = 0;
            for(int i = 0; i < preferredCustomerArray.length; i++)
            {
                if(preferredCustomerArray[i].getGuestId() == arr[0])
                {
                    totalAmountSpent = amountSpent + preferredCustomerArray[i].getAmountSpent();
                    bonusBucks = (int)((totalAmountSpent - 200)/5);
                    PreferredCustomerPlatinum newPlatinumCustomer = (PreferredCustomerPlatinum) preferredCustomerArray[i];

                    newPlatinumCustomer.setBonusBucks(bonusBucks);
                    //preferredCustomerArray[i].setBonusBucks(bonusBucks);
                }

            }
    }
    public float applyBonusBucks(float amountSpent,String [] arr)
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
    public void updateFiles() throws FileNotFoundException {
        File customerPath = new File("src/customer.dat");
        PrintWriter customer = new PrintWriter(customerPath);

        File preferredPath = new File("src/preferred.dat");
        PrintWriter preferred = new PrintWriter(preferredPath);

        for(int i = 0; i < preferredCustomerArray.length; i++)
        {
            preferred.print(preferredCustomerArray[i]);
        }

        for(int i = 0; i < regularCustomerArray.length; i++)
        {
            customer.print(regularCustomerArray[i]);
        }

    }

}
