package client;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Client {
	
	JSONObject file;
	static String fileName = "data.txt";
	
	public Client() {
		file = new JSONObject();
	}
	
	//key ����� ������ �о�´�. ����� �� ������ null�� ��ȯ�Ѵ�.
	public String getString(String key) {
		return this.read(key);
	}
	
	//key�� ����� ���� ������ �־��� �����ǰ��� ���� �����
	public void setString(String key, String value) {
		try {
			write(key, value);
		} catch (IOException e) {
			e.getStackTrace();
		}
	}
	
	//key�� �׿� �ش��ϴ� value�� �����Ѵ�
	public void remove(String key) {
		String saved = read();
		if(read(saved, key) != null) { //�ش� key�� ����� ���� ������
			JSONObject jsonObject = string2JSONObject(saved);
			jsonObject.remove(key);
			try {
				write(jsonObject.toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	//key�� ����Ǿ� �ִ� ������ �ڿ� ���ο� value�� �����δ�.
	//addEnter true�̸� "���� ������" + "\n" + "�� ������"
	//addEnter false�̸� "���� ������" + "�� ������"
	//������ ����� key�� ������ �׳� setString�� �����ϴ�
	public void addString(String key, String value, boolean addEnter) {
		String temp = this.getString(key);
		
		try {
			write(key, (temp == null ? "" : temp) + (temp != null && addEnter ? "\n" : "") + value);
		} catch (IOException e) {
			e.getStackTrace();
		}
	}
	
	private void write(String key, String value) throws IOException {
		String saved = this.read();
		JSONObject jsonObject = string2JSONObject(saved);
		
		jsonObject.put(key, value);
		String message = jsonObject.toString();
		write(message);
	}
	
	private void write(String value) throws IOException {
		if(value == null) {
			System.out.print("[���] write(value)���� value�� null�Դϴ�.\n");
		}
		
		BufferedOutputStream buffer = null;
		try {
			OutputStream outputStream = new FileOutputStream(fileName);
			buffer = new BufferedOutputStream(outputStream);
			byte by[] = value.getBytes();
			buffer.write(by);
		} catch(Exception e) {
			e.getStackTrace();
		} finally {
			buffer.close();
		}
	}

	//����� key ������ null ����
	private String read(String key) {
		String saved = read();
		
		return read(saved, key);
	}
	
	//saved�� ���Ͽ� ����� �ؽ�Ʈ �����̴�. saved���� key���� ���� value�� �о�´�
	//����� key ������ null ����
	private String read(String saved, String key) {
		JSONObject jsonObject = string2JSONObject(saved);
		Object value = (Object)jsonObject.get(key);
		
		return value == null ? null : jsonObject.get(key).toString();
	}
	
	private String read() {
		byte buffer[] = null;
		try {
			InputStream inputStream = new FileInputStream(fileName);
			buffer = new byte[inputStream.available()];
			while(inputStream.read(buffer) != -1 && !(new String(buffer)).equals("")) {}
			
			inputStream.close();
		} catch(Exception e) {
			e.getStackTrace();
		}
		
		return (buffer == null) ? "" : new String(buffer);
	}
	
	//str�� �Ľ��� �� ������ new JSONObject�� return
	private JSONObject string2JSONObject(String str) {
		JSONParser parser = new JSONParser();
		JSONObject jsonObject = null;
		try {
			jsonObject = (JSONObject)parser.parse(str);
		} catch (ParseException e) {
			//�Ľ��� �� ���� ���(������ ����ְų� �����̰ų� JSON ������ �ƴ� �̻��� ���ڰ� ���� ������ catch�� �´�.)
			jsonObject = new JSONObject();
		}
		
		return jsonObject;
	}
	
}
