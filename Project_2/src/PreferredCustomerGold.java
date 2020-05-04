//Programmer's Name: Medha Aiyah
//Programmer's Net Id: mva170001
/*
This class is mostly referring to the gold members.
 */

public class PreferredCustomerGold extends Customer
{
    //Private Data Fields

    private Integer discountPercentage;

    public PreferredCustomerGold()
    {
        super();
        this.discountPercentage = 0;
    }

    //Overloaded constructor

    PreferredCustomerGold(String first, String last, String id, float spent)
    {
        super(first, last, id, spent);
        if(spent >= 50 && spent < 100)
        {
            this.discountPercentage = 5;
        }
        else if(spent >= 100 && spent < 150)
        {
            this.discountPercentage = 10;
        }
        else if(spent >= 150)
        {
            this.discountPercentage = 15;
        }

    }

    PreferredCustomerGold(String first, String last, String id, float spent, Integer percentage)
    {
        super(first, last, id, spent);
        this.discountPercentage = percentage;
    }

    //Getter and Setter for the member variable in this class

    public Integer getDiscountPercentage()
    {
        return discountPercentage;
    }
    public void setDiscountPercentage(Integer percentage)
    {
        this.discountPercentage = percentage;
    }

    //This is the function that will be used to print out the contents in the files

    public String print()
    {
       return (getGuestId() + " " + getFirstName() + " " + getLastName() + " " + getAmountSpent() + " " + getDiscountPercentage() + "%" );
    }

}
