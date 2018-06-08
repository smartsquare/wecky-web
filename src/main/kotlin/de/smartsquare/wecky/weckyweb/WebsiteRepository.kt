package de.smartsquare.wecky.weckyweb

import org.socialsignin.spring.data.dynamodb.repository.EnableScan
import org.springframework.data.repository.CrudRepository

@EnableScan
interface WebsiteRepository : CrudRepository<Website, String>