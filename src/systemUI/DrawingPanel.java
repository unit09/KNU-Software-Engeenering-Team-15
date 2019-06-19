package systemUI;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

public class DrawingPanel extends JPanel {
	private int unit = 2;
	private int[] numbers = new int[6];
	
	public void paint(Graphics g) {
		 g.clearRect(0,0,getWidth(),getHeight());
		 g.drawLine(50,400,650,400);
		 for(int cnt = 1 ;cnt <= 11;cnt++) {
		  g.drawString(cnt *unit +"",25,400-30*cnt);
		  g.drawLine(50, 400-30*cnt, 650,400-30*cnt);
		 }
		 g.drawLine(50,50,50,400);		 
		 
		 g.drawString("2014��",100,420);
		 g.drawString("2015��",200,420);
		 g.drawString("2016��",300,420);
		 g.drawString("2017��",400,420);
		 g.drawString("2018��",500,420);
		 g.drawString("2019��",600,420);
			 
		 g.setColor(Color.RED);
		 
		 g.fillRect(115,400-numbers[0]*15,15,numbers[0]*15);
		 g.fillRect(215,400-numbers[1]*15,15,numbers[1]*15);
		 g.fillRect(315,400-numbers[2]*15,15,numbers[2]*15);
		 g.fillRect(415,400-numbers[3]*15,15,numbers[3]*15);
		 g.fillRect(515,400-numbers[4]*15,15,numbers[4]*15);
		 g.fillRect(615,400-numbers[5]*15,15,numbers[5]*15);
	}
	
	public void setList(int[] list) {
		numbers = list;
	}
}
