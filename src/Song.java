import java.io.Serializable;

public class Song implements Serializable{
	private int id;
	private String name;
	private String musician;
	private int comment;
	public Song(int a,String b,String c,int d){
		id=a;
		name=b;
		musician=c;
		comment=d;
	}
	public void setId(int id){
		this.id=id;
	}
	public void setName(String name){
		this.name=name;
	}
	public void setMusician(String musician){
		this.musician=musician;
	}
	public void setComment(int comment){
		this.comment=comment;
	}
	public int getId(){
		return id;
	}
	public String getName(){
		return name;
	}
	public String getMusician(){
		return musician;
	}
	public int getComment(){
		return comment;
	}
}
