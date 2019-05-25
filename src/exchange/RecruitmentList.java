package exchange;

import javax.swing.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Vector;

public class RecruitmentList implements Serializable {
    private ArrayList<Recruitment> recruitments = new ArrayList<Recruitment>();

    //������ �ڵ� ����

    public void addList(Recruitment newThing){
        recruitments.add(newThing);
    }

    public void deleteList(int index){
        recruitments.remove(index);
    }

    public Vector<String> printList(){	// �������� ��ȸ�� �ʿ��� JList�� ����� ���� vector�� return
        Vector<String> RList = new Vector<String>();

        for(int i = 0; i < recruitments.size(); i++){
            Recruitment temp = recruitments.get(i);
            RList.add("" + temp.getRecruitNum() + ". " + temp.getTitle());
        }

        return RList;
    }

    public Vector<String> printList2(){	// �������� ������ �ʿ��� JList�� ����� ���� vertor�� return
        Vector<String> RList = new Vector<String>();

        for(int i = 0; i < recruitments.size(); i++){
            Recruitment temp = recruitments.get(i);
            RList.add("" + temp.getRecruitNum() + "�� �������� : " + temp.getTitle());
        }

        return RList;
    }
    
    public Vector<String> printState(int stID){
        Vector<String> RList = new Vector<String>();
        int index;
        for(int i = 0; i < recruitments.size(); i++){
            index = recruitments.get(i).checkUser(stID);
            if(index != -1){
                RList.addElement(recruitments.get(i).printState(index));
            }
        }
        return RList;
    }
    
    public JPanel printContents(int index){
        Recruitment temp = recruitments.get(index);
        JPanel temp2 = temp.printRecuritment();
        return temp2;
    }

    public boolean checkList(int num){	// �������� ��ȣ�� num�� �������� �����ϴ��� üũ�ϴ� �޼ҵ�
        int i;
        for(i = 0; i < recruitments.size(); i ++){
            Recruitment check = recruitments.get(i);
            if(check.getRecruitNum() == num)
                break;
        }
        if(i == recruitments.size())
            return false;
        else
            return true;
    }

    public boolean checkUser(int index, int stID){  // index��°�� �ִ� �������� stID�� �л��� ��û�� �ߴ��� ���ߴ��� Ȯ���ϴ� �޼ҵ�
        if(recruitments.get(index).checkUser(stID) != -1)
            return true;
        else
            return false;
    }

    public void apply(int index, Application newone){
        recruitments.get(index).addList(newone);
    }

    public void deleteAplication(int num, int stID){
        for(int i = 0; i < recruitments.size(); i++){
            if(num == recruitments.get(i).getRecruitNum()){
                int index = recruitments.get(i).checkUser(stID);
                recruitments.get(i).deleteList(index);
                break;
            }
        }
    }

    public int finalChoice(int num, int stID){	// ��������� ���� �޼ҵ�
    	int index = 0;
        for(int i = 0; i < recruitments.size(); i++) {
            if (num == recruitments.get(i).getRecruitNum()) {
                index = recruitments.get(i).checkUser(stID);
                if(recruitments.get(i).getProgress() == 2){
                    recruitments.get(i).finalChoice(index);
                }
                else
                    JOptionPane.showMessageDialog(null, "����� �� ���� �����Դϴ�.", "�˸�", JOptionPane.PLAIN_MESSAGE);	// �̰� ��� ��ü�� UI���� �ϵ��� �ؾߵǴµ�...
                break;
            }
        }
        
        return index;
    }

    public void setRecruitState(int year, int month, int date) {	//�ÿ��� ���� �ɻ�ܰ踦 �Ѿ�� �ٷ� �հ�ó���� ��
    	for(int i = 0; i < recruitments.size(); i++) {
    		int temp = recruitments.get(i).getDeadline();
    		int t_y = temp / 10000;
    		int t_m = (temp/100) - (t_y*100);
    		int t_d = temp - (t_y*10000) - (t_m*100);
    		if(t_y < year) {	
    			recruitments.get(i).setProgress(2);
    			recruitments.get(i).setPass();
    		}
    		else if(t_y == year){
    			if(month > t_m) {
    				recruitments.get(i).setProgress(2);
    				recruitments.get(i).setPass();
    			}
    			else if(month == t_m && date > t_d) {
    				recruitments.get(i).setProgress(2);
    				recruitments.get(i).setPass();
    			}
    		}
    		
    		temp = recruitments.get(i).getSelectDeadline();
    		t_y = temp / 10000;
    		t_m = (temp/100) - (t_y*100);
    		t_d = temp - (t_y*10000) - (t_m*100);
    		if(t_y < year) {
    			recruitments.get(i).setProgress(3);
    		}
    		else if(t_y == year){
    			if(month > t_m) {
    				recruitments.get(i).setProgress(3);
    			}
    			else if(month == t_m && date > t_d) {
    				recruitments.get(i).setProgress(3);
    			}
    		}
    		
    	}
    }

    //getter
    public int getRecruitNum(int index){
        return recruitments.get(index).getRecruitNum();
    }
    public int getRecruitState(int index) {
    	return recruitments.get(index).getProgress();
    }
    
    public int getSemester(int index) {
        return recruitments.get(index).getStartSemester();
    }
    public int getYear(int index) {
        return recruitments.get(index).getStartYear();
    }
    public String getMajor(int index) {
        return recruitments.get(index).getMajor();
    }
    public String getNation(int index) {
        return recruitments.get(index).getNation();
    }
    public String getUniv(int index) {
        return recruitments.get(index).getUniversity();
    }
    public int getPeriod(int index) {
    	return recruitments.get(index).getPeriod();
    }
    
}
