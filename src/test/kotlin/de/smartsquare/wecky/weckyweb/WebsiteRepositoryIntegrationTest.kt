package de.smartsquare.wecky.weckyweb

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput
import com.amazonaws.services.dynamodbv2.model.ResourceInUseException
import de.smartsquare.wecky.weckyweb.domain.Website
import de.smartsquare.wecky.weckyweb.domain.WebsiteRepository
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest(classes = [WeckyWebApplication::class])
@TestPropertySource(properties = [
    "amazon.dynamodb.endpoint=http://localhost:8000/",
    "amazon.aws.accesskey=test1",
    "amazon.aws.secretkey=test231"
])
class WebsiteRepositoryIntegrationTest(
        @Autowired
        val amazonDynamoDB: AmazonDynamoDB,
        @Autowired
        val repository: WebsiteRepository
) {
    private var dynamoDBMapper: DynamoDBMapper? = null

    @BeforeEach
    @Throws(Exception::class)
    fun setup() {
        dynamoDBMapper = DynamoDBMapper(amazonDynamoDB)

        val tableRequest = dynamoDBMapper!!
                .generateCreateTableRequest(Website::class.java)
        tableRequest.provisionedThroughput = ProvisionedThroughput(1L, 1L)

        try {
            amazonDynamoDB.createTable(tableRequest)
        } catch (ex: ResourceInUseException) {
            //ignore existing table
        }

        dynamoDBMapper!!.batchDelete(
                repository.findAll() as List<Website>)
    }

    @Test
    fun single_save_and_retrieve() {
        val foobar = Website("foobar", "https://www.foobar.com")
        repository.save(foobar)

        val result = repository.findAll() as List<Website>

        assertTrue(result.size == 1)
    }
}