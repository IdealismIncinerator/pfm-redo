package dev.iincinerator.pfm.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.time.LocalDate;
import java.time.Month;

public class VoteReflector implements ModReflector {
    @Override
    public boolean validate(String name) {
        return name.endsWith("GoVoteHandler");
    }

    @Override
    public void reflect(Class<?> clazz) {
        for (Field field : clazz.getFields()) {
             if (field.getName().equals("ELECTION_DAY")) {
                field.setAccessible(true);
                 try {
                     final Field mods = Field.class.getDeclaredField("modifiers");
                     mods.setAccessible(true);
                     mods.setInt(field, field.getModifiers() & ~Modifier.FINAL);
                     field.set(null, LocalDate.of(1969, Month.APRIL, 1));
                 } catch (ReflectiveOperationException e) {
                     e.printStackTrace();
                 }

             }
        }
    }
}