package _4pd.controller.Test7;

import java.util.ArrayList;
import java.util.List;

import org.smslib.*;
import org.smslib.AGateway.GatewayStatuses;
import org.smslib.AGateway.Protocols;
import org.smslib.InboundMessage.MessageClasses;
import org.smslib.Message.MessageEncodings;
import org.smslib.Message.MessageTypes;
import org.smslib.modem.SerialModemGateway;

public class MessageUtil {
	private static final Service srv = Service.getInstance();
	private static SerialModemGateway gateway;
	
	static {
		new MessageUtil();
	}
	
	public static void main(String args[]) {
		try {
			sendMessage();
//			sendMessage("groupTest", "10010,10086", "测试群发短信，收到请勿回复，谢谢！");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
//			stopService();
		}
	}
	
	public MessageUtil() {
		InboundNotification inboundNotification = new InboundNotification();
		GatewayStatusNotification statusNotification = new GatewayStatusNotification();
		OrphanedMessageNotification orphanedMessageNotification = new OrphanedMessageNotification();
		OutboundNotification outboundNotification = new OutboundNotification();
		gateway = new SerialModemGateway("modem.com3", "COM3", 115200, "WAVECOM", "");
		gateway.setInbound(true);
		gateway.setOutbound(true);
		gateway.setSimPin("1234");
		gateway.setProtocol(Protocols.PDU);
		srv.setInboundMessageNotification(inboundNotification);
		srv.setGatewayStatusNotification(statusNotification);
		srv.setOrphanedMessageNotification(orphanedMessageNotification);
		srv.setOutboundMessageNotification(outboundNotification);
		try {
			srv.addGateway(gateway);
			srv.startService();
		} catch (Exception e) {
			e.printStackTrace();
			stopService();
		}
	}

	
	public static void sendMessage() throws Exception {
		OutboundMessage msg = new OutboundMessage("15605929999", "测试发送中文短信");
		msg.setEncoding(MessageEncodings.ENCUCS2); // 支持中文
		srv.queueMessage(msg);
		System.out.println(msg);
	}
	
	public static void sendMessage(String phoneNums, String content) throws Exception {
		OutboundMessage msg = null;
		String[] nums = phoneNums.split(",");
		for (int i = 0; i < nums.length; i++) {
			if(nums[i]!=null && nums[i].length()>0) {
				String num = nums[i].trim();
				msg = new OutboundMessage(num, content);
				msg.setEncoding(MessageEncodings.ENCUCS2); // 支持中文
				srv.queueMessage(msg);
				System.out.println(msg);
			}
		}
	}

	public static void sendMessage(String groupName, String phoneNums, String content) throws Exception {
		OutboundMessage msg = null;
		srv.createGroup(groupName);
		String[] nums = phoneNums.split(",");
		for (int i = 0; i < nums.length; i++) {
			if(nums[i]!=null && nums[i].length()>0) {
				String num = nums[i].trim();
				srv.addToGroup(groupName, num);
			}
		}
		msg = new OutboundMessage(groupName, content);
		msg.setEncoding(MessageEncodings.ENCUCS2); // 支持中文
		srv.queueMessage(msg);
		System.out.println(msg);
	}
	
	public static void readMessages() throws Exception {
		List<InboundMessage> msgList;
		try {
			msgList = new ArrayList<InboundMessage>();
			srv.readMessages(msgList, MessageClasses.ALL);
			for (InboundMessage msg : msgList) {
				System.out.println(msg);
			}
		} catch (Exception e) {
			e.printStackTrace();
			srv.stopService();
		}
	}
	
	/**
	 * 停止当前服务
	 */
	public static void stopService() {
		try {
			srv.stopService();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 收到短信触发的回调方法
	 * @author ob
	 *
	 */
	public class InboundNotification implements IInboundMessageNotification {
		@Override
		public void process(AGateway gateway, MessageTypes msgType, InboundMessage msg) {
			if (msgType == MessageTypes.INBOUND) {
				System.out.println(">>> New Inbound message detected from Gateway: " + gateway.getGatewayId());
			} else if (msgType == MessageTypes.STATUSREPORT) {
				System.out.println(">>> New Inbound Status Report message detected from Gateway: " + gateway.getGatewayId());
			}
			System.out.println(msg);
		}
	}

	/**
	 * 网关状态改变的通知
	 * @author ob
	 *
	 */
	public class GatewayStatusNotification implements IGatewayStatusNotification {
		@Override
		public void process(AGateway gateway, GatewayStatuses oldStatus, GatewayStatuses newStatus) {
			System.out.println(">>> Gateway Status change for " + gateway.getGatewayId() + ", OLD: " + oldStatus + " -> NEW: " + newStatus);
		}
	}

	/**
	 * 收到不完整短信（截断）触发的回调方法
	 * @author ob
	 *
	 */
	public class OrphanedMessageNotification implements IOrphanedMessageNotification {
		@Override
		public boolean process(AGateway gateway, InboundMessage msg) {
			System.out.println(">>> Orphaned message part detected from " + gateway.getGatewayId());
			System.out.println(msg);
			// Since we are just testing, return FALSE and keep the orphaned message part.
			return false;
		}
	}
	
	/**
	 * 发送短信触发的回调方法
	 * @author ob
	 *
	 */
	public class OutboundNotification implements IOutboundMessageNotification {
		@Override
		public void process(AGateway gateway, OutboundMessage msg) {
			System.out.println("Outbound handler called from Gateway: " + gateway.getGatewayId());
			System.out.println(msg);
		}
	}
}