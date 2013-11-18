package nearu;

import java.awt.FlowLayout;
import java.awt.Graphics;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GuiDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame frame = new TestPaintComponent();
		frame.setSize(400, 400);
		frame.setTitle("hello");
		/*
		frame.setLayout(new FlowLayout());
		for(int i = 0; i < 5; i++ ){
			frame.add(new JButton("" + i));
		}
		*/
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}


}
class TestPaintComponent extends JFrame {
	public TestPaintComponent() {
		add(new NewPanel());
	}
}

class NewPanel extends JPanel {
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawLine(0, 0, 50, 50);
		g.drawString("hello", 0, 40);
	}
}
