import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.util.regex.*;
public class GetCount{
    public static void main( String[] args ) throws IOException{
    	Document doc = Jsoup.connect("https://music.163.com/weapi/v1/resource/comments/R_SO_4_489998572?csrf_token=")
    			.header("Accept", "*/*")
    			.header("Accept-Encoding", "gzip, deflate")
    			.header("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3")
    			.header("Referer", "http://music.163.com/")
    			.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36")
    			.timeout(5000)
    			.data("params", "zMkmTNwNyxd4lLPnUS5J0hN9TES07xhkSTO//SRGNp7IiNlOllaTFmbDRA5cFriarOTgR2VcArRAdEOXO911eUknpOmqAYiQE/YKPMQvenKllTOoZP1x7qM4C14ZNcOL56ZD2BWhiAS5oI5pKsfb30robX6R5czEvCL3F2T7E2XqSwXvJ5nFUGe+0HwKaPzi")
    			.data("encSecKey","3dc1351d7cd37509e61d2a6bd5452282966743ca34d4039627b78e3a13060133cd9418417e4755e9b8f303406e08d07e3cdb231843849396b0a55eaaeb84e4b3b44cf1366bd796b6192996dbb13795f1327f1fb7f7c4c4a48ec22d41e2f38b6417c87f66ccc6ee9d5121dc966c514ef6a3e58ea73f0054aa3242aee84f65ebc0")
    			.post();
    	String string=doc.toString();
    	System.out.println(string);
        Pattern pa=Pattern.compile("total\":\\d+");
        Matcher ma=pa.matcher(string);
        ma.find();
        System.out.println(Integer.parseInt("aaaa".split(":")[1]));
        
        
}}
