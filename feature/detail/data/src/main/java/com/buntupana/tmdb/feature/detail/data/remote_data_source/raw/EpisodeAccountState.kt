package com.buntupana.tmdb.feature.detail.data.remote_data_source.raw

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.Serializer
import kotlinx.serialization.encoding.CompositeDecoder.Companion.DECODE_DONE
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.decodeStructure

@Serializable(with = EpisodeAccountStateRawSerializer::class)
data class EpisodeAccountStateRaw(
    val id: Long,
    @SerialName("episode_number")
    val episodeNumber: Int,
    val rated: RatedRaw? = null,
)

@OptIn(ExperimentalSerializationApi::class)
@Serializer(forClass = EpisodeAccountStateRaw::class)
object EpisodeAccountStateRawSerializer : KSerializer<EpisodeAccountStateRaw> {

    override fun deserialize(decoder: Decoder): EpisodeAccountStateRaw {
        return decoder.decodeStructure(descriptor) {

            var id: Long? = 0
            var episodeNumber = 0
            var rated: RatedRaw? = null

            loop@ while (true) {
                when (val index = decodeElementIndex(descriptor)) {
                    DECODE_DONE -> break@loop

                    0 -> id = decodeLongElement(descriptor, 0)
                    1 -> episodeNumber = decodeIntElement(descriptor, 1)
                    2 -> {
                        try {
                            decodeBooleanElement(descriptor, 2)
                        } catch (e: Exception) {
                            rated = decodeSerializableElement(
                                descriptor,
                                2,
                                RatedRaw.serializer(),
                                RatedRaw(
                                    0.0f
                                )
                            )
                        }
                    }

                    else -> throw SerializationException("Unexpected index $index")
                }
            }

            EpisodeAccountStateRaw(
                requireNotNull(id),
                episodeNumber,
                rated
            )
        }
    }
}