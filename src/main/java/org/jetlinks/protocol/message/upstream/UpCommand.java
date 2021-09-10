package org.jetlinks.protocol.message.upstream;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.function.Function;


@AllArgsConstructor
@Getter
public enum UpCommand {
    HeartBeat(47,HeartBeatMessage::decode),//心跳报-2F
    Test(48,TestMessage::decode),//测试报-30

    ;


    private int code;//类型值
    private Function<byte[],? extends WaterConservancyMessage> decoder;

    public static UpCommand of(int functionCode) {
        for (UpCommand uc : values()) {
            if (uc.code == functionCode)
                return uc;
        }
        throw new UnsupportedOperationException("未识别的上行命令类型" + functionCode);
    }
}
