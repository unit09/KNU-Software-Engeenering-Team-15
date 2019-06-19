package systemUI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import client.Client;
import creditManagement.CompletedCredit;
import creditManagement.CompletedCreditList;

public class CreditUI extends JFrame implements MouseListener {
	
	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
	private JComboBox comboBox;
	
	private JTable credit_JTable;
	private CompletedCreditList c_list;
	ArrayList<CompletedCredit> list;
	
	private CreditSemester SemesterList = null;
	
	private int selected_index = -1;
	private JButton register_button = null;
	
	public CreditUI(Client client, int st_id, boolean isapped) throws ClassNotFoundException  {
		setForeground(Color.WHITE);
        setBackground(new Color(246, 245, 247));
        //setBorder(new EmptyBorder(5, 5, 5, 5));
        //setSize(800, 620);
        setBounds((screenSize.width-800)/2, (screenSize.height-620)/2, 800, 620);
		setLayout(null);
		
		JLabel lblX = new JLabel("X");
        lblX.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent arg0) {
        	//	dispose();
        	}
        });
        lblX.setForeground(Color.BLACK);
        lblX.setFont(new Font("나눔스퀘어라운드 ExtraBold", Font.PLAIN, 23));
        lblX.setBounds(763, 10, 19, 18);
        add(lblX);
        
        JLabel lblNewLabel = new JLabel("이수학점 관리");
        lblNewLabel.setFont(new Font("나눔스퀘어라운드 Bold", Font.PLAIN, 20));
        lblNewLabel.setBounds(329, 12, 200, 52);
        add(lblNewLabel);

		JLabel label = new JLabel("년도/학기");
        label.setIcon(null);
        label.setBackground(Color.WHITE);
        label.setFont(new Font("나눔스퀘어라운드 Light", Font.PLAIN, 18));
        label.setBounds(79, 120, 84, 34);
        add(label);

		comboBox = new JComboBox(CompletedCreditList.get_completed_credit_list(client).semester_list(st_id));
        comboBox.setBounds(170, 126, 120, 24);
        add(comboBox);
        
        JButton button = new JButton("조회");
        button.setBounds(620, 125, 105, 27);
        add(button);
        
        register_button = new JButton("신청");
        register_button.setBounds(500, 125, 105, 27);
        register_button.setVisible(false);
        add(register_button);
		
		if (SemesterList != null) remove(SemesterList);
		
		SemesterList = new CreditSemester(client, st_id, isapped);
		
		ActionListener Search = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
		        if(comboBox.getSelectedItem().toString().contentEquals("내역이 없습니다")) {
		        	
		        }
		        else {
		        	ContentsPrint(client, st_id, isapped);
		        	register_button_check();
		        }
			}
			
		};
        button.addActionListener(Search);
        
        ActionListener Register = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String[] buttons = {"신청", "취소"};
				int result = 0;
				result = JOptionPane.showOptionDialog(null, "신청하시겠습니까?", "학점인정 신청", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, buttons, "취소");
				if(result == 0) {
					try {
						list.get(selected_index - 1).credit_application(client);
						JOptionPane.showMessageDialog(null, "등록되었습니다", "알림", JOptionPane.PLAIN_MESSAGE);
					} catch (ClassNotFoundException e1) {};
				}
			}
        	
        };
        register_button.addActionListener(Register);
	}
	
	public void ContentsPrint (Client client, int st_id, boolean isapped) {
		selected_index = -1;
		
		c_list = null;
	
		String contents_column[] = {"국가", "대학", "학과", "과목", "인정학점", "성적", "비고"};
		
		Object DATA[][];
		
		list = new ArrayList<>();
		
		int year = Integer.parseInt(comboBox.getSelectedItem().toString().substring(0, 4));
		int semester = Integer.parseInt(comboBox.getSelectedItem().toString().substring(6, 7));
		
		try {
			c_list = CompletedCreditList.get_completed_credit_list(client);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(isapped) {
			list = c_list.applicated_credit_list_print(st_id, year, semester);
		}
		else {
			list = c_list.completed_credit_list_print(st_id, year, semester);
		}
		DATA = new Object[list.size() + 1][7];
		DATA[0][0] = "국가";
		DATA[0][1] = "대학";
		DATA[0][2] = "학과";
		DATA[0][3] = "과목";
		DATA[0][4] = "학점";
		DATA[0][5] = "성적";
		DATA[0][6] = "비고";
		for(int i = 0; i < list.size(); i++) {
			DATA[i + 1][0] = list.get(i).getNation();
			DATA[i + 1][1] = list.get(i).getUniv();
			DATA[i + 1][2] = list.get(i).getDept();
			DATA[i + 1][3] = list.get(i).getCourse();
			DATA[i + 1][4] = list.get(i).getAccept_credit();
			DATA[i + 1][5] = list.get(i).getGrade();
			if(list.get(i).isApplication_state()) {
				DATA[i + 1][6] = "등록완료";
			}
			else {
				DATA[i + 1][6] = "미신청";
			}
		}
		
		if(credit_JTable != null) {
			remove(credit_JTable);
		}
		credit_JTable = new JTable(DATA, contents_column);
		credit_JTable.setBackground(Color.WHITE);
		credit_JTable.setGridColor(Color.BLACK);
		credit_JTable.setForeground(Color.BLACK);
		credit_JTable.setBounds(90, 170, 600, 400);
		credit_JTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		credit_JTable.addMouseListener(this);
		
		resize_rowcol(credit_JTable);
		
		
		add(credit_JTable);
		
		revalidate();
		repaint();	
	}
	
	public void resize_rowcol(JTable table) {
		final TableColumnModel model = table.getColumnModel();
		for(int col = 0; col < table.getColumnCount(); col++) {
			int width = 10;
			for(int row = 0; row < table.getRowCount(); row++) {
				TableCellRenderer renderer = table.getCellRenderer(row, col);
				Component comp = table.prepareRenderer(renderer, row, col);
				width = Math.max(comp.getPreferredSize().width + 1, width);
			}
			model.getColumn(col).setPreferredWidth(width);
		}
	}
	
	
	class CreditSemester extends JPanel{
		private Vector<String> semester_Vector;
		private JList<String> credit_JList;

		public CreditSemester(Client client, int st_id, boolean isapped) throws ClassNotFoundException {
			setLayout(null);
			c_list = CompletedCreditList.get_completed_credit_list(client);
			String[] credit_list_string;
			if(isapped) {
				credit_list_string = c_list.semester_list_isapped(st_id);				
			}
			else {
				credit_list_string = c_list.semester_list(st_id);
			}
			semester_Vector = new Vector<String>();
			if(credit_list_string != null) {
				for(String one : credit_list_string) {
					semester_Vector.add(one);
				}
				
				credit_JList = new JList<String>(credit_list_string);
				
			}
			else {
				semester_Vector.add("내역이 없습니다");
				
				String[] JTableData = {"내역이 없습니다"};
				credit_JList = new JList<String>(JTableData);
				
			}
			
			credit_JList.setVisibleRowCount(20);
			credit_JList.setFixedCellHeight(20);
			credit_JList.setFixedCellWidth(120);
			JScrollPane ScrollList = new JScrollPane(credit_JList);

	        ScrollList.setBounds(170, 126, 100, 100);
			
			add(ScrollList);
		}

		public JList<String> getCredit_JList() {
			return credit_JList;
		}

		public void setCredit_JList(JList<String> credit_JList) {
			this.credit_JList = credit_JList;
		}
		
		public CompletedCreditList getC_list() {
			return c_list;
		}

	}
	
	public void register_button_check() {
		if(selected_index > 0 && !list.get(selected_index - 1).isApplication_state()) {
			register_button.setVisible(true);
		}
		else {
			register_button.setVisible(false);
		}

		revalidate();
		repaint();	
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		if(credit_JTable != null) {
			selected_index = credit_JTable.getSelectedRow();
		}
		else {
			selected_index = -1;
		}
		register_button_check();
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
	
