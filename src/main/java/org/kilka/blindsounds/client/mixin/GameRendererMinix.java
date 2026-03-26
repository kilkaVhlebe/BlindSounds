package org.kilka.blindsounds.client.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.renderer.fog.FogData;
import net.minecraft.client.renderer.fog.FogRenderer;
import net.minecraft.client.multiplayer.ClientLevel;
import org.joml.Vector4f;
import org.kilka.blindsounds.client.BlindsoundsClient;
import org.kilka.blindsounds.client.Config;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(GameRenderer.class)
public class GameRendererMinix {

    @WrapOperation(method = "extractCamera", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/fog/FogRenderer;setupFog(Lnet/minecraft/client/Camera;ILnet/minecraft/client/DeltaTracker;FLnet/minecraft/client/multiplayer/ClientLevel;)Lnet/minecraft/client/renderer/fog/FogData;"))
    private FogData fogFucker(FogRenderer instance, Camera camera, int viewDistance, DeltaTracker renderTickCounter, float f, ClientLevel clientWorld, Operation<FogData> original) {
        if(!Config.get().modEnabled || !Config.get().fogEnabled) {
            return original.call(instance, camera, viewDistance, renderTickCounter, f, clientWorld);
        }
        return original.call(instance, camera, Config.get().chunkFogRadius, renderTickCounter, BlindsoundsClient.darkness, clientWorld);
    }
}
