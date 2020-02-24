package com.keylesspalace.tusky.util

import android.text.Spanned

/**
 * Abstracting away Android-specific things.
 */
interface HtmlConverter {
    fun fromHtml(html: String): Spanned

    fun toHtml(text: Spanned): String
}

internal class HtmlConverterImpl : HtmlConverter {
    override fun fromHtml(html: String): Spanned {
        return com.keylesspalace.tusky.util.HtmlUtils.fromHtml(html)
    }

    override fun toHtml(text: Spanned): String {
        return com.keylesspalace.tusky.util.HtmlUtils.toHtml(text)
    }
}