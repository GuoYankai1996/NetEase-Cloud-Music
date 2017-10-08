import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class IOUtil {
	public static void OutputQueue(ConcurrentLinkedQueue<Playlist> queue) throws IOException{

			FileOutputStream fout=new FileOutputStream("queue.txt");
			ObjectOutputStream out;
			out = new ObjectOutputStream(fout);

		out.writeObject(queue);
		out.close();
	}
	public static void OutputSongQueue(ConcurrentLinkedQueue<Song> songqueue) throws IOException{
		FileOutputStream fout=new FileOutputStream("songqueue.txt");
		ObjectOutputStream out=new ObjectOutputStream(fout);
		out.writeObject(songqueue);
		out.close();
		File file=new File("songqueuelist.txt");
		file.delete();
		FileWriter fw=new FileWriter("songqueuelist.txt");		
		while(!songqueue.isEmpty()){
			fw.write(songqueue.poll().getName()+"\\r\\t");
		}
		fw.close();
		
	}
	public static void OutputRepeat(ConcurrentHashMap<Integer,Integer> repeat) throws IOException{
		FileOutputStream fout=new FileOutputStream("repeat.txt");
		ObjectOutputStream out=new ObjectOutputStream(fout);
		out.writeObject(repeat);
		out.close();
	}
	public static ConcurrentLinkedQueue<Playlist> InputQueue() throws IOException, ClassNotFoundException {
		FileInputStream fin=new FileInputStream("queue.txt");
		ObjectInputStream in=new ObjectInputStream(fin);
		return (ConcurrentLinkedQueue<Playlist>) in.readObject();
		
	}
	public static ConcurrentLinkedQueue<Song> InputSongQueue() throws IOException, ClassNotFoundException{
		FileInputStream fin=new FileInputStream("songqueue.txt");
		ObjectInputStream in=new ObjectInputStream(fin);
		return (ConcurrentLinkedQueue<Song>) in.readObject();		
	}
	public static ConcurrentHashMap<Integer,Integer>  InputRepeat() throws IOException, ClassNotFoundException{
		FileInputStream fin=new FileInputStream("repeat.txt");
		ObjectInputStream in=new ObjectInputStream(fin);
		return (ConcurrentHashMap<Integer,Integer>)in.readObject();
	}
}
