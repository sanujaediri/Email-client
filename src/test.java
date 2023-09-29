public class test {
	public static void main(String[] args) {
	Email testmail=new Email("email@mail.com","subject","message");
	SendEmailTLS.sendmail(testmail);
	}
}
