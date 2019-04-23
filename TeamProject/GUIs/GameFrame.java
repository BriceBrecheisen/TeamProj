package GUIs;

import javax.swing.*;
import communications.*;

@SuppressWarnings("serial")
public class GameFrame extends JFrame {
	
	private ChatClient client;
	
	public GameFrame() {
	}

	public static void main(String[] args) {		
		JFrame frame = new JFrame("Poker Game");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1200, 800);

		
		frame.getContentPane().add(new MainPanel());
		//frame.pack();
		frame.setVisible(true);
	}
}
