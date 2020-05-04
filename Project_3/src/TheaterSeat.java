//Programmer's Name: Medha Aiyah
//Programmer's Net Id: mva170001
/*
* This is the TheaterSeat class. The TheaterSeat class is derived from the BaseNode class. This class will create
* the nodes that are needed to create the auditorium grid and to move through the linked list that we are creating. The
* member variables that we are using in this class include the four main directions: up, down, left, and right.
 */

public class TheaterSeat extends BaseNode{

    //These are the member variables in the TheaterSeat class

    private TheaterSeat up;
    private TheaterSeat down;
    private TheaterSeat left;
    private TheaterSeat right;

    public TheaterSeat()
    {
        super(1, 'A', true, 'A');
        this.up = null;
        this.down = null;
        this.left = null;
        this.right = null;
    }

    //This is the overloaded constructor in the TheaterSeat class

    public TheaterSeat(int row, char seat, boolean reserved, char ticketType, TheaterSeat up, TheaterSeat down, TheaterSeat left, TheaterSeat right) {
        super(row, seat, reserved, ticketType);
        this.up = up;
        this.down = down;
        this.left = left;
        this.right = right;
    }

    //These are the getters in the TheaterSeat class

    public TheaterSeat getLeft() {
        return left;
    }

    public TheaterSeat getDown() {
        return down;
    }

    public TheaterSeat getRight() {
        return right;
    }

    public TheaterSeat getUp() {
        return up;
    }

    //These are the setters in the TheaterSeat class

    public void setLeft(TheaterSeat left) {
        this.left = left;
    }

    public void setDown(TheaterSeat down) {
        this.down = down;
    }

    public void setRight(TheaterSeat right) {
        this.right = right;
    }

    public void setUp(TheaterSeat up) {
        this.up = up;
    }
}
