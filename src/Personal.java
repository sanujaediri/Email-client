//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//public class Personal extends Recipient implements greetable{
//	public String nickname;
//	public Date DoB;
//	
//	public Personal(String[] details) throws ParseException {
//		super(details[0],details[2]);
//		this.nickname=details[1];
//		this.DoB=new SimpleDateFormat("yyyy/MM/dd").parse(details[3]);
//	}
//	
//	public void sendBdayWish() {
//		Email bdaywish=new Email(this.email,"Happy Birthday!","Hugs and love on your birthday." + "\nSanuja");
//		SendEmailTLS.sendmail(bdaywish);
//
//	}
//
//}
