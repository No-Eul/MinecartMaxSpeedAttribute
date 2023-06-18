package dev.noeul.fabricmod.minecartmaxspeed;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.vehicle.AbstractMinecartEntity;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.command.AttributeCommand;

public interface AttributeCommandHelper {
	static EntityAttributeInstance getAttributeInstance(Entity entity, RegistryEntry<EntityAttribute> attribute) throws CommandSyntaxException {
		EntityAttributeInstance entityAttributeInstance = getAttributes(entity).getCustomInstance(attribute);
		if (entityAttributeInstance == null) {
			throw AttributeCommand.NO_ATTRIBUTE_EXCEPTION.create(entity.getName(), AttributeCommand.getName(attribute));
		} else {
			return entityAttributeInstance;
		}
	}

	static double getAttributeValue(Entity entity, RegistryEntry<EntityAttribute> attribute) throws CommandSyntaxException {
		return getAttributes(entity).getValue(attribute);
	}

	static double getAttributeBaseValue(Entity entity, RegistryEntry<EntityAttribute> attribute) throws CommandSyntaxException {
		return getAttributes(entity).getBaseValue(attribute);
	}

	static AttributeContainer getAttributes(Entity entity) throws CommandSyntaxException {
		if (entity instanceof LivingEntity livingEntity)
			return livingEntity.getAttributes();
		else if (entity instanceof AbstractMinecartEntity minecartEntity)
			return ((IAbstractMinecartEntity) minecartEntity).getAttributes();
		throw AttributeCommand.ENTITY_FAILED_EXCEPTION.create(entity.getName());
	}

	static Entity getLivingEntityWithAttribute(Entity entity, RegistryEntry<EntityAttribute> attribute) throws CommandSyntaxException {
		if (!getAttributes(entity).hasAttribute(attribute)) {
			throw AttributeCommand.NO_ATTRIBUTE_EXCEPTION.create(entity.getName(), AttributeCommand.getName(attribute));
		} else {
			return entity;
		}
	}
}
