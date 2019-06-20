package creditManagement;

import java.io.*;

import java.util.*;

import client.Client;
import question.Qna;

 
public class CompletedCreditList {
	private static CompletedCreditList ListInstance = null;
	private static ArrayList<CompletedCredit> CreditList;
	
	public final static String NO_LIST = "������ �����ϴ�";
	
	private CompletedCreditList() {} // singleton���� �����ϱ� ���� �����ڸ� private��
	
	public static CompletedCreditList get_completed_credit_list(Client client) throws ClassNotFoundException {
		if(ListInstance == null) {
			ListInstance = new CompletedCreditList();
			readCreditList(client);
		}
		return ListInstance;
	}
	public ArrayList<CompletedCredit> get_instance_list(){
		return CreditList;
	}
	
	public static void readCreditList(Client client)
	{
		CreditList = (ArrayList<CompletedCredit>)client.getObject("CreditList");
		if(CreditList == null) {
			CreditList = new ArrayList<>();
		}
	}
	
	public static void saveCreditList(Client client)
	{
		client.setObject("CreditList", CreditList);
	}
		
	//instance initial�뵵
	public boolean completed_credit_list_append(Client client, CompletedCredit input) throws ClassNotFoundException {
		
		//������ ����� ����Ʈ �ҷ���
		readCreditList(client);
		try{
			// �ߺ��˻� - �� �л��� ������ �Ϳ����� �й�, �г⵵, �б�, ������ �ߺ��� �� �� ���⿡ �̰����� �ߺ��˻� (object���� �񱳰� �ȸ����� �Ф�)

			int st_id = input.getStudentID();
			int year = input.getYear();
			int semester = input.getSemester();
			String univ = input.getUniv();
			String course = input.getCourse();
			boolean overlap = false;
			for(CompletedCredit one : CreditList) {
				if(one.getStudentID() == st_id && one.getUniv().equals(univ) && one.getYear() == year && one.getSemester() == semester && one.getCourse().equals(course)) {
					overlap = true;
				}
			}
			// �ߺ� �ƴ� ��쿡�� add
			if(!overlap) {
				CreditList.add(CreditList.size(), input);
				saveCreditList(client);
			}
			
			return true;
		}
		catch(Exception e) {
			return false; // fail
		}
		
	}
	
	public boolean completed_credit_list_modify(Client client, CompletedCredit input) throws ClassNotFoundException {
		boolean ischanged = false;
		try{
			// ������ ������ �����ϱ� ���� ����Ʈ�� �ν��Ͻ� ���� �ٲ��� ���� �����ͷ� ���� �� ã��. �ε��� ���⿡�� �� ��������.
			int st_id = input.getStudentID();
			int year = input.getYear();
			int semester = input.getSemester();
			String course = input.getCourse();
			String univ = input.getUniv();
			int i = 0;
			for(CompletedCredit one : CreditList) {
				if(one.getStudentID() == st_id && one.getUniv().equals(univ) && one.getYear() == year && one.getSemester() == semester && one.getCourse().equals(course)) {
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
	
	
	public int count_std_term_credit(int st_id, int year, int semester) {
		int count = 0;
		for(CompletedCredit one : CreditList) {
			if(one.getStudentID() == st_id && one.getYear() == year && one.getSemester() == semester) {
				count ++;
			}
		}
		return count;
	}
	public int count_std_term_credit_isapped(int st_id, int year, int semester) {
		int count = 0;
		for(CompletedCredit one : CreditList) {
			if(one.getStudentID() == st_id && one.getYear() == year && one.getSemester() == semester && one.isApplication_state()) {
				count ++;
			}
		}
		return count;
	}
	
	public String[] semester_list(int st_id) {
		int count = 0;
		String[] SemeList = {NO_LIST};
		if(CreditList == null) {
			return null;
		}
		else {
			int[][] temp = new int[CreditList.size()][2];
			
			for(int i = 0; i < CreditList.size(); i++) {
				if(CreditList.get(i).getStudentID() == st_id) {
					boolean judge = true;
					for(int j = 0; j < CreditList.size(); j++) {
						if(temp[j][0] == CreditList.get(i).getYear() && temp[j][1] == CreditList.get(i).getSemester()) {
							judge = false;
						}
					}
					if(judge) {
						temp[count][0] = CreditList.get(i).getYear();
						temp[count][1] = CreditList.get(i).getSemester();
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
	
	public String[] semester_list_isapped(int st_id) {
		int count = 0;
		String[] SemeList_isapped = {NO_LIST};
		if(CreditList == null) {
			return null;
		}
		int[][] temp = new int[CreditList.size()][2];
		
		for(int i = 0; i < CreditList.size(); i++) {
			if(CreditList.get(i).getStudentID() == st_id && CreditList.get(i).isApplication_state()) {
				boolean judge = true;
				for(int j = 0; j < CreditList.size(); j++) {
					if(temp[j][0] == CreditList.get(i).getYear() && temp[j][1] == CreditList.get(i).getSemester()) {
						judge = false;
					}
				}
				if(judge) {
					temp[count][0] = CreditList.get(i).getYear();
					temp[count][1] = CreditList.get(i).getSemester();
					count++;
				}
			}
		}
		
		if(count > 0) {
			SemeList_isapped = new String[count];
			for(int i = 0; i < count; i++) {
				SemeList_isapped[i] = String.format("%4s��%2s�б�", temp[i][0], temp[i][1]);
			}
		}
		return SemeList_isapped;
	}
	
	public ArrayList<CompletedCredit> completed_credit_list_print(int st_id, int year, int semester) { // �б⺰ �̼��� ���� ��� ���
		int count = count_std_term_credit(st_id, year, semester);
		ArrayList<CompletedCredit> CCLP = new ArrayList<>();
		
		if(count > 0) {
			for(CompletedCredit one : CreditList) {
				if(one.getStudentID() == st_id && one.getYear() == year && one.getSemester() == semester) {
					CCLP.add(one);
				}
			}
		}
		return CCLP;
	}
	
	public ArrayList<CompletedCredit> applicated_credit_list_print(int st_id, int year, int semester) { // �̼��� ���� ��� �� ��û�� ���� ��� ���
		int count = count_std_term_credit_isapped(st_id, year, semester);
		ArrayList<CompletedCredit> ACLP = new ArrayList<>();
		
		if(count > 0) {
			for(CompletedCredit one : CreditList) {
				if(one.getStudentID() == st_id && one.getYear() == year && one.getSemester() == semester && one.isApplication_state()) {
					ACLP.add(one);
				}
			}
		}
		return ACLP;
	}
	
}
