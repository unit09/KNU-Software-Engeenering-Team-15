package client;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ClientTest {
	Client one = new Client();
	String TestKey = "TestKey";
	String test = "Test";
	String test2 = "Test2";
	
	int testInt = 100;
	
	@Test
	void testGetStringValidKey() {
		one.setString(TestKey, test);
		assertEquals(test, one.getString(TestKey));
	}
	
	@Test
	void testGetStringNullKey() {
		assertEquals(null, one.getString("InValid_Key"));
	}
	
	@Test
	void testGetIntKeyInValidType() {
		//String ����� Key���� Int�� get�Ϸ� �ص� ����ó���� Failure�� ���� ���� ���´�.
		try {
			one.getInt(TestKey);
		}
		catch (Exception e) {
			fail("InVaild type catch");
		}
	}
}
