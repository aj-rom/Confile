package core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import core.serialization.ConfigurationSerializable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MemorySection implements ConfigurationSection {
    protected final Map<String, Object> map = new LinkedHashMap();
    private final Configuration root;
    private final ConfigurationSection parent;
    private final String path;
    private final String fullPath;

    protected MemorySection() {
        if (!(this instanceof Configuration)) {
            throw new IllegalStateException("Cannot construct a root core.MemorySection when not a Configuration");
        } else {
            this.path = "";
            this.fullPath = "";
            this.parent = null;
            this.root = (Configuration)this;
        }
    }

    protected MemorySection(@NotNull ConfigurationSection parent, @NotNull String path) {
        this.path = path;
        this.parent = parent;
        this.root = parent.getRoot();
        this.fullPath = createPath(parent, path);
    }

    @NotNull
    public Set<String> getKeys(boolean deep) {
        Set<String> result = new LinkedHashSet();
        Configuration root = this.getRoot();
        if (root != null && root.options().copyDefaults()) {
            ConfigurationSection defaults = this.getDefaultSection();
            if (defaults != null) {
                result.addAll(defaults.getKeys(deep));
            }
        }

        this.mapChildrenKeys(result, this, deep);
        return result;
    }

    @NotNull
    public Map<String, Object> getValues(boolean deep) {
        Map<String, Object> result = new LinkedHashMap();
        Configuration root = this.getRoot();
        if (root != null && root.options().copyDefaults()) {
            ConfigurationSection defaults = this.getDefaultSection();
            if (defaults != null) {
                result.putAll(defaults.getValues(deep));
            }
        }

        this.mapChildrenValues(result, this, deep);
        return result;
    }

    public boolean contains(@NotNull String path) {
        return this.contains(path, false);
    }

    public boolean contains(@NotNull String path, boolean ignoreDefault) {
        return (ignoreDefault ? this.get(path, (Object)null) : this.get(path)) != null;
    }

    public boolean isSet(@NotNull String path) {
        Configuration root = this.getRoot();
        if (root == null) {
            return false;
        } else if (root.options().copyDefaults()) {
            return this.contains(path);
        } else {
            return this.get(path, (Object)null) != null;
        }
    }

    @NotNull
    public String getCurrentPath() {
        return this.fullPath;
    }

    @NotNull
    public String getName() {
        return this.path;
    }

    @Nullable
    public Configuration getRoot() {
        return this.root;
    }

    @Nullable
    public ConfigurationSection getParent() {
        return this.parent;
    }

    public void addDefault(@NotNull String path, @Nullable Object value) {
        Configuration root = this.getRoot();
        if (root == null) {
            throw new IllegalStateException("Cannot add default without root");
        } else if (root == this) {
            throw new UnsupportedOperationException("Unsupported addDefault(String, Object) implementation");
        } else {
            root.addDefault(createPath(this, path), value);
        }
    }

    @Nullable
    public ConfigurationSection getDefaultSection() {
        Configuration root = this.getRoot();
        Configuration defaults = root == null ? null : root.getDefaults();
        return defaults != null && defaults.isConfigurationSection(this.getCurrentPath()) ? defaults.getConfigurationSection(this.getCurrentPath()) : null;
    }

    public void set(@NotNull String path, @Nullable Object value) {
        Configuration root = this.getRoot();
        if (root == null) {
            throw new IllegalStateException("Cannot use section without a root");
        } else {
            char separator = root.options().pathSeparator();
            int i1 = -1;
            ConfigurationSection section = this;

            int i2;
            String key;
            while((i1 = path.indexOf(separator, i2 = i1 + 1)) != -1) {
                key = path.substring(i2, i1);
                ConfigurationSection subSection = section.getConfigurationSection(key);
                if (subSection == null) {
                    if (value == null) {
                        return;
                    }

                    section = section.createSection(key);
                } else {
                    section = subSection;
                }
            }

            key = path.substring(i2);
            if (section == this) {
                if (value == null) {
                    this.map.remove(key);
                } else {
                    this.map.put(key, value);
                }
            } else {
                section.set(key, value);
            }

        }
    }

    @Nullable
    public Object get(@NotNull String path) {
        return this.get(path, this.getDefault(path));
    }

    @Nullable
    public Object get(@NotNull String path, @Nullable Object def) {
        if (path.length() == 0) {
            return this;
        } else {
            Configuration root = this.getRoot();
            if (root == null) {
                throw new IllegalStateException("Cannot access section without a root");
            } else {
                char separator = root.options().pathSeparator();
                int i1 = -1;
                Object section = this;

                int i2;
                while((i1 = path.indexOf(separator, i2 = i1 + 1)) != -1) {
                    section = ((ConfigurationSection)section).getConfigurationSection(path.substring(i2, i1));
                    if (section == null) {
                        return def;
                    }
                }

                String key = path.substring(i2);
                if (section == this) {
                    Object result = this.map.get(key);
                    return result == null ? def : result;
                } else {
                    return ((ConfigurationSection)section).get(key, def);
                }
            }
        }
    }

    @NotNull
    public ConfigurationSection createSection(@NotNull String path) {
        Configuration root = this.getRoot();
        if (root == null) {
            throw new IllegalStateException("Cannot create section without a root");
        } else {
            char separator = root.options().pathSeparator();
            int i1 = -1;
            ConfigurationSection section = this;

            int i2;
            String key;
            while((i1 = path.indexOf(separator, i2 = i1 + 1)) != -1) {
                key = path.substring(i2, i1);
                ConfigurationSection subSection = section.getConfigurationSection(key);
                if (subSection == null) {
                    section = section.createSection(key);
                } else {
                    section = subSection;
                }
            }

            key = path.substring(i2);
            if (section == this) {
                ConfigurationSection result = new MemorySection(this, key);
                this.map.put(key, result);
                return result;
            } else {
                return section.createSection(key);
            }
        }
    }

    @NotNull
    public ConfigurationSection createSection(@NotNull String path, @NotNull Map<?, ?> map) {
        ConfigurationSection section = this.createSection(path);
        Iterator var5 = map.entrySet().iterator();

        while(var5.hasNext()) {
            Entry<?, ?> entry = (Entry)var5.next();
            if (entry.getValue() instanceof Map) {
                section.createSection(entry.getKey().toString(), (Map)entry.getValue());
            } else {
                section.set(entry.getKey().toString(), entry.getValue());
            }
        }

        return section;
    }

    @Nullable
    public String getString(@NotNull String path) {
        Object def = this.getDefault(path);
        return this.getString(path, def != null ? def.toString() : null);
    }

    @Nullable
    public String getString(@NotNull String path, @Nullable String def) {
        Object val = this.get(path, def);
        return val != null ? val.toString() : def;
    }

    public boolean isString(@NotNull String path) {
        Object val = this.get(path);
        return val instanceof String;
    }

    @Nullable
    public List<?> getList(@NotNull String path) {
        Object def = this.getDefault(path);
        return this.getList(path, def instanceof List ? (List)def : null);
    }

    @Nullable
    public List<?> getList(@NotNull String path, @Nullable List<?> def) {
        Object val = this.get(path, def);
        return (List)(val instanceof List ? val : def);
    }

    public boolean isList(@NotNull String path) {
        Object val = this.get(path);
        return val instanceof List;
    }

    @NotNull
    public ArrayList<String> getStringArrayList(@NotNull String path) {
        return (ArrayList<String>) getStringList(path);
    }

    @NotNull
    public List<String> getStringList(@NotNull String path) {
        List<?> list = this.getList(path);
        if (list == null) {
            return new ArrayList(0);
        } else {
            List<String> result = new ArrayList();
            Iterator var5 = list.iterator();

            while(true) {
                Object object;
                do {
                    if (!var5.hasNext()) {
                        return result;
                    }

                    object = var5.next();
                } while(!(object instanceof String) && !this.isPrimitiveWrapper(object));

                result.add(String.valueOf(object));
            }
        }
    }

    @NotNull
    public List<Character> getCharacterList(@NotNull String path) {
        List<?> list = this.getList(path);
        if (list == null) {
            return new ArrayList(0);
        } else {
            List<Character> result = new ArrayList();
            Iterator var5 = list.iterator();

            while(var5.hasNext()) {
                Object object = var5.next();
                if (object instanceof Character) {
                    result.add((Character)object);
                } else if (object instanceof String) {
                    String str = (String)object;
                    if (str.length() == 1) {
                        result.add(str.charAt(0));
                    }
                } else if (object instanceof Number) {
                    result.add((char)((Number)object).intValue());
                }
            }

            return result;
        }
    }

    @NotNull
    public List<Map<?, ?>> getMapList(@NotNull String path) {
        List<?> list = this.getList(path);
        List<Map<?, ?>> result = new ArrayList();
        if (list == null) {
            return result;
        } else {
            Iterator var5 = list.iterator();

            while(var5.hasNext()) {
                Object object = var5.next();
                if (object instanceof Map) {
                    result.add((Map)object);
                }
            }

            return result;
        }
    }

    @Nullable
    public <T> T getObject(@NotNull String path, @NotNull Class<T> clazz) {
        Object def = this.getDefault(path);
        return this.getObject(path, clazz, def != null && clazz.isInstance(def) ? clazz.cast(def) : null);
    }

    @Nullable
    public <T> T getObject(@NotNull String path, @NotNull Class<T> clazz, @Nullable T def) {
        Object val = this.get(path, def);
        return val != null && clazz.isInstance(val) ? clazz.cast(val) : def;
    }

    @Nullable
    public <T extends ConfigurationSerializable> T getSerializable(@NotNull String path, @NotNull Class<T> clazz) {
        return this.getObject(path, clazz);
    }

    @Nullable
    public <T extends ConfigurationSerializable> T getSerializable(@NotNull String path, @NotNull Class<T> clazz, @Nullable T def) {
        return this.getObject(path, clazz, def);
    }

    @Nullable
    public ConfigurationSection getConfigurationSection(@NotNull String path) {
        Object val = this.get(path, (Object)null);
        if (val != null) {
            return val instanceof ConfigurationSection ? (ConfigurationSection)val : null;
        } else {
            val = this.get(path, this.getDefault(path));
            return val instanceof ConfigurationSection ? this.createSection(path) : null;
        }
    }

    public boolean isConfigurationSection(@NotNull String path) {
        Object val = this.get(path);
        return val instanceof ConfigurationSection;
    }

    protected boolean isPrimitiveWrapper(@Nullable Object input) {
        return input instanceof Integer || input instanceof Boolean || input instanceof Character || input instanceof Byte || input instanceof Short || input instanceof Double || input instanceof Long || input instanceof Float;
    }

    @Nullable
    protected Object getDefault(@NotNull String path) {
        Configuration root = this.getRoot();
        Configuration defaults = root == null ? null : root.getDefaults();
        return defaults == null ? null : defaults.get(createPath(this, path));
    }

    protected void mapChildrenKeys(@NotNull Set<String> output, @NotNull ConfigurationSection section, boolean deep) {
        Iterator var6;
        if (section instanceof MemorySection) {
            MemorySection sec = (MemorySection)section;
            var6 = sec.map.entrySet().iterator();

            while(var6.hasNext()) {
                Entry<String, Object> entry = (Entry)var6.next();
                output.add(createPath(section, (String)entry.getKey(), this));
                if (deep && entry.getValue() instanceof ConfigurationSection) {
                    ConfigurationSection subsection = (ConfigurationSection)entry.getValue();
                    this.mapChildrenKeys(output, subsection, deep);
                }
            }
        } else {
            Set<String> keys = section.getKeys(deep);
            var6 = keys.iterator();

            while(var6.hasNext()) {
                String key = (String)var6.next();
                output.add(createPath(section, key, this));
            }
        }

    }

    protected void mapChildrenValues(@NotNull Map<String, Object> output,
                                     @NotNull ConfigurationSection section, boolean deep) {
        Entry entry;
        Iterator var6;
        if (section instanceof MemorySection) {
            MemorySection sec = (MemorySection)section;
            var6 = sec.map.entrySet().iterator();

            while(var6.hasNext()) {
                entry = (Entry)var6.next();
                String childPath = createPath(section, (String)entry.getKey(), this);
                output.remove(childPath);
                output.put(childPath, entry.getValue());
                if (entry.getValue() instanceof ConfigurationSection && deep) {
                    this.mapChildrenValues(output, (ConfigurationSection)entry.getValue(), deep);
                }
            }
        } else {
            Map<String, Object> values = section.getValues(deep);
            var6 = values.entrySet().iterator();

            while(var6.hasNext()) {
                entry = (Entry)var6.next();
                output.put(createPath(section, (String)entry.getKey(), this), entry.getValue());
            }
        }

    }

    @NotNull
    public static String createPath(@NotNull ConfigurationSection section, @Nullable String key) {
        return createPath(section, key, section == null ? null : section.getRoot());
    }

    @NotNull
    public static String createPath(@NotNull ConfigurationSection section, @Nullable String key, @Nullable ConfigurationSection relativeTo) {
        Configuration root = section.getRoot();
        if (root == null) {
            throw new IllegalStateException("Cannot create path without a root");
        } else {
            char separator = root.options().pathSeparator();
            StringBuilder builder = new StringBuilder();
            if (section != null) {
                for(ConfigurationSection parent = section; parent != null && parent != relativeTo; parent = parent.getParent()) {
                    if (builder.length() > 0) {
                        builder.insert(0, separator);
                    }

                    builder.insert(0, parent.getName());
                }
            }

            if (key != null && key.length() > 0) {
                if (builder.length() > 0) {
                    builder.append(separator);
                }

                builder.append(key);
            }

            return builder.toString();
        }
    }

    public String toString() {
        Configuration root = this.getRoot();
        return this.getClass().getSimpleName()
                + "[path='" + this.getCurrentPath()
                + "', root='" + (root == null ? null : root.getClass().getSimpleName()) + "']";
    }
}
