package systemUI;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

import client.Client;
import exchange.*;
import user.Student;

public class StateLookUI extends JPanel implements Observer {
    private JButton del;
    private JButton sel;
    private JList list;
    private Student man;

    public StateLookUI(RecruitmentList mainList, Student user, ArrayList<DispatchRecord> records, Client client){
    	list = new JList(mainList.printState(user.getStudentID()));  //�����Ȳ�� �ʿ��� ����Ʈ
        list.setVisibleRowCount(19);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setFixedCellHeight(19);
        list.setFixedCellWidth(500);
    	
        man = user;
        
        setLayout(new FlowLayout());
        setSize(500, 400);
        
        add(new JScrollPane(list));

        sel = new JButton("���� ���");
        sel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if(!list.isSelectionEmpty()) {
            		String[] buttons = {"���� ���", "���"};
    				int result = 0;
    				result = JOptionPane.showOptionDialog(null, "���� ����� �Ͻðڽ��ϱ�?", "�հ� ���� ���� ���", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, buttons, "���");
    				if(result == 0) {
	            		String buf = (String)list.getSelectedValue();
	            		int in = buf.indexOf(".");
	            		buf = buf.substring(0, in);
	            		int index = mainList.finalChoice(Integer.parseInt(buf), man.getStudentID());
	            		Observable.uploadData();
	            		Observable.notifyObservers();
	            		list.setListData(mainList.printState(man.getStudentID()));
	            		
	            		DispatchRecord newone = new DispatchRecord(mainList.getNation(index), mainList.getUniv(index), mainList.getYear(index), mainList.getSemester(index), mainList.getPeriod(index), mainList.getMajor(index));
	            		records.add(newone); 
	            		client.setObject("DispatchRecord", records);
    				}
            	}
            }
        });
        add(sel);

        del = new JButton("���� ���");
        del.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if(!list.isSelectionEmpty()) {
            		String[] buttons = {"���� ���", "���"};
    				int result = 0;
    				result = JOptionPane.showOptionDialog(null, "���õ� ������ ����Ͻðڽ��ϱ�?", "���� ���", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, buttons, "���");
    				if(result == 0) {
	            		String buf = (String)list.getSelectedValue();
	            		int in = buf.indexOf(".");
	            		buf = buf.substring(0, in);
	            		mainList.deleteAplication(Integer.parseInt(buf), user.getStudentID());
	            		Observable.notifyObservers();
	            		list.setListData(mainList.printState(man.getStudentID()));
	            		JOptionPane.showMessageDialog(null, "��ҵǾ����ϴ�.", "�˸�", JOptionPane.PLAIN_MESSAGE);
    				}
            	}
            }
        });
        add(del);
    }
    
    public void update(RecruitmentList mainList) {
    	list.setListData(mainList.printState(man.getStudentID()));
    }
}
