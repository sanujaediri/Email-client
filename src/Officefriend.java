//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//public class Officefriend extends Official implements greetable{
//	public Date DoB;
//	public Officefriend(String[] details) throws ParseException {
//		// TODO Auto-generated constructor stub
//		super(details);
//		this.DoB=new SimpleDateFormat("yyyy/MM/dd").parse(details[3]);
//	}
//	@Override
//	public void sendBdayWish() {
//		Email bdaywish=new Email(this.email,"Happy Birthday!","Wish you a Happy Birthday." + "\nSanuja");
//		SendEmailTLS.sendmail(bdaywish);
//	}
//
//}
