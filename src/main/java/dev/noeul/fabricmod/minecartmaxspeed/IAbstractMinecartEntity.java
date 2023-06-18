package dev.noeul.fabricmod.minecartmaxspeed;

import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.registry.entry.RegistryEntry;

public interface IAbstractMinecartEntity {
	static DefaultAttributeContainer.Builder createAttributes() {
		return DefaultAttributeContainer.builder()
				.add(MinecartMaxSpeedAttribute.maxSpeed);
	}

	default EntityAttributeInstance getAttributeInstance(RegistryEntry<EntityAttribute> attribute) {
		return this.getAttributes().getCustomInstance(attribute);
	}

	default double getAttributeValue(RegistryEntry<EntityAttribute> attribute) {
		return this.getAttributes().getValue(attribute);
	}

	default double getAttributeBaseValue(RegistryEntry<EntityAttribute> attribute) {
		return this.getAttributes().getBaseValue(attribute);
	}

	AttributeContainer getAttributes();
}
