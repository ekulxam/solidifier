package survivalblock.solidifier.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.client.texture.AbstractTexture;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import survivalblock.solidifier.Solidifier;
import survivalblock.solidifier.SolidifierConfig;

import java.io.IOException;

@Mixin(TextureManager.class)
public abstract class TextureManagerMixin {

    @Shadow
    @Final
    private ResourceManager resourceContainer;

    @Shadow
    public abstract void registerTexture(Identifier id, AbstractTexture texture);

    @ModifyReturnValue(method = "getTexture", at = @At("RETURN"))
    private AbstractTexture soilify(AbstractTexture abstractTexture, Identifier id) {
        if (!SolidifierConfig.INSTANCE.changeGUI || Solidifier.COMPUTED.contains(id)) {
            return abstractTexture;
        }
        if (id.equals(Solidifier.textureId) || (id.getNamespace().equals(Identifier.DEFAULT_NAMESPACE) && id.getPath().startsWith("missing"))) {
            return abstractTexture;
        }
        if (Solidifier.blacklistIds != null && Solidifier.blacklistIds.contains(id)) {
            return abstractTexture;
        }

        NativeImage thisImage;
        try {
            thisImage = NativeImage.read(this.resourceContainer.open(id));
        } catch (IOException ioException) {
            if (SolidifierConfig.INSTANCE.debug) {
                Solidifier.LOGGER.error("Could not create a NativeImage for {}", id, ioException);
            }
            return abstractTexture;
        }

        NativeImage nativeImage = Solidifier.compute(thisImage, this.resourceContainer);
        if (nativeImage == null) {
            return abstractTexture;
        }

        NativeImageBackedTexture newDirt = new NativeImageBackedTexture(nativeImage);
        this.registerTexture(id, newDirt);
        Solidifier.COMPUTED.add(id);
        return newDirt;
    }
}
