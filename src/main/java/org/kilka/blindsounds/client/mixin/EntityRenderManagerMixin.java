package org.kilka.blindsounds.client.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.EntityRenderManager;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.DisplayEntity;
import org.kilka.blindsounds.client.Config;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(EntityRenderManager.class)
public class EntityRenderManagerMixin <S extends EntityRenderState> {
    @WrapMethod(method = "render")
    private void EntityFucker(S renderState, CameraRenderState cameraState, double offsetX, double offsetY, double offsetZ, MatrixStack matrices, OrderedRenderCommandQueue queue, Operation<Void> original) {

        if(!Config.get().modEnabled || !Config.get().mobsEnabled) {
            original.call(renderState, cameraState, offsetX, offsetY, offsetZ, matrices, queue);
            return;
        }

        if (!(renderState instanceof LivingEntityRenderState) || (renderState instanceof PlayerEntityRenderState)) {
            original.call(renderState, cameraState, offsetX, offsetY, offsetZ, matrices, queue);
        }
    }
}
