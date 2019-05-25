package systemUI;

import java.awt.FlowLayout;
import java.util.*;
import javax.swing.*;
import exchange.*;

public class DispatchUI extends JPanel {
	private JComboBox nations;
	private JComboBox unis;
	private JComboBox majors;
	private JButton search;
	private JTable table;
	
	private ArrayList<String> nation = new ArrayList<String>();
	private ArrayList<String> university = new ArrayList<String>();
	private ArrayList<String> major = new ArrayList<String>();
	private ArrayList<Integer> year = new ArrayList<Integer>(); 
	//JTable������ ������ �б�, �Ⱓ�� ���� list�� �ʿ��Ѱ�?
	//���Ⱑ �ƴ϶� �ؿ��� 1ȸ������ �����ؾߵ�����
	
	public DispatchUI(ArrayList<DispatchRecord> records) {
		setLayout(new FlowLayout());
		setSize(500, 400);
		
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
		unis = new JComboBox(university.toArray());
		majors = new JComboBox(major.toArray());
		
		search = new JButton("�˻�");
		//�˻� ��ư�� ������ �İ߽����� �����鼭 �˻� ���ǿ� �ش��ϴ� �İ߽����� �а� �׿� �´� JTable�� �׸���
		//�׷����� ǥ�� ���� �˻��� ���������� �����ְ� ���� �˻��� ������ ������ ǥ�� �����ϰ� �ٽ� �׸���
		//Collections.sort(year);
		
		add(nations);
		add(unis);
		add(majors);
		
		add(search);
		
		
	}
}
