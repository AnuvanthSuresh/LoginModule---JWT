package com.stock.jparepository;

import org.springframework.data.repository.CrudRepository;

import com.stock.requests.SignupRequestModel;

public interface SignupRequestRepository extends CrudRepository<SignupRequestModel, String> {

}
