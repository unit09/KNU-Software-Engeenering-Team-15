package systemUI;

import exchange.*;
import user.*;

import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.io.*;
import java.util.*;

public class UserInterface extends Observable {
    private JList list;
    private JList list2;
    private JList list3;
    private JTabbedPane Tab = new JTabbedPane(JTabbedPane.LEFT);

	private CreditUI CreditViewIsapped;
	
    private static ArrayList<DispatchRecord> record;
    private static File fp = new File("database/rerucitment/Rerucitment DB.txt");
    private static File fp2 = new File("database/dispatch_record/Dispatch Record.txt");

    Student user;
    Administer admin;
    Guest guest;
    int userType;
    int year, month, date;
    
    public UserInterface(Student userinfo) {
        super("��ȯ�л� ���� ���α׷�");

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

        //DB�� �ҷ����� �κ� - ���߿� ����� ����
        if(fp.length() > 0) {
            try {
                FileInputStream fis = new FileInputStream(fp);
                ObjectInputStream ois = new ObjectInputStream(fis);
                try {
                    mainList = (RecruitmentList) ois.readObject();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                System.out.println("��������� ����");
                System.exit(1);
            }
        }
        else{
            mainList = new RecruitmentList();
        }
        
        if(fp2.length() > 0) {
            try {
                FileInputStream fis = new FileInputStream(fp2);
                ObjectInputStream ois = new ObjectInputStream(fis);
                try {
                    record = (ArrayList<DispatchRecord>) ois.readObject();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                System.out.println("fp2 �ε� ����");
                System.exit(1);
            }
        }
        else{
        	record = new ArrayList<DispatchRecord>();
        }
        
        
        //���� ��¥�� �ҷ��ͼ� ����������� ���¸� �ٲ��ִ� �κ�
        Calendar cal = Calendar.getInstance();
        year = cal.get(cal.YEAR);
        month = cal.get(cal.MONTH) + 1;
        date = cal.get(cal.DATE);
        mainList.setRecruitState(year, month, date);
        

        setLayout(null);

        Tab.setBounds(10, 10, 650, 480);

        Tab.addTab("����", new Initial());
        
        RecruitLookUI rlUI = new RecruitLookUI(userType, mainList, user);
        addObserver(rlUI);
        Tab.addTab("�������� ��ȸ", rlUI);
        
        if(userType == 0) {
            Tab.addTab("�����Ȳ ��ȸ", new StateLookUI(mainList, user, record));
    		try {
				Tab.addTab("�̼����� ����", new CreditUI(user.getStudentID(), this, false));
	    		CreditViewIsapped = new CreditUI(user.getStudentID(), this, true);
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			}
    		Tab.addTab("������� ��ȸ", CreditViewIsapped);
        }
        
        Tab.addTab("�İ߽��� ��ȸ", new DispatchUI(record));
        
        try {
			Tab.addTab("QNA �Խ���", new QnAUI(userinfo.getStudentID()));
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
        
        if(userType == 1) {
            Tab.addTab("�������� �ۼ�", new RecruitCreateUI(mainList, admin));
            RecruitDeleteUI rdUI = new RecruitDeleteUI(mainList);
            addObserver(rdUI);
            Tab.addTab("�������� ����", rdUI);
        }

        add(Tab);

        //����ɶ� �����ͺ��̽��� ���ε��ϴ� �κ� - ���߿� ����� ����
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    FileOutputStream fos = new FileOutputStream(fp);
                    ObjectOutputStream oos = new ObjectOutputStream(fos);
                    try{
                        oos.writeObject(mainList);
                    }catch (Exception ex){
                        ex.printStackTrace();
                    }
                }catch (Exception ex){
                    System.out.println("��������� ����");
                    System.exit(1);
                }
                
                if(!record.isEmpty()) {
                	try {
                		FileOutputStream fos = new FileOutputStream(fp2);
                		ObjectOutputStream oos = new ObjectOutputStream(fos);
                		try{
                			oos.writeObject(record);
                		}catch (Exception ex){
                			ex.printStackTrace();
                		}
                	}catch (Exception ex){
                			System.out.println(ex);
                			System.exit(1);
                	}
                }
            }
        });
    }

    class Initial extends JPanel{
		private JLabel ment;

        public Initial(){            
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