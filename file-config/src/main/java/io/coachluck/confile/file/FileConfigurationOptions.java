/*
 *   Project: Confile
 *   File: FileConfigurationOptions.java
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

import io.coachluck.confile.MemoryConfiguration;
import io.coachluck.confile.MemoryConfigurationOptions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FileConfigurationOptions extends MemoryConfigurationOptions {
    private String header = null;
    private boolean copyHeader = true;

    protected FileConfigurationOptions(@NotNull MemoryConfiguration configuration) {
        super(configuration);
    }

    @NotNull
    public FileConfiguration configuration() {
        return (FileConfiguration) super.configuration();
    }

    @NotNull
    public FileConfigurationOptions copyDefaults(boolean value) {
        super.copyDefaults(value);
        return this;
    }

    @NotNull
    public FileConfigurationOptions pathSeparator(char value) {
        super.pathSeparator(value);
        return this;
    }

    @Nullable
    public String header() {
        return this.header;
    }

    @NotNull
    public FileConfigurationOptions header(@Nullable String value) {
        this.header = value;
        return this;
    }

    public boolean copyHeader() {
        return this.copyHeader;
    }

    @NotNull
    public FileConfigurationOptions copyHeader(boolean value) {
        this.copyHeader = value;
        return this;
    }
}