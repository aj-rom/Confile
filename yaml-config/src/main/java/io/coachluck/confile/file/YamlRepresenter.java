/*
 *   Project: Confile
 *   File: YamlRepresenter.java
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

import io.coachluck.confile.ConfigurationSection;
import io.coachluck.confile.serialization.ConfigurationSerializable;
import io.coachluck.confile.serialization.ConfigurationSerialization;
import org.jetbrains.annotations.NotNull;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.representer.Representer;

import java.util.LinkedHashMap;
import java.util.Map;

public class YamlRepresenter extends Representer {
    public YamlRepresenter() {
        this.multiRepresenters.put(ConfigurationSection.class, new RepresentConfigurationSection());
        this.multiRepresenters.put(ConfigurationSerializable.class, new RepresentConfigurationSerializable());
        this.multiRepresenters.remove(Enum.class);
    }

    private class RepresentConfigurationSection extends RepresentMap {
        private RepresentConfigurationSection() {
        }

        @NotNull
        public Node representData(@NotNull Object data) {
            return super.representData(((ConfigurationSection) data).getValues(false));
        }
    }

    private class RepresentConfigurationSerializable extends RepresentMap {
        private RepresentConfigurationSerializable() { }

        @NotNull
        public Node representData(@NotNull Object data) {
            ConfigurationSerializable serializable = (ConfigurationSerializable) data;
            Map<String, Object> values = new LinkedHashMap();
            values.put("==", ConfigurationSerialization.getAlias(serializable.getClass()));
            values.putAll(serializable.serialize());
            return super.representData(values);
        }
    }
}