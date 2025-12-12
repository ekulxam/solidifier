package survivalblock.solidifier;

import folk.sisby.kaleido.lib.quiltconfig.api.values.ConfigSerializableObject;

public interface ConfigStringSerializable extends ConfigSerializableObject<String> {

    @Override
    ConfigSerializableObject<String> convertFrom(String string);

    @Override
    String getRepresentation();
}
