/*
 *   Project: Confile
 *   File: FileConfiguration.java
 *   Last Modified: 1/17/21, 8:41 PM
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

import io.coachluck.confile.Configuration;
import io.coachluck.confile.InvalidConfigurationException;
import io.coachluck.confile.MemoryConfiguration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

public abstract class FileConfiguration extends MemoryConfiguration {
    public FileConfiguration() { }

    public FileConfiguration(@Nullable Configuration defaults) {
        super(defaults);
    }

    /**
     *
     * @param file
     * @throws IOException
     */
    public void save(@NotNull File file) throws IOException {
        file.getParentFile().mkdirs();
        String data = this.saveToString();
        OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8);

        try {
            writer.write(data);
        } finally {
            writer.close();
        }

    }

    /**
     *
     * @param file
     * @throws IOException
     */
    public void save(@NotNull String file) throws IOException {
        this.save(new File(file));
    }

    /**
     *
     * @return
     */
    @NotNull
    public abstract String saveToString();

    /**
     *
     * @param file
     * @throws FileNotFoundException
     * @throws IOException
     * @throws InvalidConfigurationException
     */
    public void load(@NotNull File file) throws FileNotFoundException, IOException, InvalidConfigurationException {
        FileInputStream stream = new FileInputStream(file);
        this.load(new InputStreamReader(stream, StandardCharsets.UTF_8));
    }

    /**
     *
     * @param reader
     * @throws IOException
     * @throws InvalidConfigurationException
     */
    public void load(@NotNull Reader reader) throws IOException, InvalidConfigurationException {
        BufferedReader input = reader instanceof BufferedReader ? (BufferedReader) reader : new BufferedReader(reader);
        StringBuilder builder = new StringBuilder();

        String line;
        try {
            while((line = input.readLine()) != null) {
                builder.append(line);
                builder.append('\n');
            }
        } finally {
            input.close();
        }

        this.loadFromString(builder.toString());
    }

    /**
     *
     * @param file
     * @throws IOException
     * @throws InvalidConfigurationException
     */
    public void load(@NotNull String file) throws IOException, InvalidConfigurationException {
        this.load(new File(file));
    }

    /**
     *
     * @param str
     * @throws InvalidConfigurationException
     */
    public abstract void loadFromString(@NotNull String str) throws InvalidConfigurationException;

    @NotNull
    public abstract String buildHeader();

    @NotNull
    public FileConfigurationOptions options() {
        if (this.options == null) {
            this.options = new FileConfigurationOptions(this);
        }

        return (FileConfigurationOptions)this.options;
    }
}
