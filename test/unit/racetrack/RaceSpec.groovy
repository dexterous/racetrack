package racetrack

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Race)
class RaceSpec extends Specification {

    void "should convert distance to miles"() {
      def race = new Race(distance: 5.0)

      expect:
      race.inMiles() == 3.107
    }

    void "should not accept past start date"() {
      def race = new Race(startDate: new Date() - 1)

      expect:
      !race.validate()
      race.hasErrors()
      race.errors.getFieldError('startDate').codes.contains('race.startDate.validator.invalid')

      println "Errors: ${ race.errors ?: "no errors found" }"
      println "\nBadField: ${ race.errors.getFieldError('startDate') ?: "startDate wasn't a bad field" }"
      println "\nCode: ${ race.errors.getFieldError('startDate').codes.find { it == 'race.startDate.validator.invalid' } ?: "startDate wasn't a bad field" }"
    }
}
