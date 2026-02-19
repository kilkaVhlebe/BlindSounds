package org.kilka.blindsounds.client.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.render.fog.FogRenderer;
import org.kilka.blindsounds.client.BlindsoundsClient;
import org.kilka.blindsounds.client.Config;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(FogRenderer.class)
public class FogRendererMixin {

    @ModifyExpressionValue(method = "applyFog(Lnet/minecraft/client/render/Camera;ILnet/minecraft/client/render/RenderTickCounter;FLnet/minecraft/client/world/ClientWorld;)Lorg/joml/Vector4f;", at = @At(value = "CONSTANT", args = "intValue=16"))
    private int fogFucker(int original) {
        if(!Config.get().modEnabled || !Config.get().fogEnabled) {
            return original;
        }
        return BlindsoundsClient.blockView;
    }
}
