package de.smartsquare.wecky.weckyweb.domain

import org.socialsignin.spring.data.dynamodb.repository.EnableScan
import org.springframework.data.repository.CrudRepository

@EnableScan
interface UserRepository : CrudRepository<User, String> {
}