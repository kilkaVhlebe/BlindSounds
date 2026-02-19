package org.kilka.blindsounds.client.mixin;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import org.kilka.blindsounds.client.BlindsoundsClient;
import org.kilka.blindsounds.client.Config;
import org.kilka.blindsounds.client.SoundIndicatorManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
@Mixin(InGameHud.class)
public class InGameHudMixin {
    @Inject(method = "render", at = @At("TAIL"))
    private void onRender(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        if(!Config.get().modEnabled || !Config.get().soundMarksEnabled) return;

        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null || client.world == null) return;

        Camera camera = client.gameRenderer.getCamera();
        if (camera == null) return;

        int screenWidth = client.getWindow().getScaledWidth();
        int screenHeight = client.getWindow().getScaledHeight();

        int centerX = screenWidth / 2;
        int centerY = screenHeight / 2;
        int radius = 80;  // Радиус компаса

        for (LivingEntity entity : SoundIndicatorManager.getActive().keySet()) {
            if (!entity.isAlive()) continue;

            Vec3d cameraPos = camera.getCameraPos();
            Vec3d entityPos = entity.getEntityPos();
            Vec3d relativePos = entityPos.subtract(cameraPos);

            // Угол до entity
            float angleToEntity = (float) Math.toDegrees(Math.atan2(-relativePos.x, relativePos.z));
            float diff = angleDifference(camera.getYaw(), angleToEntity+270);

            int x, y;

            Identifier warningSing = Identifier.of(BlindsoundsClient.MOD_ID, "warning");

            double diffRad = Math.toRadians(diff);
            x = centerX + (int)(radius * Math.cos(diffRad));
            y = centerY + (int)(radius * Math.sin(diffRad));

            // Рисуем квадрат 10x10 (центрированный)
            //context.fill(x - 5, y - 5, x + 5, y + 5, 0xFFFFFFFF);
            context.drawGuiTexture(RenderPipelines.GUI_TEXTURED, warningSing, 16, 16, 0, 0, x, y, 16, 16);
        }
    }

    private float angleDifference(float a, float b) {
        float diff = (b - a + 180) % 360 - 180;
        return diff < -180 ? diff + 360 : diff;
    }
}