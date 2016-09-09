package test;


public abstract class NewTest {
	
	private String name;
	private String gender;
	
	public NewTest(String na, String gen)
	{
		 this.name=na;
		 this.gender = gen;
		 
	}
	
	public abstract void work();
	
	public String toString()
	{
		return "Name="+this.name+"Gender="+this.gender;
	}
	public void changeName(String NameNew)
	{
		this.name = NameNew;
	}
	
  }
  


