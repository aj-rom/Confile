/*
 *   Project: Confile
 *   File: YamlConfigurationOptions.java
 *   Last Modified: 1/17/21, 8:36 PM
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

    /**
     * Creates the default YamlConfigurationOptions from a YamlConfiguration
     * @param configuration the YamlConfiguration
     */
    protected YamlConfigurationOptions(@NotNull YamlConfiguration configuration) {
        super(configuration);
    }

    /**
     * Gets the YamlConfiguration that this is attached too
     * @return the YamlConfiguration that this is attached too
     */
    @NotNull
    public YamlConfiguration configuration() {
        return (YamlConfiguration) super.configuration();
    }

    /**
     * Whether or not to copy the defaults already set
     * @param value true to copy, false to not
     * @return the updated YamlConfigurationOptions
     */
    @NotNull
    public YamlConfigurationOptions copyDefaults(boolean value) {
        super.copyDefaults(value);
        return this;
    }

    /**
     * Sets the path separator of the YamlConfigurationOptions
     * @param separator the character to separate paths
     * @return the updated YamlConfigurationOptions
     */
    @NotNull
    public YamlConfigurationOptions pathSeparator(char separator) {
        super.pathSeparator(separator);
        return this;
    }

    /**
     * Sets the header of the YamlConfiguration file from a string
     * @param header header content as a string.
     * @return the YamlConfigurationOptions with the applied header
     */
    @NotNull
    public YamlConfigurationOptions header(@Nullable String header) {
        super.header(header);
        return this;
    }

    /**
     * Whether or not to copy the header of the file for saving later
     * @param value true to copy, false to not
     * @return the updated YamlConfigurationOptions
     */
    @NotNull
    public YamlConfigurationOptions copyHeader(boolean value) {
        super.copyHeader(value);
        return this;
    }

    /**
     * Gets the number of spaces for an indent on a YamlConfiguration
     * @return the number of spaces of an indent
     */
    public int indent() {
        return this.indent;
    }

    /**
     * Sets the indent of the YamlConfiguration
     * @param spaces the number of spaces of the indent
     * @return the updated YamlConfigurationOptions
     */
    @NotNull
    public YamlConfigurationOptions indent(int spaces) {
        this.indent = spaces;
        return this;
    }
}
