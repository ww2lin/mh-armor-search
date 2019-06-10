data class Equipment(
    var id: Int = 0,
    var name: String = "",
    var gender: Gender = Gender.UNKNOWN,
    var classType: ClassType = ClassType.UNKNOWN,
    var rarity: Int = 0,
    var slots: Int = 0,
    // 99 means you cant get it.
    var onlineMonsterAvailableAtQuestLevel: Int = 0,
    var villageMonsterAvailableAtQuestLevel: Int = 0,

    // guessing this means if you need to do both online/offline quest
    var needBothOnlineAndOffLineQuest: Boolean = false,

    var baseDefense: Int = 0,
    var maxDefense: Int = 0,
    var resistances: List<Resistance> = emptyList(),
    var armorSkills: List<ArmorSkill> = emptyList(),
    var itemParts: List<ItemPart> = emptyList(),
    var equipmentType: EquipmentType = EquipmentType.UNKNOWN
) {
    companion object {
        private val NOT_AVAILABLE = 99
    }

    val isAvailable: Boolean
        get() = onlineMonsterAvailableAtQuestLevel != NOT_AVAILABLE || villageMonsterAvailableAtQuestLevel != NOT_AVAILABLE

}