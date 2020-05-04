//Programmer's Name: Medha Aiyah
//Programmer's Net Id: mva170001

public class PreferredCustomerPlatinum extends Customer
{
    //Private Data Fields

    Integer bonusBucks;

    // Default Constructor

    public PreferredCustomerPlatinum()
    {
        super();
        this.bonusBucks = 0;
    }

    //Overloaded constructor

    PreferredCustomerPlatinum(String first, String last, String id, float spent)
    {
        super(first, last, id, spent);
        this.bonusBucks = (int) (spent - 200)/5;
    }

    PreferredCustomerPlatinum(String first, String last, String id, float spent, Integer bucks)
    {
        super(first, last, id, spent);
        this.bonusBucks = bucks;
    }

    //Getter and Setter for the member variable in this class

    public Integer getBonusBucks()
    {
        return bonusBucks;
    }
    public void setBonusBucks(Integer bucks)
    {
        this.bonusBucks = bucks;
    }

    //This is the function that will be used to print out the contents in the files

    public String print()
    {
        return (getGuestId() + " " + getFirstName() + " " + getLastName() + " " + getAmountSpent()  + " " + getBonusBucks());
    }
}
