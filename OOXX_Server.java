import java.io.*;
import java.net.*;
import java.util.*;

class OOXX_Server {
	DataOutputStream outstream0;
	DataInputStream instream0;
	DataOutputStream outstream1;
	DataInputStream instream1;
	Socket socket;
	String str;
	ServerSocket SS = new ServerSocket(1200);
    
	private int row; 
	private int col; 
	private int player; 
	
	public OOXX_Server() throws IOException{
			Socket sk0 = SS.accept();
			System.out.println("Player0 connect...");
			instream0 = new DataInputStream(sk0.getInputStream());
			outstream0 = new DataOutputStream(sk0.getOutputStream());
			str = "0";
			outstream0.writeUTF(str);
			Socket sk1 = SS.accept();
			System.out.println("Player1 connect...");
			instream1 = new DataInputStream(sk1.getInputStream());
			outstream1 = new DataOutputStream(sk1.getOutputStream());
			str = "1";
			outstream1.writeUTF(str);
			while(true){
				try{
					str = instream0.readUTF();
					player = Integer.valueOf(str);
					sendToOther(player);
					System.out.println("Player0 finished");
					
					str = instream1.readUTF();
					player = Integer.valueOf(str);
					sendToOther(player);
					System.out.println("Player1 finished");
				}
				catch(Exception e){
					System.out.println(e);
				}
			}
	}
	
	public void sendToOther(int player)throws Exception{
		if(player == 0){
			str = instream0.readUTF();
			row = Integer.valueOf(str);
			str = instream0.readUTF();
			col = Integer.valueOf(str);
			System.out.println("Server input player0 data");
			str = String.valueOf(player);
			outstream1.writeUTF(str);
			str = String.valueOf(row);
			outstream1.writeUTF(str);
			str = String.valueOf(col);
			outstream1.writeUTF(str);
			System.out.println("Server send player0 data to player1");
		 }
		else if(player == 1){
			str = instream1.readUTF();
			row = Integer.valueOf(str);
			str = instream1.readUTF();
			col = Integer.valueOf(str);
			System.out.println("Server input player1 data");
			str = String.valueOf(player);
			outstream0.writeUTF(str);
			str = String.valueOf(row);
			outstream0.writeUTF(str);
			str = String.valueOf(col);
			outstream0.writeUTF(str);
			System.out.println("Server send player1 data to player0");
		 }
	}
	
	public static void main(String args[]) throws IOException{
		  OOXX_Server OXS = new OOXX_Server();
	}
}
      
