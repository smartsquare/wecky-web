package de.smartsquare.wecky.weckyweb.domain

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping
class WebsiteController(private val websiteRepository: WebsiteRepository) {

    @GetMapping("website")
    fun getAllWebsites() : MutableIterable<Website> = websiteRepository.findAll()

}