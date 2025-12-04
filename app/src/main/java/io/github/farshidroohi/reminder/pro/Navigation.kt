package io.github.farshidroohi.reminder.pro

enum class Destination(
    val route: String,
    val label: String,
    val icon: Int,
    val contentDescription: String
) {
    HOME("home", "Home", R.drawable.ic_home, "Home"),
    NOTE("note", "Note", R.drawable.ic_note, "Note"),
    VOICE("voice", "Voice", R.drawable.ic_voice, "Voice"),
    LOCATION("location", "Location", R.drawable.ic_location, "Location"),
}