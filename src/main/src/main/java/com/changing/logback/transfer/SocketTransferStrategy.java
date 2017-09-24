package com.changing.logback.transfer;

import com.changing.logback.config.SocketConfig;
import com.changing.model.Message;
import com.changing.util.Constants;
import com.changing.util.ObjectStreamUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * @Description : 通过长连接来进行通信
 * @Author : wuchangqing
 * @Date : 2017/9/22
 */
public class SocketTransferStrategy implements TransferStrategy {

    private static final Logger LOGGER = LoggerFactory.getLogger(SocketTransferStrategy.class);

    private SocketConfig socketConfig;

    /**
     * 用于通信的客户端socket
     */
    private Socket socket;

    private DataOutputStream outputStream;



    @Override
    public void connect() {
        try {
            socket = new Socket(socketConfig.getHost() , socketConfig.getPort());
        } catch (IOException e) {
            LOGGER.error("connect error", e);
        }
    }

    /**
     * 获取输出流，如果为null则重新获取
     */
    public DataOutputStream getOutPutStream(){
        int retryCount = 0;
        try {
            while(outputStream == null && retryCount++ < 3){
                if(socket == null || socket.isClosed()){
                    socket = new Socket(socketConfig.getHost() , socketConfig.getPort());
                    outputStream = new DataOutputStream(socket.getOutputStream());
                }else{
                    outputStream = new DataOutputStream(socket.getOutputStream());
                }
            }
        } catch (IOException e) {
            LOGGER.error("getOutPutStream error", e);
        }
        return outputStream;
    }

    @Override
    public void writeData(Message message) {
        //TODO

        try {
            byte[] bytes = ObjectStreamUtil.writeClassAndObject(message);
            // 先简单的使用流处理，后期可以完善为自定义消息类型，服务器端按位进行获取。
            DataOutputStream outputStream = getOutPutStream();

            if(outputStream != null){
                //确保写出去对方可以完全读取到， 总长度 + 类型 + 具体数据
                outputStream.writeInt(4 + 1 + bytes.length);
                outputStream.writeByte(Constants.DataType.TYPE_LOG);
                outputStream.write(bytes);
                outputStream.flush();
            }else{
                //TODO  如果流为null
            }
        }catch (IOException e) {
            LOGGER.error("SocketTransferStrategy writeData error" , e);
        }
    }

    @Override
    public void close() {
        try {
            getOutPutStream().close();
            socket.close();
        } catch (IOException e) {
            LOGGER.error("SocketTransferStrategy close error" , e);
        }
    }

    public SocketTransferStrategy() {
    }

    public SocketTransferStrategy(SocketConfig socketConfig) {
        this.socketConfig = socketConfig;
    }
}
