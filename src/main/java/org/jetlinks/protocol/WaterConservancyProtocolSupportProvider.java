package org.jetlinks.protocol;

import org.jetlinks.core.ProtocolSupport;
import org.jetlinks.core.defaults.CompositeProtocolSupport;
import org.jetlinks.core.device.DeviceRegistry;
import org.jetlinks.core.spi.ProtocolSupportProvider;
import org.jetlinks.core.spi.ServiceContext;
import org.jetlinks.protocol.message.WaterConservancyDeviceCodec;
import org.jetlinks.supports.official.JetLinksDeviceMetadataCodec;
import reactor.core.publisher.Mono;

public class WaterConservancyProtocolSupportProvider implements ProtocolSupportProvider {
    @Override
    public Mono<? extends ProtocolSupport> create(ServiceContext context) {
        CompositeProtocolSupport support = new CompositeProtocolSupport();
        support.setName("GB-LS651水利");
        support.setId("GB-LS651");
        support.setDescription("国标SL651水利设备协议");
        support.setMetadataCodec(new JetLinksDeviceMetadataCodec());
        context.getService(DeviceRegistry.class).ifPresent(registry->{
            WaterConservancyDeviceCodec codec = new WaterConservancyDeviceCodec(registry);
            support.addMessageCodecSupport(codec);
        });
        return Mono.just(support);
    }
}
