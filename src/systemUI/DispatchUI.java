package systemUI;

import java.awt.FlowLayout;
import java.util.ArrayList;
import javax.swing.*;
import exchange.*;

public class DispatchUI extends JPanel {
	JComboBox test;
	ArrayList<String> nation = new ArrayList<String>();
	ArrayList<String> university = new ArrayList<String>();;
	ArrayList<String> major = new ArrayList<String>();;
	
	public DispatchUI(ArrayList<DispatchRecord> record) {
		setLayout(new FlowLayout());
		setSize(500, 400);
		
		nation.add("�������� ����");
		nation.add("õ����");
		test = new JComboBox(nation.toArray());
		
		add(test);
	}
}
