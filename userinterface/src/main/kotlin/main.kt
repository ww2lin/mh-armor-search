

fun main() {
    val reader = CsvReader()

    val head = reader.getEquipmentFromCsvFile("data/MH_EQUIP_HEAD.csv", EquipmentType.HEAD)

    head.forEach{ println(it.toString()) }
}