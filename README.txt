This is a command-line based email client.

The email client has two types of recipients, official and personal. Some official recipients are close friends.

Details of the recipient list are stored in clientList.txt file. recipient’s record in the text file has the following formats:

Ex:     Official: <name>,<email>,<position in the company>
        Office_friend: <name>,<email>,<position in th company>,<recipient's birthday in yyyy/mm/dd>
        Personal: <name>,<nick-name>,<email>,<recipient's birthday in yyyy/mm/dd>

For each recipient having a birthday, a birthday greeting is sent on the correct day. Official friends and personal recipients should be sent different messages 
Ex: "Wish you a Happy Birthday. <your name>" for an office friend,
 and "hugs and love on your birthday. <your name>" for personal recipients 
 
All the emails sent out by the email client is saved in Emails.ser

Command-line options are available for:

    Adding a new recipient
    Sending an email
    Printing out all the names of recipients who have their birthday set to current date
    Printing out details (subject and recipient) of all the emails sent on a date specified by user input
    Printing out the number of recipient objects in the application

This application imports the javax.mail package, which is included in the javax.mail.jar and should be activated from javax.activation-1.2.0.jar

