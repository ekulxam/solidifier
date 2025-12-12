package survivalblock.solidifier;

import folk.sisby.kaleido.api.WrappedConfig;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;

public class SolidifierConfig extends WrappedConfig {
    public static final SolidifierConfig INSTANCE = SolidifierConfig.createToml(FabricLoader.getInstance().getConfigDir(), "", Solidifier.MOD_ID, SolidifierConfig.class);

    public Identifier dirt = Solidifier.DIRT;
    public boolean changeGUI = false;
    public boolean changeAtlases = true;
    public boolean debug = false;
}
