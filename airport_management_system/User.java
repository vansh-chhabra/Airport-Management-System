package airport_management_system;



public class User {
	private int id;
	private String name;
	private String pwd;
	User(String name,String pwd){
		this.name = name;
		this.pwd = pwd;
	}
	User(){
		
	}
	User(String id,String name,String pwd){
		this.id = Integer.parseInt(id);
		this.name = name;
		this.pwd = pwd;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
}
