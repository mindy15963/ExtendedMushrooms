package cech12.extendedmushrooms.item;

import cech12.extendedmushrooms.ExtendedMushrooms;
import cech12.extendedmushrooms.api.block.ExtendedMushroomsBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Comparator;
import java.util.function.Supplier;

public enum MushroomType implements IStringSerializable {

    BROWN_MUSHROOM(0, ()->Items.BROWN_MUSHROOM, ()->Blocks.BROWN_MUSHROOM_BLOCK, ()->Blocks.MUSHROOM_STEM, DyeColor.BROWN),
    RED_MUSHROOM(1, ()->Items.RED_MUSHROOM, ()->Blocks.RED_MUSHROOM_BLOCK, ()->Blocks.MUSHROOM_STEM, DyeColor.RED),
    GLOWSHROOM(2, ()->ExtendedMushroomsBlocks.GLOWSHROOM, ()->ExtendedMushroomsBlocks.GLOWSHROOM_CAP, ()->ExtendedMushroomsBlocks.GLOWSHROOM_STEM, DyeColor.BLUE,
            ()->ExtendedMushroomsBlocks.GLOWSHROOM_CAP.getLightValue(ExtendedMushroomsBlocks.GLOWSHROOM_CAP.getDefaultState()));

    private static final MushroomType[] VALUES = Arrays.stream(values()).sorted(Comparator.comparingInt(MushroomType::getId)).toArray(MushroomType[]::new);

    private final int id;
    private final Supplier<IItemProvider> item;
    private final Supplier<Block> capBlock;
    private final Supplier<Block> stemBlock;
    private final DyeColor color;
    private final Supplier<Integer> lightValue;

    MushroomType(int id, Supplier<IItemProvider> item, Supplier<Block> capBlock, Supplier<Block> stemBlock, @Nonnull DyeColor color) {
        this(id, item, capBlock, stemBlock, color, () -> 0);
    }

    MushroomType(int id, Supplier<IItemProvider> item, Supplier<Block> capBlock, Supplier<Block> stemBlock, @Nonnull DyeColor color, Supplier<Integer> lightValue) {
        this.id = id;
        this.item = item;
        this.capBlock = capBlock;
        this.stemBlock = stemBlock;
        this.color = color;
        this.lightValue = lightValue;
    }

    public int getId() {
        return this.id;
    }

    public Item getItem() {
        return this.item.get().asItem();
    }

    public Block getCapBlock() {
        return this.capBlock.get();
    }

    public Block getStemBlock() {
        return this.stemBlock.get();
    }

    public DyeColor getColor() {
        return this.color;
    }

    public int getLightValue() {
        return this.lightValue.get();
    }

    public ResourceLocation getSheepLootTable() {
        return new ResourceLocation(ExtendedMushrooms.MOD_ID, "entities/sheep/" + this.getItem().getRegistryName().getPath());
    }

    @Override
    public String getName() {
        return this.getItem().getRegistryName().getPath();
    }

    public static MushroomType byId(int id) {
        if (id < 0 || id >= VALUES.length) {
            id = 0;
        }
        return VALUES[id];
    }

    public static MushroomType byItemOrNull(Item item) {
        for (MushroomType mushroomType : VALUES) {
            if (mushroomType.getItem() == item) {
                return mushroomType;
            }
        }
        return null;
    }

    public static MushroomType byItem(Item item) {
        MushroomType type = byItemOrNull(item);
        if (type == null) {
            type = BROWN_MUSHROOM;
        }
        return type;
    }
}
