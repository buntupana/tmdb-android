package com.buntupana.tmdb.feature.detail.data.raw

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.Serializer
import kotlinx.serialization.encoding.CompositeDecoder.Companion.DECODE_DONE
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.decodeStructure

@Serializable(with = MediaAccountStateRawSerializer::class)
data class MediaAccountStateRaw(
    val id: Long,
    val favorite: Boolean,
    val rated: RatedRaw? = null,
    val watchlist: Boolean
)

@OptIn(ExperimentalSerializationApi::class)
@Serializer(forClass = MediaAccountStateRaw::class)
object MediaAccountStateRawSerializer : KSerializer<MediaAccountStateRaw> {

    override fun deserialize(decoder: Decoder): MediaAccountStateRaw {
        return decoder.decodeStructure(descriptor) {

            var id: Long? = 0
            var favorite = false
            var rated: RatedRaw? = null
            var watchlist = false

            loop@ while (true) {
                when (val index = decodeElementIndex(descriptor)) {
                    DECODE_DONE -> break@loop

                    0 -> id = decodeLongElement(descriptor, 0)
                    1 -> favorite = decodeBooleanElement(descriptor, 1)
                    2 -> {
                        try {
                            decodeBooleanElement(descriptor, 2)
                        } catch (e: Exception) {
                            rated = decodeSerializableElement(
                                descriptor,
                                2,
                                RatedRaw.serializer(),
                                RatedRaw(0.0f)
                            )
                        }
                    }

                    3 -> watchlist = decodeBooleanElement(descriptor, 3)

                    else -> throw SerializationException("Unexpected index $index")
                }
            }

            MediaAccountStateRaw(
                requireNotNull(id),
                favorite,
                rated,
                watchlist
            )
        }
    }
}