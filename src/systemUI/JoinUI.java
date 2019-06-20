package systemUI;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import client.Client;

import javax.swing.JFrame;
import javax.swing.JLabel;

import user.*;

public class JoinUI extends JFrame {
    private JPanel contentPane;
    private JTextField pwdPassword;
    private JTextField IDField;
    
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    int xx,xy;
    private JTextField passwordField;
    private JTextField name_Field;
    private JTextField textField;
    private JTextField textField_1;
    private JTextField textField_2;
    private JTextField textField_3;
    private JTextField textField_4;

    private boolean IDcheck;
    private String stored;
    
    private static final String FONT1 = "������������� ExtraBold";
    private static final String FONT2 = "������������� Regular";
    private static final String CAUTION = "caution";
    
    public JoinUI(Client client) {
    	IDcheck = false;
    	
    	setTitle("ȸ������");
    	setBackground(Color.WHITE);
    	
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds((screenSize.width-395)/2, (screenSize.height-800)/2, 395, 800); 
        contentPane = new JPanel();
        contentPane.setForeground(Color.WHITE);
        contentPane.setBackground(new Color(246, 245, 247));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        pwdPassword = new JPasswordField();
        pwdPassword.setFont(new Font("����", Font.PLAIN, 18));
        pwdPassword.setBounds(31, 172, 312, 45);
        contentPane.add(pwdPassword);
        
        passwordField = new JPasswordField();
        passwordField.setFont(new Font("����", Font.PLAIN, 18));
        passwordField.setBounds(31, 237, 312, 45);
        contentPane.add(passwordField);

        IDField = new JTextField();
        IDField.setFont(new Font(FONT2, Font.PLAIN, 18));
        IDField.setBounds(31, 101, 210, 45);
        contentPane.add(IDField); 
        IDField.setColumns(10);
        
        JButton succ_button = new JButton("\uAC00\uC785\uD558\uAE30");
        succ_button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        succ_button.setForeground(Color.WHITE);
        succ_button.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		if(IDcheck == false) {
        			JOptionPane.showMessageDialog(null, "���̵� �ߺ�Ȯ���� ���ֽñ� �ٶ��ϴ�.", CAUTION, JOptionPane.DEFAULT_OPTION);
        		}
        		else {
        			if(checkIDPWFormat()) {
        				if(checkIntFormat()) {
        					Student temp = new Student(Integer.parseInt(textField.getText()), name_Field.getText(), textField_4.getText(), Integer.parseInt(textField_1.getText()), 3.3, textField_2.getText(), textField_3.getText());
        					client.setObject(IDField.getText(), pwdPassword.getText(), temp);
        					client.setString(IDField.getText() + "##", "on");
        					JOptionPane.showMessageDialog(null, "ȸ�������� �Ϸ�Ǿ����ϴ�.", "ȸ������ �Ϸ�", JOptionPane.DEFAULT_OPTION);
        					dispose();
        				}
        			}
        		}
        	}
        });
        
        JLabel lblNewLabel = new JLabel("ex)010123456789 - \uC5C6\uC774 \uC785\uB825\uD558\uC138\uC694");
        lblNewLabel.setFont(new Font("������������� Bold", Font.PLAIN, 16));
        lblNewLabel.setForeground(Color.GRAY);
        lblNewLabel.setBounds(41, 662, 286, 18);
        contentPane.add(lblNewLabel);
        
        JLabel label_6 = new JLabel("");
        label_6.setIcon(new ImageIcon(JoinUI.class.getResource("/systemUI/image/join3.PNG")));
        label_6.setBounds(79, 12, 218, 68);
        contentPane.add(label_6);
        succ_button.setFont(new Font(FONT1, Font.BOLD, 26));
        succ_button.setBackground(new Color(135, 206, 250));
        succ_button.setBounds(31, 701, 312, 45);
        contentPane.add(succ_button);
        
        JLabel ID_label = new JLabel("\uC544\uC774\uB514");
        ID_label.setFont(new Font(FONT1, Font.PLAIN, 12));
        ID_label.setBounds(31, 85, 57, 15);
        contentPane.add(ID_label);
        
        JLabel PASSWD_label = new JLabel("\uBE44\uBC00\uBC88\uD638");
        PASSWD_label.setFont(new Font(FONT1, Font.PLAIN, 12));
        PASSWD_label.setBounds(31, 155, 86, 15);
        contentPane.add(PASSWD_label);
        
        
        name_Field = new JTextField();
        name_Field.setFont(new Font(FONT2, Font.PLAIN, 18));
        name_Field.setBounds(31, 301, 312, 45);
        contentPane.add(name_Field);
        name_Field.setColumns(10);
        
        JLabel label = new JLabel("\uBE44\uBC00\uBC88\uD638 \uC7AC\uD655\uC778");
        label.setFont(new Font(FONT1, Font.PLAIN, 12));
        label.setBounds(31, 222, 96, 15);
        contentPane.add(label);
        
        JLabel name_label = new JLabel("\uC774\uB984");
        name_label.setFont(new Font(FONT1, Font.PLAIN, 12));
        name_label.setBounds(31, 285, 86, 15);
        contentPane.add(name_label);
        
        JButton id_chack_button = new JButton("\uC911\uBCF5\uD655\uC778");
        id_chack_button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        id_chack_button.setForeground(Color.WHITE);
        id_chack_button.setBackground(new Color(135,206,250));
        id_chack_button.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		String checkID = IDField.getText();
        		String check = (String)client.getString(checkID + "##");
            	
            	if(IDField.getText().equals("")) {
            		JOptionPane.showMessageDialog(null, "���̵� �����Դϴ�.", CAUTION, JOptionPane.DEFAULT_OPTION);
            	}            	
            	else if(check != null) {
            		IDcheck = false;
            		JOptionPane.showMessageDialog(null, "�̹� �����ϴ� ���̵��Դϴ�.", CAUTION, JOptionPane.DEFAULT_OPTION);
            	}
            	else {
              		IDcheck = true;
              		stored = checkID;
            		JOptionPane.showMessageDialog(null, "��밡���� ���̵��Դϴ�.", "�˸�", JOptionPane.DEFAULT_OPTION);
            	}
            }
        });
        id_chack_button.setFont(new Font(FONT1, Font.PLAIN, 17));
        id_chack_button.setBounds(242, 101, 100, 44);
        contentPane.add(id_chack_button);
        
        textField = new JTextField();
        textField.setFont(new Font(FONT2, Font.PLAIN, 18));
        textField.setColumns(10);
        textField.setBounds(31, 434, 312, 45);
        contentPane.add(textField);
        
        JLabel label_1 = new JLabel("\uD559\uBC88");
        label_1.setFont(new Font(FONT1, Font.PLAIN, 12));
        label_1.setBounds(31, 417, 86, 15);
        contentPane.add(label_1);
        
        textField_1 = new JTextField();
        textField_1.setFont(new Font(FONT2, Font.PLAIN, 18));
        textField_1.setColumns(10);
        textField_1.setBounds(31, 505, 312, 45);
        contentPane.add(textField_1);
        
        JLabel label_2 = new JLabel("\uD559\uB144");
        label_2.setFont(new Font(FONT1, Font.BOLD, 12));
        label_2.setBounds(31, 488, 86, 15);
        contentPane.add(label_2);
        
        textField_2 = new JTextField();
        textField_2.setFont(new Font(FONT2, Font.PLAIN, 18));
        textField_2.setColumns(10);
        textField_2.setBounds(31, 576, 312, 45);
        contentPane.add(textField_2);
        
        JLabel label_3 = new JLabel("\uC774\uBA54\uC77C");
        label_3.setFont(new Font(FONT1, Font.PLAIN, 12));
        label_3.setBounds(31, 559, 86, 15);
        contentPane.add(label_3);
        
        textField_3 = new JTextField();
        textField_3.setFont(new Font(FONT2, Font.PLAIN, 18));
        textField_3.setColumns(10);
        textField_3.setBounds(31, 647, 312, 45);
        contentPane.add(textField_3);
        
        JLabel label_4 = new JLabel("\uD734\uB300\uD3F0\uBC88\uD638");
        label_4.setFont(new Font(FONT1, Font.PLAIN, 12));
        label_4.setBounds(31, 630, 86, 15);
        contentPane.add(label_4);
        
        textField_4 = new JTextField();
        textField_4.setFont(new Font(FONT2, Font.PLAIN, 18));
        textField_4.setColumns(10);
        textField_4.setBounds(31, 366, 312, 45);
        contentPane.add(textField_4);
        
        JLabel label_5 = new JLabel("\uD559\uACFC");
        label_5.setFont(new Font(FONT1, Font.PLAIN, 12));
        label_5.setBounds(31, 350, 86, 15);
        contentPane.add(label_5);
        
        JLabel lblX = new JLabel("X");
        lblX.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblX.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent arg0) {
        		dispose();
        	}
        });
        lblX.setForeground(new Color(135, 206, 250));
        lblX.setFont(new Font(FONT1, Font.PLAIN, 23));
        lblX.setBounds(360, 10, 19, 18);
        contentPane.add(lblX);
    }
    
    private boolean checkFormat(String s) {
    	try {
    		Integer.parseInt(s);
    		return true;
    	}
    	catch(NumberFormatException e) {
    		return false;
    	}    	
    }
    
    private boolean checkIDPWFormat() {
    	if(IDField.getText().equals("") || pwdPassword.getText().equals("") || name_Field.getText().equals("") || textField_4.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "�Է����� ���� �׸��� �ֽ��ϴ�.", CAUTION, JOptionPane.DEFAULT_OPTION);
			return false;
		}
		if(!stored.equals(IDField.getText())) {
			IDcheck = false;
			JOptionPane.showMessageDialog(null, "���̵� �ߺ�Ȯ���� ���ֽñ� �ٶ��ϴ�.", CAUTION, JOptionPane.DEFAULT_OPTION);
			return false;
		}        			
		if(!pwdPassword.getText().equals(passwordField.getText())) {
			JOptionPane.showMessageDialog(null, "��й�ȣ�� ��й�ȣ Ȯ���� ���� �ٸ��ϴ�.", CAUTION, JOptionPane.DEFAULT_OPTION);
			return false;
		}	
		return true;
    }
    
    private boolean checkIntFormat() {
    	if(!checkFormat(textField.getText()))
			JOptionPane.showMessageDialog(null, "�й� �Է��� �߸��Ǿ����ϴ�.\n������ �̷���� �й��� �Է��ؾ��մϴ�.", CAUTION, JOptionPane.ERROR_MESSAGE);
		else if(!checkFormat(textField_1.getText()))
			JOptionPane.showMessageDialog(null, "�г� �Է��� �߸��Ǿ����ϴ�.\n������ �Է��ؾ��մϴ�.", CAUTION, JOptionPane.ERROR_MESSAGE);
		else {
			return true;
		}
    	
    	return false;
    }
}
