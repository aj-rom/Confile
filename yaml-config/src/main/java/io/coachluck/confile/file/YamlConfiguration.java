/*
 *   Project: Confile
 *   File: YamlConfiguration.java
 *   Last Modified: 1/22/21, 2:37 PM
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
import io.coachluck.confile.ConfigurationSection;
import io.coachluck.confile.InvalidConfigurationException;
import org.jetbrains.annotations.NotNull;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.DumperOptions.FlowStyle;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.error.YAMLException;
import org.yaml.snakeyaml.representer.Representer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.util.Map;

public class YamlConfiguration extends FileConfiguration {
    protected static final String COMMENT_PREFIX = "# ";
    protected static final String BLANK_CONFIG = "{}\n";
    private final DumperOptions yamlOptions = new DumperOptions();
    private final LoaderOptions loaderOptions = new LoaderOptions();
    private final Representer yamlRepresenter = new YamlRepresenter();
    private final Yaml yaml;

    /**
     * Creates a new YamlConfiguration object
     */
    public YamlConfiguration() {
        this.yaml = new Yaml(new YamlConstructor(), this.yamlRepresenter, this.yamlOptions, this.loaderOptions);
    }

    /**
     * Saves the YamlConfiguration Object as a string
     * @return the YamlConfiguration as a string
     */
    @NotNull
    public String saveToString() {
        this.yamlOptions.setIndent(this.options().indent());
        this.yamlOptions.setDefaultFlowStyle(FlowStyle.BLOCK);
        this.yamlRepresenter.setDefaultFlowStyle(FlowStyle.BLOCK);
        String header = this.buildHeader();
        String dump = this.yaml.dump(this.getValues(false));
        if (dump.equals(BLANK_CONFIG)) {
            dump = "";
        }

        return header + dump;
    }

    /**
     * Loads the YamlConfiguration contents from a string
     * @param contents the contents to add to the YamlConfiguration
     * @throws InvalidConfigurationException on YAMLException and when top level is not a Map
     */
    public void loadFromString(@NotNull String contents) throws InvalidConfigurationException {
        Map input;
        try {
            this.loaderOptions.setMaxAliasesForCollections(2147483647);
            input = this.yaml.load(contents);
        } catch (YAMLException e) {
            throw new InvalidConfigurationException(e);
        } catch (ClassCastException e) {
            throw new InvalidConfigurationException("Top level is not a Map.");
        }

        String header = this.parseHeader(contents);
        if (header.length() > 0) {
            this.options().header(header);
        }

        this.map.clear();
        if (input != null) {
            this.convertMapsToSections(input, this);
        }
    }

    protected void convertMapsToSections(@NotNull Map<?, ?> input, @NotNull ConfigurationSection section) {

        for (Map.Entry<?, ?> item : input.entrySet()) {
            String key = item.getKey().toString();
            Object value = item.getValue();

            if (value instanceof Map) {
                this.convertMapsToSections((Map) value, section.createSection(key));
                return;
            }

            section.set(key, value);
        }
    }

    @NotNull
    protected String parseHeader(@NotNull String input) {
        String[] lines = input.split("\r?\n", -1);
        StringBuilder result = new StringBuilder();
        boolean readingHeader = true;
        boolean foundHeader = false;

        for(int i = 0; i < lines.length && readingHeader; ++i) {
            String line = lines[i];
            if (line.startsWith(COMMENT_PREFIX)) {
                if (i > 0) {
                    result.append("\n");
                }

                if (line.length() > COMMENT_PREFIX.length()) {
                    result.append(line.substring(COMMENT_PREFIX.length()));
                }

                foundHeader = true;
            } else if (foundHeader && line.length() == 0) {
                result.append("\n");
            } else if (foundHeader) {
                readingHeader = false;
            }
        }

        return result.toString();
    }

    /**
     * Builds the header of the YamlConfiguration
     * @return the built header as a String
     */
    @NotNull
    public String buildHeader() {
        String header = this.options().header();
        if (this.options().copyHeader()) {
            Configuration def = this.getDefaults();
            if (def instanceof FileConfiguration) {
                FileConfiguration filedefaults = (FileConfiguration) def;
                String defaultsHeader = filedefaults.buildHeader();

                if (defaultsHeader.length() > 0) {
                    return defaultsHeader;
                }
            }
        }

        if (header != null) {
            StringBuilder builder = new StringBuilder();
            String[] lines = header.split("\r?\n", -1);
            boolean startedHeader = false;

            for(int i = lines.length - 1; i >= 0; --i) {
                builder.insert(0, "\n");
                if (startedHeader || lines[i].length() != 0) {
                    builder.insert(0, lines[i]);
                    builder.insert(0, COMMENT_PREFIX);
                    startedHeader = true;
                }
            }

            return builder.toString();
        }

        return "";
    }

    /**
     * Get the YamlConfiguration options of the YamlConfiguration
     * @return the YamlConfigurations options
     */
    @NotNull
    public YamlConfigurationOptions options() {
        if (this.options == null) {
            this.options = new YamlConfigurationOptions(this);
        }

        return (YamlConfigurationOptions) this.options;
    }

    /**
     * Loads a YamlConfiguration from a file
     * @param file the file to load into a YamlConfiguration
     * @return the file as a YamlConfiguration object.
     */
    @NotNull
    public static YamlConfiguration loadConfiguration(@NotNull File file) {
        YamlConfiguration config = new YamlConfiguration();

        try {
            config.load(file);
        } catch (FileNotFoundException ignored) {
        } catch (IOException | InvalidConfigurationException e) {
            System.out.println("Cannot load " + file + "\n" + e);
        }

        return config;
    }

    /**
     * Loads a YamlConfiguration from a file
     * @param reader the read to load into a YamlConfiguration
     * @return the reader as a YamlConfiguration object.
     */
    @NotNull
    public static YamlConfiguration loadConfiguration(@NotNull Reader reader) {
        YamlConfiguration config = new YamlConfiguration();
        try {
            config.load(reader);
        } catch (IOException | InvalidConfigurationException e) {
            System.out.println("Cannot load configuration from stream: \n" + e);
        }

        return config;
    }
}
