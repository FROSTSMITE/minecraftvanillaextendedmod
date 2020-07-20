
package net.mcreator.enhancedvanilla.item;

import net.minecraftforge.registries.ObjectHolder;

import net.minecraft.world.World;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.item.UseAction;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Item;
import net.minecraft.item.Food;
import net.minecraft.entity.LivingEntity;
import net.minecraft.client.util.ITooltipFlag;

import net.mcreator.enhancedvanilla.procedures.CrackedeggitemFoodEatenProcedure;
import net.mcreator.enhancedvanilla.EnhancedvanillaModElements;

import java.util.List;

@EnhancedvanillaModElements.ModElement.Tag
public class CrackedeggitemItem extends EnhancedvanillaModElements.ModElement {
	@ObjectHolder("enhancedvanilla:crackedeggitem")
	public static final Item block = null;
	public CrackedeggitemItem(EnhancedvanillaModElements instance) {
		super(instance, 1);
	}

	@Override
	public void initElements() {
		elements.items.add(() -> new FoodItemCustom());
	}
	public static class FoodItemCustom extends Item {
		public FoodItemCustom() {
			super(new Item.Properties().group(ItemGroup.FOOD).maxStackSize(64).food((new Food.Builder()).hunger(1).saturation(0.1f).build()));
			setRegistryName("crackedeggitem");
		}

		@Override
		public int getUseDuration(ItemStack stack) {
			return 45;
		}

		@Override
		public UseAction getUseAction(ItemStack par1ItemStack) {
			return UseAction.DRINK;
		}

		@Override
		public void addInformation(ItemStack itemstack, World world, List<ITextComponent> list, ITooltipFlag flag) {
			super.addInformation(itemstack, world, list, flag);
			list.add(new StringTextComponent("an egg fresher than the chicken it came from."));
		}

		@Override
		public ItemStack onItemUseFinish(ItemStack itemStack, World world, LivingEntity entity) {
			ItemStack retval = super.onItemUseFinish(itemStack, world, entity);
			int x = (int) entity.getPosX();
			int y = (int) entity.getPosY();
			int z = (int) entity.getPosZ();
			{
				java.util.HashMap<String, Object> $_dependencies = new java.util.HashMap<>();
				$_dependencies.put("entity", entity);
				CrackedeggitemFoodEatenProcedure.executeProcedure($_dependencies);
			}
			return retval;
		}
	}
}
