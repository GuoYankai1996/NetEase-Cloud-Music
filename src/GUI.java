import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.*;
import javax.swing.JButton;
import javax.swing.JFrame;

public class GUI {
	public static void main(String[] args){
		long time=0;
		JFrame f=new JFrame("control");
		f.setSize(400, 300);
		f.setLocation(580, 200);
		f.setLayout(null);
		JButton b1=new JButton("开始");
		JButton b2=new JButton("暂停");
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
					IOUtil.OutputQueue(GetSongs.queue);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					fo = new FileOutputStream("boolean");
					ObjectOutputStream o=new ObjectOutputStream(fo);
					o.writeObject(GetSongs.temp);
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
			try {
				Thread.sleep(100000);
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
					System.out.println("目前数据库总数为"+k+"   当前时间为"+LocalTime.now());
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}
}
