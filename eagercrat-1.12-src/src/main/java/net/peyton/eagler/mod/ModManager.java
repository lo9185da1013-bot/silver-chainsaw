package net.peyton.eagler.mod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Manager for AstroClient mods
 */
public class ModManager {
    private static final ModManager instance = new ModManager();
    private Map<String, BaseMod> mods = new HashMap<>();
    private List<BaseMod> modList = new ArrayList<>();

    private ModManager() {
    }

    public static ModManager getInstance() {
        return instance;
    }

    public void registerMod(BaseMod mod) {
        mods.put(mod.getName().toLowerCase(), mod);
        modList.add(mod);
    }

    public BaseMod getMod(String name) {
        return mods.get(name.toLowerCase());
    }

    public List<BaseMod> getModList() {
        return new ArrayList<>(modList);
    }

    public void onClientTick() {
        for (BaseMod mod : modList) {
            if (mod.isEnabled()) {
                mod.onTick();
            }
        }
    }

    public boolean onKeyPress(int keyCode) {
        for (BaseMod mod : modList) {
            if (mod.isEnabled() && mod.onKeyPress(keyCode)) {
                return true;
            }
        }
        return false;
    }
}
