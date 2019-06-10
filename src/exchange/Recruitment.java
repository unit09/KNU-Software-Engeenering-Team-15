package exchange;

import javax.swing.*;
import java.io.Serializable;
import java.util.ArrayList;

public class Recruitment implements Serializable {
    private int recruitNum;
    private String title;
    private String contents;
    private int deadline;
    private int selectDeadline;
    private int startYear;
    private int startSemester;	// 3: ���� �����б�, 4: �ܿ� �����б�
    private int period;
    private String nation;
    private String university;
    private String major;

    private int progress = 0;   //0:������, 1:�ɻ���, 2:�ɻ�����, 3:�ٳ���

    private ArrayList<Application> applications = new ArrayList<Application>();

    public Recruitment(int recruitNum, String title, String contents, int deadline, int selectDeadline, int startYear, int startSemester, int period,
                       String nation, String university,String major){
        this.recruitNum = recruitNum;
        this.title = title;
        this.contents = contents;
        this.deadline = deadline;
        this.selectDeadline = selectDeadline;
        this.startYear = startYear;
        this.startSemester = startSemester;
        this.period = period;
        this.nation = nation;
        this.university = university;
        this.major = major;
    }

    public void addList(Application apply){
        applications.add(apply);
    }

    public int checkUser(int stID){
        int i;
        for(i = 0; i < applications.size(); i++){
            Application ch = applications.get(i);
            if(ch.getStudentID() == stID)
                break;
        }
        if(i == applications.size())
            return -1;
        else
            return i;
    }

    public void deleteList(int index){ ;
        applications.remove(index);
    }

    public JPanel printRecuritment(){
        JPanel pane = new JPanel();
        pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
        JTextField num = new JTextField("�������� ��ȣ : " + recruitNum, 32);
        JTextField ti = new JTextField("���� : " + title, 32);
        JTextField na = new JTextField("����/����/���� : " + nation + "/" + university + "/" + major, 32);
        JTextField st = new JTextField("���� : " + startYear + "�� " + startSemester + "�б���� ����", 32);
        JTextField pe = new JTextField("�Ⱓ : " + period + "�б⵿��", 32);
        JTextArea con = new JTextArea(contents, 12, 32);
        JTextField de = new JTextField("�����Ⱓ : " + deadline + "����", 32);
        JTextField sde = new JTextField("������� ������ : " + selectDeadline + "����", 32);

        num.setEditable(false);
        ti.setEditable(false);
        na.setEditable(false);
        st.setEditable(false);
        pe.setEditable(false);
        con.setEnabled(false);
        de.setEditable(false);
        sde.setEditable(false);

        pane.add(num);
        pane.add(ti);
        pane.add(na);
        pane.add(st);
        pane.add(pe);
        pane.add(con);
        pane.add(de);
        pane.add(sde);

        return pane;
    }
    
    public void finalChoice(int index){
        if(applications.get(index).isChoice() == true){
            JOptionPane.showMessageDialog(null, "�̹� ��ϵǾ��ֽ��ϴ�.", "�˸�", JOptionPane.PLAIN_MESSAGE);
        }
        else if(applications.get(index).isPass() == true){
            applications.get(index).setChoice(true);
            JOptionPane.showMessageDialog(null, "��ϵǾ����ϴ�.", "�˸�", JOptionPane.PLAIN_MESSAGE);
        }
        else{
            JOptionPane.showMessageDialog(null, "����� �� �ִ� ���°� �ƴմϴ�.", "�˸�", JOptionPane.PLAIN_MESSAGE);
        }
    }

    public String printState(int index){
    	if(progress == 0)
            return recruitNum + ". " + title + " : ������";
        else if(progress == 1)
            return recruitNum + ". " + title + " : �ɻ���";
        else if(progress == 2){
            if(applications.get(index).isPass() == true) {
                if(applications.get(index).isChoice() == true)
                    return recruitNum + ". " + title + " : ���� ��� �Ϸ�";
                else
                    return recruitNum + ". " + title + " : �հ�";
            }
            else
                return recruitNum + ". " + title + " : ���հ�";
        }
        else{
            if(applications.get(index).isChoice() == true)
                return recruitNum + ". " + title + " : ���� - ���� ��� �Ϸ�";
            else
                return recruitNum + ". " + title + " : ����";
        }
    }
    
    public void setPass() {	//������ �ܱ����п��� ���ÿ����� �޾Ƽ� ���� ó�������� ���⼭�� ������ 4.0 �̻��̸� �ڵ� �հ��ϵ��� ����
    	for(int i = 0; i < applications.size(); i++) {
    		if(applications.get(i).getScore() >= 4.0)
    			applications.get(i).setPass(true);
    	}
    }

    //getter & setter
    public int getRecruitNum() {
        return recruitNum;
    }
    public String getTitle() {
        return title;
    }
    public int getDeadline() {
        return deadline;
    }
    public int getPeriod() {
        return period;
    }
    public String getMajor() {
        return major;
    }
    public int getProgress() {
        return progress;
    }
    public int getSelectDeadline() {
        return selectDeadline;
    }
    public int getStartSemester() {
        return startSemester;
    }
    public int getStartYear() {
        return startYear;
    }
    public String getContents() {
        return contents;
    }
    public String getNation() {
        return nation;
    }
    public String getUniversity() {
        return university;
    }

    public void setRecruitNum(int recruitNum) {
        this.recruitNum = recruitNum;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setContents(String contents) {
        this.contents = contents;
    }
    public void setDeadline(int deadline) {
        this.deadline = deadline;
    }
    public void setNation(String nation) {
        this.nation = nation;
    }
    public void setPeriod(int period) {
        this.period = period;
    }
    public void setProgress(int progress) {
        this.progress = progress;
    }
    public void setSelectDeadline(int selectDeadline) {
        this.selectDeadline = selectDeadline;
    }
    public void setStartSemester(int startSemester) {
        this.startSemester = startSemester;
    }
    public void setMajor(String major) {
        this.major = major;
    }
    public void setStartYear(int startYear) {
        this.startYear = startYear;
    }
    public void setUniversity(String university) {
        this.university = university;
    }
}
