package racetrack

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(User)
class UserSpec extends Specification {

  def setup() {
  }

  def cleanup() {
  }

  void "test simple constraint"() {
    def user = new User(login: 'someone', password: 'blah', role: 'admin')

    expect:
    user.validate()
  }

  void "test unique constraint"() {
    given:
    def jdoe = new User(login: 'jdoe', password: 'password').save()
    def admin = new User(login: 'admin', password: 'password').save()
    mockForConstraintsTests(User, [jdoe, admin])

    when:
    def badUser = new User(login: 'jdoe', password: 'password')
    badUser.save()

    then:
    User.count() == 2
    badUser.errors['login'] == 'unique'

    when:
    def goodUser = new User(login: 'good', password: 'password')
    goodUser.save()

    then:
    !goodUser.hasErrors()
    User.count() == 3
    User.findByLogin('good')
  }
}
