package com.benhagy.gamefinder3.util

import android.graphics.Typeface
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.core.text.HtmlCompat
import com.benhagy.gamefinder3.R
import com.benhagy.gamefinder3.domain.models.Platform
import com.benhagy.gamefinder3.domain.models.Tag
import java.time.LocalDateTime
import java.util.Locale

/*
this file contains helper functions to parse input data from various sources into readable text
 */


// extension function to preserve formatting when converting an HTML string into an Annotated String
fun Spanned.toAnnotatedString(): AnnotatedString = buildAnnotatedString {
    val spanned = this@toAnnotatedString
    append(spanned.toString())
    getSpans(0, spanned.length, Any::class.java).forEach { span ->
        val start = getSpanStart(span)
        val end = getSpanEnd(span)
        when (span) {
            is StyleSpan -> when (span.style) {
                Typeface.BOLD -> addStyle(SpanStyle(fontWeight = FontWeight.Bold), start, end)
                Typeface.ITALIC -> addStyle(SpanStyle(fontStyle = FontStyle.Italic), start, end)
                Typeface.BOLD_ITALIC -> addStyle(
                    SpanStyle(
                        fontWeight = FontWeight.Bold,
                        fontStyle = FontStyle.Italic
                    ), start, end
                )
            }

            is UnderlineSpan -> addStyle(
                SpanStyle(textDecoration = TextDecoration.Underline),
                start,
                end
            )

            is ForegroundColorSpan -> addStyle(
                SpanStyle(color = Color(span.foregroundColor)),
                start,
                end
            )
        }
    }
}

// above function is called here, the actual parsing function
fun parse(input: String): AnnotatedString {
    return HtmlCompat.fromHtml(input, HtmlCompat.FROM_HTML_MODE_LEGACY).toAnnotatedString()
}

// release date formatter
fun parseReleaseDate(input: String?): String {
    if ( input?.length != 10) {
        return "Unknown release date"
    } else {
        val year = input.take(4)
        val month = input.substring(5..6)
        val day = input.substring(8..9)
        val monthAsText = when (month) {
            "01" -> "Jan"
            "02" -> "Feb"
            "03" -> "Mar"
            "04" -> "Apr"
            "05" -> "May"
            "06" -> "Jun"
            "07" -> "July"
            "08" -> "Aug"
            "09" -> "Sept"
            "10" -> "Oct"
            "11" -> "Nov"
            else -> "Dec"
        }
        return "$monthAsText $day, $year"
    }
}

// assigns correct esrb logos
fun parseEsrbAsLogo(rating: String): Int {
    return when(rating) {
        "Everyone" -> R.drawable.everyone
        "Everyone 10+" -> R.drawable.everyone
        "Teen" -> R.drawable.teen
        "Mature" -> R.drawable.mature
        "Adults Only" -> R.drawable.adults_only
        else -> R.drawable.rating_pending
    }
}
// assigns correct esrb fluff text
fun parseEsrbFluffText(rating: String): String {
    return when(rating) {
        "Everyone" -> "Rated E for Everyone"
        "Everyone 10+" -> "Rated E for Everyone"
        "Teen" -> "Rated T for Teen"
        "Mature" -> "Rated M for Mature"
        "Adults Only" -> "Rated AO for Adults Only"
        else -> "Rating unknown or pending"
    }
}

// maps list into a readable string for tags on the details screen
fun parseTagsList(tags: List<Tag>): String {
    return (tags.map { it.name }).toString().removeSurrounding("[", "]")
}

// maps list into a readable string for platforms on the details screen
fun parsePlatformsList(platforms: List<Platform>): String {
    return (platforms.map { it.name }).toString().removeSurrounding("[", "]")
}

// parses web urls as hyperlinks reading "Official Website" (obscures actual web url)
fun parseWebsiteAsHyperlink(website: String): AnnotatedString {
    val output = buildAnnotatedString {
        append("Official Website")

        pushStringAnnotation(tag = website, annotation = website)
        withStyle(style = SpanStyle(color = Color.Cyan)) {

        }
        pop()
    }
    return output
}

// parses date added for the bookmarks screen
fun parseDate(date: LocalDateTime): String {
    val year = date.year.toString()
    val month = date.month.toString().lowercase()
    val day = date.dayOfMonth.toString()
    return "${month.replaceFirstChar{ 
        if (it.isLowerCase()) {
            it.titlecase(Locale.ROOT) }
        else it.toString() }} $day, $year"
}