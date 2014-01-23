package racetrack

class LoginTagLib {
    static defaultEncodeAs = 'raw'
    //static encodeAsForTags = [tagName: 'raw']

    def loginControl = {
        if(session?.user) {
            out << 'Hello ' << session.user.login << ' [' << link(controller: 'user', action: 'logout') { 'Logout' } << ']'
        } else {
            out << '[' << link(controller: 'user', action: 'login') { 'Login' } << ']'
        }
    }
}
