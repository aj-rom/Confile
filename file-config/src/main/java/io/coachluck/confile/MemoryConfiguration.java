package io.coachluck.confile;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
        Iterator var3 = defaults.entrySet().iterator();

        while(var3.hasNext()) {
            Entry<String, Object> entry = (Entry) var3.next();
            this.addDefault(entry.getKey(), entry.getValue());
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