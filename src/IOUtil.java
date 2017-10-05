import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.concurrent.ConcurrentLinkedQueue;

public class IOUtil {
	public static void OutputQueue(ConcurrentLinkedQueue<Playlist> queue) throws IOException{
		FileOutputStream fout=new FileOutputStream("queue.txt");
		ObjectOutputStream out=new ObjectOutputStream(fout);
		out.writeObject(queue);
		out.close();
	}
	public static void OutputSongQueue(ConcurrentLinkedQueue<Song> songqueue) throws IOException{
		FileOutputStream fout=new FileOutputStream("songqueue.txt");
		ObjectOutputStream out=new ObjectOutputStream(fout);
		out.writeObject(songqueue);
		out.close();
	}
	public static ConcurrentLinkedQueue<Playlist> InputQueue() throws IOException, ClassNotFoundException{
		FileInputStream fin=new FileInputStream("queue.txt");
		ObjectInputStream in=new ObjectInputStream(fin);
		return (ConcurrentLinkedQueue<Playlist>) in.readObject();		
	}
	public static ConcurrentLinkedQueue<Song> InputSongQueue() throws IOException, ClassNotFoundException{
		FileInputStream fin=new FileInputStream("songqueue.txt");
		ObjectInputStream in=new ObjectInputStream(fin);
		return (ConcurrentLinkedQueue<Song>) in.readObject();		
	}
}
