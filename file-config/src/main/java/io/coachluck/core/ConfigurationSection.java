package io.coachluck.core;

import java.util.List;
import java.util.Map;
import java.util.Set;

import io.coachluck.core.serialization.ConfigurationSerializable;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ConfigurationSection {

    @NotNull
    Set<String> getKeys(boolean deep);

    @NotNull
    Map<String, Object> getValues(boolean deep);

    boolean contains(@NotNull String key);

    boolean contains(@NotNull String key, boolean var2);

    boolean isSet(@NotNull String key);

    @Nullable
    String getCurrentPath();

    @NotNull
    String getName();

    @Nullable
    Configuration getRoot();

    @Nullable
    ConfigurationSection getParent();

    @Nullable
    Object get(@NotNull String var1);

    @Nullable
    Object get(@NotNull String key, @Nullable Object obj);

    void set(@NotNull String key, @Nullable Object obj);

    @NotNull
    ConfigurationSection createSection(@NotNull String key);

    @NotNull
    ConfigurationSection createSection(@NotNull String key, @NotNull Map<?, ?> data);

    @Nullable
    String getString(@NotNull String key);

    @Nullable
    String getString(@NotNull String key, @Nullable String string);

    @Nullable
    List<?> getList(@NotNull String key);

    @Nullable
    List<?> getList(@NotNull String key, @Nullable List<?> list);

    boolean isList(@NotNull String key);

    @NotNull
    List<String> getStringList(@NotNull String key);

    @NotNull
    List<Character> getCharacterList(@NotNull String key);

    @NotNull
    List<Map<?, ?>> getMapList(@NotNull String key);

    @Nullable
    <T> T getObject(@NotNull String key, @NotNull Class<T> clazz);

    @Nullable
    <T> T getObject(@NotNull String key, @NotNull Class<T> clazz, @Nullable T type);

    @Nullable
    <T extends ConfigurationSerializable> T getSerializable(@NotNull String key, @NotNull Class<T> clazz);

    @Nullable
    <T extends ConfigurationSerializable> T getSerializable(@NotNull String key, @NotNull Class<T> clazz,
                                                            @Nullable T type);

    @Nullable
    ConfigurationSection getConfigurationSection(@NotNull String key);

    boolean isConfigurationSection(@NotNull String key);

    @Nullable
    ConfigurationSection getDefaultSection();

    void addDefault(@NotNull String key, @Nullable Object obj);
}
