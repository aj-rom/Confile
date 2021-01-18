/*
 *   Project: Confile
 *   File: YamlConfigurationOptions.java
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

package io.coachluck.confile.file;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class YamlConfigurationOptions extends FileConfigurationOptions {
    private int indent = 2;

    protected YamlConfigurationOptions(@NotNull YamlConfiguration configuration) {
        super(configuration);
    }

    @NotNull
    public YamlConfiguration configuration() {
        return (YamlConfiguration)super.configuration();
    }

    @NotNull
    public YamlConfigurationOptions copyDefaults(boolean value) {
        super.copyDefaults(value);
        return this;
    }

    @NotNull
    public YamlConfigurationOptions pathSeparator(char value) {
        super.pathSeparator(value);
        return this;
    }

    @NotNull
    public YamlConfigurationOptions header(@Nullable String value) {
        super.header(value);
        return this;
    }

    @NotNull
    public YamlConfigurationOptions copyHeader(boolean value) {
        super.copyHeader(value);
        return this;
    }

    public int indent() {
        return this.indent;
    }

    @NotNull
    public YamlConfigurationOptions indent(int value) {
        this.indent = value;
        return this;
    }
}
