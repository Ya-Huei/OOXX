import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.net.*;
import java.util.*;


public class OOXX_Client extends JFrame implements ActionListener { 
	private ImageIcon icons[] = {new ImageIcon("SmallFireDragon.gif"), new ImageIcon("WaterTurtle.gif")};
	
	private static int row;
	private static int col;
	
	private int[][] jFrame;
	private static int player;
	private int count = 0;
	
	public static JButton[] buttonArr;

	static private OOXX_Client_Thread OXCT;
	
	public OOXX_Client() throws HeadlessException {
		this(3, 3);
	}

	public OOXX_Client(int row, int col) throws HeadlessException {
		super();
		this.row = row;
		this.col = col;
	}
	
	public void newGame() {
		jFrame = new int[row][col];
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				jFrame[i][j] = -1;
			}
		}
		newFrame();
		setVisible(true);
	}
	
	private void newFrame() {
		setLayout(new GridLayout(row, col));
		setTitle("OOXX");
		
		int w = col * 80;
		int h = row * 80;
		setSize(w, h);

		buttonArr = new JButton[row * col];
		
		for (int i = 0; i < row * col; i++) {
			buttonArr[i] = new JButton();
			buttonArr[i].setActionCommand(String.valueOf(i));
			buttonArr[i].addActionListener(this);
			add(buttonArr[i]);
		}
	}

	private int check(int player, int[][] checkArr) {
		int noBodyWin = -1;
		int checkColCount = 0;
		int checkRowCount = 0;
		int leftUpRightDown = 0;
		int rightUpLeftDown = 0;
		this.player = player;
		checkArr = jFrame;
		
		for(int i = 0; i < row; i++){
			for(int j = 0; j < col; j++){
				if(checkArr[i][j] == player){
					checkColCount++;
				}
				if(checkArr[j][i] == player){
					checkRowCount++;
				}
				if(checkArr[j][j] == player){
					leftUpRightDown++;
				}
			}
			if(checkArr[i][row - i - 1] == player){
				rightUpLeftDown++;
			}
			if(checkColCount == row || checkRowCount == row || leftUpRightDown == row || rightUpLeftDown == row){
				return player;
			}
			checkColCount = 0;
			checkRowCount = 0;
			leftUpRightDown = 0;
		}
		rightUpLeftDown = 0;
		return noBodyWin;
	}
	
	public static void main(String[] args) {
		OOXX_Client game = new OOXX_Client();
		game.newGame();
		
		OXCT = new OOXX_Client_Thread("127.0.0.1", 1200, buttonArr);
		player = OXCT.setPlayer();
		OXCT.start();
	}
	
	public void actionPerformed(ActionEvent e) { 
		String cmd = e.getActionCommand();
		int loc = Integer.parseInt(cmd);
		int aRow = loc / col;
		int aCol = loc % col;
		if (jFrame[aRow][aCol] != -1)
			return;
		OXCT.send(aRow, aCol, player);
		jFrame[aRow][aCol] = player;
		JButton btn = (JButton) e.getSource();
		btn.setIcon(icons[player]);
		
		this.repaint();
		int win = check(player, jFrame);
		count++; //cc
		
		if (win == 0){
			JOptionPane.showMessageDialog(null, "SmallFireDragon WIN!!", "Game Over", JOptionPane.INFORMATION_MESSAGE); //likj
		}
		else if(win == 1){
			JOptionPane.showMessageDialog(null, "WaterTurtle WIN!!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
		}
		else if(count == row * col){
			JOptionPane.showMessageDialog(null, "The game has DRAWN!!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
		}
	}
}
