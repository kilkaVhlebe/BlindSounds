package org.kilka.blindsounds.client.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.client.render.SkyRendering;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.world.MoonPhase;
import org.kilka.blindsounds.client.BlindsoundsClient;
import org.kilka.blindsounds.client.Config;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(SkyRendering.class)
public class SkyRenderingMixin {

    @WrapMethod(method = "renderSun")
    private void SKyNullifier(float alpha, MatrixStack matrices, Operation<Void> original) {
        if(!Config.get().modEnabled) {
            original.call(alpha, matrices);
        }
    }

    @WrapMethod(method = "renderMoon")
    private void SKyNullifier(MoonPhase moonPhase, float alpha, MatrixStack matrices, Operation<Void> original) {
        if(!Config.get().modEnabled) {
            original.call(moonPhase, alpha, matrices);
        }
    }
}
