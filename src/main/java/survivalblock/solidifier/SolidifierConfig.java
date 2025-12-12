package survivalblock.solidifier;

import folk.sisby.kaleido.api.WrappedConfig;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class SolidifierConfig extends WrappedConfig {
    public static final SolidifierConfig INSTANCE = SolidifierConfig.createToml(FabricLoader.getInstance().getConfigDir(), "", Solidifier.MOD_ID, SolidifierConfig.class);

    public String texture = Solidifier.DIRT.getPath();
    public boolean changeGUI = false;
    public boolean changeAtlases = true;
    public boolean debug = false;
    public List<String> textureBlacklist = new ArrayList<>();

    public void updateValues() {
        Solidifier.textureId = Identifier.tryParse(this.texture);
        Solidifier.blacklistIds = textureBlacklist.stream()
                .map(Identifier::tryParse)
                .filter(Objects::nonNull)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
