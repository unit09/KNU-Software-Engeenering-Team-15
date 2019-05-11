package systemUI;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import exchange.RecruitmentList;
import user.Student;

public class StateLook extends JPanel{
    private JButton del;
    private JButton sel;

    public StateLook(RecruitmentList sampleList, Student user, JList list){
        setLayout(new FlowLayout());
        setSize(500, 400);

        add(new JScrollPane(list));

        sel = new JButton("���� ���");
        sel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if(!list.isSelectionEmpty()) {
            		String test = (String)list.getSelectedValue();
            		int in = test.indexOf(".");
            		test = test.substring(0, in);
            		sampleList.choiceYo(Integer.parseInt(test), user.getStudentID());
            		list.setListData(sampleList.printState(user.getStudentID()));
            	}
            }
        });
        add(sel);

        del = new JButton("���� ���");
        del.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if(!list.isSelectionEmpty()) {
            		String test = (String)list.getSelectedValue();
            		int in = test.indexOf(".");
            		test = test.substring(0, in);
            		sampleList.deleteAplication(Integer.parseInt(test), user.getStudentID());
            		list.setListData(sampleList.printState(user.getStudentID()));
            		JOptionPane.showMessageDialog(null, "��ҵǾ����ϴ�.", "�˸�", JOptionPane.PLAIN_MESSAGE);
            	}
            }
        });
        add(del);
    }
}
