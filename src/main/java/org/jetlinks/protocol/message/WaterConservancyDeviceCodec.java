package org.jetlinks.protocol.message;

import io.netty.buffer.Unpooled;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Hex;
import org.jetlinks.core.device.DeviceRegistry;
import org.jetlinks.core.message.Message;
import org.jetlinks.core.message.codec.*;
import org.jetlinks.core.server.session.DeviceSession;
import org.jetlinks.protocol.message.upstream.DeviceMessageDecoder;
import org.jetlinks.protocol.message.upstream.WaterConservancyMessage;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;

import javax.annotation.Nonnull;

@Slf4j
public class WaterConservancyDeviceCodec implements DeviceMessageCodec {

    private DeviceRegistry registry;
    public WaterConservancyDeviceCodec(DeviceRegistry registry) {
        this.registry=registry;
    }

    @Override
    public Transport getSupportTransport() {
        return DefaultTransport.TCP;
    }

    @Nonnull
    @Override
    public Publisher<? extends Message> decode(@Nonnull MessageDecodeContext context) {

        EncodedMessage message = context.getMessage();
        FromDeviceMessageContext messageContext = (FromDeviceMessageContext) context;
        DeviceSession session = messageContext.getSession();

        byte[] bytes = message.payloadAsBytes();
        log.warn("收到设备消息[{}]", Hex.encodeHexString(bytes));
        WaterConservancyMessage decode = DeviceMessageDecoder.decode(bytes);

        return registry.getDevice(String.valueOf(decode.getDeviceId()))
                .switchIfEmpty(Mono.fromRunnable(() -> log.error("该设备不存在:{}",decode.getDeviceId())))
                .flatMapMany(device -> {
                    if (decode.askPayload() == null){
                        return decode.toDeviceMessage(device);
                    }else {
                        return session.send(EncodedMessage.simple(Unpooled.wrappedBuffer(decode.askPayload())))
                                .thenMany(decode.toDeviceMessage(device));
                    }
                });
    }

    @Nonnull
    @Override
    public Publisher<? extends EncodedMessage> encode(@Nonnull MessageEncodeContext context) {
        return null;
    }
}
