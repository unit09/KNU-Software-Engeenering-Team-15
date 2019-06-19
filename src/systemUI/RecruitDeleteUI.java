package systemUI;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import exchange.RecruitmentList;
import user.Administer;
import java.awt.Font;
import java.awt.Color;

public class RecruitDeleteUI extends JFrame implements Observer{
    private JButton delet;
    private JList list;

    public RecruitDeleteUI(RecruitmentList mainList) {
    	super("�������� ����");
    	
    	list = new JList(mainList.printList2());  //������ �ʿ��� ����Ʈ
        list.setVisibleRowCount(20);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setFixedCellHeight(20);
        list.setFixedCellWidth(300);
        setSize(800, 620);
        setLayout(null);

        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setBounds(71, 163, 662, 326);
        add(scrollPane);

        delet = new JButton("����");
        delet.setFont(new Font("���� ���", Font.PLAIN, 20));
        delet.setBounds(355, 501, 98, 33);
        delet.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = list.getSelectedIndex();
                if (index == -1)
                    JOptionPane.showMessageDialog(null, "������ �������� �����ϼ���.", "�˸�", JOptionPane.PLAIN_MESSAGE);
                else{
                	String[] buttons = {"����", "���"};
    				int result = 0;
    				result = JOptionPane.showOptionDialog(null, "��ϵ� �������� �����Ͻðڽ��ϱ�?", "�������� ����", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, buttons, "���");
    				if(result == 0) {
	                    mainList.deleteList(index);
	                    Observable.uploadData();
	                    Observable.notifyObservers();
	                    JOptionPane.showMessageDialog(null, "�������� �����Ǿ����ϴ�.", "�˸�", JOptionPane.PLAIN_MESSAGE);
    				}
                }
            }
        });

        add(delet);
        
        JLabel label = new JLabel("");
        label.setIcon(new ImageIcon(RecruitDeleteUI.class.getResource("/systemUI/image/board-crop.jpg")));
        label.setBounds(0, 0, 800, 620);
        add(label);
    }
    
    public void update(RecruitmentList mainList) {
    	list.setListData(mainList.printList2());
    }
}
