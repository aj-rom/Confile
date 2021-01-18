/*
 *   Project: Confile
 *   File: ConfigurationSection.java
 *   Last Modified: 1/17/21, 5:37 PM
 *
 *    Copyright 2021 AJ Romaniello
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *
 */

package io.coachluck.confile;

import java.util.List;
import java.util.Map;
import java.util.Set;

import io.coachluck.confile.serialization.ConfigurationSerializable;

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
