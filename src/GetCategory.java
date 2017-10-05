import java.io.IOException;
import java.sql.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
public class GetCategory {
	public static void main(String[] args) throws IOException{
		Document doc = Jsoup.connect("https://music.163.com/discover/playlist")
				.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
				.header("Accept-Encoding", "gzip, deflate, br")
				.header("Accept-Language", "zh-CN,zh;q=0.8")
				.header("Referer", "https://music.163.com/")
				.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36")
				.timeout(5000)
				.get();
		Elements es=doc.select("a[href*=cat]").select("a.s-fc1");
		try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
  
        String sql = "insert into category values(?,?)";
        try (Connection c = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/music?characterEncoding=UTF-8","root", "admin"); 
                PreparedStatement ps = c.prepareStatement(sql);
            ) {
            for(Element e:es){
            	ps.setString(1, e.html());
            	ps.setString(2,"https://music.163.com"+e.attr("href"));
            	ps.execute();
            }
 System.out.println("OK");
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
		
		
	}
}