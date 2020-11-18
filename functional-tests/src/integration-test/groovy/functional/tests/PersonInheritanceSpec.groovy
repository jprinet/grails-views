package functional.tests

import grails.testing.mixin.integration.Integration
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import spock.lang.Issue

@Integration(applicationClass = Application)
class PersonInheritanceSpec extends HttpClientSpec {

    void 'test template inheritance produces correct json'() {
        when:
        HttpRequest request = HttpRequest.GET('/person-inheritance')
        HttpResponse<String> rsp = client.toBlocking().exchange(request, String)

        then: 'the response is correct'
        rsp.status() == HttpStatus.OK
        rsp.body() == '{"dob":"01/01/1970","lastName":"Doe","firstName":"John"}'
    }

    @Issue("https://github.com/grails/grails-views/issues/234")
    void 'test template inheritance does not produce NPE when model variable is null'() {
        when:
        HttpRequest request = HttpRequest.GET('/person-inheritance/npe')
        HttpResponse<String> rsp = client.toBlocking().exchange(request, String)

        then: 'the response is correct'
        noExceptionThrown()
        rsp.status() == HttpStatus.OK

    }
}
