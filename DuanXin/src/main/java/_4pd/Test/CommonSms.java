package _4pd.Test;

import java.util.Date;

/***
 * CommonSms ��������ȫ�ֱ��� 
 * @author cms
 *@version 1.9, 09/10/20
 * 
 */
public class CommonSms{

	/** id */
	private int id;
	/**type����ά����Ա����Ӫҵ�����Ͷ���*/
	private int type;//���ָ�ά����Ա���Ǹ�Ӫҵ�����Ͷ���
	/**��������*/
	private String smstext;
	/**���ŷ��ͷ�*/
	private String sender;//���ŷ��ͷ�
	/**���Ž��շ�*/
	private String recver;//���Ž��շ�
	/**ʱ��*/
	private Date date;
	/**��Ϣ״̬*/
	private String state;//��Ϣ״̬
	/**��Ӧ��Ӫҵ������*/
	private String bhname;//��Ӧ��Ӫҵ������

	
	public String getBhname() {
		return bhname;
	}
	public void setBhname(String bhname) {
		this.bhname = bhname;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getSmstext() {
		return smstext;
	}
	public void setSmstext(String smstext) {
		this.smstext = smstext;
	}
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public int getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
		
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getRecver() {
		return recver;
	}
	public void setRecver(String recver) {
		this.recver = recver;
	}
	
}

