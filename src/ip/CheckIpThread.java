package ip;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.LinkedList;

import javax.net.ssl.HttpsURLConnection;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CheckIpThread extends Thread{
	public void run(){
			while(IPcrawler.stop){
				IP a=IPcrawler.list.poll();
				if(a!=null){
					Document doc;
					try {
						doc = Jsoup.connect("https://www.ipip.net/").proxy(a.address, a.port)
								.get();
						String proxyip =doc.select(".ip_text").text().split("£º")[1];
						if(!proxyip.equals(IPcrawler.localIp)){
							IPcrawler.queue.add(a);
							System.out.println("chenggong"+IPcrawler.total.incrementAndGet());}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						System.out.println("Ê§°Ü");
					}
				}
				else{
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				
					
				
			}
	
	}
}
