package test;

public   class dummy {

	public static void main(String[] args){

		System.out.println(random_mail("name"));
}

	public static String random_mail(String mail)
	{
		
		 int ran;
		    ran = 100 + (int)(Math.random() * ((10000 - 100) + 1));
		  String  mail_id = mail + ran + "@test.com";
		  return mail_id;
	}

}