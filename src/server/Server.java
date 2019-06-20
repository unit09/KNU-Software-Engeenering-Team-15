package server;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

public class Server {

	private static ServerSocket serverSocket;
	private static ArrayList<Socket> clientList;
	private static Thread acceptThread;
	private static Thread recvThread;
	
	public static String FILE_NAME = "data.txt";
	
	public static void main(String[] args) {
		try {
			//���� ���� ����
			serverSocket = new ServerSocket(1515);
			startServer();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void startServer() {
		System.out.println("Server start.");
		
		initClientList();
		
		//client�� ����ؼ� accept�ؼ� clientList�� �־��ִ� ������
		acceptThread = new Thread(new AcceptThreadRunnable(serverSocket));
		acceptThread.start();
		
		//client�� ��û�� �޾ƿ��� ������
		recvThread = new Thread(new RecvThreadRunnable(clientList));
		recvThread.start();
	}
	
	private static void initClientList() {
		if(clientList != null) { //�̹� clientList�� null�� �ƴϸ� return�ؼ� ����
			return;
		}
		
		clientList = new ArrayList<Socket>();
	}
	
	public static boolean isClientListNull() {
		if(clientList == null) {
			return true;
		} else {
			return false;
		}
	}
	
	//clientList�� client�� �߰��ϴ� �Լ�
	public static void addClient(Socket s) {
		if(isClientListNull()) {
			return;
		}
		
		clientList.add(s);
	}

	//Ŭ���̾�Ʈ���� object�� ������ (client�� getObject�� �ϸ� �� �Լ��� ���ؼ� object ����
	public static void sendObject2Client(Socket socket, Object object) {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			
			oos.writeObject(object);
			oos.flush();
//			oos.close(); //close�ϸ� socket�� close �Ǿ������
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static Object getFileContent() {
		try {
			File f = new File(Server.FILE_NAME);
			if(f.exists()) {
				System.out.println("file exists!!");
			} else {
				System.out.println("file not exists!!!");
				f.createNewFile();
				
				return null;
			}
			
			FileInputStream fis = new FileInputStream(Server.FILE_NAME);
			ObjectInputStream ois = new ObjectInputStream(fis);
			return ois.readObject();
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static void setFileContent(Object object) {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(Server.FILE_NAME));
			oos.writeObject(object);
			oos.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}

class AcceptThreadRunnable implements Runnable {
	ServerSocket serverSocket;
	
	public AcceptThreadRunnable(ServerSocket serverSocket) {
		this.serverSocket = serverSocket;
	}

	@Override
	public void run() {
		try {
			while(true) {
				Socket s = serverSocket.accept();
				Server.addClient(s);
				System.out.println("Accpeted a client");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

class RecvThreadRunnable implements Runnable {
	ArrayList<Socket> clientList;
	
	public RecvThreadRunnable(ArrayList<Socket> clientList) {
		this.clientList = clientList;
	}
	
	@Override
	public void run() {
		while(true) {
			
			try {
				Thread.sleep(50);
//				System.out.println(clientList.size() + "");
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			for(int i = 0; i < clientList.size(); i++) {
				Socket socket = clientList.get(i);
				
				if(socket.isClosed()) { //���� ���� ���� ������ ����Ʈ���� ��
					System.out.println(i + "��° ������ close�Ǿ����ϴ�.");
					clientList.remove(i);
					break;
				}
				
				try {
					ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
					String type = (String)ois.readObject();
					
					if(type.equals("get")) { //get ��û�� ���
						System.out.println("get is required");
						
						Object content = Server.getFileContent();
						Server.sendObject2Client(socket, content);
						
						if(content != null) {
							System.out.println("sended data.txt content to client: " + content.toString());
						}
					} else if(type.equals("set")) { //set ��û�� ���
						Object o = ois.readObject();
						
						System.out.println("set is required");
						
						Server.setFileContent(o);
						System.out.println("file saved");
					} else {
						System.out.println("strange request is arrived from client (not get and not set)");
					}
				} catch(EOFException | SocketException e) {
					//Ŭ���̾�Ʈ ���� ���ڱ� ���� �Ǿ��� ��
					clientList.remove(i);
				} catch (IOException | ClassNotFoundException e) { //��¥ ����
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}
	
}