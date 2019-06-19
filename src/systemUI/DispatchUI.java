package systemUI;

import java.awt.FlowLayout;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import client.Client;
import exchange.*;

public class DispatchUI extends JPanel {
	private JComboBox nations;
	private JComboBox unis;
	private JComboBox majors;
	private JButton search;
	private DefaultTableModel model;
	
	private ArrayList<String> nation = new ArrayList<String>();
	private ArrayList<String> university = new ArrayList<String>();
	private ArrayList<String> major = new ArrayList<String>();
	private ArrayList<Integer> year = new ArrayList<Integer>();
	
	//JTable������ ������ �б�, �Ⱓ�� ���� list�� �ʿ��Ѱ�?
	//���Ⱑ �ƴ϶� �ؿ��� 1ȸ������ �����ؾߵ�����
	
	public DispatchUI(ArrayList<DispatchRecord> records) {
		setLayout(null);
		setSize(700, 540);
		
		nation.add("���� ����");
		university.add("���� ����");
		major.add("���� ����");
		
		for(int i = 0; i < records.size(); i++) {
			DispatchRecord record = records.get(i);
			if(!nation.contains(record.getNation())) {
				nation.add(record.getNation());
			}
			if(!university.contains(record.getUniversity())) {
				university.add(record.getUniversity());
			}
			if(!major.contains(record.getMajor())) {
				major.add(record.getMajor());
			}
		}
		
		nations = new JComboBox(nation.toArray());
		nations.setBounds(379, 28, 117, 36);
		add(nations);
		
		unis = new JComboBox(university.toArray());
		unis.setBounds(117, 28, 117, 36);
		add(unis);
		
		majors = new JComboBox(major.toArray());
		majors.setBounds(248, 28, 117, 36);
		add(majors);
		
		search = new JButton("�˻�");
		search.setBounds(510, 28, 61, 36);
		add(search);
		
		String[][] test = { {"test", "", ""}, {"test2", "", ""} };
		model = new DefaultTableModel(test, major.toArray());
		
		//���� ��Ȳ�� ���� ��Ȳ���� ������ ǥ��?
		
		//�˻� ��ư�� ������ �İ߽����� �����鼭 �˻� ���ǿ� �ش��ϴ� �İ߽����� �а� �׿� �´� JTable�� �׸���
		//�׷����� ǥ�� ���� �˻��� ���������� �����ְ� ���� �˻��� ������ ������ ǥ�� �����ϰ� �ٽ� �׸���
		//Collections.sort(year);
		
		
		JLabel background = new JLabel("");
		background.setIcon(new ImageIcon(DispatchUI.class.getResource("/systemUI/image/graph.jpg")));
		background.setBounds(0, 0, 700, 540);
		add(background);
		
		
	}
}
