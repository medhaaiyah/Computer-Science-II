//Programmer's Name: Medha Aiyah
//Programmer's Net Id: mva170001
/*
*  The BaseNode is an abstract class which has four main member functions: the row, the seat, reserved, and the
*  ticketType. There is an overloaded constructor as well as getters and setters which will be used in the functions
*  which are called in the auditorium class.
 */

public abstract class BaseNode {

    //These are the member variables in the base node class

    private int row;
    private char seat;
    private boolean reserved;
    private char ticketType;

    //This is the overloaded constructor

    public BaseNode(int row, char seat, boolean reserved, char ticketType) {
        this.row = row;
        this.seat = seat;
        this.reserved = reserved;
        this.ticketType = ticketType;
    }


    //These are the getters in the base node class

    public char getSeat() {
        return seat;
    }

    public char getTicketType() {
        return ticketType;
    }

    public int getRow() {
        return row;
    }

    public boolean getReserved(){
        return reserved;
    }

    //These are the setters in the base node class

    public void setSeat(char seat) {
        this.seat = seat;
    }

    public void setTicketType(char ticketType) {
        this.ticketType = ticketType;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setReserved(boolean reserved) {
        this.reserved = reserved;
    }

}
