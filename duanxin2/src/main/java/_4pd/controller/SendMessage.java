// SendMessage.java - Sample application.
//
// This application shows you the basic procedure for sending messages.
// You will find how to send synchronous and asynchronous messages.
//
// For asynchronous dispatch, the example application sets a callback
// notification, to see what's happened with messages.

package _4pd.controller;

import org.smslib.*;
import org.smslib.Message.MessageEncodings;
import org.smslib.modem.SerialModemGateway;

public class SendMessage
{
	private static final Service srv = Service.getInstance();

	public void doIt() throws Exception

	{
		//System.out.println(System.getProperty('java.library.path'));
		System.loadLibrary("win32com.dll");
		Service srv;
		OutboundMessage msg;
		OutboundNotification outboundNotification = new OutboundNotification();
		System.out.println("Example: Send message from a serial gsm modem.");
		System.out.println(Library.getLibraryDescription());
		System.out.println("Version: " + Library.getLibraryVersion());
		srv =  Service.getInstance();

		SerialModemGateway gateway = new SerialModemGateway("modem.com1", "COM1", 115200, "wavecom", "17254");//115200�ǲ����ʣ�һ��Ϊ9600������ͨ�������ն˲��Գ���
		gateway.setInbound(true);
		gateway.setOutbound(true);
		gateway.setSimPin("0000");
		//gateway.setOutboundNotification(outboundNotification);
		srv.addGateway(gateway);
		srv.startService();
		System.out.println("Modem Information:");
		System.out.println("  Manufacturer: " + gateway.getManufacturer());
		System.out.println("  Model: " + gateway.getModel());
		System.out.println("  Serial No: " + gateway.getSerialNo());
		System.out.println("  SIM IMSI: " + gateway.getImsi());
		System.out.println("  Signal Level: " + gateway.getSignalLevel() + "%");
		System.out.println("  Battery Level: " + gateway.getBatteryLevel() + "%");
		System.out.println();
		// Send a message synchronously.
		
		msg = new OutboundMessage("13152195134", "�������java�������Ķ���!");
		msg.setEncoding(MessageEncodings.ENCUCS2);//��仰�Ƿ����Ķ��ű����
		srv.sendMessage(msg);
		System.out.println(msg);
		System.out.println("Now Sleeping - Hit <enter> to terminate.");
		System.in.read();
		srv.stopService();
	}

	public class OutboundNotification implements IOutboundMessageNotification
	{
		@Override
		public void process(AGateway gatewayId, OutboundMessage msg)
		{
			System.out.println("Outbound handler called from Gateway: " + gatewayId);
			System.out.println(msg);
		}


	}

	public static void main(String args[])
	{
		SendMessage app = new SendMessage();
		try
		{
			app.doIt();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
