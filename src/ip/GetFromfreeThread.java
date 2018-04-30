package ip;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.plaf.synth.SynthSpinnerUI;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GetFromfreeThread extends Thread{
	public void run(){
		File file1=new File("1.html");
		File file2=new File("2.html");
		File file3=new File("3.html");
		Document doc;
		try {
			Pattern pa=Pattern.compile("\"[\\w%]+\"");
			doc = Jsoup.parse(file2, "UTF-8");
			Elements es=doc.select("tr:has(script)");
			for(Element e:es){
				if(e.select("td").size()>5){
				Matcher ma=pa.matcher(e.select("script").html());
				ma.find();
				IPcrawler.list.add(new IP(ma.group(),Integer.parseInt(e.select("td").get(1).html())));
				}
			}
			doc = Jsoup.parse(file2, "UTF-8");
			 es=doc.select("tr:has(script)");
			for(Element e:es){
				if(e.select("td").size()>5){
				Matcher ma=pa.matcher(e.select("script").html());
				ma.find();
				IPcrawler.list.add(new IP(ma.group(),Integer.parseInt(e.select("td").get(1).html())));}
				//System.out.println(ma.group());
			}
			doc = Jsoup.parse(file3, "UTF-8");
			es=doc.select("tr:has(script)");
			for(Element e:es){if(e.select("td").size()>5){
				Matcher ma=pa.matcher(e.select("script").html());
				ma.find();
				IPcrawler.list.add(new IP(ma.group(),Integer.parseInt(e.select("td").get(1).html())));}
			}
			System.out.println("free成功获得列表");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
