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
    public FileConfiguration() {
    }

    public FileConfiguration(@Nullable Configuration defaults) {
        super(defaults);
    }

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

    public void save(@NotNull String file) throws IOException {
        this.save(new File(file));
    }

    @NotNull
    public abstract String saveToString();

    public void load(@NotNull File file) throws FileNotFoundException, IOException, InvalidConfigurationException {
        FileInputStream stream = new FileInputStream(file);
        this.load(new InputStreamReader(stream, StandardCharsets.UTF_8));
    }

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

    public void load(@NotNull String file) throws IOException, InvalidConfigurationException {
        this.load(new File(file));
    }

    public abstract void loadFromString(@NotNull String var1) throws InvalidConfigurationException;

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
