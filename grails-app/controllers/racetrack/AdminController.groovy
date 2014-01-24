package racetrack

//import static org.springframework.http.HttpStatus.*
//import grails.transaction.Transactional

//@Transactional(readOnly = true)
class AdminController {

    def beforeInterceptor = [action: this.&auth]

    private auth() {
        if(!session.user) {
            redirect controller: 'user', action: 'login'
            return false
        }

        if(!session.user.admin) {
            flash.message = 'Tsk tsk-admins only'
            redirect controller: 'race', action: 'index'
            return false
        }
    }

    def index() {}
}
