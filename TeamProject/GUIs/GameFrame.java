package GUIs;

import javax.swing.*;

@SuppressWarnings("serial")
public class GameFrame extends JFrame {
	public GameFrame() {
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("Poker Game");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1600, 1000);

		
		frame.getContentPane().add(new MainPanel());
		//frame.pack();
		frame.setVisible(true);
	}
}
