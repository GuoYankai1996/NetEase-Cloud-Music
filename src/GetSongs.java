import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.*;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

import ip.IP;
public class GetSongs {
	static ConcurrentLinkedQueue<Song> songqueue=new ConcurrentLinkedQueue<Song>();
	static volatile boolean temp=false;
	static ConcurrentHashMap<Integer,Integer> repeat;
	static ConcurrentLinkedQueue<Playlist> queue=new ConcurrentLinkedQueue<Playlist>();
	public static void getSongs(){
		FileInputStream fi;
		try {
			fi = new FileInputStream("boolean");
			ObjectInputStream in=new ObjectInputStream(fi);
			temp=(boolean)in.readObject();
		} catch (IOException | ClassNotFoundException e1) {
			System.out.println("初次加载");
		}
		try{
			Class.forName("com.mysql.jdbc.Driver");
		}catch(Exception e){
			e.printStackTrace();
		}
		if(temp){
			try {
				queue=IOUtil.InputQueue();
			} catch (ClassNotFoundException e) {
				
			} catch (IOException e) {
				System.out.println("queue读取错误");
			}
			try {
				songqueue=IOUtil.InputSongQueue();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				System.out.println("无songqueue1");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("无songqueue2");
			}
			try {
				repeat=IOUtil.InputRepeat();
			} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
				System.out.println("repeat读取错误1");
			} catch (IOException e) {
					// TODO Auto-generated catch block
				System.out.println("repeat读取错误1");
			}
			temp=false;
		}else{
			list(queue);
			repeat=new ConcurrentHashMap<Integer,Integer>(10000);
		}
		
	}
	public static void list(ConcurrentLinkedQueue<Playlist> queue){
		String sql="select * from playlist";
		try(Connection c = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/music?characterEncoding=UTF-8","root", "admin");
			Statement s=c.createStatement()	){
			ResultSet set=s.executeQuery(sql);
			while(set.next()){
				Playlist a=new Playlist(set.getInt(1),set.getString(2),set.getString(3));
				queue.add(a);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
