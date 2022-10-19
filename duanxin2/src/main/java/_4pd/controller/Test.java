package _4pd.controller;
import java.io.IOException;

import org.smslib.AGateway;
import org.smslib.GatewayException;
import org.smslib.IOutboundMessageNotification;
import org.smslib.Message.MessageEncodings;
import org.smslib.OutboundMessage;
import org.smslib.SMSLibException;
import org.smslib.Service;
import org.smslib.TimeoutException;
import org.smslib.modem.SerialModemGateway;

/**
 * @Project : pmmm
 * @ClassName: MessageUtil
 * @Description: TODO(发送短信帮助类)
 * @author cp
 * @date 2014年11月21日 下午2:28:39
 * @Copyright : Copyright (c) 2014 Wuhan Hongyi Infomation Co., Ltd.
 * @version V1.0
 *
 */
public class Test {
    //private static Logger logger = Logger.getLogger("com/hy/pmmm/common/MessageUtil");

    private static Test instance = new Test();

    /**
     * 启动的发送短信Service,设为静态变量，打开服务后就不关闭，实现连续发短信
     */
    private static Service srv;

    static {
        OutboundNotification outboundNotification = new OutboundNotification();
        srv = Service.getInstance();

        String port = "COM4";
        SerialModemGateway gateway = new SerialModemGateway("modem." + port,
                port, 9600, "Siemens", "MC35i"); // 设置端口与波特率
        gateway.setInbound(true);
        gateway.setOutbound(true);
        gateway.setSimPin("0000");
        Service.getInstance().setOutboundMessageNotification(
                outboundNotification);
        try {
            srv.addGateway(gateway);
            //logger.info("初始化成功，准备开启服务");
            srv.startService();
            //logger.info("服务启动成功");
        } catch (GatewayException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (SMSLibException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public static class OutboundNotification implements IOutboundMessageNotification {

        @Override
        public void process(AGateway gateway, OutboundMessage msg) {
            System.out.println("Outbound handler called from Gateway: "
                    + gateway.getGatewayId());
            System.out.println(msg);
        }


    }

    private void MessageUtil() {

    }

    /**
     * 给短信号码发送相应内容
     *
     * @param phones  发送短信号码数组
     * @param
     * @return
     */
    public static void sendMessage(String[] phones, String message)
            throws Exception {
        instance.sendSmsInfo(phones, message);
        //logger.info("给" + Arrays.toString(phones) + "发送短信,内容为[" + message+ "]");
    }

    /**
     * 给特定短信号码数组发送短信
     *
     * @param phones  短信号码数组
     * @param content 发送短信内容
     */
    private void sendSmsInfo(String[] phones, String content) {
        try {
            OutboundMessage msg;
            boolean isSendSuc;
            for (int i = 0; i < phones.length; i++) {
                msg = new OutboundMessage(phones[i], content);
                msg.setEncoding(MessageEncodings.ENCUCS2); // 中文
                isSendSuc = srv.sendMessage(msg);
                if (isSendSuc) {
                    //logger.info("send " + msg + "success!");
                } else {
                    //logger.info("send " + msg + "fail!");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}


