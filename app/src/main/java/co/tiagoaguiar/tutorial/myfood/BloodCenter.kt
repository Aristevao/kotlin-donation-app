package co.tiagoaguiar.tutorial.myfood

data class BloodCenter(
    val id: Int,
    val bannerUrl: String,
    val text: String,
    val city: String,
    val distance: Double,
    val address: String,
    val neighborhood: String
)