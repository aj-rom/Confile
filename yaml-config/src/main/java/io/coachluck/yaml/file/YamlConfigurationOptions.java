package io.coachluck.yaml.file;

import io.coachluck.core.file.FileConfigurationOptions;
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
