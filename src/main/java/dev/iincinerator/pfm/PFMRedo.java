package dev.iincinerator.pfm;

import com.google.common.collect.ImmutableSet;
import dev.iincinerator.pfm.reflect.ModReflector;
import dev.iincinerator.pfm.reflect.VoteReflector;
import dev.iincinerator.pfm.util.FMLUtils;
import dev.iincinerator.pfm.util.IOUtils;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.moddiscovery.ModFile;
import net.minecraftforge.fml.loading.moddiscovery.ModFileInfo;

import java.util.Set;
import java.util.jar.JarInputStream;

@Mod("pfm-redo")
public final class PFMRedo {
    public PFMRedo() {
        FMLJavaModLoadingContext.get()
                .getModEventBus()
                .addListener(this::onCommonSetup);
    }

    private void onCommonSetup(FMLCommonSetupEvent event) {
        FMLUtils.getModFileInfoList()
                .stream()
                .map(ModFileInfo::getFile)
                .forEach(this::reflectJarEntries);
    }

    private void reflectJarEntries(ModFile file) {
        final JarInputStream jis = IOUtils.getJarStream(file);
        IOUtils.getJarEntries(jis)
                .stream()
                .filter(FMLUtils::isValidClassName)
                .map(FMLUtils::getClassName)
                .forEach(this::reflectClasses);
    }

    private void reflectClasses(String name) {
        for (ModReflector reflector : getReflectors()) {
            if (reflector.validate(name)) {
                try {
                    final Class<?> clazz = Class.forName(name);
                    reflector.reflect(clazz);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private Set<ModReflector> getReflectors() {
        return ImmutableSet.of(
                new VoteReflector()
        );
    }
}