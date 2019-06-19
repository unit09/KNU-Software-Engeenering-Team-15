package systemUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.Stack;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import client.Client;
import question.Faq;
import question.QnAList;
import question.Qna;

public class QnAUI extends JPanel{

	private JPanel contentPane;
	static Stack<JList> previousPanels = new Stack<JList>();
	static Stack<JPanel> previousButtons = new Stack<JPanel>();
	JList previousListTemp; //
	JPanel previousPanelTemp;
	JButton back; //
	JButton create;
	JButton create2;
	JButton write;
	JButton answer;
	JButton delete;

	public QnAUI(int id_type, Client client) throws ClassNotFoundException {
		setLayout(null);
		QnAList.readQnaList(client);
		
		DefaultListModel dm = new DefaultListModel();
		
		for(Qna list1 : QnAList.getQnaList())
		{
			if(id_type == -1 || Integer.toString(id_type).equals(list1.getQuestioner())) {
				String ttemp = list1.getQuestioner() + " " + list1.getQuestionName();
				
				if(list1.getState() == 0)
					ttemp = ttemp + " �亯 ������";
				else
					ttemp = ttemp + " �亯 �Ϸ�";
				
				dm.addElement(ttemp);
			}
		}
		
		JList<String> list2 = new JList<String>();
		list2.setModel(dm);
		JScrollPane ScrollList2 = new JScrollPane(list2);
		ScrollList2.setBounds(40, 45, 300, 150);
		add(ScrollList2);
		
		DefaultListModel dm2 = new DefaultListModel();
		
        JLabel faqTitle = new JLabel("<FAQ �Խ���>");
        faqTitle.setBounds(40, 210, 95, 15);
        add(faqTitle);
		
		QnAList.readFaqList(client);
		
		for(Faq list1 : QnAList.getFaqList())
		{
			String ttemp = "< Q " + list1.getFaqNum() + " > " + list1.getQuestionName();
			
			dm2.addElement(ttemp);
		}
			      
		JList<String> list3 = new JList<String>();
		list3.setModel(dm2);
		JScrollPane ScrollList3 = new JScrollPane(list3);
		ScrollList3.setBounds(40, 230, 300, 150);
		add(ScrollList3);
		
		JPanel createButtonDisplay = new JPanel(new BorderLayout());
		createButtonDisplay.setBounds(365, 48, 85, 30);
		
		add(createButtonDisplay, BorderLayout.EAST);
		
		create = new JButton("Create");
		
		JPanel createButton2Display = new JPanel(new BorderLayout());
		createButton2Display.setBounds(365, 233, 85, 30);
		
		add(createButton2Display, BorderLayout.EAST);
		
		create2 = new JButton("Create");
		
		ActionListener createButtonPressHandler = new ActionListener()
		{
	        JTextField titleFormat = new JTextField();
	        JTextArea contextFormat = new JTextArea(8, 40);
			JPanel createDisplay = new JPanel(new BorderLayout());
			
			public void actionPerformed(ActionEvent e)
			{				
				removeAll();
				
				previousButtons.push(createButtonDisplay);
				remove(createButtonDisplay);				
				previousButtons.push(createButton2Display);
				remove(createButton2Display);
				
				remove(ScrollList2);
				previousPanels.push(list2);
				remove(ScrollList3);
				previousPanels.push(list3);
				
				remove(faqTitle);
				
		        JLabel qnaTitle = new JLabel("<QnA �Խ���>");
		        qnaTitle.setBounds(40, 25, 95, 15);
		        add(qnaTitle);
				
				createDisplay.setBounds(40, 45, 300, 250);
				createDisplay.setBackground(Color.WHITE);
				add(createDisplay);
				createDisplay.setLayout(null);
		        
		        JLabel title = new JLabel("<�� ����>");
		        title.setBounds(5, 5, 65, 15);
		        createDisplay.add(title);
				
	            titleFormat.setBounds(5, 25, 265, 20);
	            
	            JLabel context = new JLabel("<�� ����>");
		        context.setBounds(5, 60, 65, 15);
		        createDisplay.add(context);
		        
	            contextFormat.setBounds(5, 80, 265, 165);
		        
	            createDisplay.add(titleFormat);
	            createDisplay.add(contextFormat);
				
				JPanel writeButtonDisplay = new JPanel(new BorderLayout());
				writeButtonDisplay.setBounds(365, 48, 85, 30);
				add(writeButtonDisplay, BorderLayout.EAST);
				
				write = new JButton("Write");
				write.addActionListener(writeButtonPressHandler);
				writeButtonDisplay.add(write);
			
				JPanel backButtonDisplay = new JPanel(new BorderLayout());
				backButtonDisplay.setBounds(365, 85, 85, 30);
				add(backButtonDisplay, BorderLayout.EAST);
				
				back = new JButton("Back");
				back.addActionListener(backButtonPressHandler);
				backButtonDisplay.add(back);
				
				revalidate();
				repaint();
			}
			
			ActionListener writeButtonPressHandler = new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					String[] buttons = {"�ۼ�", "���"};
					int result = 0;
					result = JOptionPane.showOptionDialog(null, "�ۼ��Ͻðڽ��ϱ�?", "QnA ���", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, buttons, "���");
					if(result == 0) {
						String titleTemp = titleFormat.getText();
						System.out.println(titleTemp);
						String contextTemp = contextFormat.getText();
						System.out.println(contextTemp);
						
						try
						{
							QnAList.createQna(Integer.toString(id_type), titleTemp, contextTemp);
							
							QnAList.saveQnaList(client);
						} catch (IOException e1)
						{
							e1.printStackTrace();
						}					
						
						Qna q = QnAList.getQnaList().get(QnAList.getQnaList().size() - 1);
						
						String ttemp = q.getQuestioner() + " " + q.getQuestionName();
						
						if(q.getState() == 0)
							ttemp = ttemp + " �亯 ������";
						else
							ttemp = ttemp + " �亯 �Ϸ�";
						
						dm.addElement(ttemp);
						
						removeAll(); 
						
				        previousListTemp = previousPanels.pop();
				        previousListTemp.setBounds(40, 230, 300, 150);
				        add(previousListTemp);	
				        
						previousListTemp = previousPanels.pop();									
						previousListTemp.setBounds(40, 45, 300, 150);
						add(previousListTemp);
						
						previousPanelTemp = previousButtons.pop();
						previousPanelTemp.setBounds(365, 233, 85, 30);
						add(previousPanelTemp, BorderLayout.EAST);
						
						previousPanelTemp = previousButtons.pop();
						previousPanelTemp.setBounds(365, 48, 85, 30);
						add(previousPanelTemp, BorderLayout.EAST);
						
				        JLabel qnaTitle = new JLabel("<QnA �Խ���>");
				        qnaTitle.setBounds(40, 25, 95, 15);
				        add(qnaTitle);
				        
						revalidate();
						repaint();
						JOptionPane.showMessageDialog(null, "�ۼ��Ǿ����ϴ�", "�˸�", JOptionPane.PLAIN_MESSAGE);
					}
				}
			};
			
