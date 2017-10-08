package ip;

import java.io.Serializable;

public class IP implements Serializable{
 public String address;
 public int port;
 public IP(String address,int port){
	 this.address=address;
	 this.port=port;
 }
}
