package io.github.farshidroohi.reminder.pro

enum class Destination(
    val route: String,
    val label: String,
    val unSelectIcon: Int,
    val selectIcon: Int,
    val contentDescription: String
) {
    HOME(
        route = "home",
        label = "Home",
        unSelectIcon = R.drawable.ic_home_outline,
        selectIcon = R.drawable.ic_home_fill,
        contentDescription = "Home"
    ),
    NOTE(
        route = "note",
        label = "Note",
        unSelectIcon = R.drawable.ic_note_outline,
        selectIcon = R.drawable.ic_note_fill,
        contentDescription = "Note"
    ),
    VOICE(
        route = "voice",
        label = "Voice",
        unSelectIcon = R.drawable.ic_voice_outline,
        selectIcon = R.drawable.ic_voice_fill,
        contentDescription = "Voice"
    ),
    LOCATION(
        route = "location",
        label = "Location",
        unSelectIcon = R.drawable.ic_location_outline,
        selectIcon = R.drawable.ic_location_fill,
        contentDescription = "Location"
    ),
}