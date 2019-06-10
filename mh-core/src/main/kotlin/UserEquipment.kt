
data class UserEquipment(val equipment: Equipment,
                         val decorations: List<Decoration> = ArrayList(),
                         var equipmentWeight: Float = Float.MIN_VALUE) {

    fun getArmorSkills(): List<ArmorSkill> = equipment.armorSkills

    fun getSlots(): Int = equipment.slots
}