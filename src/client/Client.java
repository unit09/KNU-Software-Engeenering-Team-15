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
	
	//key 저장된 내용을 읽어온다. 저장된 게 없으면 null을 반환한다.
	public String getString(String key) {
		Object o = this.readObject(key);
		
		return o == null ? null : o.toString();
	}
	
	//key 저장된 내용을 int로 읽어온다. 저장된 게 없으면 null을 반환한다.
	public int getInt(String key) {
		Object o = this.readObject(key);
		
		return o == null ? null : (int)o;
	}
	
	//key 저장된 내용을 double로 읽어온다. 저장된 게 없으면 null을 반환한다.
	public double getDouble(String key) {
		Object o = this.readObject(key);
		
		return o == null ? null : (double)o;
	}
	
	//key 저장된 내용을 읽어온다. 저장된 게 없으면 null을 반환한다.
	public Object getObject(String key) {
		return this.readObject(key);
	}
	
	//key 저장된 내용을 읽어온다. 저장된 게 없으면 null을 반환한다.
	public Object getObject(String key1, String key2) {
		JSONObject jsonObject = null;
		jsonObject = (JSONObject)this.getObject(key1);
		
		if(jsonObject == null) {
			return null;
		} else {
			return jsonObject.get(key2);
		}
	}
	
	//key에 저장된 값이 기존에 있었건 없었건간에 덮어 씌운다
	public void setString(String key, String value) {
		setObject(key, value);
	}
	
	//key에 저장된 값이 기존에 있었건 없었건간에 덮어 씌운다
	public void setInt(String key, int value) {
		setObject(key, value);
	}
	
	//key에 저장된 값이 기존에 있었건 없었건간에 덮어 씌운다
	public void setDouble(String key, double value) {
		setObject(key, value);
	}

	//key에 저장된 값이 기존에 있었건 없었건간에 덮어 씌운다
	public void setObject(String key, Object o) {
		try {
			writeObject(key, o);
		} catch (IOException e) {
		}
	}

	//key에 저장된 값이 기존에 있었건 없었건간에 덮어 씌운다
	public void setString(String key1, String key2, String value) {
		this.setObject(key1, key2, value);
	}

	//key에 저장된 값이 기존에 있었건 없었건간에 덮어 씌운다
	public void setObject(String key1, String key2, Object o) {
		JSONObject jsonObject;
		jsonObject = (JSONObject)this.getObject(key1);
		if(jsonObject == null) { //key1이 없을 때
			jsonObject = new JSONObject();
		}
		
		jsonObject.put(key2, o);
		this.setObject(key1, jsonObject);
	}
	
	//key와 그에 해당하는 value를 삭제한다
	public void remove(String key) {
		JSONObject saved = (JSONObject)readObject();
		
		if(saved != null && saved.get(key) != null) { //해당 key로 저장된 값이 있으면
			saved.remove(key);
			this.write(saved);
		}
	}
	
	//key에 저장되어 있던 데이터 뒤에 새로운 value를 덧붙인다.
	//addEnter가 true이면 "기존 데이터" + "\n" + "새 데이터"
	//addEnter가 false이면 "기존 데이터" + "새 데이터"
	//기존에 저장된 key가 없으면 그냥 setString과 동일하다
	public void addString(String key, String value, boolean addEnter) {
		String temp = this.getString(key);
		
		try {
			writeObject(key, (temp == null ? "" : temp) + (temp != null && addEnter ? "\n" : "") + value);
		} catch (IOException e) {
		}
	}
	
	private void writeObject(String key, Object o) throws IOException {
		JSONObject saved;
		saved = (JSONObject)this.readObject();
		if(saved == null) { //기존에 저장된 값이 없는 경우
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
			System.err.print("[경고] write(o)에서 value가 null입니다.\n");
		}
		
		OutputStream outputStream = null;
		ObjectOutputStream objectOutputStream = null;
		try {
			outputStream = new FileOutputStream(fileName);
			objectOutputStream = new ObjectOutputStream(outputStream);
			
			objectOutputStream.writeObject(o);
		} catch(Exception e) {
		} finally {
			try {
				if(outputStream != null)
					outputStream.close();
			} catch (IOException e1) {
			}
			try {
				if(objectOutputStream != null)
					objectOutputStream.close();
			} catch (IOException e) {
			}
		}
	}

	//저장된 key 없으면 null 리턴
	private Object readObject(String key) {
		JSONObject saved = (JSONObject)readObject();
		if(saved == null) {
			return null;
		}
		
		return saved.get(key);
	}
	
	//saved는 파일에 저장된 텍스트 내용이다. saved에서 key값을 가진 value를 읽어온다
	//저장된 key 없으면 null 리턴
	private String readString(String saved, String key) {
		String temp = (String)readObject(saved, key);
		if(temp == null)
			return null;
		else
			return temp.toString();
	}
	
	//saved는 파일에 저장된 텍스트 내용이다. saved에서 key값을 가진 value를 읽어온다
	//저장된 key 없으면 null 리턴
	private Object readObject(String saved, String key) {
		JSONObject jsonObject = string2JSONObject(saved);
		Object value = (Object)jsonObject.get(key);
		
		return value == null ? null : jsonObject.get(key);
	}
	
	//txt 파일을 그대로 읽어오는 함수
	private String read() {
		byte buffer[] = null;
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(fileName);
			buffer = new byte[inputStream.available()];
			while(inputStream.read(buffer) != -1 && !(new String(buffer)).equals("")) {}
			
		} catch(Exception e) {
		}
		finally {
			try {
				if(inputStream != null)
					inputStream.close();
			} catch (IOException e) {
			}
		}
		
		return (buffer == null) ? "" : new String(buffer);
	}
	
	//txt 파일을 그대로 읽어오는 함수
	private Object readObject() {
		Object output = null;
		
		InputStream fis = null;
		ObjectInputStream ois = null;
		
		try {
			fis = new FileInputStream(fileName);
			ois = new ObjectInputStream(fis);
			
			output = ois.readObject();
		} catch (IOException | ClassNotFoundException e) {
		}
		finally {
			try {
				if(fis != null)
					fis.close();
			} catch (IOException e) {
			}
			try {
				if(ois != null)
					ois.close();
			} catch (IOException e) {
			}
		}
		
		return output == null ? null : output;
	}
	
	//str에 파싱할 게 없으면 new JSONObject를 return
	private JSONObject string2JSONObject(String str) {
		JSONParser parser = new JSONParser();
		JSONObject jsonObject = null;
		try {
			jsonObject = (JSONObject)parser.parse(str);
		} catch (ParseException e) {
			//파싱할 게 없는 경우(파일이 비어있거나 공백이거나 JSON 형식이 아닌 이상한 문자가 적혀 있으면 catch로 온다.)
			jsonObject = new JSONObject();
		}
		
		return jsonObject;
	}
	
}