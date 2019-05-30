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

public class RecruitLookUI extends JPanel implements Observer {
    private JButton select;
    private JPanel con;
    private JList list;

    public RecruitLookUI(int userType, RecruitmentList mainList, Student user){
    	list = new JList(mainList.printList());   //��ȸ�� �ʿ��� ����Ʈ
        list.setVisibleRowCount(20);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setFixedCellHeight(20);
        list.setFixedCellWidth(120);
        
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
                    con.add(mainList.printContents(index));
                    select = new JButton("���ÿ��� �ۼ�");

                    select.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                        	if(mainList.getRecruitState(index) == 0) {
                        		if (mainList.checkUser(index, user.getStudentID()) == false) {
                                    Application newone = user.ApplicationCreate(mainList.getRecruitNum(index));
                                    mainList.apply(index, newone);
                                    Observable.uploadData();
                                    Observable.notifyObservers();
                                    
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
    
    public void update(RecruitmentList mainList) {
    	list.setListData(mainList.printList());
    }
}
