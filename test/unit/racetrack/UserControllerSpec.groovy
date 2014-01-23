package racetrack



import grails.test.mixin.*
import spock.lang.*

@TestFor(UserController)
@Mock(User)
class UserControllerSpec extends Specification {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
        params['login'] = 'test-user'
        params['password'] = 'test-password'
    }

    void "Test the authenticate action adds user to session"() {
        given:"A user in the system"
        def user = new User(login: 'jdoe', password: 'password')
        user.save(flush: true)

        when:"The authenticate action is executed with valid credentials"
        params.login = 'jdoe'
        params.password = 'password'
        controller.authenticate()

        then:"A redirect is issued to the race controller"
        response.redirectedUrl == '/race/index'

        and:"The authenticated user is added to the session"
        session.user == user

        and:"An appropriate flash message is sent"
        flash.message == 'default.login.succeeded.message'
    }

    void "Test the authenticate action does not add user with invalid creds to session"() {
        given:"A user in the system"
        def user = new User(login: 'jdoe', password: 'password')
        user.save(flush: true)

        when:"The authenticate action is executed with invalid credentials"
        params.login = 'jdoe'
        params.password = 'blah'
        controller.authenticate()

        then:"A redirect is issued to the race controller"
        response.redirectedUrl == '/user/login'

        and:"The user is not added to the session"
        session.user == null

        and:"An appropriate flash message is sent"
        flash.message == 'default.login.failed.message'
    }

    void "Test the index action returns the correct model"() {
        when:"The index action is executed"
            controller.index()

        then:"The model is correct"
            !model.userInstanceList
            model.userInstanceCount == 0
    }

    void "Test the create action returns the correct model"() {
        when:"The create action is executed"
            controller.create()

        then:"The model is correctly created"
            model.userInstance!= null
    }

    void "Test the save action correctly persists an instance"() {

        when:"The save action is executed with an invalid instance"
            def user = new User()
            user.validate()
            controller.save(user)

        then:"The create view is rendered again with the correct model"
            model.userInstance!= null
            view == 'create'

        when:"The save action is executed with a valid instance"
            response.reset()
            populateValidParams(params)
            user = new User(params)

            controller.save(user)

        then:"A redirect is issued to the show action"
            response.redirectedUrl == '/user/show/1'
            flash.message != null
            User.count() == 1
    }

    void "Test that the show action returns the correct model"() {
        when:"The show action is executed with a null domain"
            controller.show(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the show action"
            populateValidParams(params)
            def user = new User(params)
            controller.show(user)

        then:"A model is populated containing the domain instance"
            model.userInstance == user
    }

    void "Test that the edit action returns the correct model"() {
        when:"The edit action is executed with a null domain"
            controller.edit(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the edit action"
            populateValidParams(params)
            def user = new User(params)
            controller.edit(user)

        then:"A model is populated containing the domain instance"
            model.userInstance == user
    }

    void "Test the update action performs an update on a valid domain instance"() {
        when:"Update is called for a domain instance that doesn't exist"
            controller.update(null)

        then:"A 404 error is returned"
            response.redirectedUrl == '/user/index'
            flash.message != null


        when:"An invalid domain instance is passed to the update action"
            response.reset()
            def user = new User()
            user.validate()
            controller.update(user)

        then:"The edit view is rendered again with the invalid instance"
            view == 'edit'
            model.userInstance == user

        when:"A valid domain instance is passed to the update action"
            response.reset()
            populateValidParams(params)
            user = new User(params).save(flush: true)
            controller.update(user)

        then:"A redirect is issues to the show action"
            response.redirectedUrl == "/user/show/$user.id"
            flash.message != null
    }

    void "Test that the delete action deletes an instance if it exists"() {
        when:"The delete action is called for a null instance"
            controller.delete(null)

        then:"A 404 is returned"
            response.redirectedUrl == '/user/index'
            flash.message != null

        when:"A domain instance is created"
            response.reset()
            populateValidParams(params)
            def user = new User(params).save(flush: true)

        then:"It exists"
            User.count() == 1

        when:"The domain instance is passed to the delete action"
            controller.delete(user)

        then:"The instance is deleted"
            User.count() == 0
            response.redirectedUrl == '/user/index'
            flash.message != null
    }
}
