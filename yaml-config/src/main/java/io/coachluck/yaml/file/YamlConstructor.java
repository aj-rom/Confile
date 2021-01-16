package io.coachluck.yaml.file;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import core.serialization.ConfigurationSerialization;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.yaml.snakeyaml.constructor.SafeConstructor;
import org.yaml.snakeyaml.error.YAMLException;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.Tag;

public class YamlConstructor extends SafeConstructor {
    public YamlConstructor() {
        this.yamlConstructors.put(Tag.MAP, new ConstructCustomObject());
    }

    private class ConstructCustomObject extends ConstructYamlMap {
        private ConstructCustomObject() { }

        @Nullable
        public Object construct(@NotNull Node node) {
            if (node.isTwoStepsConstruction()) {
                throw new YAMLException("Unexpected referential mapping structure. Node: " + node);
            } else {
                Map<?, ?> raw = (Map)super.construct(node);
                if (!raw.containsKey("==")) {
                    return raw;
                } else {
                    Map<String, Object> typed = new LinkedHashMap(raw.size());
                    Iterator var5 = raw.entrySet().iterator();

                    while(var5.hasNext()) {
                        Entry<?, ?> entry = (Entry)var5.next();
                        typed.put(entry.getKey().toString(), entry.getValue());
                    }

                    try {
                        return ConfigurationSerialization.deserializeObject(typed);
                    } catch (IllegalArgumentException var6) {
                        throw new YAMLException("Could not deserialize object", var6);
                    }
                }
            }
        }

        public void construct2ndStep(@NotNull Node node, @NotNull Object object) {
            throw new YAMLException("Unexpected referential mapping structure. Node: " + node);
        }
    }
}
