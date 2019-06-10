data class ArmorSkill (// Note this is NOT the name of the skill, rather its the 'kind' of the skill
    // E.g its NOT AuS, AuM, or negate stun,
    // it is Attack, Poison, Stun, Hearing
    val kind: String = "", // Can be positive or negative
    val points: Int = 0
) {
    val isPositive: Boolean
        get() = points > 0

//    val isTorsoUp: Boolean
//        get() = points == 0 && !isNull || kind!!.equals(StringConstants.ARMOR_SKILL_TORSO_UP, ignoreCase = true)

    fun isKind(kind: String): Boolean {
        return this.kind.equals(kind, ignoreCase = true)
    }
}