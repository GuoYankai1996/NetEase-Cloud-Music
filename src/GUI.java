import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.*;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.JButton;
import javax.swing.JFrame;

import ip.IP;

public class GUI {
	public static AtomicInteger a=new AtomicInteger(0);
	static volatile IP ip=new IP("202.168.244.106",53281);
	public static void main(String[] args){
		
		long time=0;
		int temp=0;
		JFrame f=new JFrame("control");
		f.setSize(400, 300);
		f.setLocation(580, 200);
		f.setLayout(null);
		JButton b1=new JButton("��ʼ");
		JButton b2=new JButton("��ͣ");
		b1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				GetSongs.getSongs();
			}
		});
		b2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				GetSongs.temp=true;	
				FileOutputStream fo;
				try {
					fo = new FileOutputStream("boolean");
					ObjectOutputStream o=new ObjectOutputStream(fo);
					o.writeObject(GetSongs.temp);
					o.close();
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			}
		});
		f.add(b1);
		f.add(b2);
		b1.setBounds(100, 100, 100, 30);
		b2.setBounds(200,100 , 100, 30);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
		time=System.currentTimeMillis();
		while(true){
			if(GetSongsThread.count.get()>300){
				GetSongsThread.j=++GetSongsThread.j%15;
				GetSongs.iplist.add(ip);
				ip=GetSongs.iplist.poll();
				GetSongsThread.count.set(0);
			}
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if((System.currentTimeMillis()-time)>600000){
				time=System.currentTimeMillis();
				try(Connection c= DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/music?characterEncoding=UTF-8","root", "admin");
						Statement s=c.createStatement();){
					String sql="select count(*) from song";
					ResultSet set=s.executeQuery(sql);
					set.next();
					int k=set.getInt(1);
					System.out.println("Ŀǰ���ݿ�����Ϊ"+k+"   ��ǰʱ��Ϊ"+LocalTime.now()+"���ӵ���������Ϊ"+(k-temp));
					temp=k;
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			if(GetSongs.temp&&SongqueueThread.count.get()==15){
				try {
					IOUtil.OutputSongQueue(GetSongs.songqueue);
					IOUtil.OutputRepeat(GetSongs.repeat);
					IOUtil.OutputQueue(GetSongs.queue);
					System.out.println("�ɹ����棬�����˳�");
				} catch (IOException e) {
					e.printStackTrace();
					System.out.println("����Ҫ������song,�˳�");
					File file=new File("songqueue.txt");
					file.delete();
				}
				break;
			}
			
		}
	}
}
