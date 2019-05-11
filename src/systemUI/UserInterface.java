package systemUI;

import exchange.*;
import user.*;

import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.io.*;
import java.util.Calendar;

public class UserInterface extends JFrame {
    private JList list;
    private JList list2;
    private JList list3;
    private JTabbedPane Tab = new JTabbedPane(JTabbedPane.LEFT);

	private CreditUI CreditViewIsapped;
	
    private static RecruitmentList sampleList;
    private static File fp = new File("database/rerucitment/Rerucitment DB.txt");

    Student user;
    Administer admin;
    Guest guest;
    int userType;
    int year, month, date;
    
    
    public UserInterface(Student userinfo) {
        super("��ȯ�л� ���α׷�");

        if(userinfo.getStudentID() == -1){
            admin = new Administer(userinfo.getName(), userinfo.getYear());
            userType = 1;
        }
        else if(userinfo.getStudentID() == -2){
            guest = new Guest();
            userType = 2;
        }
        else{
            user = userinfo;
            userType = 0;
        }

        //DB�� �ҷ����� �κ�
        if(fp.length() > 0) {
            try {
                FileInputStream fis = new FileInputStream(fp);
                ObjectInputStream ois = new ObjectInputStream(fis);
                try {
                    sampleList = (RecruitmentList) ois.readObject();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                System.out.println("��������� ����");
                System.exit(1);
            }
        }
        else{
            sampleList = new RecruitmentList();
        }
        
        //���� ��¥�� �ҷ��ͼ� ����������� ���¸� �ٲ��ִ� �κ�
        Calendar cal = Calendar.getInstance();
        year = cal.get(cal.YEAR);
        month = cal.get(cal.MONTH) + 1;
        date = cal.get(cal.DATE);
        
        sampleList.setRecruitState(year, month, date);
        

        setLayout(null);

        Tab.setBounds(10, 10, 650, 480);

        Tab.addTab("����", new Initial());
        Tab.addTab("�������� ��ȸ", new RecruitLook(userType, sampleList, user, list));
        if(userType == 0) {
            Tab.addTab("�����Ȳ ��ȸ", new StateLook(sampleList, user, list3));
    		try {
				Tab.addTab("�̼����� ����", new CreditUI(user.getStudentID(), this, false));
	    		CreditViewIsapped = new CreditUI(user.getStudentID(), this, true);
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			}
    		Tab.addTab("������� ��ȸ", CreditViewIsapped);
        }
        Tab.addTab("�İ߽��� ��ȸ", new JPanel());    //�̱���
        try {
			Tab.addTab("QNA �Խ���", new QnAUI(userinfo.getStudentID()));
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
        if(userType == 1) {
            Tab.addTab("�������� �ۼ�", new RecruitCreate(sampleList, admin, list2));
            Tab.addTab("�������� ����", new RecruitDelete(sampleList, list2));
        }

        Tab.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
            	list.clearSelection();
            	list.setListData(sampleList.printList());
                if(userType == 0) {
                    list3.clearSelection();
                    list3.setListData(sampleList.printState(user.getStudentID()));
                }
            }
        });

        add(Tab);

        //����ɶ� �����ͺ��̽��� ���ε��ϴ� �κ�
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    FileOutputStream fos = new FileOutputStream(fp);
                    ObjectOutputStream oos = new ObjectOutputStream(fos);
                    try{
                        oos.writeObject(sampleList);
                    }catch (Exception ex){
                        ex.printStackTrace();
                    }
                }catch (Exception ex){
                    System.out.println("��������� ����");
                    System.exit(1);
                }
            }
        });
    }

    class Initial extends JPanel{
		private JLabel ment;

        public Initial(){
        	list = new JList(sampleList.printList());   //��ȸ�� �ʿ��� ����Ʈ
            list.setVisibleRowCount(20);
            list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            list.setFixedCellHeight(20);
            list.setFixedCellWidth(120);
            
            list2 = new JList(sampleList.printList2());  //������ �ʿ��� ����Ʈ
            list2.setVisibleRowCount(20);
            list2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            list2.setFixedCellHeight(20);
            list2.setFixedCellWidth(300);

            if(userType == 0) {
                list3 = new JList(sampleList.printState(user.getStudentID()));  //�����Ȳ�� �ʿ��� ����Ʈ
                list3.setVisibleRowCount(19);
                list3.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                list3.setFixedCellHeight(19);
                list3.setFixedCellWidth(500);
            }

            setLayout(null);
            JLabel intro;
            if(userType == 0){
                intro = new JLabel("�������. " + user.getName() + "��       �й� : " + user.getStudentID());
            }
            else if(userType == 1){
                intro = new JLabel("�������. " + admin.getName() + "��");
            }
            else{
                intro = new JLabel("�մ� �������� �����ϼ̱���! ������� �����̿�");
            }
            intro.setBounds(20,140, 400, 20);
            add(intro);

            JLabel dateYo = new JLabel("���� ��¥ : " + year +"�� "+ month + "�� " + date + "��");
            dateYo.setBounds(20, 220, 400, 20);
            add(dateYo);
         
            ment = new JLabel("���Ͻô� ����� ���� �޴����� �������ּ���.");
            ment.setBounds(20, 180, 300, 20);
            add(ment);
        }
    }
    
	public CreditUI getCreditViewIsapped() {
		return CreditViewIsapped;
	}
}