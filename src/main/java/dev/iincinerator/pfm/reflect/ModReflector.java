package dev.iincinerator.pfm.reflect;

public interface ModReflector {
    boolean validate(String name);

    void reflect(Class<?> clazz);
}