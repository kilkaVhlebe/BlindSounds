# BlindSounds

A Minecraft Fabric mod that creates an atmospheric blind experience.

## Features

- **Dense Fog** - Visibility reduced to 3 blocks
- **Hidden Mobs** - Mobs become invisible but still produce sounds
- **Sound Indicators** - Visual markers around the screen showing direction to sound sources (within 10 blocks)
- **No Sky** - Sun and moon are hidden

## Requirements

- Minecraft 1.21.11
- Fabric Loader 0.18.4+
- [Fabric API](https://modmuss50.me/fabric.html)
- [Yet Another Config Lib](https://modrinth.com/mod/yacl)
- [Mod Menu](https://modrinth.com/mod/modmenu) (optional)

## Installation

1. Install [Fabric Loader](https://fabricmc.net/use/installer/) and Fabric API
2. Download the latest mod JAR from [Releases](https://github.com/kilka-v/BlindSounds/releases)
3. Place the JAR in your `mods` folder
4. Launch the game

## Configuration

Open the config screen from:
- Mod Menu (if installed) → Click config icon
- Or edit `blind_sounds_config.json5` in your config folder

### Options

| Setting | Description |
|---------|-------------|
| Mod enabled | Toggle all mod features |
| Enable mode fog | Toggle dense fog |
| Hide mobs | Toggle mob visibility |
| Sound marks enabled | Toggle directional sound indicators |

## Building from source

```bash
./gradlew build
```

The built JAR will appear in `build/libs/`
