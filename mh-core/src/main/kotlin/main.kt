fun main() {
    val reader = CsvReader()

    val head = reader.getEquipmentFromCsvFile("data/MH_EQUIP_HEAD.csv", EquipmentType.HEAD)
    val body = reader.getEquipmentFromCsvFile("data/MH_EQUIP_BODY.csv", EquipmentType.BODY)
    val arm = reader.getEquipmentFromCsvFile("data/MH_EQUIP_ARM.csv", EquipmentType.ARM)
    val wst = reader.getEquipmentFromCsvFile("data/MH_EQUIP_WST.csv", EquipmentType.WST)
    val leg = reader.getEquipmentFromCsvFile("data/MH_EQUIP_LEG.csv", EquipmentType.LEG)
    val deco = reader.getDecorationFromCsvFile("data/MH_DECO.csv")
    val skillActivation = reader.getSkillActivationRequirementFromCsvFile("data/MH_SKILL_TRANS.csv")
    val charm = reader.getCharmFromCsvFile("data/MH_CHARM_TABLE.csv")

    println(charm)
}