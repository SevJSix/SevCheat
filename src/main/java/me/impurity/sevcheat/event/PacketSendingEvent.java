package me.impurity.sevcheat.event;

import net.minecraft.network.Packet;
import net.minecraftforge.fml.common.eventhandler.Cancelable;

@Cancelable
public class PacketSendingEvent extends PacketEvent {
    public PacketSendingEvent(Packet<?> packet) {
        super(packet);
    }
}
