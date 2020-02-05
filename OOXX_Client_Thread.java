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
	private int row; //直的
	private int col; //橫的
	
	public JButton[] buttonArr;

	private int[][] jFrame;
	public OOXX_Client_Thread(String ip, int port,JButton[] arr) //多接buttonArr
	  {
	     try{
	        socket = new Socket(InetAddress.getByName(ip),port);
	        outstream = new DataOutputStream(socket.getOutputStream());
	        instream=new DataInputStream(socket.getInputStream());
			buttonArr = arr;
	      }
	      catch(IOException e){
	         System.out.println("IO執行錯誤"); 
	      }
	  }
    public void run(){
        try {
            while(true){
				str = instream.readUTF();
				player = Integer.valueOf(str);
				System.out.println("讀到資料player: " + player);
				str = instream.readUTF();
				row = Integer.valueOf(str);
				System.out.println("讀到資料row: " + row);
            	str = instream.readUTF();
				col = Integer.valueOf(str);
				System.out.println("讀到資料col: " + col);
            	invoke(row, col, player);
				System.out.println("畫圖");
            }
        }
        catch (Exception exception) {
			exception.getMessage();
            System.out.println("Thread執行錯誤");
        }
    }
	public void send(int row, int col, int player){
		try {
			str = String.valueOf(player);
			outstream.writeUTF(str);
			System.out.println("送出資料Player: " + player);
			str = String.valueOf(row);
			outstream.writeUTF(str);
			System.out.println("送出資料row: " + row);
			str = String.valueOf(col);
			outstream.writeUTF(str);
			System.out.println("送出資料col: " + col);
			
			
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	public int setPlayer(){
		try{
			str = instream.readUTF();
			player = Integer.valueOf(str);
			System.out.println("玩家設定: " + player);
			return player;
		}
		catch (IOException e1){
			e1.printStackTrace();
		}
		return -1;
	}
	
	public void invoke(final int row, final int col, final int player) { //只讀不寫
		EventQueue.invokeLater(new Runnable() { //調整UI
			public void run() {
				buttonArr[row * 3 + col].setIcon(icons[player]); //設定client的button
			}
		});
	}
	
	@Override
	public void actionPerformed(ActionEvent e){}
}