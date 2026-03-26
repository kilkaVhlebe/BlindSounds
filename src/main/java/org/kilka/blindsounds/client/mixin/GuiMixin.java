package org.kilka.blindsounds.client.mixin;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.resources.Identifier;
import net.minecraft.world.phys.Vec3;
import org.kilka.blindsounds.client.BlindsoundsClient;
import org.kilka.blindsounds.client.Config;
import org.kilka.blindsounds.client.SoundIndicatorManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
@Mixin(Gui.class)

public class GuiMixin {
    @Inject(method = "extractCrosshair", at = @At("HEAD"))
    private void onRender(GuiGraphicsExtractor context, DeltaTracker tickCounter, CallbackInfo ci) {
        if(!Config.get().modEnabled || !Config.get().soundMarksEnabled) return;

        Minecraft client = Minecraft.getInstance();
        if (client.player == null) return;

        Camera camera = client.gameRenderer.getMainCamera();

        int screenWidth = client.getWindow().getGuiScaledWidth();
        int screenHeight = client.getWindow().getGuiScaledHeight();

        int centerX = screenWidth / 2;
        int centerY = screenHeight / 2;
        int radius = 80;

        for (LivingEntity entity : SoundIndicatorManager.getActive().keySet()) {
            if (!entity.isAlive()) continue;

            Vec3 cameraPos = camera.position();
            Vec3 entityPos = entity.position();
            Vec3 relativePos = entityPos.subtract(cameraPos);

            float angleToEntity = (float) Math.toDegrees(Math.atan2(-relativePos.x, relativePos.z));
            float diff = angleDifference(camera.yaw(), angleToEntity+270);

            int x, y;

            Identifier warningSing = Identifier.fromNamespaceAndPath(BlindsoundsClient.MOD_ID, "textures/gui/sprites/warning.png");

            double diffRad = Math.toRadians(diff);
            x = centerX + (int)(radius * Math.cos(diffRad));
            y = centerY + (int)(radius * Math.sin(diffRad));

            context.blit(RenderPipelines.GUI_TEXTURED, warningSing, x, y, 0, 0, 16,16,16, 16);
        }
    }

    private float angleDifference(float a, float b) {
        float diff = (b - a + 180) % 360 - 180;
        return diff < -180 ? diff + 360 : diff;
    }
}