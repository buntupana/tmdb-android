package com.buntupana.tmdb.feature.detail.domain.usecase

import com.buntupana.tmdb.feature.detail.domain.model.MediaImages
import com.buntupana.tmdb.feature.detail.domain.repository.DetailRepository
import com.panabuntu.tmdb.core.common.entity.MediaType
import com.panabuntu.tmdb.core.common.entity.NetworkError
import com.panabuntu.tmdb.core.common.entity.Result
import com.panabuntu.tmdb.core.common.entity.map

/**
 * Use case for retrieving media images (posters and backdrops) for a given media ID and type.
 * It prioritizes the main backdrop and poster if provided, placing them at the beginning of their respective lists.
 *
 * @param detailRepository The repository responsible for fetching media details.
 */
class GetMediaImagesUseCase(
    private val detailRepository: DetailRepository
) {

    suspend operator fun invoke(
        mediaId: Long,
        mediaType: MediaType,
        mainBackdrop: String? = null,
        mainPoster: String? = null
    ): Result<MediaImages, NetworkError> {

        return detailRepository.getMediaImages(mediaId = mediaId, mediaType = mediaType).map {
            MediaImages(
                posterList = getFinalList(mainImage = mainPoster, imageList =  it.posterList),
                backdropList = getFinalList(mainImage = mainBackdrop, imageList = it.backdropList)
            )
        }
    }


    /**
     * Helper function to reorder the image list by moving the main image (if present) to the beginning.
     *
     * If [mainImage] is null, the original [imageList] is returned.
     * Otherwise, it extracts the ID from the [mainImage] URL.
     * It then iterates through the [imageList]:
     *  - If an image in [imageList] contains the extracted ID, it's considered the main image.
     *  - Other images are added to a new list.
     * Finally, if the main image was found, it's inserted at the beginning of the new list.
     *
     * @param mainImage The URL of the main image, or null if there isn't one.
     * @param imageList The original list of image URLs.
     * @return A new list of image URLs with the main image (if found) at the first position,
     * or the original [imageList] if [mainImage] is null.
     */
    private fun getFinalList(mainImage: String?, imageList: List<String>): List<String> {

        mainImage ?: return imageList

        val mainImageId = mainImage.substringAfterLast("/").substringBeforeLast(".")
        val newImageList = mutableListOf<String>()
        var mainBackdrop: String? = null

        imageList.forEach { image ->
            if (image.contains(mainImageId)) {
                mainBackdrop = image
            } else {
                newImageList.add(image)
            }
        }
        if (mainBackdrop != null) {
            newImageList.add(0, mainBackdrop)
        }
        return newImageList
    }
}