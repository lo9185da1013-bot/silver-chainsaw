package net.peyton.eagler.util;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.util.ResourceLocation;

public final class ResourceFallbacks {
    private ResourceFallbacks() {}

    private static volatile boolean enabled = true;

    public static boolean isEnabled() {
        return enabled;
    }

    public static void setEnabled(boolean e) {
        enabled = e;
    }

    public static List<ResourceLocation> alternatives(ResourceLocation loc) {
        List<ResourceLocation> alts = new ArrayList<>();
        String domain = loc.getResourceDomain();
        String path = loc.getResourcePath();
        String name = path.substring(path.lastIndexOf('/') + 1);

        if (path.startsWith("textures/item/")) {
            alts.add(new ResourceLocation(domain, "textures/items/" + name));
            alts.add(new ResourceLocation(domain, "textures/entity/" + name));
            alts.add(new ResourceLocation(domain, "textures/block/" + name));
        } else if (path.startsWith("textures/items/")) {
            alts.add(new ResourceLocation(domain, "textures/item/" + name));
            alts.add(new ResourceLocation(domain, "textures/entity/" + name));
        } else if (path.startsWith("models/item/")) {
            String base = name.endsWith(".json") ? name : name + ".json";
            alts.add(new ResourceLocation(domain, "models/item/" + base));
            alts.add(new ResourceLocation(domain, "textures/item/" + base.replace(".json", ".png")));
            alts.add(new ResourceLocation(domain, "textures/items/" + base.replace(".json", ".png")));
        } else if (path.startsWith("textures/entity/")) {
            alts.add(new ResourceLocation(domain, "textures/item/" + name));
            alts.add(new ResourceLocation(domain, "textures/items/" + name));
        } else {
            alts.add(new ResourceLocation(domain, "textures/item/" + name));
            alts.add(new ResourceLocation(domain, "textures/items/" + name));
            alts.add(new ResourceLocation(domain, "textures/entity/" + name));
            alts.add(new ResourceLocation(domain, "models/item/" + (name.endsWith(".json") ? name : name + ".json")));
        }

        return alts;
    }
}
