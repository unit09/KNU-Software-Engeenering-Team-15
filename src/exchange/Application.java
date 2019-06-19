package exchange;

import java.io.Serializable;

import user.*;

public class Application implements Serializable {
    private Student applyStudent;
    private int recuritNum;
    private boolean pass = false;
    private boolean choice = false;
    //�߰������� ���ؼ��� �������� ����

    public Application(int recuritNum, Student applyStudent){
        this.recuritNum = recuritNum;
        this.applyStudent = applyStudent;
    }

    //getter & setter
    public int getRecuritNum() {
        return recuritNum;
    }
    public boolean isPass() {
        return pass;
    }
    public boolean isChoice() {
        return choice;
    }

    public void setRecuritNum(int recuritNum) {
        this.recuritNum = recuritNum;
    }
    public void setPass(boolean pass) {
        this.pass = pass;
    }
    public void setChoice(boolean choice) {
        this.choice = choice;
    }

    public int getStudentID(){
        return this.applyStudent.getStudentID();
    }
    public double getScore() {
    	return this.applyStudent.getScore();
    }
}