			ActionListener backButtonPressHandler = new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					if(e.getSource() == back)
					{
						String[] buttons = {"���ư���", "���"};
						int result = 0;
						result = JOptionPane.showOptionDialog(null, "�ۼ��� ���߰� ���� �޴��� ���ư��ðڽ��ϱ�?", "QnA ���", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, buttons, "���");
						if(result == 0) {
							removeAll();
							
					        JLabel qnaTitle = new JLabel("<QnA �Խ���>");
					        qnaTitle.setBounds(40, 25, 95, 15);
					        add(qnaTitle);
					        
					        JLabel faqTitle = new JLabel("<FAQ �Խ���>");
					        faqTitle.setBounds(40, 210, 95, 15);
					        add(faqTitle);
					        
					        previousListTemp = previousPanels.pop();
					        previousListTemp.setBounds(40, 230, 300, 150);
					        add(previousListTemp);			        
					        
							previousListTemp = previousPanels.pop();
							previousListTemp.setBounds(40, 45, 300, 150);
							add(previousListTemp);
							
							previousPanelTemp = previousButtons.pop();
							previousPanelTemp.setBounds(365, 233, 85, 30);
							add(previousPanelTemp, BorderLayout.EAST);
							
							previousPanelTemp = previousButtons.pop();
							previousPanelTemp.setBounds(365, 48, 85, 30);
							add(previousPanelTemp, BorderLayout.EAST);
							
							revalidate();
							repaint();
						}
					}
				}
			};
		};
		
		ActionListener createButton2PressHandler = new ActionListener()
		{
	        JTextField title2Format = new JTextField();
	        JTextArea context2Format = new JTextArea(8, 40);
	        JTextArea context3Format = new JTextArea(8, 40);
			JPanel create2Display = new JPanel(new BorderLayout());
			
			public void actionPerformed(ActionEvent e)
			{			
				//FAQ �ۼ��ϱ�
				
				removeAll();
				
				previousButtons.push(createButtonDisplay);
				remove(createButtonDisplay);				
				previousButtons.push(createButton2Display);
				remove(createButton2Display);
				
				remove(ScrollList2);
				previousPanels.push(list2);
				remove(ScrollList3);
				previousPanels.push(list3);
				
				//remove(qnaTitle);
				
		        JLabel faqTitle = new JLabel("<FAQ �Խ���>");
		        faqTitle.setBounds(40, 25, 95, 15);
		        add(faqTitle);
				
				create2Display.setBounds(40, 45, 300, 350);
				create2Display.setBackground(Color.WHITE);
				add(create2Display);
				create2Display.setLayout(null);
				
		        JLabel title = new JLabel("<�� ����>");
		        title.setBounds(5, 5, 65, 15);
		        create2Display.add(title);
				
	            title2Format.setBounds(5, 25, 265, 20);
	            
	            JLabel context = new JLabel("<�� ����>");
		        context.setBounds(5, 60, 65, 15);
		        create2Display.add(context);
		        
	            context2Format.setBounds(5, 80, 265, 125);
	            
	            JLabel answer = new JLabel("<�� �亯>");
		        answer.setBounds(5, 220, 65, 15);
		        create2Display.add(answer);
		        
		        context3Format.setBounds(5, 240, 265, 135);
		        
	            create2Display.add(title2Format);
	            create2Display.add(context2Format);
	            create2Display.add(context3Format);
	            
				JPanel writeButtonDisplay = new JPanel(new BorderLayout());
				writeButtonDisplay.setBounds(365, 48, 85, 30);
				add(writeButtonDisplay, BorderLayout.EAST);
				
				write = new JButton("Write");
				write.addActionListener(writeButton2PressHandler);
				writeButtonDisplay.add(write);
			
				JPanel backButtonDisplay = new JPanel(new BorderLayout());
				backButtonDisplay.setBounds(365, 85, 85, 30);
				add(backButtonDisplay, BorderLayout.EAST);
				
				back = new JButton("Back");
				back.addActionListener(backButton2PressHandler);
				backButtonDisplay.add(back);
		        
				revalidate();
				repaint();
			}
			
			ActionListener writeButton2PressHandler = new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					String[] buttons = {"���", "���"};
					int result = 0;
					result = JOptionPane.showOptionDialog(null, "�ۼ��� FAQ�� ����Ͻðڽ��ϱ�?", "FAQ �ۼ�", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, buttons, "���");
					
					if(result == 0) {
						String titleTemp = title2Format.getText();
						//System.out.println(titleTemp);
						String contextTemp = context2Format.getText();
						//System.out.println(contextTemp);
						String context2Temp = context3Format.getText();
						//System.out.println(context2Temp);
						
						try
						{
							QnAList.createFaq(titleTemp, contextTemp, context2Temp);
							
							QnAList.saveFaqList(client);
						} catch (IOException e1)
						{
							e1.printStackTrace();
						}					
						
						Faq q = QnAList.getFaqList().get(QnAList.getFaqList().size() - 1);
						
						String ttemp = "< Q " + q.getFaqNum() + " > " + q.getQuestionName();
						
						dm2.addElement(ttemp);
						
						removeAll(); 
						
				        previousListTemp = previousPanels.pop();
				        previousListTemp.setBounds(40, 230, 300, 150);
				        add(previousListTemp);	
				        
						previousListTemp = previousPanels.pop();									
						previousListTemp.setBounds(40, 45, 300, 150);
						add(previousListTemp);
						
						previousPanelTemp = previousButtons.pop();
						previousPanelTemp.setBounds(365, 233, 85, 30);
						add(previousPanelTemp, BorderLayout.EAST);
						
						previousPanelTemp = previousButtons.pop();
						previousPanelTemp.setBounds(365, 48, 85, 30);
						add(previousPanelTemp, BorderLayout.EAST);
						
				        JLabel qnaTitle = new JLabel("<QnA �Խ���>");
				        qnaTitle.setBounds(40, 25, 95, 15);
				        add(qnaTitle);
				        
				        JLabel faqTitle = new JLabel("<FAQ �Խ���>");
				        faqTitle.setBounds(40, 210, 95, 15);
				        add(faqTitle);
				        
						revalidate();
						repaint();
					}
				}
			};
			
			ActionListener backButton2PressHandler = new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					if(e.getSource() == back)
					{
						String[] buttons = {"���ư���", "���"};
						int result = 0;
						result = JOptionPane.showOptionDialog(null, "�ۼ��� ���߰� ���� �޴��� ���ư��ðڽ��ϱ�?", "FAQ �ۼ�", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, buttons, "���");
						if(result == 0) {
							removeAll();
							
					        JLabel qnaTitle = new JLabel("<QnA �Խ���>");
					        qnaTitle.setBounds(40, 25, 95, 15);
					        add(qnaTitle);
					        
					        JLabel faqTitle = new JLabel("<FAQ �Խ���>");
					        faqTitle.setBounds(40, 210, 95, 15);
					        add(faqTitle);
					        
					        previousListTemp = previousPanels.pop();
					        previousListTemp.setBounds(40, 230, 300, 150);
					        add(previousListTemp);			        
					        
							previousListTemp = previousPanels.pop();
							previousListTemp.setBounds(40, 45, 300, 150);
							add(previousListTemp);
							
							previousPanelTemp = previousButtons.pop();
							previousPanelTemp.setBounds(365, 233, 85, 30);
							add(previousPanelTemp, BorderLayout.EAST);
							
							previousPanelTemp = previousButtons.pop();
							previousPanelTemp.setBounds(365, 48, 85, 30);
							add(previousPanelTemp, BorderLayout.EAST);
							
							revalidate();
							repaint();
						}
					}
				}
			};
		};
				
		create.addActionListener(createButtonPressHandler);
		createButtonDisplay.add(create);
		if(id_type < 0)
			create.setVisible(false);
		
		create2.addActionListener(createButton2PressHandler);
		createButton2Display.add(create2);
		if(id_type != -1)
			create2.setVisible(false);
		
		
		MouseListener mouseListener = new MouseAdapter()
		{
			JPanel answerButtonDisplay = new JPanel(new BorderLayout());
			JPanel backButtonDisplay = new JPanel(new BorderLayout());
			Qna temp;
			int updateIndex;
			int count;
			int deleteIndex = 0;
			
			public void mouseClicked(MouseEvent e)
			{
				JList theList = (JList) e.getSource();
				
				int index = theList.locationToIndex(e.getPoint());
				
				if(index >= 0)
				{
					//QnA�����ؼ� ����
					deleteIndex = index;
					System.out.print(deleteIndex);
					
					removeAll();
					
					previousButtons.push(createButtonDisplay);
					remove(createButtonDisplay);					
					previousButtons.push(createButton2Display);
					remove(createButton2Display);
						
					previousPanels.push(list2);
					remove(ScrollList2);
					previousPanels.push(list3);
					remove(ScrollList3);
					
					count = 0;
					for(Qna one: QnAList.getQnaList())
					{
						if(id_type == -1 || Integer.toString(id_type).equals(one.getQuestioner())) {
							if(count == index) {
								temp = one;
								break;
							}
							else {
								count++;
							}
						}
					}
					
			        JLabel qnaTitle = new JLabel(temp.getQuestionName());
			        qnaTitle.setBounds(40, 25, 205, 15);
			        add(qnaTitle);
					
					JPanel qnaDisplay = new JPanel(new BorderLayout());
					qnaDisplay.setLayout(null);
					qnaDisplay.setBounds(40, 45, 300, 250);
					qnaDisplay.setBackground(Color.WHITE);
					add(qnaDisplay);
					
					JLabel qnaContext = new JLabel();
					
					String ques = temp.getQuestion();
				    
					qnaContext.setText("<html>" + ques.replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br/>") + "</html>");

					qnaContext.setBounds(10, 5, 265, 120);
					qnaDisplay.add(qnaContext);
					
					JLabel qnaAnswerTitle = new JLabel("<�亯>");
					qnaAnswerTitle.setBounds(10, 140, 70, 15);
					qnaDisplay.add(qnaAnswerTitle);
					
					if(temp.getState() == 1)
					{
						JLabel qnaAnswer = new JLabel(temp.getAnswer());
						
						ques = temp.getAnswer();
						
						qnaAnswer.setText("<html>" + ques.replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br/>") + "</html>");
						
						qnaAnswer.setBounds(10, 130, 260, 100);
						qnaDisplay.add(qnaAnswer);
					}
					else
					{
						JLabel qnaAnswer = new JLabel("�亯�� �����ϴ�.");
						qnaAnswer.setBounds(10, 130, 260, 100);
						qnaDisplay.add(qnaAnswer);
					}
					
					JPanel answerButtonDisplay = new JPanel(new BorderLayout());
					answerButtonDisplay.setBounds(365, 48, 85, 30);
					add(answerButtonDisplay, BorderLayout.EAST);
					
					answer = new JButton("Answer");
					answer.addActionListener(answerButtonPressHandler);
					answerButtonDisplay.add(answer);
					
					if(id_type != -1)
						answer.setVisible(false);
					
					JPanel deleteButtonDisplay = new JPanel(new BorderLayout());
					deleteButtonDisplay.setBounds(365, 85, 85, 30);
					add(deleteButtonDisplay, BorderLayout.EAST);
					
					delete = new JButton("Delete");
					delete.addActionListener(deleteButtonPressHandler);
					deleteButtonDisplay.add(delete);
					
					JPanel backButtonDisplay = new JPanel(new BorderLayout());
					backButtonDisplay.setBounds(365, 122, 85, 30);
					add(backButtonDisplay, BorderLayout.EAST);
					
					back = new JButton("Back");
					back.addActionListener(backButtonPressHandler);
					backButtonDisplay.add(back);					
					
					revalidate();
					repaint();
				}
			}
			
			ActionListener deleteButtonPressHandler = new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					if(e.getSource() == delete)
					{
						//QnA ����Ʈ ����
						String[] buttons = {"����", "���"};
						int result = 0;
						result = JOptionPane.showOptionDialog(null, "�ش� QnA�� �����Ͻðڽ��ϱ�?", "QnA ����", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, buttons, "���");
						if(result == 0) {
							QnAList.deleteQna(temp);
							QnAList.saveQnaList(client);
							dm.removeElementAt(deleteIndex);
							
							removeAll();
							
					        JLabel qnaTitle = new JLabel("<QnA �Խ���>");
					        qnaTitle.setBounds(40, 25, 95, 15);
					        add(qnaTitle);
					        
					        JLabel faqTitle = new JLabel("<FAQ �Խ���>");
					        faqTitle.setBounds(40, 210, 95, 15);
					        add(faqTitle);
					        
					        previousListTemp = previousPanels.pop();
					        previousListTemp.setBounds(40, 230, 300, 150);
					        add(previousListTemp);	
					        
							previousListTemp = previousPanels.pop();
							previousListTemp.setBounds(40, 45, 300, 150);
							add(previousListTemp);
							
							previousPanelTemp = previousButtons.pop();
							previousPanelTemp.setBounds(365, 233, 85, 30);
							add(previousPanelTemp);
							
							previousPanelTemp = previousButtons.pop();
							previousPanelTemp.setBounds(365, 48, 85, 30);
							add(previousPanelTemp);
							
							revalidate();
							repaint();
						}
					}
				}
			};
			
			ActionListener backButtonPressHandler = new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					if(e.getSource() == back)
					{
						//QnA �� ���� ���ư���
						
						removeAll();
						
				        JLabel qnaTitle = new JLabel("<QnA �Խ���>");
				        qnaTitle.setBounds(40, 25, 95, 15);
				        add(qnaTitle);
				        
				        JLabel faqTitle = new JLabel("<FAQ �Խ���>");
				        faqTitle.setBounds(40, 210, 95, 15);
				        add(faqTitle);
				        
				        previousListTemp = previousPanels.pop();
				        previousListTemp.setBounds(40, 230, 300, 150);
				        add(previousListTemp);	
				        
						previousListTemp = previousPanels.pop();
						previousListTemp.setBounds(40, 45, 300, 150);
						add(previousListTemp);
						
						previousPanelTemp = previousButtons.pop();
						previousPanelTemp.setBounds(365, 233, 85, 30);
						add(previousPanelTemp);
						
						previousPanelTemp = previousButtons.pop();
						previousPanelTemp.setBounds(365, 48, 85, 30);
						add(previousPanelTemp);
						
						revalidate();
						repaint();
					}
				}
			};
			
			ActionListener answerButtonPressHandler = new ActionListener()
			{
		        JTextArea contextFormat = new JTextArea(8, 40);
				JPanel createDisplay = new JPanel(new BorderLayout());
				
				public void actionPerformed(ActionEvent e)
				{
					if(temp.getState() == 0)
					{
						//QnA �ۼ���ư ������
						
						removeAll();
					
						remove(answerButtonDisplay);
						
						remove(backButtonDisplay);
						
						remove(ScrollList2);
						
						createDisplay.setBounds(40, 45, 300, 250);
						createDisplay.setBackground(Color.WHITE);
						add(createDisplay);
						createDisplay.setLayout(null);
				        
				        JLabel title = new JLabel("<" + temp.getQuestionName() + "�� ���� �亯>");
				        title.setBounds(40, 25, 155, 15);
				        add(title);
			            
			            JLabel context = new JLabel("<�� ����>");
				        context.setBounds(10, 20, 65, 15);
				        createDisplay.add(context);
				        
			            contextFormat.setBounds(10, 40, 265, 165);
				        
			            createDisplay.add(contextFormat);
						
						JPanel writeButtonDisplay = new JPanel(new BorderLayout());
						writeButtonDisplay.setBounds(365, 48, 85, 30);
						add(writeButtonDisplay, BorderLayout.EAST);
						
						write = new JButton("Write");
						write.addActionListener(writeButtonPressHandler);
						writeButtonDisplay.add(write);
					
						JPanel answerBackButtonDisplay = new JPanel(new BorderLayout());
						answerBackButtonDisplay.setBounds(365, 85, 85, 30);
						add(answerBackButtonDisplay, BorderLayout.EAST);
						
						back = new JButton("Back");
						back.addActionListener(answerBackButtonPressHandler);
						answerBackButtonDisplay.add(back);
						
						revalidate();
						repaint();
					}
					else
						 JOptionPane.showMessageDialog(null, "�̹� �亯�� �ۼ��� �����Դϴ�.", "�˸�", JOptionPane.WARNING_MESSAGE);
				}
				
				ActionListener writeButtonPressHandler = new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{		
						String[] buttons = {"���", "���"};
						int result = 0;
						result = JOptionPane.showOptionDialog(null, "�ۼ��� �亯�� ����Ͻðڽ��ϱ�?", "QnA �亯���", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, buttons, "���");
						if(result == 0) {
							String contextTemp = contextFormat.getText();
							System.out.println(contextTemp);
							
							temp.getAnswer(contextTemp);
							
							QnAList.saveQnaList(client);	
							
							updateIndex = list2.getSelectedIndex();
							
							Qna q = QnAList.getQnaList().get(updateIndex);
							
							String ttemp = q.getQuestioner() + " " + q.getQuestionName();
							
							ttemp = ttemp + " �亯 �Ϸ�";
							
							dm.add(updateIndex, ttemp);
							dm.remove(updateIndex+1);
							
							removeAll(); 
							
					        JLabel qnaTitle = new JLabel("<QnA �Խ���>");
					        qnaTitle.setBounds(40, 25, 95, 15);
					        add(qnaTitle);
					        
					        JLabel faqTitle = new JLabel("<FAQ �Խ���>");
					        faqTitle.setBounds(40, 210, 95, 15);
					        add(faqTitle);
					        
					        previousListTemp = previousPanels.pop();
					        previousListTemp.setBounds(40, 230, 300, 150);
					        add(previousListTemp);
							
							previousListTemp = previousPanels.pop();										
							previousListTemp.setBounds(40, 45, 300, 150);
							add(previousListTemp);
							
							previousPanelTemp = previousButtons.pop();
							previousPanelTemp.setBounds(365, 233, 85, 30);
							add(previousPanelTemp, BorderLayout.EAST);
						
							previousPanelTemp = previousButtons.pop();
							previousPanelTemp.setBounds(365, 48, 85, 30);
							add(previousPanelTemp, BorderLayout.EAST);
							
							revalidate();
							repaint();
						}
					}
				};
				
				ActionListener answerBackButtonPressHandler = new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						if(e.getSource() == back)
						{
							String[] buttons = {"���ư���", "���"};
							int result = 0;
							result = JOptionPane.showOptionDialog(null, "�ۼ��� ���߰� ���� �޴��� ���ư��ðڽ��ϱ�?", "QnA �亯 ���", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, buttons, "���");
							if(result == 0) {
								removeAll();
								
						        JLabel qnaTitle = new JLabel("<QnA �Խ���>");
						        qnaTitle.setBounds(40, 25, 95, 15);
						        add(qnaTitle);
						        
						        JLabel faqTitle = new JLabel("<FAQ �Խ���>");
						        faqTitle.setBounds(40, 210, 95, 15);
						        add(faqTitle);
						        
						        previousListTemp = previousPanels.pop();
						        previousListTemp.setBounds(40, 230, 300, 150);
						        add(previousListTemp);
						        
						        previousListTemp = previousPanels.pop();
						        previousListTemp.setBounds(40, 45, 300, 150);
						        add(previousListTemp);	 
								
								previousPanelTemp = previousButtons.pop();
								previousPanelTemp.setBounds(365, 233, 85, 30);
								add(previousPanelTemp, BorderLayout.EAST);
								
								previousPanelTemp = previousButtons.pop();
								previousPanelTemp.setBounds(365, 48, 85, 30);
								add(previousPanelTemp, BorderLayout.EAST);
								
								revalidate();
								repaint();
							}
						}
					}
				};
			};
		};
		
		MouseListener mouseListener2 = new MouseAdapter()
		{
			JPanel backButtonDisplay = new JPanel(new BorderLayout());
			Faq temp;
			int updateIndex;
			
			public void mouseClicked(MouseEvent e)
			{
				JList theList = (JList) e.getSource();
				
				int index = theList.locationToIndex(e.getPoint());
				
				if(index >= 0)
				{
					//FAQ �����ؼ� ����
					
					removeAll();
					
					previousButtons.push(createButtonDisplay);
					remove(createButtonDisplay);					
					previousButtons.push(createButton2Display);
					remove(createButton2Display);
						
					Object o = theList.getModel().getElementAt(index);
					String[] q = o.toString().split(" ");
					
					previousPanels.push(list2);
					remove(ScrollList2);
					previousPanels.push(list3);
					remove(ScrollList3);
					
					temp = QnAList.getFaqList().get(Integer.parseInt(q[2]) - 1);
					
			        JLabel faqTitle = new JLabel(temp.getQuestionName());
			        faqTitle.setBounds(40, 25, 205, 15);
			        add(faqTitle);
					
					JPanel faqDisplay = new JPanel(new BorderLayout());
					faqDisplay.setLayout(null);
					faqDisplay.setBounds(40, 45, 300, 250);
					faqDisplay.setBackground(Color.WHITE);
					add(faqDisplay);
					
					JLabel faqContext = new JLabel();
					
					String ques = temp.getQuestion();
				    
					faqContext.setText("<html>" + ques.replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br/>") + "</html>");

					faqContext.setBounds(10, 15, 265, 100);
					faqDisplay.add(faqContext);
					
					JLabel faqAnswerTitle = new JLabel("<�亯>");
					faqAnswerTitle.setBounds(10, 120, 70, 15);
					faqDisplay.add(faqAnswerTitle);
					
					JLabel faqAnswer = new JLabel();
					
					ques = temp.getAnswer();
					
					faqAnswer.setText("<html>" + ques.replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br/>") + "</html>");
					
					faqAnswer.setBounds(10, 130, 260, 100);
					faqDisplay.add(faqAnswer);
						
					JPanel backButtonDisplay = new JPanel(new BorderLayout());
					backButtonDisplay.setBounds(365, 48, 85, 30);
					add(backButtonDisplay, BorderLayout.EAST);
					
					back = new JButton("Back");
					back.addActionListener(backButtonPressHandler);
					backButtonDisplay.add(back);					
					
					revalidate();
					repaint();
				}
			}
			
			ActionListener backButtonPressHandler = new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					if(e.getSource() == back)
					{
						//FAQ ���� ���ư���
						
						removeAll();
						
				        JLabel qnaTitle = new JLabel("<QnA �Խ���>");
				        qnaTitle.setBounds(40, 25, 95, 15);
				        add(qnaTitle);
				        
				        JLabel faqTitle = new JLabel("<FAQ �Խ���>");
				        faqTitle.setBounds(40, 210, 95, 15);
				        add(faqTitle);
				        
				        previousListTemp = previousPanels.pop();
				        previousListTemp.setBounds(40, 230, 300, 150);
				        add(previousListTemp);	
				        
						previousListTemp = previousPanels.pop();
						previousListTemp.setBounds(40, 45, 300, 150);
						add(previousListTemp);
						
						previousPanelTemp = previousButtons.pop();
						previousPanelTemp.setBounds(365, 233, 85, 30);
						add(previousPanelTemp, BorderLayout.EAST);
						
						previousPanelTemp = previousButtons.pop();
						previousPanelTemp.setBounds(365, 48, 85, 30);
						add(previousPanelTemp, BorderLayout.EAST);
						
						revalidate();
						repaint();
					}
				}
			};
		};

		list2.addMouseListener(mouseListener);
		list3.addMouseListener(mouseListener2);
	}
}
