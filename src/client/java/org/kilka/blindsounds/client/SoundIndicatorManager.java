package org.kilka.blindsounds.client;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.LivingEntity;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SoundIndicatorManager {
    private static final Map<LivingEntity, Long> INDICATORS = new ConcurrentHashMap<>();
    private static final long DURATION_MS = Config.get().soundMarkerDuration;
    public static void add(LivingEntity entity) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;

        double distance = client.player.getEntityPos().distanceTo(entity.getEntityPos());
        if (distance > Config.get().soundMarkersReaction) return;

        INDICATORS.put(entity, System.currentTimeMillis() + DURATION_MS);
    }

    public static Map<LivingEntity, Long> getActive() {
        long now = System.currentTimeMillis();
        Iterator<Map.Entry<LivingEntity, Long>> it = INDICATORS.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<LivingEntity, Long> entry = it.next();
            if (entry.getValue() <= now || !entry.getKey().isAlive()) {
                it.remove();
            }
        }
        return INDICATORS;
    }
}