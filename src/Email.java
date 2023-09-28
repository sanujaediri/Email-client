//import java.io.*;
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//
//public class Email implements Serializable{
//	public String email_addr;
//	public String subject;
//	public String body;
//	public LocalDate date;
//	public static List<Email> email_list=new ArrayList<Email>();
//
//	public Email(String email_addr,String subject,String body) {
//		this.email_addr=email_addr;
//		this.subject=subject;
//		this.body=body;
//		this.date=LocalDate.now();
//		email_list=retrieve();
//		email_list.add(this);
//		store(email_list);
//		
//	}
//	public static void store(List<Email> email_list_store) {
//		try {
//			// Make a FileOutputStream
//			FileOutputStream fileStream = new FileOutputStream("Emails.ser");
//			// Make a ObjectOutputStream
//			ObjectOutputStream os = new ObjectOutputStream(fileStream);
//			// Write the object
//			os.writeObject(email_list_store);
//			// Close the ObjectOutputStream
//			os.close();
//			}
//			catch(IOException e) {
//				System.out.println("Error saving to '.ser' file");
//			}
//	}
//	
//	public static List<Email> retrieve() {
//		try {
//		// Make a FileInputStream
//		FileInputStream fileStream = new FileInputStream("Emails.ser");
//		// Make an ObjectInputStream
//		ObjectInputStream os = new ObjectInputStream(fileStream);
//		// Read the objects
//		Object obj = os.readObject();
//		// Cast the objects
//		List<Email> email_list_stored=(List<Email>) obj;
//		os.close();
//		return email_list_stored;
//		
//		}catch(ClassNotFoundException e){
//			System.out.println("Error loading '.ser' file");
//		}catch(IOException e){
//			return email_list;
//		}
//		return null;
//	}
//}
