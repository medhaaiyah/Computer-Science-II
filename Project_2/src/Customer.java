//Programmer's Name: Medha Aiyah
//Programmer's Net Id: mva170001
/*
This is the super class and has the main data fields which will also be included in the PreferredCustomerGold class
and the PreferredCustomerPlatinum class.
 */

public class Customer
{
    //Private Data Fields

    private String firstName;
    private String lastName;
    private String guestId;
    private float amountSpent;

    //Default constructor

    public Customer()
    {
        this.firstName = null;
        this.lastName = null;
        this.guestId = null;
        this.amountSpent = 0;
    }

    //Overloaded constructor

    Customer(String first, String last, String id, float spent)
    {
        this.firstName = first;
        this.lastName = last;
        this.guestId = id;
        this.amountSpent = spent;
    }

    //Getter's and Setter's for each of the member variables of the class

    public String getFirstName()
    {
        return firstName;
    }
    public void setFirstName(String first)
    {
        this.firstName = first;
    }

    public String getLastName()
    {
        return lastName;
    }
    public void setLastName(String last)
    {
        this.lastName = last;
    }

    public String getGuestId()
    {
        return guestId;
    }
    public void setGuestId(String id)
    {
        this.guestId = id;
    }

    public float getAmountSpent()
    {
        return amountSpent;
    }
    public void setAmountSpent(float spent)
    {
        this.amountSpent = spent;
    }

    //This is the function that will be used to print out the contents in the files

    public String print()
    {
        return (getGuestId() + " " + getFirstName() + " " + getLastName() + " " + getAmountSpent());
    }
}
