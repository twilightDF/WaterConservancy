package org.jetlinks.protocol.message.upstream;

import org.jetlinks.core.device.DeviceOperator;
import org.jetlinks.core.message.DeviceMessage;
import org.jetlinks.core.message.property.ReportPropertyMessage;
import org.jetlinks.core.utils.BytesUtils;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;


public class HeartBeatMessage extends WaterConservancyMessage {

    private int time;
    public static HeartBeatMessage decode(byte[] data) {
        return new HeartBeatMessage().from(data);
    }

    private HeartBeatMessage from(byte[] data) {
        int offset = 0;
        super.setSerial(BytesUtils.leToInt(data,offset,2));
        offset += 2;
        time = BytesUtils.beToInt(data,offset,6);
        return this;
    }

    @Override
    public Publisher<? extends DeviceMessage> toDeviceMessage(DeviceOperator operator) {
        ReportPropertyMessage message = new ReportPropertyMessage();
        message.setDeviceId(String.valueOf(super.getDeviceId()));
        message.setTimestamp(System.currentTimeMillis());

        Map<String, Object> map = new HashMap<>();
        map.put("serial",super.getSerial());
        map.put("time",time);

        message.setProperties(map);
        return Mono.just(message);
    }

    @Override
    public byte[] askPayload() {
        return null;
    }
}
