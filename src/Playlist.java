import java.io.Serializable;

public class Playlist implements Serializable{
	private int id;
	private String name;
	private String category; 
	public Playlist(int id,String name,String category){
		this.name=name;
		this.id=id;
		this.category=category;
	}
	public void setId(int id){
		this.id=id;
	}
	public void setName(String name){
		this.name=name;
	}
	public void setCategory(String category){
		this.category=category;
	}
	public String getName(){
		return this.name;
	}
	public int getId(){
		return this.id;
	}
	public String getCategory(){
		return this.category;
	}
}
