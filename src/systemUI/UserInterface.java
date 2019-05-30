package systemUI;

import exchange.*;
import user.*;
import client.Client;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.io.*;
import java.util.*;

public class UserInterface extends Observable {
    private JTabbedPane Tab = new JTabbedPane(JTabbedPane.LEFT);
    
    private static ArrayList<DispatchRecord> records;
    private static File fp2 = new File("database/dispatch_record/Dispatch Record.txt");

    private CreditUI CreditViewIsapped;
    
    Student user;
    Administer admin;
    Guest guest;
    int userType;
    int year, month, date;
    
    public UserInterface(Student userinfo) {
        super("��ȯ�л� ���� ���α׷�");

        client = new Client();
        
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
        mainList = (RecruitmentList)client.getObject("RecruitmentList");
        records = (ArrayList<DispatchRecord>)client.getObject("DispatchRecord");
        
        if(mainList == null) {
        	mainList = new RecruitmentList();
        }
        if(records == null) {
        	records = new ArrayList<DispatchRecord>();
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
        	
            Tab.addTab("�����Ȳ ��ȸ", new StateLookUI(mainList, user, records, client));
            
    		try {
				Tab.addTab("�̼����� ����", new CreditUI(user.getStudentID(), this, false));
	    		CreditViewIsapped = new CreditUI(user.getStudentID(), this, true);
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			}
    		
    		Tab.addTab("������� ��ȸ", CreditViewIsapped);
        }
        
        Tab.addTab("�İ߽��� ��ȸ", new DispatchUI(records));
        
        try {
			Tab.addTab("QNA �Խ���", new QnAUI(userinfo.getStudentID(), client));
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
        
        if(userType == 1) {
            Tab.addTab("�������� �ۼ�", new RecruitCreateUI(mainList, admin));
            
            RecruitDeleteUI rdUI = new RecruitDeleteUI(mainList);
            addObserver(rdUI);
            Tab.addTab("�������� ����", rdUI);
        }

        /*Tab.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                Observable.downloadData();
                Observable.notifyObservers();
            }
        });*/
        
        add(Tab);

        /*addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
            	client.setObject("DispatchRecord", records);
            }
        });*/
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

            JLabel dateSet = new JLabel("���� ��¥ : " + year +"�� "+ month + "�� " + date + "��");
            dateSet.setBounds(20, 220, 400, 20);
            add(dateSet);
         
            ment = new JLabel("���Ͻô� ����� ���� �޴����� �������ּ���.");
            ment.setBounds(20, 180, 300, 20);
            add(ment);
        }
    }
    
    
    
	public CreditUI getCreditViewIsapped() {
		return CreditViewIsapped;
	}
}