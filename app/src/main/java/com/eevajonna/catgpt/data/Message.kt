package com.eevajonna.catgpt.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters

@Entity
data class Message(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val text: String = "",
    val sender: Sender = Sender.ME,
    @TypeConverters(ReactionConverter::class)
    val reaction: Reaction? = null,
)

enum class Sender(val value: String) {
    CAT("CAT"),
    ME("ME"),
}

enum class Reaction {
    GRINNING_CAT {
        override val contentDescription: String = "Grinning cat"
        override val emoji: String = "😺"
    },
    GRINNING_CAT_WITH_SMILING_EYES {
        override val contentDescription: String = "Grinning cat with smiling eyes"
        override val emoji: String = "😸"
    },
    WEARY_CAT {
        override val contentDescription: String = "Weary cat"
        override val emoji: String = "🙀"
    },
    SMILING_CAT_WITH_HEART_EYES {
        override val contentDescription: String = "Smiling cat with heart eyes"
        override val emoji: String = "😻"
    }, ;

    abstract val emoji: String
    abstract val contentDescription: String
}

class ReactionConverter() {
    @TypeConverter
    fun fromReaction(reaction: Reaction): Int = reaction.ordinal

    @TypeConverter
    fun toReaction(value: Int): Reaction = enumValues<Reaction>()[value]
}
