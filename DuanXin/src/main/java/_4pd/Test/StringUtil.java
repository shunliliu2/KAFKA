package _4pd.Test;

import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Date;

/***
 * ָ���ַ���������
 * @author hzy
 *
 */
public class StringUtil {
	/**
	 * ʹ��Sms ��RecvSms(int index)�ķ���ʱ��ʹ�ø÷�������MODEM���ص��ַ���
	 * ����MODEM���ص��ַ�����������һ��CommonSms����
	 * @param str ���ڷ��صĶ�ȡ���Ž���ַ���
	 * @param index ��������
	 * @return
	 */
	public static CommonSms analyseSMS(String str, int index) {
		CommonSms commonSms = new CommonSms();
		String mesContent;
		String[] s = str.split("\"");
		int len = s.length;
		commonSms.setId(index);
		mesContent = s[len - 1];
		if (mesContent.indexOf("OK") != -1) {
			mesContent = mesContent.substring(0, mesContent.indexOf("OK"));
		}
		mesContent = mesContent.trim();
		commonSms.setSmstext(analyseStr(mesContent));
		// ����������ʱʹ��
		// mes.setMessage(Unicode2GBK(analyseStr(mesContent)));
		SimpleDateFormat df = new SimpleDateFormat("yy/MM/dd hh:mm:ss");
		String datestring = s[len - 2].substring(0, s[len - 2].length() - 3)
				.replace(',', ' ');// ����èʱ���ʽ09/09/09 20:18:01+32
		Date date = null;
		try {
			date = df.parse(datestring);
			System.out.println(date.toLocaleString());
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		commonSms.setDate(date);
		if (s[1].equals("REC READ")) {
			commonSms.setState("�Ѷ�");
		} else {
			commonSms.setState("δ��");
		}
		commonSms.setSender(s[3]);

		return commonSms;
	}

	/**
	 * ʹ��Sms ��RecvSmsList()����ʱ��ͨ���÷�������MODEM���������ַ���
	 * ����MODEM���ص��ַ�����������һ��CommonSms�ļ��϶���
	 * @param str MODEM���ص��ַ���
	 * @return
	 */
	public static List<CommonSms> analyseArraySMS(String str) {
		List<CommonSms> mesList = new ArrayList<CommonSms>();
		CommonSms cs;
		String[] messages;
		String temp;
		String[] t;
		if (str.indexOf("CMGL: ") == -1)
			return null;
		str = str.substring(0, str.indexOf("OK")).trim();
		messages = str.split("\n");
		if (messages.length < 2)
			return null;
		for (int i = 1; i < messages.length; i++) {
			cs = new CommonSms();
			if(messages[i].length()>=messages[i].indexOf("CMGL: ")+6){
				messages[i] = messages[i]
				   					.substring(messages[i].indexOf("CMGL: ") + 6);
			}			
			t = messages[i].split(",");
			if (t.length > 5) {
				cs.setId(Integer.parseInt(t[0].trim()));
				temp = t[1].substring(t[1].indexOf('"') + 1,
						t[1].lastIndexOf('"')).trim();
				if (temp.equals("REC READ")) {
					cs.setState("�Ѷ�");
				} else {
					cs.setState("δ��");
				}
				cs.setSender((t[2].substring(t[2].indexOf('"') + 1, t[2]
						.lastIndexOf('"')).trim()));
				SimpleDateFormat df = new SimpleDateFormat("yy/MM/dd hh:mm:ss");
				String datestring = t[4].substring(t[4].indexOf('"') + 1) + " "
						+ t[5].substring(0, t[5].indexOf('"'));// ����èʱ���ʽ09/09/09
																// 20:18:01+32
				Date date = null;
				try {
					date = df.parse(datestring);
				} catch (Exception ex) {
					System.out.println(ex.getMessage());
				}
				cs.setDate(date);
				i++;
				cs.setSmstext(analyseStr(messages[i].trim()));
				mesList.add(cs);
			}
		}
		return mesList;
	}

	/**
	 * ��PDU�����ʮ�������ַ��� �硰4F60597DFF01�� ת����unicode "\u4F60\u597D\uFF01"
	 * @param str Ҫת�����ַ���
	 * @return ת�����ʮ�������ַ���
	 */
	public static String analyseStr(String str) {
		StringBuffer sb = new StringBuffer();
		if (!(str.length() % 4 == 0))
			return str;
		for (int i = 0; i < str.length(); i++) {
			if (i == 0 || i % 4 == 0) {
				sb.append("\\u");
			}
			sb.append(str.charAt(i));
		}
		return Unicode2GBK(sb.toString());
	}

	/**
	 * ��unicode���� "\u4F60\u597D\uFF01" ת�������� "��ã�"
	 * @param dataStr Ҫת�����ַ���
	 * @return ת����������ַ���
	 */
	public static String Unicode2GBK(String dataStr) {
		int index = 0;
		StringBuffer buffer = new StringBuffer();
		while (index < dataStr.length()) {
			if (!"\\u".equals(dataStr.substring(index, index + 2))) {
				buffer.append(dataStr.charAt(index));
				index++;
				continue;
			}
			String charStr = "";
			charStr = dataStr.substring(index + 2, index + 6);
			char letter = 0;
			try{letter = (char) Integer.parseInt(charStr, 16);}catch (Exception e) {}
			buffer.append(letter);
			index += 6;
		}
		return buffer.toString();
	}

	/**
	 * �������ַ���ת����Unicode
	 * @param str Ҫת���������ַ���
	 * @return ת�����Unicode
	 */
	public static String GBK2Unicode(String str) {

		StringBuffer result = new StringBuffer();

		for (int i = 0; i < str.length(); i++) {

			char chr1 = (char) str.charAt(i);

			if (!isNeedConvert(chr1)) {

				result.append(chr1);

				continue;

			}
			try{result.append("\\u" + Integer.toHexString((int) chr1));}catch (Exception e) {}

		}

		return result.toString();

	}

	/**
	 * �������ַ���ת����Unicode�������ж��Ƿ���Ҫת��
	 * @param para Ҫת�����ַ�
	 * @return boolean
	 */
	public static boolean isNeedConvert(char para) {
		return ((para & (0x00FF)) != para);

	}

	/**
	 * ʹ��Sms �� SendSms()�������Ͷ���ʱ,���ô˷��������������ת����ʮ������
	 * @param msg ��������
	 * @return ת�����ʮ�����ƶ���
	 */
	public static final String encodeHex(String msg) {
		byte[] bytes = null;
		try {
			bytes = msg.getBytes("GBK");
		} catch (java.io.UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		StringBuffer buff = new StringBuffer(bytes.length * 4);
		String b = "";
		char a;
		int n = 0;
		int m = 0;
		for (int i = 0; i < bytes.length; i++) {
			try{b = Integer.toHexString(bytes[i]);}catch (Exception e) {}
			if (bytes[i] > 0) {
				buff.append("00");
				buff.append(b);
				n = n + 1;
			} else {
				a = msg.charAt((i - n) / 2 + n);
				m = a;
				try{b = Integer.toHexString(m);}catch (Exception e) {}
				buff.append(b.substring(0, 4));

				i = i + 1;
			}
		}
		return buff.toString();
	}
}