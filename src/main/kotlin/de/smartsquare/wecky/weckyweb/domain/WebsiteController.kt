package de.smartsquare.wecky.weckyweb.domain

import org.springframework.web.bind.annotation.*

@RestController
class WebsiteController(private val websiteRepository: WebsiteRepository) {

    @GetMapping("website")
    fun getAllWebsites() : MutableIterable<Website> = websiteRepository.findAll()

    @PutMapping("website")
    fun addWebsite(@RequestBody website: Website) = websiteRepository.save(website)

    @DeleteMapping("website/{domain}")
    fun deleteWebsite(@PathVariable domain: String) = websiteRepository.deleteById(domain)

}