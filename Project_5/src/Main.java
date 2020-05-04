//Programmer's Name: Medha Aiyah
//Programmer's Net Id: mva170001
/**Purpose of the program:
 * This program readings in the inventory.dat file by implementing the hashmap data structure. Given a log of transactions
 * such as renting or returning DVD's as well as adding or removing DVD titles, the program will parse through the
 * transaction.log file and make the appropriate changes to the contents in the binary tree. If there is a line in the
 * transaction log that does not work, it will be printed into another file called error.log. After all the changes
 * have been made through the transaction.log file, then the updated binary tree will be printed out in a formatted
 * table in a file called redbox_kiosk.txt.
 */
import java.io.*;


public class Main {
    public static void main(String[] args) throws IOException {
        new HashMapClass();
    }
}
