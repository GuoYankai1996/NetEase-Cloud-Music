
public class Category {
	private String name;
	private String href;
	public Category(String a,String b){
		name=a;
		href=b;
	}
	public void setName(String name){
		this.name=name;
	}
	public void setHref(String href){
		this.href=href;
	}
	public String getName(){
		return name;
	}
	public String getHref(){
		return href;
	}
}
