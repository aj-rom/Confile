package io.coachluck.core;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public interface Configuration extends ConfigurationSection {
    void addDefault(@NotNull String var1, @Nullable Object var2);

    void addDefaults(@NotNull Map<String, Object> var1);

    void addDefaults(@NotNull Configuration var1);

    void setDefaults(@NotNull Configuration var1);

    @Nullable
    Configuration getDefaults();

    @NotNull
    ConfigurationOptions options();
}