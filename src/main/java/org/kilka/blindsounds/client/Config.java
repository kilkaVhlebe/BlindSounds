package org.kilka.blindsounds.client;

import com.google.gson.GsonBuilder;
import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.DoubleFieldControllerBuilder;
import dev.isxander.yacl3.api.controller.LongFieldControllerBuilder;
import dev.isxander.yacl3.api.controller.TickBoxControllerBuilder;
import dev.isxander.yacl3.api.controller.IntegerFieldControllerBuilder;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import dev.isxander.yacl3.platform.YACLPlatform;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import org.apache.logging.log4j.core.appender.rolling.action.IfAll;


public class Config {

    public static final ConfigClassHandler<Config> HANDLER = ConfigClassHandler.createBuilder(Config.class)
            .serializer(config -> GsonConfigSerializerBuilder.create(config)
                    .setPath(YACLPlatform.getConfigDir().resolve("blind_sounds_config.json5"))
                    .appendGsonBuilder(GsonBuilder::setPrettyPrinting)
                    .setJson5(true)
                    .build())
            .build();

    public static void initialize() {
        load();
    }

    public static Config get() {
        return HANDLER.instance();
    }

    public static void save() {
        HANDLER.save();
    }

    public static void load() {
        HANDLER.load();
    }

    public static Screen createScreen(Screen parent) {
        return YetAnotherConfigLib.createBuilder()
                .title(Text.literal("Blind Sounds"))
                .category(ConfigCategory.createBuilder()
                        .name(Text.literal("Settings"))
                        .group(OptionGroup.createBuilder()
                                .name(Text.literal("General"))
                                .option(Option.<Boolean>createBuilder()
                                        .name(Text.literal("Mod enabled"))
                                        .binding(
                                                true, () -> Config.get().modEnabled, newVal -> Config.get().modEnabled = newVal
                                        )
                                        .controller(TickBoxControllerBuilder::create)
                                        .build())
                                .option(Option.<Boolean>createBuilder()
                                        .name(Text.literal("Enable mode fog"))
                                        .binding(
                                                true, () -> Config.get().fogEnabled, newVal -> Config.get().fogEnabled = newVal
                                        )
                                        .controller(TickBoxControllerBuilder::create)
                                        .build())
                                .option(Option.<Boolean>createBuilder()
                                        .name(Text.literal("Hide mobs"))
                                        .binding(
                                                true, () -> Config.get().mobsEnabled, newVal -> Config.get().mobsEnabled = newVal
                                        )
                                        .controller(TickBoxControllerBuilder::create)
                                        .build())
                                .option(Option.<Boolean>createBuilder()
                                        .name(Text.literal("Sound marks enabled"))
                                        .binding(
                                                true, () -> Config.get().soundMarksEnabled, newVal -> Config.get().soundMarksEnabled = newVal
                                        )
                                        .controller(TickBoxControllerBuilder::create)
                                        .build())
                                .option(Option.<Integer>createBuilder()
                                        .name(Text.literal("Interval view radius"))
                                        .description(
                                                OptionDescription.createBuilder()
                                                        .text(Text.literal(
                                                                "How many times \"Block fog view radius\" will repeat.\n" +
                                                                "Exemple: \"Block fog view radius\" - 3, \"Interval view radius\" - 2, final view radius - 6 blocks"))
                                                        .build())
                                        .binding(
                                                1, () -> Config.get().chunkFogRadius, newVal -> Config.get().chunkFogRadius = newVal
                                        )
                                        .controller(IntegerFieldControllerBuilder::create)
                                        .build())
                                .option(Option.<Integer>createBuilder()
                                        .name(Text.literal("Block fog view radius"))
                                        .description(
                                                OptionDescription.createBuilder()
                                                        .text(Text.literal("How many block you see in chunk"))
                                                .build())
                                        .binding(
                                                3, () -> Config.get().blockFogRadius, newVal -> Config.get().blockFogRadius = newVal
                                        )
                                        .controller(IntegerFieldControllerBuilder::create)
                                        .build())
                                .option(Option.<Double>createBuilder()
                                        .name(Text.literal("Sound markers radius"))
                                        .description(
                                                OptionDescription.createBuilder()
                                                        .text(Text.literal("Radius from you where mob sound will trigger sound marker"))
                                                        .build())
                                        .binding(
                                                10.0, () -> Config.get().soundMarkersReaction, newVal -> Config.get().soundMarkersReaction = newVal
                                        )
                                        .controller(DoubleFieldControllerBuilder::create)
                                        .build())
                                .option(Option.<Long>createBuilder()
                                        .name(Text.literal("Sound markers visibility duration(milliseconds)"))
                                        .description(
                                                OptionDescription.createBuilder()
                                                        .text(Text.literal("How long sound marker will visible in milliseconds"))
                                                        .build())
                                        .binding(
                                                (long) 1000, () -> Config.get().soundMarkerDuration, newVal -> Config.get().soundMarkerDuration = newVal
                                        )
                                        .controller(LongFieldControllerBuilder::create)
                                        .build())
                                .build())
                        .build())
                .save(Config::save)
                .build()
                .generateScreen(parent);
    }

    @SerialEntry
    public boolean modEnabled = true;

    @SerialEntry
    public boolean fogEnabled = true;

    @SerialEntry
    public boolean mobsEnabled = true;

    @SerialEntry
    public boolean soundMarksEnabled = true;

    @SerialEntry
    public int chunkFogRadius = 1;

    @SerialEntry
    public int blockFogRadius = 3;

    @SerialEntry
    public double soundMarkersReaction = 10.0;

    @SerialEntry
    public long soundMarkerDuration = 1000;
}
