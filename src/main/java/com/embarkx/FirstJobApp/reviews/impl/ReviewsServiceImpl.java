package com.embarkx.FirstJobApp.reviews.impl;

import com.embarkx.FirstJobApp.company.Company;
import com.embarkx.FirstJobApp.company.CompanyService;
import com.embarkx.FirstJobApp.reviews.Reviews;
import com.embarkx.FirstJobApp.reviews.ReviewsRepository;
import com.embarkx.FirstJobApp.reviews.ReviewsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewsServiceImpl implements ReviewsService {
    private ReviewsRepository reviewsRepository;
    private CompanyService companyService;

    public ReviewsServiceImpl(ReviewsRepository reviewsRepository,
                              CompanyService companyService) {
        this.reviewsRepository = reviewsRepository;
        this.companyService = companyService;
    }

    @Override
    public List<Reviews> getAllReviews(Long companyId) {
        List<Reviews> reviews = reviewsRepository.findByCompanyId(companyId);
        return reviews;
    }

    @Override
    public boolean addReview(Long companyId, Reviews review) {
        Company company = companyService.getCompanyById(companyId);
        if (company != null) {
            review.setCompany(company);
            reviewsRepository.save(review);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Reviews getReview(Long companyId, Long reviewId) {
        List<Reviews> reviews = reviewsRepository.findByCompanyId(companyId);
        return reviews.stream()
                .filter(review -> review.getId().equals(reviewId))
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean updateReview(Long companyId, Long reviewId, Reviews updatedReview) {
        if(companyService.getCompanyById(companyId) != null){
            updatedReview.setCompany(companyService.getCompanyById(companyId));
            updatedReview.setId(reviewId);
            reviewsRepository.save(updatedReview);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean deleteReview(Long companyId, Long reviewId) {
        if (companyService.getCompanyById(companyId) != null
                && reviewsRepository.existsById(reviewId)) {
            Reviews review = reviewsRepository.findById(reviewId).orElse(null);
            Company company = review.getCompany();
            company.getReviews().remove(review);
            review.setCompany(null);
            companyService.updateCompany(company, companyId);
            reviewsRepository.deleteById(reviewId);
            return true;
        }
        return false;
    }
}
