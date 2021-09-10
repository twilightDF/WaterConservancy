package org.jetlinks.protocol.message.upstream;

import org.jetlinks.core.utils.BytesUtils;

public class DeviceMessageDecoder {
    public static WaterConservancyMessage decode(byte[] payload) {
        //跳过帧起始符
        int offset = 2;
        int centerAddress = BytesUtils.leToInt(payload,offset,1);//中心站地址
        offset += 1;
        int telemetryStationAddress= BytesUtils.leToInt(payload,offset,5);//遥测站地址(设备唯一识别码)
        offset += 5;
        int password = BytesUtils.leToInt(payload,offset,2);//密码
        offset += 2;
        int functionCode = BytesUtils.leToInt(payload,offset,1);//功能码
        offset += 1;
        int length = BytesUtils.leToInt(payload,offset,2);//报文上下行标识及长度
        offset += 2;
        BytesUtils.leToInt(payload,offset,1);//报文起始符
        offset += 1;
        byte[] data = new byte[payload.length - 17];//报文正文
        System.arraycopy(payload,offset,data,0,payload.length - 17);
        BytesUtils.leToInt(payload,payload.length - 3,1);//报文结束符
        int crc = BytesUtils.leToInt(payload,payload.length - 2,2);//校验码

        WaterConservancyMessage message = UpCommand.of(functionCode).getDecoder().apply(data);
        message.setDeviceId(telemetryStationAddress);
        message.setPassword(password);
        message.setFunctionCode(functionCode);
        return message;
    }
}
