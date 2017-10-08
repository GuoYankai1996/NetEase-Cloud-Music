package ip;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GetFromxiciThread extends Thread{
	public void run(){
		for(int k=1;k<100;k++){
			try{
				Document doc=Jsoup.connect("http://www.xicidaili.com/wn/"+k).proxy("117.135.198.11", 80)
						.timeout(10000)
						.get();
				Elements es=doc.select("tr:contains(高匿)");
				for(Element e:es){
					String addr=e.select("td").get(1).html();
					int port=Integer.parseInt(e.select("td").get(2).html());
					IPcrawler.list.add(new IP(addr,port));
				}
				System.out.println("从西刺获取ip列表成功");
			}catch(Exception e){
				e.printStackTrace();
			}
			try {
				Thread.sleep((int)(Math.random()*40000));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
