package exchange;

import javax.swing.*;
import java.io.Serializable;
import java.util.ArrayList;

public class Recruitment implements Serializable {
    private int recruitNum;
    private String title;
    private String contents;
    private int startYear;
    private String nation;
    private String university;
    private String major;
    
    private int startSemester;	//3:여름계절학기, 4:겨울계절학기
    private int period;
    
    private int deadlineYear;
    private int deadlineMonth;
    private int deadlineDay;
    private int selectDeadlineYear;
    private int selectDeadlineMonth;
    private int selectDeadlineDay;

    private int progress = 0;   //0:모집중, 1:심사중, 2:심사종료, 3:다끝남

    private ArrayList<Application> applications = new ArrayList<>();

    public Recruitment(int recruitNum, String title, String contents, int startYear, String nation, String university,String major){
        this.recruitNum = recruitNum;
        this.title = title;
        this.contents = contents;
        this.startYear = startYear;
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

    public void deleteList(int index){
        applications.remove(index);
    }
    
    public void finalChoice(int index){
        if(applications.get(index).isChoice() == true){
            JOptionPane.showMessageDialog(null, "이미 등록되어있습니다.", "알림", JOptionPane.PLAIN_MESSAGE);
        }
        else if(applications.get(index).isPass() == true){
            applications.get(index).setChoice(true);
            JOptionPane.showMessageDialog(null, "등록되었습니다.", "알림", JOptionPane.PLAIN_MESSAGE);
        }
        else{
            JOptionPane.showMessageDialog(null, "등록할 수 있는 상태가 아닙니다.", "알림", JOptionPane.PLAIN_MESSAGE);
        }
    }

    public String printState(int index){
    	if(progress == 0)
            return recruitNum + ". " + title + " : 모집중";
        else if(progress == 1)
            return recruitNum + ". " + title + " : 심사중";
        else if(progress == 2){
            if(applications.get(index).isPass() == true) {
                if(applications.get(index).isChoice() == true)
                    return recruitNum + ". " + title + " : 최종 등록 완료";
                else
                    return recruitNum + ". " + title + " : 합격";
            }
            else
                return recruitNum + ". " + title + " : 불합격";
        }
        else{
            if(applications.get(index).isChoice() == true)
                return recruitNum + ". " + title + " : 마감 - 최종 등록 완료";
            else
                return recruitNum + ". " + title + " : 마감";
        }
    }
    
    public void setPass() {	//원래는 외국대학에서 응시원서를 받아서 보고 처리하지만 여기서는 학점이 4.0 이상이면 자동 합격하도록 설정
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
    public int getDeadlineYear() {
        return deadlineYear;
    }
    public int getDeadlineMonth() {
        return deadlineMonth;
    }
    public int getDeadlineDay() {
        return deadlineDay;
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
    public int getSelectDeadlineYear() {
        return selectDeadlineYear;
    }
    public int getSelectDeadlineMonth() {
        return selectDeadlineMonth;
    }
    public int getSelectDeadlineDay() {
        return selectDeadlineDay;
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
    public void setDeadlineYear(int deadline) {
        this.deadlineYear = deadline;
    }
    public void setDeadlineMonth(int deadline) {
        this.deadlineMonth = deadline;
    }
    public void setDeadlineDay(int deadline) {
        this.deadlineDay = deadline;
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
    public void setSelectDeadlineYear(int selectDeadline) {
        this.selectDeadlineYear = selectDeadline;
    }
    public void setSelectDeadlineMonth(int selectDeadline) {
        this.selectDeadlineMonth = selectDeadline;
    }
    public void setSelectDeadlineDay(int selectDeadline) {
        this.selectDeadlineDay = selectDeadline;
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
