package ejektaflex.bountiful.data.structure

import ejektaflex.bountiful.ext.getUnsortedList
import ejektaflex.bountiful.ext.setUnsortedListOfNbt
import net.minecraft.nbt.CompoundNBT
import net.minecraftforge.common.util.INBTSerializable

class DecreeList : INBTSerializable<CompoundNBT> {

    var ids: MutableList<String> = mutableListOf()

    override fun deserializeNBT(nbt: CompoundNBT) {
        ids = nbt.getUnsortedList("ids").map { tag ->
            tag.getString("id")
        }.toMutableList()
    }

    operator fun plus(other: DecreeList): DecreeList {
        return DecreeList().apply {
            this.ids = (this@DecreeList.ids + other.ids).toSet().toMutableList()
        }
    }

    override fun serializeNBT(): CompoundNBT {
        return CompoundNBT().apply {
            setUnsortedListOfNbt("ids", ids.map { id ->
                CompoundNBT().apply {
                    putString("id", id)
                }
            }.toSet())
        }
    }

}