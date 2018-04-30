import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
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
	public static String address;
	public static AtomicInteger a=new AtomicInteger(0);
	public static void main(String[] args){
		LinkedList<String> list=getAddressList();
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
				for(int i=0;i<14;i++){
					Thread a=new GetSongsThread(GetSongs.queue,GetSongs.songqueue);
					a.start();
				}
				for(int m=0;m<1;m++){
					new SongqueueThread(GetSongs.songqueue,GetSongs.queue).start();
				}
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
			if(GetSongsThread.count.get()>600){
				String string;
				SetNewIP(string=list.poll());
				list.add(string);
				long time1=System.currentTimeMillis();
				while(true){
					int a1=GetSongsThread.count.get();
					try {
						Thread.sleep(20000);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					if((System.currentTimeMillis()-time1)>120000) break;
					if(a1==GetSongsThread.count.get()) break;
				}
				GetSongsThread.count.set(0);
			}
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if((System.currentTimeMillis()-time)>60000){
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
			if(GetSongs.temp&&SongqueueThread.count.get()==14){
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
	public static void SetNewIP(String address){
		try {
			Runtime.getRuntime().exec("cmd /c echo yes|reg add HKEY_LOCAL_MACHINE\\SYSTEM\\CurrentControlSet\\Control\\Class\\{4D36E972-E325-11CE-BFC1-08002BE10318}\\0012 /v NetworkAddress /t REG_SZ /d "+address);
			Runtime.getRuntime().exec("cmd /c netsh interface set interface �������� disabled");
			Thread.sleep(3000);
			Runtime.getRuntime().exec("cmd /c netsh interface set interface �������� enabled");
			Thread.sleep(5000);
				Runtime.getRuntime().exec("cmd.exe /c taskkill /F /IM \"iNode*\" /T");
				Thread.sleep(3000);
				
				Runtime.getRuntime().exec("cmd.exe /c start C:\\\"Program Files (x86)\"\\H3C\\\"iNode Client\"\\\"iNode Client.exe\"");
			
			System.out.println("�л������ַ");
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static LinkedList<String> getAddressList(){
		LinkedList<String> list=new LinkedList<String>();
		File file=new File("E:\\workspace\\MusicCrawler\\address.txt");
		try {
			BufferedReader in=new BufferedReader(new FileReader(file));
			String string;
			while((string=in.readLine())!=null){
				list.add(string);
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
		
	}
}
