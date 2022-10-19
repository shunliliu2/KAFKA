package _4pd.controller.Test7;

import org.smslib.AGateway;
import org.smslib.IOutboundMessageNotification;
import org.smslib.Library;
import org.smslib.OutboundMessage;
import org.smslib.Service;
import org.smslib.AGateway.Protocols;
import org.smslib.Message.MessageEncodings;
import org.smslib.modem.SerialModemGateway;

public class SendMessage {
	public void doIt() throws Exception {
		OutboundNotification outboundNotification = new OutboundNotification();
		System.out.println("Example: Send message from a serial gsm modem.");
		System.out.println(Library.getLibraryDescription());
		System.out.println("Version: " + Library.getLibraryVersion());
		SerialModemGateway gateway = new SerialModemGateway("modem.com7", "COM7", 115200, "WAVECOM", "17254");
		gateway.setInbound(true);
		gateway.setOutbound(true);
		gateway.setSimPin("1234");
		gateway.setProtocol(Protocols.PDU);
		// Explicit SMSC address set is required for some modems.
		// Below is for VODAFONE GREECE - be sure to set your own!
//		gateway.setSmscNumber("+15605929999");	这个值不能设置，否则信息发送不出去(SMSCNUMBER(短消息中心号码))
		Service.getInstance().setOutboundMessageNotification(outboundNotification);
		Service.getInstance().addGateway(gateway);
		Service.getInstance().startService();
		System.out.println();
		System.out.println("Modem Information:");
		System.out.println("  Manufacturer: " + gateway.getManufacturer());
		System.out.println("  Model: " + gateway.getModel());
		System.out.println("  Serial No: " + gateway.getSerialNo());
		System.out.println("  SIM IMSI: " + gateway.getImsi());
		System.out.println("  Signal Level: " + gateway.getSignalLevel() + " dBm");
		System.out.println("  Battery Level: " + gateway.getBatteryLevel() + "%");
		System.out.println();
		// Send a message synchronously.
		OutboundMessage msg = new OutboundMessage("17538059859", "Hello World from SMSLib!");
		msg.setEncoding(MessageEncodings.ENCUCS2); // 中文
		Service.getInstance().sendMessage(msg);
		System.out.println(msg);
		// Or, send out a WAP SI message.
		// OutboundWapSIMessage wapMsg = new OutboundWapSIMessage("306974000000", new URL("http://www.smslib.org/"), "Visit SMSLib now!");
		// Service.getInstance().sendMessage(wapMsg);
		// System.out.println(wapMsg);
		// You can also queue some asynchronous messages to see how the callbacks
		// are called...
		// msg = new OutboundMessage("309999999999", "Wrong number!");
		// srv.queueMessage(msg, gateway.getGatewayId());
		// msg = new OutboundMessage("308888888888", "Wrong number!");
		// srv.queueMessage(msg, gateway.getGatewayId());
		System.out.println("Now Sleeping - Hit <enter> to terminate.");
		System.in.read();
		Service.getInstance().stopService();
	}

	public class OutboundNotification implements IOutboundMessageNotification {
		@Override
		public void process(AGateway gateway, OutboundMessage msg) {
			System.out.println("Outbound handler called from Gateway: " + gateway.getGatewayId());
			System.out.println(msg);
		}


	}

	public static void main(String args[]) {
		SendMessage app = new SendMessage();
		try {
			app.doIt();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}