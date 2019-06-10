data class SkillActivationRequirement(
    val id: Int
) {
    var name: String = ""
    var kind: String = ""
    var pointsNeededToActivate: Int = 0
    var classType: ClassType = ClassType.ALL
    var isNegativeSkill: Boolean = false
    // only for displaying purposes.
    // This is what will be shown on the UI
    var displayText: String? = null
}