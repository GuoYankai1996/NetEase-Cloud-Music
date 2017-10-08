import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class GetPlaylist {
	public static Elements run(String url,int offset) throws IOException{
		Document doc = Jsoup.connect(url+"&limit=35&offset="+offset)
				.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
				.header("Accept-Encoding", "gzip, deflate, br")
				.header("Accept-Language", "zh-CN,zh;q=0.8")
				.header("Referer", "https://music.163.com/")
				.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36")
				.timeout(5000)
				.get();
		return doc.select("a.msk");
	}
	public static void main(String[] args) throws IOException{
		try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
		LinkedList<Category> list=new LinkedList<Category>();
        String sql = "select * from category";
        Elements es;
        try (Connection c = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/music?characterEncoding=UTF-8","root", "admin"); 
                Statement s = c.createStatement();
            ) {
        	ResultSet set=s.executeQuery(sql);
            while(set.next()){
            	Category ca=new Category(set.getString(1), set.getString(2));
            	list.add(ca);}	
            while(!list.isEmpty()){
            	Connection e = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/music?characterEncoding=UTF-8","root", "admin");
            	Category a=list.poll();
            	PlaylistThread thread=new PlaylistThread(a,e);
            	thread.start();
            }
        } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
       
        	
        }
}
