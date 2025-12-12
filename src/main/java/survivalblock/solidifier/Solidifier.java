package survivalblock.solidifier;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.ColorHelper;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Solidifier implements ClientModInitializer {
	public static final String MOD_ID = "solidifier";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static final Identifier DIRT = Identifier.ofVanilla("textures/block/dirt.png");
    public static final List<Identifier> COMPUTED = new ArrayList<>();

	@Override
	public void onInitializeClient() {
        SolidifierConfig.INSTANCE.registerCallback(config -> COMPUTED.clear());
	}

    @Nullable
    public static NativeImage compute(NativeImage thisImage, ResourceManager resourceManager) {
        NativeImage dirtImage;
        try {
            dirtImage = NativeImage.read(resourceManager.open(SolidifierConfig.INSTANCE.dirt));
        } catch (IOException ioException) {
            if (SolidifierConfig.INSTANCE.debug) {
                Solidifier.LOGGER.error("Could not create a NativeImage for {}", SolidifierConfig.INSTANCE.dirt, ioException);
            }
            return null;
        }

        int width = thisImage.getWidth();
        int height = thisImage.getHeight();
        int dirtWidth = dirtImage.getWidth();
        int dirtHeight = dirtImage.getHeight();
        NativeImage soilfied;
        try {
            soilfied = new NativeImage(width, height, false);
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    soilfied.setColor(i, j, ColorHelper.Abgr.withAlpha(ColorHelper.Abgr.getAlpha(thisImage.getColor(i, j)), dirtImage.getColor(i % dirtWidth, j % dirtHeight)));
                }
            }
        } catch (Throwable throwable) {
            if (SolidifierConfig.INSTANCE.debug) {
                Solidifier.LOGGER.error("An error occurred while generating solidified texture", throwable);
            }
            return null;
        }

        return soilfied;
    }
}