package com.mrh0.createaddition.compat.jei;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mrh0.createaddition.index.CABlocks;
import com.mrh0.createaddition.recipe.rolling.RollingRecipe;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import com.simibubi.create.compat.jei.category.CreateRecipeCategory;
import com.simibubi.create.compat.jei.category.animations.AnimatedMillstone;
import com.simibubi.create.content.contraptions.components.crusher.AbstractCrushingRecipe;
import com.simibubi.create.content.contraptions.processing.ProcessingOutput;
import com.simibubi.create.foundation.gui.AllGuiTextures;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class RollingMillCategory extends CARecipeCategory<RollingRecipe> {

	private AnimatedRollingMill rolling_mill = new AnimatedRollingMill();

	public RollingMillCategory() {
		super(itemIcon(CABlocks.ROLLING_MILL.get()), emptyBackground(177, 53));
	}

	@Override
	public Class<? extends RollingRecipe> getRecipeClass() {
		return RollingRecipe.class;
	}

	@Override
	public void setIngredients(RollingRecipe recipe, IIngredients ingredients) {
		ingredients.setInputIngredients(recipe.getIngredients());
		ArrayList<ItemStack> stacks = new ArrayList<>();
		stacks.add(recipe.getRecipeOutput());
		ingredients.setOutputs(VanillaTypes.ITEM, stacks);
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, RollingRecipe recipe, IIngredients ingredients) {
		IGuiItemStackGroup itemStacks = recipeLayout.getItemStacks();
		itemStacks.init(0, true, 14, 8);
		itemStacks.set(0, Arrays.asList(recipe.getIngredient().getMatchingStacks()));

		ItemStack result = recipe.getRecipeOutput();
		int yOffset = (0 / 2) * -19;

		itemStacks.init(1, false, 139, 27 + yOffset);
		itemStacks.set(1, result);

		//addStochasticTooltip(itemStacks, results);
	}

	@Override
	public void draw(RollingRecipe recipe, MatrixStack matrixStack, double mouseX, double mouseY) {
		int size = 1;//recipe.getRollableResultsAsItemStacks().size();

		AllGuiTextures.JEI_SLOT.draw(matrixStack, 14, 8);
		AllGuiTextures.JEI_ARROW.draw(matrixStack, 85, 32);
		AllGuiTextures.JEI_DOWN_ARROW.draw(matrixStack, 43, 4);
		rolling_mill.draw(matrixStack, 48, 27);

		if (size == 1) {
			getRenderedSlot(recipe, 0).draw(matrixStack, 139, 27);
			return;
		}

		for (int i = 0; i < size; i++) {
			int xOffset = i % 2 == 0 ? 0 : 19;
			int yOffset = (i / 2) * -19;
			getRenderedSlot(recipe, i).draw(matrixStack, 133 + xOffset, 27 + yOffset);
		}
	}
}