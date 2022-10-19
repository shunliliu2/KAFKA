package _4pd.Test;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Random;

import javax.comm.CommPortIdentifier;
import javax.comm.NoSuchPortException;
import javax.comm.PortInUseException;
import javax.comm.SerialPort;
import javax.comm.UnsupportedCommOperationException;
/***
 * ���ڲ���ʵ����
 * @author hzy
 *
 */
public class Port {
	private CommPortIdentifier portId;
	private SerialPort serialPort;
	private OutputStreamWriter out;
	private InputStreamReader in;
	private String COMname;
	private static char symbol1 = 13;
	
	public String getCOMname() {
		return COMname;
	}
	public void setCOMname(String mname) {
		COMname = mname;
	}
	public CommPortIdentifier getPortId() {
		return portId;
	}

	public void setPortId(CommPortIdentifier portId) {
		this.portId = portId;
	}

	
	public SerialPort getSerialPort() {
		return serialPort;
	}

	public void setSerialPort(SerialPort serialPort) {
		this.serialPort = serialPort;
	}

	public OutputStreamWriter getOut() {
		return out;
	}

	public void setOut(OutputStreamWriter out) {
		this.out = out;
	}
	public InputStreamReader getIn() {
		return in;
	}

	public void setIn(InputStreamReader in) {
		this.in = in;
	}
	public boolean isused =true;
	
	public boolean isIsused() {
		return isused;
	}

	public void setIsused(boolean isused) {
		this.isused = isused;
	}
	/**
	 * ��com�� 
	 * @param portName
	 * @return
	 */		
	public  Port(String portName) {
		try {
			portId = CommPortIdentifier.getPortIdentifier(portName);
			if (portId == null) {
				System.out.println("port is null");
			}
			try {
				serialPort = (SerialPort) portId.open(getrechargeablePassword(),20000);
			} catch (PortInUseException e) {
				System.gc();
				e.printStackTrace();
			}
			// �����ǵõ����ں�COM��ͨѶ�����롢�������
			try {
				in = new InputStreamReader(serialPort.getInputStream());
				out = new OutputStreamWriter(serialPort.getOutputStream());
			} catch (IOException e) {
				System.gc();
				System.out.println("IOException");
			}
			// �����ǳ�ʼ��COM�ڵĴ���������紫�����ʣ�9600�ȡ�
			try {
				serialPort.setSerialPortParams(9600, SerialPort.DATABITS_8,
						SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
				setCOMname(portId.getName());
				setIsused(true);				
			} catch (UnsupportedCommOperationException e) {
				e.printStackTrace();
				System.gc();
			}

		} catch (NoSuchPortException e) {
			e.printStackTrace();
			System.gc();
		}		
	}
	//��ȡ����
	public static String getrechargeablePassword() {
		Random random = new Random();
		char[] codeSequence = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I',
				'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U',
				'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6',
				'7', '8', '9', 'a', 'c', 'b', 'd', 'f', 'e', 'g', 'h', 'j',
				'i', 'l', 'k', 'n', 'm', 'o', 'p', 'q', 'r', 's', 't', 'u',
				'w', 'v' };
		String strRand = "";
		for (int i = 0; i < 18; i++) {
			strRand = strRand
					+ String.valueOf(codeSequence[random.nextInt(59)]);
		}
		return strRand;
	}
	/**
	 * ���SIM�Ƿ����
	 * @return
	 */
	public  boolean chakanPort() {
		try {
			String 	atCommand = "AT+ccid";
			String	strReturn = sendAT(atCommand);
			if (strReturn.indexOf("OK", 0) != -1) {		
				return true;
			}
			return false;
		} catch (Exception ex) {
			System.gc();
			ex.printStackTrace();
			return false;
		}
	}
	/**
	 * �ر�COM��
	 * @return boolean
	 */
	public void close() {
		try {
			in.close();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		serialPort.close();	
		System.gc();
		setIsused(false);		
	}

	/**
	 * �򴮿���д���ַ�������
	 * @param s �ַ�������
	 * @throws Exception �쳣
	 */
	public  void writeln(String s) throws Exception {
		out.write(s);
		out.write('\r');
		out.flush();
	}

	/**
	 * ��ȡCOM����ķ����ַ���
	 * @return ����ַ���
	 * @throws Exception
	 */
	public  String read() throws Exception {
		int n, i;
		char c;
		String answer = "";
		for (i = 0; i < 100; i++) {
			while (in.ready()) {
				n = in.read();
				if (n != -1) {
					c = (char) n;
					answer = answer + c;
					Thread.sleep(1);
				} else {
					break;
				}
			}
			if (answer.indexOf("OK") != -1) {
				break;
			}
			Thread.sleep(100);
		}
		return answer;
	}

	/**
	 * �򴮿ڷ���ATָ��
	 * @param atcommand ָ������
	 * @return ָ��ؽ��
	 * @throws java.rmi.RemoteException
	 */
	public String sendAT(String atcommand) throws java.rmi.RemoteException {
		String s = "";
		try {
			Thread.sleep(100);
			writeln(atcommand);
			Thread.sleep(80);
			s = read();
			Thread.sleep(150);
		} catch (Exception e) {
			System.gc();
			System.out.println("ERROR: send AT command failed; " + "Command: "
					+ atcommand + "; Answer: " + s + "  " + e);
		}
		return s;
	}
}
