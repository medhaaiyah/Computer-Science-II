//Programmer's Name: Medha Aiyah
//Programmer's Net Id: mva170001
/**
 * Welcome to the HashMapClass where all the magic happens. It will parse through the inventory file and to store all the
 *  contents into a hashmap. From there it will read the transactions and then make the appropriate changes into the binary
 * file. If a certain line in the transaction.log is not valid, then it will print that line into the error.log.
 * At the very end it will print the updated binary file with the in the redbox_kiosk.txt.
 */

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HashMapClass
{
    /**
     * Description: This is the member variable that is going to be used in this class
     */

    private Map<Integer, Node> map = new HashMap<>();

    /**
     * Method: HashMapClass()
     * Description: This constructor is being used to call the three main functions that is reading the inventory file,
     *              reading the transaction file, performing the main transactions, creating the error.log file, and
     *              creating the redbox_kiosk.txt file
     * @return: This constructor is not going to return anything
     */

    public HashMapClass() throws IOException {
        readInventory();
        readTransactions();
        report();
    }

    ////////////////////////////////FUNCTIONS USED IN THIS CLASS DEALING WITH TRANSACTIONS//////////////////////////////

    /**
     * Method: nodeExists(String title)
     * Description: this checking, based on the title if there is a node that is in the hash map
     * @param: title - this is referring to the title of the movie
     * @return: it returns a boolean value true if the movie exists or false if the movie does not exist
     */

    private boolean nodeExists(String title){
        for(Map.Entry<Integer, Node> movie : map.entrySet()){
            if(movie.getValue().getTitle().equals(title))
                return true;
        }
        return false;
    }

    /**
     * Method: findNode(String title)
     * Description: in this function it searches through the node to find a certain node and its contents
     * @param: title - this is the title of a movie that they are trying to find
     * @return: it will return the contents of the node
     */

    private Node findNode(String title){
        for(Map.Entry<Integer, Node> movie : map.entrySet()){
            if(movie.getValue().getTitle().equals(title))
                return movie.getValue();
        }
        return null;
    }

    /**
     * Method: addingCopies(String title, int quantity)
     * Description: In this function, it will add a certain number of copies to a certain node
     * @param: title - used to search for a particular title
     * @param: quantity - this is used to add the following amount to a node
     */

    public void addingCopies(String title, int quantity)
    {
        //This is what will happen if the node is not found

        if(!nodeExists(title))
        {
            Node newNode = new Node(title, quantity, 0);
            map.put(newNode.hashCode(), newNode);
        }

        //This will happen if the node is found

        else
        {
            Node movie = findNode(title);
            movie.setAvailable(quantity + movie.getAvailable());
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

                //map.remove(hash(node),node);
                map.remove(node.hashCode(),node);

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
            Node movie = new Node(title, available, rented);
            map.put(movie.hashCode(),movie);
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
                Node movie = findNode(title);

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
                Node movie = findNode(title);

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

                        Node movie = findNode(title);

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

        List<Node> nodeList = sort(map);
        outputAlphaMap(printWriter,nodeList);

        //it is going to close printWriter

        printWriter.close();
    }

    /**
     * Method: sort(Map<Integer, Node> map)
     * Description: This function is used to sort the nodes in the map based on the index and it will return that node
     * @param: map- this will refer to the particular node in the transaction.log
     * @return this function will return a particular nodeList
     */

    private List<Node> sort(Map<Integer,Node> map){
        List<Node> nodeList = new ArrayList<>();
        for(Map.Entry<Integer, Node> movie : map.entrySet()){
            nodeList.add(movie.getValue());
        }
        Collections.sort(nodeList);
        return nodeList;
    }

    /**
     * Method: outputTree(PrinterWriter p, Node iter)
     * Description: In this function, it is creating the recursive function which will be called in report to print out
     *              the contents of the binary search tree
     * @param: p - used to print the contents to the file
     * @param: node - it is going to be referring to the node stored in the hash map
     * @return: there is nothing that is going to return since it is void
     */

    private void outputAlphaMap(PrintWriter p, List<Node> nodeList){

        //this is what is going to happen if the tree is empty

        if(map.isEmpty())
            return;

        //This is how it is going to print the contents from the tree in the format

        for(Node movie : nodeList){
            p.printf("%-40.40s %-40.40s %-40.40s \n", movie.getTitle(), movie.getAvailable(), movie.getRented());
        }
    }

}
