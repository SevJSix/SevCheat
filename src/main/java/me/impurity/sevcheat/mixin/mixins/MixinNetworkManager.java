package me.impurity.sevcheat.mixin.mixins;

import io.netty.channel.ChannelHandlerContext;
import me.impurity.sevcheat.event.PacketRecievingEvent;
import me.impurity.sevcheat.event.PacketSendingEvent;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({ NetworkManager.class })
public class MixinNetworkManager
{
    @Inject(method = "sendPacket(Lnet/minecraft/network/Packet;)V", at = @At("HEAD"), cancellable = true)
    public void onSendPacket(Packet<?> packet, CallbackInfo ci) {
        PacketSendingEvent packetSendingEvent = new PacketSendingEvent(packet);
        MinecraftForge.EVENT_BUS.post(packetSendingEvent);
        if (packetSendingEvent.isCanceled() && ci.isCancellable()) {
            ci.cancel();
        }
    }

    @Inject(method = "channelRead0", at = @At("HEAD"), cancellable = true)
    private void onChannelRead(ChannelHandlerContext context, Packet<?> packet, CallbackInfo ci) {
        PacketRecievingEvent event = new PacketRecievingEvent(packet);
        MinecraftForge.EVENT_BUS.post(event);
        if (event.isCanceled() && ci.isCancellable()) {
            ci.cancel();
        }
    }
}
