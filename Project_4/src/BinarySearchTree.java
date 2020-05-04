//Programmer's Name: Medha Aiyah
//Programmer's Net Id: mva170001
/**
 * Welcome to the BinarySearchTree class where all the magic happens. Over here the binary search tree creates the root
 * node. From there it will parse through the inventory file and use the insertion function to store all the contents
 * into a binary file. From there it will read the transactions and then make the appropriate changes into the binary
 * file. If a certain line in the transaction.log is not valid, then it will print that line into the error.log.
 * At the very end it will print the updated binary file with the in the redbox_kiosk.txt.
 */

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BinarySearchTree {

    //Member variable in this class

    private Node root;

    /**
     * Method: BinarySearchTree()
     * Description: This constructor is being used to call the three main functions that is reading the inventory file,
     *              reading the transaction file, performing the main transactions, creating the error.log file, and
     *              creating the redbox_kiosk.txt file
     * @return: This constructor is not going to return anything
     */

    public BinarySearchTree() throws IOException {
        readInventory();
        readTransactions();
        report();
    }

    /**
     * Method: getRoot()
     * Description: This is a getter method for the root member variable
     * @return: It returns the member variable root.
     */

    public Node getRoot() {
        return root;
    }

    /**
     * Method: setRoot(Node root)
     * Description: This is a setter method for the root member variable
     * @param: root - the head of the binary tree
     * @return: It does not return anything since it is a void
     */

    public void setRoot(Node root) {
        this.root = root;
    }

    //////////////////////////////////THE THREE METHODS THAT ARE REQUIRED IN THIS CLASS////////////////////////////////

    //THIS IS THE INSERT FUNCTION AND IT IS RECURSIVE BY REFERRING TO THE insertRec() FUNCTION//

    /**
     * Method: insert(Node node)
     * Description: This is the insert method. In order to make this method recursive, it will call this other function
     *              called insertRec() which has all the code that is required for the insert to work
     * @param: node - this is used in order to form a new node
     * @return: This constructor is not going to return anything since it is void
     */

    public void insert(Node node) {
        root = insertRec(root, node);
    }

    /**
     * Method: insertRec(Node iter, Node node)
     * Description: The insertRec function is a recursive function that is going to be used to insert a new node in the
     *              binary search tree.
     * @param: iter - referring to the current node (root) that is looking at in order to create a new node
     * @param: node - this is used in order to form a new node
     * @return: This method will return the current node
     */

    public Node insertRec(Node iter, Node node) {

        //If the tree is empty, return a new node

        if (iter == null) {
            return node;
        }

        //If the tree is not empty then it will go through these if statements

        //If the value that you get is negative (so if the size of the title is smaller) then put on the left side

        if (node.getTitle().compareToIgnoreCase(iter.getTitle()) < 0)
        {
            iter.setLeft(insertRec(iter.getLeft(), node));
        }

        //If the value that you get is positive (so if the size of the title is larger) then put on the right side

        else if (node.getTitle().compareToIgnoreCase(iter.getTitle()) > 0)
        {
            iter.setRight(insertRec(iter.getRight(), node));
        }

        //This is what will happen if it is the same node it is referring to

        else if (node.getTitle().compareToIgnoreCase(iter.getTitle()) == 0)
        {
            return iter;
        }

        //return the (unchanged) node pointer

        return iter;
    }

    //write the search function algorithm (recursive)
    /**
     * Method: search(Node iter, String title)
     * Description: The search function is a recursive function that is going to be used to search for a node in the
     *              binary search tree. It is a recursive function.
     * @param: iter - referring to the current node (root) that is looking at in order to create a new node
     * @param: title - used in order to search for a particular node
     * @return: This method will return the current node
     */

    public Node search(Node iter, String title)
    {
        //If the a node is not there, it will return null

        if(iter==null) return null;

        //This is what will happen if it is a positive value

        if (title.compareToIgnoreCase(iter.getTitle()) > 0)
            return search(iter.getRight(), title);


        //This is what will happen if it is a negative value

        if (title.compareToIgnoreCase(iter.getTitle()) < 0)
            return search(iter.getLeft(), title);

        //This is what will happen if the node is unchanged

        return iter;
    }

    //write the delete function algorithm

    /**
     * Method: deleteKey(String title)
     * Description: This is the deleteKey method. In order to make this method recursive, it will call this other function
     *              called deleteRec() which has all the code that is required for the insert to work
     * @param: title - this is going to be used to find the node that we want to delete
     * @return: This method is not going to return anything since it is void
     */

    public void deleteKey(String title)
    {
        root = deleteRec(root, title);
    }

    /**
     * Method: deleteRec(Node iter, String title)
     * Description: The deleteRec function is a recursive function that is going to be used to delete a new node in the
     *              binary search tree.
     * @param: iter - referring to the current node (root) that is looking at in order to create a new node
     * @param: title - this is going to be used to find the node that we want to delete
     * @return: This method will return the current node
     */

    public Node deleteRec(Node iter, String title)
    {
         //This is what will happen if the tree is empty

        if (iter == null)  return null;

        // This is what will happen if the tree is not empty

        if (title.compareToIgnoreCase(iter.getTitle()) < 0)
            iter.setLeft(deleteRec(iter.getLeft(), title));
        else if (title.compareToIgnoreCase(iter.getTitle()) > 0)
            iter.setRight(deleteRec(iter.getRight(), title));

        else
        {
            // node with only one child or no child

            if (iter.getLeft() == null)
                return iter.getRight();
            else if (iter.getRight() == null)
                return iter.getLeft();

            // node with two children: Get the inorder successor (smallest
            // in the right subtree)

            iter.setTitle(minValue(iter.getRight()));

            // Delete the inorder successor

            iter.setRight(deleteRec(iter.getRight(), iter.getTitle()));
        }

        return iter;
    }

    /**
     * Method: minValue(Node iter)
     * Description: The minValue function is going to be dealing with getting the title for the left-hand side of the
     *              binary search tree. This will be called in the deleteRec() function
     * @param: iter - referring to the current node (root) that is looking at in order to create a new node
     * @return: This method will return the title of a node
     */

    public String minValue(Node iter){
        while(iter.getLeft()!=null){
            iter = iter.getLeft();
        }
        return iter.getTitle();
    }

    ////////////////////////////////FUNCTIONS USED IN THIS CLASS DEALING WITH TRANSACTIONS//////////////////////////////

    /**
     * Method: addingCopies(String title, int quantity)
     * Description: In this function, it will add a certain number of copies to a certain node
     * @param: title - used to search for a particular title
     * @param: quantity - this is used to add the following amount to a node
     */

    public void addingCopies(String title, int quantity)
    {
        //This is going to be used to search for a node

        Node findingNode = search(root, title);

        //This is what will happen if the node is not found

        if(findingNode == null)
        {
            Node newNode = new Node(title, quantity, 0);
            insert(newNode);
        }

        //This will happen if the node is found

        else
        {
            findingNode.setAvailable(quantity + findingNode.getAvailable());
        }
    }

    /**
     * Method: removingCopies(Node node, int quantity, String line)
     * Description: In this function, it will remove a certain number of copies to a certain node
     * @param: node - this is referring to a certain portion of the tree
     * @param: quantity - this is used to remove the following amount from the node
     * @param: line - a line from the transaction.log
     */

    public void removingCopies(Node node, int quantity, String line) throws IOException {

        //If the node is not available

        if((node.getAvailable() - quantity)<0)
            invalidLine(line);

        //This is what will happen if the node is available

        else{
            node.setAvailable(node.getAvailable() - quantity);

            //This is what will happen if there is 0 available and 0 rented.

            if(node.getAvailable() == 0 && node.getRented() == 0)
            {
                //Deletes the node

                deleteKey(node.getTitle());
            }
        }
    }

    /**
     * Method: rentDVD(Node node, String title, String line)
     * Description: In this function, it will increment the rent number and decrement the available number
     * @param: node - this is referring to a certain portion of the tree
     * @param: title - the title of a certain movie
     * @param: line - a line from the transaction.log
     */

    public void rentDVD(Node node, String title, String line) throws IOException {

        //If the node is not available

        if(node.getAvailable() -1 <0)
            invalidLine(line);

        //This is what will happen if the node is available

       else{

           //decrease the number of available

            node.setAvailable(node.getAvailable() - 1);

            //increase the number of rent

            node.setRented(node.getRented() + 1);
        }
    }

    /**
     * Method: returnDVD(Node node, String title, String line)
     * Description: In this function, it will decrement the rent number and increment the available number
     * @param: node - this is referring to a certain portion of the tree
     * @param: title - the title of a certain movie
     * @param: line - a line from the transaction.log
     */

    public void returnDVD(Node node, String title, String line) throws IOException {

        //If the node is not available

        if(node.getRented()<=0)
            invalidLine(line);

        //This is what will happen if the node is available

        else{

            //increase the number of available

            node.setAvailable(node.getAvailable() + 1);

            //decrease the number of rent

            node.setRented(node.getRented() - 1);
        }
    }

    /////////////////////////////FUNCTIONS USED IN THIS CLASS DEALING WITH FILE I/O/////////////////////////////////////

    /**
     * Method: readInventory()
     * Description: In this function, it is going to read the contents from the inventory.dat file and store the
     *              information into the binary search tree.
     * @param: there are no parameters
     * @return: there is nothing that is going to return since it is void
     */

    private void readInventory(){

        //This is the name of the file

        String fileName = "inventory.dat";
        Scanner fileInput = null;

        //Making sure that there is no errors in the file

        try {
            fileInput = new Scanner(new File(fileName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //This is going to be reading the file line by line

        ArrayList<String> linesOfInput = new ArrayList<String>();

        while (fileInput.hasNextLine()) {
            String line = fileInput.nextLine();
            linesOfInput.add(line);
        }

        //Each line will look at the the relevant contents in that line and then insert them appropriately

        for(String line: linesOfInput) {
            String[] splits = line.split(",");
            String title = splits[0].substring(1,splits[0].length()-1);
            int available = Integer.parseInt(splits[1]);
            int rented = Integer.parseInt(splits[2]);
            insert(new Node(title, available, rented));
        }
    }

    /**
     * Method: readTransactions()
     * Description: In this function, it is going to read the contents from the transaction.log file and based on the
     *              certain transaction they want us to perform, it will update the binary tree file. If there is an
     *              error for a certain line in the transaction.log it will place that line in the error.log file.
     * @param: there are no parameters
     * @return: there is nothing that is going to return since it is void
     */

    private void readTransactions() throws IOException {

        //This is the name of the file

        String fileName = "transaction.log";
        Scanner fileInput = null;

        //Making sure that the file is valid

        try {
            fileInput = new Scanner(new File(fileName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //This is going to be reading the file line by line

        ArrayList<String> linesOfInput = new ArrayList<String>();

        while (fileInput.hasNextLine()) {
            String line = fileInput.nextLine();
            linesOfInput.add(line);
        }

        //This is going to be splitting each line to grab the keyword

        for(String line: linesOfInput){
            Pattern p;
            Matcher m;
            String[] splits = line.split(" ",2);
            int args = splits.length;

            //This is receiving the keyword portion

            String keyword = splits[0];

            //This is what will happen if the keyword is equal to rent

            if (keyword.equals("rent"))
            {
                //This is making sure that the rent line is valid

                if(args!=2){
                    invalidLine(line);
                    continue;
                }

                //This is getting the title of the array

                String title = splits[1].substring(1,splits[1].length()-1);
                Node movie = search(root, title);

                //Making sure that the movie is available

                if(movie==null){
                   invalidLine(line);
                }

                //This is what will happen if the movie is available

                else
                    rentDVD(movie, title, line);
            }

            //This is what will happen if the keyword is equal to return

            else if(keyword.equals("return"))
            {
                //This is making sure that the return line is valid

                if(args!=2){
                    invalidLine(line);
                    continue;
                }

                //This is getting the title of the array

                String title = splits[1].substring(1,splits[1].length()-1);
                Node movie = search(root, title);

                //Making sure that the movie is available

                if(movie==null){
                    invalidLine(line);
                }

                //This is what will happen if the movie is available

                else
                    returnDVD(movie, title, line);
            }

            //This is what will happen if the keyword is equal to either add or remove

            else if(keyword.equals("add") || keyword.equals("remove"))
            {
                //This is making sure that either the add line or the remove line are valid

                if(args!=2){
                    invalidLine(line);
                    continue;
                }

                //This is now using the split to make receive the quantity to either add or remove

                String [] strArr = splits[1].split(",");

                //This is making sure that the quantity in there can be used to check whether or not it is valid

                if((1+strArr.length)!=3){
                    invalidLine(line);
                    continue;
                }

                //if it is valid it is getting the title and the quantity from the array

                String title = strArr[0].substring(1,strArr[0].length()-1);
                //int quantity = Integer.parseInt(strArr[1]);
                //Include the try and catch statement here
                try {
                    int quantity = Integer.parseInt(strArr[1]);
                    //This is what will happen if it is the keyword add

                    if(keyword.equals("add"))
                    {
                        addingCopies(title, quantity);
                    }

                    //This is what will happen if the keyword is not add

                    else
                    {
                        //This is making sure that the movie is valid

                        Node movie = search(root, title);

                        //This is what will happen if the movie is not valid

                        if(movie==null){
                            invalidLine(line);
                        }

                        //This is what will happen if the movie is valid

                        else
                            removingCopies(movie, quantity, line);
                    }
                } catch (NumberFormatException exception) {
                    // Output expected NumberFormatException.
                    invalidLine(line);
                }


                //This is what will happen if it is the keyword add

                //if(keyword.equals("add"))
                //{
                    //addingCopies(title, quantity);
                //}

                //This is what will happen if the keyword is not add

                //else
                //{
                    //This is making sure that the movie is valid

                    //Node movie = search(root, title);

                    //This is what will happen if the movie is not valid

                    //if(movie==null){
                        //invalidLine(line);
                    //}

                    //This is what will happen if the movie is valid

                    //else
                        //removingCopies(movie, quantity, line);
                //}
            }

            //This will happen if there is no keyword


            else{
                invalidLine(line);
            }
        }
    }

    /**
     * Method: invalidLine(String line)
     * Description: In this function, it is creating the error.log file which will output a particular line that has the
     *              error
     * @param: line - the line in the transaction.log file that has an error
     * @return: there is nothing that is going to return since it is void
     */

    public void invalidLine(String line) throws IOException {

        //This is creating the error.log file which print the lines that have an error in the transaction.log file

        FileWriter fileWriter = new FileWriter("error.log", true); //Set true for append mode
        PrintWriter printWriter = new PrintWriter(fileWriter);

        //It is going to print that specific line into the file

        printWriter.println(line);

        //it is going to close printWrite and fileWriter

        printWriter.close();
        fileWriter.close();
    }

    /**
     * Method: report()
     * Description: In this function, it is creating the redbox_kiosk.txt file which will output the updated binary
     *              search tree.
     * @param: there are no parameters
     * @return: there is nothing that is going to return since it is void
     */

    private void report() throws IOException {

        //This is going to create the redbox_kiosk.txt file where it has the report

        FileWriter fileWriter = new FileWriter("redbox_kiosk.txt", true); //Set true for append mode
        PrintWriter printWriter = new PrintWriter(fileWriter);

        //This is going to print out the contents from the binary search tree

        printWriter.println("Title                                   Copies Available                        Copies Rented");
        printWriter.println();

        //This is the function we call to print out the contents recursively

        outputTree(printWriter, root);

        //it is going to close printWriter

        printWriter.close();
    }

    /**
     * Method: outputTree(PrinterWriter p, Node iter)
     * Description: In this function, it is creating the recursive function which will be called in report to print out
     *              the contents of the binary search tree
     * @param: p - used to print the contents to the file
     * @param: iter - it is going to be referring to the root of the tree
     * @return: there is nothing that is going to return since it is void
     */

    private void outputTree(PrintWriter p, Node iter){

        //this is what is going to happen if the tree is empty

        if(iter==null)
            return;

        //This is how it is going to print the contents from the tree in the format

        outputTree(p, iter.getLeft());
        p.printf("%-40.40s %-40.40s %-40.40s \n", iter.getTitle(), iter.getAvailable(), iter.getRented());
        outputTree(p, iter.getRight());


    }

}
