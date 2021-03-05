package dev.iincinerator.pfm.util;

import com.google.common.collect.ImmutableSet;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.fml.loading.moddiscovery.ModFileInfo;
import net.minecraftforge.forgespi.language.IModInfo;

import java.util.List;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.stream.Collectors;

public final class FMLUtils {
    private FMLUtils() { throw new UnsupportedOperationException("Cannot instantiate utility class FMLUtils."); }

    public static List<ModFileInfo> getModFileInfoList() {
        return FMLLoader.getLoadingModList()
                .getModFiles()
                .stream()
                .filter(info -> isModInfoValid(info.getMods()))
                .collect(Collectors.toList());
    }

    public static boolean isModInfoValid(List<IModInfo> infoList) {
        final Set<String> bannedModIds = getBannedModIds();
        return infoList
                .stream()
                .noneMatch(info ->
                        bannedModIds.contains(info.getModId())
                );
    }

    public static boolean isValidClassName(JarEntry entry) {
        final String name = entry.getName();
        return !name.startsWith("assets")
                && !name.contains("package-info")
                && !name.contains("$");
    }

    public static String getClassName(JarEntry entry) {
        final String name = entry.getName();
        return name
                .replace('/', '.')
                .replace(".class", "");
    }

    private static Set<String> getBannedModIds() {
        return ImmutableSet.of(
                "pfm-redo"
        );
    }
}