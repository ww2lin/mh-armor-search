import java.util.HashSet
import java.util.LinkedList

internal object CsvToModel {

    fun csvEquipmentRowToModel(id: Int, row: Array<String>, equipmentType: EquipmentType): Equipment {
        val name = row[0]
        val gender = Gender.values()[tryParseInt(row[1])]
        val classType = ClassType.values()[tryParseInt(row[2])]
        val rarity = tryParseInt(row[3])
        val slots = tryParseInt(row[4])
        val onlineQuestLevelRequirement = tryParseInt(row[5])
        val villageQuestLevelRequirement = tryParseInt(row[6])
        val needBothOnlineAndOffLineQuest = tryParseInt(row[7]) == 1
        val baseDefense = tryParseInt(row[8])
        val maxDefense = tryParseInt(row[9])

        val resistances = LinkedList<Resistance>()
        resistances.add(Resistance(ResistanceType.FIRE, tryParseInt(row[10])))
        resistances.add(Resistance(ResistanceType.WATER, tryParseInt(row[11])))
        resistances.add(Resistance(ResistanceType.THUNDER, tryParseInt(row[12])))
        resistances.add(Resistance(ResistanceType.ICE, tryParseInt(row[13])))
        resistances.add(Resistance(ResistanceType.DRAGON, tryParseInt(row[14])))

        var armorSkills = mutableListOf<ArmorSkill>()
        armorSkills.add(ArmorSkill(row[15], tryParseInt(row[16])))
        armorSkills.add(ArmorSkill(row[17], tryParseInt(row[18])))
        armorSkills.add(ArmorSkill(row[19], tryParseInt(row[20])))
        armorSkills.add(ArmorSkill(row[21], tryParseInt(row[22])))
        armorSkills.add(ArmorSkill(row[23], tryParseInt(row[24])))
        armorSkills = armorSkills.filter { it.kind.isNotEmpty() && it.points > 0 }.toMutableList()


        var itemParts = mutableListOf<ItemPart>()
        itemParts.add(ItemPart(row[25], tryParseInt(row[26])))
        itemParts.add(ItemPart(row[27], tryParseInt(row[28])))
        itemParts.add(ItemPart(row[29], tryParseInt(row[30])))
        itemParts.add(ItemPart(row[31], tryParseInt(row[32])))
        itemParts = itemParts.filter { it.name.isNotEmpty() && it.amount > 0 }.toMutableList()


        return Equipment().apply {
            this.id = id
            this.name = name
            this.gender = gender
            this.classType = classType
            this.rarity = rarity
            this.slots = slots
            this.onlineMonsterAvailableAtQuestLevel = onlineQuestLevelRequirement
            this.villageMonsterAvailableAtQuestLevel = villageQuestLevelRequirement
            this.needBothOnlineAndOffLineQuest = needBothOnlineAndOffLineQuest
            this.baseDefense = baseDefense
            this.maxDefense = maxDefense
            this.resistances = resistances
            this.armorSkills = armorSkills
            this.itemParts = itemParts
            this.equipmentType = equipmentType
        }
    }

    fun csvSkillActivationRequirementRowToModel(row: Array<String>, id: Int): SkillActivation {
        val name = row[0]
        val kind = row[1]
        val pointsToActivate = tryParseInt(row[2])
        val classType = ClassType.values()[tryParseInt(row[3])]

        val displayText = row[5]

        return SkillActivation().apply {
            this.id = id
            this.name = name
            this.kind = kind
            this.pointsNeededToActivate = pointsToActivate
            this.classType = classType
            this.isNegativeSkill = pointsToActivate <= 0
            this.displayText = displayText
        }
    }

    fun csvDecorationRowToModel(id: Int, row: Array<String>): Decoration {
        val name = row[0]
        val rarity = tryParseInt(row[1])
        val slotsNeeded = tryParseInt(row[2])
        val onlineQuestLevelRequirement = tryParseInt(row[3])
        val villageQuestLevelRequirement = tryParseInt(row[4])
        val needBothOnlineAndOffLineQuest = tryParseInt(row[5]) == 1

        val armorSkills = HashSet<ArmorSkill>()
        armorSkills.add(ArmorSkill(row[6], tryParseInt(row[7])))
        armorSkills.add(ArmorSkill(row[8], tryParseInt(row[9])))

        val itemParts = LinkedList<List<ItemPart>>()
        val itemParts1 = LinkedList<ItemPart>()
        val itemParts2 = LinkedList<ItemPart>()

        itemParts1.add(ItemPart(row[10], tryParseInt(row[11])))
        itemParts1.add(ItemPart(row[12], tryParseInt(row[13])))
        itemParts1.add(ItemPart(row[14], tryParseInt(row[15])))
        itemParts1.add(ItemPart(row[16], tryParseInt(row[17])))

        itemParts2.add(ItemPart(row[18], tryParseInt(row[19])))
        itemParts2.add(ItemPart(row[20], tryParseInt(row[21])))
        itemParts2.add(ItemPart(row[22], tryParseInt(row[23])))
        itemParts2.add(ItemPart(row[24], tryParseInt(row[25])))

        if (!itemParts1.isEmpty()) {
            itemParts.add(itemParts1)
        }

        if (!itemParts2.isEmpty()) {
            itemParts.add(itemParts2)
        }

        return Decoration().apply {
            this.id = id
            this.name = name
            this.rarity = rarity
            this.slotsNeeded = slotsNeeded
            this.onlineMonsterAvailableAtQuestLevel = onlineQuestLevelRequirement
            this.villageMonsterAvailableAtQuestLevel = villageQuestLevelRequirement
            this.needBothOnlineAndOffLineQuest = needBothOnlineAndOffLineQuest
            this.armorSkills = armorSkills
            this.itemParts = itemParts
        }
    }

    fun csvCharmRowToModel(charmTypes: Array<String>, row1: Array<String>, row2: Array<String>): List<CharmData> {
        val skillkind = row1[0]
        return listOf(
            getCharmPoints(skillkind, row1[2], row2[2], charmTypes[2]),
            getCharmPoints(skillkind, row1[3], row2[3], charmTypes[3]),
            getCharmPoints(skillkind, row1[4], row2[4], charmTypes[4]),
            getCharmPoints(skillkind, row1[5], row2[5], charmTypes[5])
        ).flatMap { it.asIterable() }
    }

    private fun getCharmPoints(
        skillkind: String,
        skill1: String?,
        skill2: String?,
        charmType: String
    ): List<CharmData> {
        val charmDatas = mutableListOf<CharmData>()
        if (skill1 != null && !skill1.isEmpty()) {
            // skill is separated by ~
            val skillRange = skill1.split("~".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val min = tryParseInt(skillRange[0])
            val max = tryParseInt(skillRange[1])

            charmDatas.add(CharmData(skillkind, charmType, min, max, 1))
        }

        if (skill2 != null && !skill2.isEmpty()) {
            // skill is separated by ~
            val skillRange = skill2.split("~".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val min = tryParseInt(skillRange[0])
            val max = tryParseInt(skillRange[1])

            charmDatas.add(CharmData(skillkind, charmType, min, max, 2))
        }

        return charmDatas
    }

    private fun tryParseInt(value: String): Int {
        return value.toIntOrNull() ?: 0
    }
}