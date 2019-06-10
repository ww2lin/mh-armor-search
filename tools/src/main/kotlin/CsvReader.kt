import java.io.FileReader
import com.opencsv.CSVReader
import java.util.HashMap
import java.util.LinkedHashMap
import java.util.ArrayList


class CsvReader {

    fun getEquipmentFromCsvFile(path: String, equipmentType: EquipmentType): List<Equipment> {
        val reader = CSVReader(FileReader(path))
        var id = 1
        val lst = ArrayList<Equipment>()
        var nextLine: Array<String>
        // skip over the header
        reader.readNext()

        val it = reader.iterator()
        while (it.hasNext()) {
            nextLine = it.next()
            val equipment = CsvToModel.csvEquipmentRowToModel(id++, nextLine, equipmentType)
            lst.add(equipment)
        }
        reader.close()
        return lst
    }

    fun getSkillActivationRequirementFromCsvFile(path: String): Map<String, List<SkillActivationRequirement>> {
        var reader = CSVReader(FileReader(path))
        val skillActivationChart = LinkedHashMap<String, MutableList<SkillActivationRequirement>>()
        var id = 1
        var nextLine: Array<String>

        // skip over the header
        reader.readNext()
        val it = reader.iterator()
        while (it.hasNext()) {
            nextLine = it.next()
            // nextLine[] is an array of values from the line
            val skillActivationRequirement = CsvToModel.csvSkillActivationRequirementRowToModel(nextLine, id++)

            // Check to see if this kind of skill already exists, if so append it to the same list
            val kind = skillActivationRequirement.kind
            val skillActivationRequirements = skillActivationChart.getOrDefault(kind, ArrayList())
            skillActivationRequirements.add(skillActivationRequirement)

            skillActivationChart[kind] = skillActivationRequirements

        }
        reader.close()
        return skillActivationChart
    }

    fun getDecorationFromCsvFile(path: String): Map<String, List<Decoration>> {
        val reader = CSVReader(FileReader(path))
        var id = 1
        val decorationMap = HashMap<String, MutableList<Decoration>>()
        var nextLine: Array<String>

        // skip over the header
        reader.readNext()

        val it = reader.iterator()
        while (it.hasNext()) {
            nextLine = it.next()
            // nextLine[] is an array of values from the line
            val decoration = CsvToModel.csvDecorationRowToModel(id++, nextLine)
            decoration.armorSkills.forEach { armorSkill ->
                val decorationList = decorationMap.getOrDefault(armorSkill.kind, ArrayList())
                decorationList.add(decoration)
                decorationMap.put(armorSkill.kind, decorationList)
            }
        }
        reader.close()
        return decorationMap
    }

    fun getCharmFromCsvFile(path: String): Map<String, List<CharmData>> {
        val reader = CSVReader(FileReader(path))
        val charmMap = HashMap<String, List<CharmData>>()
        val header: Array<String>
        var nextLine1: Array<String>
        var nextLine2: Array<String>

        // skip over the header
        header = reader.readNext()

        val it = reader.iterator()
        while (it.hasNext()) {
            nextLine1 = it.next()
            if (!it.hasNext()) break
            nextLine2 = it.next()

            val charmDatas = CsvToModel.csvCharmRowToModel(header, nextLine1, nextLine2)
            val skillKind = nextLine1[0]
            charmMap[skillKind] = charmDatas
        }
        reader.close()
        return charmMap
    }
}