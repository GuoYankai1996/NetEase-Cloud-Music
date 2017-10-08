package ip;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.JButton;
import javax.swing.JFrame;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class IPcrawler {
	public static AtomicInteger total=new AtomicInteger(0);
	public static IP ip;
	public static AtomicInteger count=new AtomicInteger(0);
	public static ConcurrentLinkedQueue<IP> list=new ConcurrentLinkedQueue<IP>();
	public static ConcurrentLinkedQueue<IP> queue=new ConcurrentLinkedQueue<IP>();
	public static String localIp;
	public static volatile boolean stop=true;
	public static void main(String[] args){
		try{
		File file1=new File("ip");
		file1.delete();
		File file2=new File("ip.txt");
		file2.delete();}catch(Exception e){
			e.printStackTrace();
			System.out.println("初次获取，不用管");
		}
		new GetFromfreeThread().start();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		new GetFrom31fThread().start();
		new GetFromxiciThread().start();
		JFrame f = new JFrame("代理爬虫");
        f.setSize(400, 300);
        f.setLocation(580, 200);
        f.setLayout(null);
        JButton b = new JButton("开始");
        b.setBounds(100, 200, 100, 30);
        JButton c=new JButton("停止并保存");
        c.setBounds(200,200,100,30);
        f.add(b);
        f.add(c);
        try{
			Document doc=Jsoup.connect("https://www.ipip.net/")
					.get();
			localIp=doc.select(".ip_text").text().split("：")[1];
		}catch(Exception e){
			e.printStackTrace();
		}
        b.addActionListener(new ActionListener() {
      	  
            // 当按钮被点击时，就会触发 ActionEvent事件
            // actionPerformed 方法就会被执行
            public void actionPerformed(ActionEvent e) {
                for(int i=0;i<40;i++){
                	new CheckIpThread().start();
                }
            }
        });
        c.addActionListener(new ActionListener() {
 
        	  
            // 当按钮被点击时，就会触发 ActionEvent事件
            // actionPerformed 方法就会被执行
            public void actionPerformed(ActionEvent e) {
            	stop=false;
            	try {
        			FileWriter fw=new FileWriter("ip.txt");
        			FileOutputStream fi=new FileOutputStream("ip");
        			ObjectOutputStream oi=new ObjectOutputStream(fi);
						while((IPcrawler.ip=queue.poll())!=null){
        					oi.writeObject(ip);
        					fw.write(ip.address+" "+ip.port+" ");
        				}
        			
        			fw.flush();
        			fw.close();
        			oi.flush();
        			oi.close();
        			System.out.println("OK，可以退出");
        		} catch (IOException e1) {
        			// TODO Auto-generated catch block
        			e1.printStackTrace();
        		}
            }
        });
        f.setVisible(true);
        
  
	}

}
