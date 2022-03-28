package me.impurity.sevcheat.module.modules.player;

import com.mojang.authlib.GameProfile;
import me.impurity.sevcheat.module.Category;
import me.impurity.sevcheat.module.Module;
import net.minecraft.client.entity.EntityOtherPlayerMP;

import java.util.UUID;

public class FakePlayer extends Module {

    public static EntityOtherPlayerMP getFakePlayer() {
        return FakePlayer.fakePlayer;
    }

    public static EntityOtherPlayerMP fakePlayer;

    public FakePlayer() {
        super("FakePlayer", Category.CLIENT, "spawns in a fake player");
    }

    @Override
    public void onEnable() {
        FakePlayer.fakePlayer = new EntityOtherPlayerMP(mc.world, new GameProfile(UUID.randomUUID(), "SevCheat"));
        FakePlayer.fakePlayer.copyLocationAndAnglesFrom(mc.player);
        FakePlayer.fakePlayer.inventory.copyInventory(mc.player.inventory);
        FakePlayer.fakePlayer.setEntityInvulnerable(true);
        mc.world.addEntityToWorld(6969, FakePlayer.fakePlayer);
    }

    @Override
    public void onDisable() {
        mc.world.removeEntityFromWorld(6969);
        FakePlayer.fakePlayer = null;
    }
}
