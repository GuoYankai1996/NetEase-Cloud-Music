import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import ip.IP;

public class SongqueueThread extends Thread {
	ConcurrentLinkedQueue<Song> songqueue;
	ConcurrentLinkedQueue<Playlist> queue;
	static AtomicInteger count=new AtomicInteger(0);
	Song song;
		public SongqueueThread(ConcurrentLinkedQueue<Song> songqueue,ConcurrentLinkedQueue<Playlist> queue){
		this.songqueue=songqueue;
		this.queue=queue;
		}
		public void run() {
			try(Connection c= DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/music?characterEncoding=UTF-8","root", "admin");
				PreparedStatement ps=c.prepareStatement("update song set musician=?,comment=? where id=?")	){
				while(!queue.isEmpty()||!songqueue.isEmpty()){
					if((song=songqueue.poll())==null){
						try {
							Thread.sleep(60000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
					else{
						int id=song.getId();
						String musician=getMusician(id);
						int comment=getCommentCount(id);
						if(comment==-1||musician=="aaaaa"){
							songqueue.add(song);
							if(GetSongs.temp&&count.get()==14){
								break;
							}
							continue;
						}
						ps.setString(1, musician);
						ps.setInt(2, comment);
						ps.setInt(3, id);
						ps.execute();
						System.out.print(3);
					}
					if(GetSongs.temp&&count.get()==14){
						break;
					}
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		public int getCommentCount(int id){
			Document doc=null;
			try {
				doc = Jsoup.connect("https://music.163.com/weapi/v1/resource/comments/R_SO_4_"+id+"?csrf_token=")
						.header("Accept", "*/*")
						.header("Accept-Encoding", "gzip, deflate")
						.header("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3")
						.header("Referer", "http://music.163.com/")
						.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36")
						.timeout(5000)
						.data("params", "zMkmTNwNyxd4lLPnUS5J0hN9TES07xhkSTO//SRGNp7IiNlOllaTFmbDRA5cFriarOTgR2VcArRAdEOXO911eUknpOmqAYiQE/YKPMQvenKllTOoZP1x7qM4C14ZNcOL56ZD2BWhiAS5oI5pKsfb30robX6R5czEvCL3F2T7E2XqSwXvJ5nFUGe+0HwKaPzi")
						.data("encSecKey","3dc1351d7cd37509e61d2a6bd5452282966743ca34d4039627b78e3a13060133cd9418417e4755e9b8f303406e08d07e3cdb231843849396b0a55eaaeb84e4b3b44cf1366bd796b6192996dbb13795f1327f1fb7f7c4c4a48ec22d41e2f38b6417c87f66ccc6ee9d5121dc966c514ef6a3e58ea73f0054aa3242aee84f65ebc0")
						.post();
			} catch (IOException e) {
			}
			if(doc==null){
				System.out.println("评论数目获取失败");
				return -1;
			}
			String string=doc.toString();
	        Pattern pa=Pattern.compile("total\":\\d+");
	        Matcher ma=pa.matcher(string);
	        try{
	            string=ma.group();
	        }catch(Exception e){
	        	return 0;
	        }
	        return Integer.parseInt(string.split(":")[1]);
		}
		public String getMusician(int id){
			Document doc=null;
			try {
				doc = Jsoup.connect("https://music.163.com/song?id="+id)
						.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
						.header("Accept-Encoding", "gzip, deflate, br")
						.header("Accept-Language", "zh-CN,zh;q=0.8")
						//.header("X-Forwarded-For", "108.61.199.82,103.50.168.37,172.21.58.18")
						.header("Referer", "https://music.163.com/")
						.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36")
						.timeout(5000)
						.get();
			} catch (IOException e) {
			}
			if(doc==null){
				System.out.println("musician");
				return "aaaaa";
			}
			return doc.select("a[href*=/artist]").html();
		}
}
