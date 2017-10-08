package ip;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class GetFrom31fThread extends Thread{
	public void run(){
		try{
			Document doc=Jsoup.connect("https://31f.cn/https-proxy/")
					.timeout(10000)
					.get();
			Elements es=doc.select("tr:matches(\\d+\\.\\d+\\.\\d+\\.\\d+)");
			for(int i=1;i<es.size();i++){
				IPcrawler.list.add(new IP(es.get(i).select("td").get(1).html(),Integer.parseInt(es.get(i).select("td").get(2).html())));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
