package _4pd.Test;

import java.util.ArrayList;
import java.util.List;

/***
 * ����è������
 * ��������è�Ĵ򿪡��رա���ȡ�Ȳ���
 * @author hzy
 *
 */
public class Sms{

	private CommonSms commonsms;
	private static char symbol1 = 13;
	private static String strReturn = "", atCommand = "";

	public boolean SendSms(Port myport) {
		if(!myport.isIsused())
		{		
		System.out.println("COMͨѶ�˿�δ������!");		
		return false;
		}
		setMessageMode(myport,1);
		// �ո�
		char symbol2 = 34;
		// ctrl~z ����ָ��
		char symbol3 = 26;
		try {
			atCommand = "AT+CSMP=17,169,0,08" + String.valueOf(symbol1);
			strReturn = myport.sendAT(atCommand);
			System.out.println(strReturn);
			if (strReturn.indexOf("OK", 0) != -1) {
				atCommand = "AT+CMGS=" + commonsms.getRecver()
						+ String.valueOf(symbol1);
				strReturn = myport.sendAT(atCommand);
				atCommand = StringUtil.encodeHex(commonsms.getSmstext().trim())
						+ String.valueOf(symbol3) + String.valueOf(symbol1);
				strReturn = myport.sendAT(atCommand);
//				System.out.println("+++++++++++++++++++++++++++++++++++++");
//				System.out.println(atCommand+"++++++++++++++++++++"+commonsms.getSmstext().trim()+"+++++++++++++++++"+commonsms.getSmstext().trim().length());
//				System.out.println("+++++++++++++++++++++++++++++++++++++");
				if (strReturn.indexOf("OK") != -1
						&& strReturn.indexOf("+CMGS") != -1) {
					System.out.println("���ŷ��ͳɹ�...");
					return true;
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();			
			System.out.println("���ŷ���ʧ��...");		
			return false;
		}	
		System.out.println("���ŷ���ʧ��...");	
		return false;
	}
	/**
	 * ������Ϣģʽ 
	 * @param op
	 * 0-pdu 1-text(Ĭ��1 �ı���ʽ )
	 * @return
	 */
	public boolean setMessageMode(Port myport,int op) {
		try {
			String atCommand = "AT+CMGF=" + String.valueOf(op)
					+ String.valueOf(symbol1);
			String 	strReturn = myport.sendAT(atCommand);
			if (strReturn.indexOf("OK", 0) != -1) {
				System.out.println("*************�ı���ʽ���óɹ�************");
				return true;
			}
			return false;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}	
	
	/**
	* ��ȡ���ж��� 
	* @return CommonSms����
	*/
	public List<CommonSms> RecvSmsList(Port myport) {
		if(!myport.isIsused())
		{
			System.out.println("System Message:  COMͨѶ�˿�δ������!");		
			return null;
		}
		List<CommonSms> listMes = new ArrayList<CommonSms>();
		try {
			atCommand = "AT+CMGL=\"ALL\"";
			strReturn = myport.sendAT(atCommand);
			listMes = StringUtil.analyseArraySMS(strReturn);			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return listMes;
	}

	
	/**
	 * ɾ������
	 * @param index ���Ŵ洢��λ��
	 * @return boolean
	 */

	public boolean DeleteSMS(int index,Port myport) {
		if(!myport.isIsused()){
			System.out.println("System Message:  COMͨѶ�˿�δ������!");
			return false;
		}
		try {
			atCommand = "AT+CMGD=" + index;
			strReturn = myport.sendAT(atCommand);
			if (strReturn.indexOf("OK") != -1) {
				System.out.println("System Message:  �ɹ�ɾ���洢λ��Ϊ" + index
						+ "�Ķ���......");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return true;
	}
	
	/**
	 * ɾ������è�����ж���
	 * @return boolean
	 */
	public boolean DeleteAllSMS(Port myport)
	{
		List list=RecvSmsList(myport);
		boolean ret=true;
		if(list!=null&&!list.equals("")&&list.size()>0)
		{		
		for(int i=0;i<list.size();i++)
		{
			CommonSms tempcomsms=(CommonSms)list.get(i);
			if(!DeleteSMS(tempcomsms.getId(),myport))
			{
				ret=false;
			}
		}
		}
		return ret;
	}
	public CommonSms getCommonsms() {
		return commonsms;
	}

	public void setCommonsms(CommonSms commonsms) {
		this.commonsms = commonsms;
	}
	/**
	 * ���룬���ݣ����Ͷ���Ϣ
	 * @param phone
	 * @param countstring
	 * @throws Exception
	 */
	public static void sendmsn(String phone,String countstring){
		 Sms s = new Sms();
		  // ���Ͳ���		
		  CommonSms cs=new CommonSms();
		  cs.setRecver(phone);
		  cs.setSmstext(countstring);
		  s.setCommonsms(cs);
		  Port myort=new Port("COM4");
		  System.out.println(myort.isIsused()+"     "+myort.getCOMname());
		 s.SendSms(myort);	
		 // s.RecvSmsList(myort);
		 // s.DeleteSMS(3,myort);
		 s.DeleteAllSMS(myort);
		  myort.close();
	}
	
	public static void main(String[] args) throws Exception {
		sendmsn("17538059859","����һ���Ը����ȴ�Ȳ���һ������,�����۾����ҿ������ҵ�ǰ;");
		
		
	}	
}