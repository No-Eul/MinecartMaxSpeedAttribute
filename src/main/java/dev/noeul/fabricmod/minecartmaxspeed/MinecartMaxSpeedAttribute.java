package dev.noeul.fabricmod.minecartmaxspeed;

import com.google.common.collect.ImmutableList;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.vehicle.AbstractMinecartEntity;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.server.MinecraftServer;

public class MinecartMaxSpeedAttribute implements ModInitializer {
	public static final ModContainer MOD = FabricLoader.getInstance()
			.getModContainer("minecart-max-speed")
			.orElseThrow(NullPointerException::new);
	public static final String ID = MOD.getMetadata().getId();
	public static ImmutableList<EntityType<? extends AbstractMinecartEntity>> minecarts;

	public static RegistryEntry<EntityAttribute> maxSpeed;

	@Override
	public void onInitialize() {
		minecarts = ImmutableList.<EntityType<? extends AbstractMinecartEntity>>builder()
				.add(EntityType.MINECART)
				.add(EntityType.CHEST_MINECART)
				.add(EntityType.FURNACE_MINECART)
				.add(EntityType.HOPPER_MINECART)
				.add(EntityType.SPAWNER_MINECART)
				.add(EntityType.TNT_MINECART)
				.add(EntityType.COMMAND_BLOCK_MINECART)
				.build();
	}

	public static boolean areMinecartImprovementsEnabled(MinecraftServer server) {
		return server.getSaveProperties().getEnabledFeatures().contains(FeatureFlags.MINECART_IMPROVEMENTS);
	}
}
