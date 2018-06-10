package de.smartsquare.wecky.weckyweb.domain

import org.socialsignin.spring.data.dynamodb.repository.EnableScan
import org.springframework.data.repository.CrudRepository

@EnableScan
interface WebsiteRepository : CrudRepository<Website, String> {
    fun findByUrl(url: String): Iterable<Website>
}