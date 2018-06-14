package de.smartsquare.wecky.weckyweb.domain

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
@CrossOrigin
@RestController("website")
class WebsiteController(private val websiteRepository: WebsiteRepository) {

    @GetMapping
    fun getAllWebsites() : MutableIterable<Website> = websiteRepository.findAll()

    @PutMapping
    fun addWebsite(@RequestBody website: Website) = websiteRepository.save(website)

}