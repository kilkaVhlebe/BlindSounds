package org.kilka.blindsounds.client.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.render.fog.FogRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import org.joml.Vector4f;
import org.kilka.blindsounds.client.BlindsoundsClient;
import org.kilka.blindsounds.client.Config;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class GameRendererMinix {

    @WrapOperation(method = "renderWorld", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/fog/FogRenderer;applyFog(Lnet/minecraft/client/render/Camera;ILnet/minecraft/client/render/RenderTickCounter;FLnet/minecraft/client/world/ClientWorld;)Lorg/joml/Vector4f;"))
    private Vector4f fogFucker(FogRenderer instance, Camera camera, int viewDistance, RenderTickCounter renderTickCounter, float f, ClientWorld clientWorld, Operation<Vector4f> original) {
        if(!Config.get().modEnabled || !Config.get().fogEnabled) {
            return original.call(instance, camera, viewDistance, renderTickCounter, f, clientWorld);
        }
        return original.call(instance, camera, BlindsoundsClient.chunkView, renderTickCounter, BlindsoundsClient.darkness, clientWorld);
    }
}
