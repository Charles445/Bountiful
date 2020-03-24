package ejektaflex.bountiful.data.bounty.checkers

import ejektaflex.bountiful.BountifulStats
import ejektaflex.bountiful.advancement.BountifulTriggers
import ejektaflex.bountiful.data.bounty.BountyData
import ejektaflex.bountiful.data.bounty.BountyEntry
import ejektaflex.bountiful.data.bounty.BountyProgress
import ejektaflex.bountiful.data.bounty.IBountyReward
import ejektaflex.bountiful.util.ValueRegistry
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.ServerPlayerEntity
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance

object CheckerRegistry : ValueRegistry<KClass<out CheckHandler<*>>>() {

    init {
        add(StackLikeCheckHandler::class)
        add(EntityCheckHandler::class)
    }

    fun tryCashIn(player: PlayerEntity, data: BountyData): Boolean {
        val checkers = content.map {
            val inst = it.createInstance()
            inst.initialize(player, data)
            inst
        }

        println("Checkers: $checkers")
        val passesAll = checkers.all { it.isComplete }
        println("Passes all checks?: $passesAll")

        if (passesAll) {
            checkers.forEach {
                it.fulfill()
            }

            for (reward in data.rewards.content) {
                (reward as IBountyReward).reward(player)
            }

            // Increase stats
            player.addStat(BountifulStats.BOUNTIES_DONE, 1)
            player.addStat(data.rarityEnum.stat, 1)

            if (!player.world.isRemote) {

                BountifulTriggers.COMPLETE_STARTER.trigger((player as ServerPlayerEntity).advancements)

                if (data.timeLeft(player.world) < (5 * 20)) {
                    BountifulTriggers.PROCRASTINATOR.trigger(player.advancements)
                } else if (data.timeTaken(player.world) < (60 * 20)) {
                    BountifulTriggers.RUSH_ORDER.trigger(player.advancements)
                }

                //if (data.timeLeft(player.world) <= (20 * 60))

            }


            // Return success
            return true
        }
        return false
    }

    fun passedChecks(player: PlayerEntity, data: BountyData): Map<BountyEntry, BountyProgress> {
        val checkers = content.map {
            val inst = it.createInstance()
            inst.initialize(player, data)
            inst
        }

        return checkers.map {
            it.objectiveStatus().toMutableMap()
        }.reduce { mapA, mapB ->
            mapA.apply {
                putAll(mapB)
            }
        }
    }


}