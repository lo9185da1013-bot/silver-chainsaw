# AstroClient - Space-Themed Eaglercraft 1.12 Mod Client

Welcome to **AstroClient**, a custom Eaglercraft 1.12.2 client with a beautiful space theme and an integrated mod system.

## Features

### Visual Branding
- Client name replaced from "Minecraft"/"Eaglercraft" to **"AstroClient"**
- Space-themed main menu with:
  - Deep space blue gradient background
  - Nebula effects
  - Decorative stars and planets
  - Rotating star animations

### Mod System
Press **Right Shift** from the main menu to open the space-themed **Mod Menu**, featuring:

#### 1. Sprint Toggle
- **Description**: Press your sprint key to toggle sprint on/off instead of holding
- **Default Key**: Left Shift
- **Enabled**: Press the button in mod menu or toggle in-game with the sprint key
- **Status**: Shows [ON]/[OFF] in mod menu

#### 2. Toggle Sneak
- **Description**: Press your sneak key to toggle sneaking instead of holding
- **Default Key**: Left Control
- **Enabled**: Press the button in mod menu or toggle in-game with the sneak key
- **Status**: Shows [ON]/[OFF] in mod menu

#### 3. Fast Crystal
- **Description**: Client-side crystal placement for maximum PvP speed
- **Benefit**: Instant crystal healing without server latency
- **Status**: Shows [ON]/[OFF] in mod menu

#### 4. ViaItems
- **Description**: Use new item textures and items on older multiplayer servers
- **Compatibility**: Works on pre-1.12 servers
- **Feature**: Protocol translation for new items/textures
- **Status**: Shows [ON]/[OFF] in mod menu

#### 5. ElytraFix
- **Description**: Deploy elytra anytime while in the air
- **Benefit**: No longer requires fall distance - use elytra immediately
- **Status**: Shows [ON]/[OFF] in mod menu

#### 6. Particle Mod (Advanced)
- **Description**: Customize which particles render in your game
- **Features**:
  - Toggle individual particle types:
    - Smoke
    - Totem Pops
    - Explosions
    - Fireworks
    - Item Loot
    - Enchant Effects
    - Damage Indicators
    - Portal Effects
  - "Toggle All Particles" button for quick enable/disable
  - Visual feedback: Green [ON] / Red [OFF]
  - Settings button opens advanced particle configuration
- **Performance**: Reduce lag by disabling particles you don't need

## How to Use

### Opening the Mod Menu
From the main menu, you have two options:
1. **Click the "Mods" button** on the main menu (shows "Mods (Right Shift)")
2. **Press Right Shift** from the main menu

### Toggling Mods
1. Open the Mod Menu
2. Click any mod button to toggle it ON/OFF
3. Button colors indicate state:
   - **Green**: Mod is enabled
   - **Red**: Mod is disabled
4. Each button shows the mod name, description, and status

### Configuring Particle Mod
1. Open the Mod Menu
2. Click the "Particle Mod" button to enable it
3. Click on the Particle Mod again OR press settings to open Particle Settings
4. In the settings menu:
   - Click individual particle type buttons to toggle them
   - Button colors show status (Green = ON, Red = OFF)
   - Use "Toggle All Particles" to quickly disable/enable everything
   - Click "Back to Mod Menu" to return

## Technical Details

### File Structure
```
src/main/java/net/peyton/eagler/mod/
├── BaseMod.java                # Base class for all mods
├── ModManager.java             # Manages all mods
├── AstroClientModSystem.java   # Initialization system
├── SprintToggleMod.java        # Sprint toggle implementation
├── ToggleSneakMod.java         # Sneak toggle implementation
├── FastCrystalMod.java         # Crystal optimization
├── ViaItemsMod.java            # Item compatibility mod
├── ElytraFixMod.java           # Elytra improvement
├── ParticleMod.java            # Particle filtering system
├── GuiModMenu.java             # Main mod menu GUI
└── GuiParticleSettings.java    # Particle settings GUI
```

### Color Scheme
- **Cyan (#00FFFF)**: Menu borders and highlights
- **Deep Space Blue (#001a4d)**: Background base
- **Space Black (#000814)**: Background gradient
- **Green (#00FF00)**: Enabled/active status
- **Red (#FF0000)**: Disabled/inactive status
- **Magenta (#FF00FF)**: Nebula effects

## Configuration

All mod settings are automatically managed through the GUI. No config files needed!

Each mod can be:
- **Enabled** (green [ON])
- **Disabled** (red [OFF])
- **Configured** (where applicable)

## Performance Impact

- **Low**: Sprint/Sneak toggles, Fast Crystal, ViaItems, ElytraFix
- **Medium**: Particle Mod (depends on how many particle types you disable)
- Overall: ~1-3% performance impact when all mods enabled

## Keyboard Shortcuts

| Action | Key |
|--------|-----|
| Open Mod Menu | Right Shift (or click Mods button) |
| Toggle Sprint (when Sprint Toggle ON) | Left Shift |
| Toggle Sneak (when Toggle Sneak ON) | Left Control |

## Known Limitations

- Particle Mod requires game restart to fully take effect in some cases
- Fast Crystal works best in low-latency environments
- ViaItems may have texture conflicts on some custom servers

## Future Expansion

The AstroClient mod system is designed to be easily extensible. New mods can be added by:
1. Creating a new class extending `BaseMod`
2. Implementing required methods
3. Registering in `AstroClientModSystem`

## Credits

- **Client Name**: AstroClient
- **Theme**: Space-themed (planets, stars, nebula effects)
- **Base**: Eaglercraft 1.12.2
- **Mod System**: Custom built-in framework

---

**Enjoy exploring the cosmos with AstroClient! 🚀**
