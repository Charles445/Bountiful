package ejektaflex.bountiful

import net.minecraftforge.common.ForgeConfigSpec
import org.apache.commons.lang3.tuple.Pair as ApachePair

class BountifulConfig {

    companion object {

        val specPair: ApachePair<Server, ForgeConfigSpec> = ForgeConfigSpec
                .Builder()
                .configure<Server> {
                    Server(it)
                }

        val serverSpec = specPair.right
        val SERVER: Server = specPair.left

    }

    class Server(b: ForgeConfigSpec.Builder) {

        val boardCategory = b.push("bounty_board")!!


        var maxBountiesPerBoard: ForgeConfigSpec.IntValue = b.comment(
                "The maximum number of bounties present at a given board " +
                        "before it must delete some bounties to make room for more."
        ).defineInRange("maxBountiesPerBoard", 8, 1, 21)

        var boardAddFrequency: ForgeConfigSpec.IntValue = b
                .comment("How often, in seconds, we should be adding a bounty to the bounty board.")
                .defineInRange("boardCreationFrequency", 90, 1, 100000)

        var boardLifespan: ForgeConfigSpec.IntValue = b
                .comment("How long, in seconds, a bounty should be" +
                        " able to stay on a board (if it ISN'T pushed off by another bounty).")
                .defineInRange("boardLifespan", 3600, 60, 600000)

        var bountyTimeMin: ForgeConfigSpec.IntValue = b
                .comment("The minimum amount of time, in seconds, you should get to complete a bounty.")
                .defineInRange("minBountyTime", 300, 10, 600000)

        var shouldCountdownOnBoard: ForgeConfigSpec.BooleanValue = b
                .comment("Whether bounties should start counting down as soon as they are created")
                .define("instantCountdown", false)

        var bountyBoardBreakable: ForgeConfigSpec.BooleanValue = b
                .comment(
                        "Whether bounty boards should be able to be broken (currently will lose all decrees/bounties on break)",
                        "This feature requires a world restart to take effect."
                )
                .worldRestart()
                .define("boardBreakable", true)

        var villageGen: ForgeConfigSpec.BooleanValue = b
                .comment("Whether bounty boards should sometimes generate in the world in villages")
                .define("villageGen", true)

        var villageGenRate: ForgeConfigSpec.IntValue = b
                .comment("How often boards should spawn in villages. Higher = more frequent.",
                        "Note/Warning: Boards can show up multiple times, so higher numbers increase the",
                        "likelihood of multiple boards showing up in a single village. This is due to how",
                        "Minecraft generates villages in newer versions of Minecraft.")
                .defineInRange("villageGenRate", 2, 1, 8)


        val datapackCategory = b.pop().push("datapacks")!!

        var blacklistedData: ForgeConfigSpec.ConfigValue<MutableList<String>> = b
                .comment("Blacklists certain bounty data from loading (Use at your own risk)",
                        "Format is (namespace)/[decrees/pools]/(folder)",
                        "Examples:",
                        "\"bountiful/*/*\" would block all default Bountiful data from loading,",
                        "\"bountiful/decrees/*\" would block all default Bountiful decree data from loading,",
                        "\"bountiful/*/apotheosis\" would block Bountiful from loading data in",
                        "\"bountiful/bounties/pools/apotheosis\" as well as \"bountiful/bounties/decrees/apotheosis\".")
                .define("blacklistedData", mutableListOf())


        val bountiesCategory = b.pop().push("bounties")!!

        var timeMultiplier: ForgeConfigSpec.DoubleValue = b
                .comment("A global multiplier for the time needed to complete a bounty.")
                .defineInRange("timeMultiplier", 7.5, 1.0, 10000.0)

        var cashInAtBountyBoard: ForgeConfigSpec.BooleanValue = b
                .comment("If true, you can fulfill bounties by right clicking on a bounty board.",
                        "If false, you can right click anywhere with a bounty."
                )
                .define("cashInAtBoard", true)

        var worthRatio: ForgeConfigSpec.DoubleValue = b
                .comment("The ratio of balance between bounty objectives and rewards.",
                        "Numbers above 1.0 will give bounties higher requirements to complete,",
                        "and numbers below 1.0 will give bounties lower requirements to complete.",
                        "As such, changing this too much can have odd results."
                )
                .defineInRange("worthRatio", 1.0, 0.0, 4.0)

        var rarityChance: ForgeConfigSpec.DoubleValue = b
                .comment(
                        "The odds of any given bounty going from one tier up to the next.",
                        "(Higher Rarity = Higher chance of more rare rewards to show up.)",
                        "At 0.0, all bounties will be common. At 1.0, all bounties will be epic.",
                        "At 0.5, there is a 50% chance of going from any rarity to the next.",
                        "(50% chance of at least Uncommon, 25% of at least Rare, 12.5% chance of Epic)"
                )
                .defineInRange("rarityTierUpChance", 0.4, 0.0, 1.0)


        val entityCategory = b.pop().push("entity_bounties")!!

        var coopKillsCount: ForgeConfigSpec.ConfigValue<Boolean> = b
                .comment("When true, when a mob dies, players near the mob and the mob killer will also",
                        "have their bounties counted towards, if applicable.")
                .define("coopKillsCount", true)

        var coopKillDistance: ForgeConfigSpec.ConfigValue<Double> = b
                .comment("If coopKillsCount is true, this determines how far a player can be from the mob",
                        "or other player for their bounties to also get updated."
                )
                .define("coopKillDistance", 6.0)

        val experienceCategory = b.pop().push("experience_bounties")!!

        var doXpDrop: ForgeConfigSpec.ConfigValue<Boolean> = b
                .comment("If true, XP point bounty rewards will drop where the player is standing.",
                        "If false, rewards will instantly be added to their XP bar.",
                        "(NOTE: Experience bounties are currently disabled)")
                .define("doExperienceDrop", true)

    }

    var debugMode = true


}