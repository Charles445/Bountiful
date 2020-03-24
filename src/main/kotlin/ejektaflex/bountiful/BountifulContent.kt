package ejektaflex.bountiful

import ejektaflex.bountiful.block.BlockBountyBoard
import ejektaflex.bountiful.block.BoardTileEntity
import ejektaflex.bountiful.gui.BoardContainer
import ejektaflex.bountiful.item.ItemBounty
import ejektaflex.bountiful.item.ItemDecree
import net.minecraft.inventory.container.ContainerType
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraft.tileentity.TileEntityType
import net.minecraftforge.registries.ObjectHolder

object BountifulContent {

    object Blocks {
        val BOUNTYBOARD = BlockBountyBoard().setRegistryName("bountyboard")

        @ObjectHolder(BountifulMod.MODID + ":bounty-te")
        @JvmStatic
        lateinit var BOUNTYTILEENTITY: TileEntityType<BoardTileEntity>
    }

    object Items {
        val BOUNTY = ItemBounty().setRegistryName("bounty")
        val BOUNTYBOARD = BlockItem(Blocks.BOUNTYBOARD, Item.Properties().group(BountifulGroup)).setRegistryName("bountyboard")
        val DECREE = ItemDecree().setRegistryName("decree")
    }

    object Guis {
        @ObjectHolder(BountifulMod.MODID + ":bountyboard")
        @JvmStatic
        lateinit var BOARDCONTAINER: ContainerType<BoardContainer>
    }

    object BountifulGroup : ItemGroup(BountifulMod.MODID) {
        override fun createIcon() = ItemStack(Items.DECREE)
    }

}