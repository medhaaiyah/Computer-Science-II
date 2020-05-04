# Computer-Science-II

# Project 1

In this project, the user is able to reserve seats in three auditoriums. There will be a prompt used to display which
auditorium the user wants to make the reservation. From there the user will be able to display the row of the seat, a
certain column, and the number of adult, child, and senior tickets it wants to purchase.

If the seats are available, it will display the available seats but if the seats are unavailable it will display the
best seats and ask the user whether or not the user wants those seats. If the user says "y" it will update the ticket
system, if the user types "n" it will not update the reservation ticket system.

At the end it will output the total seats in the auditorium, the total tickets sold, the adult tickets sold, the child
tickets sold, and the total ticket sales for each auditorium it is looking at.

# Project 2

In this project, there are three files, the customer.dat, orders.dat,  and preferred.dat. The first thing this class will
do is to put all the contents in the customer.dat into the regularCustomerArray and put all the contents in the
preferred.dat into the preferredCustomerArray. Once the contents are saved in the appropriate array it will take a look
at the orders.dat file. Firstly it will check to see if the orders.dat has invalid data (if there is invalid data it will
ignore that contents). From there it will determine the status of each customer by referring to the ID. It will also
calculate the amount spent for each of the customers.

Depending on the status and the amount spent, there are many possibilities. If a regular customer spends $50 or more it
becomes a gold member. If a regular customer spends $150 or more it becomes a platinum member. If a gold customer
spends $200 or more it becomes a platinum customer. Based on how much the customer spends, it will make the appropriate
changes in the array. Once the changes are made to the two arrays, it will then update the specific files.

# Project 3

In this project, the user reserve tickets and acts as a ticket reservation system. It reads in the
A1.txt. It then prompts the user to enter what auditorium they want to select seats in and displays the seating chart. It then asks the user what seat they would prefer and the number of adults, children and senior citizens in their party. If the preferred seats are unavailable, a function in the program will generate the next best available seats for the user. If the user confirms these seats, a function in the program writes out the auditorium with the newly reserved seats back to the file. The program loops the main prompt; after the user finishes reserving seats, they are asked if they want to reserve more, or they can exit - which will show them the statistics for tickets sold in all the auditoriums. The program utilizes multiple functions, stores the seating information in a linked list (look at auditorium class), and uses algorithms to come up with finding the best available seating for the user.

# Project 4

In this project, the program will read the inventory.dat file and store the information into a binary tree. Given a log of transactions such as renting or returning DVD's as well as adding or removing DVD titles, the program will parse through the transaction.log file and make the appropriate changes to the contents in the binary tree. If there is a line in the transaction log that does not work, it will be printed into another file called error.log. After all the changes have been made through the transaction.log file, then the updated binary tree will be printed out in a formatted table in a file called redbox_kiosk.txt. 

# Project 5

In this project, the program will read the inventory.dat file and store the information into a hash map. Given a log of transactions such as renting or returning DVD's as well as adding or removing DVD titles, the program will parse through the transaction.log file and make the appropriate changes to the contents in the hash map. If there is a line in the transaction log that does not work, it will be printed into another file called error.log. After all the changes have been made through the transaction.log file, then the updated hash map will be printed out in a formatted table in a file called redbox_kiosk.txt. 
