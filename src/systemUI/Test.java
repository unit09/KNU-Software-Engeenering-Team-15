package systemUI;

import client.Client;
import user.*;

public class Test {
	
	public static void main(String[] args) {
		Client client = new Client();
		
		//Student temp = new Student(2015123456, "��浿", "��ǻ���к�", 2, 2.14, "test1@naver.com", "010-1264-1231");
        Student temp2 = new Student(-1, "������", "�����ڿ��л���ü", 777, 0, "year�� �����ڹ�ȣ", " ");
        //Student temp3 = new Student(2015123456, "ȫ�浿", "��ǻ���к�", 2, 4.12, "test@naver.com", "010-1234-5678");
        
        //client.setObject("s1123", "1123", temp);
       //client.setObject("s1132", "1132", temp3);
        client.setObject("a1124", "1124", temp2);
	}
}
