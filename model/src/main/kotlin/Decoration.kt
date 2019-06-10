data class Decoration(var id: Int){

    var name: String = ""
    var rarity:Int = 0
    var slotsNeeded: Int = 0
    var onlineMonsterAvailableAtQuestLevel: Int = NOT_AVAILABLE
    var villageMonsterAvailableAtQuestLevel: Int = NOT_AVAILABLE
    var needBothOnlineAndOffLineQuest: Boolean = false
    var armorSkills: Set<ArmorSkill> = emptySet()
    var itemParts: List<List<ItemPart>> = emptyList()

    companion object {
        private const val NOT_AVAILABLE = 99
    }

    val isAvailable: Boolean
        get() = onlineMonsterAvailableAtQuestLevel != NOT_AVAILABLE || villageMonsterAvailableAtQuestLevel != NOT_AVAILABLE
}