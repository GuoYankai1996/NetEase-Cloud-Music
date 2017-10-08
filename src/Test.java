import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Test {
public static void main(String[] args) {
	Document doc;
	try {
		
		URL url=new URL("https://music.163.com/playlist?id=925007233");
		Proxy proxy=new Proxy(Proxy.Type.HTTP,new InetSocketAddress("178.217.32.142", 65205));
		URLConnection c=url.openConnection(proxy);
		doc =Jsoup.parse(c.getInputStream(),"UTF-8","https://music.163.com/playlist?id=925007233");
		System.out.println(doc.select("ul").select("a[href*=song]")); 
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

}
}
