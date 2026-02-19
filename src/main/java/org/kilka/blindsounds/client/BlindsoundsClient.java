package org.kilka.blindsounds.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;

public class BlindsoundsClient implements ClientModInitializer {

    public static String MOD_ID = "blindsounds";

    public static float darkness = 2.5f;
    public static int blockView = 3;
    public static int chunkView = 1;


    @Override
    public void onInitializeClient() {
        Config.initialize();
    }
}
