package survivalblock.solidifier.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.SpriteContents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import survivalblock.solidifier.Solidifier;
import survivalblock.solidifier.SolidifierConfig;

@Mixin(SpriteContents.class)
public class SpriteContentsMixin {

    @ModifyVariable(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/texture/SpriteDimensions;height()I"), index = 3, argsOnly = true)
    private NativeImage modifyImageNow(NativeImage value) {
        if (!SolidifierConfig.INSTANCE.changeAtlases) {
            return value;
        }

        NativeImage dirt = Solidifier.compute(value, MinecraftClient.getInstance().getResourceManager());
        return dirt != null ? dirt : value;
    }
}
