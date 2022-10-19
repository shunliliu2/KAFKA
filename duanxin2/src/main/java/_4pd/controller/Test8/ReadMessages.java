package _4pd.controller.Test8;
 
import java.util.ArrayList;
import java.util.List;
import javax.crypto.spec.SecretKeySpec;
import org.smslib.AGateway;
import org.smslib.ICallNotification;
import org.smslib.IGatewayStatusNotification;
import org.smslib.IInboundMessageNotification;
import org.smslib.IOrphanedMessageNotification;
import org.smslib.InboundMessage;
import org.smslib.Library;
import org.smslib.Service;
import org.smslib.AGateway.GatewayStatuses;
import org.smslib.AGateway.Protocols;
import org.smslib.InboundMessage.MessageClasses;
import org.smslib.Message.MessageTypes;
import org.smslib.crypto.AESKey;
import org.smslib.modem.SerialModemGateway;
 
public class ReadMessages {
	public static Service srv = Service.getInstance();
 
	public void doIt() throws Exception {
		List<InboundMessage> msgList;
		InboundNotification inboundNotification = new InboundNotification();
		CallNotification callNotification = new CallNotification();
		GatewayStatusNotification statusNotification = new GatewayStatusNotification();
		OrphanedMessageNotification orphanedMessageNotification = new OrphanedMessageNotification();
		try {
			System.out.println("Example: Read messages from a serial gsm modem.");
			System.out.println(Library.getLibraryDescription());
			System.out.println("Version: " + Library.getLibraryVersion());
			SerialModemGateway gateway = new SerialModemGateway("modem.com3", "COM3", 115200, null, null);
			gateway.setProtocol(Protocols.PDU);
			gateway.setInbound(true);
			gateway.setOutbound(true);
			srv.setInboundMessageNotification(inboundNotification);
			srv.setCallNotification(callNotification);
			srv.setGatewayStatusNotification(statusNotification);
			srv.setOrphanedMessageNotification(orphanedMessageNotification);
			srv.addGateway(gateway);
			srv.startService();
			System.out.println();
			System.out.println("Modem Information:");
			System.out.println(" Manufacturer: " + gateway.getManufacturer());
			System.out.println(" Model: " + gateway.getModel());
			System.out.println(" Serial No: " + gateway.getSerialNo());
			System.out.println(" SIM IMSI: " + gateway.getImsi());
			System.out.println(" Signal Level: " + gateway.getSignalLevel() + "%");
			System.out.println(" Battery Level: " + gateway.getBatteryLevel() + "%");
			System.out.println();
			srv.getKeyManager().registerKey("+8613808080808", new AESKey(new SecretKeySpec("0011223344556677".getBytes(), "AES")));
			msgList = new ArrayList<InboundMessage>();
			srv.readMessages(msgList, MessageClasses.ALL);
			for (InboundMessage msg : msgList) {
				System.out.println(msg);
//				srv.deleteMessage(msg);		//删除短信
			}
			System.out.println("Now Sleeping - Hit <enter> to stop service.");
			System.in.read();
			System.in.read();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
 
	public class InboundNotification implements IInboundMessageNotification {
		@Override
		public void process(AGateway gateway, MessageTypes msgType, InboundMessage msg) {
			if (msgType == MessageTypes.INBOUND)
			{System.out.println(">>> New Inbound message detected from Gateway: " + gateway.getGatewayId());}
			else if (msgType == MessageTypes.STATUSREPORT)
			{System.out.println(">>> New Inbound Status Report message detected from Gateway: " + gateway.getGatewayId());}
			System.out.println(msg);
		}
	}
 
	public class CallNotification implements ICallNotification {
		@Override
		public void process(AGateway gateway, String callerId) {
			System.out.println(">>> New call detected from Gateway: " + gateway.getGatewayId() + " : " + callerId);
		}
	}
 
	public class GatewayStatusNotification implements IGatewayStatusNotification {
		@Override
		public void process(AGateway gateway, GatewayStatuses oldStatus, GatewayStatuses newStatus) {
			System.out.println(">>> Gateway Status change for " + gateway.getGatewayId() + ", OLD: " + oldStatus + " -> NEW: " + newStatus);
		}
	}
 
	public class OrphanedMessageNotification implements IOrphanedMessageNotification {
		@Override
		public boolean process(AGateway gateway, InboundMessage msg) {
			System.out.println(">>> Orphaned message part detected from " + gateway.getGatewayId());
			System.out.println(msg);
			return false;
		}
	}
 
	public static void main(String args[]) {
		ReadMessages app = new ReadMessages();
		try {
			app.doIt();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

