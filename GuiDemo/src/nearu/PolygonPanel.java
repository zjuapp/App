package nearu;

import java.awt.Graphics;
import java.awt.Polygon;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class PolygonPanel extends JPanel{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame frame = new JFrame("hei");
		PolygonPanel p = new PolygonPanel();
		frame.setSize(100,100);
		frame.setLocationRelativeTo(null);
		frame.add(p);
		frame.setVisible(true);
		 
		
	}
	protected void paintComponent(Graphics g){
		Polygon p = new Polygon();
		p.addPoint(0, 0);
		p.addPoint(0, 10);
		p.addPoint(10, 10);
		p.addPoint(10, 0);
		g.drawPolygon(p);
	}

}
