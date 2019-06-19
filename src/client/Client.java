package client;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
		Object o = this.readObject(key);
		
		return o == null ? null : o.toString();
	}
	
	//key ����� ������ int�� �о�´�. ����� �� ������ null�� ��ȯ�Ѵ�.
	public int getInt(String key) {
		Object o = this.readObject(key);
		
		return o == null ? null : (int)o;
	}
	
	//key ����� ������ double�� �о�´�. ����� �� ������ null�� ��ȯ�Ѵ�.
	public double getDouble(String key) {
		Object o = this.readObject(key);
		
		return o == null ? null : (double)o;
	}
	
	//key ����� ������ �о�´�. ����� �� ������ null�� ��ȯ�Ѵ�.
	public Object getObject(String key) {
		return this.readObject(key);
	}
	
	//key ����� ������ �о�´�. ����� �� ������ null�� ��ȯ�Ѵ�.
	public Object getObject(String key1, String key2) {
		JSONObject jsonObject = null;
		jsonObject = (JSONObject)this.getObject(key1);
		
		if(jsonObject == null) {
			return null;
		} else {
			return jsonObject.get(key2);
		}
	}
	
	//key�� ����� ���� ������ �־��� �����ǰ��� ���� �����
	public void setString(String key, String value) {
		setObject(key, value);
	}
	
	//key�� ����� ���� ������ �־��� �����ǰ��� ���� �����
	public void setInt(String key, int value) {
		setObject(key, value);
	}
	
	//key�� ����� ���� ������ �־��� �����ǰ��� ���� �����
	public void setDouble(String key, double value) {
		setObject(key, value);
	}

	//key�� ����� ���� ������ �־��� �����ǰ��� ���� �����
	public void setObject(String key, Object o) {
		try {
			writeObject(key, o);
		} catch (IOException e) {
			e.getStackTrace();
		}
	}

	//key�� ����� ���� ������ �־��� �����ǰ��� ���� �����
	public void setString(String key1, String key2, String value) {
		this.setObject(key1, key2, value);
	}

	//key�� ����� ���� ������ �־��� �����ǰ��� ���� �����
	public void setObject(String key1, String key2, Object o) {
		JSONObject jsonObject;
		jsonObject = (JSONObject)this.getObject(key1);
		if(jsonObject == null) { //key1�� ���� ��
			jsonObject = new JSONObject();
		}
		
		jsonObject.put(key2, o);
		this.setObject(key1, jsonObject);
	}
	
	//key�� �׿� �ش��ϴ� value�� �����Ѵ�
	public void remove(String key) {
		JSONObject saved = (JSONObject)readObject();
		if(saved.get(key) != null) { //�ش� key�� ����� ���� ������
			saved.remove(key);
			this.write(saved);
		}
	}
	
	//key�� ����Ǿ� �ִ� ������ �ڿ� ���ο� value�� �����δ�.
	//addEnter�� true�̸� "���� ������" + "\n" + "�� ������"
	//addEnter�� false�̸� "���� ������" + "�� ������"
	//������ ����� key�� ������ �׳� setString�� �����ϴ�
	public void addString(String key, String value, boolean addEnter) {
		String temp = this.getString(key);
		
		try {
			writeObject(key, (temp == null ? "" : temp) + (temp != null && addEnter ? "\n" : "") + value);
		} catch (IOException e) {
			e.getStackTrace();
		}
	}
	
	private void writeObject(String key, Object o) throws IOException {
		JSONObject saved;
		saved = (JSONObject)this.readObject();
		if(saved == null) { //������ ����� ���� ���� ���
			saved = new JSONObject();
		}
		
		saved.put(key, o);
		writeJSONObject(saved);
	}
	
	private void writeJSONObject(JSONObject o) throws IOException {
		write(o);
	}
	
	private void write(Object o) {
		if(o == null) {
			System.out.print("[���] write(o)���� value�� null�Դϴ�.\n");
		}
		
		OutputStream outputStream = null;
		ObjectOutputStream objectOutputStream = null;
		try {
			outputStream = new FileOutputStream(fileName);
			objectOutputStream = new ObjectOutputStream(outputStream);
			
			objectOutputStream.writeObject(o);
		} catch(Exception e) {
			e.getStackTrace();
		} finally {
			try {
				outputStream.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				objectOutputStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	//����� key ������ null ����
	private Object readObject(String key) {
		JSONObject saved = (JSONObject)readObject();
		if(saved == null) {
			return null;
		}
		
		return saved.get(key);
	}
	
	//saved�� ���Ͽ� ����� �ؽ�Ʈ �����̴�. saved���� key���� ���� value�� �о�´�
	//����� key ������ null ����
	private String readString(String saved, String key) {
		return readObject(saved, key).toString();
	}
	
	//saved�� ���Ͽ� ����� �ؽ�Ʈ �����̴�. saved���� key���� ���� value�� �о�´�
	//����� key ������ null ����
	private Object readObject(String saved, String key) {
		JSONObject jsonObject = string2JSONObject(saved);
		Object value = (Object)jsonObject.get(key);
		
		return value == null ? null : jsonObject.get(key);
	}
	
	//txt ������ �״�� �о���� �Լ�
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
	
	//txt ������ �״�� �о���� �Լ�
	private Object readObject() {
		Object output = null;
		
		InputStream fis = null;
		ObjectInputStream ois = null;
		
		try {
			fis = new FileInputStream(fileName);
			ois = new ObjectInputStream(fis);
			
			output = ois.readObject();
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return output == null ? null : output;
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
