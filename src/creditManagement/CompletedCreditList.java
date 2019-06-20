package creditManagement;

import java.util.*;

import client.Client;

public class CompletedCreditList {
	private static CompletedCreditList listInstance = null;
	private static ArrayList<CompletedCredit> creditList;
	
	public final static String noList = "������ �����ϴ�";
	
	private CompletedCreditList() {} // singleton���� �����ϱ� ���� �����ڸ� private��
	
	public static CompletedCreditList getCompletedCreditList(Client client) throws ClassNotFoundException {
		if(listInstance == null) {
			listInstance = new CompletedCreditList();
			readCreditList(client);
		}
		return listInstance;
	}
	public ArrayList<CompletedCredit> get_instance_list(){
		return creditList;
	}
	
	public static void readCreditList(Client client)
	{
		creditList = (ArrayList<CompletedCredit>)client.getObject("CreditList");
		if(creditList == null) {
			creditList = new ArrayList<>();
		}
	}
	
	public static void saveCreditList(Client client)
	{
		client.setObject("CreditList", creditList);
	}
		
	//instance initial�뵵
	public boolean completedCreditListAppend(Client client, CompletedCredit input) throws ClassNotFoundException {
		
		//������ ����� ����Ʈ �ҷ���
		readCreditList(client);
		try{
			// �ߺ��˻� - �� �л��� ������ �Ϳ����� �й�, �г⵵, �б�, ������ �ߺ��� �� �� ���⿡ �̰����� �ߺ��˻� (object���� �񱳰� �ȸ����� �Ф�)

			int studentID = input.getStudentID();
			int year = input.getYear();
			int semester = input.getSemester();
			String univ = input.getUniv();
			String course = input.getCourse();
			boolean overlap = false;
			for(CompletedCredit one : creditList) {
				if(one.getStudentID() == studentID && one.getUniv().equals(univ) && one.getYear() == year && one.getSemester() == semester && one.getCourse().equals(course)) {
					overlap = true;
				}
			}
			// �ߺ� �ƴ� ��쿡�� add
			if(!overlap) {
				creditList.add(creditList.size(), input);
				saveCreditList(client);
			}
			
			return true;
		}
		catch(Exception e) {
			return false; // fail
		}
		
	}
	
	public boolean completedCreditListModify(Client client, CompletedCredit input) throws ClassNotFoundException {
		boolean ischanged = false;
		try{
			// ������ ������ �����ϱ� ���� ����Ʈ�� �ν��Ͻ� ���� �ٲ��� ���� �����ͷ� ���� �� ã��. �ε��� ���⿡�� �� ��������.
			int studentID = input.getStudentID();
			int year = input.getYear();
			int semester = input.getSemester();
			String course = input.getCourse();
			String univ = input.getUniv();
			int i = 0;
			for(CompletedCredit one : creditList) {
				if(one.getStudentID() == studentID && one.getUniv().equals(univ) && one.getYear() == year && one.getSemester() == semester && one.getCourse().equals(course)) {
					one = input;
					ischanged = true;
					// �ٲٰ� ������Ʈ
					saveCreditList(client);
				}
			}
			
		}
		catch(Exception e) {}
		return ischanged;
	}
	
	
	public int countStudnetTermCredit(int studentID, int year, int semester) {
		int count = 0;
		for(CompletedCredit one : creditList) {
			if(one.getStudentID() == studentID && one.getYear() == year && one.getSemester() == semester) {
				count ++;
			}
		}
		return count;
	}
	public int countStudentTermCreditIsapped(int studentID, int year, int semester) {
		int count = 0;
		for(CompletedCredit one : creditList) {
			if(one.getStudentID() == studentID && one.getYear() == year && one.getSemester() == semester && one.isApplicationState()) {
				count ++;
			}
		}
		return count;
	}
	
	public String[] semesterList(int studentID) {
		int count = 0;
		String[] SemeList = {noList};
		if(creditList == null) {
			return null;
		}
		else {
			int[][] temp = new int[creditList.size()][2];
			
			for(int i = 0; i < creditList.size(); i++) {
				if(creditList.get(i).getStudentID() == studentID) {
					boolean judge = true;
					for(int j = 0; j < creditList.size(); j++) {
						if(temp[j][0] == creditList.get(i).getYear() && temp[j][1] == creditList.get(i).getSemester()) {
							judge = false;
						}
					}
					if(judge) {
						temp[count][0] = creditList.get(i).getYear();
						temp[count][1] = creditList.get(i).getSemester();
						count++;
					}
				}
			}
			
			if(count > 0) {
				SemeList = new String[count];
				for(int i = 0; i < count; i++) {
					SemeList[i] = String.format("%4s��%2s�б�", temp[i][0], temp[i][1]);
				}
			}
			return SemeList;
		}
	}
	
	public String[] semesterListIsapped(int studentID) {
		int count = 0;
		String[] semeListiIsapped = {noList};
		if(creditList == null) {
			return null;
		}
		int[][] temp = new int[creditList.size()][2];
		
		for(int i = 0; i < creditList.size(); i++) {
			if(creditList.get(i).getStudentID() == studentID && creditList.get(i).isApplicationState()) {
				boolean judge = true;
				for(int j = 0; j < creditList.size(); j++) {
					if(temp[j][0] == creditList.get(i).getYear() && temp[j][1] == creditList.get(i).getSemester()) {
						judge = false;
					}
				}
				if(judge) {
					temp[count][0] = creditList.get(i).getYear();
					temp[count][1] = creditList.get(i).getSemester();
					count++;
				}
			}
		}
		
		if(count > 0) {
			semeListiIsapped = new String[count];
			for(int i = 0; i < count; i++) {
				semeListiIsapped[i] = String.format("%4s��%2s�б�", temp[i][0], temp[i][1]);
			}
		}
		return semeListiIsapped;
	}
	
	public ArrayList<CompletedCredit> completedCreditListPrint(int studentID, int year, int semester) { // �б⺰ �̼��� ���� ��� ���
		int count = countStudnetTermCredit(studentID, year, semester);
		ArrayList<CompletedCredit> CCLP = new ArrayList<>();
		
		if(count > 0) {
			for(CompletedCredit one : creditList) {
				if(one.getStudentID() == studentID && one.getYear() == year && one.getSemester() == semester) {
					CCLP.add(one);
				}
			}
		}
		return CCLP;
	}
	
	public ArrayList<CompletedCredit> applicatedCreditListPrint(int studentID, int year, int semester) { // �̼��� ���� ��� �� ��û�� ���� ��� ���
		int count = countStudentTermCreditIsapped(studentID, year, semester);
		ArrayList<CompletedCredit> ACLP = new ArrayList<>();
		
		if(count > 0) {
			for(CompletedCredit one : creditList) {
				if(one.getStudentID() == studentID && one.getYear() == year && one.getSemester() == semester && one.isApplicationState()) {
					ACLP.add(one);
				}
			}
		}
		return ACLP;
	}
	
	public static String getNoList() {
		return noList;
	}
}
