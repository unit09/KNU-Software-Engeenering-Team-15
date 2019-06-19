package creditManagement;

import java.io.*;

import java.util.*;

 
public class CompletedCreditList {
	private static CompletedCreditList list_inst = null;
	private static ArrayList<CompletedCredit> credit_list;
	private static final String filename = "database/completed_credit/completed_credit_list_file";
	
	public final static String NO_LIST = "������ �����ϴ�";
	
	private CompletedCreditList() {} // singleton���� �����ϱ� ���� �����ڸ� private��
	
	public static CompletedCreditList get_completed_credit_list() throws ClassNotFoundException {
		if(list_inst == null) {
			list_inst = new CompletedCreditList();
			credit_list  = new ArrayList<>();
			list_download();
		}
		return list_inst;
	}
	public ArrayList<CompletedCredit> get_instance_list(){
		return credit_list;
	}
	
	@SuppressWarnings("unchecked")
	public static void list_download() throws ClassNotFoundException { // ���Ϸκ��� ����� ����Ʈ���� �ҷ�����
		FileInputStream file = null;
		ObjectInputStream obj = null;
		
		try{
			file = new FileInputStream(filename);
			obj = new ObjectInputStream(file);

			credit_list = (ArrayList<CompletedCredit>) obj.readObject();
			obj.close();
			file.close();
		}
		catch(FileNotFoundException e) { // ���ʿ� ������ ���� ��� ����ó���� �ϵ�, �׳� ����
			//e.printStackTrace();
		}
		catch (IOException e){
			System.out.println("read error");
		}

	}
	
	public void list_upload() { // �������ִ� ����Ʈ ���� ���Ͽ� �����Ű��
		FileOutputStream file = null;
		ObjectOutputStream obj = null;
		
		try{
			File backup = new File(filename);
			File rename = new File("database/completed_credit/credit_backup");
			backup.renameTo(rename);
			backup.delete();
			
			file = new FileOutputStream(filename);
			obj = new ObjectOutputStream(file);
			
			obj.writeObject(credit_list);
			obj.flush();
			obj.close();
			file.close();
		}
		catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e){
			System.out.println("write error");
		}
		
	}
	
	//instance initial�뵵
	public boolean completed_credit_list_append(CompletedCredit input) throws ClassNotFoundException {
		
		list_download(); // ������ ����� ����Ʈ �ҷ���
		
		try{
			// �ߺ��˻� - �� �л��� ������ �Ϳ����� �й�, �г⵵, �б�, ������ �ߺ��� �� �� ���⿡ �̰����� �ߺ��˻� (object���� �񱳰� �ȸ����� �Ф�)
			
			int st_id = input.getSt_id(), year = input.getYear(), semester = input.getSemester();
			String univ = input.getUniv(), course = input.getCourse();
			boolean overlap = false;
			for(CompletedCredit one : credit_list) {
				if(one.getSt_id() == st_id && one.getYear() == year && one.getSemester() == semester && one.getCourse().equals(course)) {
					overlap = true;
				}
			}
			// �ߺ� �ƴ� ��쿡�� add
			if(!overlap) {
				credit_list.add(credit_list.size(), input);
				list_upload();
			}
			
			return true;
		}
		catch(Exception e) {
			return false; // fail
		}
		
	}
	
	public boolean completed_credit_list_modify(CompletedCredit input) throws ClassNotFoundException {
		boolean ischanged = false;
		try{
			// ������ ������ �����ϱ� ���� ����Ʈ�� �ν��Ͻ� ���� �ٲ��� ���� �����ͷ� ���� �� ã��. �ε��� ���⿡�� �� ��������.
			int st_id = input.getSt_id(), year = input.getYear(), semester = input.getSemester();
			String course = input.getCourse();
			int i = 0;
			for(CompletedCredit one : credit_list) {
				if(one.getSt_id() == st_id && one.getYear() == year && one.getSemester() == semester && one.getCourse().equals(course)) {
					one = input;
					ischanged = true;
					list_upload(); // �ٲٰ� ������Ʈ
				}
			}
			
		}
		catch(Exception e) {}
		return ischanged;
	}
	
	
	public int count_std_term_credit(int st_id, int year, int semester) {
		int count = 0;
		for(CompletedCredit one : credit_list) {
			if(one.getSt_id() == st_id && one.getYear() == year && one.getSemester() == semester) {
				count ++;
			}
		}
		return count;
	}
	public int count_std_term_credit_isapped(int st_id, int year, int semester) {
		int count = 0;
		for(CompletedCredit one : credit_list) {
			if(one.getSt_id() == st_id && one.getYear() == year && one.getSemester() == semester && one.isApplication_state()) {
				count ++;
			}
		}
		return count;
	}
	
	public String[] semester_list(int st_id) {
		int count = 0;
		String[] SemeList = {NO_LIST};
		int[][] temp = new int[credit_list.size()][2];
		
		for(int i = 0; i < credit_list.size(); i++) {
			if(credit_list.get(i).getSt_id() == st_id) {
				boolean judge = true;
				for(int j = 0; j < credit_list.size(); j++) {
					if(temp[j][0] == credit_list.get(i).getYear() && temp[j][1] == credit_list.get(i).getSemester()) {
						judge = false;
					}
				}
				if(judge) {
					temp[count][0] = credit_list.get(i).getYear();
					temp[count][1] = credit_list.get(i).getSemester();
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
	
	public String[] semester_list_isapped(int st_id) {
		int count = 0;
		String[] SemeList_isapped = {NO_LIST};
		int[][] temp = new int[credit_list.size()][2];
		
		for(int i = 0; i < credit_list.size(); i++) {
			if(credit_list.get(i).getSt_id() == st_id && credit_list.get(i).isApplication_state()) {
				boolean judge = true;
				for(int j = 0; j < credit_list.size(); j++) {
					if(temp[j][0] == credit_list.get(i).getYear() && temp[j][1] == credit_list.get(i).getSemester()) {
						judge = false;
					}
				}
				if(judge) {
					temp[count][0] = credit_list.get(i).getYear();
					temp[count][1] = credit_list.get(i).getSemester();
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
			for(CompletedCredit one : credit_list) {
				if(one.getSt_id() == st_id && one.getYear() == year && one.getSemester() == semester) {
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
			for(CompletedCredit one : credit_list) {
				if(one.getSt_id() == st_id && one.getYear() == year && one.getSemester() == semester && one.isApplication_state()) {
					ACLP.add(one);
				}
			}
		}
		return ACLP;
	}
	
}
