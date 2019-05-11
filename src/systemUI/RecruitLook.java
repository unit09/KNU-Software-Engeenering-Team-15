package systemUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import exchange.*;
import user.*;

public class RecruitLook extends JPanel{
    private JButton select;
    private JPanel con;

    public RecruitLook(int userType, RecruitmentList sampleList, Student user, JList list){
    	
        
        setLayout(new FlowLayout());
        setSize(500, 400);

        add(new JScrollPane(list));

        con = new JPanel();
        con.setPreferredSize(new Dimension(380,450));
        con.setBackground(Color.WHITE);
        add(con);

        list.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int index = list.getSelectedIndex();
                con.removeAll();
                if(index >= 0) {
                    con.add(sampleList.printContents(index));
                    select = new JButton("���ÿ��� �ۼ�");

                    select.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                        	if(sampleList.getRecruitState(index) == 0) {
                        		if (sampleList.checkUser(index, user.getStudentID()) == false) {
                                    Application newone = user.ApplicationCreate(sampleList.getRecruitNum(index));
                                    sampleList.apply(index, newone);
                                    //list3.setListData(sampleList.printState(user.getStudentID()));
                                    JOptionPane.showMessageDialog(null, "���ÿ��� �ۼ��� �Ϸ�Ǿ����ϴ�.", "�˸�", JOptionPane.PLAIN_MESSAGE);
                                } else
                                    JOptionPane.showMessageDialog(null, "�̹� ������ ���������Դϴ�.", "�˸�", JOptionPane.PLAIN_MESSAGE);
                        	}
                        	else {
                        		JOptionPane.showMessageDialog(null, "��û�� �� ���� �����Դϴ�.", "�˸�", JOptionPane.PLAIN_MESSAGE);
                        	}
                        }
                    });
                    if(userType == 0)
                        con.add(select);
                }
                con.revalidate();
                con.repaint();
            }
        });
    }
}
