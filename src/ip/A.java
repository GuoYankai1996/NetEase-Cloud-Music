package ip;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class A {
	public static void main(String[] args){
		try{
			Document doc=Jsoup.connect("http://www.xicidaili.com/wn/1")
					.timeout(10000)
					.get();
			Elements es=doc.select("tr:contains(¸ßÄä)");
			for(Element e:es){
				String addr=e.select("td").get(1).html();
				int port=Integer.parseInt(e.select("td").get(2).html());
				IPcrawler.list.add(new IP(addr,port));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
}
