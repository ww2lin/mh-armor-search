
/**
 * defines one type of charm
 *
 * This is a model that maps to the CSV.
 * The reason we have both [GeneratedCharm] and this class is because
 * Before brute-forcing a charm, we have no idea which of the charm position(charmPoints)
 * will be valid. Thus this class defines what kind of values/position the charm skill can be in.
 */
data class CharmData (
    val skillKind: String,
    val charmType: String,
    val minPoint: Int,
    val maxPoint: Int,
    val skillPosition: Int
)
