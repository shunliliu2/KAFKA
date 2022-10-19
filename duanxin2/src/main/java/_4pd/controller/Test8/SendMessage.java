package _4pd.controller.Test8;
 
import org.smslib.AGateway;
import org.smslib.GatewayException;
import org.smslib.IOutboundMessageNotification;
import org.smslib.OutboundMessage;
import org.smslib.Service;
import org.smslib.Message.MessageEncodings;
import org.smslib.modem.SerialModemGateway;
 
public class SendMessage {
	public class OutboundNotification implements IOutboundMessageNotification {
		@Override
		public void process(AGateway agateway, OutboundMessage outboundmessage) {
			System.out.println("Outbound handler called from Gateway: " + agateway);
			System.out.println(outboundmessage);
			
		}
	}
 
	@SuppressWarnings("deprecation")
	public void sendSMS(String mobilePhones, String content) throws GatewayException {
		Service srv;
		OutboundMessage msg;
		OutboundNotification outboundNotification = new OutboundNotification();
//		srv = new Service();
		srv = Service.getInstance();
		SerialModemGateway gateway = new SerialModemGateway("modem.com3", "COM3", 115200, "wavecom", "17255"); // 设置端口与波特率
		gateway.setInbound(true);
		gateway.setOutbound(true);
		gateway.setSimPin("1234");
//		gateway.setOutboundNotification(outboundNotification);
		srv.setOutboundMessageNotification(outboundNotification);
		srv.addGateway(gateway);
		System.out.println("初始化成功，准备开启服务");
		try {
			srv.startService();
			System.out.println("服务启动成功");
			String[] phones = mobilePhones.split(",");
			for (int i = 0; i < phones.length; i++) {
				msg = new OutboundMessage(phones[i], content);
				msg.setEncoding(MessageEncodings.ENCUCS2); // 中文
				srv.sendMessage(msg);
			}
			srv.stopService();
                        srv.removeGateway(gateway);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
 
	public static void main(String[] args) throws GatewayException {
		SendMessage sendMessage = new SendMessage();
		sendMessage.sendSMS("17538059859", "短信内容");
	}
}

