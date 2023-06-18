package dev.noeul.fabricmod.minecartmaxspeed.mixin;

import com.google.common.collect.ImmutableMap;
import dev.noeul.fabricmod.minecartmaxspeed.IAbstractMinecartEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.DefaultAttributeRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(DefaultAttributeRegistry.class)
public class DefaultAttributeRegistryMixin {
	@Redirect(
			method = "<clinit>",
			at = @At(
					value = "INVOKE",
					target = "Lcom/google/common/collect/ImmutableMap$Builder;build()Lcom/google/common/collect/ImmutableMap;",
					ordinal = 0
			)
	)
	private static ImmutableMap<EntityType<? extends Entity>, DefaultAttributeContainer> redirect$_clinit_(ImmutableMap.Builder<EntityType<? extends Entity>, DefaultAttributeContainer> builder) {
		return builder.put(EntityType.MINECART, IAbstractMinecartEntity.createAttributes().build())
				.put(EntityType.CHEST_MINECART, IAbstractMinecartEntity.createAttributes().build())
				.put(EntityType.FURNACE_MINECART, IAbstractMinecartEntity.createAttributes().build())
				.put(EntityType.TNT_MINECART, IAbstractMinecartEntity.createAttributes().build())
				.put(EntityType.HOPPER_MINECART, IAbstractMinecartEntity.createAttributes().build())
				.put(EntityType.SPAWNER_MINECART, IAbstractMinecartEntity.createAttributes().build())
				.put(EntityType.COMMAND_BLOCK_MINECART, IAbstractMinecartEntity.createAttributes().build())
				.build();
	}
}
