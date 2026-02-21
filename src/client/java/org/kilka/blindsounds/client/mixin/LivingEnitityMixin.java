package org.kilka.blindsounds.client.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.LivingEntity;
import net.minecraft.sound.SoundEvent;
import org.kilka.blindsounds.client.Config;
import org.kilka.blindsounds.client.SoundIndicatorManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class LivingEnitityMixin {

    @Inject(method = "playSound", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;playSound(Lnet/minecraft/sound/SoundEvent;FF)V"))
    private void onSoundPlayed(SoundEvent sound, CallbackInfo ci) {
        LivingEntity entity = (LivingEntity) (Object) this;
        MinecraftClient client = MinecraftClient.getInstance();

        if (client.player == null || client.world == null) return;
        if(!Config.get().modEnabled || !Config.get().mobsEnabled) return;

        SoundIndicatorManager.add(entity);
    }

}
