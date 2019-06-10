
import java.util.HashMap

data class Equipment(val id: Int) {
    companion object {
        private val NOT_AVAILABLE = 99

    }

    var name: String = ""
    var gender: Gender = Gender.BOTH
    var classType: ClassType = ClassType.ALL
    var rarity: Int = 0
    var slots: Int = 0
    // 99 means you cant get it.

    var onlineMonsterAvailableAtQuestLevel: Int = 0
    var villageMonsterAvailableAtQuestLevel: Int = 0

    // guessing this means if you need to do both online/offline quest
    var needBothOnlineAndOffLineQuest: Boolean = false

    var baseDefense: Int = 0
    var maxDefense: Int = 0

    var resistances = emptyList<Resistance>()

    var armorSkills = emptyList<ArmorSkill>()
    var itemParts = emptyList<ItemPart>()

    // Maps: Decoration -> frequency/Count of this jewel
    var equipmentType = EquipmentType.UNKNOWN

    val isAvailable: Boolean
        get() = onlineMonsterAvailableAtQuestLevel != NOT_AVAILABLE || villageMonsterAvailableAtQuestLevel != NOT_AVAILABLE

}