package com.example.upmo.utils

object PasswordGenerator {

    private val replacements = mapOf(
        'a' to '@',
        'e' to '3',
        'i' to '1',
        'o' to '0',
        's' to '$',
        't' to '7',
        'l' to '1',
        'b' to '6',
        'g' to '9',
        'p' to '9'
    )

    /**
     * Генерация пароля на основе фразы
     */
    fun generatePassword(phrase: String): String {
        // 1. Транслитерация кириллицы в латиницу
        var result = transliterate(phrase)

        // 2. Замена символов
        result = result.map { char ->
            replacements.getOrDefault(char.lowercaseChar(), char)
        }.joinToString("")

        // 3. Дополнительные замены
        result = result.replace("o", "0")
        result = result.replace("e", "3")
        result = result.replace("i", "1")
        result = result.replace("a", "@")

        // 4. Перемешивание регистра (исправлено!)
        result = result.mapIndexed { index, char ->
            if (index % 2 == 0) char.uppercaseChar() else char.lowercaseChar()
        }.joinToString("")

        return result
    }

    private fun transliterate(text: String): String {
        val map = mapOf(
            "Я" to "Ya", "я" to "ya",
            "Ю" to "Yu", "ю" to "yu",
            "Ч" to "Ch", "ч" to "ch",
            "Ш" to "Sh", "ш" to "sh",
            "Щ" to "Sch", "щ" to "sch",
            "Ж" to "Zh", "ж" to "zh",
            "Ц" to "Ts", "ц" to "ts",
            "Й" to "Y", "й" to "y",
            "Ё" to "Yo", "ё" to "yo",
            "А" to "A", "а" to "a",
            "Б" to "B", "б" to "b",
            "В" to "V", "в" to "v",
            "Г" to "G", "г" to "g",
            "Д" to "D", "д" to "d",
            "Е" to "E", "е" to "e",
            "З" to "Z", "з" to "z",
            "И" to "I", "и" to "i",
            "К" to "K", "к" to "k",
            "Л" to "L", "л" to "l",
            "М" to "M", "м" to "m",
            "Н" to "N", "н" to "n",
            "О" to "O", "о" to "o",
            "П" to "P", "п" to "p",
            "Р" to "R", "р" to "r",
            "С" to "S", "с" to "s",
            "Т" to "T", "т" to "t",
            "У" to "U", "у" to "u",
            "Ф" to "F", "ф" to "f",
            "Х" to "H", "х" to "h",
            "Ь" to "", "ь" to "",
            "Ъ" to "", "ъ" to "",
            "Ы" to "Y", "ы" to "y",
            "Э" to "E", "э" to "e"
        )

        var result = text
        map.forEach { (key, value) ->
            result = result.replace(key, value)
        }
        return result
    }
}