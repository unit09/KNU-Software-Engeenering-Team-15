package systemUI;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

import client.Client;
import exchange.DispatchRecord;
import exchange.RecruitmentList;
import user.Administer;
import user.Student;

import javax.swing.GroupLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Font;

public class MainUI extends Observable {
	private javax.swing.JButton qnaB;
    private javax.swing.JButton stateB;
    private javax.swing.JButton creditB;
    private javax.swing.JButton dispatchB;
    private javax.swing.JButton recuitB;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    
    private static ArrayList<DispatchRecord> records;
    Student user;
    Administer admin;
    int userType;
    int year, month, date;
    
    public MainUI(Student userinfo, Client clientpass) {
    	super("교환학생 지원 프로그램");
    	
    	client = clientpass;
        
        if(userinfo.getStudentID() == -1){
            admin = new Administer(userinfo.getName(), userinfo.getYear());
            user = userinfo;
            userType = 1;
        }
        else if(userinfo.getStudentID() == -2){
            userType = 2;
        }
        else{
            user = userinfo;
            userType = 0;
        }

        //DB를 불러오는 부분
        mainList = (RecruitmentList)client.getObject("RecruitmentList");
        records = (ArrayList<DispatchRecord>)client.getObject("DispatchRecord");
        
        if(mainList == null) {
        	mainList = new RecruitmentList();
        }
        if(records == null) {
        	records = new ArrayList<DispatchRecord>();
        }
        
        //오늘 날짜를 불러와서 모집공고들의 상태를 바꿔주는 부분
        Calendar cal = Calendar.getInstance();
        year = cal.get(cal.YEAR);
        month = cal.get(cal.MONTH) + 1;
        date = cal.get(cal.DATE);
        mainList.setRecruitState(year, month, date);
    	
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        initComponents();
        jPanel1.setBackground(new Color(255, 255, 255, 200));
    }

    
    @SuppressWarnings("unchecked")
    
