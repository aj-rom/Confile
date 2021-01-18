/*
 *   Project: Confile
 *   File: MemoryConfiguration.java
 *   Last Modified: 1/17/21, 8:18 PM
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

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Map.Entry;

public class MemoryConfiguration extends MemorySection implements Configuration {
    protected Configuration defaults;
    protected MemoryConfigurationOptions options;

    public MemoryConfiguration() {
    }

    public MemoryConfiguration(@Nullable Configuration defaults) {
        this.defaults = defaults;
    }

    public void addDefault(@NotNull String path, @Nullable Object value) {
        if (this.defaults == null) {
            this.defaults = new MemoryConfiguration();
        }

        this.defaults.set(path, value);
    }

    public void addDefaults(@NotNull Map<String, Object> defaults) {
        for (Entry<String, Object> strObjEntry : defaults.entrySet()) {
            this.addDefault(strObjEntry.getKey(), strObjEntry.getValue());
        }
    }

    public void addDefaults(@NotNull Configuration defaults) {
        this.addDefaults(defaults.getValues(true));
    }

    public void setDefaults(@NotNull Configuration defaults) {
        this.defaults = defaults;
    }

    @Nullable
    public Configuration getDefaults() {
        return this.defaults;
    }

    @Nullable
    public ConfigurationSection getParent() {
        return null;
    }

    @NotNull
    public MemoryConfigurationOptions options() {
        if (this.options == null) {
            this.options = new MemoryConfigurationOptions(this);
        }

        return this.options;
    }
}