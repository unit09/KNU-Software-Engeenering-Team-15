package creditManagement;

import client.Client;

public class CompletedCreditInstanceMaker {
//�ʱ�ȭ -> �������� ��û(������) -> ���� ����
	private static CompletedCreditList CCList;
	private static String[] nation_list = {"�̱�", "����", "�߱�", "�Ϻ�"};
	private static String[][] univ_list = {{"�Ϲ���", "MIT"}, {"��������"}, {"���"}, {"������", "����", "�ͼ���"}};
	private static String[] dept_list = {"��ǻ��", "����"};
	private static String[][] course_list
	= {{"��ǻ���а���", "��ǻ������", "�̻����", "���ʹ����н���", "���ʰ��м���", "�������α׷���", "���м���", "�ڷᱸ��", "�ڷᱸ������", "�ڹ����α׷���", "���丶Ÿ", "��ȸ��"},
		{"�������ڼ���", "�������ڹ�����", "�������ڽ��輳��", "����ȸ�μ���", "��ȸ�μ���"}};
	private static int[] acc_credit_list = {2, 3, 4};
	private static double[] grade_list = {1.7, 2.0, 2.3, 2.7, 3.0, 3.3, 3.7, 4.0, 4.3};
	
	public static void main(String[] args) throws ClassNotFoundException {
		Client client = new Client();
		CCList = CompletedCreditList.get_completed_credit_list(client);
		
		
		do_initial(client, 2015123456, 2018, 1, "�̱�", "�Ϲ���", "��ǻ��", "��ǻ���а���", 3, 3.7, false);
		do_initial(client, 2015123456, 2018, 1, "�̱�", "�Ϲ���", "��ǻ��", "��ǻ������", 2, 3.3, false);
		do_initial(client, 2015123456, 2018, 1, "�̱�", "�Ϲ���", "��ǻ��", "�̻����", 2, 4.0, false);
		do_initial(client, 2015123456, 2018, 1, "�̱�", "�Ϲ���", "��ǻ��", "���ʹ����н���", 3, 3.3, false);
		do_initial(client, 2015123456, 2018, 1, "�̱�", "�Ϲ���", "��ǻ��", "���ʰ��м���", 3, 2.7, false);
		do_initial(client, 2015123456, 2018, 1, "�̱�", "�Ϲ���", "��ǻ��", "�������α׷���", 3, 3.0, false);

		do_initial(client, 2015123456, 2018, 2, "�̱�", "�Ϲ���", "��ǻ��", "���м���", 2, 3.7, false);
		do_initial(client, 2015123456, 2018, 2, "�̱�", "�Ϲ���", "��ǻ��", "�ڷᱸ��", 3, 3.7, false);
		do_initial(client, 2015123456, 2018, 2, "�̱�", "�Ϲ���", "��ǻ��", "�ڷᱸ������", 3, 4.0, false);
		do_initial(client, 2015123456, 2018, 2, "�̱�", "�Ϲ���", "��ǻ��", "�ڹ����α׷���", 3, 3.3, false);
		do_initial(client, 2015123456, 2018, 2, "�̱�", "�Ϲ���", "��ǻ��", "���丶Ÿ", 2, 2.3, false);
		do_initial(client, 2015123456, 2018, 2, "�̱�", "�Ϲ���", "��ǻ��", "��ȸ��", 2, 3.0, false);

		do_initial(client, 2015123456, 2019, 1, "����", "��������", "����", "�������ڼ���", 2, 4.0, false);
		do_initial(client, 2015123456, 2019, 1, "����", "��������", "����", "�������ڹ�����", 2, 4.3, false);
		do_initial(client, 2015123456, 2019, 1, "����", "��������", "����", "�������ڽ��輳��", 3, 4.0, false);
		do_initial(client, 2015123456, 2019, 1, "����", "��������", "����", "����ȸ�μ���", 3, 4.0, false);
		do_initial(client, 2015123456, 2019, 1, "����", "��������", "����", "��ȸ�μ���", 3, 3.7, false);
		
		random_initial(client, 2015111111, 2018, 1);
		random_initial(client, 2015111111, 2018, 2);
		random_initial(client, 2015111111, 2019, 1);
	}
	
	public static void list_add(Client client, CompletedCredit CC) {
		boolean ismod = false;
		int size = 0;
		if(CCList.get_instance_list() != null) {
			size = CCList.get_instance_list().size();
		}
		else {
			try {
				CCList.completed_credit_list_append(client, CC);
			} catch (ClassNotFoundException e) {}
		}
		for(int i = 0; i < size ; i++) {
			try {
				if(CCList.completed_credit_list_modify(client, CC)) {
					ismod = true;
					break;
				}
			} catch (ClassNotFoundException e) {}
		}
		if(!ismod) {
			try {
				CCList.completed_credit_list_append(client, CC);
			} catch (ClassNotFoundException e) {}
		}
		//command line���� append�� �� Ȯ�� �뵵
		System.out.printf("%d|%d|%d|%s|%4s|%4s|%10s|%d|%.1f|%b|%b\n",CC.getSt_id(),CC.getYear(),CC.getSemester(),CC.getNation(),CC.getUniv(),CC.getDept(),CC.getCourse(),CC.getAccept_credit(),CC.getGrade(),CC.isApplication_state(),ismod);
	}
	
	public static void do_initial(Client client, int st_id, int year, int semester, String nation, String univ, String dept, String course, int acc_credit, double grade, boolean isapped) {
		CompletedCredit s1;
		
		s1 = new CompletedCredit();
		s1.setSt_id(st_id);
		s1.setYear(year);
		s1.setSemester(semester);
		s1.setNation(nation);
		s1.setUniv(univ);
		s1.setDept(dept);
		s1.setCourse(course);
		s1.setAccept_credit(acc_credit);
		s1.setGrade(grade);
		s1.setApplication_state(isapped);
		list_add(client, s1);
		
	}
	
	public static void random_initial(Client client, int id, int year, int semester) {
		int nation_index = (int) (Math.random() * nation_list.length);
		int univ_index = (int) (Math.random() * univ_list[nation_index].length);
		int dept_index = (int) (Math.random() * dept_list.length);
		for(int i = 0; i < 6; i++) {
			int course_index = (int) (Math.random() * course_list[dept_index].length);
			int acc_credit_index = (int) (Math.random() * acc_credit_list.length);
			int grade_index = (int) (Math.random() * grade_list.length);
			
			do_initial(client, id, year, semester, nation_list[nation_index], univ_list[nation_index][univ_index], dept_list[dept_index], course_list[dept_index][course_index], acc_credit_list[acc_credit_index], grade_list[grade_index], false);
		}
	}
}
