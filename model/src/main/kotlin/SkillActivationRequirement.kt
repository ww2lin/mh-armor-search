data class SkillActivationRequirement(
    var id: Int = 0,
    var name: String = "",
    var kind: String = "",
    var pointsNeededToActivate: Int = 0,
    var classType: ClassType = ClassType.UNKNOWN,
    var isNegativeSkill: Boolean = false,
    // only for displaying purposes.
    // This is what will be shown on the UI
    var displayText: String? = null
)