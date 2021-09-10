package org.jetlinks.protocol.message.upstream;

import lombok.Getter;
import lombok.Setter;
import org.jetlinks.core.device.DeviceOperator;
import org.jetlinks.core.message.DeviceMessage;
import org.jetlinks.core.utils.BytesUtils;
import org.reactivestreams.Publisher;

@Setter
@Getter
public abstract class WaterConservancyMessage {
    private int deviceId;
    private int serial;//流水号
    private int password;//密码
    private int functionCode;//功能码

    public abstract Publisher<? extends DeviceMessage> toDeviceMessage(DeviceOperator operator);

    public byte[] askPayload(){

        int offset = 0;
        byte[] payload = new byte[17 + 8];

        payload[offset] = 126;
        offset += 1;
        payload[offset] = 126;//帧起始符
        offset += 1;
        payload[offset] = 2;//中心站地址
        offset += 1;
        BytesUtils.numberToLe(payload,deviceId,offset,5);//遥测站地址
        offset += 5;
        BytesUtils.numberToLe(payload,password,offset,2);//密码
        offset += 2;
        BytesUtils.numberToLe(payload,functionCode,offset,1);//功能码
        offset += 1;
        BytesUtils.numberToLe(payload, 32776, offset, 2);//报文下行标识及长度8008(1000 000000001000)
        offset += 2;
        payload[offset] = 2;//报文起始符
        offset += 1;
        BytesUtils.numberToLe(payload,serial,offset,2);//流水号
        offset += 2;
        //TODO继续构建通用回复包









        return payload;
    }
}
