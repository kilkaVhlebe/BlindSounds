package org.kilka.blindsounds.client;

import com.google.gson.GsonBuilder;
import dev.isxander.yacl3.api.ConfigCategory;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.OptionGroup;
import dev.isxander.yacl3.api.YetAnotherConfigLib;
import dev.isxander.yacl3.api.controller.TickBoxControllerBuilder;
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
                                .name(Text.literal("general"))
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
}
