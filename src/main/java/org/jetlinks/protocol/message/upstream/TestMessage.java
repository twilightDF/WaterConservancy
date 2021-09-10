package org.jetlinks.protocol.message.upstream;

import lombok.extern.slf4j.Slf4j;
import org.jetlinks.core.device.DeviceOperator;
import org.jetlinks.core.message.DeviceMessage;
import org.jetlinks.core.utils.BytesUtils;
import org.reactivestreams.Publisher;

@Slf4j
public class TestMessage extends WaterConservancyMessage {
    private int time;//发报时间
    private int addressFlag;//地址标识符
    private int telemetryStationAddress;//遥测站地址
    private int telemetryTypeCode;//遥测站分类码
    private int observationTimeFlag;//观测时间标识符
    private int observationTime;//观测时间
    private int realTimePrecipitationFlag;//当前降水量标识符
    private int realTimePrecipitation;//当前降水量
    private int totalPrecipitationFlag;//降水量累计值标识符
    private int totalPrecipitation;//降水量累计值
    private int realTimeLevelFlag;//瞬时水位标识符
    private int realTimeLevel;//瞬时水位
    private int otherElement;//其他要素
    private int voltageFlag;//电压标识符
    private int voltage;//电压
    public static TestMessage decode(byte[] data) {
        return new TestMessage().from(data);
    }

    private TestMessage from(byte[] data) {
        int offset = 0;
        super.setSerial(BytesUtils.leToInt(data,offset,2));
        offset += 2;
        time = BytesUtils.leToInt(data,offset,6);
        offset += 6;
        addressFlag = BytesUtils.leToInt(data,offset,1);
        offset += 1;
        telemetryStationAddress = BytesUtils.leToInt(data,offset,5);
        offset += 5;
        telemetryTypeCode = BytesUtils.leToInt(data,offset,1);
        offset += 1;
        observationTimeFlag = BytesUtils.leToInt(data,offset,1);
        offset += 1;
        observationTime = BytesUtils.leToInt(data,offset,5);
        offset += 5;
        realTimePrecipitationFlag = BytesUtils.leToInt(data,offset,1);
        offset += 1;
        realTimePrecipitation = BytesUtils.leToInt(data,offset,3);
        offset += 3;
        totalPrecipitationFlag = BytesUtils.leToInt(data,offset,1);
        offset += 1;
        totalPrecipitation = BytesUtils.leToInt(data,offset,3);
        offset += 3;
        realTimeLevelFlag = BytesUtils.leToInt(data,offset,1);
        offset += 1;
        realTimeLevel = BytesUtils.beToInt(data,offset,4);
        offset += 4;
        voltageFlag = BytesUtils.beToInt(data,offset,1);
        offset += 1;
        voltage = BytesUtils.beToInt(data,offset,2);
        return this;
    }

    @Override
    public byte[] askPayload() {
        return super.askPayload();
    }

    @Override
    public Publisher<? extends DeviceMessage> toDeviceMessage(DeviceOperator operator) {
        return null;
    }
}
