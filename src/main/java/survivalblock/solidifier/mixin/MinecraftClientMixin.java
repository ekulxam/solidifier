package survivalblock.solidifier.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import survivalblock.solidifier.Solidifier;

import java.util.concurrent.CompletableFuture;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {

    @WrapOperation(method = "reloadResources(ZLnet/minecraft/client/MinecraftClient$LoadingContext;)Ljava/util/concurrent/CompletableFuture;", at = @At(value = "NEW", target = "()Ljava/util/concurrent/CompletableFuture;"))
    private CompletableFuture<Void> clearTextureCache(Operation<CompletableFuture<Void>> original) {
        Solidifier.COMPUTED.clear();
        return original.call();
    }
}
