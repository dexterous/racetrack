package racetrack

class AdminFilters {

    def filters = {
        adminOnly(controller:'*', action:'(create|save|edit|update|delete)') {
            before = {
                if(!session.user?.admin) {
                    flash.message = 'Sorry, admin only'
                    redirect controller: 'race', action: 'index'
                    return false
                }
            }
        }
    }
}
