//Programmer's Name: Medha Aiyah
//Programmer's Net Id: mva170001
/**
 * This is the node class. In the node class, it is creating the ways that we will use to go through the binary tree
 * (such as moving left or right). It also creates the member variables that are needed to store the information in a
 * certain node (such as the title of the movie, whether it is available, or whether a particular movie is rented)
 */

public class Node {

    //These are the member variables in this class

    private String title;
    private int available;
    private int rented;
    private Node left;
    private Node right;

    /**
     * Method: Node(String title, int available, int rented)
     * Description: This constructor is being used to initialize the contents that are going to be stored in a node on
     *              the binary tree
     * @param: title - The name of the movie
     * @param: available - The number of copies that are available
     * @param: rented - The number of copies that are rented
     * @return: Since it is void there is no return statement here
     */

    public Node(String title, int available, int rented)
    {
        this.title = title;
        this.available = available;
        this.rented = rented;
        this.left = null;
        this.right = null;
    }

    /**
     * Method: getAvailable()
     * Description: This is a getter method for the available member variable
     * @return: It returns the member variable available.
     */

    public int getAvailable() {
        return available;
    }

    /**
     * Method: setAvailable(int available)
     * Description: This is a setter method for the available member variable
     * @param: available - number of copies that are available
     * @return: It does not return anything since it is a void
     */

    public void setAvailable(int available) {
        this.available = available;
    }

    /**
     * Method: getTitle()
     * Description: This is a getter method for the title member variable
     * @return: It returns the member variable title.
     */

    public String getTitle() {
        return title;
    }

    /**
     * Method: setTitle(String t)
     * Description: This is a setter method for the title member variable
     * @param: title - name of the title for a particular movie
     * @return: It does not return anything since it is a void
     */

    public void setTitle(String t) {title = t;}

    /**
     * Method: getLeft()
     * Description: This is a getter method for the left member variable
     * @return: It returns the member variable left.
     */

    public Node getLeft() {
        return left;
    }

    /**
     * Method: setLeft(Node left)
     * Description: This is a setter method for the left member variable
     * @param: left - aids with the way the binary tree works
     * @return: It does not return anything since it is a void
     */

    public void setLeft(Node left) {
        this.left = left;
    }

    /**
     * Method: getRented()
     * Description: This is a getter method for the rented member variable
     * @return: It returns the member variable rented.
     */

    public int getRented() {
        return rented;
    }

    /**
     * Method: setRented(int rented)
     * Description: This is a setter method for the rented member variable
     * @param: rented - number of copies that are rented
     * @return: It does not return anything since it is a void
     */

    public void setRented(int rented) {
        this.rented = rented;
    }

    /**
     * Method: getRight()
     * Description: This is a getter method for the right member variable
     * @return: It returns the member variable right.
     */

    public Node getRight() {
        return right;
    }

    /**
     * Method: setRight(Node right)
     * Description: This is a setter method for the right member variable
     * @param: right - aids with the way the binary tree works
     * @return: It does not return anything since it is a void
     */

    public void setRight(Node right) {
        this.right = right;
    }
}
