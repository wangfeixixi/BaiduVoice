package wangfeixixi.com.baiduvoice;

import android.os.Message;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class UDPServer implements Runnable {
    private byte[] msg = new byte[1024];
    private boolean life = true;
    private DatagramSocket dSocket = null;

    public UDPServer() {
        try {
            dSocket = new DatagramSocket(17009);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return the life
     */
    public boolean isServer() {
        return life;
    }

    /**
     * @param lifes the life to set
     */
    public void setServer(boolean lifes) {
        this.life = lifes;
//        LogUtil.e("dSocket.isClosed()show==");
        if (life == false && dSocket != null) {
            if (!dSocket.isClosed()) {
                dSocket.close();
            }
            dSocket.disconnect();
            //                dSocket = null;
        }
    }

    @Override
    public void run() {
        DatagramPacket dPacket = new DatagramPacket(msg, msg.length);
        while (life) {
            try {
                Thread.sleep(5);
                if (dSocket != null) {
                    dSocket.receive(dPacket);
//                    String datas = ByteUtil.byte2HexStr(dPacket.getData(), dPacket.getLength());
                    //将收到的消息发给主界面
                    if (dPacket.getAddress() != null) {
                        Message message = new Message();
//                        message.obj = dPacket.getAddress() + "--" + datas;
                        message.what = 2;
//                        myHandler.sendMessage(message);
                    }
                } else {
                    life = false;
                }
            } catch (IOException e) {
                life = false;
                if (dSocket != null) {
                    if (!dSocket.isClosed()) {
                        dSocket.close();
                    }
                    dSocket.disconnect();
                }
            } catch (InterruptedException e) {
                life = false;
                if (dSocket != null) {
                    if (!dSocket.isClosed()) {
                        dSocket.close();
                    }
                    dSocket.disconnect();
                }
            }
        }
    }
}