package com.rawsome1234.tape.util;

import com.rawsome1234.tape.Tape;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;

public class TapeTags {

    public static class Entities {

        public static final Tags.IOptionalNamedTag<EntityType<?>> SACK =
                createTag("kidnapping_sack");

        private static Tags.IOptionalNamedTag<EntityType<?>> createTag(String name) {
            return EntityTypeTags.createOptional(new ResourceLocation(Tape.MOD_ID, name));
        }

        private static Tags.IOptionalNamedTag<EntityType<?>> createForgeTag(String name) {
            return EntityTypeTags.createOptional(new ResourceLocation("forge", name));
        }

    }

    public static class Blocks {

        private static Tags.IOptionalNamedTag<Block> createTag(String name) {
            return BlockTags.createOptional(new ResourceLocation(Tape.MOD_ID, name));
        }

        private static Tags.IOptionalNamedTag<Block> createForgeTag(String name) {
            return BlockTags.createOptional(new ResourceLocation("forge", name));
        }

    }

    public static class Items {

        public static final Tags.IOptionalNamedTag<Item> TAPE =
                createTag("tape");

        public static final Tags.IOptionalNamedTag<Item> SLIP =
                createTag("slip");

        public static final Tags.IOptionalNamedTag<Item> BEANS =
                createTag("beans");


        public static final Tags.IOptionalNamedTag<Item> ALLOWED_COFFEE_FLUIDS =
                createTag("coffee_buckets");

        public static final Tags.IOptionalNamedTag<Item> COFFEES =
                createTag("coffees");


        private static Tags.IOptionalNamedTag<Item> createTag(String name) {
            return ItemTags.createOptional(new ResourceLocation(Tape.MOD_ID, name));
        }

        private static Tags.IOptionalNamedTag<Item> createForgeTag(String name) {
            return ItemTags.createOptional(new ResourceLocation("forge", name));
        }

    }

}
