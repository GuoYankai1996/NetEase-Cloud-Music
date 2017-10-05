import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

class PlaylistThread extends Thread{
        	Category cg;
        	Elements es;
        	Connection c;
        	public PlaylistThread(Category cg,Connection c){
        		this.cg=cg;
        		this.c=c;
        	}
			@Override
			public void run() {
				for(int i=0;i<1400;i+=35){
	        		try {
						es=GetPlaylist.run(cg.getHref(),i);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
	        		try(PreparedStatement ps=c.prepareStatement("insert into playlist values(?,?,?)")){
	        			for(Element e:es){
		        			String href=e.attr("href");
		        			String[] a=href.split("=");
		        			int id=Integer.parseInt(a[1]);
		        			String name=e.attr("title");
		        			System.out.println(id);
		        			try {
		        			ps.setInt(1, id);
							ps.setString(2, name);
		        			ps.setString(3, cg.getName());
		        			ps.execute();}
		        			catch(Exception e1){
		        				e1.printStackTrace();
		        			}
		        		}
	        		} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	        	}
			}
}