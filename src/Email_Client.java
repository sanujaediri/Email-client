import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class Email_Client {

	// lists to store recipient objects
	private static List<Official> official_list = new ArrayList<Official>();
	private static List<Officefriend> officefriend_list = new ArrayList<Officefriend>();
	private static List<Personal> personal_list = new ArrayList<Personal>();

	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);

		// creating all recipients objects at start
		try {
			FileReader read = new FileReader("clientList.txt");
			BufferedReader read_buff = new BufferedReader(read);
			String line = null;
			RecFactory recfactory = new RecFactory();
			while ((line = read_buff.readLine()) != null) {
				addtolist(recfactory.createRec(line));
			}
			read.close();
		} catch (IOException | ParseException e) {
			System.out.println("Error reading clientList");
		}

		// traversing the object lists and sending out birthday wishes to recipients who
		// have birthday today
		LocalDate today_date = LocalDate.now();
		for (Officefriend f : officefriend_list) {
			LocalDate dob_f = f.getDoB().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			if (today_date.getDayOfMonth() == dob_f.getDayOfMonth()
					&& today_date.getMonthValue() == dob_f.getMonthValue()) {
				f.sendBdayWish();
			}
		}
		for (Personal p : personal_list) {
			LocalDate dob_p = p.getDoB().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			if (today_date.getDayOfMonth() == dob_p.getDayOfMonth()
					&& today_date.getMonthValue() == dob_p.getMonthValue()) {
				p.sendBdayWish();
			}
		}

		// input
		int option = 0;
		while (option != 6) {
			System.out.println("Enter option type: \n" + "1 - Adding a new recipient\n" + "2 - Sending an email\n"
					+ "3 - Printing out all the recipients who have birthdays\n"
					+ "4 - Printing out details of all the emails sent\n"
					+ "5 - Printing out the number of recipient objects in the application\n"
					+ "6 - Close Email Client");

			String option_s = scanner.nextLine();
			option = Integer.parseInt(option_s);

			switch (option) {
			case 1:
				// code to add a new recipient
				// input format - Official: nimal,nimal@gmail.com,ceo
				// Use a single input to get all the details of a recipient

				System.out.println("Enter recipient details:");
				String recipient = scanner.nextLine();
				RecFactory recfactory2 = new RecFactory();
				try {
					Recipient rec = recfactory2.createRec(recipient);
					addtolist(rec);
				} catch (ParseException e1) {
				}

				addtofile(recipient); // store details in clientList.txt file
				break;
			case 2:
				// input format - email, subject, content
				// code to send an email
				System.out.println("Enter email details: <email>, <subject>, <content>");
				String email_data = scanner.nextLine();
				String[] split = email_data.split(", ");
				Email email = new Email(split[0], split[1], split[2]); // creating email objects
				SendEmailTLS.sendmail(email);

				break;
			case 3:
				// input format - yyyy/MM/dd (ex: 2018/09/17)
				// code to print recipients who have birthdays on the given date
				System.out.println("Enter date:yyyy/MM/dd");
				String date = scanner.nextLine();
				// convert string to date
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
				LocalDate check_date = LocalDate.parse(date, formatter);

				// check object lists for recipients who have the given birthday(month and date)
				for (Officefriend f : officefriend_list) {
					LocalDate dob_f = f.getDoB().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
					if (check_date.getDayOfMonth() == dob_f.getDayOfMonth()
							&& check_date.getMonthValue() == dob_f.getMonthValue()) {
						System.out.println(f.getName());
					}
				}
				for (Personal p : personal_list) {
					LocalDate dob_p = p.getDoB().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
					if (check_date.getDayOfMonth() == dob_p.getDayOfMonth()
							&& check_date.getMonthValue() == dob_p.getMonthValue()) {
						System.out.println(p.getName());
					}
				}

				break;

			case 4:
				// input format - yyyy/MM/dd (ex: 2018/09/17)
				// code to print the details of all the emails sent on the input date
				System.out.println("Enter date:yyyy/MM/dd");
				String date1 = scanner.nextLine();
				DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy/MM/dd");
				LocalDate check_date1 = LocalDate.parse(date1, formatter1);

				// access the "Emails.ser" file and print the emails sent on the given date
				List<Email> email_list = Email.retrieve();
				for (Email e : email_list) {
					if (e.getDate().getMonthValue() == check_date1.getMonthValue()
							&& e.getDate().getDayOfMonth() == check_date1.getDayOfMonth()
							&& e.getDate().getYear() == check_date1.getYear()) {
						System.out.println("Email: " + e.getEmail() + ",  Subject: " + e.getSubject() + ",  Content: "
								+ e.getBody());
					}
				}
				Email.store(email_list);
				break;

			case 5:
				// code to print the number of recipient objects in the application
				System.out.println(Recipient.count);
				break;

			}
		}
		scanner.close();

	}

	// adds the created recipient objects to each list
	private static void addtolist(Recipient rec) {
		// TODO Auto-generated method stub
		if (rec instanceof Personal) {
			personal_list.add((Personal) rec);
		} else if (rec instanceof Officefriend) {
			officefriend_list.add((Officefriend) rec);
		} else {
			official_list.add((Official) rec);
		}

	}

	// adds the recipient to the clientList.txt file
	private static void addtofile(String recipient) {
		try {
			FileWriter writer = new FileWriter("clientList.txt", true);
			BufferedWriter writer_buff = new BufferedWriter(writer);

			writer_buff.append(recipient + "\n");
			writer_buff.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}

//Recipient Factory
class RecFactory {
	public Recipient createRec(String string) throws ParseException {
		String[] split_rec = string.split(": ");
		String[] details = split_rec[1].split(",");
		switch (split_rec[0]) {
		case "Official":
			Recipient off_rec = new Official(details);
			return off_rec;
		case "Office_friend":
			Recipient off_friend = new Officefriend(details);
			return off_friend;
		case "Personal":
			Recipient personal = new Personal(details);
			return personal;
		}
		return null;
	}
}

//interface for the recipients who have birthdays
interface greetable {
	public void sendBdayWish();
}

abstract class Recipient {
	private String name;
	private String email;
	public static int count;

	public String getName() {
		return this.name;
	}

	public String getEmail() {
		return this.email;
	}

	public Recipient(String name, String email) {
		this.name = name;
		this.email = email;
		count++;
	}

}

//personal friend class
class Personal extends Recipient implements greetable {
	private String nickname;
	private Date DoB;

	public String getNickname() {
		return this.nickname;
	}

	public Date getDoB() {
		return this.DoB;
	}

	public Personal(String[] details) throws ParseException {
		super(details[0], details[2]);
		this.nickname = details[1];
		this.DoB = new SimpleDateFormat("yyyy/MM/dd").parse(details[3]);
	}

	public void sendBdayWish() {
		Email bdaywish = new Email(this.getEmail(), "Happy Birthday!", "Hugs and love on your birthday. Sanuja");
		SendEmailTLS.sendmail(bdaywish);

	}

}

//official recipient class
class Official extends Recipient {
	private String designation;

	public String getDesignation() {
		return this.designation;
	}

	public Official(String[] details) {
		// TODO Auto-generated constructor stub
		super(details[0], details[1]);

		this.designation = details[2];
	}
}

//official friend class
class Officefriend extends Official implements greetable {
	private Date DoB;

	public Date getDoB() {
		return this.DoB;
	}

	public Officefriend(String[] details) throws ParseException {
		// TODO Auto-generated constructor stub
		super(details);
		this.DoB = new SimpleDateFormat("yyyy/MM/dd").parse(details[3]);
	}

	@Override
	public void sendBdayWish() {
		Email bdaywish = new Email(this.getEmail(), "Happy Birthday!", "Wish you a Happy Birthday. Sanuja");
		SendEmailTLS.sendmail(bdaywish);
	}

}

//class for email objects
class Email implements Serializable {
	private String email_addr;
	private String subject;
	private String body;
	private LocalDate date;

	public LocalDate getDate() {
		return this.date;
	}

	public String getEmail() {
		return this.email_addr;
	}

	public String getBody() {
		return this.body;
	}

	public String getSubject() {
		return this.subject;
	}

	// list to store every email object created
	public static List<Email> email_list = new ArrayList<Email>();

	public Email(String email_addr, String subject, String body) {
		this.email_addr = email_addr;
		this.subject = subject;
		this.body = body;
		this.date = LocalDate.now(); // stores the date the email is sent

		// access the Emails.ser file, add it and store it back
		email_list = retrieve();
		email_list.add(this);
		store(email_list);

	}

	// stores the email list in Emails.ser
	public static void store(List<Email> email_list_store) {
		try {
			// Make a FileOutputStream
			FileOutputStream fileStream = new FileOutputStream("Emails.ser");
			// Make a ObjectOutputStream
			ObjectOutputStream os = new ObjectOutputStream(fileStream);
			// Write the object
			os.writeObject(email_list_store);
			// Close the ObjectOutputStream
			os.close();
		} catch (IOException e) {
			System.out.println("Error saving to '.ser' file");
		}
	}

	// retrieves the Email.ser
	public static List<Email> retrieve() {
		try {
			// Make a FileInputStream
			FileInputStream fileStream = new FileInputStream("Emails.ser");
			// Make an ObjectInputStream
			ObjectInputStream os = new ObjectInputStream(fileStream);
			// Read the objects
			Object obj = os.readObject();
			// Cast the objects
			List<Email> email_list_stored = (List<Email>) obj;
			os.close();
			return email_list_stored;

		} catch (ClassNotFoundException e) {
			System.out.println("Error loading '.ser' file");
		} catch (IOException e) {
			return email_list;
		}
		return null;
	}
}

//sends the email from [emailaddress]
class SendEmailTLS {

	public static void sendmail(Email mail) {
		String email = mail.getEmail();
		String subject = mail.getSubject();
		String msg = mail.getBody();

		final String username = [emailaddress];
		final String password = [smtp credentials];

		Properties prop = new Properties();
		prop.put("mail.smtp.host", "smtp.gmail.com");
		prop.put("mail.smtp.port", "587");
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.starttls.enable", "true"); // TLS

		Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress([emailaddress]));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
			message.setSubject(subject);
			message.setText(msg);

			Transport.send(message);

			System.out.println("Email sent successfully to " + email);

		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
}
