import java.awt.event.*;
import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.*;

import javax.swing.*;

class OOXX_Client_Thread extends Thread implements ActionListener{
	DataOutputStream outstream;
	DataInputStream instream;
	Socket socket;
	static String str;
	private static int player;
	private ImageIcon icons[] = {new ImageIcon("SmallFireDragon.gif"), new ImageIcon("WaterTurtle.gif")};
	private ActionEvent e;
	private int row;
	private int col;
	
	public JButton[] buttonArr;

	private int[][] jFrame;
	public OOXX_Client_Thread(String ip, int port,JButton[] arr)
	  {
	     try{
	        socket = new Socket(InetAddress.getByName(ip),port);
	        outstream = new DataOutputStream(socket.getOutputStream());
	        instream=new DataInputStream(socket.getInputStream());
			buttonArr = arr;
	      }
	      catch(IOException e){
	         System.out.println("IO execute failed!"); 
	      }
	  }
    public void run(){
        try {
            while(true){
				str = instream.readUTF();
				player = Integer.valueOf(str);
				str = instream.readUTF();
				row = Integer.valueOf(str);
            	str = instream.readUTF();
				col = Integer.valueOf(str);
            	invoke(row, col, player);
            	System.out.print("input player: " + player);
            	System.out.print(", row: " + row);
            	System.out.print(", col: " + col);
				System.out.println();
            }
        }
        catch (Exception exception) {
			exception.getMessage();
            System.out.println("Thread excute failed!");
        }
    }
	public void send(int row, int col, int player){
		try {
			str = String.valueOf(player);
			outstream.writeUTF(str);
			str = String.valueOf(row);
			outstream.writeUTF(str);
			str = String.valueOf(col);
			outstream.writeUTF(str);
			System.out.print("send player: " + player);
	    	System.out.print(", row: " + row);
	    	System.out.print(", col: " + col);
			System.out.println();
			
			
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	public int setPlayer(){
		try{
			str = instream.readUTF();
			player = Integer.valueOf(str);
			System.out.println("Set player: " + player);
			return player;
		}
		catch (IOException e1){
			e1.printStackTrace();
		}
		return -1;
	}
	
	public void invoke(final int row, final int col, final int player) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				buttonArr[row * 3 + col].setIcon(icons[player]);
			}
		});
	}
	
	@Override
	public void actionPerformed(ActionEvent e){}
}