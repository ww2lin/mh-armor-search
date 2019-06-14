import model.EquipmentSet
import model.UserEquipment
import kotlin.math.max

const val uninputskillWeight = 0.05f
const val topSizeEquipmentToSearch = 3

fun main() {
    val reader = CsvReader()

    val head = reader.getEquipmentFromCsvFile("data/MH_EQUIP_HEAD.csv", EquipmentType.HEAD)
    val body = reader.getEquipmentFromCsvFile("data/MH_EQUIP_BODY.csv", EquipmentType.BODY)
    val arm = reader.getEquipmentFromCsvFile("data/MH_EQUIP_ARM.csv", EquipmentType.ARM)
    val wst = reader.getEquipmentFromCsvFile("data/MH_EQUIP_WST.csv", EquipmentType.WST)
    val leg = reader.getEquipmentFromCsvFile("data/MH_EQUIP_LEG.csv", EquipmentType.LEG)

    val equipments = arrayListOf(head, body, arm, wst, leg)

    val decorations = reader.getDecorationFromCsvFile("data/MH_DECO.csv")

    val skillActivation = reader.getSkillActivationRequirementFromCsvFile("data/MH_SKILL_TRANS.csv")
    val charm = reader.getCharmFromCsvFile("data/MH_CHARM_TABLE.csv")

    val inputSkillIds = arrayOf(84, 142, 146, 147, 150)
    val inputSkills = skillActivation.values.flatten().filter { it.id in inputSkillIds }

    val list = filterList(Gender.MALE, equipments, inputSkills)
    val equipmentSets = getEquipmentSets(list)
    println(inputSkills)
}

fun applyDecorationAndFindCharm(equipmentSets: List<EquipmentSet>) {

}

fun findDecoration(equipmentSet: EquipmentSet) {
    val userEquipments = equipmentSet.userEquipments

}

fun getEquipmentSets(
    equipments: List<List<UserEquipment>>
): List<EquipmentSet> {
    val size = equipments.size
    var previous = ArrayList<EquipmentSet>()
    var result = ArrayList<EquipmentSet>()
    // base case
    equipments[0].forEach {
        val lst = ArrayList<UserEquipment>()
        lst.add(it)
        previous.add(EquipmentSet(lst))
    }

    for (i in 1 until size) {
        previous.forEach { previousEquipmentSet ->
            equipments[i].forEach { equipmentSet ->
                val copy = ArrayList(previousEquipmentSet.userEquipments)
                copy.add(equipmentSet)
                result.add(EquipmentSet(copy))
            }
        }
        previous = ArrayList(result)
        result.clear()
    }

    result = previous

    println("${result.size} sets to be try $result")
    return result
}

/**
 * Weight is computed by the (sum of "wanted skill" points)/(sum of "needed" skills points)
 * @param userEquipment - the armor skills from a equipment to be calculated
 * @param activationTable - expected map from {@link SkillActivation.kind} to {@ SkillActivation.pointsNeededToActivate}
 * @return the weight of this equipment
 */
fun computeWeight(userEquipment: UserEquipment, activationTable: Map<String, Int>): Float {
    // keep track of what is the total skill based on a equipment's skill kind
    val totalPointsMap = HashMap<String, Int>()
    val armorPointsMap = HashMap<String, Float>()
    val weightMap = HashMap<String, Float>()
    userEquipment.getArmorSkills().forEach { armorSkill ->
        val kind = armorSkill.kind

        totalPointsMap[kind] = totalPointsMap.getOrDefault(kind, 0) + activationTable.getOrDefault(kind, 0)

        // if this skill did not input they want it, give it a weight of 0.1
        armorPointsMap[kind] = armorPointsMap.getOrDefault(kind, 0f) + when (activationTable.containsKey(kind)) {
            true -> armorSkill.points.toFloat()
            else -> uninputskillWeight
        }
    }

    armorPointsMap.forEach { (kind, points) ->
        weightMap[kind] = points / max(1, totalPointsMap.getValue(kind))
    }

    // TODO: calculate weight based on the decorations
    val slotWeight = when (val slots = userEquipment.getSlots()) {
        3 -> slots / 5f
        2 -> slots / 7f
        else -> slots / 10f
    }

    return weightMap.values.sum() + slotWeight
}

fun filterList(
    gender: Gender,
    equipments: List<List<Equipment>>,
    inputSkill: List<SkillActivation>,
    limit: Int = topSizeEquipmentToSearch
): List<List<UserEquipment>> {
    val skillKinds = inputSkill.map { it.kind }
    val filteredLst: ArrayList<List<UserEquipment>> = ArrayList()
    val activationTable = inputSkill.associateBy({ it.kind }, { it.pointsNeededToActivate })

    val totalPointsNeeded = activationTable.values.sum()

    equipments.forEach { all ->
        val filterPart = all.filter { equipment ->
            (equipment.gender == gender || equipment.gender == Gender.BOTH)
                    && (equipment.classType == ClassType.BLADEMASTER || equipment.classType == ClassType.ALL)
                    && equipment.armorSkills.any { armorSkill ->
                armorSkill.kind in skillKinds && armorSkill.isPositive
            }
        }.map { UserEquipment(it) }
        filteredLst.add(filterPart)
    }

    filteredLst.forEach { userEquipments ->
        userEquipments.forEach { userEquipment ->
            userEquipment.equipmentWeight = computeWeight(userEquipment, activationTable)
        }
    }

    for (i in 0 until filteredLst.size) {
        filteredLst[i] =
            filteredLst[i].sortedByDescending { userEquipment -> userEquipment.equipmentWeight }.take(limit)
    }

    filteredLst.forEach {
        it.forEach {
            println("${it.equipmentWeight}  $it")
        }
        println("--------------------------")
    }

    return filteredLst
}