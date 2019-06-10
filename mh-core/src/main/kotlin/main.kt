fun main() {
    val reader = CsvReader()

    val head = reader.getEquipmentFromCsvFile("data/MH_EQUIP_HEAD.csv", EquipmentType.HEAD)
    val body = reader.getEquipmentFromCsvFile("data/MH_EQUIP_BODY.csv", EquipmentType.BODY)
    val arm = reader.getEquipmentFromCsvFile("data/MH_EQUIP_ARM.csv", EquipmentType.ARM)
    val wst = reader.getEquipmentFromCsvFile("data/MH_EQUIP_WST.csv", EquipmentType.WST)
    val leg = reader.getEquipmentFromCsvFile("data/MH_EQUIP_LEG.csv", EquipmentType.LEG)
    
    val equipments = arrayListOf(head, body, arm, wst, leg)

    val decorations  = reader.getDecorationFromCsvFile("data/MH_DECO.csv")

    val skillActivation = reader.getSkillActivationRequirementFromCsvFile("data/MH_SKILL_TRANS.csv")
    val charm = reader.getCharmFromCsvFile("data/MH_CHARM_TABLE.csv")

    val inputSkillIds = arrayOf(84, 142, 146, 147, 150)
    val inputSkills = skillActivation.values.flatten().filter { it.id in inputSkillIds}

    println(inputSkills)
}