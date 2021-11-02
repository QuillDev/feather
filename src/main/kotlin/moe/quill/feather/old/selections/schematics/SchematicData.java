package moe.quill.feather.old.selections.schematics;

import org.bukkit.Bukkit;
import org.bukkit.block.data.BlockData;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public record SchematicData(List<List<List<BlockData>>> schematicTypeList) implements ConfigurationSerializable {

    public static SchematicData deserialize(Map<String, Object> map) {
        final var string3DList = (List<List<List<String>>>) map.getOrDefault("block-data", new ArrayList<>());

        final var mat3DList = string3DList.stream()
                .map(yList -> yList.stream()
                        .map(xList -> xList.stream()
                                .map(Bukkit::createBlockData)
                                .collect(Collectors.toList())
                        ).collect(Collectors.toList())
                ).collect(Collectors.toList());

        return new SchematicData(mat3DList);
    }

    @Override
    public @NotNull
    Map<String, Object> serialize() {
        final var string3DList = schematicTypeList.stream()
                .map(yList -> yList.stream()
                        .map(xList -> xList.stream()
                                .map(BlockData::getAsString)
                                .collect(Collectors.toList())
                        ).collect(Collectors.toList())
                ).collect(Collectors.toList());

        return new HashMap<>() {{
            put("block-data", string3DList);
        }};
    }

    public List<List<List<BlockData>>> getBlockData() {
        return schematicTypeList;
    }
}
