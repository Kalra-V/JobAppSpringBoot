package com.embarkx.FirstJobApp.reviews;

import java.util.List;

public interface ReviewsService {
    List<Reviews> getAllReviews(Long companyId);
    boolean addReview(Long companyId, Reviews review);
    Reviews getReview(Long companyId, Long reviewId);
    boolean updateReview(Long companyId, Long reviewId, Reviews updatedReview);
    boolean deleteReview(Long companyId, Long reviewId);
}
