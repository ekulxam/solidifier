package survivalblock.solidifier.mixin;

import folk.sisby.kaleido.lib.quiltconfig.api.values.ComplexConfigValue;
import folk.sisby.kaleido.lib.quiltconfig.api.values.ConfigSerializableObject;
import folk.sisby.kaleido.lib.quiltconfig.api.values.TrackedValue;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import survivalblock.solidifier.ConfigStringSerializable;
import survivalblock.solidifier.Solidifier;

@SuppressWarnings({"unchecked", "DataFlowIssue"})
@Environment(EnvType.CLIENT)
@Implements(@Interface(iface = ConfigStringSerializable.class, prefix = "solidifier$", remap = Interface.Remap.NONE))
@Mixin(Identifier.class)
public abstract class IdentifierMixin {

    @Shadow
    public abstract String getNamespace();

    @Shadow
    public abstract String getPath();

    public ConfigSerializableObject<String> solidifier$convertFrom(String str) {
        Identifier id = Identifier.tryParse(str);
        if (id == null) {
            id = Solidifier.DIRT;
        }
        return (ConfigSerializableObject) (Object) id;
    }

    public String solidifier$getRepresentation() {
        return this.toString();
    }

    public ComplexConfigValue solidifier$copy() {
        return (ComplexConfigValue) (Object) Identifier.of(this.getNamespace(), this.getPath());
    }
}
