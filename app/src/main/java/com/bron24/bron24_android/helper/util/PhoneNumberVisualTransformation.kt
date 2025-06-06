package com.bron24.bron24_android.helper.util

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class PhoneNumberVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val trimmed = if (text.text.length >= 9) text.text.substring(0..8) else text.text
        var output = ""
        for (i in trimmed.indices) {
            output += trimmed[i]
            if (i == 1 || i == 4 || i == 6) output += " "
        }

        val phoneNumberOffsetTranslator = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                if (offset <= 1) return offset
                if (offset <= 4) return offset + 1
                if (offset <= 6) return offset + 2
                if (offset <= 9) return offset + 3
                return 12
            }

            override fun transformedToOriginal(offset: Int): Int {
                if (offset <= 2) return offset
                if (offset <= 6) return offset - 1
                if (offset <= 9) return offset - 2
                if (offset <= 12) return offset - 3
                return 9
            }
        }

        return TransformedText(AnnotatedString(output), phoneNumberOffsetTranslator)
    }
}