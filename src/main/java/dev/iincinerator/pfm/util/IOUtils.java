package dev.iincinerator.pfm.util;

import net.minecraftforge.fml.loading.moddiscovery.ModFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

public final class IOUtils {
    private IOUtils() { throw new UnsupportedOperationException("Cannot instantiate utility class IOUtils."); }

    public static JarInputStream getJarStream(ModFile file) {
        JarInputStream jis = null;
        try {
            final InputStream is = Files.newInputStream(file.getFilePath());
            jis = new JarInputStream(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jis;
    }

    public static List<JarEntry> getJarEntries(JarInputStream jis) {
        final List<JarEntry> entries = new ArrayList<>();
        try {
            JarEntry entry;
            while ((entry = jis.getNextJarEntry()) != null) {
                entries.add(entry);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return entries;
    }
}