    private void initComponents() {
        jPanel1 = new JPanel();
        stateB = new JButton();
        creditB = new JButton();
        qnaB = new JButton();
        dispatchB = new JButton();
        recuitB = new JButton();
        jLabel3 = new JLabel();
        jLabel4 = new JLabel();
        jLabel1 = new JLabel();
        jLabel2 = new JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        
        //옵저버들 추가
        RecruitLookUI rlUI = new RecruitLookUI(userType, mainList, user);
        addObserver(rlUI);
        rlUI.setResizable(false);     
        
        RecruitDeleteUI rdUI = new RecruitDeleteUI(mainList);
        addObserver(rdUI);
        rdUI.setResizable(false);
        
        StateLookUI slUI = new StateLookUI(mainList, user, records, client);
        addObserver(slUI);
        slUI.setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jPanel1MouseDragged(evt);
            }
        });
        jPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jPanel1MousePressed(evt);
            }
        });
        
        
        stateB.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        if(userType == 1) {
        	stateB.setIcon(new ImageIcon(MainUI.class.getResource("/systemUI/image/UserInterface/\uBAA8\uC9D1\uACF5\uACE0\uC791\uC131.gif")));
        }
        else {
        	stateB.setIcon(new ImageIcon(MainUI.class.getResource("/systemUI/image/UserInterface/\uC9C4\uD589\uC0C1\uD669\uC870\uD68C.gif"))); // NOI18N
        }
        stateB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	Observable.downloadData();
            	Observable.notifyObservers();
            	if(userType == 0) {
            		slUI.setBounds(500, 300, 800, 650);
                    slUI.setVisible(true);
            	}
            	else {
            		RecruitCreateUI rcUI = new RecruitCreateUI(mainList, admin);
                	rcUI.setResizable(false);
                	rcUI.setBounds(500, 300, 800, 650);
                    rcUI.setVisible(true);
            	}
            }
        });
        
        creditB.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        if(userType == 1) {
        	creditB.setIcon(new ImageIcon(MainUI.class.getResource("/systemUI/image/UserInterface/\uBAA8\uC9D1\uACF5\uACE0\uC0AD\uC81C.gif")));
        }
        else {
        	creditB.setIcon(new ImageIcon(MainUI.class.getResource("/systemUI/image/UserInterface/\uC774\uC218\uD559\uC810\uAD00\uB9AC.gif"))); // NOI18N
        }
        creditB.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent arg0) {
        		if(userType == 0) {
        			try {
						CreditUI CRUI = new CreditUI(client, user.getStudentID(), false);
						CRUI.setVisible(true);
						CRUI.setResizable(false);
					} catch (ClassNotFoundException e) {
					}
        		}
        		else {
        			Observable.downloadData();
        			Observable.notifyObservers();
        			rdUI.setBounds(500, 300, 800, 650);
                    rdUI.setVisible(true);
        		}
        	}
        });
        
        qnaB.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        qnaB.setIcon(new ImageIcon(MainUI.class.getResource("/systemUI/image/UserInterface/QNA.gif"))); // NOI18N
        qnaB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	try {
            		JFrame temp = new JFrame();
					QnAUI qnaUI = new QnAUI(user.getStudentID(), client);
					temp.setResizable(false);
					temp.add(qnaUI);
	            	temp.setBounds(500, 300, 472, 500);
	                temp.setVisible(true);
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					//e1.printStackTrace();
				}
            }
        });
        
        dispatchB.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        dispatchB.setIcon(new ImageIcon(MainUI.class.getResource("/systemUI/image/UserInterface/\uD30C\uACAC\uC2E4\uC801\uC870\uD68C.gif"))); // NOI18N
        dispatchB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	DispatchUI dUI = new DispatchUI(records);
                dUI.setResizable(false);
            	dUI.setBounds(500, 300, 800, 650);
                dUI.setVisible(true);
            }
        });
        
        recuitB.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        recuitB.setIcon(new ImageIcon(MainUI.class.getResource("/systemUI/image/UserInterface/\uBAA8\uC9D1\uACF5\uACE0\uC870\uD68C.gif"))); // NOI18N
        recuitB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	Observable.downloadData();
            	Observable.notifyObservers();
                rlUI.setBounds(500, 300, 800, 650);
                rlUI.setVisible(true);
            }
        });
        
        jLabel3.setIcon(new ImageIcon(MainUI.class.getResource("/systemUI/image/UserInterface/\uACBD\uBD81\uB300\uD559\uAD50.gif"))); // NOI18N

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 51, 51));
        jLabel4.setText("짝징");
        jLabel4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
        	@Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel4MouseClicked();
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 51, 51));
        jLabel1.setText("x");
        jLabel1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
        	@Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked();
            }
        });
        
        if(userType == 0) {
        	idNum = new JLabel(Integer.toString(user.getStudentID()));
        	idNum.setFont(new Font("함초롬돋움", Font.BOLD, 20));
        }
        else {
        	idNum = new JLabel("");
        	idNum.setFont(new Font("함초롬돋움", Font.BOLD, 20));
        }
        
        name = new JLabel(user.getName());
        name.setHorizontalAlignment(SwingConstants.LEFT);
        name.setFont(new Font("함초롬돋움", Font.BOLD, 20));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1Layout.setHorizontalGroup(
        	jPanel1Layout.createParallelGroup(Alignment.TRAILING)
        		.addGroup(jPanel1Layout.createSequentialGroup()
        			.addGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING)
        				.addGroup(Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
        					.addContainerGap()
        					.addComponent(name, GroupLayout.PREFERRED_SIZE, 137, GroupLayout.PREFERRED_SIZE))
        				.addGroup(jPanel1Layout.createSequentialGroup()
        					.addGap(39)
        					.addComponent(jLabel3))
        				.addGroup(jPanel1Layout.createSequentialGroup()
        					.addGap(87)
        					.addComponent(idNum)))
        			.addPreferredGap(ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
        			.addGroup(jPanel1Layout.createParallelGroup(Alignment.TRAILING)
        				.addComponent(qnaB, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE)
        				.addComponent(recuitB, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE)
        				.addComponent(creditB, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE))
        			.addGap(26)
        			.addGroup(jPanel1Layout.createParallelGroup(Alignment.TRAILING)
        				.addComponent(stateB, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE)
        				.addComponent(dispatchB, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE))
        			.addGap(66))
        		.addGroup(jPanel1Layout.createSequentialGroup()
        			.addContainerGap(590, Short.MAX_VALUE)
        			.addComponent(jLabel4, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addComponent(jLabel1)
        			.addGap(21))
        );
        jPanel1Layout.setVerticalGroup(
        	jPanel1Layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(jPanel1Layout.createSequentialGroup()
        			.addGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING)
        				.addGroup(jPanel1Layout.createSequentialGroup()
        					.addContainerGap()
        					.addGroup(jPanel1Layout.createParallelGroup(Alignment.BASELINE)
        						.addComponent(jLabel1)
        						.addComponent(jLabel4))
        					.addGap(23)
        					.addGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING)
        						.addComponent(stateB, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE)
        						.addComponent(recuitB, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE))
        					.addGap(18)
        					.addGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING)
        						.addComponent(dispatchB, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE)
        						.addGroup(jPanel1Layout.createSequentialGroup()
        							.addGroup(jPanel1Layout.createParallelGroup(Alignment.TRAILING)
        								.addComponent(idNum)
        								.addComponent(creditB, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE))
        							.addGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING)
        								.addGroup(jPanel1Layout.createSequentialGroup()
        									.addGap(18)
        									.addComponent(qnaB, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE))
        								.addGroup(jPanel1Layout.createSequentialGroup()
        									.addPreferredGap(ComponentPlacement.RELATED)
        									.addComponent(name, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))))))
        				.addGroup(jPanel1Layout.createSequentialGroup()
        					.addGap(31)
        					.addComponent(jLabel3)))
        			.addContainerGap(37, Short.MAX_VALUE))
        );
        jPanel1.setLayout(jPanel1Layout);

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 650, 520));

        jLabel2.setIcon(new ImageIcon(MainUI.class.getResource("/systemUI/image/UserInterface/background.jpg"))); // NOI18N
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 650, 520));

        pack();
    }                       

    int xy;
    int xx;
    private JLabel idNum;
    private JLabel name;
    
    private void jPanel1MouseDragged(MouseEvent evt) {                                     
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();
        this.setLocation(x-xx, y-xy);
    }                                    

    private void jPanel1MousePressed(MouseEvent evt) {                                     
        xx = evt.getX();
        xy = evt.getY();
    }                                    

    private void jLabel1MouseClicked() {                                     
        System.exit(0);
    }                                    

    private void jLabel4MouseClicked() {                                     
        setState(ICONIFIED);
    }                                   
}